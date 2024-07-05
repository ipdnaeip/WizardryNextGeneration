package com.ipdnaeip.wizardrynextgeneration.potion;

import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.constants.Element;
import electroblob.wizardry.event.SpellCastEvent;
import electroblob.wizardry.potion.PotionMagicEffect;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionEmpowerment extends PotionMagicEffect {

    public static final float EMPOWERMENT_INCREASE = 0.30f;

    public PotionEmpowerment(int liquidColor, ResourceLocation texture) {
        super(false, liquidColor, texture);
    }

    @SubscribeEvent
    public static void onSpellCastPreEvent(SpellCastEvent.Pre event) {
        EntityLivingBase caster = event.getCaster();
        if (caster != null) {
            SpellModifiers modifiers = event.getModifiers();
            Element element = event.getSpell().getElement();
            if (caster.isPotionActive(WNGPotions.EMPOWERMENT)) {
                float potency = 1.0F + EMPOWERMENT_INCREASE * (float) (caster.getActivePotionEffect(WNGPotions.EMPOWERMENT).getAmplifier() + 1);
                modifiers.set(SpellModifiers.POTENCY, modifiers.get(SpellModifiers.POTENCY) * potency, true);
            }
            if (caster.isPotionActive(WNGPotions.EMPOWERMENT_EARTH) && element == Element.EARTH) {
                float potency = 1.0F + EMPOWERMENT_INCREASE * (float) (caster.getActivePotionEffect(WNGPotions.EMPOWERMENT_EARTH).getAmplifier() + 1);
                modifiers.set(SpellModifiers.POTENCY, modifiers.get(SpellModifiers.POTENCY) * potency, true);
            }
            if (caster.isPotionActive(WNGPotions.EMPOWERMENT_FIRE) && element == Element.FIRE) {
                float potency = 1.0F + EMPOWERMENT_INCREASE * (float) (caster.getActivePotionEffect(WNGPotions.EMPOWERMENT_FIRE).getAmplifier() + 1);
                modifiers.set(SpellModifiers.POTENCY, modifiers.get(SpellModifiers.POTENCY) * potency, true);
            }
            if (caster.isPotionActive(WNGPotions.EMPOWERMENT_ICE) && element == Element.ICE) {
                float potency = 1.0F + EMPOWERMENT_INCREASE * (float) (caster.getActivePotionEffect(WNGPotions.EMPOWERMENT_ICE).getAmplifier() + 1);
                modifiers.set(SpellModifiers.POTENCY, modifiers.get(SpellModifiers.POTENCY) * potency, true);
            }
            if (caster.isPotionActive(WNGPotions.EMPOWERMENT_HEALING) && element == Element.HEALING) {
                float potency = 1.0F + EMPOWERMENT_INCREASE * (float) (caster.getActivePotionEffect(WNGPotions.EMPOWERMENT_HEALING).getAmplifier() + 1);
                modifiers.set(SpellModifiers.POTENCY, modifiers.get(SpellModifiers.POTENCY) * potency, true);
            }
            if (caster.isPotionActive(WNGPotions.EMPOWERMENT_LIGHTNING) && element == Element.LIGHTNING) {
                float potency = 1.0F + EMPOWERMENT_INCREASE * (float) (caster.getActivePotionEffect(WNGPotions.EMPOWERMENT_LIGHTNING).getAmplifier() + 1);
                modifiers.set(SpellModifiers.POTENCY, modifiers.get(SpellModifiers.POTENCY) * potency, true);
            }
            if (caster.isPotionActive(WNGPotions.EMPOWERMENT_NECROMANCY) && element == Element.NECROMANCY) {
                float potency = 1.0F + EMPOWERMENT_INCREASE * (float) (caster.getActivePotionEffect(WNGPotions.EMPOWERMENT_NECROMANCY).getAmplifier() + 1);
                modifiers.set(SpellModifiers.POTENCY, modifiers.get(SpellModifiers.POTENCY) * potency, true);
            }
            if (caster.isPotionActive(WNGPotions.EMPOWERMENT_SORCERY) && element == Element.SORCERY) {
                float potency = 1.0F + EMPOWERMENT_INCREASE * (float) (caster.getActivePotionEffect(WNGPotions.EMPOWERMENT_SORCERY).getAmplifier() + 1);
                modifiers.set(SpellModifiers.POTENCY, modifiers.get(SpellModifiers.POTENCY) * potency, true);
            }
        }
    }

}
