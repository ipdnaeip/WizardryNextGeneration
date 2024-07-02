package com.ipdnaeip.wizardrynextgeneration.mixininterfaces.minecraft;

import net.minecraft.item.Item;

import java.util.function.Predicate;

public interface IMixinLootPool {

    Predicate<Item> getFilter();

    void setFilter(Predicate<Item> filter);

}
