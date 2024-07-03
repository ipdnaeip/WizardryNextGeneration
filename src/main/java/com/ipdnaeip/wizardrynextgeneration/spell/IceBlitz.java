package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.util.WNGUtils;
import electroblob.wizardry.block.BlockStatue;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.registry.WizardryBlocks;
import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.registry.WizardryPotions;
import electroblob.wizardry.spell.SpellRay;
import electroblob.wizardry.util.EntityUtils;
import electroblob.wizardry.util.MagicDamage;
import electroblob.wizardry.util.ParticleBuilder;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class IceBlitz extends SpellRay {

    public static final String ICE_DURATION = "ice_duration";
    public IceBlitz() {
        super(WizardryNextGeneration.MODID, "ice_blitz", SpellActions.POINT, false);
        this.soundValues(1.0F, 1.0F, 0.1F);
        this.addProperties(DAMAGE, EFFECT_DURATION, ICE_DURATION);
    }

    @Override
    protected boolean onEntityHit(World world, Entity target, Vec3d hit, EntityLivingBase caster, Vec3d origin, int ticksInUse, SpellModifiers modifiers) {
        if (target instanceof EntityLivingBase) {
            MagicDamage.DamageType damageType = MagicDamage.DamageType.FROST;
            if (WNGUtils.canMagicDamageEntity(caster, target, damageType, this, ticksInUse)) {
                EntityUtils.attackEntityWithoutKnockback(target, MagicDamage.causeDirectMagicDamage(caster, damageType), getProperty(DAMAGE).floatValue() * modifiers.get(SpellModifiers.POTENCY));
                if (target instanceof EntityLiving && !world.isRemote && ((BlockStatue) WizardryBlocks.ice_statue).convertToStatue((EntityLiving) target, caster, (int) (this.getProperty(ICE_DURATION).floatValue() * modifiers.get(WizardryItems.duration_upgrade)))) {
                }
                EntityLivingBase targetEntity = (EntityLivingBase) target;
                targetEntity.addPotionEffect(new PotionEffect(WizardryPotions.frost, (int) (this.getProperty(EFFECT_DURATION).intValue() * modifiers.get(WizardryItems.duration_upgrade)), 1));
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
        float brightness = 0.5F + world.rand.nextFloat() * 0.5F;
        ParticleBuilder.create(ParticleBuilder.Type.SPARKLE).pos(x, y, z).time(12 + world.rand.nextInt(8)).clr(brightness, brightness + 0.1F, 1.0F).spawn(world);
        ParticleBuilder.create(ParticleBuilder.Type.SNOW).pos(x, y, z).time(20 + world.rand.nextInt(10)).spawn(world);
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.SPELL_BOOK_WNG || item == WNGItems.SCROLL_WNG;
    }
}
