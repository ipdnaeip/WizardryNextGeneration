package com.ipdnaeip.wizardrynextgeneration.util;

import electroblob.wizardry.Wizardry;
import electroblob.wizardry.item.ItemArtefact;
import electroblob.wizardry.spell.Spell;
import electroblob.wizardry.util.SpellProperties;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class GeneratorSnippets {

    public GeneratorSnippets() {
    }

    public static void generateAll(String modid, String modDisplayName, String githubRepo, String artefactCreativeTab) {
        writeSpellPages(modid, modDisplayName, githubRepo);
        writeSpellSummaryPage(modid, modDisplayName, githubRepo);
        writeArtefactSummaryPage(modid, modDisplayName, githubRepo, artefactCreativeTab);
    }

    public static void writeSpellPages(String modid, String modDisplayName, String githubRepo) {
        createFolderIfNotExists();
        int i = 1;

        for (Spell spell : Spell.getSpells((spell) -> spell.getRegistryName().getNamespace().equals(modid))) {
            try {
                FileWriter writer = new FileWriter(".\\generated\\" + spell.getDisplayName().replace(' ', '-') + ".md");
                writer.write(
                        "A spell added by " + modDisplayName + "\n" +
                                "| " + spell.getDisplayName() + " |![](https://github.com/" + githubRepo + "/src/main/resources/assets/" + modid + "/textures/spells/" + spell.getRegistryName().getPath() + ".png)"
                                + "|\n|---|---|\n| Tier | " + spell.getTier().getDisplayName() + " |\n"
                                + "| Element | " + spell.getElement().getDisplayName() + " |\n"
                                + "| Type | " + spell.getType().getDisplayName() + " |\n"
                                + "| Mana Cost | " + spell.getCost() + " |\n"
                                + "| Continuous | " + (spell.isContinuous ? "Yes" : "No") + " |\n"
                                + "| Cast by wizards | " + (spell.isEnabled(SpellProperties.Context.NPCS) ? "Yes" : "No") + " |\n"
                                + "| Cast by dispensers | " + ((spell.isEnabled(SpellProperties.Context.DISPENSERS) && spell.canBeCastBy(new TileEntityDispenser())) ? "Yes" : "No") + " |\n"
                                + "| ID | `" + spell.getRegistryName() + "` |\n"
                                + "| Metadata | " + i++ + " "
                                + "|\n## Description\n_" + spell.getDescription() + "_");
                writer.close();
            }
            catch (IOException exception) {
                exception.printStackTrace();
            }
        }

    }

    public static void writeSpellSummaryPage(String modid, String modDisplayName, String githubRepo) {
        int i = 1;
        List<Spell> spells = Spell.getSpells((spell) -> spell.getRegistryName().getNamespace().equals(modid));
        FileWriter writer = null;
        try {
            createFolderIfNotExists();
            writer = new FileWriter(".\\generated\\Spells.md");
            writer.write(
                modDisplayName + " adds " + spells.size() + " new spells. This page is a list of all the spells currently in the addon, along with their descriptions and various statistics. Click on the links to the pages (the spell's name) for each spell for more details.\n"
                + "\n"
                + "\n"
                + "> **Spoiler alert!** The information in the table below may spoil your enjoyment of the mod, as it contains details of spells you may not have discovered. I recommend playing the mod for a while before reading about all the spells; discovering spells you have never seen before is a big part of the gameplay. If you have played the mod, or don't really mind spoilers, then read on...\n"
                + "---\n"
                + "\n"
                + "### Key to headings\n"
                + "\n"
                + "1. **#** The order of the spells as shown in the creative tab\n"
                + "1. **Icon** The spell's icon as displayed in its spell book and on the spell HUD\n"
                + "1. **Name** The name of the spell in English\n"
                + "1. **Tier** The tier that the spell belongs to\n"
                + "1. **Element** The element that the spell belongs to\n"
                + "1. **Type** The type of spell - attack, defence, utility, etc.\n"
                + "1. **Mana** The mana cost of the spell\n"
                + "1. **Continuous?** Whether the spell requires the use item button to be held in order to cast it\n"
                + "1. **Wizards?** Whether wizard NPCs can cast this spell\n"
                + "1. **Dispensers?** Whether dispensers can cast this spell\n"
                + "1. **Since** The major version of Wizardry Next Generation in which the spell was first introduced\n"
                + "\n"
                + "> Pro tip! Use shift + scroll wheel to see more columns.\n\n" +
                "| #   | Icon | Name                       | Tier       | Element    | Type       | Mana      | Chargeup | Cooldown | Continuous? | Wizards? | Dispensers? |\n"
                + "|-----|------|----------------------------|------------|------------|------------|-----------|-------------|----------|----------|----------|-------------|\n"
            );
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
        //List<Spell> sortedList = spells.stream().sorted(Comparator.comparing(Spell::metadata)).collect(Collectors.toList());
        List<Spell> sortedList = spells.stream().sorted(Comparator.comparing(Spell::getElement)).sorted(Comparator.comparing(Spell::getTier)).collect(Collectors.toList());
        for (Spell spell : sortedList) {
            try {
                writer.write(
                    "| " + i++
                    + " | " + "![](https://github.com/" + githubRepo + "/src/main/resources/assets/" + modid + "/textures/spells/" + spell.getRegistryName().getPath() + ".png)"
                    + " | " + "[[" + spell.getDisplayName() + "]]"
                    + " | " + spell.getTier().getDisplayName()
                    + " | " + spell.getElement().getDisplayName()
                    + " | " + spell.getType().getDisplayName()
                    + " | " + spell.getCost()
                    + " | " + spell.getChargeup()
                    + " | " + spell.getCooldown()
                    + " | " + (spell.isContinuous ? "Yes" : "No")
                    + " | " + (spell.isEnabled(SpellProperties.Context.NPCS) ? "Yes" : "No")
                    + " | " + (spell.isEnabled(SpellProperties.Context.DISPENSERS) && spell.canBeCastBy(new TileEntityDispenser()) ? "Yes" : "No") + "|\n"
                );
            }
            catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        try {
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeArtefactSummaryPage(String modid, String modDisplayName, String githubRepo, String creativeTab) {
        int i = 1;
        List<Item> artefacts = ForgeRegistries.ITEMS.getValuesCollection().stream()
                .filter((item) -> item.getRegistryName().getNamespace().equals(modid))
                .filter((item) -> (item instanceof ItemArtefact))
                //.sorted(Comparator.comparing(Item::getIdFromItem))
                .sorted(Comparator.comparing(i2 -> i2.getRegistryName().getPath()))
                .collect(Collectors.toList());
        Iterator<Item> artefactIterator = artefacts.iterator();

        FileWriter writer = null;
        try {
            createFolderIfNotExists();
            writer = new FileWriter(".\\generated\\Artefacts.md");
            writer.write(
                "| Artefacts | |\n"
                + "|---|---|\n"
                + "| Craftable\t|No|\n"
                + "| Stackable\t|No|\n"
                + "| Creative Tab | " + creativeTab + " |\n"
                + "| Item ID |" + modid + ":[ring/amulet/charm/belt/body/head]_[name] (indicative rule, might not be followed by all items)|\n"
                + "\n"
                + "> When exploring a shrine, you may be lucky enough to uncover some kind of ancient magical artefact - an object capable of granting its wearer unique and powerful buffs and special powers.\n"
                + "\n"
                + "~ The Wizard's Handbook\n"
                + "\n"
                + modDisplayName + " currently adds " + artefacts.size() + " artefacts. These items can be found [the same way](https://github.com/Electroblob77/Wizardry/wiki/Artefacts#obtaining) you can find default artefacts in Wizardry. For more info about the artefact items, refer to [Electroblob's Wiki](https://github.com/Electroblob77/Wizardry/wiki/Artefacts).\n"
                + "\n"
                + "## List of Artefacts"
                + "\n"
                + "\n"
                + "| Icon | Name | Type | Rarity | Effect description |" + "\n"
                + "|---|---|---|---|---|" + "\n"

            );

        }
        catch (IOException exception) {
            exception.printStackTrace();
        }

        while (artefactIterator.hasNext()) {
            Item item = artefactIterator.next();
            try {
                writer.write(
                    "| " + i++ +
                    " | " + "![](https://github.com/" + githubRepo + "/src/main/resources/assets/" + modid + "/textures/items/" + item.getRegistryName().getPath() + ".png)"
                    + " | " + toTitleCase((((ItemArtefact)item).getType().name().toLowerCase()))
                    + " | " + item.getItemStackDisplayName(new ItemStack(item))
                    + " | " + item.getRarity(new ItemStack(item)).getName()
                    + " | "  + Wizardry.proxy.translate("item." + item.getRegistryName().toString() + ".desc").replace("\n", "<br />")
                    + " |\n"
                );
            }
            catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        try {
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String toTitleCase(String input) {
        input = input.toLowerCase();
        char c = input.charAt(0);
        String s = "" + c;
        String f = s.toUpperCase();
        return f + input.substring(1);
    }

    public static void createFolderIfNotExists() {
        File dir = new File(".\\generated");
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
}
