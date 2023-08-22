package com.ipdnaeip.wizardrynextgeneration.entity.projectile;

import com.ipdnaeip.wizardrynextgeneration.registry.WNGSpells;
import electroblob.wizardry.entity.projectile.EntityMagicArrow;
import electroblob.wizardry.util.ParticleBuilder;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityFissionBlast extends EntityMagicArrow {

    double fission_damage = WNGSpells.fission_blast.getProperty("damage").doubleValue();

    public EntityFissionBlast(World world) {
        super(world);
        this.setSize(0.2F, 0.2F);

        }

    @Override
    public boolean doGravity() {
        return false;
    }

    public double getDamage() {
            return fission_damage;
        }

    @Override
    public void aim(EntityLivingBase caster, float speed) {
        this.setCaster(caster);
        this.setLocationAndAngles(caster.posX, caster.posY + (double) caster.getEyeHeight() - 0.1, caster.posZ, caster.rotationYaw, caster.rotationPitch);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.motionX = (-MathHelper.sin(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F));
        this.motionY = (-MathHelper.sin(this.rotationPitch / 180.0F * 3.1415927F));
        this.motionZ = (MathHelper.cos(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F));
        this.shoot(this.motionX, this.motionY, this.motionZ, speed * 1.5F, (float) (Math.random() * 3F) + 3F);
    }

    @Override
    public void onEntityHit(EntityLivingBase entityHit) {
        entityHit.hurtResistantTime = 0;
        this.playSound(SoundEvents.BLOCK_METAL_BREAK, 1F, 1F / (this.rand.nextFloat() * 0.2F + 0.4F));
    }

    @Override
    public void onBlockHit(RayTraceResult hit) {
        this.playSound(SoundEvents.BLOCK_METAL_BREAK, 1F, 1F / (this.rand.nextFloat() * 0.2F + 0.4F));
    }

    public int getLifetime() {
            return 10;
    }

}
