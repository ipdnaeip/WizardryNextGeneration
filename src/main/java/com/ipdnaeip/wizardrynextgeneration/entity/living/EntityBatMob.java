package com.ipdnaeip.wizardrynextgeneration.entity.living;

import com.ipdnaeip.wizardrynextgeneration.entity.ai.EntityFlightHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Calendar;

public class EntityBatMob extends EntityMob
{
    private static final DataParameter<Boolean> HANGING = EntityDataManager.createKey(EntityBatMob.class, DataSerializers.BOOLEAN);

    public EntityBatMob(World worldIn)
    {
        super(worldIn);
        this.moveHelper = new EntityFlightHelper(this);
        this.setSize(0.5F, 0.9F);
        this.setIsBatHanging(true);
    }

    @Override
    protected void initEntityAI()
    {
        super.initEntityAI();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityAIAttackMelee(this, 1, true));
//        this.tasks.addTask(8, new EntityBatMob.AIMoveRandom(this, 0.25));
        this.tasks.addTask(8, new EntityBatMob.AIMoveRandom());
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
        return super.getSoundPitch() * 0.95F;
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
    public boolean canBePushed()
    {
        return false;
    }

    @Override
    protected void collideWithEntity(Entity entityIn) {
    }

    @Override
    protected void collideWithNearbyEntities() {
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10);
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
        if (this.isEntityInvulnerable(source))
        {
            return false;
        }
        else
        {
            if (!this.world.isRemote && this.getIsBatHanging())
            {
                this.setIsBatHanging(false);
            }

            return super.attackEntityFrom(source, amount);
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn)
    {
        return entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float) this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.dataManager.set(HANGING, compound.getBoolean("BatFlags"));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
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
    
/*    class AIMoveRandom extends EntityAIBase
    {
        EntityCreature creature;
        BlockPos blockPos;
        double speed;

        public AIMoveRandom(EntityCreature creature, double speed) {
            this.setMutexBits(1);
            this.creature = creature;
            this.blockPos = new BlockPos(creature);
            this.speed = speed;
        }

        @Override
        public boolean shouldExecute() {
            return EntityBatMob.this.rand.nextInt(30) == 0 || this.blockPos.distanceSq((int)creature.posX, (int)creature.posY, (int)creature.posZ) < 4.0D;
        }

        @Override
        public boolean shouldContinueExecuting() {
            return !EntityBatMob.this.getMoveHelper().isUpdating();
        }

        @Override
        public void updateTask() {
            blockPos = getRandomPosition();
            if (EntityBatMob.this.world.isAirBlock(blockPos)) {
                EntityBatMob.this.moveHelper.setMoveTo((double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 0.5D, (double) blockPos.getZ() + 0.5D, this.speed);
                this.creature.getNavigator().tryMoveToXYZ((double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 0.5D, (double) blockPos.getZ() + 0.5D, this.speed);
                if (EntityBatMob.this.getAttackTarget() == null) {
                    EntityBatMob.this.getLookHelper().setLookPosition((double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 0.5D, (double) blockPos.getZ() + 0.5D, 180.0F, 20.0F);
                }
            }
        }

        public BlockPos getRandomPosition() {
            return blockPos.add(EntityBatMob.this.rand.nextInt(15) - 7, EntityBatMob.this.rand.nextInt(11) - 5, EntityBatMob.this.rand.nextInt(15) - 7);
        }
    }*/

    class AIMoveRandom extends EntityAIBase
    {
        public AIMoveRandom()
        {
            this.setMutexBits(1);
        }

        public boolean shouldExecute()
        {
            return !EntityBatMob.this.getMoveHelper().isUpdating() && EntityBatMob.this.rand.nextInt(7) == 0;
        }

        public boolean shouldContinueExecuting()
        {
            return false;
        }

        public void updateTask()
        {
            BlockPos blockpos = EntityBatMob.this.getPosition();

            for (int i = 0; i < 3; ++i)
            {
                BlockPos blockpos1 = blockpos.add(EntityBatMob.this.rand.nextInt(15) - 7, EntityBatMob.this.rand.nextInt(11) - 5, EntityBatMob.this.rand.nextInt(15) - 7);

                if (EntityBatMob.this.world.isAirBlock(blockpos1))
                {
                    EntityBatMob.this.moveHelper.setMoveTo((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 0.25D);

                    if (EntityBatMob.this.getAttackTarget() == null)
                    {
                        EntityBatMob.this.getLookHelper().setLookPosition((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 180.0F, 20.0F);
                    }
                    break;
                }
            }
        }
    }
}
