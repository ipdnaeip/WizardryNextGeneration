package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGSpells;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.spell.SpellRay;
import electroblob.wizardry.util.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class Veneficium extends SpellRay {

    public Veneficium() {
        super(WizardryNextGeneration.MODID, "veneficium", SpellActions.POINT, false);
        this.soundValues(1F, 0.7F, 0.1F);
        this.addProperties(EFFECT_DURATION, EFFECT_RADIUS);
    }

    @Override
    protected boolean onEntityHit(World world, Entity target, Vec3d hit, EntityLivingBase caster, Vec3d origin, int ticksInUse, SpellModifiers modifiers) {
        if (world.isRemote) {
            for (int i = 0; (float) i < 60.0F * this.getProperty(EFFECT_RADIUS).floatValue(); ++i) {
            ParticleBuilder.create(ParticleBuilder.Type.CLOUD, world.rand, target.posX, target.posY + target.getEyeHeight() - 0.5F, target.posZ, (2.0F * modifiers.get(WizardryItems.blast_upgrade)), false).clr(0, 100, 0).fade(0, 170, 0).time(10 + world.rand.nextInt(10)).shaded(true).spawn(world);
            }
        }
        if (!world.isRemote) {
            double range = (WNGSpells.veneficium.getProperty(EFFECT_RADIUS).floatValue() * modifiers.get(WizardryItems.blast_upgrade));
            List<EntityLivingBase> targets = EntityUtils.getLivingWithinRadius(range, target.posX, target.posY, target.posZ, world);
            int duration = (int)(WNGSpells.veneficium.getProperty(EFFECT_DURATION).floatValue() * modifiers.get(WizardryItems.duration_upgrade));
            for (EntityLivingBase targetEntity : targets) {
                if (AllyDesignationSystem.isValidTarget(caster, targetEntity)) {
                    targetEntity.addPotionEffect(new PotionEffect(WNGPotions.veneficium, duration, (int) ((modifiers.get("potency") - 1) * 3.5)));
                }
            }
        }

        return true;
    }
    @Override
    protected boolean onBlockHit(World world, BlockPos pos, EnumFacing side, Vec3d hit, EntityLivingBase caster, Vec3d origin, int ticksInUse, SpellModifiers modifiers) {
        Vec3d target = new Vec3d(pos);
        for (int i = 0; (float) i < 60.0F * this.getProperty(EFFECT_RADIUS).floatValue(); ++i) {
            ParticleBuilder.create(ParticleBuilder.Type.CLOUD, world.rand, target.x, target.y, target.z, (2.0F * modifiers.get(WizardryItems.blast_upgrade)), false).clr(0, 100, 0).fade(0, 170, 0).time(10 + world.rand.nextInt(20)).shaded(true).spawn(world);
            if (!world.isRemote) {
                double range = (WNGSpells.veneficium.getProperty(EFFECT_RADIUS).floatValue() * modifiers.get(WizardryItems.blast_upgrade));
                List<EntityLivingBase> targets = EntityUtils.getLivingWithinRadius(range, target.x, target.y, target.z, world);
                int duration = (int)(WNGSpells.veneficium.getProperty(EFFECT_DURATION).floatValue() * modifiers.get(WizardryItems.duration_upgrade));
                for (EntityLivingBase targetEntity : targets) {
                    if (AllyDesignationSystem.isValidTarget(caster, targetEntity)) {
                        targetEntity.addPotionEffect(new PotionEffect(WNGPotions.veneficium, duration, (int) ((modifiers.get("potency") - 1) * 3.5)));
                    }
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
        ParticleBuilder.create(ParticleBuilder.Type.DARK_MAGIC).pos(x, y, z).clr(0, 170, 0).time(20 + world.rand.nextInt(20)).spawn(world);
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.spell_book_wng || item == WNGItems.scroll_wng;
    }
}
