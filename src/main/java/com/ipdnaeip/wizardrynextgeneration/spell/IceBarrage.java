package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGSpells;
import electroblob.wizardry.block.BlockStatue;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.registry.WizardryBlocks;
import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.registry.WizardryPotions;
import electroblob.wizardry.spell.SpellRay;
import electroblob.wizardry.util.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class IceBarrage extends SpellBarrage {

    public static final String ICE_DURATION = "ice_duration";
    public IceBarrage() {
        super(WizardryNextGeneration.MODID, "ice_barrage", SpellActions.POINT, false);
        this.soundValues(1.0F, 7.0F, 0.1F);
        this.addProperties(DAMAGE, EFFECT_DURATION, ICE_DURATION);
    }

    @Override
    protected void barrageEffect(World world, EntityLivingBase target, EntityLivingBase caster, int ticksInUse, SpellModifiers modifiers) {

        EntityUtils.attackEntityWithoutKnockback(target, MagicDamage.causeDirectMagicDamage(caster, MagicDamage.DamageType.FROST), getProperty(DAMAGE).floatValue() * modifiers.get(SpellModifiers.POTENCY));
        if (target instanceof EntityLiving && !world.isRemote && ((BlockStatue) WizardryBlocks.ice_statue).convertToStatue((EntityLiving) target, caster, (int) (this.getProperty("ice_duration").floatValue() * modifiers.get(WizardryItems.duration_upgrade)))) {
        }
        target.addPotionEffect(new PotionEffect(WizardryPotions.frost, (int)(this.getProperty(EFFECT_DURATION).floatValue() * modifiers.get(WizardryItems.duration_upgrade)), 1));
    }

    @Override
    protected void spawnParticle(World world, double x, double y, double z, double vx, double vy, double vz) {
        float brightness = 0.5F + world.rand.nextFloat() * 0.5F;
        ParticleBuilder.create(ParticleBuilder.Type.SPARKLE).pos(x, y, z).time(12 + world.rand.nextInt(8)).clr(brightness, brightness + 0.1F, 1.0F).spawn(world);
        ParticleBuilder.create(ParticleBuilder.Type.SNOW).pos(x, y, z).time(20 + world.rand.nextInt(10)).spawn(world);
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.spell_book_wng || item == WNGItems.scroll_wng;
    }
}
