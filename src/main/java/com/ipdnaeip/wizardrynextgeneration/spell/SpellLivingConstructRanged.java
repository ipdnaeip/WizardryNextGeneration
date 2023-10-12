package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.entity.construct.EntityLivingMagicConstruct;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.spell.Spell;
import electroblob.wizardry.util.BlockUtils;
import electroblob.wizardry.util.RayTracer;
import electroblob.wizardry.util.SpellModifiers;
import java.util.function.Function;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class SpellLivingConstructRanged<T extends EntityLivingMagicConstruct> extends SpellLivingConstruct<T> {
    protected boolean hitLiquids;
    protected boolean ignoreUncollidables;


    public SpellLivingConstructRanged(String modID, String name, Function<World, T> constructFactory, boolean permanent) {
        super(modID, name, SpellActions.POINT, constructFactory, permanent);
        this.hitLiquids = false;
        this.ignoreUncollidables = false;
        this.addProperties("range");
        this.npcSelector((e, o) -> true);
    }

    public Spell hitLiquids(boolean hitLiquids) {
        this.hitLiquids = hitLiquids;
        return this;
    }

    public Spell ignoreUncollidables(boolean ignoreUncollidables) {
        this.ignoreUncollidables = ignoreUncollidables;
        return this;
    }

    @Override
    public boolean canBeCastBy(TileEntityDispenser dispenser) {
        return true;
    }

    @Override
    public boolean cast(World world, EntityPlayer caster, EnumHand hand, int ticksInUse, SpellModifiers modifiers) {
        double range = this.getProperty("range").doubleValue() * (double)modifiers.get(WizardryItems.range_upgrade);
        RayTraceResult rayTrace = RayTracer.standardBlockRayTrace(world, caster, range, this.hitLiquids, this.ignoreUncollidables, false);
        if (rayTrace == null || rayTrace.typeOfHit != Type.BLOCK || rayTrace.sideHit != EnumFacing.UP && this.requiresFloor) {
            if (this.requiresFloor) {
                return false;
            }

            if (!world.isRemote) {
                Vec3d look = caster.getLookVec();
                double x = caster.posX + look.x * range;
                double y = caster.posY + (double)caster.getEyeHeight() + look.y * range;
                double z = caster.posZ + look.z * range;
                if (!this.spawnConstruct(world, x, y, z, null, caster, modifiers)) {
                    return false;
                }
            }
        } else if (!world.isRemote) {
            double x = rayTrace.hitVec.x;
            double y = rayTrace.hitVec.y;
            double z = rayTrace.hitVec.z;
            if (!this.spawnConstruct(world, x, y, z, rayTrace.sideHit, caster, modifiers)) {
                return false;
            }
        }

        this.playSound(world, caster, ticksInUse, -1, modifiers);
        return true;
    }

    @Override
    public boolean cast(World world, EntityLiving caster, EnumHand hand, int ticksInUse, EntityLivingBase target, SpellModifiers modifiers) {
        double range = this.getProperty("range").doubleValue() * (double)modifiers.get(WizardryItems.range_upgrade);
        Vec3d origin = caster.getPositionEyes(1.0F);
        if (target != null && (double)caster.getDistance(target) <= range) {
            if (!world.isRemote) {
                double x = target.posX;
                double y = target.posY;
                double z = target.posZ;
                RayTraceResult hit = world.rayTraceBlocks(origin, new Vec3d(x, y, z), this.hitLiquids, this.ignoreUncollidables, false);
                if (hit != null && hit.typeOfHit == Type.BLOCK && !hit.getBlockPos().equals(new BlockPos(x, y, z))) {
                    return false;
                }

                EnumFacing side = null;
                if (!target.onGround && this.requiresFloor) {
                    Integer floor = BlockUtils.getNearestFloor(world, new BlockPos(x, y, z), 3);
                    if (floor == null) {
                        return false;
                    }

                    y = (double)floor;
                    side = EnumFacing.UP;
                }

                if (!this.spawnConstruct(world, x, y, z, side, caster, modifiers)) {
                    return false;
                }
            }

            caster.swingArm(hand);
            this.playSound(world, caster, ticksInUse, -1, modifiers);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean cast(World world, double x, double y, double z, EnumFacing direction, int ticksInUse, int duration, SpellModifiers modifiers) {
        double range = this.getProperty("range").doubleValue() * (double)modifiers.get(WizardryItems.range_upgrade);
        Vec3d origin = new Vec3d(x, y, z);
        Vec3d endpoint = origin.add((new Vec3d(direction.getDirectionVec())).scale(range));
        RayTraceResult rayTrace = world.rayTraceBlocks(origin, endpoint, this.hitLiquids, this.ignoreUncollidables, false);
        if (rayTrace == null || rayTrace.typeOfHit != Type.BLOCK || rayTrace.sideHit != EnumFacing.UP && this.requiresFloor) {
            if (this.requiresFloor) {
                return false;
            }

            if (!world.isRemote && !this.spawnConstruct(world, endpoint.x, endpoint.y, endpoint.z, null, null, modifiers)) {
                return false;
            }
        } else if (!world.isRemote) {
            double x1 = rayTrace.hitVec.x;
            double y1 = rayTrace.hitVec.y;
            double z1 = rayTrace.hitVec.z;
            if (!this.spawnConstruct(world, x1, y1, z1, rayTrace.sideHit, null, modifiers)) {
                return false;
            }
        }

        this.playSound(world, x - (double)direction.getXOffset(), y - (double)direction.getYOffset(), z - (double)direction.getZOffset(), ticksInUse, duration, modifiers);
        return true;
    }
}
