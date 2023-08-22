package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
        import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
        import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
        import electroblob.wizardry.spell.SpellBuff;
        import net.minecraft.item.Item;

public class Preparation extends SpellBuff {

    public Preparation() {
        super(WizardryNextGeneration.MODID, "preparation", 0.745f, 0.745f, 0.51f, () -> WNGPotions.preparation);
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.spell_book_wng || item == WNGItems.scroll_wng;
    }
}

