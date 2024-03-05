package com.ipdnaeip.wizardrynextgeneration.registry;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;

public class WNGConstants {

    private WNGConstants() {

    }

    //NBT Keys

    public static final String ANIMAL_ALLEGIANCE_CASTER = registerTag("animal_alliance_caster");
    public static final String ANIMAL_ATTACK_MULTIPLIER = registerTag("animal_attack_multiplier");
    public static final String CD_ARTEFACT_LAST_TIME_ACTIVATED = registerTag("cd_artefact_last_time_activated");
    public static final String AMULET_MOON_FULL_MOON = registerTag("amulet_moon_full_moon");
    public static final String LAST_SPELL_ELEMENT = registerTag("last_spell_element");

    public static String registerTag(String key) {
        return WizardryNextGeneration.MODID + "." + key;
    }

}
