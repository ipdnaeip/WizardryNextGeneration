package com.ipdnaeip.wizardrynextgeneration.accessor;

import net.minecraft.item.Item;

import java.util.function.Predicate;

public interface LootContextAccessor {

    Predicate<Item> wizardrynextgeneration$getFilter();

    void wizardrynextgeneration$setFilter(Predicate<Item> filter);

}
