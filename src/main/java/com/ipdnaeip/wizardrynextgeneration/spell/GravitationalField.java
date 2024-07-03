package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.entity.construct.EntityGravitationalField;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import electroblob.wizardry.spell.SpellConstructRanged;
import net.minecraft.item.Item;

public class GravitationalField extends SpellConstructRanged<EntityGravitationalField> {

    public GravitationalField() {
        super(WizardryNextGeneration.MODID, "gravitational_field", EntityGravitationalField::new, false);
        this.soundValues(1F, 1F, 0F);
        this.addProperties(EFFECT_RADIUS);
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.SPELL_BOOK_WNG || item == WNGItems.SCROLL_WNG;
    }
}

