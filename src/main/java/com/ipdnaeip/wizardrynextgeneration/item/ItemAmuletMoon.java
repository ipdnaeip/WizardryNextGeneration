package com.ipdnaeip.wizardrynextgeneration.item;

import com.ipdnaeip.wizardrynextgeneration.util.WNGUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;


//most of the code was copied from Windanesz's ItemDailyArtefact
public class ItemAmuletMoon extends ItemCooldownArtefact {

    public static final String AMULET_MOON_FULL_MOON = WNGUtils.registerTag("amulet_moon_full_moon");

    public ItemAmuletMoon(EnumRarity rarity, Type type) {
        super(rarity, type);
        this.setCooldown(24000);
        this.addReadinessPropertyOverride();
    }

    @Override
    public boolean isReady(World world, ItemStack stack) {
        if (world != null && !stack.isEmpty() && stack.hasTagCompound() && stack.getTagCompound().hasKey(CD_ARTEFACT_LAST_TIME_ACTIVATED)) {
            long currentWorldTime = world.getTotalWorldTime();
            long lastAccess = stack.getTagCompound().getLong(CD_ARTEFACT_LAST_TIME_ACTIVATED);
            return isCooldownReset(lastAccess, currentWorldTime) && stack.getTagCompound().getBoolean(AMULET_MOON_FULL_MOON);
        }
        return true;
    }

    public void action(EntityPlayer player, ItemStack stack) {
        player.setHealth(1F);
        player.clearActivePotions();
        player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 100, 1));
        player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 900, 1));
        player.world.playSound(player, player.getPosition(), SoundEvents.ITEM_TOTEM_USE, SoundCategory.BLOCKS, 1F, 0F);
        setHasBeenFullMoon(stack, false);
    }

    public static void setHasBeenFullMoon(ItemStack stack, boolean fullMoon) {
        NBTTagCompound nbt;
        if (stack.hasTagCompound()) {
            nbt = stack.getTagCompound();
        } else {
            nbt = new NBTTagCompound();
        }
        nbt.setBoolean(AMULET_MOON_FULL_MOON, fullMoon);
        stack.setTagCompound(nbt);
    }

}
