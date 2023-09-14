package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.util.WNGUtils;
import electroblob.wizardry.spell.SpellRay;
import electroblob.wizardry.util.EntityUtils;
import electroblob.wizardry.util.RayTracer;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumAction;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public abstract class SpellRayMultiple extends SpellRay {

    protected boolean penetratesBlocks;

    public SpellRayMultiple(String modID, String name, EnumAction action, boolean isContinuous) {
        super(modID, name, action, isContinuous);
        this.penetratesBlocks = false;
    }

    @Override
    protected boolean shootSpell(World world, Vec3d origin, Vec3d direction, @Nullable EntityLivingBase caster, int ticksInUse, SpellModifiers modifiers) {
        double range = this.getRange(world, origin, direction, caster, ticksInUse, modifiers);
        Vec3d endpoint = origin.add(direction.scale(range));
        List<RayTraceResult> rayTrace = WNGUtils.rayTraceMultiple(world, origin, endpoint, this.aimAssist, this.hitLiquids, this.ignoreUncollidables, false, penetratesBlocks, Entity.class, this.ignoreLivingEntities ? EntityUtils::isLiving : RayTracer.ignoreEntityFilter(caster));
        boolean flag = false;
        if (!rayTrace.isEmpty()) {
            for (RayTraceResult rayTraceResult : rayTrace) {
                if (rayTraceResult.typeOfHit == RayTraceResult.Type.ENTITY) {
                    flag = this.onEntityHit(world, rayTraceResult.entityHit, rayTraceResult.hitVec, caster, origin, ticksInUse, modifiers);
                } else if (rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {
                    flag = this.onBlockHit(world, rayTraceResult.getBlockPos(), rayTraceResult.sideHit, rayTraceResult.hitVec, caster, origin, ticksInUse, modifiers);
                }
            }
        }
        if (!flag && !this.onMiss(world, caster, origin, direction, ticksInUse, modifiers)) {
            return false;
        } else {
            if (world.isRemote) {
                this.spawnParticleRay(world, origin, direction, caster, range);
            }
            return true;
        }
    }
}
