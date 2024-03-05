package com.ipdnaeip.wizardrynextgeneration.item;

import com.ipdnaeip.wizardrynextgeneration.registry.WNGConstants;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

//most of the code was copied from Windanesz's ItemDailyArtefact
public abstract class ItemCooldownArtefact extends ItemWNGArtefact {

    public long cooldown;

    public ItemCooldownArtefact(EnumRarity rarity, Type type) {
        super(rarity, type);
    }

    //24000 is a full day
    public void setCooldown(long cooldown) {
        this.cooldown = cooldown;
    }

    public void addReadinessPropertyOverride() {
        this.addPropertyOverride(new ResourceLocation("ready"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                if (worldIn == null && entityIn != null) {
                    return isReady(entityIn.getEntityWorld(), stack) ? 0f : 1f;
                } else {
                    return isReady(worldIn, stack) ? 0f : 1f;
                }
            }
        });
    }

    public boolean isReady(World world, ItemStack stack) {
        if (world != null && !stack.isEmpty() && stack.hasTagCompound() && stack.getTagCompound().hasKey(WNGConstants.CD_ARTEFACT_LAST_TIME_ACTIVATED)) {
            long currentWorldTime = world.getTotalWorldTime();
            long lastAccess = stack.getTagCompound().getLong(WNGConstants.CD_ARTEFACT_LAST_TIME_ACTIVATED);
            return isCooldownReset(lastAccess, currentWorldTime);
        }
        return true;
    }

    public boolean isCooldownReset(long startTime, long endTime) {
        return (endTime - startTime) >= cooldown;
    }

    public abstract void performAction(EntityPlayer player, ItemStack stack);

    public static void setLastTimeActivated(ItemStack stack, long currentTime) {
        NBTTagCompound nbt;
        if (stack.hasTagCompound()) {
            nbt = stack.getTagCompound();
        } else {
            nbt = new NBTTagCompound();
        }
        nbt.setLong(WNGConstants.CD_ARTEFACT_LAST_TIME_ACTIVATED, currentTime);
        stack.setTagCompound(nbt);
    }

}
