package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.spell.SpellBuff;
import net.minecraft.item.Item;

public class SolarSentinel extends SpellBuff {

    public SolarSentinel() {
        super(WizardryNextGeneration.MODID, "solar_sentinel", 1f, 0.706f, 0.196f, () -> WNGPotions.SOLAR_WINDS, () -> WNGPotions.CLEANSING_FLAMES);
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.SPELL_BOOK_WNG || item == WNGItems.SCROLL_WNG;
    }
}


