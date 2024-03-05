package com.ipdnaeip.wizardrynextgeneration.potion;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.constants.Element;
import electroblob.wizardry.event.SpellCastEvent;
import electroblob.wizardry.potion.PotionMagicEffect;
import electroblob.wizardry.registry.Spells;
import electroblob.wizardry.registry.WizardryPotions;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionEmpowerment extends PotionMagicEffect {

    public Element element;

    public PotionEmpowerment(int liquidcolor, ResourceLocation texture) {
        super(false, liquidcolor, texture);
        this.element = element;
    }

    @SubscribeEvent
    public static void onSpellCastPreEvent(SpellCastEvent.Pre event) {
        if (event.getCaster() != null) {
            if (event.getCaster().isPotionActive(WNGPotions.empowerment)) {
                float potency = 1.0F + 0.30f * (float) (event.getCaster().getActivePotionEffect(WNGPotions.empowerment).getAmplifier() + 1);
                event.getModifiers().set("potency", event.getModifiers().get("potency") * potency, true);
            }
            if (event.getCaster().isPotionActive(WNGPotions.empowerment_earth) && event.getSpell().getElement() == Element.EARTH) {
                float potency = 1.0F + 0.30f * (float) (event.getCaster().getActivePotionEffect(WNGPotions.empowerment_earth).getAmplifier() + 1);
                event.getModifiers().set("potency", event.getModifiers().get("potency") * potency, true);
            }
            if (event.getCaster().isPotionActive(WNGPotions.empowerment_fire) && event.getSpell().getElement() == Element.FIRE) {
                float potency = 1.0F + 0.30f * (float) (event.getCaster().getActivePotionEffect(WNGPotions.empowerment_fire).getAmplifier() + 1);
                event.getModifiers().set("potency", event.getModifiers().get("potency") * potency, true);
            }
            if (event.getCaster().isPotionActive(WNGPotions.empowerment_ice) && event.getSpell().getElement() == Element.ICE) {
                float potency = 1.0F + 0.30f * (float) (event.getCaster().getActivePotionEffect(WNGPotions.empowerment_ice).getAmplifier() + 1);
                event.getModifiers().set("potency", event.getModifiers().get("potency") * potency, true);
            }
            if (event.getCaster().isPotionActive(WNGPotions.empowerment_healing) && event.getSpell().getElement() == Element.HEALING) {
                float potency = 1.0F + 0.30f * (float) (event.getCaster().getActivePotionEffect(WNGPotions.empowerment_healing).getAmplifier() + 1);
                event.getModifiers().set("potency", event.getModifiers().get("potency") * potency, true);
            }
            if (event.getCaster().isPotionActive(WNGPotions.empowerment_lightning) && event.getSpell().getElement() == Element.LIGHTNING) {
                float potency = 1.0F + 0.30f * (float) (event.getCaster().getActivePotionEffect(WNGPotions.empowerment_lightning).getAmplifier() + 1);
                event.getModifiers().set("potency", event.getModifiers().get("potency") * potency, true);
            }
            if (event.getCaster().isPotionActive(WNGPotions.empowerment_necromancy) && event.getSpell().getElement() == Element.NECROMANCY) {
                float potency = 1.0F + 0.30f * (float) (event.getCaster().getActivePotionEffect(WNGPotions.empowerment_necromancy).getAmplifier() + 1);
                event.getModifiers().set("potency", event.getModifiers().get("potency") * potency, true);
            }
            if (event.getCaster().isPotionActive(WNGPotions.empowerment_sorcery) && event.getSpell().getElement() == Element.SORCERY) {
                float potency = 1.0F + 0.30f * (float) (event.getCaster().getActivePotionEffect(WNGPotions.empowerment_sorcery).getAmplifier() + 1);
                event.getModifiers().set("potency", event.getModifiers().get("potency") * potency, true);
            }
        }
    }

}
