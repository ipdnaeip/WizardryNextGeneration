package com.ipdnaeip.wizardrynextgeneration.potion;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.accessor.AccessorEntityLivingBase;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.potion.PotionMagicEffect;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;

@Mod.EventBusSubscriber
public class PotionBleed extends PotionMagicEffect {

    public PotionBleed() {
        super(true, 0xAA0000, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/bleed.png"));
    }

    public static boolean canBleed(EntityLivingBase entity) {
        if (WizardryNextGeneration.settings.bleedEffectWhitelist.contains(EntityList.getKey(entity.getClass()))) {
            return true;
        } else return !entity.isEntityUndead() && !(entity instanceof EntityGolem) && !WizardryNextGeneration.settings.bleedEffectBlacklist.contains(EntityList.getKey(entity.getClass()));
	}

    @SubscribeEvent
    public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.isPotionActive(WNGPotions.BLEED)) {
            if (entity.ticksExisted % (Math.max(30 - (entity.getActivePotionEffect(WNGPotions.BLEED).getAmplifier() * 2), 10)) == 0) {
                entity.attackEntityFrom(DamageSource.WITHER, 1F);
            }
        }
    }

    @SubscribeEvent
    public static void onPotionApplicableEvent(PotionEvent.PotionApplicableEvent event) {
        if (event.getPotionEffect().getPotion() == WNGPotions.BLEED) {
            if (event.getEntityLiving().isEntityUndead() || event.getEntityLiving() instanceof EntityGolem || Arrays.asList(WizardryNextGeneration.settings.bleedEffectBlacklist).contains(EntityList.getKey(event.getEntityLiving().getClass())) && !Arrays.asList(WizardryNextGeneration.settings.bleedEffectWhitelist).contains(EntityList.getKey(event.getEntityLiving().getClass()))) {
                event.setResult(Event.Result.DENY);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHealEvent(LivingHealEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.isPotionActive(WNGPotions.BLEED) && !((AccessorEntityLivingBase)entity).wizardrynextgeneration$isNaturalHeal()) {
            entity.removePotionEffect(WNGPotions.BLEED);
        }
        ((AccessorEntityLivingBase)entity).wizardrynextgeneration$setNaturalHeal(false);
    }

}