package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.spell.SpellBuff;
import electroblob.wizardry.util.ParticleBuilder;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class Betrayal extends SpellBarrage {

    public Betrayal() {
        super(WizardryNextGeneration.MODID, "betrayal", SpellActions.POINT, false);
        this.soundValues(1F, 0.1F, 0.1F);
        this.addProperties(EFFECT_RADIUS, EFFECT_STRENGTH, EFFECT_DURATION);
    }

    @Override
    protected void barrageEffect(World world, EntityLivingBase target, EntityLivingBase caster, int ticksInUse, SpellModifiers modifiers) {
        if (target instanceof EntityLiving && target instanceof IMob) {
            int bonusAmplifier = SpellBuff.getStandardBonusAmplifier(modifiers.get(SpellModifiers.POTENCY));
            target.addPotionEffect(new PotionEffect(WNGPotions.betrayal, this.getProperty(EFFECT_DURATION).intValue(), (int)this.getProperty(EFFECT_STRENGTH).floatValue() + bonusAmplifier));
        }
    }

    @Override
    protected void spawnParticle(World world, double x, double y, double z, double vx, double vy, double vz) {
        ParticleBuilder.create(ParticleBuilder.Type.SPARKLE).pos(x, y, z).vel(0.0, 0.0, 0.0).clr(0.9F, 0.1F, 0.0F).spawn(world);
        ParticleBuilder.create(ParticleBuilder.Type.DARK_MAGIC).pos(x, y, z).vel(0.0, 0.0, 0.0).clr(0.9F, 0.1F, 0.0F).spawn(world);
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
