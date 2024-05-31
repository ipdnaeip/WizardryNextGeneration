package com.ipdnaeip.wizardrynextgeneration.entity.living;

import com.google.common.base.Predicate;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.Wizardry;
import electroblob.wizardry.entity.living.ISummonedCreature;
import electroblob.wizardry.registry.WizardrySounds;
import electroblob.wizardry.util.AllyDesignationSystem;
import java.util.Arrays;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityRighteousDefender extends EntityCreature {
    public final double AISpeed = 0.5;
    protected Predicate<Entity> targetSelector;

    public EntityRighteousDefender(World world) {
        super(world);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAIAttackMelee(this, this.AISpeed, true));
        this.tasks.addTask(2, new EntityAIWander(this, this.AISpeed));
        this.tasks.addTask(3, new EntityAILookIdle(this));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
        this.targetSelector = (entity) -> entity != null && !entity.isInvisible() && AllyDesignationSystem.isValidTarget(this, entity) && (entity instanceof IMob || entity instanceof ISummonedCreature || Arrays.asList(Wizardry.settings.summonedCreatureTargetsWhitelist).contains(EntityList.getKey(entity.getClass()))) && !Arrays.asList(Wizardry.settings.summonedCreatureTargetsBlacklist).contains(EntityList.getKey(entity.getClass()));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(0, new EntityAINearestAttackableTarget<>(this, EntityLiving.class, 0, false, true, this.targetSelector));
    }

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata){
        this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.CHAINMAIL_HELMET));
        this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.CHAINMAIL_CHESTPLATE));
        this.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(Items.CHAINMAIL_LEGGINGS));
        this.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(Items.CHAINMAIL_BOOTS));
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
        this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        return livingdata;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.AISpeed);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(12);
    }

    @Override
    protected float getSoundPitch()
    {
        return super.getSoundPitch() * 0.75F;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return WizardrySounds.ENTITY_WIZARD_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return WizardrySounds.ENTITY_WIZARD_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return WizardrySounds.ENTITY_WIZARD_DEATH;
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn)
    {
        return entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float) this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.updateArmSwingProgress();
        if (this.ticksExisted % 50 == 1) {
            this.addPotionEffect(new PotionEffect(WNGPotions.taunt, 50, 0, false, false));
        }
    }
}
