package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.accessor.AccessorEntityLivingBase;
import com.ipdnaeip.wizardrynextgeneration.accessor.AccessorTileEntityDispenser;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.util.WNGUtils;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.spell.SpellRay;
import electroblob.wizardry.util.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

//New spell engine please!
public class CelestialBeam extends SpellRay {

    //0.5 =
    public static final float WIDTH = 0.5f;

    public CelestialBeam() {
        super(WizardryNextGeneration.MODID, "celestial_beam", SpellActions.POINT, true);
        this.addProperties(DAMAGE);
    }

    @Override
    protected boolean onEntityHit(World world, Entity target, Vec3d hit, @Nullable EntityLivingBase caster, Vec3d origin, int ticksInUse, SpellModifiers modifiers) {
        if (ticksInUse == 0) {
            setTargeter(world, caster, origin, null);
        }
        Vec3d vec3d = target.getPositionVector();
        if (!target.onGround) {
            vec3d = GeometryUtils.getCentre(target);
        }
        shootBeam(world, caster, vec3d, origin, ticksInUse, modifiers);
        return true;
    }

    @Override
    protected boolean onBlockHit(World world, BlockPos pos, EnumFacing side, Vec3d hit, @Nullable EntityLivingBase caster, Vec3d origin, int ticksInUse, SpellModifiers modifiers) {
        if (ticksInUse == 0) {
            setTargeter(world, caster, origin, null);
        }
        shootBeam(world, caster, hit, origin, ticksInUse, modifiers);
        return true;
    }

    @Override
    protected boolean onMiss(World world, @Nullable EntityLivingBase caster, Vec3d origin, Vec3d direction, int ticksInUse, SpellModifiers modifiers) {
        if (ticksInUse != 0) {
            this.spawnFinalBeam(world, caster, origin, ticksInUse, modifiers);
        }
        setTargeter(world, caster, origin, null);
        return false;
    }

    public static Vec3d getTargeter(World world, @Nullable EntityLivingBase caster, Vec3d origin) {
        if (caster == null) {
            TileEntity tileEntity = world.getTileEntity(new BlockPos(origin));
            if (tileEntity instanceof TileEntityDispenser) {
                TileEntityDispenser dispenser = (TileEntityDispenser)tileEntity;
                return ((AccessorTileEntityDispenser)dispenser).wizardrynextgeneration$getTarget();
            }
        } else {
            return ((AccessorEntityLivingBase)caster).wizardrynextgeneration$getTarget();
        }
        return null;
    }

    public static void setTargeter(World world, @Nullable EntityLivingBase caster, Vec3d origin, @Nullable Vec3d target) {
        if (caster == null) {
            TileEntity tileEntity = world.getTileEntity(new BlockPos(origin));
            if (tileEntity instanceof TileEntityDispenser) {
                TileEntityDispenser dispenser = (TileEntityDispenser)tileEntity;
                ((AccessorTileEntityDispenser)dispenser).wizardrynextgeneration$setTarget(target);
            }
        } else {
            ((AccessorEntityLivingBase)caster).wizardrynextgeneration$setTarget(target);
        }
    }

    //Cant set custom predicate filter for the spell so need to override
    @Override
    protected boolean shootSpell(World world, Vec3d origin, Vec3d direction, @Nullable EntityLivingBase caster, int ticksInUse, SpellModifiers modifiers){
        double range = getRange(world, origin, direction, caster, ticksInUse, modifiers);
        Vec3d endpoint = origin.add(direction.scale(range));
        RayTraceResult rayTrace = RayTracer.rayTrace(world, origin, endpoint, aimAssist, hitLiquids, ignoreUncollidables, false, Entity.class, entity -> !(entity instanceof EntityLivingBase) || entity == caster || ((EntityLivingBase)entity).deathTime > 0);
        boolean flag = false;
        if (rayTrace != null) {
            if (rayTrace.typeOfHit == RayTraceResult.Type.ENTITY) {
                flag = onEntityHit(world, rayTrace.entityHit, rayTrace.hitVec, caster, origin, ticksInUse, modifiers);
                if(flag) range = origin.distanceTo(rayTrace.hitVec);
            } else if (rayTrace.typeOfHit == RayTraceResult.Type.BLOCK) {
                flag = onBlockHit(world, rayTrace.getBlockPos(), rayTrace.sideHit, rayTrace.hitVec, caster, origin, ticksInUse, modifiers);
                range = origin.distanceTo(rayTrace.hitVec);
            }
        }
        if (!flag && !onMiss(world, caster, origin, direction, ticksInUse, modifiers)) return false;
        if (world.isRemote) {
            spawnParticleRay(world, origin, direction, caster, range);
        }
        return true;
    }

