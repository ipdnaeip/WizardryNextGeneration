package com.ipdnaeip.wizardrynextgeneration.client;

import com.ipdnaeip.wizardrynextgeneration.CommonProxy;
import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.client.renderer.entity.layers.LayerSolarSentinel;
import com.ipdnaeip.wizardrynextgeneration.entity.living.EntityWebspitter;
import com.ipdnaeip.wizardrynextgeneration.entity.projectile.EntityAcceleratedMass;
import electroblob.wizardry.client.renderer.entity.RenderMagicArrow;
import electroblob.wizardry.client.renderer.entity.RenderProjectile;
import electroblob.wizardry.client.renderer.entity.layers.LayerTiledOverlay;
import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

    @Override
    public void initialiseLayers() {
        LayerTiledOverlay.initialiseLayers(LayerSolarSentinel::new);
    }

    @Override
    public void registerRenderers() {

        //living
        RenderingRegistry.registerEntityRenderingHandler(EntityWebspitter.class, RenderSpider::new);

        //projectiles
        RenderingRegistry.registerEntityRenderingHandler(EntityAcceleratedMass.class, manager -> new RenderMagicArrow(manager, new ResourceLocation(WizardryNextGeneration.MODID, "textures/entity/accelerated_mass.png"), false, 11, 5, 11, 5, true));

    }
}
