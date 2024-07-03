package com.ipdnaeip.wizardrynextgeneration.mixininterfaces.minecraft;

import net.minecraft.item.Item;

import java.util.function.Predicate;

public interface IMixinLootPool {

    Predicate<Item> wizardryNextGeneration$getFilter();

    void wizardryNextGeneration$setFilter(Predicate<Item> filter);

}
