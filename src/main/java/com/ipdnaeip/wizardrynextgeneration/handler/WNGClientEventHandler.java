package com.ipdnaeip.wizardrynextgeneration.handler;

import com.ipdnaeip.wizardrynextgeneration.enchantment.EnchantmentRanger;
import com.ipdnaeip.wizardrynextgeneration.item.ItemNewArtefact;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGEnchantments;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import electroblob.wizardry.item.ItemArtefact;
import electroblob.wizardry.item.ItemWand;
import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.registry.WizardryPotions;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovementInput;
import net.minecraftforge.client.event.InputUpdateEvent;
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
        int level = EnchantmentHelper.getEnchantmentLevel(WNGEnchantments.ranger, stack);
        if (player.isHandActive() && stack.getItem() instanceof ItemBow) {
           player.setSprinting(false);
            MovementInput var10000 = event.getMovementInput();
            var10000.moveStrafe *= 1F + (0.75F * level);
            var10000 = event.getMovementInput();
            var10000.moveForward *= 1F + (0.75F * level);
        }
        level = EnchantmentHelper.getEnchantmentLevel(WNGEnchantments.phalanx, stack);
        if (player.isHandActive() && stack.getItem() instanceof ItemShield) {
            player.setSprinting(false);
            MovementInput var10000 = event.getMovementInput();
            var10000.moveStrafe *= 1F + (0.75F * level);
            var10000 = event.getMovementInput();
            var10000.moveForward *= 1F + (0.75F * level);
        }
        if (ItemNewArtefact.isNewArtefactActive(player, WNGItems.body_hashashin) && ItemNewArtefact.isNewArtefactActive(player, WNGItems.head_hashashin) && player.isSneaking()) {
            input.moveForward *= 1.5F;
            input.moveStrafe *= 1.5F;
        }

    }

}
