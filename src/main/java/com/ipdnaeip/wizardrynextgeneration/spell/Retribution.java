package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.spell.SpellBuff;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;

public class Retribution extends SpellBuff {

    public Retribution() {
        super(WizardryNextGeneration.MODID, "retribution", 1f, 0.84f, 0.32f, () -> WNGPotions.retribution);
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.spell_book_wng || item == WNGItems.scroll_wng;
    }
}
