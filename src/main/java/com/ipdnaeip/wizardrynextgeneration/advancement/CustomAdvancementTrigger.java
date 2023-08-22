package com.ipdnaeip.wizardrynextgeneration.advancement;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.critereon.AbstractCriterionInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

public class CustomAdvancementTrigger implements ICriterionTrigger<CustomAdvancementTrigger.Instance> {
    private final ResourceLocation id;
    private final SetMultimap<PlayerAdvancements, ICriterionTrigger.Listener<? extends ICriterionInstance>> listeners = HashMultimap.create();

    public CustomAdvancementTrigger(String name) {
        this.id = new ResourceLocation(WizardryNextGeneration.MODID, name);
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public void addListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<Instance> listener) {
        this.listeners.put(playerAdvancementsIn, listener);
    }

    public void removeListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<Instance> listener) {
        this.listeners.remove(playerAdvancementsIn, listener);
    }

    public void removeAllListeners(PlayerAdvancements playerAdvancementsIn) {
        this.listeners.removeAll(playerAdvancementsIn);
    }

    public Instance deserializeInstance(JsonObject json, JsonDeserializationContext context) {
        return new Instance(this.id);
    }

    public void triggerFor(EntityPlayer player) {
        if (player instanceof EntityPlayerMP) {
            PlayerAdvancements advances = ((EntityPlayerMP)player).getAdvancements();
            this.listeners.get(advances).forEach((listener) -> {
                listener.grantCriterion(advances);
            });
        }

    }

    public static class Instance extends AbstractCriterionInstance {
        public Instance(ResourceLocation triggerId) {
            super(triggerId);
        }
    }
}

