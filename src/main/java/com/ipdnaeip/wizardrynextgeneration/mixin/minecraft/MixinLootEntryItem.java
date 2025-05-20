package com.ipdnaeip.wizardrynextgeneration.mixin.minecraft;

import com.ipdnaeip.wizardrynextgeneration.accessor.LootEntryItemAccessor;
import net.minecraft.item.Item;
import net.minecraft.world.storage.loot.LootEntryItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LootEntryItem.class)
public abstract class MixinLootEntryItem implements LootEntryItemAccessor {

    @Shadow @Final protected Item item;

    @Unique
    public Item wizardrynextgeneration$getItem() {
        return this.item;
    }

}
