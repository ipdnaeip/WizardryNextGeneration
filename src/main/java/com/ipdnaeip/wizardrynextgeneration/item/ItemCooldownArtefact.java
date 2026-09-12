package com.ipdnaeip.wizardrynextgeneration.item;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.util.WNGUtils;
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

    public static final String LAST_TIME_ACTIVATED = WNGUtils.registerTag("last_time_activated");
    public static final String COOLDOWN = WNGUtils.registerTag("last_time_activated");
    int cooldown;

    public ItemCooldownArtefact(EnumRarity rarity, Type type) {
        super(rarity, type);
        this.addReadinessPropertyOverride();
    }

    //24000 is a full day
    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public void addReadinessPropertyOverride() {
        this.addPropertyOverride(new ResourceLocation("ready"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                if (entityIn != null) {
                    return isReady(entityIn.world, stack) ? 0f : 1f;
                } else {
                    return 0;
                }
            }
        });
    }

    public static boolean isReady(World world, ItemStack stack) {
        if (world != null) {
            if (stack.getItem() instanceof ItemCooldownArtefact) {
                if (stack.hasTagCompound()) {
                    ItemCooldownArtefact cooldownArtefact = (ItemCooldownArtefact)stack.getItem();
                    long currentWorldTime = world.getTotalWorldTime();
                    long lastAccess = stack.getTagCompound().getLong(LAST_TIME_ACTIVATED);
                    int cooldown = stack.getTagCompound().getInteger(COOLDOWN);
                    return currentWorldTime >= lastAccess + cooldown && cooldownArtefact.areAdditionalConditionsMet(world, stack);
                }
            //Don't send to the client as the client checks the readiness constantly and can cause spam
            } else if (!world.isRemote) {
                WizardryNextGeneration.logger.warn("Checking readiness of an artefact that is not a cooldown artefact!");
            }
        }
        return true;
    }

    public static void performAction(EntityPlayer player, ItemStack stack) {
        if (stack.getItem() instanceof ItemCooldownArtefact) {
            ItemCooldownArtefact cooldownArtefact = (ItemCooldownArtefact)stack.getItem();
            cooldownArtefact.action(player, stack);
            setLastTimeActivated(stack, player.getEntityWorld().getTotalWorldTime());
        } else {
            WizardryNextGeneration.logger.warn("Trying to perform the action of an item that is not a cooldown artefact!");
        }
    }

    public boolean areAdditionalConditionsMet(World world, ItemStack stack) {
        return true;
    }

    public abstract void action(EntityPlayer player, ItemStack stack);

    public static void setLastTimeActivated(ItemStack stack, long currentTime) {
        if (!(stack.getItem() instanceof ItemCooldownArtefact)) {
            WizardryNextGeneration.logger.warn("Trying to set last time activated on an item that is not a cooldown artefact!");
            return;
        }
        NBTTagCompound nbt;
        if (stack.hasTagCompound()) {
            nbt = stack.getTagCompound();
        } else {
            nbt = new NBTTagCompound();
        }
        nbt.setLong(LAST_TIME_ACTIVATED, currentTime);
        nbt.setInteger(COOLDOWN, ((ItemCooldownArtefact)stack.getItem()).cooldown);
        stack.setTagCompound(nbt);
    }

}
