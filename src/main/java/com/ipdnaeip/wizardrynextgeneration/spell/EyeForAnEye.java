package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.spell.SpellBuff;
import net.minecraft.item.Item;

public class EyeForAnEye extends SpellBuff {

    public EyeForAnEye() {
        super(WizardryNextGeneration.MODID, "eye_for_an_eye", 1f, 0.84f, 0.32f, () -> WNGPotions.eye_for_an_eye);
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.SPELL_BOOK_WNG || item == WNGItems.SCROLL_WNG;
    }
}
