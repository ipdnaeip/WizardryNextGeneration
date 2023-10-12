package com.ipdnaeip.wizardrynextgeneration.entity.construct;


import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public abstract class EntityLivingScaledConstruct extends EntityLivingMagicConstruct {
    protected float sizeMultiplier = 1.0F;

    public EntityLivingScaledConstruct(World world) {
        super(world);
    }

    public float getSizeMultiplier() {
        return this.sizeMultiplier;
    }

    public void setSizeMultiplier(float sizeMultiplier) {
        this.sizeMultiplier = sizeMultiplier;
        this.setSize(this.shouldScaleWidth() ? this.width * sizeMultiplier : this.width, this.shouldScaleHeight() ? this.height * sizeMultiplier : this.height);
    }

    protected boolean shouldScaleWidth() {
        return true;
    }

    protected boolean shouldScaleHeight() {
        return true;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setSizeMultiplier(nbt.getFloat("sizeMultiplier"));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setFloat("sizeMultiplier", this.sizeMultiplier);
    }

    @Override
    public void readSpawnData(ByteBuf data) {
        super.readSpawnData(data);
        this.setSizeMultiplier(data.readFloat());
    }

    @Override
    public void writeSpawnData(ByteBuf data) {
        super.writeSpawnData(data);
        data.writeFloat(this.sizeMultiplier);
    }
}

