package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import electroblob.wizardry.Wizardry;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.spell.SpellAreaEffect;
import electroblob.wizardry.util.EntityUtils;
import electroblob.wizardry.util.MagicDamage;
import electroblob.wizardry.util.ParticleBuilder;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class Tremors extends SpellAreaEffect {

    public Tremors() {
        super(WizardryNextGeneration.MODID, "tremors", SpellActions.POINT_DOWN, true);
        this.alwaysSucceed(true);
        this.addProperties(DAMAGE);
    }

    @Override
    public boolean cast(World world, EntityPlayer caster, EnumHand hand, int ticksInUse, SpellModifiers modifiers) {
        if (ticksInUse % 10 == 0 && caster.onGround) {
            this.playSound(world, caster, ticksInUse, -1, modifiers);
            this.findAndAffectEntities(world, caster.getPositionVector(), caster, ticksInUse, modifiers);
            return true;
        }
        return false;
    }

    @Override
    public boolean cast(World world, EntityLiving caster, EnumHand hand, int ticksInUse, EntityLivingBase target, SpellModifiers modifiers) {
        if (ticksInUse % 10 == 0 && caster.onGround) {
            this.playSound(world, caster, ticksInUse, -1, modifiers);
            this.findAndAffectEntities(world, caster.getPositionVector(), caster, ticksInUse, modifiers);
            return true;
        }
        return false;
    }

    @Override
    public boolean cast(World world, double x, double y, double z, EnumFacing direction, int ticksInUse, int duration, SpellModifiers modifiers) {
        if (ticksInUse % 10 == 0 && world.getBlockState(new BlockPos(x, y, z).down()).getBlock().isCollidable()) {
            this.playSound(world, x, y, z, ticksInUse, -1, modifiers);
            this.findAndAffectEntities(world, new Vec3d(x, y, z), null, ticksInUse, modifiers);
            return true;
        }
        return false;
    }

    @Override
    protected boolean affectEntity(World world, Vec3d origin, @Nullable EntityLivingBase caster, EntityLivingBase target, int i, int ticksInUse, SpellModifiers modifiers) {
        double velocityFactor = origin.distanceTo(target.getPositionVector()) * 4;
        if (target.onGround) {
            target.attackEntityFrom(MagicDamage.causeDirectMagicDamage(caster, MagicDamage.DamageType.BLAST), this.getProperty("damage").floatValue() * modifiers.get("potency"));
            double dx = target.posX - origin.x;
            double dy = target.posY + 1.0 - origin.y;
            double dz = target.posZ - origin.z;
            target.motionX = dx / velocityFactor;
            target.motionY = dy / velocityFactor;
            target.motionZ = dz / velocityFactor;
            return true;
        }
        return false;
    }

    @Override
    protected void spawnParticleEffect(World world, Vec3d origin, double radius, @Nullable EntityLivingBase caster, SpellModifiers modifiers) {
        EntityUtils.getEntitiesWithinRadius(radius, origin.x, origin.y, origin.z, world, EntityPlayer.class).forEach((p) -> Wizardry.proxy.shakeScreen(p, 2.0F));
        for (int i = 0; i < 40; ++i) {
            double particleX = origin.x - 1.0 + 2.0 * world.rand.nextDouble();
            double particleZ = origin.z - 1.0 + 2.0 * world.rand.nextDouble();
            IBlockState block = world.getBlockState(new BlockPos(origin.x, origin.y - 0.5, origin.z));
            world.spawnParticle(EnumParticleTypes.BLOCK_DUST, particleX, origin.y, particleZ, particleX - origin.x, 0.0, particleZ - origin.z, Block.getStateId(block));
        }
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.spell_book_wng || item == WNGItems.scroll_wng;
    }
}

