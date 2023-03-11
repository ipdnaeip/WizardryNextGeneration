package com.ipdnaeip.wizardrynextgeneration.client;

import com.ipdnaeip.wizardrynextgeneration.CommonProxy;
import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.client.renderer.entity.RenderWebspitter;
import com.ipdnaeip.wizardrynextgeneration.client.renderer.entity.layers.LayerSolarSentinel;
import com.ipdnaeip.wizardrynextgeneration.entity.construct.EntityAntiGravitationalField;
import com.ipdnaeip.wizardrynextgeneration.entity.construct.EntityGravitationalField;
import com.ipdnaeip.wizardrynextgeneration.entity.living.EntityWebspitter;
import com.ipdnaeip.wizardrynextgeneration.entity.projectile.EntityAcceleratedMass;
import com.ipdnaeip.wizardrynextgeneration.entity.projectile.EntityFissionBlast;
import com.ipdnaeip.wizardrynextgeneration.entity.projectile.EntityPiercingMass;
import electroblob.wizardry.client.renderer.entity.RenderMagicArrow;
import electroblob.wizardry.client.renderer.entity.RenderSigil;
import electroblob.wizardry.client.renderer.entity.layers.LayerTiledOverlay;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerParticles() {

    }


    @Override
    public void registerRenderers() {

        //construct
        RenderingRegistry.registerEntityRenderingHandler(EntityAntiGravitationalField.class, manager -> new RenderSigil(manager, new ResourceLocation(WizardryNextGeneration.MODID, "textures/entity/anti_gravitational_field.png"), 0.0F, false));
        RenderingRegistry.registerEntityRenderingHandler(EntityGravitationalField.class, manager -> new RenderSigil(manager, new ResourceLocation(WizardryNextGeneration.MODID, "textures/entity/gravitational_field.png"), 0.0F, false));

        //living
        RenderingRegistry.registerEntityRenderingHandler(EntityWebspitter.class, (manager) -> {return new RenderWebspitter(manager) {
                protected ResourceLocation getEntityTexture(EntityWebspitter entity) {
                    return new ResourceLocation("wizardrynextgeneration", "textures/entity/webspitter.png");
                }
            };
        });

        //projectiles
        RenderingRegistry.registerEntityRenderingHandler(EntityAcceleratedMass.class, manager -> new RenderMagicArrow(manager, new ResourceLocation(WizardryNextGeneration.MODID, "textures/entity/accelerated_mass.png"), false, 11, 5, 11, 5, true));
        RenderingRegistry.registerEntityRenderingHandler(EntityFissionBlast.class, manager -> new RenderMagicArrow(manager, new ResourceLocation(WizardryNextGeneration.MODID, "textures/entity/fission_blast.png"), false, 3, 3, 3, 3, true));
        RenderingRegistry.registerEntityRenderingHandler(EntityPiercingMass.class, manager -> new RenderMagicArrow(manager, new ResourceLocation(WizardryNextGeneration.MODID, "textures/entity/piercing_mass.png"), false, 11, 5, 11, 5, true));

    }

    @Override
    public void initialiseLayers() {

        LayerTiledOverlay.initialiseLayers(LayerSolarSentinel::new);
    }

}