    @Override
    public boolean cast(World world, double x, double y, double z, EnumFacing direction, int ticksInUse, int duration, SpellModifiers modifiers){
        Vec3d vec = new Vec3d(direction.getDirectionVec());
        //Pass through the coordinates of the dispenser for the purposes of this spell
        Vec3d origin = new Vec3d(x - direction.getXOffset(), y - direction.getYOffset(), z - direction.getZOffset());
        if(!shootSpell(world, origin, vec, null, ticksInUse, modifiers)) return false;
        // This MUST be the coordinates of the actual dispenser, so we need to offset it
        this.playSound(world, x - direction.getXOffset(), y - direction.getYOffset(), z - direction.getZOffset(), ticksInUse, duration, modifiers);
        return true;
    }

    @Override
    public void finishCasting(World world, @Nullable EntityLivingBase caster, double x, double y, double z, @Nullable EnumFacing direction, int duration, SpellModifiers modifiers) {
        super.finishCasting(world, caster, x, y, z, direction, duration, modifiers);
        Vec3d origin;
        if (caster == null) {
            origin = new Vec3d(x, y, z);
        } else {
            origin = caster.getPositionVector();
        }
        this.spawnFinalBeam(world, caster, origin, duration, modifiers);
    }

    public void spawnFinalBeam(World world, @Nullable EntityLivingBase caster, Vec3d origin, int ticksInUse, SpellModifiers modifiers) {
        Vec3d target = getTargeter(world, caster, origin);
        if (world.isRemote && target != null) {
            ParticleBuilder.create(ParticleBuilder.Type.BEAM).pos(origin.x, world.getActualHeight(), origin.z).target(target).scale((ticksInUse % 5) / 5f + WIDTH * 10).time(10).clr(16760576).spawn(world);
        }
    }

    public void shootBeam(World world, @Nullable EntityLivingBase caster, Vec3d hit, Vec3d origin, int ticksInUse, SpellModifiers modifiers) {
        EntityLivingBase target;
        List<RayTraceResult> rayTrace = WNGUtils.rayTraceMultiple(world, new Vec3d(origin.x, world.getActualHeight(), origin.z), hit, WIDTH, false, true, true, true, EntityLivingBase.class, RayTracer.ignoreEntityFilter(caster));
        for (RayTraceResult rayTraceResult : rayTrace) {
            if (rayTraceResult.typeOfHit == RayTraceResult.Type.ENTITY) {
                target = (EntityLivingBase)rayTraceResult.entityHit;
                EntityUtils.attackEntityWithoutKnockback(target, MagicDamage.causeDirectMagicDamage(caster, MagicDamage.DamageType.RADIANT), this.getProperty(DAMAGE).floatValue() * modifiers.get(SpellModifiers.POTENCY));
            }
        }
        if (world.isRemote) {
            ParticleBuilder beam = ParticleBuilder.create(ParticleBuilder.Type.BEAM).pos(0, world.getActualHeight(), 0).target(hit).scale((ticksInUse % 5) / 5f + WIDTH * 10).clr(16760576);
            //ParticleBuilder beam = ParticleBuilder.create(ParticleBuilder.Type.BEAM).pos(0, world.getActualHeight(), 0).target(hit).scale(MathHelper.sin((float)ticksInUse * 0.8F) + 6F).clr(16760576);
            if (caster instanceof EntityLivingBase) {
                beam.entity(caster);
            }
            Vec3d targetVec = getTargeter(world, caster, origin);
            if (targetVec != null) {
                beam.tvel(hit.subtract(targetVec));
            }
            beam.spawn(world);
        }
        setTargeter(world, caster, origin, hit);
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.SPELL_BOOK_WNG || item == WNGItems.SCROLL_WNG;
    }

}
