package com.ipdnaeip.wizardrynextgeneration.registry;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.entity.construct.EntityAntiGravitationalField;
import com.ipdnaeip.wizardrynextgeneration.entity.construct.EntityGravitationalField;
import com.ipdnaeip.wizardrynextgeneration.entity.living.EntityWebspitter;
import com.ipdnaeip.wizardrynextgeneration.entity.projectile.EntityAcceleratedMass;
import com.ipdnaeip.wizardrynextgeneration.entity.projectile.EntityFissionBlast;
import com.ipdnaeip.wizardrynextgeneration.entity.projectile.EntityPiercingMass;
import electroblob.wizardry.Wizardry;
import electroblob.wizardry.entity.construct.EntityHealAura;
import electroblob.wizardry.registry.WizardryEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Arrays;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber
public class WNGEntities {

    private WNGEntities() {}

    private static int id = 0;

    private static <T extends Entity> EntityEntryBuilder<T> createEntry(Class<T> entityClass, String name, TrackingType tracking) {
        return createEntry(entityClass, name).tracker(tracking.range, tracking.interval, tracking.trackVelocity);
    }

    private static <T extends Entity> EntityEntryBuilder<T> createEntry(Class<T> entityClass, String name) {
        ResourceLocation registryName = new ResourceLocation(WizardryNextGeneration.MODID, name);
        return EntityEntryBuilder.<T>create().entity(entityClass).id(registryName, id++).name(registryName.toString());
    }

    private static <T extends Entity> EntityEntryBuilder<T> createEntry(Class<T> entityClass, String name, String modid, TrackingType tracking) {
        return createEntry(entityClass, name, modid).tracker(tracking.range, tracking.interval, tracking.trackVelocity);
    }

    private static <T extends Entity> EntityEntryBuilder<T> createEntry(Class<T> entityClass, String name, String modid) {
        ResourceLocation registryName = new ResourceLocation(modid, name);
        return EntityEntryBuilder.<T>create().entity(entityClass).id(registryName, id++).name(registryName.toString());
    }


    enum TrackingType {
        LIVING(80, 3, true),
        PROJECTILE(64, 10, true),
        CONSTRUCT(160, 10, false);

        int range;
        int interval;
        boolean trackVelocity;

        TrackingType(int range, int interval, boolean trackVelocity) {
            this.range = range;
            this.interval = interval;
            this.trackVelocity = trackVelocity;
        }
    }

    @SubscribeEvent
    public static void register(RegistryEvent.Register<EntityEntry> event) {

        IForgeRegistry<EntityEntry> registry = event.getRegistry();

        //construct
        registry.register(createEntry(EntityAntiGravitationalField.class, "anti_gravitational_field", WNGEntities.TrackingType.CONSTRUCT).build());
        registry.register(createEntry(EntityGravitationalField.class, "gravitational_field", WNGEntities.TrackingType.CONSTRUCT).build());

        //living
        registry.register(createEntry(EntityWebspitter.class, "webspitter", WNGEntities.TrackingType.LIVING).egg(3357763, 960667).spawn(EnumCreatureType.MONSTER, 20, 1, 3, ForgeRegistries.BIOMES.getValuesCollection().stream().filter((b) -> BiomeDictionary.hasType(b, BiomeDictionary.Type.PLAINS) ).collect(Collectors.toSet())).build());

        //projectile
        registry.register(createEntry(EntityAcceleratedMass.class, "accelerated_mass", WNGEntities.TrackingType.PROJECTILE).build());
        registry.register(createEntry(EntityFissionBlast.class, "fission_blast", WNGEntities.TrackingType.PROJECTILE).build());
        registry.register(createEntry(EntityPiercingMass.class, "piercing_mass", WNGEntities.TrackingType.PROJECTILE).build());

    }

}
