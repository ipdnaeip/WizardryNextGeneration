package com.ipdnaeip.wizardrynextgeneration.item;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.handler.WNGGuiHandler;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;


public class ItemSpellEncyclopedia extends Item {

    public ItemSpellEncyclopedia() {
        this.setMaxStackSize(1);
        this.setCreativeTab(WNGTabs.WIZARDRYNEXTGENERATION);
    }


    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        player.openGui(WizardryNextGeneration.instance, WNGGuiHandler.SPELL_ENCYCLOPEDIA, world, 0, 0, 0);
        return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
    }
}
