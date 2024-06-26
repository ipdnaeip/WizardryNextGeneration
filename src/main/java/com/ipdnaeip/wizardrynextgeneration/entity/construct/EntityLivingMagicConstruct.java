package com.ipdnaeip.wizardrynextgeneration.entity.construct;

import electroblob.wizardry.Wizardry;
import electroblob.wizardry.item.ISpellCastingItem;
import electroblob.wizardry.registry.WizardrySounds;
import electroblob.wizardry.util.AllyDesignationSystem;
import electroblob.wizardry.util.EntityUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class EntityLivingMagicConstruct extends EntityLivingBase implements IEntityOwnable, IEntityAdditionalSpawnData {
    private UUID casterUUID;
    public int lifetime = 600;
    public float damageMultiplier = 1.0F;

    public EntityLivingMagicConstruct(World world) {
        super(world);
        this.height = 1.0F;
        this.width = 1.0F;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
        this.setPosition(x, y, z);
        this.setRotation(yaw, pitch);
    }

    @Override
    public void onUpdate() {
        if (this.ticksExisted > this.lifetime && this.lifetime != -1) {
            this.despawn();
        }
        super.onUpdate();
    }

    @Override
    public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand) {
        if (this.lifetime == -1 && this.getCaster() == player && player.isSneaking() && player.getHeldItem(hand).getItem() instanceof ISpellCastingItem) {
            this.despawn();
            return EnumActionResult.SUCCESS;
        } else {
            return super.applyPlayerInteraction(player, vec, hand);
        }
    }

    public void despawn() {
        this.setDead();
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        if (nbttagcompound.hasUniqueId("casterUUID")) {
            this.casterUUID = nbttagcompound.getUniqueId("casterUUID");
        }
        this.lifetime = nbttagcompound.getInteger("lifetime");
        this.damageMultiplier = nbttagcompound.getFloat("damageMultiplier");
    }

    @Override
    public Iterable<ItemStack> getArmorInventoryList() {
        return NonNullList.withSize(0, ItemStack.EMPTY);
    }

    @Override
    public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack) {
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        if (this.casterUUID != null) {
            nbttagcompound.setUniqueId("casterUUID", this.casterUUID);
        }
        nbttagcompound.setInteger("lifetime", this.lifetime);
        nbttagcompound.setFloat("damageMultiplier", this.damageMultiplier);
    }

    public void writeSpawnData(ByteBuf data) {
        data.writeInt(this.lifetime);
        data.writeInt(this.getCaster() == null ? -1 : this.getCaster().getEntityId());
    }

    public void readSpawnData(ByteBuf data) {
        this.lifetime = data.readInt();
        int id = data.readInt();
        if (id == -1) {
            this.setCaster(null);
        } else {
            Entity entity = this.world.getEntityByID(id);
            if (entity instanceof EntityLivingBase) {
                this.setCaster((EntityLivingBase)entity);
            } else {
                Wizardry.logger.warn("Construct caster with ID in spawn data not found");
            }
        }
    }

    @Nullable
    public UUID getOwnerId() {
        return this.casterUUID;
    }

    @Nullable
    public Entity getOwner() {
        return this.getCaster();
    }

    @Nullable
    public EntityLivingBase getCaster() {
        Entity entity = EntityUtils.getEntityByUUID(this.world, this.getOwnerId());
        if (entity != null && !(entity instanceof EntityLivingBase)) {
            Wizardry.logger.warn("{} has a non-living owner!", this);
            entity = null;
        }
        return (EntityLivingBase)entity;
    }

    public void setCaster(@Nullable EntityLivingBase caster) {
        this.casterUUID = caster == null ? null : caster.getUniqueID();
    }

    public boolean isValidTarget(Entity target) {
        return AllyDesignationSystem.isValidTarget(this.getCaster(), target);
    }

    @Override
    public SoundCategory getSoundCategory() {
        return WizardrySounds.SPELLS;
    }

    @Override
    public boolean isMovementBlocked() { return true; }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    public EnumHandSide getPrimaryHand() {
        return EnumHandSide.RIGHT;
    }

    @Override
    protected void collideWithEntity(Entity entityIn) { }

}

/*
public abstract class EntityLivingMagicConstruct extends EntityLiving implements IEntityOwnable, IEntityAdditionalSpawnData {
    private UUID casterUUID;
    public int lifetime = 600;
    public float damageMultiplier = 1.0F;

    public EntityLivingMagicConstruct(World world) {
        super(world);
        this.height = 1.0F;
        this.width = 1.0F;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
        this.setPosition(x, y, z);
        this.setRotation(yaw, pitch);
    }

    @Override
    public void onUpdate() {
        if (this.ticksExisted > this.lifetime && this.lifetime != -1) {
            this.despawn();
        }
        super.onUpdate();
    }

    @Override
    public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand) {
        if (this.lifetime == -1 && this.getCaster() == player && player.isSneaking() && player.getHeldItem(hand).getItem() instanceof ISpellCastingItem) {
            this.despawn();
            return EnumActionResult.SUCCESS;
        } else {
            return super.applyPlayerInteraction(player, vec, hand);
        }
    }

    public void despawn() {
        this.setDead();
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        if (nbttagcompound.hasUniqueId("casterUUID")) {
            this.casterUUID = nbttagcompound.getUniqueId("casterUUID");
        }
        this.lifetime = nbttagcompound.getInteger("lifetime");
        this.damageMultiplier = nbttagcompound.getFloat("damageMultiplier");
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        if (this.casterUUID != null) {
            nbttagcompound.setUniqueId("casterUUID", this.casterUUID);
        }
        nbttagcompound.setInteger("lifetime", this.lifetime);
        nbttagcompound.setFloat("damageMultiplier", this.damageMultiplier);
    }

    public void writeSpawnData(ByteBuf data) {
        data.writeInt(this.lifetime);
        data.writeInt(this.getCaster() == null ? -1 : this.getCaster().getEntityId());
    }

    public void readSpawnData(ByteBuf data) {
        this.lifetime = data.readInt();
        int id = data.readInt();
        if (id == -1) {
            this.setCaster(null);
        } else {
            Entity entity = this.world.getEntityByID(id);
            if (entity instanceof EntityLivingBase) {
                this.setCaster((EntityLivingBase)entity);
            } else {
                Wizardry.logger.warn("Construct caster with ID in spawn data not found");
            }
        }
    }

    @Nullable
    public UUID getOwnerId() {
        return this.casterUUID;
    }

    @Nullable
    public Entity getOwner() {
        return this.getCaster();
    }

    @Nullable
    public EntityLivingBase getCaster() {
        Entity entity = EntityUtils.getEntityByUUID(this.world, this.getOwnerId());
        if (entity != null && !(entity instanceof EntityLivingBase)) {
            Wizardry.logger.warn("{} has a non-living owner!", this);
            entity = null;
        }
        return (EntityLivingBase)entity;
    }

    public void setCaster(@Nullable EntityLivingBase caster) {
        this.casterUUID = caster == null ? null : caster.getUniqueID();
    }

    public boolean isValidTarget(Entity target) {
        return AllyDesignationSystem.isValidTarget(this.getCaster(), target);
    }

    @Override
    public SoundCategory getSoundCategory() {
        return WizardrySounds.SPELLS;
    }

    @Override
    public boolean canDespawn() { return false; }

    @Override
    public boolean isMovementBlocked() { return true; }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    protected void collideWithEntity(Entity entityIn) { }

}*/
