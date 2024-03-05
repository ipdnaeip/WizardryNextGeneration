package com.ipdnaeip.wizardrynextgeneration.potion;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.potion.PotionMagicEffect;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
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
        this.setPotionName("potion." + WizardryNextGeneration.MODID + ":bleed");
    }

    @SubscribeEvent
    public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.isPotionActive(WNGPotions.bleed)) {
            if (entity.ticksExisted % (Math.max(40 - (entity.getActivePotionEffect(WNGPotions.bleed).getAmplifier() * 5), 10)) == 0) {
                entity.attackEntityFrom(DamageSource.WITHER, 1F);
            }
        }
    }

    @SubscribeEvent
    public static void onPotionApplicableEvent(PotionEvent.PotionApplicableEvent event) {
        if (event.getPotionEffect().getPotion() == WNGPotions.bleed) {
            if (event.getEntityLiving().isEntityUndead() || event.getEntityLiving() instanceof EntityGolem || Arrays.asList(WizardryNextGeneration.settings.bleedEffectBlacklist).contains(EntityList.getKey(event.getEntityLiving().getClass())) && !Arrays.asList(WizardryNextGeneration.settings.bleedEffectWhitelist).contains(EntityList.getKey(event.getEntityLiving().getClass()))) {
                event.setResult(Event.Result.DENY);
            }
        }
    }

    @SubscribeEvent
    public static void onPotionAddedEvent(PotionEvent.PotionAddedEvent event) {
        if (event.getOldPotionEffect() != null && event.getPotionEffect().getPotion() == WNGPotions.bleed && event.getOldPotionEffect().getPotion() == WNGPotions.bleed) {
            event.getEntityLiving().removePotionEffect(WNGPotions.bleed);
            event.getEntityLiving().addPotionEffect(new PotionEffect(WNGPotions.bleed, event.getPotionEffect().getDuration(), event.getOldPotionEffect().getAmplifier() + event.getPotionEffect().getAmplifier() + 1));
        }
    }

    @SubscribeEvent
    public static void onLivingHealEvent(LivingHealEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.isPotionActive(WNGPotions.bleed) && event.getAmount() > 0.5f) {
            entity.removePotionEffect(WNGPotions.bleed);
        }
    }

}