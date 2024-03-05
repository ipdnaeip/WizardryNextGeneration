package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.spell.SpellBuff;
import net.minecraft.item.Item;

public class LightningReflexes extends SpellBuff {

    public LightningReflexes() {
        super(WizardryNextGeneration.MODID, "lightning_reflexes", .6f, 1f, 0.75f, () -> WNGPotions.lightning_reflexes);
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.spell_book_wng || item == WNGItems.scroll_wng;
    }

}
