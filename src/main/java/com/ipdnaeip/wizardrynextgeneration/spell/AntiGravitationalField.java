package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.entity.construct.EntityAntiGravitationalField;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import electroblob.wizardry.spell.SpellConstructRanged;
import net.minecraft.item.Item;

public class AntiGravitationalField extends SpellConstructRanged<EntityAntiGravitationalField> {

    public AntiGravitationalField() {
        super(WizardryNextGeneration.MODID, "anti_gravitational_field", EntityAntiGravitationalField::new, false);
        this.soundValues(1F, 1F, 0F);
        this.addProperties(EFFECT_RADIUS);
        this.allowOverlap = true;
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.SPELL_BOOK_WNG || item == WNGItems.SCROLL_WNG;
    }
}

