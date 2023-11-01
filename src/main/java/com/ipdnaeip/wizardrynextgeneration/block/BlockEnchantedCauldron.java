package com.ipdnaeip.wizardrynextgeneration.block;

import net.minecraft.block.BlockCauldron;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockEnchantedCauldron extends BlockCauldron {

    public BlockEnchantedCauldron() {

    }

    @Override
    public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        int i = state.getValue(LEVEL);
        float f = (float)pos.getY() + (6.0F + (float)(3 * i)) / 16.0F;

        if (!worldIn.isRemote && entityIn.isBurning() && i > 0 && entityIn.getEntityBoundingBox().minY <= (double)f)
        {
            entityIn.extinguish();
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemstack = playerIn.getHeldItem(hand);
        if (itemstack.isEmpty()) {
            return true;
        }
        else {
            int i = state.getValue(LEVEL);
            Item item = itemstack.getItem();
            if (item == Items.WATER_BUCKET) {
                if (i < 3 && !worldIn.isRemote) {
                    if (!playerIn.capabilities.isCreativeMode) {
                        playerIn.setHeldItem(hand, new ItemStack(Items.BUCKET));
                    }
                    playerIn.addStat(StatList.CAULDRON_FILLED);
                    this.setWaterLevel(worldIn, pos, state, 3);
                    worldIn.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }

                return true;
            }
            else if (item == Items.GLASS_BOTTLE) {
                if (i > 0 && !worldIn.isRemote) {
                    if (!playerIn.capabilities.isCreativeMode) {
                        ItemStack itemstack3 = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER);
                        playerIn.addStat(StatList.CAULDRON_USED);
                        itemstack.shrink(1);

                        if (itemstack.isEmpty()) {
                            playerIn.setHeldItem(hand, itemstack3);
                        }
                        else if (!playerIn.inventory.addItemStackToInventory(itemstack3)) {
                            playerIn.dropItem(itemstack3, false);
                        }
                        else if (playerIn instanceof EntityPlayerMP) {
                            ((EntityPlayerMP)playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
                        }
                    }
                    worldIn.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
                return true;
            }
            else if (item == Items.POTIONITEM && PotionUtils.getPotionFromItem(itemstack) == PotionTypes.WATER) {
                if (i < 3 && !worldIn.isRemote) {
                    if (!playerIn.capabilities.isCreativeMode) {
                        ItemStack itemstack2 = new ItemStack(Items.GLASS_BOTTLE);
                        playerIn.addStat(StatList.CAULDRON_USED);
                        playerIn.setHeldItem(hand, itemstack2);
                        if (playerIn instanceof EntityPlayerMP) {
                            ((EntityPlayerMP)playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
                        }
                    }

                    worldIn.playSound((EntityPlayer)null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    this.setWaterLevel(worldIn, pos, state, i + 1);
                }
                return true;
            }
            else {
                if (i > 0 && item instanceof ItemArmor) {
                    ItemArmor itemarmor = (ItemArmor)item;
                    if (itemarmor.getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER && itemarmor.hasColor(itemstack) && !worldIn.isRemote) {
                        itemarmor.removeColor(itemstack);
                        playerIn.addStat(StatList.ARMOR_CLEANED);
                        return true;
                    }
                }
                if (i > 0 && item instanceof ItemBanner) {
                    if (TileEntityBanner.getPatterns(itemstack) > 0 && !worldIn.isRemote) {
                        ItemStack itemstack1 = itemstack.copy();
                        itemstack1.setCount(1);
                        TileEntityBanner.removeBannerData(itemstack1);
                        playerIn.addStat(StatList.BANNER_CLEANED);
                        if (!playerIn.capabilities.isCreativeMode) {
                            itemstack.shrink(1);
                        }
                        if (itemstack.isEmpty()) {
                            playerIn.setHeldItem(hand, itemstack1);
                        }
                        else if (!playerIn.inventory.addItemStackToInventory(itemstack1)) {
                            playerIn.dropItem(itemstack1, false);
                        }
                        else if (playerIn instanceof EntityPlayerMP) {
                            ((EntityPlayerMP)playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
                        }
                    }
                    return true;
                }
                else {
                    return false;
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        double d0 = (double)pos.getX() + 0.5D;
        double d1 = (double)pos.getY() + 0.7D;
        double d2 = (double)pos.getZ() + 0.5D;
        worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        worldIn.spawnParticle(EnumParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D);
    }
}
