package com.ipdnaeip.wizardrynextgeneration.handler;

import com.ipdnaeip.wizardrynextgeneration.enchantment.EnchantmentPhalanx;
import com.ipdnaeip.wizardrynextgeneration.enchantment.EnchantmentRanger;
import com.ipdnaeip.wizardrynextgeneration.item.ItemMovementWandUpgrade;
import com.ipdnaeip.wizardrynextgeneration.network.c2s.C2SPacketMultijump;
import com.ipdnaeip.wizardrynextgeneration.network.c2s.C2SPacketSolarWinds;
import com.ipdnaeip.wizardrynextgeneration.registry.*;
import com.ipdnaeip.wizardrynextgeneration.spell.SolarWinds;
import electroblob.wizardry.Wizardry;
import electroblob.wizardry.item.ISpellCastingItem;
import electroblob.wizardry.item.ItemArtefact;
import electroblob.wizardry.util.WandHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MovementInput;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber({Side.CLIENT})
public class WNGClientEvents {

    public static final float HASHASHIN_SNEAK_MULTIPLIER = 2f;

    private static boolean wasJumping = false;
    private static boolean wasJumpRefiredInAir = false;
    private static int jumpsInAir = 0;

    private WNGClientEvents() {}

    @SubscribeEvent
    public static void onInputUpdateEvent(InputUpdateEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        ItemStack stack = player.getActiveItemStack();
        MovementInput input = event.getMovementInput();
        Item item = stack.getItem();
        //Do I need to check this for syncing issues?
        if (player.isHandActive()) {
            int level;
            if (item instanceof ISpellCastingItem) {
                level = WandHelper.getUpgradeLevel(player.getActiveItemStack(), WNGItems.UPGRADE_MOVEMENT);
                if (level > 0) {
                    player.setSprinting(false);
                    input.moveStrafe *= 1F + (level * ItemMovementWandUpgrade.MOVEMENT_PER_LEVEL);
                    input.moveForward *= 1F + (level * ItemMovementWandUpgrade.MOVEMENT_PER_LEVEL);
                }
            } else if (item instanceof ItemBow) {
                level = EnchantmentHelper.getEnchantmentLevel(WNGEnchantments.RANGER, stack);
                if (level > 0) {
                    player.setSprinting(false);
                    input.moveStrafe *= 1F + (level * EnchantmentRanger.MOVEMENT_SPEED_PER_LEVEL);
                    input.moveForward *= 1F + (level * EnchantmentRanger.MOVEMENT_SPEED_PER_LEVEL);
                }
            } else if (item instanceof ItemShield) {
                level = EnchantmentHelper.getEnchantmentLevel(WNGEnchantments.PHALANX, stack);
                if (level > 0) {
                    player.setSprinting(false);
                    input.moveStrafe *= 1F + (level * EnchantmentPhalanx.MOVEMENT_SPEED_PER_LEVEL);
                    input.moveForward *= 1F + (level * EnchantmentPhalanx.MOVEMENT_SPEED_PER_LEVEL);
                }
            }
        }
        if (ItemArtefact.isArtefactActive(player, WNGItems.BODY_HASHASHIN) && ItemArtefact.isArtefactActive(player, WNGItems.HEAD_HASHASHIN) && player.isSneaking()) {
            input.moveForward *= HASHASHIN_SNEAK_MULTIPLIER;
            input.moveStrafe *= HASHASHIN_SNEAK_MULTIPLIER;
        }
        //If the player was not jumping and is now jumping and in the air, it has been refired
        if (!wasJumping && input.jump && !player.onGround) {
            wasJumpRefiredInAir = true;
            //Inject jump logic here
            if (!player.isRiding()) {
                PotionEffect potionEffect = player.getActivePotionEffect(WNGPotions.ACROBATICS);
                if (potionEffect != null && jumpsInAir < potionEffect.getAmplifier() + 1) {
                    player.setSprinting(false);
                    player.jump();
                    player.fallDistance = 0;
                    //Reset fall distance to sync the fall particle and sound effect as they are server side
                    IMessage msg = new C2SPacketMultijump.Message();
                    WNGPackets.net.sendToServer(msg);
                }
            }
            jumpsInAir++;
        }
        //Reset when the player touches the ground
        else if (player.onGround) {
            wasJumpRefiredInAir = false;
            jumpsInAir = 0;
        }
        if (input.jump && wasJumpRefiredInAir) {
            if (!player.isRiding()) {
                PotionEffect potionEffect = player.getActivePotionEffect(WNGPotions.SOLAR_WINDS);
                if (potionEffect != null) {
                    double multiplier = (potionEffect.getAmplifier() * 0.5) + 1;
                    double prevMotionY = player.motionY;
/*                    if (player.motionY < 0.5 * multiplier) {
                        player.motionY += 0.1 * multiplier;
                    }*/
                    float upwardVelocity = WNGSpells.SOLAR_WINDS.getProperty(SolarWinds.SPEED).floatValue();
                    float upwardAcceleration = WNGSpells.SOLAR_WINDS.getProperty(SolarWinds.ACCELERATION).floatValue();
                    player.motionY = Math.min(player.motionY + upwardAcceleration, upwardVelocity);
                    //It's very annoying that fall damage is calculated by distance and not velocity. I think this should work
                    if (!Wizardry.settings.replaceVanillaFallDamage) {
                        if (player.motionY < 0 && player.motionY > prevMotionY) {
                            player.fallDistance *= (float)(player.motionY / prevMotionY);
                        } else if (player.motionY >= 0) {
                            player.fallDistance = 0;
                        }
                    }
                    //Modify fall distance and play sound
                    IMessage msg = new C2SPacketSolarWinds.Message(player.fallDistance);
                    WNGPackets.net.sendToServer(msg);
                    if (Minecraft.getMinecraft().gameSettings.keyBindSprint.isKeyDown() && ItemArtefact.isArtefactActive(player, WNGItems.HEAD_RA)) {
                        //Maybe multiply velocity instead?
                        player.jumpMovementFactor = 0.05F;
                    }
                }
            }
        }
        //This should be at the end
        wasJumping = input.jump;
    }

}
