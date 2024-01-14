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
}

/*
@Config(modid = WizardryNextGeneration.MODID)
public final class Settings {

    public static final String TWEAKS_CATEGORY = "tweaks";
    private Configuration config;
    public ResourceLocation[] bleedEffectWhitelist;
    public ResourceLocation[] bleedEffectBlacklist;

    public Settings() {
        this.bleedEffectWhitelist = new ResourceLocation[0];
        this.bleedEffectBlacklist = new ResourceLocation[0];
    }

    public void initConfig(FMLPreInitializationEvent event) {
        this.config = new Configuration(new File(WizardryNextGeneration.configDirectory, WizardryNextGeneration.MODID + ".cfg"));
    }

    private void setupTweaksConfig() {
        List<String> propOrder = new ArrayList();
        this.config.addCustomCategoryComment(TWEAKS_CATEGORY, "Assorted settings for tweaking the mod's behaviour. In multiplayer, the server/LAN host settings will apply.");
        Property property = this.config.get(TWEAKS_CATEGORY, "bleedEffectWhitelist", new String[]{}, "Whitelist for entities that can be affected by the bleed effect. Modifying this will override blacklisted mobs.");
        property.setLanguageKey("config.wizardrynextgeneration.bleed_effect_whitelist");
        this.bleedEffectWhitelist = getResourceLocationList(property);
        propOrder.add(property.getName());
        property = this.config.get(TWEAKS_CATEGORY, "bleedEffectBlacklist", new String[]{}, "Blacklist for entities that can be affected by the bleed effect. By default, all undead and golems are blacklisted and must be whitelisted in order to be affected.");
        property.setLanguageKey("config.wizardrynextgeneration.bleed_effect_whitelist");
        this.bleedEffectWhitelist = getResourceLocationList(property);
        propOrder.add(property.getName());
    }

    public static ResourceLocation[] getResourceLocationList(Property property) {
        return toResourceLocations(property.getStringList());
    }

    public static ResourceLocation[] toResourceLocations(String... strings) {
        return (ResourceLocation[]) Arrays.stream(strings).map((s) -> {
            return new ResourceLocation(s.toLowerCase(Locale.ROOT).trim());
        }).toArray(ResourceLocation[]::new);
    }
}
*/
