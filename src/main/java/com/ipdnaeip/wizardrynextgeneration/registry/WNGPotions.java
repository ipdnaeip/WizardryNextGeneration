package com.ipdnaeip.wizardrynextgeneration.registry;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.potion.*;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;

@GameRegistry.ObjectHolder(WizardryNextGeneration.MODID)
@Mod.EventBusSubscriber
public class WNGPotions {

    @Nonnull
    @SuppressWarnings("ConstantConditions")
    private static <T> T placeholder() { return null; }

    public static final Potion betrayal = placeholder();
    public static final Potion bleed = placeholder();
    public static final Potion camouflage = placeholder();
    public static final Potion cleansing_flames = placeholder();
    public static final Potion disempowerment = placeholder();
    public static final Potion divine_shield = placeholder();
    public static final Potion frenzy = placeholder();
    public static final Potion gravity = placeholder();
    public static final Potion magic_weakness = placeholder();
    public static final Potion pacify = placeholder();
    public static final Potion preparation = placeholder();
    public static final Potion shock_weakness = placeholder();
    public static final Potion solar_winds = placeholder();
    public static final Potion suffocation = placeholder();
    public static final Potion taunt = placeholder();
    public static final Potion veneficium = placeholder();

    public static void registerPotion(IForgeRegistry<Potion> registry, String name, Potion potion) {
        potion.setRegistryName(WizardryNextGeneration.MODID, name);
        potion.setPotionName("potion." + potion.getRegistryName().toString());
        registry.register(potion);
    }

    @SubscribeEvent
    public static void register(RegistryEvent.Register<Potion> event) {

        IForgeRegistry<Potion> registry = event.getRegistry();

        registerPotion(registry, "betrayal", new PotionBetrayal());
        registerPotion(registry, "bleed", new PotionBleed());
        registerPotion(registry, "camouflage", new PotionCamouflage().setBeneficial());
        registerPotion(registry, "cleansing_flames", new PotionCleansingFlames().setBeneficial());
        registerPotion(registry, "disempowerment", new PotionDisempowerment());
        registerPotion(registry, "divine_shield", new PotionDivineShield().setBeneficial());
        registerPotion(registry, "frenzy", new PotionFrenzy());
        registerPotion(registry, "gravity", new PotionGravity());
        registerPotion(registry, "magic_weakness", new PotionMagicWeakness());
        registerPotion(registry, "pacify", new PotionPacify());
        registerPotion(registry, "preparation", new PotionPreparation().setBeneficial());
        registerPotion(registry, "shock_weakness", new PotionShockWeakness());
        registerPotion(registry, "solar_winds", new PotionSolarWinds().setBeneficial());
        registerPotion(registry, "suffocation", new PotionSuffocation());
        registerPotion(registry, "taunt", new PotionTaunt());
        registerPotion(registry, "veneficium", new PotionVeneficium());


    }
}
