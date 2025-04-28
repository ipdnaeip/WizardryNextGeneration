package com.ipdnaeip.wizardrynextgeneration.handler;

import com.ipdnaeip.wizardrynextgeneration.registry.WNGEnchantments;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import electroblob.wizardry.item.ItemArtefact;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovementInput;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber({Side.CLIENT})
public class WNGClientEventHandler {

    private WNGClientEventHandler() {}

    @SubscribeEvent
    public static void onInputUpdateEvent(InputUpdateEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        ItemStack stack = player.getActiveItemStack();
        MovementInput input = event.getMovementInput();
        int level = EnchantmentHelper.getEnchantmentLevel(WNGEnchantments.RANGER, stack);
        if (player.isHandActive() && stack.getItem() instanceof ItemBow) {
            player.setSprinting(false);
            input.moveStrafe *= 1F + (0.75f * level);
            input.moveForward *= 1F + (0.75f * level);
        }
        level = EnchantmentHelper.getEnchantmentLevel(WNGEnchantments.PHALANX, stack);
        if (player.isHandActive() && stack.getItem() instanceof ItemShield) {
            player.setSprinting(false);
            input.moveStrafe *= 1F + (0.75f * level);
            input.moveForward *= 1F + (0.75f * level);
        }
        if (ItemArtefact.isArtefactActive(player, WNGItems.BODY_HASHASHIN) && ItemArtefact.isArtefactActive(player, WNGItems.HEAD_HASHASHIN) && player.isSneaking()) {
            input.moveForward *= 2F;
            input.moveStrafe *= 2F;
        }
    }

}
