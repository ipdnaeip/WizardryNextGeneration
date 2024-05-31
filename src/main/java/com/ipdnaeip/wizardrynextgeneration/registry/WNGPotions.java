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

    public static final Potion animal_allegiance = new PotionAnimalAllegiance().setBeneficial();
    public static final Potion betrayal = new PotionBetrayal();
    public static final Potion bleed = new PotionBleed();
    public static final Potion cleansing_flames = new PotionCleansingFlames().setBeneficial();
    //public static final Potion consecration = new PotionConsecration().setBeneficial();
    public static final Potion disempowerment = new PotionDisempowerment();
    public static final Potion divine_shield = new PotionDivineShield().setBeneficial();
    public static final Potion empowerment = new PotionEmpowerment(8611773, new ResourceLocation("ebwizardry", "textures/items/crystal_grand.png")).setBeneficial();
    public static final Potion empowerment_earth = new PotionEmpowerment(43520, new ResourceLocation("ebwizardry", "textures/items/crystal_earth.png")).setBeneficial();
    public static final Potion empowerment_fire = new PotionEmpowerment(11141120, new ResourceLocation("ebwizardry", "textures/items/crystal_fire.png")).setBeneficial();
    public static final Potion empowerment_healing = new PotionEmpowerment(16777045, new ResourceLocation("ebwizardry", "textures/items/crystal_healing.png")).setBeneficial();
    public static final Potion empowerment_ice = new PotionEmpowerment(5636095, new ResourceLocation("ebwizardry", "textures/items/crystal_ice.png")).setBeneficial();
    public static final Potion empowerment_lightning = new PotionEmpowerment(43690, new ResourceLocation("ebwizardry", "textures/items/crystal_lightning.png")).setBeneficial();
    public static final Potion empowerment_necromancy = new PotionEmpowerment(11141290, new ResourceLocation("ebwizardry", "textures/items/crystal_necromancy.png")).setBeneficial();
    public static final Potion empowerment_sorcery = new PotionEmpowerment(5635925, new ResourceLocation("ebwizardry", "textures/items/crystal_sorcery.png")).setBeneficial();
    public static final Potion eye_for_an_eye = new PotionEyeForAnEye().setBeneficial();
    public static final Potion focus = new PotionFocus().setBeneficial();
    public static final Potion frenzy = new PotionFrenzy();
    public static final Potion gravity = new PotionGravity();
    public static final Potion lightning_reflexes = new PotionLightningReflexes().setBeneficial();
    public static final Potion magic_weakness = new PotionMagicWeakness();
    public static final Potion napalm = new PotionNapalm();
    public static final Potion pacify = new PotionPacify();
    public static final Potion preparation = new PotionPreparation().setBeneficial();
    public static final Potion rally = new PotionRally();
    public static final Potion retribution = new PotionRetribution().setBeneficial();
    public static final Potion shock_weakness = new PotionShockWeakness();
    public static final Potion solar_winds = new PotionSolarWinds().setBeneficial();
    public static final Potion suffocation = new PotionSuffocation();
    public static final Potion taunt = new PotionTaunt();
    public static final Potion veneficium = new PotionVeneficium();

    public static void registerPotion(IForgeRegistry<Potion> registry, String name, Potion potion) {
        potion.setRegistryName(WizardryNextGeneration.MODID, name);
        registry.register(potion);
    }

    @SubscribeEvent
    public static void register(RegistryEvent.Register<Potion> event) {

        IForgeRegistry<Potion> registry = event.getRegistry();

        registerPotion(registry, "animal_allegiance", animal_allegiance);
        registerPotion(registry, "betrayal", betrayal);
        registerPotion(registry, "bleed", bleed);
        registerPotion(registry, "cleansing_flames", cleansing_flames);
        //registerPotion(registry, "consecration", consecration);
        registerPotion(registry, "disempowerment", disempowerment);
        registerPotion(registry, "divine_shield", divine_shield);
        registerPotion(registry, "empowerment", empowerment);
        registerPotion(registry, "empowerment_earth", empowerment_earth);
        registerPotion(registry, "empowerment_fire", empowerment_fire);
        registerPotion(registry, "empowerment_healing", empowerment_healing);
        registerPotion(registry, "empowerment_ice", empowerment_ice);
        registerPotion(registry, "empowerment_lightning", empowerment_lightning);
        registerPotion(registry, "empowerment_necromancy", empowerment_necromancy);
        registerPotion(registry, "empowerment_sorcery", empowerment_sorcery);
        registerPotion(registry, "eye_for_an_eye", eye_for_an_eye);
        registerPotion(registry, "focus", focus);
        registerPotion(registry, "frenzy", frenzy);
        registerPotion(registry, "gravity", gravity);
        registerPotion(registry, "lightning_reflexes", lightning_reflexes);
        registerPotion(registry, "magic_weakness", magic_weakness);
        registerPotion(registry, "napalm", napalm);
        registerPotion(registry, "pacify", pacify);
        registerPotion(registry, "preparation", preparation);
        registerPotion(registry, "rally", rally);
        registerPotion(registry, "retribution", retribution);
        registerPotion(registry, "shock_weakness", shock_weakness);
        registerPotion(registry, "solar_winds", solar_winds);
        registerPotion(registry, "suffocation", suffocation);
        registerPotion(registry, "taunt", taunt);
        registerPotion(registry, "veneficium", veneficium);

    }
}
