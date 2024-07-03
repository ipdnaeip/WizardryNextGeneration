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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Migraine extends SpellRay {

    public Migraine() {
        super(WizardryNextGeneration.MODID, "migraine", SpellActions.POINT, true);
        this.addProperties(DAMAGE, EFFECT_DURATION);
    }

    @Override
    protected SoundEvent[] createSounds() {
        return this.createContinuousSpellSounds();
    }

    @Override
    protected void playSound(World world, EntityLivingBase entity, int ticksInUse, int duration, SpellModifiers modifiers, String... sounds) {
        this.playSoundLoop(world, entity, ticksInUse);
    }

    @Override
    protected void playSound(World world, double x, double y, double z, int ticksInUse, int duration, SpellModifiers modifiers, String... sounds) {
        this.playSoundLoop(world, x, y, z, ticksInUse, duration);
    }

    @Override
    protected boolean onEntityHit(World world, Entity target, Vec3d hit, EntityLivingBase caster, Vec3d origin, int ticksInUse, SpellModifiers modifiers) {
        if (target instanceof EntityLivingBase) {
            EntityLivingBase targetEntity = (EntityLivingBase) target;
            MagicDamage.DamageType damageType = MagicDamage.DamageType.SHOCK;
            if (WNGUtils.canMagicDamageEntity(caster, target, damageType, this, ticksInUse)) {
                if (ticksInUse % 10 == 0) {
                    EntityUtils.attackEntityWithoutKnockback(targetEntity, MagicDamage.causeDirectMagicDamage(caster, damageType), this.getProperty(DAMAGE).floatValue() * modifiers.get(SpellModifiers.POTENCY));
                    if (targetEntity instanceof EntityPlayer) {
                        targetEntity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, this.getProperty(EFFECT_DURATION).intValue()));
                        targetEntity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, this.getProperty(EFFECT_DURATION).intValue()));
                    } else {
                        targetEntity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, this.getProperty(EFFECT_DURATION).intValue(), 1));
                    }
                }
            }
        }
        return true;
    }

    @Override
    protected boolean onBlockHit(World world, BlockPos pos, EnumFacing side, Vec3d hit, EntityLivingBase caster, Vec3d origin, int ticksInUse, SpellModifiers modifiers) {
        return true;
    }

    @Override
    protected boolean onMiss(World world, EntityLivingBase caster, Vec3d origin, Vec3d direction, int ticksInUse, SpellModifiers modifiers) {
        return true;
    }

    @Override
    protected void spawnParticle(World world, double x, double y, double z, double vx, double vy, double vz) {
        ParticleBuilder.create(ParticleBuilder.Type.DARK_MAGIC).pos(x, y, z).vel(vx, vy, vz).clr(85, 255, 85).spawn(world);
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.SPELL_BOOK_WNG || item == WNGItems.SCROLL_WNG;
    }
}
