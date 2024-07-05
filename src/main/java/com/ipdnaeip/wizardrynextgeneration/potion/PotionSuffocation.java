package com.ipdnaeip.wizardrynextgeneration.potion;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.potion.PotionMagicEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionSuffocation extends PotionMagicEffect {

    public PotionSuffocation() {
        super(true, 0x5555FF, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/suffocation.png"));
    }

    @SubscribeEvent
    public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.isPotionActive(WNGPotions.SUFFOCATION)) {
            entity.setAir(0);
            if (entity.ticksExisted % 20 == 0) {
                entity.attackEntityFrom(DamageSource.DROWN, 2F + entity.getActivePotionEffect(WNGPotions.SUFFOCATION).getAmplifier());
            }
        }
    }

}
