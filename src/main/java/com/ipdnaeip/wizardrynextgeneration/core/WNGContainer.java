package com.ipdnaeip.wizardrynextgeneration.core;

import com.google.common.eventbus.EventBus;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;

public class WNGContainer extends DummyModContainer {
	public WNGContainer() {
		super(new ModMetadata());
		ModMetadata meta = this.getMetadata();
		meta.modId = "wizardrynextgenerationpackcore";
		meta.name = "Wizardry Next Generation";
		meta.description = "Core functionality of Wizardry Next Generation";
		meta.version = "1.12.2-1.0.7";
		meta.authorList.add("ipdnaeip");
	}

	@Override
	public boolean registerBus(EventBus bus, LoadController controller) {
		bus.register(this);
		return true;
	}
}