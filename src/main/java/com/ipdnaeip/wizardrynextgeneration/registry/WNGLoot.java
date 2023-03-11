package com.ipdnaeip.wizardrynextgeneration.registry;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import electroblob.wizardry.Wizardry;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.List;

@Mod.EventBusSubscriber
public class WNGLoot {

    private static LootTable UNCOMMON_ARTEFACTS;
    private static LootTable RARE_ARTEFACTS;
    private static LootTable EPIC_ARTEFACTS;
    private static LootTable WIZARDRYNEXTGENERATION_BOOKS_AND_SCROLLS;
    private static LootTable LIBRARY_RUINS_BOOKSHELF;
    private static LootTable OBELISK;
    private static LootTable SHRINE;
    private static LootTable SHRINE_EXTRAS;
    private static LootTable WIZARD_TOWER;

    private WNGLoot() {}

    public static void register(){

        //chests
        LootTableList.register(new ResourceLocation(WizardryNextGeneration.MODID, "chests/dungeon_additions"));

        //subsets
        LootTableList.register(new ResourceLocation(WizardryNextGeneration.MODID, "subsets/uncommon_artefacts"));
        LootTableList.register(new ResourceLocation(WizardryNextGeneration.MODID, "subsets/rare_artefacts"));
        LootTableList.register(new ResourceLocation(WizardryNextGeneration.MODID, "subsets/epic_artefacts"));
        LootTableList.register(new ResourceLocation(WizardryNextGeneration.MODID, "subsets/wizardrynextgeneration_books_and_scrolls"));

        //entities
        LootTableList.register(new ResourceLocation(WizardryNextGeneration.MODID, "entities/webspitter"));

    }

    @SubscribeEvent
    public static void onLootTableLoadEvent(LootTableLoadEvent event) {

        // Fortunately the loot tables load before wizardry, so we can make a static reference to them and reuse it
        if (event.getName().toString().equals(WizardryNextGeneration.MODID + ":subsets/uncommon_artefacts")) {
            UNCOMMON_ARTEFACTS = event.getTable();
        } else if (event.getName().toString().equals(WizardryNextGeneration.MODID + ":subsets/rare_artefacts")) {
            RARE_ARTEFACTS = event.getTable();
        } else if (event.getName().toString().equals(WizardryNextGeneration.MODID + ":subsets/epic_artefacts")) {
            EPIC_ARTEFACTS = event.getTable();
        }

        // Inject books and scrolls to ebwiz tables
        if (event.getName().toString().equals(Wizardry.MODID + ":chests/library_ruins_bookshelf") && LIBRARY_RUINS_BOOKSHELF != null) {
            LootPool targetPool = event.getTable().getPool("wizardry");
            LootPool sourcePool = LIBRARY_RUINS_BOOKSHELF.getPool("WizardryNextGeneration");
            injectEntries(sourcePool, targetPool);
        } else if (event.getName().toString().equals(Wizardry.MODID + ":chests/obelisk") && OBELISK != null) {
            LootPool targetPool = event.getTable().getPool("high_value");
            LootPool sourcePool = OBELISK.getPool("WizardryNextGeneration");
            injectEntries(sourcePool, targetPool);
        } else if (event.getName().toString().equals(Wizardry.MODID + ":chests/shrine") && SHRINE_EXTRAS != null) {
            // add shrine extras
            event.getTable().addPool(SHRINE_EXTRAS.getPool("shrine_extras"));
        } else if (event.getName().toString().equals(Wizardry.MODID + ":chests/wizard_tower") && WIZARD_TOWER != null) {
            LootPool targetPool = event.getTable().getPool("wizardry");
            LootPool sourcePool = WIZARD_TOWER.getPool("WizardryNextGeneration");
            injectEntries(sourcePool, targetPool);
        }

        if (event.getName().toString().equals(Wizardry.MODID + ":chests/shrine") && SHRINE != null) {
            LootPool targetPool = event.getTable().getPool("high_value");
            LootPool sourcePool = SHRINE.getPool("WizardryNextGeneration");
            injectEntries(sourcePool, targetPool);

        }

        // inject artefacts to ebwiz tables
    if (event.getName().toString().equals(Wizardry.MODID + ":subsets/uncommon_artefacts") && UNCOMMON_ARTEFACTS != null) {
            LootPool targetPool = event.getTable().getPool("uncommon_artefacts");
            LootPool sourcePool = UNCOMMON_ARTEFACTS.getPool("main");
            injectEntries(sourcePool, targetPool);
        }
        if (event.getName().toString().equals(Wizardry.MODID + ":subsets/rare_artefacts") && RARE_ARTEFACTS != null) {
            LootPool targetPool = event.getTable().getPool("rare_artefacts");
            LootPool sourcePool = RARE_ARTEFACTS.getPool("main");

            injectEntries(sourcePool, targetPool);
        }
        if (event.getName().toString().equals(Wizardry.MODID + ":subsets/epic_artefacts") && EPIC_ARTEFACTS != null) {
            LootPool targetPool = event.getTable().getPool("epic_artefacts");
            LootPool sourcePool = EPIC_ARTEFACTS.getPool("main");
            injectEntries(sourcePool, targetPool);
        }

    }
    private static void injectEntries(LootPool sourcePool, LootPool targetPool) {
        // Accessing {@link net.minecraft.world.storage.loot.LootPool.lootEntries}
        if (sourcePool != null && targetPool != null) {
            List<LootEntry> lootEntries = ObfuscationReflectionHelper.getPrivateValue(LootPool.class, sourcePool, "field_186453_a");

            for (LootEntry entry : lootEntries) {
                targetPool.addEntry(entry);
            }
        } else {
            WizardryNextGeneration.logger.warn("Attempted to inject to null pool source or target.");
        }

    }

    private static LootPool getAdditive(String entryName, String poolName) {
        return new LootPool(new LootEntry[] {getAdditiveEntry(entryName, 1)}, new LootCondition[0],
                new RandomValueRange(1), new RandomValueRange(0, 1), WizardryNextGeneration.MODID + "_" + poolName);
    }

    private static LootEntryTable getAdditiveEntry(String name, int weight) {
        return new LootEntryTable(new ResourceLocation(name), weight, 0, new LootCondition[0],
                WizardryNextGeneration.MODID + "_additive_entry");
    }


}
