package com.ipdnaeip.wizardrynextgeneration.item;

import com.ipdnaeip.wizardrynextgeneration.entity.projectile.EntityConjuredPotion;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGSpells;
import electroblob.wizardry.item.IConjuredItem;
import electroblob.wizardry.util.InventoryUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemConjuredPotion extends Item implements IConjuredItem {

    public float durationMultiplier = 1;
    public float blastMultiplier = 1;

    public ItemConjuredPotion() {
        this.setMaxDamage(600);
        this.setCreativeTab(null);
        this.addAnimationPropertyOverrides();
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return this.getMaxDamageFromNBT(stack, WNGSpells.conjure_potion);
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        return IConjuredItem.getTimerBarColour(stack);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return ((oldStack.isEmpty() && newStack.isEmpty()) || oldStack.getItem() != newStack.getItem() || slotChanged) && super.shouldCauseReequipAnimation(oldStack, newStack, slotChanged);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        int damage = stack.getItemDamage();
        if (damage > stack.getMaxDamage()) {
            InventoryUtils.replaceItemInInventory(entity, slot, stack, ItemStack.EMPTY);
        }
        stack.setItemDamage(damage + 1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return true;
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player) {
        return false;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (!player.isCreative()) {
            stack.shrink(1);
        }
        player.playSound(SoundEvents.ENTITY_SPLASH_POTION_THROW, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        player.getCooldownTracker().setCooldown(this, 20);
        if (!world.isRemote) {
            EntityConjuredPotion potion = new EntityConjuredPotion(world);
            potion.damageMultiplier = IConjuredItem.getDamageMultiplier(stack);
            potion.durationMultiplier = ItemConjuredPotion.getEffectDurationMultiplier(stack);
            potion.blastMultiplier = ItemConjuredPotion.getBlastMultiplier(stack);
            potion.isLingering = ItemConjuredPotion.getLingering(stack);
            potion.aim(player, 1.0F);
            world.spawnEntity(potion);
        }
        return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
    }

    public static void setEffectDurationMultiplier(ItemStack stack, float multiplier) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }

        stack.getTagCompound().setFloat("effectDurationMultiplier", multiplier);
    }

    public static void setBlastMultiplier(ItemStack stack, float multiplier) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }

        stack.getTagCompound().setFloat("blastMultiplier", multiplier);
    }

    public static void setLingering(ItemStack stack, boolean lingering) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }

        stack.getTagCompound().setBoolean("lingering", lingering);
    }

    public static float getEffectDurationMultiplier(ItemStack stack) {
        return !stack.hasTagCompound() ? 1.0F : stack.getTagCompound().getFloat("effectDurationMultiplier");
    }

    public static float getBlastMultiplier(ItemStack stack) {
        return !stack.hasTagCompound() ? 1.0F : stack.getTagCompound().getFloat("blastMultiplier");
    }

    public static boolean getLingering(ItemStack stack) {
        return stack.hasTagCompound() && stack.getTagCompound().getBoolean("lingering");
    }

}
