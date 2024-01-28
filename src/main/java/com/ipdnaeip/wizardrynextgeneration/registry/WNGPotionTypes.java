package com.ipdnaeip.wizardrynextgeneration.registry;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import electroblob.wizardry.registry.WizardryPotions;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class WNGPotionTypes {

    public static final PotionType empowerment = new PotionType(WizardryNextGeneration.MODID + ":empowerment", new PotionEffect(WizardryPotions.empowerment, 600, 0)).setRegistryName(WizardryNextGeneration.MODID, "empowerment");
    public static final PotionType empowerment_long = new PotionType(WizardryNextGeneration.MODID + ":empowerment", new PotionEffect(WizardryPotions.empowerment, 900, 0)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_long");
    public static final PotionType empowerment_strong = new PotionType(WizardryNextGeneration.MODID + ":empowerment", new PotionEffect(WizardryPotions.empowerment, 400, 1)).setRegistryName(WizardryNextGeneration.MODID, "empowerment_strong");

    @SubscribeEvent
    public static void register(RegistryEvent.Register<PotionType> event) {
        IForgeRegistry<PotionType> registry = event.getRegistry();

        registry.register(empowerment);
        registry.register(empowerment_long);
        registry.register(empowerment_strong);

    }

}