package com.ipdnaeip.wizardrynextgeneration;

import com.ipdnaeip.wizardrynextgeneration.integration.baubles.WNGBaublesIntegration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGLoot;
import electroblob.wizardry.util.SpellNetworkIDSorter;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = WizardryNextGeneration.MODID, name = WizardryNextGeneration.NAME, version = WizardryNextGeneration.VERSION)
public class WizardryNextGeneration
{
    public static final String MODID = "wizardrynextgeneration";
    public static final String NAME = "Wizardry Next Generation";
    public static final String VERSION = "1.0";

    public static Logger logger;

    @Mod.Instance(WizardryNextGeneration.MODID)
    public static WizardryNextGeneration instance;

    @SidedProxy(clientSide = "com.ipdnaeip.wizardrynextgeneration.client.ClientProxy", serverSide = "com.ipdnaeip.wizardrynextgeneration.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        WNGLoot.register();
        WNGItems.registerBookshelfModelTextures();
        proxy.registerRenderers();
        WNGBaublesIntegration.init();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        WNGItems.registerDispenseBehaviours();
        WNGItems.registerBookItems();
        proxy.registerParticles();

    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.initialiseLayers();
        SpellNetworkIDSorter.init();
    }
}
