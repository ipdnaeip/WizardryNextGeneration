package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGSpells;
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
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class IceBarrage extends SpellRay {

    public static final String ICE_DURATION = "ice_duration";
    public IceBarrage() {
        super(WizardryNextGeneration.MODID, "ice_barrage", SpellActions.POINT, false);
        this.soundValues(1.0F, 7.0F, 0.1F);
        this.addProperties(DAMAGE, RANGE, EFFECT_DURATION, ICE_DURATION, EFFECT_RADIUS);
    }

    @Override
    protected boolean onEntityHit(World world, Entity target, Vec3d hit, EntityLivingBase caster, Vec3d origin, int ticksInUse, SpellModifiers modifiers) {
        if (!world.isRemote) {
            double range = (WNGSpells.ice_barrage.getProperty(EFFECT_RADIUS).floatValue() * modifiers.get(WizardryItems.blast_upgrade));
            List<EntityLivingBase> targets = EntityUtils.getLivingWithinRadius(range, target.posX, target.posY, target.posZ, world);
            int duration = (int) (WNGSpells.ice_barrage.getProperty(EFFECT_DURATION).floatValue() * modifiers.get(WizardryItems.duration_upgrade));
            Iterator var6 = targets.iterator();

            while (var6.hasNext()) {
                EntityLivingBase targetEntity = (EntityLivingBase) var6.next();
                if (targetEntity != caster) {
                    EntityUtils.attackEntityWithoutKnockback(targetEntity, MagicDamage.causeDirectMagicDamage(caster, MagicDamage.DamageType.FROST), getProperty(DAMAGE).floatValue() * modifiers.get(SpellModifiers.POTENCY));
                    if (targetEntity instanceof EntityLiving && ((BlockStatue) WizardryBlocks.ice_statue).convertToStatue((EntityLiving)targetEntity, caster, (int)(this.getProperty("ice_duration").floatValue() * modifiers.get(WizardryItems.duration_upgrade)))) {
                    }
                    targetEntity.addPotionEffect(new PotionEffect(WizardryPotions.frost, duration, 1));
                }
            }
        }
        return true;
    }

    @Override
    protected boolean onBlockHit(World world, BlockPos pos, EnumFacing side, Vec3d hit, EntityLivingBase caster, Vec3d origin, int ticksInUse, SpellModifiers modifiers) {
        Vec3d target = new Vec3d(pos);
        if (!world.isRemote) {
            double range = (WNGSpells.ice_barrage.getProperty(EFFECT_RADIUS).floatValue() * modifiers.get(WizardryItems.blast_upgrade));
            List<EntityLivingBase> targets = EntityUtils.getLivingWithinRadius(range, target.x, target.y, target.z, world);
            int duration = (int) (WNGSpells.ice_barrage.getProperty(EFFECT_DURATION).floatValue() * modifiers.get(WizardryItems.duration_upgrade));
            Iterator var6 = targets.iterator();

            while (var6.hasNext()) {
                EntityLivingBase targetEntity = (EntityLivingBase) var6.next();
                if (targetEntity != caster) {
                    EntityUtils.attackEntityWithoutKnockback(targetEntity, MagicDamage.causeDirectMagicDamage(caster, MagicDamage.DamageType.FROST), getProperty(DAMAGE).floatValue() * modifiers.get(SpellModifiers.POTENCY));
                    if (targetEntity instanceof EntityLiving && ((BlockStatue) WizardryBlocks.ice_statue).convertToStatue((EntityLiving)targetEntity, caster, (int)(this.getProperty("ice_duration").floatValue() * modifiers.get(WizardryItems.duration_upgrade)))) {
                    }
                    targetEntity.addPotionEffect(new PotionEffect(WizardryPotions.frost, duration, 1));
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
        float brightness = 0.5F + world.rand.nextFloat() * 0.5F;
        ParticleBuilder.create(ParticleBuilder.Type.SPARKLE).pos(x, y, z).time(12 + world.rand.nextInt(8)).clr(brightness, brightness + 0.1F, 1.0F).spawn(world);
        ParticleBuilder.create(ParticleBuilder.Type.SNOW).pos(x, y, z).time(20 + world.rand.nextInt(10)).spawn(world);
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.spell_book_wng || item == WNGItems.scroll_wng;
    }
}
