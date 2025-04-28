package com.ipdnaeip.wizardrynextgeneration.item;

import com.ipdnaeip.wizardrynextgeneration.registry.WNGTabs;
import electroblob.wizardry.item.ItemArtefact;
import net.minecraft.item.EnumRarity;
import net.minecraftforge.fml.common.Mod;

public class ItemWNGArtefact extends ItemArtefact {

    public ItemWNGArtefact(EnumRarity rarity, Type type) {

        super(rarity, type);
        this.setCreativeTab(WNGTabs.WIZARDRYNEXTGENERATION_GEAR);
    }
}
