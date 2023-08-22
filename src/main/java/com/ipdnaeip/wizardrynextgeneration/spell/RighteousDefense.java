package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
        import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
        import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
        import electroblob.wizardry.spell.SpellBuff;
        import net.minecraft.item.Item;

public class RighteousDefense extends SpellBuff {

    public RighteousDefense() {
        super(WizardryNextGeneration.MODID, "righteous_defense", 1f, 1f, 0.3f, () -> WNGPotions.taunt);
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.spell_book_wng || item == WNGItems.scroll_wng;
    }
}


