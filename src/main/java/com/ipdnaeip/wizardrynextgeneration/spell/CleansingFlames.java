package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.spell.SpellBuff;
import net.minecraft.item.Item;

public class CleansingFlames extends SpellBuff {

    public CleansingFlames() {
        super(WizardryNextGeneration.MODID, "cleansing_flames", 1f, 0.784f, 0.235f, () -> WNGPotions.cleansing_flames);
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.spell_book_wng || item == WNGItems.scroll_wng;
    }
}

