package com.ipdnaeip.wizardrynextgeneration.registry;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.accessor.LootPoolAccessor;
import electroblob.wizardry.Wizardry;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.List;

@Mod.EventBusSubscriber
public class WNGLoot {

    private WNGLoot() {}

    public static void register(){

        //chests
        LootTableList.register(new ResourceLocation(WizardryNextGeneration.MODID, "chests/dungeon_additions"));

        //subsets
        LootTableList.register(new ResourceLocation(WizardryNextGeneration.MODID, "subsets/uncommon_artefacts"));
        LootTableList.register(new ResourceLocation(WizardryNextGeneration.MODID, "subsets/rare_artefacts"));
        LootTableList.register(new ResourceLocation(WizardryNextGeneration.MODID, "subsets/epic_artefacts"));
        LootTableList.register(new ResourceLocation(WizardryNextGeneration.MODID, "subsets/wand_upgrades"));
        LootTableList.register(new ResourceLocation(WizardryNextGeneration.MODID, "subsets/wizardrynextgeneration_books_and_scrolls"));

        //entities
        LootTableList.register(new ResourceLocation(WizardryNextGeneration.MODID, "entities/webspitter"));

    }

    @SubscribeEvent
    public static void onLootTableLoadEvent(LootTableLoadEvent event) {
        // inject artefacts to ebwiz tables
        if (event.getName().equals(new ResourceLocation(Wizardry.MODID, "subsets/epic_artefacts"))) {
            LootTable lootTable = event.getLootTableManager().getLootTableFromLocation(new ResourceLocation(WizardryNextGeneration.MODID, "subsets/epic_artefacts"));
            LootPool targetPool = event.getTable().getPool("epic_artefacts");
            LootPool sourcePool = lootTable.getPool("main");
            injectEntries(sourcePool, targetPool);
        }
        if (event.getName().equals(new ResourceLocation(Wizardry.MODID, "subsets/rare_artefacts"))) {
            LootTable lootTable = event.getLootTableManager().getLootTableFromLocation(new ResourceLocation(WizardryNextGeneration.MODID, "subsets/rare_artefacts"));
            LootPool targetPool = event.getTable().getPool("rare_artefacts");
            LootPool sourcePool = lootTable.getPool("main");
            injectEntries(sourcePool, targetPool);
        }
        if (event.getName().equals(new ResourceLocation(Wizardry.MODID, "subsets/uncommon_artefacts"))) {
            LootTable lootTable = event.getLootTableManager().getLootTableFromLocation(new ResourceLocation(WizardryNextGeneration.MODID, "subsets/uncommon_artefacts"));
            LootPool targetPool = event.getTable().getPool("uncommon_artefacts");
            LootPool sourcePool = lootTable.getPool("main");
            injectEntries(sourcePool, targetPool);
        }
    }

    private static void injectEntries(LootPool sourcePool, LootPool targetPool) {
        if (sourcePool != null && targetPool != null) {
            List<LootEntry> lootEntries = ((LootPoolAccessor)sourcePool).wizardrynextgeneration$getLootEntries();
            for (LootEntry entry : lootEntries) {
                targetPool.addEntry(entry);
            }
        }
    }

/*    private static void injectEntries(LootPool sourcePool, LootPool targetPool) {
        // Accessing {@link net.minecraft.world.storage.loot.LootPool.lootEntries}
        if (sourcePool != null && targetPool != null) {
            List<LootEntry> lootEntries = ObfuscationReflectionHelper.getPrivateValue(LootPool.class, sourcePool, "field_186453_a");

            for (LootEntry entry : lootEntries) {
                targetPool.addEntry(entry);
            }
        } else {
            WizardryNextGeneration.logger.warn("Attempted to inject to null pool source or target.");
        }
    }*/

}
