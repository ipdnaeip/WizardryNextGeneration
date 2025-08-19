package com.ipdnaeip.wizardrynextgeneration.core;

import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.ArrayList;
import java.util.List;

public class WNGMixinLoader implements ILateMixinLoader {
	@Override
	public List<String> getMixinConfigs() {
		List<String> configs = new ArrayList<>();
		// CLIENT ONLY
		if (WNGLoadingPlugin.isClient) {
			configs.add("wizardrynextgeneration.ebwizardry.client.mixins.json");
		}
		// COMMON
		configs.add("wizardrynextgeneration.ebwizardry.mixins.json");
		return configs;
	}

	@Override
	public boolean shouldMixinConfigQueue(String mixinConfig) {
		if (WNGLoadingPlugin.isClient) {
			if (mixinConfig.equals("wizardrynextgeneration.ebwizardry.client.mixins.json")) {
				return true;
			}
		}
		return true;
	}
}