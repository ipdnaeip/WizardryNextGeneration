package com.ipdnaeip.wizardrynextgeneration;

import com.ipdnaeip.wizardrynextgeneration.handler.WNGGuiHandler;
import com.ipdnaeip.wizardrynextgeneration.integration.baubles.WNGBaublesIntegration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGAdvancementTriggers;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGLoot;
import electroblob.wizardry.util.SpellNetworkIDSorter;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.Logger;

@Mod(modid = WizardryNextGeneration.MODID, name = WizardryNextGeneration.NAME, version = WizardryNextGeneration.VERSION)
public class WizardryNextGeneration
{
    public static final String MODID = "wizardrynextgeneration";
    public static final String NAME = "Wizardry Next Generation";
    public static final String VERSION = "1.0.2";

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
        WNGAdvancementTriggers.register();
        proxy.registerRenderers();
        WNGBaublesIntegration.init();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new WNGGuiHandler());
        proxy.registerParticles();
        WNGItems.registerDispenseBehaviours();
        WNGItems.registerBookItems();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.initialiseLayers();
        SpellNetworkIDSorter.init();
    }
}
