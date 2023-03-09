package com.ipdnaeip.wizardrynextgeneration.entity.projectile;

import com.ipdnaeip.wizardrynextgeneration.registry.WNGSpells;
import electroblob.wizardry.entity.projectile.EntityMagicArrow;
import electroblob.wizardry.util.AllyDesignationSystem;
import electroblob.wizardry.util.EntityUtils;
import electroblob.wizardry.util.MagicDamage;
import electroblob.wizardry.util.RayTracer;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.UUID;

public class EntityPiercingMass extends EntityMagicArrow {

    public static final double LAUNCH_Y_OFFSET = 0.1;
    public static final int SEEKING_TIME = 15;
    private int blockX = -1;
    private int blockY = -1;
    private int blockZ = -1;
    private IBlockState stuckInBlock;
    private int inData;
    private boolean inGround;
    public int arrowShake;
    private WeakReference<EntityLivingBase> caster;
    private UUID casterUUID;
    int ticksInGround;
    int ticksInAir;
    private int knockbackStrength;
    double piercing_damage = WNGSpells.piercing_mass.getProperty("damage").doubleValue();

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
    public void onEntityHit(EntityLivingBase entityHit) {
        this.playSound(SoundEvents.BLOCK_METAL_BREAK, 0.5F, 0.5F / (this.rand.nextFloat() * 0.2F + 0.4F));

    }

    @Override
    public void onBlockHit(RayTraceResult hit) {
        this.playSound(SoundEvents.BLOCK_METAL_BREAK, 0.5F, 0.7F / (this.rand.nextFloat() * 0.2F + 0.4F));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.getLifetime() >= 0 && this.ticksExisted > this.getLifetime()) {
            this.setDead();
        }

        if (this.getCaster() == null && this.casterUUID != null) {
            Entity entity = EntityUtils.getEntityByUUID(this.world, this.casterUUID);
            if (entity instanceof EntityLivingBase) {
                this.caster = new WeakReference((EntityLivingBase)entity);
            }
        }

        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
            float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0 / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f) * 180.0 / Math.PI);
        }

        if (this.arrowShake > 0) {
            --this.arrowShake;
        }
            ++this.ticksInAir;
            Vec3d vec3d1 = new Vec3d(this.posX, this.posY, this.posZ);
            Vec3d vec3d = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            RayTraceResult raytraceresult = this.world.rayTraceBlocks(vec3d1, vec3d, false, true, false);
            vec3d1 = new Vec3d(this.posX, this.posY, this.posZ);
            vec3d = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            if (raytraceresult != null) {
                vec3d = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
            }

            Entity entity = null;
            List<?> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ).grow(1.0, 1.0, 1.0));
            double d0 = 0.0;

            Entity damagesource;
            for(int i = 0; i < list.size(); ++i) {
                damagesource = (Entity)list.get(i);
                if (damagesource.canBeCollidedWith() && (damagesource != this.getCaster() || this.ticksInAir >= 5)) {
                    float f1 = 0.3F;
                    AxisAlignedBB axisalignedbb1 = damagesource.getEntityBoundingBox().grow((double)f1, (double)f1, (double)f1);
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

            if (raytraceresult != null && raytraceresult.entityHit != null && raytraceresult.entityHit instanceof EntityPlayer) {
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
                            if (this.knockbackStrength > 0) {
                                f4 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
                                if (f4 > 0.0F) {
                                    raytraceresult.entityHit.addVelocity(this.motionX * (double)this.knockbackStrength * 0.6000000238418579 / (double)f4, 0.1, this.motionZ * (double)this.knockbackStrength * 0.6000000238418579 / (double)f4);
                                }
                            }

                            if (this.getCaster() != null) {
                                EnchantmentHelper.applyThornEnchantments(entityHit, this.getCaster());
                                EnchantmentHelper.applyArthropodEnchantments(this.getCaster(), entityHit);
                            }

                            if (this.getCaster() != null && raytraceresult.entityHit != this.getCaster() && raytraceresult.entityHit instanceof EntityPlayer && this.getCaster() instanceof EntityPlayerMP) {
                                ((EntityPlayerMP)this.getCaster()).connection.sendPacket(new SPacketChangeGameState(6, 0.0F));
                            }
                        }
                    }

                } else {
                    this.blockX = raytraceresult.getBlockPos().getX();
                    this.blockY = raytraceresult.getBlockPos().getY();
                    this.blockZ = raytraceresult.getBlockPos().getZ();
                    this.motionX = (double)((float)(raytraceresult.hitVec.x - this.posX));
                    this.motionY = (double)((float)(raytraceresult.hitVec.y - this.posY));
                    this.motionZ = (double)((float)(raytraceresult.hitVec.z - this.posZ));
                    this.arrowShake = 7;
                    this.onBlockHit(raytraceresult);
                }
            }

            if (this.getSeekingStrength() > 0.0F) {
                Vec3d velocity = new Vec3d(this.motionX, this.motionY, this.motionZ);
                RayTraceResult hit = RayTracer.rayTrace(this.world, this.getPositionVector(), this.getPositionVector().add(velocity.scale(15.0)), this.getSeekingStrength(), false, true, false, EntityLivingBase.class, RayTracer.ignoreEntityFilter((Entity)null));
                if (hit != null && hit.entityHit != null && AllyDesignationSystem.isValidTarget(this.getCaster(), hit.entityHit)) {
                    Vec3d direction = (new Vec3d(hit.entityHit.posX, hit.entityHit.posY + (double)(hit.entityHit.height / 2.0F), hit.entityHit.posZ)).subtract(this.getPositionVector()).normalize().scale(velocity.length());
                    this.motionX += 2.0 * (direction.x - this.motionX) / 15.0;
                    this.motionY += 2.0 * (direction.y - this.motionY) / 15.0;
                    this.motionZ += 2.0 * (direction.z - this.motionZ) / 15.0;
                }
            }

            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;

            for(this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0 / Math.PI); this.rotationPitch - this.prevRotationPitch >= 180.0F; this.prevRotationPitch += 360.0F) {
            }

            while(this.rotationYaw - this.prevRotationYaw < -180.0F) {
                this.prevRotationYaw -= 360.0F;
            }

            while(this.rotationYaw - this.prevRotationYaw >= 180.0F) {
                this.prevRotationYaw += 360.0F;
            }

            this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
            this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
            float f3 = 0.99F;
            if (this.isInWater()) {
                for(int l = 0; l < 4; ++l) {
                    f4 = 0.25F;
                    this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * (double)f4, this.posY - this.motionY * (double)f4, this.posZ - this.motionZ * (double)f4, this.motionX, this.motionY, this.motionZ, new int[0]);
                }

                f3 = 0.8F;
            }

            if (this.isWet()) {
                this.extinguish();
            }

            if (this.doDeceleration()) {
                this.motionX *= (double)f3;
                this.motionY *= (double)f3;
                this.motionZ *= (double)f3;
            }

            if (this.doGravity()) {
                this.motionY -= 0.05;
            }

            this.setPosition(this.posX, this.posY, this.posZ);
            this.doBlockCollisions();
        }



    protected void entityInit() {
    }

    public int getLifetime() {
            return 50;
    }


}
