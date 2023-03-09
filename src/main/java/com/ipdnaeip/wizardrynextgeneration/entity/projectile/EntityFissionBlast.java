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
        this.posX -= (MathHelper.cos(this.rotationYaw / 180.0F * 3.1415927F) * 0.16F);
        this.posY -= 0.10000000149011612;
        this.posZ -= (MathHelper.sin(this.rotationYaw / 180.0F * 3.1415927F) * 0.16F);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.motionX = (-MathHelper.sin(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F));
        this.motionY = (-MathHelper.sin(this.rotationPitch / 180.0F * 3.1415927F));
        this.motionZ = (MathHelper.cos(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F));
        this.shoot(this.motionX, this.motionY, this.motionZ, speed * 1.5F, (float) (Math.random() * 10F));
    }

    @Override
    public void onEntityHit(EntityLivingBase entityHit) {
        this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1F, 1F / (this.rand.nextFloat() * 0.2F + 0.4F));
    }

    @Override
    public void onBlockHit(RayTraceResult hit) {
        this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1F, 1F / (this.rand.nextFloat() * 0.2F + 0.4F));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
    }

    protected void entityInit() {
    }

    public int getLifetime() {
            return 50;
    }


}
