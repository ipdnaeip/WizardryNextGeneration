package com.ipdnaeip.wizardrynextgeneration.item;

import com.ipdnaeip.wizardrynextgeneration.registry.WNGConstants;
import electroblob.wizardry.client.DrawingUtils;
import electroblob.wizardry.constants.Constants;
import electroblob.wizardry.item.IManaStoringItem;
import electroblob.wizardry.item.IWorkbenchItem;
import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.registry.WizardryRecipes;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Slot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class ItemCharmBloodstone extends ItemWNGArtefact implements IWorkbenchItem, IManaStoringItem {

    public static final String BLOODSTONE_IS_ACTIVE = WNGConstants.registerTag("bloodstone_is_active");
    public static final int MAX_MANA = 1000;
    public static final int TICKS_INBETWEEN = 100;
    public static final int CHARGE_PER_USE = 5;
    public static final int ACTIVE_COST_MULTIPLIER = 2;
    public static final int ACTIVE_TIME_MULTIPLIER = 2;

    public ItemCharmBloodstone(EnumRarity rarity, Type type) {
        super(rarity, type);
        this.setMaxStackSize(1);
        this.setMaxDamage(MAX_MANA);
        this.addReadinessPropertyOverride();
        WizardryRecipes.addToManaFlaskCharging(this);
    }

    public void addReadinessPropertyOverride() {
        this.addPropertyOverride(new ResourceLocation("active"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                return isActive(stack) ? 0f : 1f;
            }
        });
    }

    public static boolean isActive(ItemStack stack) {
        if (!stack.isEmpty() && stack.hasTagCompound() && stack.getTagCompound().hasKey(BLOODSTONE_IS_ACTIVE)) {
            return stack.getTagCompound().getBoolean(BLOODSTONE_IS_ACTIVE);
        }
        return false;
    }

    public static void changeActive(ItemStack stack) {
        NBTTagCompound nbt;
        if (stack.hasTagCompound()) {
            nbt = stack.getTagCompound();
        } else {
            nbt = new NBTTagCompound();
        }
        nbt.setBoolean(BLOODSTONE_IS_ACTIVE, !nbt.getBoolean(BLOODSTONE_IS_ACTIVE));
        stack.setTagCompound(nbt);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        changeActive(stack);
        return stack;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 25;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        playerIn.setActiveHand(handIn);
        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack){
        return DrawingUtils.mix(0xff8bfe, 0x8e2ee4, (float)getDurabilityForDisplay(stack));
    }

    @Override
    public int getMana(ItemStack stack) {
        return getManaCapacity(stack) - getDamage(stack);
    }

    @Override
    public void setMana(ItemStack stack, int mana) {
        super.setDamage(stack, getManaCapacity(stack) - mana);
    }

    @Override
    public int getManaCapacity(ItemStack stack) {
        return MAX_MANA;
    }

    @Override
    public int getSpellSlotCount(ItemStack stack) {
        return 0;
    }

    @Override
    public boolean onApplyButtonPressed(EntityPlayer player, Slot centre, Slot crystals, Slot upgrade, Slot[] spellBooks) {
        boolean changed = false;
        if (crystals.getStack() != ItemStack.EMPTY && !this.isManaFull(centre.getStack())) {
            int chargeDepleted = this.getManaCapacity(centre.getStack()) - this.getMana(centre.getStack());
            int manaPerItem = Constants.MANA_PER_CRYSTAL;
            if (crystals.getStack().getItem() == WizardryItems.crystal_shard) { manaPerItem = Constants.MANA_PER_SHARD; }
            if (crystals.getStack().getItem() == WizardryItems.grand_crystal) { manaPerItem = Constants.GRAND_CRYSTAL_MANA; }
            if (crystals.getStack().getCount() * manaPerItem < chargeDepleted) {
                // If there aren't enough crystals to fully charge the wand
                this.rechargeMana(centre.getStack(), crystals.getStack().getCount() * manaPerItem);
                crystals.decrStackSize(crystals.getStack().getCount());
            } else {
                // If there are excess crystals (or just enough)
                this.setMana(centre.getStack(), this.getManaCapacity(centre.getStack()));
                crystals.decrStackSize((int) Math.ceil(((double) chargeDepleted) / manaPerItem));
            }
            changed = true;
        }
        return changed;
    }

    @Override
    public boolean showTooltip(ItemStack stack) {
        return false;
    }

}
