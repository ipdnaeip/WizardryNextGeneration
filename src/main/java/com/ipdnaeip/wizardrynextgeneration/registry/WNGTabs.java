package com.ipdnaeip.wizardrynextgeneration.registry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public final class WNGTabs {

    public static final CreativeTabs WIZARDRYNEXTGENERATION = new CreativeTabs("wizardrynextgeneration") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(WNGItems.SPELL_BOOK_WNG);
        }
    };

    public static final CreativeTabs WIZARDRYNEXTGENERATION_GEAR = new CreativeTabs("wizardrynextgenerationgear") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(WNGItems.RING_VOID);
        }
    };

}