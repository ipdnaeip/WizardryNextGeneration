package com.ipdnaeip.wizardrynextgeneration.potion;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.potion.PotionMagicEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionRally extends PotionMagicEffect {

    public static final float DAMAGE_INCREASE = 0.1f;
    public static final float DAMAGE_DECREASE = 0.2f;
    public static final int HEAD_RALLY_INCREASE = 1;

    public PotionRally() {
        super(true, 0x0f000f, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/rally.png"));
    }

    @SubscribeEvent
    public static void onLivingHurtEvent(LivingHurtEvent event) {
        //Damage increases at a decreasing rate to a maximum of 2x damage
        if (event.getSource().getTrueSource() instanceof EntityLivingBase && ((EntityLivingBase)event.getSource().getTrueSource()).isPotionActive(WNGPotions.RALLY)) {
            event.setAmount(event.getAmount() * (2f - (float)Math.pow(1 - DAMAGE_INCREASE, ((EntityLivingBase) event.getSource().getTrueSource()).getActivePotionEffect(WNGPotions.RALLY).getAmplifier() + 1)));
        }
        //Damage decreases at a decreasing rate to a minimum of 0.5x damage
        if (event.getEntityLiving().isPotionActive(WNGPotions.RALLY) && event.getSource() != DamageSource.OUT_OF_WORLD) {
            event.setAmount(event.getAmount() * ((1f + (float)Math.pow(1 - DAMAGE_DECREASE, event.getEntityLiving().getActivePotionEffect(WNGPotions.RALLY).getAmplifier() + 1)) / 2f));
        }
    }

}
