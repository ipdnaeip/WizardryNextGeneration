/*
package com.ipdnaeip.wizardrynextgeneration.core;

import electroblob.wizardry.Wizardry;
import net.minecraftforge.fml.common.Loader;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.ArrayList;
import java.util.List;

public class WNGMixinLoader implements ILateMixinLoader {
	@Override
	public List<String> getMixinConfigs() {
		List<String> configs = new ArrayList<>();
		// CLIENT ONLY
		// COMMON
		configs.add("wizardrynextgeneration.ebwizardry.mixins.json");
		return configs;
	}

	@Override
	public boolean shouldMixinConfigQueue(String mixinConfig) {
		if (mixinConfig.contains("ebwizardry")) {
			return Loader.isModLoaded("ebwizardry");
		}
		return true;
	}
}*/
