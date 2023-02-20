package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGSpells;
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class Tase extends SpellRay {
    public Tase() {
        super(WizardryNextGeneration.MODID, "tase", SpellActions.POINT, true);
        this.aimAssist(0.6F);
        this.addProperties(DAMAGE, EFFECT_DURATION);
    }

    @Override
    public boolean canBeCastBy(TileEntityDispenser dispenser) {
        return false;
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
        if (EntityUtils.isLiving(target)) {
            if (MagicDamage.isEntityImmune(MagicDamage.DamageType.SHOCK, target)) {
                if (!world.isRemote && ticksInUse == 1 && caster instanceof EntityPlayer) {
                    ((EntityPlayer)caster).sendStatusMessage(new TextComponentTranslation("spell.resist", new Object[]{target.getName(), this.getNameForTranslationFormatted()}), true);
                }
            } else if (ticksInUse % 10 == 0) {
                EntityUtils.attackEntityWithoutKnockback(target, MagicDamage.causeDirectMagicDamage(caster, MagicDamage.DamageType.SHOCK), this.getProperty(DAMAGE).floatValue() * modifiers.get(SpellModifiers.POTENCY));
                if (target instanceof EntityLivingBase && Math.random() < 0.3F * (1 + modifiers.get(SpellModifiers.POTENCY))) {
                    EntityLivingBase targetEntity = (EntityLivingBase) target;
                    targetEntity.addPotionEffect(new PotionEffect(WizardryPotions.paralysis, WNGSpells.tase.getProperty(EFFECT_DURATION).intValue(), 0));
                    world.playSound(null, target.getPosition(), WizardrySounds.ENTITY_LIGHTNING_ARROW_HIT, SoundCategory.BLOCKS, 1.0F, world.rand.nextFloat() * 0.2F + 1.2F);
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
            if (caster != null) {
                ParticleBuilder.create(ParticleBuilder.Type.LIGHTNING).entity(caster).pos(origin.subtract(caster.getPositionVector())).length(freeRange).spawn(world);
            } else {
                ParticleBuilder.create(ParticleBuilder.Type.LIGHTNING).pos(origin).target(origin.add(direction.scale(freeRange))).spawn(world);
            }
        }

        return true;
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.spell_book_wng || item == WNGItems.scroll_wng;
    }
}
