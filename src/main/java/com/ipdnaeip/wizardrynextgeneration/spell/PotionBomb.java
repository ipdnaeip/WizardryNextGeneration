package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.entity.projectile.EntityConjuredPotion;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import electroblob.wizardry.spell.SpellProjectile;
import net.minecraft.item.Item;

public class PotionBomb extends SpellProjectile<EntityConjuredPotion> {

    public PotionBomb() {
        super(WizardryNextGeneration.MODID, "potion_bomb", EntityConjuredPotion::new);
        this.addProperties(BLAST_RADIUS, EFFECT_DURATION);
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.SPELL_BOOK_WNG || item == WNGItems.SCROLL_WNG;
    }

}
