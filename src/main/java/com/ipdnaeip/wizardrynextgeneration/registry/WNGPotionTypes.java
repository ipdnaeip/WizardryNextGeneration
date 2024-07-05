package com.ipdnaeip.wizardrynextgeneration.registry;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import electroblob.wizardry.registry.WizardryItems;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class WNGPotionTypes {

    public static final PotionType EMPOWERMENT = new PotionType(WizardryNextGeneration.MODID + ":empowerment", new PotionEffect(WNGPotions.EMPOWERMENT, 600, 0)).setRegistryName(WizardryNextGeneration.MODID, "empowerment");
    public static final PotionType EMPOWERMENT_LONG = new PotionType(WizardryNextGeneration.MODID + ":empowerment", new PotionEffect(WNGPotions.EMPOWERMENT, 900, 0)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_long");
    public static final PotionType EMPOWERMENT_STRONG = new PotionType(WizardryNextGeneration.MODID + ":empowerment", new PotionEffect(WNGPotions.EMPOWERMENT, 400, 1)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_strong");
    public static final PotionType EMPOWERMENT_EARTH = new PotionType(WizardryNextGeneration.MODID + ":empowerment_earth", new PotionEffect(WNGPotions.EMPOWERMENT_EARTH, 600, 0)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_earth");
    public static final PotionType EMPOWERMENT_EARTH_LONG = new PotionType(WizardryNextGeneration.MODID + ":empowerment_earth", new PotionEffect(WNGPotions.EMPOWERMENT_EARTH, 900, 0)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_earth_long");
    public static final PotionType EMPOWERMENT_EARTH_STRONG = new PotionType(WizardryNextGeneration.MODID + ":empowerment_earth", new PotionEffect(WNGPotions.EMPOWERMENT_EARTH, 400, 1)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_earth_strong");
    public static final PotionType EMPOWERMENT_FIRE = new PotionType(WizardryNextGeneration.MODID + ":empowerment_fire", new PotionEffect(WNGPotions.EMPOWERMENT_FIRE, 600, 0)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_fire");
    public static final PotionType EMPOWERMENT_FIRE_LONG = new PotionType(WizardryNextGeneration.MODID + ":empowerment_fire", new PotionEffect(WNGPotions.EMPOWERMENT_FIRE, 900, 0)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_fire_long");
    public static final PotionType EMPOWERMENT_FIRE_STRONG = new PotionType(WizardryNextGeneration.MODID + ":empowerment_fire", new PotionEffect(WNGPotions.EMPOWERMENT_FIRE, 400, 1)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_fire_strong");
    public static final PotionType EMPOWERMENT_HEALING = new PotionType(WizardryNextGeneration.MODID + ":empowerment_healing", new PotionEffect(WNGPotions.EMPOWERMENT_HEALING, 600, 0)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_healing");
    public static final PotionType EMPOWERMENT_HEALING_LONG = new PotionType(WizardryNextGeneration.MODID + ":empowerment_healing", new PotionEffect(WNGPotions.EMPOWERMENT_HEALING, 900, 0)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_healing_long");
    public static final PotionType EMPOWERMENT_HEALING_STRONG = new PotionType(WizardryNextGeneration.MODID + ":empowerment_healing", new PotionEffect(WNGPotions.EMPOWERMENT_HEALING, 400, 1)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_healing_strong");
    public static final PotionType EMPOWERMENT_ICE = new PotionType(WizardryNextGeneration.MODID + ":empowerment_ice", new PotionEffect(WNGPotions.EMPOWERMENT_ICE, 600, 0)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_ice");
    public static final PotionType EMPOWERMENT_ICE_LONG = new PotionType(WizardryNextGeneration.MODID + ":empowerment_ice", new PotionEffect(WNGPotions.EMPOWERMENT_ICE, 900, 0)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_ice_long");
    public static final PotionType EMPOWERMENT_ICE_STRONG = new PotionType(WizardryNextGeneration.MODID + ":empowerment_ice", new PotionEffect(WNGPotions.EMPOWERMENT_ICE, 400, 1)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_ice_strong");
    public static final PotionType EMPOWERMENT_LIGHTNING = new PotionType(WizardryNextGeneration.MODID + ":empowerment_lightning", new PotionEffect(WNGPotions.EMPOWERMENT_LIGHTNING, 600, 0)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_lightning");
    public static final PotionType EMPOWERMENT_LIGHTNING_LONG = new PotionType(WizardryNextGeneration.MODID + ":empowerment_lightning", new PotionEffect(WNGPotions.EMPOWERMENT_LIGHTNING, 900, 0)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_lightning_long");
    public static final PotionType EMPOWERMENT_LIGHTNING_STRONG = new PotionType(WizardryNextGeneration.MODID + ":empowerment_lightning", new PotionEffect(WNGPotions.EMPOWERMENT_LIGHTNING, 400, 1)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_lightning_strong");
    public static final PotionType EMPOWERMENT_NECROMANCY = new PotionType(WizardryNextGeneration.MODID + ":empowerment_necromancy", new PotionEffect(WNGPotions.EMPOWERMENT_NECROMANCY, 600, 0)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_necromancy");
    public static final PotionType EMPOWERMENT_NECROMANCY_LONG = new PotionType(WizardryNextGeneration.MODID + ":empowerment_necromancy", new PotionEffect(WNGPotions.EMPOWERMENT_NECROMANCY, 900, 0)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_necromancy_long");
    public static final PotionType EMPOWERMENT_NECROMANCY_STRONG = new PotionType(WizardryNextGeneration.MODID + ":empowerment_necromancy", new PotionEffect(WNGPotions.EMPOWERMENT_NECROMANCY, 400, 1)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_necromancy_strong");
    public static final PotionType EMPOWERMENT_SORCERY = new PotionType(WizardryNextGeneration.MODID + ":empowerment_sorcery", new PotionEffect(WNGPotions.EMPOWERMENT_SORCERY, 600, 0)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_sorcery");
    public static final PotionType EMPOWERMENT_SORCERY_LONG = new PotionType(WizardryNextGeneration.MODID + ":empowerment_sorcery", new PotionEffect(WNGPotions.EMPOWERMENT_SORCERY, 900, 0)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_sorcery_long");
    public static final PotionType EMPOWERMENT_SORCERY_STRONG = new PotionType(WizardryNextGeneration.MODID + ":empowerment_sorcery", new PotionEffect(WNGPotions.EMPOWERMENT_SORCERY, 400, 1)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_sorcery_strong");


    @SubscribeEvent
    public static void register(RegistryEvent.Register<PotionType> event) {
        IForgeRegistry<PotionType> registry = event.getRegistry();

        registry.register(EMPOWERMENT);
        registry.register(EMPOWERMENT_LONG);
        registry.register(EMPOWERMENT_STRONG);
        registry.register(EMPOWERMENT_EARTH);
        registry.register(EMPOWERMENT_EARTH_LONG);
        registry.register(EMPOWERMENT_EARTH_STRONG);
        registry.register(EMPOWERMENT_FIRE);
        registry.register(EMPOWERMENT_FIRE_LONG);
        registry.register(EMPOWERMENT_FIRE_STRONG);
        registry.register(EMPOWERMENT_HEALING);
        registry.register(EMPOWERMENT_HEALING_LONG);
        registry.register(EMPOWERMENT_HEALING_STRONG);
        registry.register(EMPOWERMENT_ICE);
        registry.register(EMPOWERMENT_ICE_LONG);
        registry.register(EMPOWERMENT_ICE_STRONG);
        registry.register(EMPOWERMENT_LIGHTNING);
        registry.register(EMPOWERMENT_LIGHTNING_LONG);
        registry.register(EMPOWERMENT_LIGHTNING_STRONG);
        registry.register(EMPOWERMENT_NECROMANCY);
        registry.register(EMPOWERMENT_NECROMANCY_LONG);
        registry.register(EMPOWERMENT_NECROMANCY_STRONG);
        registry.register(EMPOWERMENT_SORCERY);
        registry.register(EMPOWERMENT_SORCERY_LONG);
        registry.register(EMPOWERMENT_SORCERY_STRONG);

        PotionHelper.addMix(PotionTypes.AWKWARD, WizardryItems.grand_crystal, EMPOWERMENT);
        PotionHelper.addMix(EMPOWERMENT, Items.REDSTONE, EMPOWERMENT_LONG);
        PotionHelper.addMix(EMPOWERMENT, Items.GLOWSTONE_DUST, EMPOWERMENT_STRONG);
        PotionHelper.addMix(PotionTypes.AWKWARD, Ingredient.fromStacks(new ItemStack(WizardryItems.magic_crystal, 1, 5)), EMPOWERMENT_EARTH);
        PotionHelper.addMix(EMPOWERMENT_EARTH, Items.REDSTONE, EMPOWERMENT_EARTH_LONG);
        PotionHelper.addMix(EMPOWERMENT_EARTH, Items.GLOWSTONE_DUST, EMPOWERMENT_EARTH_STRONG);
        PotionHelper.addMix(PotionTypes.AWKWARD, Ingredient.fromStacks(new ItemStack(WizardryItems.magic_crystal, 1, 1)), EMPOWERMENT_FIRE);
        PotionHelper.addMix(EMPOWERMENT_FIRE, Items.REDSTONE, EMPOWERMENT_FIRE_LONG);
        PotionHelper.addMix(EMPOWERMENT_FIRE, Items.GLOWSTONE_DUST, EMPOWERMENT_FIRE_STRONG);
        PotionHelper.addMix(PotionTypes.AWKWARD, Ingredient.fromStacks(new ItemStack(WizardryItems.magic_crystal, 1, 7)), EMPOWERMENT_HEALING);
        PotionHelper.addMix(EMPOWERMENT_HEALING, Items.REDSTONE, EMPOWERMENT_HEALING_LONG);
        PotionHelper.addMix(EMPOWERMENT_HEALING, Items.GLOWSTONE_DUST, EMPOWERMENT_HEALING_STRONG);
        PotionHelper.addMix(PotionTypes.AWKWARD, Ingredient.fromStacks(new ItemStack(WizardryItems.magic_crystal, 1, 2)), EMPOWERMENT_ICE);
        PotionHelper.addMix(EMPOWERMENT_ICE, Items.REDSTONE, EMPOWERMENT_ICE_LONG);
        PotionHelper.addMix(EMPOWERMENT_ICE, Items.GLOWSTONE_DUST, EMPOWERMENT_ICE_STRONG);
        PotionHelper.addMix(PotionTypes.AWKWARD, Ingredient.fromStacks(new ItemStack(WizardryItems.magic_crystal, 1, 3)), EMPOWERMENT_LIGHTNING);
        PotionHelper.addMix(EMPOWERMENT_LIGHTNING, Items.REDSTONE, EMPOWERMENT_LIGHTNING_LONG);
        PotionHelper.addMix(EMPOWERMENT_LIGHTNING, Items.GLOWSTONE_DUST, EMPOWERMENT_LIGHTNING_STRONG);
        PotionHelper.addMix(PotionTypes.AWKWARD, Ingredient.fromStacks(new ItemStack(WizardryItems.magic_crystal, 1, 4)), EMPOWERMENT_NECROMANCY);
        PotionHelper.addMix(EMPOWERMENT_NECROMANCY, Items.REDSTONE, EMPOWERMENT_NECROMANCY_LONG);
        PotionHelper.addMix(EMPOWERMENT_NECROMANCY, Items.GLOWSTONE_DUST, EMPOWERMENT_NECROMANCY_STRONG);
        PotionHelper.addMix(PotionTypes.AWKWARD, Ingredient.fromStacks(new ItemStack(WizardryItems.magic_crystal, 1, 6)), EMPOWERMENT_SORCERY);
        PotionHelper.addMix(EMPOWERMENT_SORCERY, Items.REDSTONE, EMPOWERMENT_SORCERY_LONG);
        PotionHelper.addMix(EMPOWERMENT_SORCERY, Items.GLOWSTONE_DUST, EMPOWERMENT_SORCERY_STRONG);
    }

}