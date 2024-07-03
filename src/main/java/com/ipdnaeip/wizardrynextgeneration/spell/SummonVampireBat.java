package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.entity.living.EntityVampireBatMinion;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import electroblob.wizardry.spell.SpellMinion;
import net.minecraft.item.Item;

public class SummonVampireBat extends SpellMinion<EntityVampireBatMinion> {

    public SummonVampireBat() {
        super(WizardryNextGeneration.MODID, "summon_vampire_bat", EntityVampireBatMinion::new);
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.SPELL_BOOK_WNG || item == WNGItems.SCROLL_WNG;
    }
}
