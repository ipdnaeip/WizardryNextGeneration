package com.ipdnaeip.wizardrynextgeneration.potion;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.potion.PotionMagicEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionGravity extends PotionMagicEffect {

    public PotionGravity() {
        super(true, 0x55FF55, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/gravity.png"));
        this.setPotionName("potion." + WizardryNextGeneration.MODID + ":gravity");
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @SubscribeEvent
    public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.isPotionActive(WNGPotions.gravity)) {
            entity.motionY -= 0.025 * (2 << (entity.getActivePotionEffect(WNGPotions.gravity).getAmplifier()));
        }
    }

    @SubscribeEvent
    public static void onLivingFallEvent(LivingFallEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.isPotionActive(WNGPotions.gravity)) {
            event.setDamageMultiplier(2 << (entity.getActivePotionEffect(WNGPotions.gravity).getAmplifier()));
        }
    }

}
