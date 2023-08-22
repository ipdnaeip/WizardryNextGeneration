package com.ipdnaeip.wizardrynextgeneration.constants;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import electroblob.wizardry.Wizardry;
import javax.annotation.Nullable;

import electroblob.wizardry.constants.Element;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

import java.util.*;

import static com.ipdnaeip.wizardrynextgeneration.constants.DisciplineElements.*;

public class Discipline implements IStringSerializable {

/*    DEMON(TextFormatting.DARK_RED, DEMON_ELEMENTS, "demon"),
    DRACO(TextFormatting.RED, DRACO_ELEMENTS, "draco"),
    DRUID(TextFormatting.GREEN, DRUID_ELEMENTS, "druid"),
    HOLY(TextFormatting.GOLD, HOLY_ELEMENTS, "holy"),
    LICH(TextFormatting.AQUA, LICH_ELEMENTS, "lich"),
    LUNAR(TextFormatting.WHITE, LUNAR_ELEMENTS, "lunar"),
    NATURE(TextFormatting.DARK_AQUA, NATURE_ELEMENTS, "nature"),
    OCCULT(TextFormatting.GRAY, OCCULT_ELEMENTS, "occult"),
    SHAMAN(TextFormatting.DARK_BLUE, SHAMAN_ELEMENTS, "shaman"),
    TERRA(TextFormatting.DARK_GREEN, TERRA_ELEMENTS, "terra"),
    VAMPIRE(TextFormatting.DARK_PURPLE, VAMPIRE_ELEMENTS, "vampire"),
    VOID(TextFormatting.BLACK, VOID_ELEMENTS, "void"),
    WITCH(TextFormatting.LIGHT_PURPLE, WITCH_ELEMENTS, "witch");*/

    private final Style color;
    private final Set<Element> elements;
    private int level;
    private final String unlocalisedName;
    private final ResourceLocation icon;


    Discipline(TextFormatting textFormatting, Set<Element> elements, String name) {
        this(textFormatting, elements, name, WizardryNextGeneration.MODID);
    }

    Discipline(TextFormatting textFormatting, Set<Element> elements, String name, String modid) {
        this.color = new Style().setColor(textFormatting);
        this.elements = elements;
        this.level = 0;
        this.unlocalisedName = name;
        this.icon = new ResourceLocation(modid, "textures/gui/container/discipline_icon_" + this.unlocalisedName + ".png");
    }

    public static Discipline ALL = new Discipline(TextFormatting.WHITE, ALL_ELEMENTS, "all");
    public static Discipline DEMON = new Discipline(TextFormatting.DARK_RED, DEMON_ELEMENTS, "demon");
    public static Discipline DRACO = new Discipline(TextFormatting.RED, DRACO_ELEMENTS, "draco");
    public static Discipline DRUID = new Discipline(TextFormatting.GREEN, DRUID_ELEMENTS,  "druid");
    public static Discipline EARTH = new Discipline(TextFormatting.DARK_GREEN, EARTH_ELEMENTS, "earth");
    public static Discipline FIRE = new Discipline(TextFormatting.DARK_RED, FIRE_ELEMENTS,  "fire");
    public static Discipline HEALING = new Discipline(TextFormatting.YELLOW, HEALING_ELEMENTS,  "healing");
    public static Discipline HOLY = new Discipline(TextFormatting.GOLD, HOLY_ELEMENTS,  "holy");
    public static Discipline ICE = new Discipline(TextFormatting.AQUA, ICE_ELEMENTS,  "ice");
    public static Discipline LICH = new Discipline(TextFormatting.AQUA, LICH_ELEMENTS, "lich");
    public static Discipline LIGHTNING = new Discipline(TextFormatting.DARK_AQUA, LIGHTNING_ELEMENTS, "lightning");
    public static Discipline LUNAR = new Discipline(TextFormatting.GRAY, LUNAR_ELEMENTS, "lunar");
    public static Discipline NATURE = new Discipline(TextFormatting.DARK_AQUA, NATURE_ELEMENTS, "nature");
    public static Discipline NECROMANCY = new Discipline(TextFormatting.DARK_PURPLE, NECROMANCY_ELEMENTS, "necromancy");
    public static Discipline OCCULT = new Discipline(TextFormatting.DARK_GRAY, OCCULT_ELEMENTS, "occult");
    public static Discipline SHAMAN = new Discipline(TextFormatting.DARK_BLUE, SHAMAN_ELEMENTS, "shaman");
    public static Discipline SORCERY = new Discipline(TextFormatting.GREEN, SORCERY_ELEMENTS, "sorcery");
    public static Discipline TERRA = new Discipline(TextFormatting.DARK_GREEN, TERRA_ELEMENTS, "terra");
    public static Discipline VAMPIRE = new Discipline(TextFormatting.DARK_PURPLE, VAMPIRE_ELEMENTS, "vampire");
    public static Discipline VOID = new Discipline(TextFormatting.BLACK, VOID_ELEMENTS, "void");
    public static Discipline WITCH = new Discipline(TextFormatting.LIGHT_PURPLE, WITCH_ELEMENTS, "witch");

    public String getDisplayName() {
        return Wizardry.proxy.translate("discipline." + this.getName());
    }

    public Style getColor() {
        return this.color;
    }

    public String getFormattingCode() {
        return this.color.getFormattingCode();
    }

    public String getName() {
        return this.unlocalisedName;
    }

    public ResourceLocation getIcon() {
        return this.icon;
    }

    public int getLevel() {
        return level;
    }

    public void addLevel(int level) {
        this.level += level;
    }

    public void incrementLevel() { this.level++; }

    public void setLevel(int level) { this.level = level; }

    public int getMaxCharge() {
        return 10 + (level * 5);
    }

    public int getLevelProgression() {
        //return 250 + (level * 25);
        return 100 + (level * 10);
    }

    public HashSet<Element> getElements() {
        return new HashSet<>(elements);
    }
}

