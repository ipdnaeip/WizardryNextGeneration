package com.ipdnaeip.wizardrynextgeneration.registry;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.item.*;
import electroblob.wizardry.block.BlockBookshelf;
import electroblob.wizardry.inventory.ContainerBookshelf;
import electroblob.wizardry.item.ItemArtefact;
import electroblob.wizardry.item.ItemScroll;
import electroblob.wizardry.misc.BehaviourSpellDispense;
import electroblob.wizardry.registry.WizardryTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
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

    //misc
    public static final Item BLESSED_MEAT = new ItemBlessedMeat();

    //magical items
    public static final Item CONJURED_POTION = new ItemConjuredPotion();
    public static final Item ENCHANTABLE_AMULET = new ItemEnchantableArtefact(ItemArtefact.Type.AMULET);
    public static final Item ENCHANTABLE_BELT = new ItemEnchantableArtefact(ItemArtefact.Type.BELT);
    public static final Item ENCHANTABLE_BODY = new ItemEnchantableArtefact(ItemArtefact.Type.BODY);
    public static final Item ENCHANTABLE_CHARM = new ItemEnchantableArtefact(ItemArtefact.Type.CHARM);
    public static final Item ENCHANTABLE_HEAD = new ItemEnchantableArtefact(ItemArtefact.Type.HEAD);
    public static final Item ENCHANTABLE_RING = new ItemEnchantableArtefact(ItemArtefact.Type.RING);
    public static final Item ENCHANTABLE_TRINKET = new ItemEnchantableArtefact(ItemArtefact.Type.TRINKET);
    public static final Item SCROLL_WNG = new ItemScroll();
    public static final Item SPELL_BOOK_WNG = new ItemWNGSpellBook();
    public static final Item SPELL_ENCYCLOPEDIA = new ItemSpellEncyclopedia();
    public static final Item UPGRADE_CHARGEUP = new ItemChargeupWandUpgrade();
    public static final Item UPGRADE_LOOTING = new ItemLootingWandUpgrade();
    public static final Item UPGRADE_MOVEMENT = new ItemMovementWandUpgrade();

    //amulet
    public static final Item AMULET_MOON = new ItemAmuletMoon(EnumRarity.EPIC, ItemArtefact.Type.AMULET);

    //belt
    public static final Item BELT_POTION = new ItemBeltPotion(EnumRarity.RARE, ItemArtefact.Type.BELT);

    //body
    public static final Item BODY_HASHASHIN = new ItemWNGArtefact(EnumRarity.RARE, ItemArtefact.Type.BODY);
    public static final Item BODY_ARTEMIS = new ItemWNGArtefact(EnumRarity.RARE, ItemArtefact.Type.BODY);

    //charm
    public static final Item CHARM_BLOODSTONE = new ItemCharmBloodstone(EnumRarity.RARE, ItemArtefact.Type.CHARM);
    public static final Item CHARM_DICE = new ItemWNGArtefact(EnumRarity.EPIC, ItemArtefact.Type.CHARM);
    public static final Item CHARM_HORN = new ItemCharmHorn(EnumRarity.EPIC, ItemArtefact.Type.CHARM);
    public static final Item CHARM_ICICLE = new ItemWNGArtefact(EnumRarity.UNCOMMON, ItemArtefact.Type.CHARM);
    public static final Item CHARM_LINGERING = new ItemWNGArtefact(EnumRarity.EPIC, ItemArtefact.Type.CHARM);
    public static final Item CHARM_PYRAMID = new ItemWNGArtefact(EnumRarity.RARE, ItemArtefact.Type.CHARM);
    public static final Item CHARM_YANG = new ItemWNGArtefact(EnumRarity.RARE, ItemArtefact.Type.CHARM);
    public static final Item CHARM_YIN = new ItemWNGArtefact(EnumRarity.RARE, ItemArtefact.Type.CHARM);
    public static final Item CHARM_YIN_YANG = new ItemWNGArtefact(EnumRarity.EPIC, ItemArtefact.Type.CHARM);

    //head
    public static final Item HEAD_HASHASHIN = new ItemWNGArtefact(EnumRarity.RARE, ItemArtefact.Type.HEAD);
    public static final Item HEAD_RA = new ItemWNGArtefact(EnumRarity.EPIC, ItemArtefact.Type.HEAD);
    //public static final Item HEAD_RALLY = new ItemWNGArtefact(EnumRarity.RARE, ItemArtefact.Type.HEAD);
    public static final Item HEAD_RAIJIN = new ItemWNGArtefact(EnumRarity.EPIC, ItemArtefact.Type.HEAD);
    public static final Item HEAD_THORNS = new ItemWNGArtefact(EnumRarity.RARE, ItemArtefact.Type.HEAD);

    //ring
    public static final Item RING_9_TH_CIRCLE = new ItemWNGArtefact(EnumRarity.UNCOMMON, ItemArtefact.Type.RING);
    public static final Item RING_ANODIZED = new ItemWNGArtefact(EnumRarity.RARE, ItemArtefact.Type.RING);
    public static final Item RING_LOOTING = new ItemWNGArtefact(EnumRarity.UNCOMMON, ItemArtefact.Type.RING);
    public static final Item RING_NULLIFICATION = new ItemWNGArtefact(EnumRarity.RARE, ItemArtefact.Type.RING);
    public static final Item RING_RAINBOW = new ItemWNGArtefact(EnumRarity.EPIC, ItemArtefact.Type.RING);
    public static final Item RING_STATIC_SHOCK = new ItemWNGArtefact(EnumRarity.RARE, ItemArtefact.Type.RING);
    public static final Item RING_VOID = new ItemWNGArtefact(EnumRarity.UNCOMMON, ItemArtefact.Type.RING);

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

    private static void registerItemBlock(IForgeRegistry<Item> registry, Block block) {
        registerItemBlock(registry, block, new ItemBlock(block));
    }

    private static void registerItemBlock(IForgeRegistry<Item> registry, Block block, ItemBlock itemblock) {
        itemblock.setRegistryName(block.getRegistryName());
        registry.register(itemblock);
        if (block.getCreativeTab() instanceof WizardryTabs.CreativeTabListed) {
            ((WizardryTabs.CreativeTabListed)block.getCreativeTab()).order.add(itemblock);
        }

    }

    @SubscribeEvent
    public static void register(RegistryEvent.Register<Item> event) {

        IForgeRegistry<Item> registry = event.getRegistry();

        //block

        //misc
        registerItem(registry, "blessed_meat", new ItemBlessedMeat());

        //magical items
        registerItem(registry, "conjured_potion", CONJURED_POTION);
        registerItem(registry, "enchantable_amulet", ENCHANTABLE_AMULET);
        registerItem(registry, "enchantable_belt", ENCHANTABLE_BELT);
        registerItem(registry, "enchantable_body", ENCHANTABLE_BODY);
        registerItem(registry, "enchantable_charm", ENCHANTABLE_CHARM);
        registerItem(registry, "enchantable_head", ENCHANTABLE_HEAD);
        registerItem(registry, "enchantable_ring", ENCHANTABLE_RING);
        registerItem(registry, "enchantable_trinket", ENCHANTABLE_TRINKET);
        registerItem(registry, "scroll_wng", SCROLL_WNG);
        registerItem(registry, "spell_book_wng", SPELL_BOOK_WNG);
        registerItem(registry, "spell_encyclopedia", SPELL_ENCYCLOPEDIA);
        registerItem(registry, "upgrade_chargeup", UPGRADE_CHARGEUP);
        registerItem(registry, "upgrade_looting", UPGRADE_LOOTING);
        registerItem(registry, "upgrade_movement", UPGRADE_MOVEMENT);

        //amulet
        registerItem(registry, "amulet_moon", AMULET_MOON);

        //belt
        registerItem(registry, "belt_potion", BELT_POTION);

        //body
        registerItem(registry, "body_artemis", BODY_ARTEMIS);
        registerItem(registry, "body_hashashin", BODY_HASHASHIN);

        //charm
        registerItem(registry, "charm_bloodstone", CHARM_BLOODSTONE);
        registerItem(registry, "charm_dice", CHARM_DICE);
        registerItem(registry, "charm_horn", CHARM_HORN);
        registerItem(registry, "charm_icicle", CHARM_ICICLE);
        registerItem(registry, "charm_lingering", CHARM_LINGERING);
        registerItem(registry, "charm_pyramid", CHARM_PYRAMID);
        registerItem(registry, "charm_yang", CHARM_YANG);
        registerItem(registry, "charm_yin", CHARM_YIN);
        registerItem(registry, "charm_yin_yang", CHARM_YIN_YANG);

        //head
        registerItem(registry, "head_hashashin", HEAD_HASHASHIN);
        registerItem(registry, "head_ra", HEAD_RA);
        //registerItem(registry, "head_rally", HEAD_RALLY);
        registerItem(registry, "head_raijin", HEAD_RAIJIN);
        registerItem(registry, "head_thorns", HEAD_THORNS);

        //ring
        registerItem(registry, "ring_9th_circle", RING_9_TH_CIRCLE);
        registerItem(registry, "ring_anodized", RING_ANODIZED);
        registerItem(registry, "ring_looting", RING_LOOTING);
        registerItem(registry, "ring_nullification", RING_NULLIFICATION);
        registerItem(registry, "ring_rainbow", RING_RAINBOW);
        registerItem(registry, "ring_static_shock", RING_STATIC_SHOCK);
        registerItem(registry, "ring_void", RING_VOID);


    }

    public static void registerDispenseBehaviours(){
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(SCROLL_WNG, new BehaviourSpellDispense());
    }

    public static void registerBookItems(){
        ContainerBookshelf.registerBookItem(WNGItems.SPELL_BOOK_WNG);
        ContainerBookshelf.registerBookItem(WNGItems.SCROLL_WNG);
    }

    public static void registerBookshelfModelTextures(){
        BlockBookshelf.registerBookModelTexture(() -> WNGItems.SPELL_BOOK_WNG, new ResourceLocation(WizardryNextGeneration.MODID, "blocks/books_wng"));
        BlockBookshelf.registerBookModelTexture(() -> WNGItems.SCROLL_WNG, new ResourceLocation(WizardryNextGeneration.MODID, "blocks/scrolls_wng"));
    }

}