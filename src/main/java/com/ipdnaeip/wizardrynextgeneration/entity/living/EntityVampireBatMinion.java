package com.ipdnaeip.wizardrynextgeneration.entity.living;

import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGSounds;
import electroblob.wizardry.Wizardry;
import electroblob.wizardry.entity.living.ISummonedCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import java.util.UUID;

public class EntityVampireBatMinion extends EntityVampireBat implements ISummonedCreature {

    private int lifetime = -1;
    private UUID casterUUID;
    public float multiplier = 1;

    public EntityVampireBatMinion(World world) {
        super(world);
        this.experienceValue = 0;
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityAIAttackMelee(this, this.getFlyingSpeed(), true));
        this.tasks.addTask(4, new EntityAIFlyingWander(this, this.getFlyingSpeed()));
        this.tasks.addTask(2, new EntityAIFollowOwner(this, 0, 10));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(1, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityLivingBase.class, 0, false, true, this.getTargetSelector()));
    }

    @Override
    public int getLifetime() {
        return this.lifetime;
    }

    @Override
    public void setLifetime(int lifetime) {
        this.lifetime = lifetime;
    }

    @Override
    public UUID getOwnerId() {
        return this.casterUUID;
    }

    @Override
    public void setOwnerId(UUID uuid) {
        this.casterUUID = uuid;
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
    public void onSuccessfulAttack(EntityLivingBase entityIn)
    {
        this.playSound(WNGSounds.VAMPIRE_BAT_BITE, 1f, 0.9f + this.rand.nextFloat() + 0.2f);
        int i = MathHelper.ceil(40 * multiplier);
        entityIn.addPotionEffect(new PotionEffect(WNGPotions.bleed, i, 0));
        this.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, i, 0));
    }

    @Override
    public void onSpawn() {
        this.spawnParticleEffect();
    }

    @Override
    public void onDespawn() {
        this.spawnParticleEffect();
    }

    private void spawnParticleEffect() {
        if (this.world.isRemote) {
            for(int i = 0; i < 15; ++i) {
                this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + (double)this.rand.nextFloat() - 0.5, this.posY + (double)(this.rand.nextFloat() * 2.0F), this.posZ + (double)this.rand.nextFloat() - 0.5, 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    public boolean hasParticleEffect() {
        return true;
    }

    @Override
    protected boolean processInteract(EntityPlayer player, EnumHand hand) {
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
    protected int getExperiencePoints(EntityPlayer player) {
        return 0;
    }

    @Override
    protected boolean canDropLoot() {
        return false;
    }

    @Override
    protected Item getDropItem() {
        return null;
    }

    @Override
    protected ResourceLocation getLootTable() {
        return null;
    }

    @Override
    public boolean canPickUpLoot() {
        return false;
    }

    @Override
    protected boolean canDespawn() {
        return this.getCaster() == null && this.getOwnerId() == null;
    }

    @Override
    public boolean getCanSpawnHere() {
        return this.world.getDifficulty() != EnumDifficulty.PEACEFUL;
    }

    @Override
    public boolean canAttackClass(Class<? extends EntityLivingBase> entityType) {
        return true;
    }

    @Override
    public ITextComponent getDisplayName() {
        return (this.getCaster() != null ? new TextComponentTranslation("entity.ebwizardry:summonedcreature.nameplate", this.getCaster().getName(), new TextComponentTranslation("entity." + this.getEntityString() + ".name")) : super.getDisplayName());
    }

    @Override
    public boolean hasCustomName() {
        return Wizardry.settings.summonedCreatureNames && this.getCaster() != null;
    }

    class EntityAIFollowOwner extends EntityAIBase
    {
        private final EntityVampireBatMinion bat;
        private EntityLivingBase owner;
        World world;
        private final PathNavigate petPathfinder;
        float maxDist;
        float minDist;

        public EntityAIFollowOwner(EntityVampireBatMinion bat, float minDistIn, float maxDistIn)
        {
            this.bat = bat;
            this.world = bat.world;
            this.petPathfinder = bat.getNavigator();
            this.minDist = minDistIn;
            this.maxDist = maxDistIn;
            this.setMutexBits(3);
        }

        public boolean shouldExecute() {
            if (this.bat.getOwner() instanceof EntityLivingBase) {
                EntityLivingBase owner = (EntityLivingBase)this.bat.getOwner();
                if (owner == null) {
                    return false;
                } else if (this.bat.getDistanceSq(owner) < (double) (this.minDist * this.minDist)) {
                    return false;
                } else {
                    this.owner = owner;
                    return true;
                }
            }
            return false;
        }

        public boolean shouldContinueExecuting()
        {
            return !this.petPathfinder.noPath() && this.bat.getDistanceSq(this.owner) > (double)(this.maxDist * this.maxDist);
        }

        public void resetTask()
        {
            this.owner = null;
        }
    }

}
