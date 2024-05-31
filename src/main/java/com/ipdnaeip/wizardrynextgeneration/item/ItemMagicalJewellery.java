/*
package com.ipdnaeip.wizardrynextgeneration.item;

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
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemMagicalJewellery extends Item {

    public ItemArtefact.Type type;

    public ItemMagicalJewellery(ItemArtefact.Type type) {
        super();
        setMaxStackSize(1);
        this.type = type;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 20;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BLOCK;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        playerIn.setActiveHand(handIn);
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(Wizardry.proxy.translate("item." + this.getRegistryName().toString() + ".desc"));
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving) {

        if (entityLiving instanceof EntityPlayer && !world.isRemote) {

            LootTable table = world.getLootTableManager().getLootTableFromLocation(new ResourceLocation(Wizardry.MODID, "chests/shrine"));
            LootContext context = new LootContext.Builder((WorldServer) world).withPlayer((EntityPlayer) entityLiving).withLuck(0).build();

            List<ItemStack> artefacts = new ArrayList<>();
            table.getPool("artefact").generateLoot(artefacts, world.rand, context);

            for (ItemStack artefact : artefacts) {
                if (!(artefact.getItem() instanceof ItemArtefact || ((ItemArtefact)(artefact.getItem())).getType() == this.type)) {
                    artefacts.remove(artefact);
                    break;
                }
            }

            System.out.println("list size: " + artefacts.size());
            return artefacts.get(itemRand.nextInt(artefacts.size()));

        }
        return super.onItemUseFinish(stack, world, entityLiving);
    }

}
*/
