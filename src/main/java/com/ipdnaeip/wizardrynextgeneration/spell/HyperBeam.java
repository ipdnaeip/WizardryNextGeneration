package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.util.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class HyperBeam extends SpellRayMultiple {

    public HyperBeam() {
        super(WizardryNextGeneration.MODID, "hyper_beam", SpellActions.POINT, false);
        this.soundValues(2F, 1F, 0.1F);
        this.addProperties(DAMAGE, EFFECT_DURATION);
        this.aimAssist = 0.5f;
    }

    @Override
    protected boolean onEntityHit(World world, Entity target, Vec3d hit, EntityLivingBase caster, Vec3d origin, int ticksInUse, SpellModifiers modifiers) {
        if (target instanceof EntityLivingBase) {
            target.attackEntityFrom(MagicDamage.causeDirectMagicDamage(caster, MagicDamage.DamageType.MAGIC), this.getProperty(DAMAGE).floatValue());
        }
        if (caster != null) caster.addPotionEffect(new PotionEffect(WNGPotions.disempowerment, this.getProperty(EFFECT_DURATION).intValue(), 7));
        return true;
    }

    @Override
    protected boolean onBlockHit(World world, BlockPos pos, EnumFacing side, Vec3d hit, EntityLivingBase caster, Vec3d origin, int ticksInUse, SpellModifiers modifiers) {
        return false;
    }

    @Override
    protected boolean onMiss(World world, EntityLivingBase caster, Vec3d origin, Vec3d direction, int ticksInUse, SpellModifiers modifiers) {
        if (caster != null) caster.addPotionEffect(new PotionEffect(WNGPotions.disempowerment, this.getProperty(EFFECT_DURATION).intValue(), 7));
        return true;
    }

    protected void spawnParticleRay(World world, Vec3d origin, Vec3d direction, EntityLivingBase caster, double distance) {
        if (caster != null) {
            origin = new Vec3d(caster.posX, caster.posY + (double)caster.getEyeHeight() - 0.25, caster.posZ);
            ParticleBuilder.create(ParticleBuilder.Type.BEAM).entity(caster).pos(origin.subtract(caster.getPositionVector())).length(distance).scale(10f).time(10).clr(255, 255, 255).spawn(world);
        } else {
            ParticleBuilder.create(ParticleBuilder.Type.BEAM).pos(origin).target(origin.add(direction.scale(distance))).scale(10f).clr(255, 255, 255).time(10).spawn(world);
        }
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.spell_book_wng || item == WNGItems.scroll_wng;
    }
}






