package com.ipdnaeip.wizardrynextgeneration.registry;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.constants.Discipline;
import com.ipdnaeip.wizardrynextgeneration.item.*;
import electroblob.wizardry.block.BlockBookshelf;
import electroblob.wizardry.inventory.ContainerBookshelf;
import electroblob.wizardry.item.ItemArtefact;
import electroblob.wizardry.item.ItemScroll;
import electroblob.wizardry.item.ItemSpellBook;
import electroblob.wizardry.misc.BehaviourSpellDispense;
import electroblob.wizardry.registry.WizardryTabs;
import net.minecraft.block.BlockDispenser;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;

@GameRegistry.ObjectHolder(WizardryNextGeneration.MODID)
@Mod.EventBusSubscriber
public final class WNGItems {

    private WNGItems() {
    }

    @Nonnull
    @SuppressWarnings("ConstantConditions")
    private static <T> T placeholder() {
        return null;
    }

    //misc
    public static final Item battleaxe_iron = placeholder();
    public static final Item blessed_meat = placeholder();
    public static final Item spear_diamond = placeholder();
    public static final Item spear_stone = placeholder();

    //magical items
    public static final Item scroll_wng = placeholder();
    public static final Item spell_book_wng = placeholder();
    public static final Item spell_encyclopedia = placeholder();
    public static final Item tablet_earth = placeholder();

    //amulet
    public static final Item amulet_moon = placeholder();

    //belt
    public static final Item belt_potion = placeholder();

    //body
    public static final Item body_hashashin = placeholder();
    public static final Item body_artemis = placeholder();

    //charm
    public static final Item charm_dice = placeholder();
    public static final Item charm_horn = placeholder();

    //head
    public static final Item head_hashashin = placeholder();
    public static final Item head_ra = placeholder();
    public static final Item head_raijin = placeholder();

    //ring
    public static final Item ring_9th_circle = placeholder();
    public static final Item ring_anodized = placeholder();
    public static final Item ring_nullification = placeholder();
    public static final Item ring_static_shock = placeholder();
    public static final Item ring_void = placeholder();

    // below registry methods are courtesy of EB
    public static void registerItem(IForgeRegistry<Item> registry, String name, Item item) {
        registerItem(registry, name, item, false);
    }

    // below registry methods are courtesy of EB
    public static void registerItem(IForgeRegistry<Item> registry, String name, String modid, Item item) {
        registerItem(registry, name, modid, item, false);
    }

    public static void registerItem(IForgeRegistry<Item> registry, String name, Item item, boolean setTabIcon) {
        item.setRegistryName(WizardryNextGeneration.MODID, name);
        item.setTranslationKey(item.getRegistryName().toString());
        registry.register(item);

        if (setTabIcon && item.getCreativeTab() instanceof WizardryTabs.CreativeTabSorted) {
            ((WizardryTabs.CreativeTabSorted) item.getCreativeTab()).setIconItem(new ItemStack(item));
        }

        if (item.getCreativeTab() instanceof WizardryTabs.CreativeTabListed) {
            ((WizardryTabs.CreativeTabListed) item.getCreativeTab()).order.add(item);
        }
    }

    public static void registerItem(IForgeRegistry<Item> registry, String modid, String name, Item item, boolean setTabIcon) {
        item.setRegistryName(modid, name);
        item.setTranslationKey(item.getRegistryName().toString());
        registry.register(item);

        if (setTabIcon && item.getCreativeTab() instanceof WizardryTabs.CreativeTabSorted) {
            ((WizardryTabs.CreativeTabSorted) item.getCreativeTab()).setIconItem(new ItemStack(item));
        }

        if (item.getCreativeTab() instanceof WizardryTabs.CreativeTabListed) {
            ((WizardryTabs.CreativeTabListed) item.getCreativeTab()).order.add(item);
        }
    }

    @SubscribeEvent
    public static void register(RegistryEvent.Register<Item> event) {

        IForgeRegistry<Item> registry = event.getRegistry();

        //misc
        registerItem(registry, "blessed_meat", new ItemBlessedMeat());

        //magical items
        registerItem(registry, "scroll_wng", new ItemScroll());
        registerItem(registry,"spell_book_wng", new ItemWNGSpellBook());
        registerItem(registry, "spell_encyclopedia", new ItemSpellEncyclopedia());

        //amulet
        registerItem(registry, "amulet_moon", new ItemAmuletMoon(EnumRarity.EPIC, ItemArtefact.Type.AMULET));

        //belt
        registerItem(registry, "belt_potion", new ItemBeltPotion(EnumRarity.RARE, ItemNewArtefact.AdditionalType.BELT));

        //body
        registerItem(registry, "body_artemis", new ItemNewArtefact(EnumRarity.RARE, ItemNewArtefact.AdditionalType.BODY));
        registerItem(registry, "body_hashashin", new ItemNewArtefact(EnumRarity.RARE, ItemNewArtefact.AdditionalType.BODY));

        //charm
        registerItem(registry, "charm_dice", new ItemWNGArtefact(EnumRarity.EPIC, ItemArtefact.Type.CHARM));
        registerItem(registry, "charm_horn", new ItemCharmHorn(EnumRarity.EPIC, ItemArtefact.Type.CHARM));

        //head
        registerItem(registry, "head_hashashin", new ItemNewArtefact(EnumRarity.RARE, ItemNewArtefact.AdditionalType.HEAD));
        registerItem(registry, "head_ra", new ItemNewArtefact(EnumRarity.EPIC, ItemNewArtefact.AdditionalType.HEAD));
        registerItem(registry, "head_raijin", new ItemNewArtefact(EnumRarity.EPIC, ItemNewArtefact.AdditionalType.HEAD));

        //ring
        registerItem(registry, "ring_9th_circle", new ItemWNGArtefact(EnumRarity.UNCOMMON, ItemArtefact.Type.RING));
        registerItem(registry, "ring_anodized", new ItemWNGArtefact(EnumRarity.RARE, ItemArtefact.Type.RING));
        registerItem(registry, "ring_nullification", new ItemWNGArtefact(EnumRarity.RARE, ItemArtefact.Type.RING));
        registerItem(registry, "ring_static_shock", new ItemWNGArtefact(EnumRarity.RARE, ItemArtefact.Type.RING));
        registerItem(registry, "ring_void", new ItemWNGArtefact(EnumRarity.UNCOMMON, ItemArtefact.Type.RING));


    }

    public static void registerDispenseBehaviours(){
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(scroll_wng, new BehaviourSpellDispense());
    }

    public static void registerBookItems(){
        ContainerBookshelf.registerBookItem(WNGItems.spell_book_wng);
        ContainerBookshelf.registerBookItem(WNGItems.scroll_wng);
    }

    public static void registerBookshelfModelTextures(){
        BlockBookshelf.registerBookModelTexture(() -> WNGItems.spell_book_wng, new ResourceLocation(WizardryNextGeneration.MODID, "blocks/books_wng"));
        BlockBookshelf.registerBookModelTexture(() -> WNGItems.scroll_wng, new ResourceLocation(WizardryNextGeneration.MODID, "blocks/scrolls_wng"));
    }

}