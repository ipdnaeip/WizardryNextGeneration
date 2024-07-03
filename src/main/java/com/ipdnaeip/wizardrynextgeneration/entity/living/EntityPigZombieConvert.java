package com.ipdnaeip.wizardrynextgeneration.entity.living;

import electroblob.wizardry.Wizardry;
import electroblob.wizardry.entity.living.ISummonedCreature;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import java.util.UUID;

public class EntityPigZombieConvert extends EntityPigZombie implements ISummonedCreature {

    private static final DataParameter<Boolean> SPAWN_PARTICLES = EntityDataManager.createKey(EntityPigZombieConvert.class, DataSerializers.BOOLEAN);

    private int lifetime = -1;
    private UUID casterUUID;

    public EntityPigZombieConvert(World world) {
        super(world);
    }

    @Override
    public int getLifetime() { return lifetime; }

    @Override
    public void setLifetime(int lifetime) { this.lifetime = lifetime; }

    @Override
    public UUID getOwnerId() { return casterUUID; }

    @Override
    public void setOwnerId(UUID uuid) { this.casterUUID = uuid; }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(SPAWN_PARTICLES, true);
    }

    @Override
    protected void applyEntityAI() {
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityLivingBase.class,
                0, false, true, this.getTargetSelector()));
    }

    @Override
    public boolean isChild() { return false; }

    @Override
    public void setChild(boolean childZombie) {
    }

    @Override
    public void onKillEntity(EntityLivingBase entityLivingIn) {
    }

    @Override
    public void setChildSize(boolean isChild) {
    }

    @Override
    public boolean shouldRevengeTarget(EntityLivingBase entity) {
        return true;
    }

    @Override
    public void setRevengeTarget(EntityLivingBase entity) {
        if (this.shouldRevengeTarget(entity)) {
            super.setRevengeTarget(entity);
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.updateDelegate();
    }

    @Override
    public void onSpawn() {
        if (this.dataManager.get(SPAWN_PARTICLES)) { this.spawnParticleEffect(); }
    }

    public void onDespawn() {
    }

    private void spawnParticleEffect() {
        if (this.world.isRemote) {
            for (int i = 0; i < 15; i++) {
                this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + this.rand.nextFloat() - 0.5f,
                        this.posY + this.rand.nextFloat() * 2, this.posZ + this.rand.nextFloat() - 0.5f, 0, 0, 0);
            }
        }
    }

    @Override
    public boolean hasParticleEffect() {
        return false;
    }

    @Override
    public boolean hasAnimation() {
        return this.dataManager.get(SPAWN_PARTICLES) || this.ticksExisted > 20;
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        return this.interactDelegate(player, hand) || super.processInteract(player, hand);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        this.writeNBTDelegate(nbttagcompound);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        this.readNBTDelegate(nbttagcompound);
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    public boolean getCanSpawnHere() {
        return this.world.getDifficulty() != EnumDifficulty.PEACEFUL;
    }

    @Override
    public boolean canAttackClass(Class<? extends EntityLivingBase> entityType) {
        return !EntityFlying.class.isAssignableFrom(entityType);
    }

    @Override
    public ITextComponent getDisplayName() {
        if (getCaster() != null) {
            return new TextComponentTranslation(NAMEPLATE_TRANSLATION_KEY, getCaster().getName(),
                    new TextComponentTranslation("entity." + this.getEntityString() + ".name"));
        } else {
            return super.getDisplayName();
        }
    }

    @Override
    public boolean hasCustomName() {
        return Wizardry.settings.summonedCreatureNames && getCaster() != null;
    }
}