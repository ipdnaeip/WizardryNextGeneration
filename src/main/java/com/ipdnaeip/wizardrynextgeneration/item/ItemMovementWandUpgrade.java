package com.ipdnaeip.wizardrynextgeneration.item;

import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import electroblob.wizardry.item.ItemWand;
import electroblob.wizardry.util.WandHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovementInput;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber({Side.CLIENT})
public class ItemMovementWandUpgrade extends ItemWNGWandUpgrade {

    @SubscribeEvent
    public static void onInputUpdateEvent(InputUpdateEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        ItemStack stack = player.getActiveItemStack();
        MovementInput input = event.getMovementInput();
        if (player.isHandActive() && stack.getItem() instanceof ItemWand && WandHelper.getUpgradeLevel(player.getActiveItemStack(), WNGItems.UPGRADE_MOVEMENT) > 0) {
            player.setSprinting(false);
            input.moveStrafe *= 1F + WandHelper.getUpgradeLevel(player.getActiveItemStack(), WNGItems.UPGRADE_MOVEMENT);
            input.moveForward *= 1F + WandHelper.getUpgradeLevel(player.getActiveItemStack(), WNGItems.UPGRADE_MOVEMENT);
        }
    }

}
