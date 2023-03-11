package com.ipdnaeip.wizardrynextgeneration.registry;

import electroblob.wizardry.item.ItemScroll;
import electroblob.wizardry.item.ItemSpellBook;
import electroblob.wizardry.registry.WizardryTabs;
import electroblob.wizardry.spell.Spell;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public final class WNGTabs {

    public static final CreativeTabs WIZARDRYNEXTGENERATION = new CreativeTabs("wizardrynextgeneration") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(WNGItems.spell_book_wng);
        }
    };

    public static final CreativeTabs WIZARDRYNEXTGENERATION_GEAR = new CreativeTabs("wizardrynextgenerationgear") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(WNGItems.ring_void);
        }
    };

}