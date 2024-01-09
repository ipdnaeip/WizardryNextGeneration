package com.ipdnaeip.wizardrynextgeneration.potion;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.potion.PotionMagicEffect;
import electroblob.wizardry.registry.WizardryPotions;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionPacify extends PotionMagicEffect {

    public PotionPacify() {
        super(false, 0xFFF7C1, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/pacify.png"));
        this.setPotionName("potion." + WizardryNextGeneration.MODID + ":pacify");
    }

    @SubscribeEvent
    public static void onLivingAttackEvent(LivingAttackEvent event) {
        if (event.getSource() != null && event.getSource().getTrueSource() instanceof EntityLivingBase && event.getEntityLiving().isPotionActive(WNGPotions.pacify)) {
            event.getEntityLiving().removePotionEffect(WNGPotions.pacify);
        }
    }

    @SubscribeEvent
    public static void onLivingSetAttackTargetEvent(LivingSetAttackTargetEvent event) {
        if (event.getEntityLiving().isPotionActive(WNGPotions.pacify) && event.getEntityLiving() instanceof EntityLiving && event.getTarget() != null) {
            ((EntityLiving)event.getEntityLiving()).setAttackTarget(null);
        }
    }

}
