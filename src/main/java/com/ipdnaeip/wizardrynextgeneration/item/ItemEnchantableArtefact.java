package com.ipdnaeip.wizardrynextgeneration.item;

import com.ipdnaeip.wizardrynextgeneration.accessor.LootContextAccessor;
import com.ipdnaeip.wizardrynextgeneration.handler.ArtefactEnchantingHandler;
import electroblob.wizardry.Wizardry;
import electroblob.wizardry.item.ItemArtefact;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.*;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ItemEnchantableArtefact extends Item {

    public ItemArtefact.Type type;

    public ItemEnchantableArtefact(ItemArtefact.Type type) {
        super();
        setMaxStackSize(1);
        this.type = type;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 1;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.NONE;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        playerIn.setActiveHand(handIn);
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(Wizardry.proxy.translate("item." + this.getRegistryName().toString() + ".desc"));
    }

/*    @Override
    public ItemStack onItemUseFinish(ItemStack itemStack, World world, EntityLivingBase entityLiving) {
        if (entityLiving instanceof EntityPlayer && !world.isRemote) {
            ItemStack newItemStack = ArtefactEnchantingHandler.filterArtefacts(itemsToBeAdded(this));
            if (newItemStack != null) {
                itemStack = newItemStack;
                ((EntityPlayer)entityLiving).addItemStackToInventory(newItemStack);
            }
        }
        return super.onItemUseFinish(itemStack, world, entityLiving);
    }*/

/*    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving) {
        if (entityLiving instanceof EntityPlayer && !world.isRemote) {
            //Get the LootTable
            LootTable lootTable = world.getLootTableManager().getLootTableFromLocation(new ResourceLocation(Wizardry.MODID, "chests/shrine"));
            //Get the LootContext
            LootContext lootContext = new LootContext.Builder((WorldServer) world).withPlayer((EntityPlayer) entityLiving).withLuck(0).build();
            //Get the artefact LootPool
            LootPool lootPool = lootTable.getPool("artefact");
            //Filter the LootPool and
            List<ItemStack> artefacts = ArtefactEnchantingHandler.createFilteredLootPool(world, lootPool, lootContext);
            for (ItemStack artefact : artefacts) {
                ((EntityPlayer) entityLiving).addItemStackToInventory(artefact);
            }
        }
        return super.onItemUseFinish(stack, world, entityLiving);
    }*/

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving) {
        if (entityLiving instanceof EntityPlayer && !world.isRemote) {
            EntityPlayer player = (EntityPlayer)entityLiving;
            //Get the LootTable
            LootTable lootTable = world.getLootTableManager().getLootTableFromLocation(new ResourceLocation(Wizardry.MODID, "chests/shrine"));
            //Get the LootContext
            LootContext lootContext = new LootContext.Builder((WorldServer) world).withPlayer((EntityPlayer) entityLiving).withLuck(player.getLuck()).build();
            //Set the filter
            ((LootContextAccessor)lootContext).wizardrynextgeneration$setFilter(itemsToBeAdded(this));
            //Get the artefact LootPool
            List<ItemStack> artefacts = lootTable.generateLootForPools(itemRand, lootContext);
            for (ItemStack artefact : artefacts) {
                ((EntityPlayer) entityLiving).addItemStackToInventory(artefact);
            }
        }
        return super.onItemUseFinish(stack, world, entityLiving);
    }

/*    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving) {
        if (entityLiving instanceof EntityPlayer && !world.isRemote) {
            EntityPlayer player = (EntityPlayer)entityLiving;
            //Get the LootTable
            LootTable lootTable = world.getLootTableManager().getLootTableFromLocation(new ResourceLocation(Wizardry.MODID, "chests/shrine"));
            //Get the LootContext
            LootContext lootContext = new LootContext.Builder((WorldServer) world).withPlayer((EntityPlayer) entityLiving).withLuck(player.getLuck()).build();
            //Set the filter
            ((LootContextAccessor)lootContext).wizardrynextgeneration$setFilter(itemsToBeAdded(this));
            //Get the artefact LootPool
            LootPool lootPool = lootTable.getPool("artefact");
            List<ItemStack> artefacts = new ArrayList<>();
            lootPool.generateLoot(artefacts, itemRand, lootContext);
            for (ItemStack artefact : artefacts) {
                ((EntityPlayer) entityLiving).addItemStackToInventory(artefact);
            }
        }
        return super.onItemUseFinish(stack, world, entityLiving);
    }*/

    public static Predicate<Item> itemsToBeAdded(ItemEnchantableArtefact itemArtefact) {
        return item -> item instanceof ItemArtefact && ((ItemArtefact)item).getType() == itemArtefact.type;
    }

    public static Predicate<Item> itemsToBeRemoved(ItemEnchantableArtefact itemArtefact) {
        return item -> !(item instanceof ItemArtefact) || ((ItemArtefact)item).getType() != itemArtefact.type;
    }
}

