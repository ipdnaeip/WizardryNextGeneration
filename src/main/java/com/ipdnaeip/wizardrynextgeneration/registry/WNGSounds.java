package com.ipdnaeip.wizardrynextgeneration.registry;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(WizardryNextGeneration.MODID)
@Mod.EventBusSubscriber(modid = WizardryNextGeneration.MODID)
public class WNGSounds {

    private WNGSounds() {}

    public static final SoundEvent VAMPIRE_BAT_BITE = createSound("entity.vampire_bat.bite");

    public static SoundEvent createSound(String name) {
        return createSound(WizardryNextGeneration.MODID, name);
    }

    public static SoundEvent createSound(String modID, String name) {
        return new SoundEvent (new ResourceLocation(modID, name)).setRegistryName(name);
    }

    @SubscribeEvent
    public static void register(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().register(VAMPIRE_BAT_BITE);
    }
}
