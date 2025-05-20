package com.ipdnaeip.wizardrynextgeneration.core;

import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import zone.rong.mixinbooter.ILateMixinLoader;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@IFMLLoadingPlugin.Name("WizardryNextGenerationMods")
@IFMLLoadingPlugin.MCVersion(ForgeVersion.mcVersion)
public class WNGMixinLate implements IFMLLoadingPlugin, ILateMixinLoader
{

    @Override
    public List<String> getMixinConfigs()
    {
        List<String> configs = new ArrayList<>();
        configs.add("wizardrynextgeneration.mixins.ebwizardry.json");
        return configs;
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}