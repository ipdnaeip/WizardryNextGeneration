package com.ipdnaeip.wizardrynextgeneration.potion;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.potion.PotionMagicEffect;
import electroblob.wizardry.registry.WizardrySounds;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionDivineShield extends PotionMagicEffect {

    public PotionDivineShield() {
        super(false, 0xFFD651, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/divine_shield.png"));
        this.setPotionName("potion." + WizardryNextGeneration.MODID + ":divine_shield");
    }

    @SubscribeEvent
    public static void onLivingDamageEvent(LivingDamageEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.isPotionActive(WNGPotions.divine_shield)) {
            event.setCanceled(true);
            entity.removePotionEffect(WNGPotions.divine_shield);
            entity.world.playSound(null, entity.posX, entity.posY, entity.posZ, SoundEvents.ITEM_SHIELD_BLOCK, SoundCategory.PLAYERS, 1.25F, 1.0F);
            entity.world.playSound(null, entity.posX, entity.posY, entity.posZ, WizardrySounds.ENTITY_SHIELD_DEFLECT, SoundCategory.PLAYERS, 1.25F, 1.0F);
        }
    }

}
