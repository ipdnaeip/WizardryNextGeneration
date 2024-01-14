package com.ipdnaeip.wizardrynextgeneration.entity.living;

import com.ipdnaeip.wizardrynextgeneration.entity.ai.EntityBatFlightHelper;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGSounds;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Calendar;

public class EntityVampireBat extends EntityMob
{
    private static final DataParameter<Boolean> HANGING = EntityDataManager.createKey(EntityVampireBat.class, DataSerializers.BOOLEAN);

    public EntityVampireBat(World worldIn)
    {
        super(worldIn);
        this.moveHelper = new EntityBatFlightHelper(this);
        this.setSize(0.5F, 0.9F);
        this.setIsBatHanging(true);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityAIAttackMelee(this, this.getFlyingSpeed(), true));
        this.tasks.addTask(4, new EntityAIFlyingWander(this, this.getFlyingSpeed()));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(1, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(HANGING, true);
    }

    @Override
    protected float getSoundVolume()
    {
        return 0.1F;
    }

    @Override
    protected float getSoundPitch()
    {
        return super.getSoundPitch() * 0.5F;
    }

    @Override
    @Nullable
    public SoundEvent getAmbientSound() {
        return this.getIsBatHanging() && this.rand.nextInt(4) != 0 ? null : SoundEvents.ENTITY_BAT_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_BAT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_BAT_DEATH;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6);
        this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.05);
    }

    public double getFlyingSpeed() {
        return this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).getAttributeValue();
    }

    @Override
    protected PathNavigate createNavigator(World worldIn)
    {
        PathNavigateFlying pathnavigateflying = new PathNavigateFlying(this, worldIn);
        pathnavigateflying.setCanOpenDoors(false);
        pathnavigateflying.setCanFloat(true);
        pathnavigateflying.setCanEnterDoors(true);
        return pathnavigateflying;
    }

    public boolean getIsBatHanging()
    {
        return (this.dataManager.get(HANGING));
    }

    public void setIsBatHanging(boolean isHanging)
    {
        this.dataManager.set(HANGING, isHanging);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.setNoGravity(true);
        if (this.getIsBatHanging())
        {
            this.motionX = 0.0D;
            this.motionY = 0.0D;
            this.motionZ = 0.0D;
            this.posY = (double)MathHelper.floor(this.posY) + 1.0D - (double)this.height;
        }
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
        BlockPos blockpos = new BlockPos(this);
        BlockPos blockpos1 = blockpos.up();

        if (this.getIsBatHanging()) {
            this.moveHelper.setMoveTo(this.posX, this.posY, this.posZ, 0);
            if (this.world.getBlockState(blockpos1).isNormalCube())
            {
                if (this.rand.nextInt(200) == 0)
                {
                    this.rotationYawHead = (float)this.rand.nextInt(360);
                }

                if (this.world.getNearestPlayerNotCreative(this, 4.0D) != null)
                {
                    this.setIsBatHanging(false);
                    this.world.playEvent(null, 1025, blockpos, 0);
                }
            }
            else
            {
                this.setIsBatHanging(false);
                this.world.playEvent(null, 1025, blockpos, 0);
            }
        }
        else
        {
            if (this.rand.nextInt(100) == 0 && this.world.getBlockState(blockpos1).isNormalCube())
            {
                this.setIsBatHanging(true);
            }
        }
    }

    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
    }

    @Override
    public boolean doesEntityNotTriggerPressurePlate()
    {
        return true;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isEntityInvulnerable(source)) {
            return false;
        }
        else
        {
            if (!this.world.isRemote && this.getIsBatHanging()) {
                this.setIsBatHanging(false);
            }
            return super.attackEntityFrom(source, amount);
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn)
    {
        if (super.attackEntityAsMob(entityIn)) {
            this.playSound(WNGSounds.VAMPIRE_BAT_BITE, 1f, 0.9f + this.rand.nextFloat() + 0.2f);
            if (entityIn instanceof EntityLivingBase) {
                int i = 0;
                if (this.world.getDifficulty() == EnumDifficulty.EASY) {
                    i = 40;
                }
                if (this.world.getDifficulty() == EnumDifficulty.NORMAL) {
                    i = 80;
                }
                else if (this.world.getDifficulty() == EnumDifficulty.HARD) {
                    i = 120;
                }
                if (i > 0) {
                    ((EntityLivingBase)entityIn).addPotionEffect(new PotionEffect(WNGPotions.bleed, i, 0));
                    this.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, i, 0));
                }
            }
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.dataManager.set(HANGING, compound.getBoolean("BatFlags"));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setBoolean("BatFlags", this.dataManager.get(HANGING));
    }

    @Override
    public boolean getCanSpawnHere() {
        BlockPos blockpos = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);

        if (blockpos.getY() >= this.world.getSeaLevel())
        {
            return false;
        }
        else
        {
            int i = this.world.getLightFromNeighbors(blockpos);
            int j = 4;

            if (this.isDateAroundHalloween(this.world.getCurrentDate()))
            {
                j = 7;
            }
            else if (this.rand.nextBoolean())
            {
                return false;
            }

            return i <= this.rand.nextInt(j) && super.getCanSpawnHere();
        }
    }

    private boolean isDateAroundHalloween(Calendar cal) {
        return cal.get(Calendar.MONTH) + 1 == 10 && cal.get(Calendar.DATE) >= 20 || cal.get(Calendar.MONTH) + 1 == 11 && cal.get(Calendar.DATE) <= 3;
    }

    @Override
    public float getEyeHeight()
    {
        return this.height / 2.0F;
    }

    class EntityAIFlyingWander extends EntityAIBase
    {
        protected final EntityCreature entity;
        protected double x;
        protected double y;
        protected double z;
        protected final double speed;

        public EntityAIFlyingWander(EntityCreature creatureIn, double speedIn)
        {
            this.entity = creatureIn;
            this.speed = speedIn;
            this.setMutexBits(1);
        }

        public boolean shouldExecute()
        {
            Vec3d vec3d = this.getPosition();

            if (vec3d == null)
            {
                return false;
            }
            else
            {
                this.x = vec3d.x;
                this.y = vec3d.y;
                this.z = vec3d.z;
                return true;
            }
        }

        @Nullable
        protected Vec3d getPosition()
        {
            //return RandomPositionGenerator.findRandomTarget(this.entity, 10, 7);
            return new Vec3d(this.entity.posX + this.entity.world.rand.nextGaussian() * 7, this.entity.posY + this.entity.world.rand.nextFloat() * 6 - 2, this.entity.posZ + this.entity.world.rand.nextGaussian() * 7);
        }

        public boolean shouldContinueExecuting()
        {
            return !this.entity.getNavigator().noPath();
        }

        public void startExecuting()
        {
            this.entity.getNavigator().tryMoveToXYZ(this.x, this.y, this.z, this.speed);
        }

    }
}
