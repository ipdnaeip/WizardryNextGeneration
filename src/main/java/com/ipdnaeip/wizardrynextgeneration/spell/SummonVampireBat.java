package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.entity.living.EntityVampireBatMinion;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import electroblob.wizardry.spell.SpellMinion;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

public class SummonVampireBat extends SpellMinion<EntityVampireBatMinion> {

    public SummonVampireBat() {
        super(WizardryNextGeneration.MODID, "summon_vampire_bat", EntityVampireBatMinion::new);
    }

    @Override
    protected void addMinionExtras(EntityVampireBatMinion minion, BlockPos pos, @Nullable EntityLivingBase caster, SpellModifiers modifiers, int alreadySpawned) {
        minion.multiplier = modifiers.get(POTENCY_ATTRIBUTE_MODIFIER);
        System.out.println(modifiers.get(POTENCY_ATTRIBUTE_MODIFIER));
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.spell_book_wng || item == WNGItems.scroll_wng;
    }
}
