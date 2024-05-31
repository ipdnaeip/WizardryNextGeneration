package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.entity.living.EntityRighteousDefenderMinion;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import electroblob.wizardry.spell.SpellMinion;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.IMob;
import net.minecraft.item.Item;

public class SummonRighteousDefender extends SpellMinion<EntityRighteousDefenderMinion> {

    public SummonRighteousDefender() {
        super(WizardryNextGeneration.MODID, "summon_righteous_defender", EntityRighteousDefenderMinion::new);
    }

    @Override
    public boolean canBeCastBy(EntityLiving npc, boolean override) {
        return !(npc instanceof IMob);
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.spell_book_wng || item == WNGItems.scroll_wng;
    }
}
