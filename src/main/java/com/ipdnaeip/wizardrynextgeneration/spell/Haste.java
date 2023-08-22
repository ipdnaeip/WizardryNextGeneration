package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
        import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
        import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
        import electroblob.wizardry.registry.WizardryPotions;
        import electroblob.wizardry.spell.SpellBuff;
        import net.minecraft.init.MobEffects;
        import net.minecraft.item.Item;

public class Haste extends SpellBuff {

    public Haste() {
        super(WizardryNextGeneration.MODID, "haste", 0.333f, 1f, 0.333f, () -> MobEffects.HASTE);
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.spell_book_wng || item == WNGItems.scroll_wng;
    }
}
