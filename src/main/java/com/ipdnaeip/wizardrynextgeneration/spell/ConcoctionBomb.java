package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.entity.projectile.EntityConjuredPotion;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import electroblob.wizardry.spell.SpellProjectile;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;

import javax.annotation.Nullable;

public class ConcoctionBomb extends SpellProjectile<EntityConjuredPotion> {

    public ConcoctionBomb() {
        super(WizardryNextGeneration.MODID, "concoction_bomb", EntityConjuredPotion::new);
        this.addProperties("blast_radius", "effect_duration");
    }

    @Override
    protected void addProjectileExtras(EntityConjuredPotion projectile, @Nullable EntityLivingBase caster, SpellModifiers modifiers) {
        projectile.hasInstantEffect = true;
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.spell_book_wng || item == WNGItems.scroll_wng;
    }

}
