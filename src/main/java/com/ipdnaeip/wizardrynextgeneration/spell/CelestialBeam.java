package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.util.WNGUtils;
import electroblob.wizardry.Wizardry;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.spell.SpellRay;
import electroblob.wizardry.util.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class CelestialBeam extends SpellRay {

    public CelestialBeam() {
        super(WizardryNextGeneration.MODID, "celestial_beam", SpellActions.POINT, true);
        this.addProperties(DAMAGE);
        this.ignoreLivingEntities = true;
    }

    @Override
    protected boolean onEntityHit(World world, Entity target, Vec3d hit, EntityLivingBase caster, Vec3d origin, int ticksInUse, SpellModifiers modifiers) {
        return false;
    }

    @Override
    protected boolean onBlockHit(World world, BlockPos pos, EnumFacing side, Vec3d hit, EntityLivingBase caster, Vec3d origin, int ticksInUse, SpellModifiers modifiers) {
        RayTraceResult rayTrace = world.rayTraceBlocks(new Vec3d(origin.x, world.getActualHeight(), origin.z), hit, false, true, false);
        if (rayTrace != null && rayTrace.getBlockPos() == pos) {
            shootBeam(world, caster, hit, origin, modifiers);
            return true;
        }
        return false;
    }

    @Override
    protected boolean onMiss(World world, EntityLivingBase caster, Vec3d origin, Vec3d direction, int ticksInUse, SpellModifiers modifiers) {
        return false;
    }

    public void shootBeam(World world, @Nullable EntityLivingBase caster, Vec3d hit, Vec3d origin, SpellModifiers modifiers) {
        EntityLivingBase target;
        List<RayTraceResult> rayTrace = WNGUtils.rayTraceMultiple(world, new Vec3d(origin.x, world.getActualHeight(), origin.z), hit, this.aimAssist, false, true, false, false, EntityLivingBase.class, RayTracer.ignoreEntityFilter(caster));
        for (RayTraceResult rayTraceResult : rayTrace) {
            if (rayTraceResult.typeOfHit == RayTraceResult.Type.ENTITY) {
                target = (EntityLivingBase)rayTraceResult.entityHit;
                EntityUtils.attackEntityWithoutKnockback(target, MagicDamage.causeDirectMagicDamage(caster, MagicDamage.DamageType.RADIANT), this.getProperty(DAMAGE).floatValue() * modifiers.get("potency"));
            }
        }
        if (world.isRemote) {
            ParticleBuilder.create(ParticleBuilder.Type.BEAM).pos(origin.x, world.getActualHeight(), origin.z).target(hit).scale(MathHelper.sin((float) Wizardry.proxy.getThePlayer().ticksExisted * 0.5F) + 3F).clr(16760576).spawn(world);
        }
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.spell_book_wng || item == WNGItems.scroll_wng;
    }

}
