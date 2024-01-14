package com.ipdnaeip.wizardrynextgeneration.registry;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.block.BlockEnchantedCauldron;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;

@GameRegistry.ObjectHolder(WizardryNextGeneration.MODID)
@Mod.EventBusSubscriber
public class WNGBlocks {

    private WNGBlocks() {
    }

    @Nonnull
    @SuppressWarnings("ConstantConditions")
    private static <T> T placeholder() {
        return null;
    }

    public static final Block enchanted_cauldron = placeholder();

    public static void registerBlock(IForgeRegistry<Block> registry, String name, Block block) {
        block.setRegistryName(WizardryNextGeneration.MODID, name);
        block.setTranslationKey(block.getRegistryName().toString());
        registry.register(block);
    }

    @SubscribeEvent
    public static void register(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();
        registerBlock(registry, "enchanted_cauldron", new BlockEnchantedCauldron().setCreativeTab(WNGTabs.WIZARDRYNEXTGENERATION));
    }
}