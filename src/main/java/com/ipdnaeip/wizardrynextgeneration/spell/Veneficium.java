package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGSpells;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.spell.SpellBuff;
import electroblob.wizardry.util.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Veneficium extends SpellBarrage {

    public Veneficium() {
        super(WizardryNextGeneration.MODID, "veneficium", SpellActions.POINT, false);
        this.soundValues(1F, 0.7F, 0.1F);
        this.addProperties(EFFECT_DURATION);
    }

    protected void barrageEffect(World world, EntityLivingBase target, EntityLivingBase caster, int ticksInUse, SpellModifiers modifiers) {
        int duration = (int)(WNGSpells.VENEFICIUM.getProperty(EFFECT_DURATION).floatValue() * modifiers.get(WizardryItems.duration_upgrade));
        target.addPotionEffect(new PotionEffect(WNGPotions.VENEFICIUM, duration, SpellBuff.getStandardBonusAmplifier(modifiers.get(SpellModifiers.POTENCY))));
    }

    @Override
    protected void barrageParticles(World world, Vec3d hit, EntityLivingBase caster, int ticksInUse, SpellModifiers modifiers) {
        for (int i = 0; (float) i < 60.0F * this.getProperty(EFFECT_RADIUS).floatValue(); ++i) {
            ParticleBuilder.create(ParticleBuilder.Type.CLOUD, world.rand, hit.x, hit.y, hit.z, (2.0F * modifiers.get(WizardryItems.blast_upgrade)), false).clr(0, 100, 0).fade(0, 170, 0).time(10 + world.rand.nextInt(20)).shaded(true).spawn(world);
        }
    }

    @Override
    protected void spawnParticle(World world, double x, double y, double z, double vx, double vy, double vz) {
        ParticleBuilder.create(ParticleBuilder.Type.DARK_MAGIC).pos(x, y, z).clr(0, 170, 0).time(20 + world.rand.nextInt(20)).spawn(world);
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.SPELL_BOOK_WNG || item == WNGItems.SCROLL_WNG;
    }
}