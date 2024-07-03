package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.spell.SpellAreaEffect;
import electroblob.wizardry.spell.SpellBuff;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class Inspire extends SpellAreaEffect  {

    public Inspire() {
        super(WizardryNextGeneration.MODID, "inspire", SpellActions.POINT_DOWN, false);
        this.soundValues(1F, 1F, 0.1F);
        this.targetAllies(true);
        this.alwaysSucceed(true);
        this.addProperties(EFFECT_DURATION, EFFECT_STRENGTH);
    }

    @Override
    protected boolean affectEntity(World world, Vec3d vec3d, @Nullable EntityLivingBase caster, EntityLivingBase target, int i, int ticksInUse, SpellModifiers modifiers) {
        if (target instanceof IEntityOwnable) {
            target.addPotionEffect(new PotionEffect(WNGPotions.rally, this.getProperty(EFFECT_DURATION).intValue(), this.getProperty(EFFECT_STRENGTH).intValue() + SpellBuff.getStandardBonusAmplifier(modifiers.get(SpellModifiers.POTENCY))));
        }
        return true;
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.SPELL_BOOK_WNG || item == WNGItems.SCROLL_WNG;
    }
}
