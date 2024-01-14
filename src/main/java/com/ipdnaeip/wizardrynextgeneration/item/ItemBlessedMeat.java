package com.ipdnaeip.wizardrynextgeneration.item;

import com.ipdnaeip.wizardrynextgeneration.registry.WNGTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class ItemBlessedMeat extends ItemFood {
    public ItemBlessedMeat() {

        super(8, 1.2F, true);
        this.setCreativeTab(WNGTabs.WIZARDRYNEXTGENERATION);
    }

}
