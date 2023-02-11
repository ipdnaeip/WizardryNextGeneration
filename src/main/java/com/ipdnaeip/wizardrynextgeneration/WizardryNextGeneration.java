package com.ipdnaeip.wizardrynextgeneration;

import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = WizardryNextGeneration.MODID, name = WizardryNextGeneration.NAME, version = WizardryNextGeneration.VERSION)
public class WizardryNextGeneration
{
    public static final String MODID = "wizardrynextgeneration";
    public static final String NAME = "Wizardry Next Generation";
    public static final String VERSION = "1.0";

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        WNGItems.registerBookshelfModelTextures();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        WNGItems.registerBookItems();
        // some example code
        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
}
