package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.util.WNGUtils;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.spell.SpellRay;
import electroblob.wizardry.util.EntityUtils;
import electroblob.wizardry.util.MagicDamage;
import electroblob.wizardry.util.ParticleBuilder;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ShadowBlitz extends SpellRay {

    public static final String DARKNESS_MULTIPLIER = "darkness_multiplier";

    public ShadowBlitz() {
        super(WizardryNextGeneration.MODID, "shadow_blitz", SpellActions.POINT, false);
        this.soundValues(1F, 1F, 0.1F);
        this.addProperties(DAMAGE, EFFECT_DURATION, DARKNESS_MULTIPLIER);
    }

    @Override
    protected boolean onEntityHit(World world, Entity target, Vec3d hit, EntityLivingBase caster, Vec3d origin, int ticksInUse, SpellModifiers modifiers) {
        if (target instanceof EntityLivingBase) {
            EntityLivingBase targetEntity = (EntityLivingBase) target;
            MagicDamage.DamageType damageType = MagicDamage.DamageType.WITHER;
            if (WNGUtils.canMagicDamageEntity(caster, target, damageType, this, ticksInUse)) {
                int duration = (int) (this.getProperty(EFFECT_DURATION).floatValue() * modifiers.get(WizardryItems.duration_upgrade));
                float damage = this.getProperty(DAMAGE).floatValue() * modifiers.get(SpellModifiers.POTENCY);
                if (targetEntity.getBrightness() < 0.25f) {
                    damage *= this.getProperty(DARKNESS_MULTIPLIER).floatValue();
                    targetEntity.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, duration));
                    world.playSound(null, target.getPosition(), SoundEvents.ENTITY_ENDERDRAGON_FLAP, SoundCategory.BLOCKS, 1f, 0.9f + world.rand.nextFloat() * 0.2f);
                }
                EntityUtils.attackEntityWithoutKnockback(targetEntity, MagicDamage.causeDirectMagicDamage(caster, damageType), damage);
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
        ParticleBuilder.create(ParticleBuilder.Type.DARK_MAGIC).pos(x, y, z).clr(0, 0, 0).time(20 + world.rand.nextInt(10)).spawn(world);
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.SPELL_BOOK_WNG || item == WNGItems.SCROLL_WNG;
    }
}
