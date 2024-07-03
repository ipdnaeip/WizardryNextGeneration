package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.entity.construct.EntityWhirlpool;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import electroblob.wizardry.spell.SpellConstructRanged;
import net.minecraft.item.Item;

public class Whirlpool extends SpellConstructRanged<EntityWhirlpool> {

    public Whirlpool() {
        super(WizardryNextGeneration.MODID, "whirlpool", EntityWhirlpool::new, false);
        this.soundValues(1F, 1F, 0F);
        this.addProperties(EFFECT_RADIUS, DAMAGE);
        this.floor(true);
        this.allowOverlap = true;
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.SPELL_BOOK_WNG || item == WNGItems.SCROLL_WNG;
    }
}

