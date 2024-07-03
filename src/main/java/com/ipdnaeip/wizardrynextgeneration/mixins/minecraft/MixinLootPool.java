package com.ipdnaeip.wizardrynextgeneration.mixins.minecraft;

import com.google.common.collect.Lists;
import com.ipdnaeip.wizardrynextgeneration.handler.ArtefactEnchantingHandler;
import com.ipdnaeip.wizardrynextgeneration.mixininterfaces.minecraft.IMixinLootEntry;
import com.ipdnaeip.wizardrynextgeneration.mixininterfaces.minecraft.IMixinLootEntryItem;
import com.ipdnaeip.wizardrynextgeneration.mixininterfaces.minecraft.IMixinLootPool;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.conditions.LootConditionManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

@Mixin(LootPool.class)
public abstract class MixinLootPool implements IMixinLootPool {

    @Shadow @Final private List<LootEntry> lootEntries;
    @Unique
    private Predicate<Item> wizardryNextGeneration$filter;

    @Unique
    public Predicate<Item> wizardryNextGeneration$getFilter() {
        return wizardryNextGeneration$filter;
    }

    @Unique
    public void wizardryNextGeneration$setFilter(Predicate<Item> filter) {
        this.wizardryNextGeneration$filter = filter;
    }

    @Inject(method = "createLootRoll", at = @At("HEAD"), cancellable = true)
    public void createLootRoll(Collection<ItemStack> stacks, Random rand, LootContext context, CallbackInfo info) {
        if (ArtefactEnchantingHandler.filter != null) {
            info.cancel();
            List<LootEntry> list = Lists.newArrayList();
            int i = 0;
            for (LootEntry lootentry : this.lootEntries) {
                if (LootConditionManager.testAllConditions(((IMixinLootEntry) lootentry).wizardryNextGeneration$getConditions(), rand, context)) {
                    int j = lootentry.getEffectiveWeight(context.getLuck());
                    if (j > 0) {
                        if (!(lootentry instanceof LootEntryItem && ArtefactEnchantingHandler.filter.test(((IMixinLootEntryItem) lootentry).wizardryNextGeneration$getItem()))) {
                            list.add(lootentry);
                            i += j;
                        }
                    }
                }
            }
            if (i != 0 && !list.isEmpty()) {
                int k = rand.nextInt(i);
                for (LootEntry lootentry1 : list) {
                    k -= lootentry1.getEffectiveWeight(context.getLuck());
                    if (k < 0) {
                        lootentry1.addLoot(stacks, rand, context);
                        return;
                    }
                }
            }
        }
    }
}
