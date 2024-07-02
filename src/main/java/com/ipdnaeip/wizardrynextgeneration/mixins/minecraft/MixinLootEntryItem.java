package com.ipdnaeip.wizardrynextgeneration.mixins.minecraft;

import com.ipdnaeip.wizardrynextgeneration.mixininterfaces.minecraft.IMixinLootEntryItem;
import net.minecraft.item.Item;
import net.minecraft.world.storage.loot.LootEntryItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LootEntryItem.class)
public abstract class MixinLootEntryItem implements IMixinLootEntryItem {

    @Shadow @Final protected Item item;

    @Unique
    public Item getItem() {
        return this.item;
    }

}
