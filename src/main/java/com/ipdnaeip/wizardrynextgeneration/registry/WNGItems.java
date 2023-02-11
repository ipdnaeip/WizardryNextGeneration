package com.ipdnaeip.wizardrynextgeneration.registry;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.item.ItemBlessedMeat;
import com.ipdnaeip.wizardrynextgeneration.item.ItemWNGArtefact;
import com.ipdnaeip.wizardrynextgeneration.item.ItemWNGSpellBook;
import electroblob.wizardry.block.BlockBookshelf;
import electroblob.wizardry.inventory.ContainerBookshelf;
import electroblob.wizardry.item.ItemArtefact;
import electroblob.wizardry.item.ItemScroll;
import electroblob.wizardry.item.ItemSpellBook;
import electroblob.wizardry.registry.WizardryTabs;
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
    public static final Item blessed_meat = placeholder();

    //magical items
    public static final Item scroll_wng = placeholder();
    public static final Item spell_book_wng = placeholder();

    //amulet
    public static final Item amulet_halfmoon = placeholder();

    //charm

    //head

    public static final Item head_ra = placeholder();

    //ring
    public static final Item ring_9th_circle = placeholder();
    public static final Item ring_anodized = placeholder();
    public static final Item ring_static_shock = placeholder();


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

        //amulet
        registerItem(registry, "amulet_halfmoon", new ItemWNGArtefact(EnumRarity.UNCOMMON, ItemArtefact.Type.AMULET));

        //charm

        //head
        registerItem(registry, "head_ra", new ItemWNGArtefact(EnumRarity.EPIC, ItemArtefact.Type.AMULET));


        //ring
        registerItem(registry, "ring_9th_circle", new ItemWNGArtefact(EnumRarity.UNCOMMON, ItemArtefact.Type.RING));
        registerItem(registry, "ring_anodized", new ItemWNGArtefact(EnumRarity.RARE, ItemArtefact.Type.RING));
        registerItem(registry, "ring_static_shock", new ItemWNGArtefact(EnumRarity.RARE, ItemArtefact.Type.RING));

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