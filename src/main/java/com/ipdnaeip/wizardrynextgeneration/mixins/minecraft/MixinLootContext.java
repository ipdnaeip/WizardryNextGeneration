package com.ipdnaeip.wizardrynextgeneration.mixins.minecraft;

import com.ipdnaeip.wizardrynextgeneration.accessor.LootContextAccessor;
import net.minecraft.item.Item;
import net.minecraft.world.storage.loot.LootContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.Predicate;

@Mixin(LootContext.class)
public abstract class MixinLootContext implements LootContextAccessor {

    @Unique
    public Predicate<Item> filter;

    public Predicate<Item> wizardrynextgeneration$getFilter() {
        return this.filter;
    }

    public void wizardrynextgeneration$setFilter(Predicate<Item> filter) {
        this.filter = filter;
    }

}
