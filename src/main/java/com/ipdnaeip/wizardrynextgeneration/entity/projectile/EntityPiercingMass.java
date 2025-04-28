package com.ipdnaeip.wizardrynextgeneration.entity.projectile;

import com.ipdnaeip.wizardrynextgeneration.registry.WNGSpells;
import electroblob.wizardry.entity.projectile.EntityMagicArrow;
import electroblob.wizardry.spell.Spell;
import electroblob.wizardry.util.MagicDamage;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import java.util.List;

public class EntityPiercingMass extends EntityMagicArrow {

    double piercing_damage = WNGSpells.PIERCING_MASS.getProperty(Spell.DAMAGE).doubleValue();

    public EntityPiercingMass(World world) {
        super(world);
    }

    public double getDamage() {
        return piercing_damage;
    }

    @Override
    public boolean doGravity() {
        return false;
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
    public void move(MoverType type, double x, double y, double z) {
    }

    @Override
    public void aim(EntityLivingBase caster, float speed) {
        this.setCaster(caster);
        this.setLocationAndAngles(caster.posX, caster.posY + (double) caster.getEyeHeight() - 0.1, caster.posZ, caster.rotationYaw, caster.rotationPitch);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.motionX = (-MathHelper.sin(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F));
        this.motionY = (-MathHelper.sin(this.rotationPitch / 180.0F * 3.1415927F));
        this.motionZ = (MathHelper.cos(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F));
        this.shoot(this.motionX, this.motionY, this.motionZ, speed * 1.5F, 0F);
    }

    @Override
    public void onEntityHit(EntityLivingBase entityHit) {
        this.playSound(SoundEvents.BLOCK_METAL_BREAK, 0.5F, 0.5F / (this.rand.nextFloat() * 0.2F + 0.4F));

    }

    @Override
    public void onBlockHit(RayTraceResult hit) {
        this.playSound(SoundEvents.BLOCK_METAL_BREAK, 0.5F, 0.7F / (this.rand.nextFloat() * 0.2F + 0.4F));
    }

    @Override
    public void onUpdate() {

        //From Entity.onUpdate
        if (!this.world.isRemote)
        {
            this.setFlag(6, this.isGlowing());
        }
        this.onEntityUpdate();

        if (this.getLifetime() >= 0 && this.ticksExisted > this.getLifetime()) {
            this.setDead();
        }

        Vec3d vec3d1 = new Vec3d(this.posX, this.posY, this.posZ);
        Vec3d vec3d = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        RayTraceResult raytraceresult = new RayTraceResult(this);

        //Finds the closest entity to the projectile
        Entity entity = null;
        List<?> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ).grow(1.0, 1.0, 1.0));
        double d0 = 0.0;
        Entity damagesource;
        for(int i = 0; i < list.size(); ++i) {
            damagesource = (Entity)list.get(i);
            if (damagesource.canBeCollidedWith() && damagesource != this.getCaster()) {
                float f1 = 0.3F;
                AxisAlignedBB axisalignedbb1 = damagesource.getEntityBoundingBox().grow(f1, f1, f1);
                RayTraceResult RayTraceResult1 = axisalignedbb1.calculateIntercept(vec3d1, vec3d);
                if (RayTraceResult1 != null) {
                    double d1 = vec3d1.distanceTo(RayTraceResult1.hitVec);
                    if (d1 < d0 || d0 == 0.0) {
                        entity = damagesource;
                        d0 = d1;
                    }
                }
            }
        }
        if (entity != null) {
            raytraceresult = new RayTraceResult(entity);
        }
        if (raytraceresult.entityHit instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer)raytraceresult.entityHit;
            if (entityplayer.capabilities.disableDamage || this.getCaster() instanceof EntityPlayer && !((EntityPlayer)this.getCaster()).canAttackPlayer(entityplayer)) {
                raytraceresult = null;
            }
        }
        float f4;
        if (raytraceresult != null) {
            if (raytraceresult.entityHit != null) {
                DamageSource damagesource2;
                if (this.getCaster() == null) {
                    damagesource2 = DamageSource.causeThrownDamage(this, this);
                } else {
                    damagesource2 = MagicDamage.causeIndirectMagicDamage(this, this.getCaster(), this.getDamageType()).setProjectile();
                }
                if (raytraceresult.entityHit.attackEntityFrom(damagesource2, (float)(this.getDamage() * (double)this.damageMultiplier))) {
                    if (raytraceresult.entityHit instanceof EntityLivingBase) {
                        EntityLivingBase entityHit = (EntityLivingBase)raytraceresult.entityHit;
                        this.onEntityHit(entityHit);
                        if (this.getCaster() != null) {
                            EnchantmentHelper.applyThornEnchantments(entityHit, this.getCaster());
                            EnchantmentHelper.applyArthropodEnchantments(this.getCaster(), entityHit);
                        }
                        if (this.getCaster() != null && raytraceresult.entityHit != this.getCaster() && raytraceresult.entityHit instanceof EntityPlayer && this.getCaster() instanceof EntityPlayerMP) {
                            ((EntityPlayerMP)this.getCaster()).connection.sendPacket(new SPacketChangeGameState(6, 0.0F));
                        }
                    }
                }
            }
        }
        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
        float f3 = 0.99F;
        if (this.isInWater()) {
            for(int l = 0; l < 4; ++l) {
                f4 = 0.25F;
                this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * (double)f4, this.posY - this.motionY * (double)f4, this.posZ - this.motionZ * (double)f4, this.motionX, this.motionY, this.motionZ);
            }

            f3 = 0.8F;
        }
        if (this.isWet()) {
            this.extinguish();
        }
        if (this.doDeceleration()) {
            this.motionX *= f3;
            this.motionY *= f3;
            this.motionZ *= f3;
        }
        if (this.doGravity()) {
            this.motionY -= 0.05;
        }
    }

    public int getLifetime() {
        return 50;
    }


}
