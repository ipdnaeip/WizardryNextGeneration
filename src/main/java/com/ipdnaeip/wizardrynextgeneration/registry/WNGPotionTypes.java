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

    public static final PotionType empowerment = new PotionType(WizardryNextGeneration.MODID + ":empowerment", new PotionEffect(WNGPotions.empowerment, 600, 0)).setRegistryName(WizardryNextGeneration.MODID, "empowerment");
    public static final PotionType empowerment_long = new PotionType(WizardryNextGeneration.MODID + ":empowerment", new PotionEffect(WNGPotions.empowerment, 900, 0)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_long");
    public static final PotionType empowerment_strong = new PotionType(WizardryNextGeneration.MODID + ":empowerment", new PotionEffect(WNGPotions.empowerment, 400, 1)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_strong");
    public static final PotionType empowerment_earth = new PotionType(WizardryNextGeneration.MODID + ":empowerment_earth", new PotionEffect(WNGPotions.empowerment_earth, 600, 0)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_earth");
    public static final PotionType empowerment_earth_long = new PotionType(WizardryNextGeneration.MODID + ":empowerment_earth", new PotionEffect(WNGPotions.empowerment_earth, 900, 0)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_earth_long");
    public static final PotionType empowerment_earth_strong = new PotionType(WizardryNextGeneration.MODID + ":empowerment_earth", new PotionEffect(WNGPotions.empowerment_earth, 400, 1)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_earth_strong");
    public static final PotionType empowerment_fire = new PotionType(WizardryNextGeneration.MODID + ":empowerment_fire", new PotionEffect(WNGPotions.empowerment_fire, 600, 0)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_fire");
    public static final PotionType empowerment_fire_long = new PotionType(WizardryNextGeneration.MODID + ":empowerment_fire", new PotionEffect(WNGPotions.empowerment_fire, 900, 0)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_fire_long");
    public static final PotionType empowerment_fire_strong = new PotionType(WizardryNextGeneration.MODID + ":empowerment_fire", new PotionEffect(WNGPotions.empowerment_fire, 400, 1)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_fire_strong");
    public static final PotionType empowerment_healing = new PotionType(WizardryNextGeneration.MODID + ":empowerment_healing", new PotionEffect(WNGPotions.empowerment_healing, 600, 0)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_healing");
    public static final PotionType empowerment_healing_long = new PotionType(WizardryNextGeneration.MODID + ":empowerment_healing", new PotionEffect(WNGPotions.empowerment_healing, 900, 0)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_healing_long");
    public static final PotionType empowerment_healing_strong = new PotionType(WizardryNextGeneration.MODID + ":empowerment_healing", new PotionEffect(WNGPotions.empowerment_healing, 400, 1)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_healing_strong");
    public static final PotionType empowerment_ice = new PotionType(WizardryNextGeneration.MODID + ":empowerment_ice", new PotionEffect(WNGPotions.empowerment_ice, 600, 0)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_ice");
    public static final PotionType empowerment_ice_long = new PotionType(WizardryNextGeneration.MODID + ":empowerment_ice", new PotionEffect(WNGPotions.empowerment_ice, 900, 0)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_ice_long");
    public static final PotionType empowerment_ice_strong = new PotionType(WizardryNextGeneration.MODID + ":empowerment_ice", new PotionEffect(WNGPotions.empowerment_ice, 400, 1)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_ice_strong");
    public static final PotionType empowerment_lightning = new PotionType(WizardryNextGeneration.MODID + ":empowerment_lightning", new PotionEffect(WNGPotions.empowerment_lightning, 600, 0)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_lightning");
    public static final PotionType empowerment_lightning_long = new PotionType(WizardryNextGeneration.MODID + ":empowerment_lightning", new PotionEffect(WNGPotions.empowerment_lightning, 900, 0)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_lightning_long");
    public static final PotionType empowerment_lightning_strong = new PotionType(WizardryNextGeneration.MODID + ":empowerment_lightning", new PotionEffect(WNGPotions.empowerment_lightning, 400, 1)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_lightning_strong");
    public static final PotionType empowerment_necromancy = new PotionType(WizardryNextGeneration.MODID + ":empowerment_necromancy", new PotionEffect(WNGPotions.empowerment_necromancy, 600, 0)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_necromancy");
    public static final PotionType empowerment_necromancy_long = new PotionType(WizardryNextGeneration.MODID + ":empowerment_necromancy", new PotionEffect(WNGPotions.empowerment_necromancy, 900, 0)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_necromancy_long");
    public static final PotionType empowerment_necromancy_strong = new PotionType(WizardryNextGeneration.MODID + ":empowerment_necromancy", new PotionEffect(WNGPotions.empowerment_necromancy, 400, 1)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_necromancy_strong");
    public static final PotionType empowerment_sorcery = new PotionType(WizardryNextGeneration.MODID + ":empowerment_sorcery", new PotionEffect(WNGPotions.empowerment_sorcery, 600, 0)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_sorcery");
    public static final PotionType empowerment_sorcery_long = new PotionType(WizardryNextGeneration.MODID + ":empowerment_sorcery", new PotionEffect(WNGPotions.empowerment_sorcery, 900, 0)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_sorcery_long");
    public static final PotionType empowerment_sorcery_strong = new PotionType(WizardryNextGeneration.MODID + ":empowerment_sorcery", new PotionEffect(WNGPotions.empowerment_sorcery, 400, 1)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_sorcery_strong");


    @SubscribeEvent
    public static void register(RegistryEvent.Register<PotionType> event) {
        IForgeRegistry<PotionType> registry = event.getRegistry();

        registry.register(empowerment);
        registry.register(empowerment_long);
        registry.register(empowerment_strong);
        registry.register(empowerment_earth);
        registry.register(empowerment_earth_long);
        registry.register(empowerment_earth_strong);
        registry.register(empowerment_fire);
        registry.register(empowerment_fire_long);
        registry.register(empowerment_fire_strong);
        registry.register(empowerment_healing);
        registry.register(empowerment_healing_long);
        registry.register(empowerment_healing_strong);
        registry.register(empowerment_ice);
        registry.register(empowerment_ice_long);
        registry.register(empowerment_ice_strong);
        registry.register(empowerment_lightning);
        registry.register(empowerment_lightning_long);
        registry.register(empowerment_lightning_strong);
        registry.register(empowerment_necromancy);
        registry.register(empowerment_necromancy_long);
        registry.register(empowerment_necromancy_strong);
        registry.register(empowerment_sorcery);
        registry.register(empowerment_sorcery_long);
        registry.register(empowerment_sorcery_strong);

        PotionHelper.addMix(PotionTypes.AWKWARD, WizardryItems.grand_crystal, empowerment);
        PotionHelper.addMix(empowerment, Items.REDSTONE, empowerment_long);
        PotionHelper.addMix(empowerment, Items.GLOWSTONE_DUST, empowerment_strong);
        PotionHelper.addMix(PotionTypes.AWKWARD, Ingredient.fromStacks(new ItemStack(WizardryItems.magic_crystal, 1, 5)), empowerment_earth);
        PotionHelper.addMix(empowerment_earth, Items.REDSTONE, empowerment_earth_long);
        PotionHelper.addMix(empowerment_earth, Items.GLOWSTONE_DUST, empowerment_earth_strong);
        PotionHelper.addMix(PotionTypes.AWKWARD, Ingredient.fromStacks(new ItemStack(WizardryItems.magic_crystal, 1, 1)), empowerment_fire);
        PotionHelper.addMix(empowerment_fire, Items.REDSTONE, empowerment_fire_long);
        PotionHelper.addMix(empowerment_fire, Items.GLOWSTONE_DUST, empowerment_fire_strong);
        PotionHelper.addMix(PotionTypes.AWKWARD, Ingredient.fromStacks(new ItemStack(WizardryItems.magic_crystal, 1, 7)), empowerment_healing);
        PotionHelper.addMix(empowerment_healing, Items.REDSTONE, empowerment_healing_long);
        PotionHelper.addMix(empowerment_healing, Items.GLOWSTONE_DUST, empowerment_healing_strong);
        PotionHelper.addMix(PotionTypes.AWKWARD, Ingredient.fromStacks(new ItemStack(WizardryItems.magic_crystal, 1, 2)), empowerment_ice);
        PotionHelper.addMix(empowerment_ice, Items.REDSTONE, empowerment_ice_long);
        PotionHelper.addMix(empowerment_ice, Items.GLOWSTONE_DUST, empowerment_ice_strong);
        PotionHelper.addMix(PotionTypes.AWKWARD, Ingredient.fromStacks(new ItemStack(WizardryItems.magic_crystal, 1, 3)), empowerment_lightning);
        PotionHelper.addMix(empowerment_lightning, Items.REDSTONE, empowerment_lightning_long);
        PotionHelper.addMix(empowerment_lightning, Items.GLOWSTONE_DUST, empowerment_lightning_strong);
        PotionHelper.addMix(PotionTypes.AWKWARD, Ingredient.fromStacks(new ItemStack(WizardryItems.magic_crystal, 1, 4)), empowerment_necromancy);
        PotionHelper.addMix(empowerment_necromancy, Items.REDSTONE, empowerment_necromancy_long);
        PotionHelper.addMix(empowerment_necromancy, Items.GLOWSTONE_DUST, empowerment_necromancy_strong);
        PotionHelper.addMix(PotionTypes.AWKWARD, Ingredient.fromStacks(new ItemStack(WizardryItems.magic_crystal, 1, 6)), empowerment_sorcery);
        PotionHelper.addMix(empowerment_sorcery, Items.REDSTONE, empowerment_sorcery_long);
        PotionHelper.addMix(empowerment_sorcery, Items.GLOWSTONE_DUST, empowerment_sorcery_strong);
    }

}