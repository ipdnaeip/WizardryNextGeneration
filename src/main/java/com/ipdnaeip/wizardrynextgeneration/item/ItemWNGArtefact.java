package com.ipdnaeip.wizardrynextgeneration.item;

import electroblob.wizardry.item.ItemArtefact;
import net.minecraft.item.EnumRarity;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ItemWNGArtefact extends ItemArtefact {

    public ItemWNGArtefact(EnumRarity rarity, Type type) {
        super(rarity, type);
    }
}
