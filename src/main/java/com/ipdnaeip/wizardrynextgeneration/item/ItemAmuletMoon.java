package com.ipdnaeip.wizardrynextgeneration.item;

import com.ipdnaeip.wizardrynextgeneration.integration.baubles.WNGBaublesIntegration;
import electroblob.wizardry.client.DrawingUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

//most of the code was copied from Windanesz's ItemDailyArtefact
public class ItemAmuletMoon extends ItemWNGArtefact {

    private static final String LAST_TIME_ACTIVATED = "last_time_activated";

    public ItemAmuletMoon(EnumRarity rarity, Type type) {
        super(rarity, type);
        addReadinessPropertyOverride();
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

    public static void performAction(EntityPlayer player) {
        ItemStack amulet = WNGBaublesIntegration.getAmuletSlotItemStack(player);
        player.setHealth(0.5F);
        player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 100, 1));
        player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 900, 1));
        player.getEntityWorld().playSound(player, player.getPosition(), SoundEvents.ITEM_TOTEM_USE, SoundCategory.BLOCKS, 1F, 0F);
        setLastTimeActivated(amulet, player.getEntityWorld().getTotalWorldTime());
    }

    public static boolean isReady(World world, ItemStack stack) {
        if (world != null && !stack.isEmpty() && stack.hasTagCompound() && stack.getTagCompound().hasKey(LAST_TIME_ACTIVATED)) {
            long currentWorldTime = world.getTotalWorldTime();
            long lastAccess = stack.getTagCompound().getLong(LAST_TIME_ACTIVATED);
            //System.out.println(lastAccess + " " + currentWorldTime + " " + isFullDayBetween(lastAccess, currentWorldTime));
            return isFullDayBetween(lastAccess, currentWorldTime);
        }
        return true;
    }

    public static boolean isFullDayBetween(long startTime, long endTime) {
        long fullDay = 24000;
        return (endTime - startTime) >= fullDay;
    }

    public static void setLastTimeActivated(ItemStack stack, long currentTime) {
        if (stack.hasTagCompound()) {
            NBTTagCompound nbt = stack.getTagCompound();
            nbt.setLong(LAST_TIME_ACTIVATED, currentTime);
            stack.setTagCompound(nbt);
        } else {
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setLong(LAST_TIME_ACTIVATED, currentTime);
            stack.setTagCompound(nbt);
        }

    }

}
