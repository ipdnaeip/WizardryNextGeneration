package com.ipdnaeip.wizardrynextgeneration.item;

import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import electroblob.wizardry.event.SpellCastEvent;
import electroblob.wizardry.item.ItemWand;
import electroblob.wizardry.util.SpellModifiers;
import electroblob.wizardry.util.WandHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ItemChargeupWandUpgrade extends ItemWNGWandUpgrade {

    @SubscribeEvent
    public static void onSpellCastPreEvent(SpellCastEvent.Pre event) {
        if (event.getCaster() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)event.getCaster();
            SpellModifiers modifiers = event.getModifiers();
            if (player.getHeldItemMainhand().getItem() instanceof ItemWand && WandHelper.getUpgradeLevel(player.getHeldItemMainhand(), WNGItems.upgrade_chargeup) > 0) {
                modifiers.set("chargeup", (1f - 0.25f * (float)(WandHelper.getUpgradeLevel(player.getHeldItemMainhand(), WNGItems.upgrade_chargeup))), false);
                System.out.println("m " + (1f - 0.25f * (float)(WandHelper.getUpgradeLevel(player.getActiveItemStack(), WNGItems.upgrade_chargeup))) + " " + (WandHelper.getUpgradeLevel(player.getActiveItemStack(), WNGItems.upgrade_chargeup)));
            }
            else if (player.getHeldItemOffhand().getItem() instanceof ItemWand && WandHelper.getUpgradeLevel(player.getHeldItemOffhand(), WNGItems.upgrade_chargeup) > 0) {
                modifiers.set("chargeup", (1f - 0.25f * (float)(WandHelper.getUpgradeLevel(player.getHeldItemOffhand(), WNGItems.upgrade_chargeup))), false);
            }
        }
    }

}
