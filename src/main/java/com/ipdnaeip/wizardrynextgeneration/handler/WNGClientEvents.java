package com.ipdnaeip.wizardrynextgeneration.handler;

import com.ipdnaeip.wizardrynextgeneration.enchantment.EnchantmentPhalanx;
import com.ipdnaeip.wizardrynextgeneration.enchantment.EnchantmentRanger;
import com.ipdnaeip.wizardrynextgeneration.item.ItemMovementWandUpgrade;
import com.ipdnaeip.wizardrynextgeneration.network.c2s.C2SPacketMultijump;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGEnchantments;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPackets;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.item.ISpellCastingItem;
import electroblob.wizardry.item.ItemArtefact;
import electroblob.wizardry.util.WandHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
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
                    input.moveStrafe *= 1F + (level * ItemMovementWandUpgrade.MOVEMENT_PER_LEVEL);
                    input.moveForward *= 1F + (level * ItemMovementWandUpgrade.MOVEMENT_PER_LEVEL);
                }
            } else if (item instanceof ItemBow) {
                level = EnchantmentHelper.getEnchantmentLevel(WNGEnchantments.RANGER, stack);
                player.setSprinting(false);
                input.moveStrafe *= 1F + (level * EnchantmentRanger.MOVEMENT_SPEED_PER_LEVEL);
                input.moveForward *= 1F + (level * EnchantmentRanger.MOVEMENT_SPEED_PER_LEVEL);
            } else if (item instanceof ItemShield) {
                level = EnchantmentHelper.getEnchantmentLevel(WNGEnchantments.PHALANX, stack);
                player.setSprinting(false);
                input.moveStrafe *= 1F + (level * EnchantmentPhalanx.MOVEMENT_SPEED_PER_LEVEL);
                input.moveForward *= 1F + (level * EnchantmentPhalanx.MOVEMENT_SPEED_PER_LEVEL);
            }
        }
        if (ItemArtefact.isArtefactActive(player, WNGItems.BODY_HASHASHIN) && ItemArtefact.isArtefactActive(player, WNGItems.HEAD_HASHASHIN) && player.isSneaking()) {
            input.moveForward *= HASHASHIN_SNEAK_MULTIPLIER;
            input.moveStrafe *= HASHASHIN_SNEAK_MULTIPLIER;
        }
        //If the player was not jumping and is now jumping and in the air, it has been refired
        if (!wasJumping && event.getMovementInput().jump && !player.onGround) {
            wasJumpRefiredInAir = true;
            //Inject jump logic here
            if (player.isPotionActive(MobEffects.SATURATION) && jumpsInAir < 2) {
                player.setSprinting(false);
                player.jump();
                player.fallDistance = 0;
                IMessage msg = new C2SPacketMultijump.Message(player.fallDistance);
                WNGPackets.net.sendToServer(msg);
            }
            jumpsInAir++;
        }
        //Reset when the player touches the ground
        if (player.onGround) {
            wasJumpRefiredInAir = false;
            jumpsInAir = 0;
        }
        if (input.jump && wasJumpRefiredInAir) {
            if (player.isPotionActive(WNGPotions.SOLAR_WINDS)) {

            }
        }
        wasJumping = input.jump;
    }

}
