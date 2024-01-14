package com.ipdnaeip.wizardrynextgeneration.spell;

import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.spell.SpellRay;
import electroblob.wizardry.util.AllyDesignationSystem;
import electroblob.wizardry.util.EntityUtils;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumAction;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public abstract class SpellBarrage extends SpellRay {

    public SpellBarrage(String modId, String name, EnumAction action, boolean isContinuous) {
        super(modId, name, action, isContinuous);
        this.addProperties(EFFECT_RADIUS);
    }

    @Override
    protected boolean onEntityHit(World world, Entity target, Vec3d hit, EntityLivingBase caster, Vec3d origin, int ticksInUse, SpellModifiers modifiers) {
        double range = (this.getProperty(EFFECT_RADIUS).floatValue() * modifiers.get(WizardryItems.blast_upgrade));
        List<EntityLivingBase> targets = EntityUtils.getLivingWithinRadius(range, target.posX, target.posY, target.posZ, world);
        for (EntityLivingBase entity : targets) {
            if (AllyDesignationSystem.isValidTarget(caster, entity)) {
                this.barrageEffect(world, entity, caster, ticksInUse, modifiers);
            }
        }
        if (world.isRemote) this.barrageParticles(world, hit, caster, ticksInUse, modifiers);
        return true;
    }

    @Override
    protected boolean onBlockHit(World world, BlockPos pos, EnumFacing side, Vec3d hit, EntityLivingBase caster, Vec3d origin, int ticksInUse, SpellModifiers modifiers) {
        double range = (this.getProperty(EFFECT_RADIUS).floatValue() * modifiers.get(WizardryItems.blast_upgrade));
        List<EntityLivingBase> targets = EntityUtils.getLivingWithinRadius(range, pos.getX(), pos.getY(), pos.getZ(), world);
        for (EntityLivingBase entity : targets) {
            if (AllyDesignationSystem.isValidTarget(caster, entity)) {
                this.barrageEffect(world, entity, caster, ticksInUse, modifiers);
            }
        }
        if (world.isRemote) this.barrageParticles(world, hit, caster, ticksInUse, modifiers);
        return true;
    }

    @Override
    protected boolean onMiss(World world, EntityLivingBase caster, Vec3d origin, Vec3d direction, int ticksInUse, SpellModifiers modifiers) {
        return true;
    }

    protected abstract void barrageEffect(World world, EntityLivingBase target, @Nullable EntityLivingBase caster, int ticksInUse, SpellModifiers modifiers);

    protected void barrageParticles(World world, Vec3d hit, @Nullable EntityLivingBase caster, int ticksInUse, SpellModifiers modifiers) {
    }
}
