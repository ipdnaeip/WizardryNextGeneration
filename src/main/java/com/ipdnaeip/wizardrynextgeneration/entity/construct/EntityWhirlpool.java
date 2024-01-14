package com.ipdnaeip.wizardrynextgeneration.entity.construct;

import com.ipdnaeip.wizardrynextgeneration.registry.WNGSpells;
import com.ipdnaeip.wizardrynextgeneration.util.WNGUtils;
import electroblob.wizardry.entity.construct.EntityScaledConstruct;
import electroblob.wizardry.util.EntityUtils;
import electroblob.wizardry.util.MagicDamage;
import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityWhirlpool extends EntityScaledConstruct {

    public EntityWhirlpool(World world) {
        super(world);
        this.setSize(WNGSpells.whirlpool.getProperty("effect_radius").floatValue() * 2.0F, 0F);
    }

    public void onUpdate() {
        if (this.ticksExisted % 20 == 0) {
            this.playSound(SoundEvents.BLOCK_WATER_AMBIENT, 5F, 1.0F);
        }
        super.onUpdate();
        if (!this.world.isRemote) {
            List<EntityLivingBase> targets = WNGUtils.getEntitiesWithinCylinder((this.width / 2.0F), this.posX, this.posY, this.posZ, 0.5f, this.world, EntityLivingBase.class);
            for (EntityLivingBase target : targets) {
                if (this.isValidTarget(target)) {
                    if (this.ticksExisted % 10 == 0) {
                        EntityUtils.attackEntityWithoutKnockback(target, DamageSource.DROWN, WNGSpells.whirlpool.getProperty("damage").floatValue() * this.damageMultiplier);
                    }
                    double dx = (this.posX - target.posX) / (this.getPositionVector().distanceTo(target.getPositionVector()) * 20) * this.damageMultiplier;
                    double dz = (this.posZ - target.posZ) / (this.getPositionVector().distanceTo(target.getPositionVector()) * 20) * this.damageMultiplier;
                    target.motionX += dx;
                    target.motionZ += dz;
                }
            }
        } else {
            double radius = (0.5 + this.rand.nextDouble() * 0.3) * (double)this.width / 2.0;
            float angle = this.rand.nextFloat() * 3.1415927F * 2.0F;
            world.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + radius * (double) MathHelper.cos(angle), this.posY, this.posZ + radius * (double)MathHelper.sin(angle), 0, 0.1f ,0);
        }
    }

}

