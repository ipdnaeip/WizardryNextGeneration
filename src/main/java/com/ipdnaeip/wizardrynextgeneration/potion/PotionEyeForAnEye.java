package com.ipdnaeip.wizardrynextgeneration.potion;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.potion.PotionMagicEffect;
import electroblob.wizardry.registry.WizardrySounds;
import electroblob.wizardry.util.MagicDamage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionEyeForAnEye extends PotionMagicEffect {

    public PotionEyeForAnEye() {
        super(false, 0xFFD651, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/eye_for_an_eye.png"));
    }

    @SubscribeEvent
    public static void onLivingDamageEvent(LivingDamageEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.isPotionActive(WNGPotions.EYE_FOR_AN_EYE) && event.getSource().getTrueSource() != null && !(event.getSource() instanceof MagicDamage && ((MagicDamage)event.getSource()).isRetaliatory())) {
            Entity attacker = event.getSource().getTrueSource();
            attacker.attackEntityFrom(MagicDamage.causeDirectMagicDamage(entity, MagicDamage.DamageType.RADIANT, true), (0.5f + 0.25f * entity.getActivePotionEffect(WNGPotions.EYE_FOR_AN_EYE).getAmplifier()) * event.getAmount());
            entity.world.playSound(null, attacker.getPosition(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, WizardrySounds.SPELLS, 1f, 0.9f + attacker.world.rand.nextFloat() * 0.2f);
            entity.removePotionEffect(WNGPotions.EYE_FOR_AN_EYE);
        }
    }

}
