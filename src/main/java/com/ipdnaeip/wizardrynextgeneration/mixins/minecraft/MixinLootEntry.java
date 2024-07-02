package com.ipdnaeip.wizardrynextgeneration.mixins.minecraft;

import com.ipdnaeip.wizardrynextgeneration.mixininterfaces.minecraft.IMixinLootEntry;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LootEntry.class)
public class MixinLootEntry implements IMixinLootEntry {

    @Shadow @Final protected LootCondition[] conditions;

    @Unique
    public LootCondition[] getConditions() {
        return this.conditions;
    }

}
