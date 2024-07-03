package com.ipdnaeip.wizardrynextgeneration.mixininterfaces.minecraft;

import net.minecraft.world.storage.loot.conditions.LootCondition;

public interface IMixinLootEntry {
    LootCondition[] wizardryNextGeneration$getConditions();
}
