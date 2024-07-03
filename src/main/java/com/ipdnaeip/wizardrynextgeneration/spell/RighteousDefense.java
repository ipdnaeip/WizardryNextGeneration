package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
        import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
        import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
        import electroblob.wizardry.spell.SpellBuff;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.IMob;
import net.minecraft.item.Item;

public class RighteousDefense extends SpellBuff {

    public RighteousDefense() {
        super(WizardryNextGeneration.MODID, "righteous_defense", 1f, 1f, 0.3f, () -> WNGPotions.taunt);
    }

    @Override
    public boolean canBeCastBy(EntityLiving npc, boolean override) {
        return !(npc instanceof IMob);
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.SPELL_BOOK_WNG || item == WNGItems.SCROLL_WNG;
    }
}


