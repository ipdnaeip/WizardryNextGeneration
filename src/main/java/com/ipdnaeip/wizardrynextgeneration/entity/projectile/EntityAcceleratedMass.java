package com.ipdnaeip.wizardrynextgeneration.entity.projectile;

import com.ipdnaeip.wizardrynextgeneration.registry.WNGSpells;
import electroblob.wizardry.entity.projectile.EntityMagicArrow;
import electroblob.wizardry.util.ParticleBuilder;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityAcceleratedMass extends EntityMagicArrow {

    double accelerated_damage = WNGSpells.accelerated_mass.getProperty("damage").doubleValue();

    public EntityAcceleratedMass(World world) {
            super(world);
        }

    public double getDamage() {
            return accelerated_damage;
        }

    @Override
    public boolean doGravity() {
            return true;
        }

    @Override
    public boolean doDeceleration() {
            return false;
        }

    @Override
    public boolean doOverpenetration() {
        return true;
    }

    @Override
    public void onEntityHit(EntityLivingBase entityHit) {
        ParticleBuilder.create(ParticleBuilder.Type.DUST, this).time(10 + this.rand.nextInt(5)).spawn(this.world);
            this.playSound(SoundEvents.BLOCK_ANVIL_PLACE, 0.5F, 0.5F / (this.rand.nextFloat() * 0.2F + 0.4F));
    }

    @Override
    public void onBlockHit(RayTraceResult hit) {
        ParticleBuilder.create(ParticleBuilder.Type.DUST, this).time(10 + this.rand.nextInt(5)).spawn(this.world);
            this.playSound(SoundEvents.BLOCK_ANVIL_LAND, 0.5F, 0.7F / (this.rand.nextFloat() * 0.2F + 0.4F));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.world.isRemote) {
            this.motionX *= 1.01;
            this.motionY *= 1.01;
            this.motionZ *= 1.01;
            accelerated_damage = Math.min(accelerated_damage * 1.1, 10);
            ParticleBuilder.create(ParticleBuilder.Type.DUST, this).time(10 + this.rand.nextInt(5)).spawn(this.world);
        }
    }

    protected void entityInit() {
    }

    public int getLifetime() {
            return -1;
    }


}
