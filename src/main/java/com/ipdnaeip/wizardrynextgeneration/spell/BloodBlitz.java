package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.util.WNGUtils;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.spell.SpellRay;
import electroblob.wizardry.util.EntityUtils;
import electroblob.wizardry.util.MagicDamage;
import electroblob.wizardry.util.ParticleBuilder;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BloodBlitz extends SpellRay {

    public static final String HEAL_FACTOR = "heal_factor";

    public BloodBlitz() {
        super(WizardryNextGeneration.MODID, "blood_blitz", SpellActions.POINT, false);
        this.soundValues(1.0F, 1.0F, 0.1F);
        this.addProperties(DAMAGE, HEAL_FACTOR);
    }

    @Override
    protected boolean onEntityHit(World world, Entity target, Vec3d hit, EntityLivingBase caster, Vec3d origin, int ticksInUse, SpellModifiers modifiers) {
        if (target instanceof EntityLivingBase) {
            MagicDamage.DamageType damageType = MagicDamage.DamageType.WITHER;
            if (WNGUtils.canMagicDamageEntity(caster, target, damageType, this, ticksInUse)) {
                float health = ((EntityLivingBase)target).getHealth();
                EntityUtils.attackEntityWithoutKnockback(target, MagicDamage.causeDirectMagicDamage(caster, damageType), getProperty(DAMAGE).floatValue() * modifiers.get(SpellModifiers.POTENCY));
                health -= ((EntityLivingBase) target).getHealth();
                if (world.isRemote)
                    ParticleBuilder.create(ParticleBuilder.Type.CLOUD, world.rand, target.posX, target.posY + target.getEyeHeight() - 0.5f, target.posZ, 0, false).clr(170, 0, 0).time(10 + world.rand.nextInt(5)).spawn(world);
                if (caster != null)
                    caster.heal(health * getProperty(HEAL_FACTOR).floatValue());
            }
        }
        return true;
    }

    @Override
    protected boolean onBlockHit(World world, BlockPos pos, EnumFacing side, Vec3d hit, EntityLivingBase caster, Vec3d origin, int ticksInUse, SpellModifiers modifiers) {
        return false;
    }

    @Override
    protected boolean onMiss(World world, EntityLivingBase caster, Vec3d origin, Vec3d direction, int ticksInUse, SpellModifiers modifiers) {
        return true;
    }

    @Override
    protected void spawnParticle(World world, double x, double y, double z, double vx, double vy, double vz) {
        ParticleBuilder.create(ParticleBuilder.Type.DARK_MAGIC).pos(x, y, z).clr(170, 0, 0).time(20 + world.rand.nextInt(10)).spawn(world);
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.SPELL_BOOK_WNG || item == WNGItems.SCROLL_WNG;
    }
}

