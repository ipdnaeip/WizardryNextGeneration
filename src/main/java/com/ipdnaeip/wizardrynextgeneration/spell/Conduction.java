package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGSpells;
import com.ipdnaeip.wizardrynextgeneration.util.WNGUtils;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.registry.WizardryPotions;
import electroblob.wizardry.spell.SpellRay;
import electroblob.wizardry.util.MagicDamage;
import electroblob.wizardry.util.ParticleBuilder;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Conduction extends SpellRay {

    public static final String MULTIPLIER_TAG = "damage_multiplier_in_water";

    public Conduction() {
        super(WizardryNextGeneration.MODID, "conduction", SpellActions.POINT, false);
        this.soundValues(1F, 1F, 0.1F);
        this.addProperties(DAMAGE, MULTIPLIER_TAG, EFFECT_DURATION);
    }

    @Override
    protected boolean onEntityHit(World world, Entity target, Vec3d hit, EntityLivingBase caster, Vec3d origin, int ticksInUse, SpellModifiers modifiers) {

        if (target instanceof EntityLivingBase) {
            if (world.isRemote) {
                ParticleBuilder.create(ParticleBuilder.Type.LIGHTNING).entity(caster).pos(caster != null ? origin.subtract(caster.getPositionVector()) : origin).target(target).spawn(world);
                ParticleBuilder.spawnShockParticles(world, target.posX, target.posY + (double)(target.height / 2.0F), target.posZ);
            }
            EntityLivingBase targetEntity = (EntityLivingBase) target;
            MagicDamage.DamageType damageType = MagicDamage.DamageType.SHOCK;
            if (WNGUtils.canMagicDamageEntity(caster, targetEntity, damageType, this, ticksInUse)) {
                float damage = getProperty(DAMAGE).floatValue() * modifiers.get(SpellModifiers.POTENCY);
                if (targetEntity.isInWater()) {
                    damage = damage * getProperty(MULTIPLIER_TAG).floatValue();
                    targetEntity.attackEntityFrom(MagicDamage.causeDirectMagicDamage(caster, MagicDamage.DamageType.SHOCK), damage);
                    targetEntity.addPotionEffect(new PotionEffect(WizardryPotions.paralysis, (int)(WNGSpells.CONDUCTION.getProperty(EFFECT_DURATION).floatValue() * modifiers.get(WizardryItems.duration_upgrade))));
                    world.playSound(null, target.getPosition(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, world.rand.nextFloat() * 0.2F + 0.9F);
                } else {
                    targetEntity.attackEntityFrom(MagicDamage.causeDirectMagicDamage(caster, MagicDamage.DamageType.SHOCK), damage);
                }
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
        return false;
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.SPELL_BOOK_WNG || item == WNGItems.SCROLL_WNG;
    }
}

