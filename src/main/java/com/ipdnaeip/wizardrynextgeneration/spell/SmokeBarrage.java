package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGSpells;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.spell.SpellRay;
import electroblob.wizardry.util.EntityUtils;
import electroblob.wizardry.util.MagicDamage;
import electroblob.wizardry.util.ParticleBuilder;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class SmokeBarrage extends SpellRay {

    public SmokeBarrage() {
        super(WizardryNextGeneration.MODID, "smoke_barrage", SpellActions.POINT, false);
        this.soundValues(1F, 0.7F, 0.1F);
        this.addProperties(DAMAGE, RANGE, EFFECT_DURATION, EFFECT_RADIUS);
    }

    @Override
    protected boolean onEntityHit(World world, Entity target, Vec3d hit, EntityLivingBase caster, Vec3d origin, int ticksInUse, SpellModifiers modifiers) {
        ParticleBuilder.create(ParticleBuilder.Type.FLASH).pos(target.posX, target.posY + target.getEyeHeight() - 0.5F, target.posZ).scale(5.0F * modifiers.get(WizardryItems.blast_upgrade)).clr(0, 0, 0).spawn(world);
        world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, target.posX, target.posY + target.getEyeHeight() - 0.5F, target.posZ, 0.0, 0.0, 0.0);
        for (int i = 0; (float) i < 60.0F * this.getProperty(EFFECT_RADIUS).floatValue(); ++i) {
            float brightness = world.rand.nextFloat() * 0.1F + 0.1F;
            ParticleBuilder.create(ParticleBuilder.Type.CLOUD, world.rand, target.posX, target.posY + target.getEyeHeight() - 0.5F, target.posZ, (2.0F * modifiers.get(WizardryItems.blast_upgrade)), false).clr(brightness, brightness, brightness).time(WNGSpells.smoke_barrage.getProperty(EFFECT_DURATION).intValue() + world.rand.nextInt(12)).shaded(true).spawn(world);
            brightness = world.rand.nextFloat() * 0.3F;
            ParticleBuilder.create(ParticleBuilder.Type.DARK_MAGIC, world.rand, target.posX, target.posY + target.getEyeHeight() - 0.5F, target.posZ, (2.0F * modifiers.get(WizardryItems.blast_upgrade)), false).clr(brightness, brightness, brightness).spawn(world);
        }
        if (!world.isRemote) {
            double range = (WNGSpells.smoke_barrage.getProperty(EFFECT_RADIUS).floatValue() * modifiers.get(WizardryItems.blast_upgrade));
            List<EntityLivingBase> targets = EntityUtils.getLivingWithinRadius(range, target.posX, target.posY, target.posZ, world);
            int duration = (int)(WNGSpells.smoke_barrage.getProperty(EFFECT_DURATION).floatValue() * modifiers.get(WizardryItems.duration_upgrade));
            Iterator var6 = targets.iterator();

            while (var6.hasNext()) {
                EntityLivingBase targetEntity = (EntityLivingBase) var6.next();
                if (targetEntity != caster) {
                    EntityUtils.attackEntityWithoutKnockback(targetEntity, MagicDamage.causeDirectMagicDamage(caster, MagicDamage.DamageType.FIRE), getProperty(DAMAGE).floatValue() * modifiers.get(SpellModifiers.POTENCY));
                    targetEntity.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, duration, 0));
                    targetEntity.addPotionEffect(new PotionEffect(MobEffects.WITHER, duration, 2));
                }
            }
        }

        return true;
    }
    @Override
    protected boolean onBlockHit(World world, BlockPos pos, EnumFacing side, Vec3d hit, EntityLivingBase caster, Vec3d origin, int ticksInUse, SpellModifiers modifiers) {
        Vec3d target = new Vec3d(pos);
        ParticleBuilder.create(ParticleBuilder.Type.FLASH).pos(target).scale(5.0F * modifiers.get(WizardryItems.blast_upgrade)).clr(0, 0, 0).spawn(world);
        world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, target.x, target.y, target.z, 0.0, 0.0, 0.0);
        for (int i = 0; (float) i < 60.0F * this.getProperty(EFFECT_RADIUS).floatValue(); ++i) {
            float brightness = world.rand.nextFloat() * 0.1F + 0.1F;
            ParticleBuilder.create(ParticleBuilder.Type.CLOUD, world.rand, target.x, target.y, target.z, (2.0F * modifiers.get(WizardryItems.blast_upgrade)), false).clr(brightness, brightness, brightness).time(WNGSpells.smoke_barrage.getProperty(EFFECT_DURATION).intValue() + world.rand.nextInt(12)).shaded(true).spawn(world);
            brightness = world.rand.nextFloat() * 0.3F;
            ParticleBuilder.create(ParticleBuilder.Type.DARK_MAGIC, world.rand, target.x, target.y, target.z, (2.0F * modifiers.get(WizardryItems.blast_upgrade)), false).clr(brightness, brightness, brightness).spawn(world);
        }
        if (!world.isRemote) {
            double range = (WNGSpells.smoke_barrage.getProperty(EFFECT_RADIUS).floatValue() * modifiers.get(WizardryItems.blast_upgrade));
            List<EntityLivingBase> targets = EntityUtils.getLivingWithinRadius(range, target.x, target.y, target.z, world);
            int duration = (int)(WNGSpells.smoke_barrage.getProperty(EFFECT_DURATION).floatValue() * modifiers.get(WizardryItems.duration_upgrade));
            Iterator var6 = targets.iterator();

            while (var6.hasNext()) {
                EntityLivingBase targetEntity = (EntityLivingBase) var6.next();
                if (targetEntity != caster) {
                    EntityUtils.attackEntityWithoutKnockback(targetEntity, MagicDamage.causeDirectMagicDamage(caster, MagicDamage.DamageType.FIRE), getProperty(DAMAGE).floatValue() * modifiers.get(SpellModifiers.POTENCY));
                    targetEntity.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, duration, 0));
                    targetEntity.addPotionEffect(new PotionEffect(MobEffects.WITHER, duration, 2));
                }
            }
        }
        return true;
    }
    @Override
    protected boolean onMiss(World world, EntityLivingBase caster, Vec3d origin, Vec3d direction, int ticksInUse, SpellModifiers modifiers) {
        return true;
    }

    @Override
    protected void spawnParticle(World world, double x, double y, double z, double vx, double vy, double vz) {
        float brightness = world.rand.nextFloat() * 0.3F;
        ParticleBuilder.create(ParticleBuilder.Type.DARK_MAGIC).pos(x, y, z).clr(brightness, brightness, brightness).time(20 + world.rand.nextInt(10)).spawn(world);
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.spell_book_wng || item == WNGItems.scroll_wng;
    }
}