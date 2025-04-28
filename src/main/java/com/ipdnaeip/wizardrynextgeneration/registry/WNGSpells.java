package com.ipdnaeip.wizardrynextgeneration.registry;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.spell.*;
import electroblob.wizardry.spell.Spell;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@GameRegistry.ObjectHolder(WizardryNextGeneration.MODID)
@Mod.EventBusSubscriber
public final class WNGSpells {

    private WNGSpells() {}

    // WNG 1.0.0 Spells
    public static final Spell PHOTOSYNTHESIS = new Photosynthesis();
    public static final Spell SCORCH = new Scorch();
    public static final Spell BLOOD_INFUSION = new BloodInfusion();
    public static final Spell ACCELERATED_MASS = new AcceleratedMass();
    public static final Spell MOONLIGHT = new Moonlight();
    public static final Spell SAP_INTELLECT = new SapIntellect();
    public static final Spell SAP_STRENGTH = new SapStrength();
    public static final Spell EQUALITY = new Equality();
    public static final Spell TASE = new Tase();
    public static final Spell JUSTICE = new Justice();
    public static final Spell HUMILITY = new Humility();
    public static final Spell SMOKESCREEN = new Smokescreen();
    public static final Spell BLESS_MEAT = new BlessMeat();
    public static final Spell SMOKE_BLITZ = new SmokeBlitz();
    public static final Spell SMOKE_BARRAGE = new SmokeBarrage();
    public static final Spell ICE_BLITZ = new IceBlitz();
    public static final Spell ICE_BARRAGE = new IceBarrage();
    public static final Spell DIVINE_SHIELD = new DivineShield();
    public static final Spell HASTE = new Haste();
    public static final Spell BONES_TO_APPLES = new BonesToApples();
    public static final Spell CONDUCTION = new Conduction();
    public static final Spell SOLAR_WINDS = new SolarWinds();
    public static final Spell PACIFY = new Pacify();
    public static final Spell TRANQUILITY = new Tranquility();
    public static final Spell FRENZY = new Frenzy();
    public static final Spell SCAPEGOAT = new Scapegoat();
    public static final Spell CLEANSING_FLAMES = new CleansingFlames();
    public static final Spell SOLAR_SENTINEL = new SolarSentinel();
    public static final Spell MIGRAINE = new Migraine();
    public static final Spell FROSTBITE = new Frostbite();
    public static final Spell RIGHTEOUS_DEFENSE = new RighteousDefense();
    public static final Spell FROSTBURN = new Frostburn();
    public static final Spell VENEFICIUM = new Veneficium();
    public static final Spell MEDITATE = new Meditate();
    public static final Spell ASSASSINATE = new Assassinate();
    public static final Spell ANTI_GRAVITATIONAL_PUSH = new AntiGravitationalPush();
    public static final Spell GRAVITATIONAL_PULL = new GravitationalPull();
    public static final Spell CLOAK_OF_THE_VOID = new CloakOfTheVoid();
    public static final Spell GRAVITATIONAL_FIELD = new GravitationalField();
    public static final Spell ANTI_GRAVITATIONAL_FIELD = new AntiGravitationalField();
    public static final Spell SMITE = new Smite();
    public static final Spell FISSION_BLAST = new FissionBlast();
    public static final Spell PIERCING_MASS = new PiercingMass();

    // WNG 1.0.2 Spells
    public static final Spell SUMMON_WEBSPITTER = new SummonWebspitter();
    public static final Spell VICIOUS_BITE = new ViciousBite();
    public static final Spell BETRAYAL = new Betrayal();
    public static final Spell SUMMON_RIGHTEOUS_DEFENDER = new SummonRighteousDefender();
    public static final Spell PREPARATION = new Preparation();
    public static final Spell FLESH_FEED = new FleshFeed();
    public static final Spell LUNAR_SALVE = new LunarSalve();
    public static final Spell HYPER_BEAM = new HyperBeam();
    public static final Spell DOMESTICATE = new Domesticate();
    public static final Spell TREMORS = new Tremors();
    public static final Spell BIDEN_BLAST = new BidenBlast();
    public static final Spell BLOOD_BARRAGE = new BloodBarrage();
    public static final Spell BLOOD_BLITZ = new BloodBlitz();
    public static final Spell SHADOW_BLITZ = new ShadowBlitz();
    public static final Spell SHADOW_BARRAGE = new ShadowBarrage();
    public static final Spell FUSION_BEAM = new FusionBeam();
    public static final Spell PULL = new Pull();
    public static final Spell WHIRLPOOL = new Whirlpool();
    public static final Spell COMBUSTION = new Combustion();

    // WNG 1.0.3 Spells
    public static final Spell HEX_SWINE = new HexSwine();
    public static final Spell POTION_BOMB = new PotionBomb();
    public static final Spell CONJURE_POTION = new ConjurePotion();
    public static final Spell HOME_TELEPORT = new HomeTeleport();
    public static final Spell SPAWN_TELEPORT = new SpawnTeleport();
    public static final Spell CONCOCTION_BOMB = new ConcoctionBomb();
    public static final Spell NAPALM = new Napalm();
    public static final Spell ENCHANT_CAULDRON = new EnchantCauldron();
    public static final Spell CHOMP = new Chomp();

