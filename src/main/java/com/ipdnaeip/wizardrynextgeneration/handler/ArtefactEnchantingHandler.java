package com.ipdnaeip.wizardrynextgeneration.handler;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.*;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.List;
import java.util.function.Predicate;

public class ArtefactEnchantingHandler {

    /**
     * This method filters items out of a LootPool so that a specific LootPool can be made from which to generate loot.
     * @param world The world from which to obtain the LootTable.
     * @param lootPool The LootPool to filter.
     * @param filter The predicate to determine which items are filtered. I use a static method to create the predicate,
     *               though there may be a better way to do so that I have no learned yet.
     */
    /*public static void filterLootPool(World world, LootPool lootPool, Predicate<Item> filter) {
        if (lootPool != null) {
            //Access the private lootEntries in LootPool and remove specific item entries from the loot pool
            for (LootEntry entry : lootPool.lootEntries) {
                if (entry instanceof LootEntryItem) {
                    LootEntryItem entryItem = (LootEntryItem)entry;
                    Item item = entryItem.item;
                    // Filter out items that do not match the predicate
                    if (filter.test(item)) {
                        lootPool.lootEntries.remove(entry);
                        break;
                    }
                } else if (entry instanceof LootEntryTable) {
                    LootEntryTable tableEntry = (LootEntryTable)entry;
                    ResourceLocation tableLocation = tableEntry.table;
                    LootTable nestedTable = world.getLootTableManager().getLootTableFromLocation(tableLocation);
                    if (nestedTable != LootTable.EMPTY_LOOT_TABLE) {
                        for (LootPool nestedPool : nestedTable.pools) {
                            filterLootPool(world, nestedPool, filter);
                        }
                    }
                }
            }
        }
    }*/

    /**
     * This method filters items out of a LootPool so that a specific LootPool can be made from which to generate loot.
     * @param world The world from which to obtain the LootTable.
     * @param lootPool The LootPool to filter.
     * @param filter The predicate to determine which items are filtered. I use a static method to create the predicate,
     *               though there may be a better way to do so that I have no learned yet.
     */
    public static void filterLootPool(World world, LootPool lootPool, Predicate<Item> filter) {
        if (lootPool != null) {
            //Access the private lootEntries in LootPool and remove specific item entries from the loot pool
            for (LootEntry entry : getLootEntryList(lootPool)) {
                if (entry instanceof LootEntryItem) {
                    LootEntryItem entryItem = (LootEntryItem)entry;
                    Item item = getLootEntryItemItem(entryItem);
                    // Filter out items that do not match the predicate
                    if (filter.test(item)) {
                        getLootEntryList(lootPool).remove(entry);
                        System.out.println(entry);
                        break;
                    }
                } else if (entry instanceof LootEntryTable) {
                    LootEntryTable tableEntry = (LootEntryTable)entry;
                    ResourceLocation tableLocation = getLootEntryTableTable(tableEntry);
                    LootTable nestedTable = world.getLootTableManager().getLootTableFromLocation(tableLocation);
                    if (nestedTable != LootTable.EMPTY_LOOT_TABLE) {
                        for (LootPool nestedPool : getLootPoolList(nestedTable)) {
                            filterLootPool(world, nestedPool, filter);
                        }
                    }
                }
            }
        }
    }

    /**
     * This method filters items out of a LootPool so that a specific LootPool can be made from which to generate loot.
     * @param world The world from which to obtain the LootTable.
     * @param lootPoolIn The LootPool to filter.
     * @param filter The predicate to determine which items are filtered. I use a static method to create the predicate,
     *               though there may be a better way to do so that I have no learned yet.
     */
    public static void createFilteredLootPool(World world, LootPool lootPoolIn, Predicate<Item> filter) {
        if (lootPoolIn != null) {
            //Access the private lootEntries in LootPool and remove specific item entries from the loot pool
            for (LootEntry entry : getLootEntryList(lootPoolIn)) {
                if (entry instanceof LootEntryItem) {
                    LootEntryItem entryItem = (LootEntryItem)entry;
                    Item item = getLootEntryItemItem(entryItem);
                    // Filter out items that do not match the predicate
                    if (filter.test(item)) {
                        getLootEntryList(lootPoolIn).remove(entry);
                        System.out.println(entry);
                        break;
                    }
                } else if (entry instanceof LootEntryTable) {
                    LootEntryTable tableEntry = (LootEntryTable)entry;
                    ResourceLocation tableLocation = getLootEntryTableTable(tableEntry);
                    LootTable nestedTable = world.getLootTableManager().getLootTableFromLocation(tableLocation);
                    if (nestedTable != LootTable.EMPTY_LOOT_TABLE) {
                        for (LootPool nestedPool : getLootPoolList(nestedTable)) {
                            createFilteredLootPool(world, nestedPool, filter);
                        }
                    }
                }
            }
        }
    }

    private static List<LootPool> getLootPoolList(LootTable lootTable) {
        return ObfuscationReflectionHelper.getPrivateValue(LootTable.class, lootTable, "field_186466_c");
    }

    private static List<LootEntry> getLootEntryList(LootPool lootPool) {
        return ObfuscationReflectionHelper.getPrivateValue(LootPool.class, lootPool, "field_186453_a");
    }

    private static Item getLootEntryItemItem(LootEntryItem lootEntryItem) {
        return ObfuscationReflectionHelper.getPrivateValue(LootEntryItem.class, lootEntryItem, "field_186368_a");
    }

    private static ResourceLocation getLootEntryTableTable(LootEntryTable lootEntryTable) {
        return ObfuscationReflectionHelper.getPrivateValue(LootEntryTable.class, lootEntryTable, "field_186371_a");
    }

}
