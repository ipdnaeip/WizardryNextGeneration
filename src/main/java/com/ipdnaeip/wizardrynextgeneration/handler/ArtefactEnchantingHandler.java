package com.ipdnaeip.wizardrynextgeneration.handler;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.*;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public class ArtefactEnchantingHandler {

    public static Predicate<Item> filter;

    /**
     * This method filters items out of a LootPool, generates loot based on the new LootPool, and returns a list of the generated ItemStacks.
     * @param world The World from which to generate loot.
     * @param lootPool The LootPool from which to generate loot.
     * @param lootContext The LootContext for which to generate loot.
     * @param filter The filter to remove items
     * @return List<ItemStack> of the generated loot
     */

    public static List<ItemStack> createFilteredLootPool(World world, LootPool lootPool, LootContext lootContext, @Nullable Predicate<Item> filter ) {
        ArtefactEnchantingHandler.filter = filter;
        List<ItemStack> artefacts = new ArrayList<>();
        //Generate a random artefact from the LootPool with the filter
        lootPool.generateLoot(artefacts, world.rand, lootContext);
        ArtefactEnchantingHandler.filter = null;
        return artefacts;
    }

}
