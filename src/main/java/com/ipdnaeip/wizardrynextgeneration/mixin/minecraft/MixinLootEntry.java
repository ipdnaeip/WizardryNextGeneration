package com.ipdnaeip.wizardrynextgeneration.mixin.minecraft;

import com.ipdnaeip.wizardrynextgeneration.accessor.LootEntryAccessor;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LootEntry.class)
public abstract class MixinLootEntry implements LootEntryAccessor {

    @Shadow @Final protected LootCondition[] conditions;

    @Unique
    public LootCondition[] wizardrynextgeneration$getConditions() {
        return this.conditions;
    }

}
