package com.ipdnaeip.wizardrynextgeneration.registry;


import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.enchantment.EnchantmentPhalanx;
import com.ipdnaeip.wizardrynextgeneration.enchantment.EnchantmentRanger;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;

@GameRegistry.ObjectHolder(WizardryNextGeneration.MODID)
@Mod.EventBusSubscriber
public class WNGEnchantments {
    
    private WNGEnchantments() {}

    public static final Enchantment RANGER = new EnchantmentPhalanx();
    public static final Enchantment PHALANX = new EnchantmentRanger();

    @SubscribeEvent
    public static void register(RegistryEvent.Register<Enchantment> event) {

        IForgeRegistry<Enchantment> registry = event.getRegistry();

        registerEnchantment(registry, "phalanx", new EnchantmentPhalanx());
        registerEnchantment(registry, "ranger", new EnchantmentRanger());
    }

    public static void registerEnchantment(IForgeRegistry<Enchantment> registry, String name, Enchantment enchantment) {
        enchantment.setRegistryName(WizardryNextGeneration.MODID, name);
        registry.register(enchantment);
    }

}
