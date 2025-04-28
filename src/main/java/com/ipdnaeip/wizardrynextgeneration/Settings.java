package com.ipdnaeip.wizardrynextgeneration;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static electroblob.wizardry.Settings.toResourceLocations;

@Config(modid = WizardryNextGeneration.MODID)
public class Settings {

    public ResourceLocation[] bleedEffectWhitelist = toResourceLocations(generalSettings.bleed_effect_whitelist);
    public ResourceLocation[] bleedEffectBlacklist = toResourceLocations(generalSettings.bleed_effect_blacklist);

    @SuppressWarnings("unused")
    @Mod.EventBusSubscriber(modid = WizardryNextGeneration.MODID)
    private static class EventHandler {
        /**
         * Inject the new values and save to the config file when the config has been changed from the GUI.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(WizardryNextGeneration.MODID)) {
                ConfigManager.sync(WizardryNextGeneration.MODID, Config.Type.INSTANCE);
            }
        }
    }

    @Config.Name("General Settings")
    @Config.LangKey("settings.wizardrynextgeneration:general_settings")
    public static GeneralSettings generalSettings = new GeneralSettings();

    public static class GeneralSettings {

        @Config.Name("Bleed Effect Whitelist")
        @Config.Comment("Whitelist for entities that can be affected by the bleed effect. Modifying this will override blacklisted mobs.")
        public String[] bleed_effect_whitelist = {};

        @Config.Name("Bleed Effect Blacklist")
        @Config.Comment("Blacklist for entities that can be affected by the bleed effect. By default, all undead and golems are blacklisted and must be whitelisted in order to be affected.")
        public String[] bleed_effect_blacklist = {};

    }
    @Config.Name("Spell Settings")
    @Config.LangKey("settings.wizardrynextgeneration:spell_settings")
    public static SpellSettings spellSettings = new SpellSettings();

    public static class SpellSettings {

        @Config.Name("Allies Access Pack Mule")
        @Config.Comment("Determines if allies can access a player's pack mule.")
        public boolean alliesAccessPackMule = false;

    }
}

