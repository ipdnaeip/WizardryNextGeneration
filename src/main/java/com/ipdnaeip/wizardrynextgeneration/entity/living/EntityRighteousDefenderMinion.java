package com.ipdnaeip.wizardrynextgeneration.entity.living;

import com.google.common.base.Predicate;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.Wizardry;
import electroblob.wizardry.entity.living.EntityWizard;
import electroblob.wizardry.entity.living.ISummonedCreature;
import electroblob.wizardry.util.AllyDesignationSystem;
import electroblob.wizardry.util.ParticleBuilder;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.UUID;

public class EntityRighteousDefenderMinion extends EntityRighteousDefender implements ISummonedCreature {

    // Field implementations
    protected Predicate<Entity> targetSelector;
    private int lifetime = -1;
    private UUID casterUUID;
    public int taunt_strength = 0;

    // Setter + getter implementations
    @Override
    public int getLifetime(){ return lifetime; }

    @Override
    public void setLifetime(int lifetime){ this.lifetime = lifetime; }

    @Override
    public UUID getOwnerId(){ return casterUUID; }

    @Override
    public void setOwnerId(UUID uuid){ this.casterUUID = uuid; }

    /** Creates a new webspitter minion in the given world. */

    public EntityRighteousDefenderMinion(World world){
        super(world);
        this.experienceValue = 0;
    }

    // Implementations

    @Override
    public void setRevengeTarget(EntityLivingBase entity){
        if(this.shouldRevengeTarget(entity)) super.setRevengeTarget(entity);
    }

    @Override
    public void onUpdate(){
        super.onUpdate();
        this.updateDelegate();
        this.addPotionEffect(new PotionEffect(WNGPotions.taunt, 20, taunt_strength));
    }

    @Override
    public void onSpawn(){
        this.spawnParticleEffect();
    }

    @Override
    public void onDespawn(){
        this.spawnParticleEffect();
    }

    private void spawnParticleEffect(){
        if(this.world.isRemote){
            for(int i = 0; i < 15; i++){
                ParticleBuilder.create(ParticleBuilder.Type.DARK_MAGIC)
                        .pos(this.posX + this.rand.nextFloat(), this.posY + this.rand.nextFloat(), this.posZ + this.rand.nextFloat())
                        .clr(0.1f, 0.2f, 0.0f)
                        .spawn(world);
            }
        }
    }

    @Override
    public boolean hasParticleEffect(){
        return true;
    }

    @Override
    protected boolean processInteract(EntityPlayer player, EnumHand hand){
        return this.interactDelegate(player, hand) || super.processInteract(player, hand);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound){
        super.writeEntityToNBT(nbttagcompound);
        this.writeNBTDelegate(nbttagcompound);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound){
        super.readEntityFromNBT(nbttagcompound);
        this.readNBTDelegate(nbttagcompound);
    }

    // Recommended overrides

    @Override
    protected int getExperiencePoints(EntityPlayer player){ return 0; }

    @Override
    protected boolean canDropLoot(){ return false; }

    @Override
    protected Item getDropItem(){ return null; }

    @Override
    protected ResourceLocation getLootTable(){ return null; }

    @Override
    public boolean canPickUpLoot(){ return false; }

    // This vanilla method has nothing to do with the custom despawn() method.
    @Override
    protected boolean canDespawn(){
        return getCaster() == null && getOwnerId() == null;
    }

    @Override
    public boolean getCanSpawnHere(){
        return this.world.getDifficulty() != EnumDifficulty.PEACEFUL;
    }

    @Override
    public boolean canAttackClass(Class<? extends EntityLivingBase> entityType){
        // Returns true unless the given entity type is a flying entity.
        return !EntityFlying.class.isAssignableFrom(entityType);
    }

    @Override
    public ITextComponent getDisplayName(){
        if(getCaster() != null){
            return new TextComponentTranslation(NAMEPLATE_TRANSLATION_KEY, getCaster().getName(),
                    new TextComponentTranslation("entity." + this.getEntityString() + ".name"));
        }else{
            return super.getDisplayName();
        }
    }

    @Override
    public boolean hasCustomName(){
        // If this returns true, the renderer will show the nameplate when looking directly at the entity
        return Wizardry.settings.summonedCreatureNames && getCaster() != null;
    }
}

