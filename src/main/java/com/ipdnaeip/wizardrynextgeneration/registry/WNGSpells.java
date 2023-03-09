
package com.ipdnaeip.wizardrynextgeneration.registry;


import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.spell.*;
import electroblob.wizardry.spell.Spell;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;

@SuppressWarnings("unused")
@GameRegistry.ObjectHolder(WizardryNextGeneration.MODID)
@Mod.EventBusSubscriber
public final class WNGSpells {

    private WNGSpells() {}

    private static final String modId = WizardryNextGeneration.MODID;

    @Nonnull
    @SuppressWarnings("ConstantConditions")
    private static <T> T placeholder() { return null; }

    public static final Spell photosynthesis = placeholder();
    public static final Spell scorch = placeholder();
    public static final Spell blood_infusion = placeholder();
    public static final Spell accelerated_mass = placeholder();
    public static final Spell moonlight = placeholder();
    public static final Spell sap_intellect = placeholder();
    public static final Spell sap_strength = placeholder();
    public static final Spell equality = placeholder();
    public static final Spell tase = placeholder();
    public static final Spell justice = placeholder();
    public static final Spell humility = placeholder();
    public static final Spell smokescreen = placeholder();
    public static final Spell bless_meat = placeholder();
    public static final Spell smoke_blitz = placeholder();
    public static final Spell smoke_barrage = placeholder();
    public static final Spell ice_blitz = placeholder();
    public static final Spell ice_barrage = placeholder();
    public static final Spell divine_shield = placeholder();
    public static final Spell haste = placeholder();
    public static final Spell bones_to_apples = placeholder();
    public static final Spell conduction = placeholder();
    public static final Spell solar_winds = placeholder();
    public static final Spell pacify = placeholder();
    public static final Spell tranquility = placeholder();
    public static final Spell frenzy = placeholder();
    public static final Spell scapegoat = placeholder();
    public static final Spell cleansing_flames = placeholder();
    public static final Spell solar_sentinel = placeholder();
    public static final Spell migraine = placeholder();
    public static final Spell frostbite = placeholder();
    public static final Spell righteous_defense = placeholder();
    public static final Spell frostburn = placeholder();
    public static final Spell veneficium = placeholder();
    public static final Spell meditate = placeholder();
    public static final Spell assassinate = placeholder();
    public static final Spell anti_gravitational_push = placeholder();
    public static final Spell gravitational_pull = placeholder();
    public static final Spell cloak_of_the_void = placeholder();
    public static final Spell gravitational_field = placeholder();
    public static final Spell anti_gravitational_field = placeholder();
    public static final Spell smite = placeholder();
    public static final Spell fission_blast = placeholder();
    public static final Spell piercing_mass = placeholder();

    @SuppressWarnings("unchecked")
    @SubscribeEvent
    public static void register(RegistryEvent.Register<Spell> event) {

        IForgeRegistry<Spell> registry = event.getRegistry();

        // WNG 1.0 Spells
        registry.register(new Photosynthesis());
        registry.register(new Scorch());
        registry.register(new BloodInfusion());
        //registry.register(new AcceleratedMass());
        registry.register(new Moonlight());
        registry.register(new SapIntellect());
        registry.register(new SapStrength());
        registry.register(new Equality());
        registry.register(new Tase());
        registry.register(new Justice());
        registry.register(new Humility());
        registry.register(new Smokescreen());
        registry.register(new BlessMeat());
        registry.register(new SmokeBlitz());
        registry.register(new SmokeBarrage());
        registry.register(new IceBlitz());
        registry.register(new IceBarrage());
        registry.register(new DivineShield());
        registry.register(new Haste());
        registry.register(new BonesToApples());
        registry.register(new Conduction());
        registry.register(new SolarWinds());
        registry.register(new Pacify());
        registry.register(new Tranquility());
        registry.register(new Frenzy());
        registry.register(new Scapegoat());
        registry.register(new CleansingFlames());
        registry.register(new SolarSentinel());
        registry.register(new Migraine());
        registry.register(new Frostbite());
        registry.register(new RighteousDefense());
        registry.register(new Frostburn());
        registry.register(new Veneficium());
        //registry.register(new Meditate());
        registry.register(new Assassinate());
        registry.register(new AntiGravitationalPush());
        registry.register(new GravitationalPull());
        registry.register(new CloakOfTheVoid());
        registry.register(new GravitationalField());
        registry.register(new AntiGravitationalField());
        registry.register(new Smite());
        //registry.register(new FissionBlast());
        //registry.register(new PiercingMass());
    }
}

