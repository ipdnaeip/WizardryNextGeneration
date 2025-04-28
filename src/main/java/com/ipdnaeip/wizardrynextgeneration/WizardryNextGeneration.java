package com.ipdnaeip.wizardrynextgeneration;

import com.ipdnaeip.wizardrynextgeneration.handler.ExperiencedPotionHandler;
import com.ipdnaeip.wizardrynextgeneration.handler.WNGGuiHandler;
import com.ipdnaeip.wizardrynextgeneration.item.ItemWNGWandUpgrade;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGAdvancementTriggers;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGLoot;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGTabs;
import com.ipdnaeip.wizardrynextgeneration.util.GeneratorSnippets;
import electroblob.wizardry.data.WizardData;
import electroblob.wizardry.util.SpellNetworkIDSorter;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(modid = WizardryNextGeneration.MODID, name = WizardryNextGeneration.NAME, version = WizardryNextGeneration.VERSION)
public class WizardryNextGeneration
{
    public static final String MODID = "wizardrynextgeneration";
    public static final String NAME = "Wizardry Next Generation";
    public static final String VERSION = "1.0.7";
    public static Settings settings = new Settings();
    public static Logger logger;
    public static File configDirectory;
    @Mod.Instance(WizardryNextGeneration.MODID)
    public static WizardryNextGeneration instance;

    @SidedProxy(clientSide = "com.ipdnaeip.wizardrynextgeneration.client.ClientProxy", serverSide = "com.ipdnaeip.wizardrynextgeneration.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        settings = new Settings();
        WNGLoot.register();
        WNGItems.registerBookshelfModelTextures();
        WNGAdvancementTriggers.register();
        proxy.registerRenderers();
        WizardData.registerStoredVariables(ExperiencedPotionHandler.EXPERIENCED_POTION_NAMES);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new WNGGuiHandler());
        proxy.registerParticles();
        WNGItems.registerDispenseBehaviours();
        WNGItems.registerBookItems();
        ItemWNGWandUpgrade.init();
        GeneratorSnippets.generateAll(MODID, NAME, "ipdnaeip/WizardryNextGeneration/tree/main", I18n.format(WNGTabs.WIZARDRYNEXTGENERATION_GEAR.getTranslationKey()));
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.initialiseLayers();
        SpellNetworkIDSorter.init();
    }
}
