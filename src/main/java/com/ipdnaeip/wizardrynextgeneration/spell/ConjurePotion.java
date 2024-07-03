package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.item.ItemConjuredPotion;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.spell.SpellConjuration;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ConjurePotion extends SpellConjuration {

    public ConjurePotion() {
        super(WizardryNextGeneration.MODID, "conjure_potion", WNGItems.CONJURED_POTION);
    }

    @Override
    protected void addItemExtras(EntityPlayer caster, ItemStack stack, SpellModifiers modifiers) {
        ItemConjuredPotion.setEffectDurationMultiplier(stack, modifiers.get(WizardryItems.duration_upgrade));
        ItemConjuredPotion.setBlastMultiplier(stack, modifiers.get(WizardryItems.blast_upgrade));
        ItemConjuredPotion.setLingering(stack, false);
        ItemConjuredPotion.setInstantEffect(stack, false);
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.SPELL_BOOK_WNG || item == WNGItems.SCROLL_WNG;
    }
}
