package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.entity.living.EntityWebspitterMinion;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import electroblob.wizardry.spell.SpellMinion;
import net.minecraft.item.Item;

public class SummonWebspitter extends SpellMinion<EntityWebspitterMinion> {

    public SummonWebspitter() {
        super(WizardryNextGeneration.MODID, "summon_webspitter", EntityWebspitterMinion::new);
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.SPELL_BOOK_WNG || item == WNGItems.SCROLL_WNG;
    }
}
