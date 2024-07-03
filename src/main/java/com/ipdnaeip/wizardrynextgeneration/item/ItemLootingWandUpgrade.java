package com.ipdnaeip.wizardrynextgeneration.item;

import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import electroblob.wizardry.item.ItemWand;
import electroblob.wizardry.util.WandHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ItemLootingWandUpgrade extends ItemWNGWandUpgrade {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLootingLevelEvent(LootingLevelEvent event) {
        if (event.getDamageSource().getTrueSource() instanceof EntityLivingBase) {
            EntityLivingBase entity = (EntityLivingBase)event.getDamageSource().getTrueSource();
            if (entity.getHeldItemMainhand().getItem() instanceof ItemWand && WandHelper.getUpgradeLevel(entity.getHeldItemMainhand(), WNGItems.UPGRADE_LOOTING) > 0) {
                event.setLootingLevel(Math.max(event.getLootingLevel(), WandHelper.getUpgradeLevel(entity.getHeldItemMainhand(), WNGItems.UPGRADE_LOOTING)));
            }
            else if (entity.getHeldItemOffhand().getItem() instanceof ItemWand && WandHelper.getUpgradeLevel(entity.getHeldItemOffhand(), WNGItems.UPGRADE_LOOTING) > 0) {
                event.setLootingLevel(Math.max(event.getLootingLevel(), WandHelper.getUpgradeLevel(entity.getHeldItemOffhand(), WNGItems.UPGRADE_LOOTING)));
            }
        }
    }

}
