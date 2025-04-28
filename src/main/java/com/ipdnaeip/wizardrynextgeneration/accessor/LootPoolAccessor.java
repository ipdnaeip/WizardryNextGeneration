package com.ipdnaeip.wizardrynextgeneration.accessor;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootEntry;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public interface LootPoolAccessor {

    List<LootEntry> wizardrynextgeneration$getLootEntries();

/*    Predicate<Item> wizardrynextgeneration$getFilter();

    void wizardryNextGeneration$setFilter(Predicate<Item> filter);*/

}
