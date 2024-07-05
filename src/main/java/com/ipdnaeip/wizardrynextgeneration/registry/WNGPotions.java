package com.ipdnaeip.wizardrynextgeneration.registry;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.potion.*;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@GameRegistry.ObjectHolder(WizardryNextGeneration.MODID)
@Mod.EventBusSubscriber
public class WNGPotions {

    public static final Potion ANIMAL_ALLEGIANCE = new PotionAnimalAllegiance().setBeneficial();
    public static final Potion BETRAYAL = new PotionBetrayal();
    public static final Potion BLEED = new PotionBleed();
    public static final Potion CLEANSING_FLAMES = new PotionCleansingFlames().setBeneficial();
    public static final Potion DISEMPOWERMENT = new PotionDisempowerment();
    public static final Potion DIVINE_SHIELD = new PotionDivineShield().setBeneficial();
    public static final Potion EMPOWERMENT = new PotionEmpowerment(8611773, new ResourceLocation("ebwizardry", "textures/items/crystal_grand.png")).setBeneficial();
    public static final Potion EMPOWERMENT_EARTH = new PotionEmpowerment(43520, new ResourceLocation("ebwizardry", "textures/items/crystal_earth.png")).setBeneficial();
    public static final Potion EMPOWERMENT_FIRE = new PotionEmpowerment(11141120, new ResourceLocation("ebwizardry", "textures/items/crystal_fire.png")).setBeneficial();
    public static final Potion EMPOWERMENT_HEALING = new PotionEmpowerment(16777045, new ResourceLocation("ebwizardry", "textures/items/crystal_healing.png")).setBeneficial();
    public static final Potion EMPOWERMENT_ICE = new PotionEmpowerment(5636095, new ResourceLocation("ebwizardry", "textures/items/crystal_ice.png")).setBeneficial();
    public static final Potion EMPOWERMENT_LIGHTNING = new PotionEmpowerment(43690, new ResourceLocation("ebwizardry", "textures/items/crystal_lightning.png")).setBeneficial();
    public static final Potion EMPOWERMENT_NECROMANCY = new PotionEmpowerment(11141290, new ResourceLocation("ebwizardry", "textures/items/crystal_necromancy.png")).setBeneficial();
    public static final Potion EMPOWERMENT_SORCERY = new PotionEmpowerment(5635925, new ResourceLocation("ebwizardry", "textures/items/crystal_sorcery.png")).setBeneficial();
    public static final Potion EYE_FOR_AN_EYE = new PotionEyeForAnEye().setBeneficial();
    public static final Potion FOCUS = new PotionFocus().setBeneficial();
    public static final Potion FRENZY = new PotionFrenzy();
    public static final Potion GRAVITY = new PotionGravity();
    public static final Potion LIGHTNING_REFLEXES = new PotionLightningReflexes().setBeneficial();
    public static final Potion NAPALM = new PotionNapalm();
    public static final Potion PACIFY = new PotionPacify();
    public static final Potion PREPARATION = new PotionPreparation().setBeneficial();
    public static final Potion RALLY = new PotionRally();
    public static final Potion RETRIBUTION = new PotionRetribution().setBeneficial();
    public static final Potion SOLAR_WINDS = new PotionSolarWinds().setBeneficial();
    public static final Potion SUFFOCATION = new PotionSuffocation();
    public static final Potion TAUNT = new PotionTaunt();
    public static final Potion VENEFICIUM = new PotionVeneficium();
    public static final Potion VULNERABILITY_MAGIC = new PotionVulnerability(8611773, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/magic_weakness.png"));
    public static final Potion VULNERABILITY_FIRE = new PotionEmpowerment(11141120, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/magic_weakness.png"));
    public static final Potion VULNERABILITY_FROST = new PotionEmpowerment(5636095, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/magic_weakness.png"));
    public static final Potion VULNERABILITY_SHOCK = new PotionEmpowerment(43690, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/magic_weakness.png"));
    public static final Potion VULNERABILITY_WITHER = new PotionEmpowerment(11141290, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/magic_weakness.png"));
    public static final Potion VULNERABILITY_POISON = new PotionEmpowerment(43520, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/magic_weakness.png"));
    public static final Potion VULNERABILITY_FORCE = new PotionEmpowerment(5635925, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/magic_weakness.png"));
    public static final Potion VULNERABILITY_BLAST = new PotionEmpowerment(5635925, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/magic_weakness.png"));
    public static final Potion VULNERABILITY_RADIANT = new PotionEmpowerment(16777045, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/magic_weakness.png"));

    public static void registerPotion(IForgeRegistry<Potion> registry, String name, Potion potion) {
        potion.setRegistryName(WizardryNextGeneration.MODID, name);
        potion.setPotionName("potion." + WizardryNextGeneration.MODID + ":" + name);
        registry.register(potion);
    }

    @SubscribeEvent
    public static void register(RegistryEvent.Register<Potion> event) {

        IForgeRegistry<Potion> registry = event.getRegistry();

        registerPotion(registry, "animal_allegiance", ANIMAL_ALLEGIANCE);
        registerPotion(registry, "betrayal", BETRAYAL);
        registerPotion(registry, "bleed", BLEED);
        registerPotion(registry, "cleansing_flames", CLEANSING_FLAMES);
        registerPotion(registry, "disempowerment", DISEMPOWERMENT);
        registerPotion(registry, "divine_shield", DIVINE_SHIELD);
        registerPotion(registry, "empowerment", EMPOWERMENT);
        registerPotion(registry, "empowerment_earth", EMPOWERMENT_EARTH);
        registerPotion(registry, "empowerment_fire", EMPOWERMENT_FIRE);
        registerPotion(registry, "empowerment_healing", EMPOWERMENT_HEALING);
        registerPotion(registry, "empowerment_ice", EMPOWERMENT_ICE);
        registerPotion(registry, "empowerment_lightning", EMPOWERMENT_LIGHTNING);
        registerPotion(registry, "empowerment_necromancy", EMPOWERMENT_NECROMANCY);
        registerPotion(registry, "empowerment_sorcery", EMPOWERMENT_SORCERY);
        registerPotion(registry, "eye_for_an_eye", EYE_FOR_AN_EYE);
        registerPotion(registry, "focus", FOCUS);
        registerPotion(registry, "frenzy", FRENZY);
        registerPotion(registry, "gravity", GRAVITY);
        registerPotion(registry, "lightning_reflexes", LIGHTNING_REFLEXES);
        registerPotion(registry, "napalm", NAPALM);
        registerPotion(registry, "pacify", PACIFY);
        registerPotion(registry, "preparation", PREPARATION);
        registerPotion(registry, "rally", RALLY);
        registerPotion(registry, "retribution", RETRIBUTION);
        registerPotion(registry, "solar_winds", SOLAR_WINDS);
        registerPotion(registry, "suffocation", SUFFOCATION);
        registerPotion(registry, "taunt", TAUNT);
        registerPotion(registry, "veneficium", VENEFICIUM);
        registerPotion(registry, "vulnerability_magic", VULNERABILITY_MAGIC);
        registerPotion(registry, "vulnerability_fire", VULNERABILITY_FIRE);
        registerPotion(registry, "vulnerability_frost", VULNERABILITY_FROST);
        registerPotion(registry, "vulnerability_shock", VULNERABILITY_SHOCK);
        registerPotion(registry, "vulnerability_wither", VULNERABILITY_WITHER);
        registerPotion(registry, "vulnerability_poison", VULNERABILITY_POISON);
        registerPotion(registry, "vulnerability_force", VULNERABILITY_FORCE);
        registerPotion(registry, "vulnerability_blast", VULNERABILITY_BLAST);
        registerPotion(registry, "vulnerability_radiant", VULNERABILITY_RADIANT);
    }
}
