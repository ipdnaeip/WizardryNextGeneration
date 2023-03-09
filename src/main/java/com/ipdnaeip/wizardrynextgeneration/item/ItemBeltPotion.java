package com.ipdnaeip.wizardrynextgeneration.item;

import electroblob.wizardry.client.DrawingUtils;
import electroblob.wizardry.constants.Constants;
import electroblob.wizardry.item.IManaStoringItem;
import electroblob.wizardry.item.IWorkbenchItem;
import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.registry.WizardryRecipes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemBeltPotion extends ItemNewArtefact implements IWorkbenchItem, IManaStoringItem {

    public static final int MAX_MANA = 1000;

    public ItemBeltPotion(EnumRarity rarity, AdditionalType type) {
        super(rarity, type);
        setMaxStackSize(1);
        setMaxDamage(MAX_MANA);
        WizardryRecipes.addToManaFlaskCharging(this);
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
