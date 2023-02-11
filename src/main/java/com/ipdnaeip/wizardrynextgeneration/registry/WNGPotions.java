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

    public static final Potion disempowerment = placeholder();
    public static final Potion magic_weakness = placeholder();
    public static final Potion pacify = placeholder();
    public static final Potion divine_shield = placeholder();
    public static final Potion solar_winds = placeholder();
    public static final Potion cleansing_flames = placeholder();
    public static final Potion solar_sentinel = placeholder();

    public static void registerPotion(IForgeRegistry<Potion> registry, String name, Potion potion) {
        potion.setRegistryName(WizardryNextGeneration.MODID, name);
        potion.setPotionName("potion." + potion.getRegistryName().toString());
        registry.register(potion);
    }

    @SubscribeEvent
    public static void register(RegistryEvent.Register<Potion> event) {

        IForgeRegistry<Potion> registry = event.getRegistry();

        registerPotion(registry, "disempowerment", new PotionDisempowerment());
        registerPotion(registry, "magic_weakness", new PotionMagicWeakness());
        registerPotion(registry, "pacify", new PotionPacify());
        registerPotion(registry, "divine_shield", new PotionDivineShield());
        registerPotion(registry, "solar_winds", new PotionSolarWinds());
        registerPotion(registry, "cleansing_flames", new PotionCleansingFlames());
        registerPotion(registry, "solar_sentinel", new PotionSolarSentinel());
    }

}
