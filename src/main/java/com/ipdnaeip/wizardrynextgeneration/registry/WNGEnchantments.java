package com.ipdnaeip.wizardrynextgeneration.registry;


import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.enchantment.EnchantmentRanger;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nonnull;

@GameRegistry.ObjectHolder(WizardryNextGeneration.MODID)
@Mod.EventBusSubscriber
public class WNGEnchantments {
    public static final Enchantment ranger = placeholder();
    public static final Enchantment phalanx = placeholder();

    private WNGEnchantments() {}

    @Nonnull
    @SuppressWarnings("ConstantConditions")
    private static <T> T placeholder() { return null; }

    @SubscribeEvent
    public static void register(RegistryEvent.Register<Enchantment> event) {

        event.getRegistry().register(new EnchantmentRanger().setRegistryName(WizardryNextGeneration.MODID, "ranger"));
    }

}
