package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGSpells;
import com.ipdnaeip.wizardrynextgeneration.util.WNGUtils;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.registry.WizardryPotions;
import electroblob.wizardry.registry.WizardrySounds;
import electroblob.wizardry.spell.SpellRay;
import electroblob.wizardry.util.EntityUtils;
import electroblob.wizardry.util.MagicDamage;
import electroblob.wizardry.util.ParticleBuilder;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Tase extends SpellRay {
    public Tase() {
        super(WizardryNextGeneration.MODID, "tase", SpellActions.POINT, true);
        this.aimAssist(0.6F);
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
        if (target instanceof EntityLivingBase && ticksInUse % 10 == 0) {
            MagicDamage.DamageType damageType = MagicDamage.DamageType.SHOCK;
            if (WNGUtils.canMagicDamageEntity(caster, target, damageType, this, ticksInUse)) {
                EntityLivingBase targetEntity = (EntityLivingBase) target;
                EntityUtils.attackEntityWithoutKnockback(target, MagicDamage.causeDirectMagicDamage(caster, damageType), this.getProperty(DAMAGE).floatValue() * modifiers.get(SpellModifiers.POTENCY));
                if (Math.random() < 0.3F * (1 + modifiers.get(SpellModifiers.POTENCY))) {
                    targetEntity.addPotionEffect(new PotionEffect(WizardryPotions.paralysis, WNGSpells.TASE.getProperty(EFFECT_DURATION).intValue(), 0));
                    world.playSound(null, target.getPosition(), WizardrySounds.ENTITY_LIGHTNING_ARROW_HIT, WizardrySounds.SPELLS, 1.0F, world.rand.nextFloat() * 0.2F + 1.2F);
                }
            }
        }
        if (world.isRemote) {
            if (ticksInUse % 3 == 0) {
                ParticleBuilder.create(ParticleBuilder.Type.LIGHTNING).entity(caster).pos(caster != null ? origin.subtract(caster.getPositionVector()) : origin).target(target).spawn(world);
            }
            for(int i = 0; i < 5; ++i) {
                ParticleBuilder.create(ParticleBuilder.Type.SPARK, target).spawn(world);
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
        if (world.isRemote && ticksInUse % 4 == 0) {
            double freeRange = 0.8 * this.getRange(world, origin, direction, caster, ticksInUse, modifiers);
            ParticleBuilder.create(ParticleBuilder.Type.LIGHTNING).entity(caster).pos(caster != null ? origin.subtract(caster.getPositionVector()) : origin).length(freeRange).spawn(world);
            ParticleBuilder.create(ParticleBuilder.Type.LIGHTNING).pos(origin).target(origin.add(direction.scale(freeRange))).spawn(world);
        }
        return true;
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.SPELL_BOOK_WNG || item == WNGItems.SCROLL_WNG;
    }
}