    // WNG 1.0.4 Spells
    public static final Spell SUMMON_VAMPIRE_BAT = new SummonVampireBat();
    public static final Spell TAN_FLESH = new TanFlesh();
    public static final Spell LIGHTNING_REFLEXES = new LightningReflexes();
    public static final Spell RETRIBUTION = new Retribution();
    public static final Spell EYE_FOR_AN_EYE = new EyeForAnEye();

    // WNG 1.0.5 Spells
    public static final Spell ANIMAL_ALLEGIANCE = new AnimalAllegiance();
    //public static final Spell BOOST_MORALE = new Inspire();

    // WNG 1.0.7 Spells
    public static final Spell CALL_PACK_MULE = new CallPackMule();
    public static final Spell REST = new Rest();


    @SubscribeEvent
    public static void register(RegistryEvent.Register<Spell> event) {

        IForgeRegistry<Spell> registry = event.getRegistry();

        // WNG 1.0.0 Spells (42)

        registry.register(PHOTOSYNTHESIS);
        registry.register(SCORCH);
        registry.register(BLOOD_INFUSION);
        registry.register(ACCELERATED_MASS);
        registry.register(MOONLIGHT);
        registry.register(SAP_INTELLECT);
        registry.register(SAP_STRENGTH);
        registry.register(EQUALITY);
        registry.register(TASE);
        registry.register(JUSTICE);
        registry.register(HUMILITY);
        registry.register(SMOKESCREEN);
        registry.register(BLESS_MEAT);
        registry.register(SMOKE_BLITZ);
        registry.register(SMOKE_BARRAGE);
        registry.register(ICE_BLITZ);
        registry.register(ICE_BARRAGE);
        registry.register(DIVINE_SHIELD);
        registry.register(HASTE);
        registry.register(BONES_TO_APPLES);
        registry.register(CONDUCTION);
        registry.register(SOLAR_WINDS);
        registry.register(PACIFY);
        registry.register(TRANQUILITY);
        registry.register(FRENZY);
        registry.register(SCAPEGOAT);
        registry.register(CLEANSING_FLAMES);
        registry.register(SOLAR_SENTINEL);
        registry.register(MIGRAINE);
        registry.register(FROSTBITE);
        registry.register(RIGHTEOUS_DEFENSE);
        registry.register(FROSTBURN);
        registry.register(VENEFICIUM);
        registry.register(MEDITATE);
        registry.register(ASSASSINATE);
        registry.register(ANTI_GRAVITATIONAL_PUSH);
        registry.register(GRAVITATIONAL_PULL);
        registry.register(CLOAK_OF_THE_VOID);
        registry.register(GRAVITATIONAL_FIELD);
        registry.register(ANTI_GRAVITATIONAL_FIELD);
        registry.register(SMITE);
        registry.register(FISSION_BLAST);

        // WNG 1.0.2 Spells (19)

        registry.register(SUMMON_WEBSPITTER);
        registry.register(VICIOUS_BITE);
        registry.register(BETRAYAL);
        registry.register(SUMMON_RIGHTEOUS_DEFENDER);
        registry.register(PREPARATION);
        registry.register(FLESH_FEED);
        registry.register(LUNAR_SALVE);
        registry.register(HYPER_BEAM);
        registry.register(DOMESTICATE);
        registry.register(TREMORS);
        registry.register(BIDEN_BLAST);
        registry.register(BLOOD_BARRAGE);
        registry.register(BLOOD_BLITZ);
        registry.register(SHADOW_BLITZ);
        registry.register(SHADOW_BARRAGE);
        registry.register(FUSION_BEAM);
        registry.register(PULL);
        registry.register(WHIRLPOOL);
        registry.register(COMBUSTION);

        // WNG 1.0.3 Spells (9)

        registry.register(HEX_SWINE);
        registry.register(POTION_BOMB);
        registry.register(CONJURE_POTION);
        registry.register(HOME_TELEPORT);
        registry.register(SPAWN_TELEPORT);
        registry.register(CONCOCTION_BOMB);
        registry.register(NAPALM);
        registry.register(ENCHANT_CAULDRON);
        registry.register(CHOMP);

        // WNG 1.0.4 Spells (5)

        registry.register(SUMMON_VAMPIRE_BAT);
        registry.register(TAN_FLESH);
        registry.register(LIGHTNING_REFLEXES);
        registry.register(RETRIBUTION);
        registry.register(EYE_FOR_AN_EYE);

        // WNG 1.0.5 Spells (1)

        registry.register(ANIMAL_ALLEGIANCE);
        //registry.register(PIERCING_MASS);

        // WNG 1.0.7 Spells
        registry.register(CALL_PACK_MULE);
        registry.register(REST);

    }
}


