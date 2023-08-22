package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.spell.SpellBuff;
import net.minecraft.item.Item;

public class SolarSentinel extends SpellBuff {

    public SolarSentinel() {
        super(WizardryNextGeneration.MODID, "solar_sentinel", 1f, 0.706f, 0.196f, () -> WNGPotions.solar_winds, () -> WNGPotions.cleansing_flames);
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.spell_book_wng || item == WNGItems.scroll_wng;
    }
}


