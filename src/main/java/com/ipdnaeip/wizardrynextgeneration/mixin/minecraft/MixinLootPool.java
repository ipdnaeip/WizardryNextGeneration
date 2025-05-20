package com.ipdnaeip.wizardrynextgeneration.mixin.minecraft;

import com.google.common.collect.Lists;
import com.ipdnaeip.wizardrynextgeneration.accessor.LootContextAccessor;
import com.ipdnaeip.wizardrynextgeneration.accessor.LootEntryAccessor;
import com.ipdnaeip.wizardrynextgeneration.accessor.LootEntryItemAccessor;
import com.ipdnaeip.wizardrynextgeneration.accessor.LootPoolAccessor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.conditions.LootConditionManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.List;
import java.util.Random;

@Mixin(LootPool.class)
public abstract class MixinLootPool implements LootPoolAccessor {

    @Shadow @Final private List<LootEntry> lootEntries;

    public List<LootEntry> wizardrynextgeneration$getLootEntries() {
        return this.lootEntries;
    }

    @Inject(method = "createLootRoll", at = @At("HEAD"), cancellable = true)
    public void createLootRoll(Collection<ItemStack> stacks, Random rand, LootContext context, CallbackInfo info) {
        if (((LootContextAccessor)context).wizardrynextgeneration$getFilter() != null) {
            info.cancel();
            List<LootEntry> list = Lists.newArrayList();
            int i = 0;
            for (LootEntry lootentry : this.lootEntries) {
                if (LootConditionManager.testAllConditions(((LootEntryAccessor) lootentry).wizardrynextgeneration$getConditions(), rand, context)) {
                    int j = lootentry.getEffectiveWeight(context.getLuck());
                    if (j > 0) {
                        if (!(lootentry instanceof LootEntryItem) || ((LootContextAccessor)context).wizardrynextgeneration$getFilter().test(((LootEntryItemAccessor)lootentry).wizardrynextgeneration$getItem())) {
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

/*    @ModifyVariable(method = "createLootRoll", at = @At(value = "LOAD"), ordinal = 1)
    public boolean injectCreateLootRoll(boolean b, Collection<ItemStack> stacks, Random rand, LootContext lootContext, LootEntry lootEntry) {
        return b && (((LootContextAccessor)lootContext).wizardrynextgeneration$getFilter() == null || (lootEntry instanceof LootEntryItem && ((LootContextAccessor)lootContext).wizardrynextgeneration$getFilter().test(((LootEntryItemAccessor)lootEntry).wizardrynextgeneration$getItem())));
    }*/

/*    @ModifyVariable(method = "Lnet/minecraft/world/storage/loot/LootPool;createLootRoll(Ljava/util/Collection;Ljava/util/Random;Lnet/minecraft/world/storage/loot/LootContext;)V", at = @At("STORE"), ordinal = 1)
    public int modifyCreateLootRoll(int j, Collection<ItemStack> stacks, Random rand, LootContext context, LootEntry entry) {
        if (((LootContextAccessor)context).wizardrynextgeneration$getFilter() == null || !(entry instanceof LootEntryItem) || ((LootContextAccessor)context).wizardrynextgeneration$getFilter().test(((LootEntryItemAccessor)entry).wizardrynextgeneration$getItem())) {
            return j;
        } else {
            return 0;
        }
    }*/
}
