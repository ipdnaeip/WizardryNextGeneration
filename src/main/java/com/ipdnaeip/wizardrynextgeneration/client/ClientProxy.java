package com.ipdnaeip.wizardrynextgeneration.client;

import com.ipdnaeip.wizardrynextgeneration.CommonProxy;
import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.client.renderer.entity.construct.RenderNapalm;
import com.ipdnaeip.wizardrynextgeneration.client.renderer.entity.layers.LayerHolyThorns;
import com.ipdnaeip.wizardrynextgeneration.client.renderer.entity.living.RenderVampireBat;
import com.ipdnaeip.wizardrynextgeneration.client.renderer.entity.living.RenderRighteousDefender;
import com.ipdnaeip.wizardrynextgeneration.client.renderer.entity.living.RenderWebspitter;
import com.ipdnaeip.wizardrynextgeneration.client.renderer.entity.layers.LayerDivineShield;
import com.ipdnaeip.wizardrynextgeneration.client.renderer.entity.layers.LayerSolarSentinel;
import com.ipdnaeip.wizardrynextgeneration.entity.construct.EntityAntiGravitationalField;
import com.ipdnaeip.wizardrynextgeneration.entity.construct.EntityGravitationalField;
import com.ipdnaeip.wizardrynextgeneration.entity.construct.EntityNapalm;
import com.ipdnaeip.wizardrynextgeneration.entity.construct.EntityWhirlpool;
import com.ipdnaeip.wizardrynextgeneration.entity.living.EntityVampireBat;
import com.ipdnaeip.wizardrynextgeneration.entity.living.EntityPigZombieConvert;
import com.ipdnaeip.wizardrynextgeneration.entity.living.EntityRighteousDefender;
import com.ipdnaeip.wizardrynextgeneration.entity.living.EntityWebspitter;
import com.ipdnaeip.wizardrynextgeneration.entity.projectile.EntityAcceleratedMass;
import com.ipdnaeip.wizardrynextgeneration.entity.projectile.EntityFissionBlast;
import com.ipdnaeip.wizardrynextgeneration.entity.projectile.EntityConjuredPotion;
//import com.ipdnaeip.wizardrynextgeneration.entity.projectile.EntityPiercingMass;
import com.ipdnaeip.wizardrynextgeneration.entity.projectile.EntityPiercingMass;
import electroblob.wizardry.client.renderer.entity.RenderMagicArrow;
import electroblob.wizardry.client.renderer.entity.RenderProjectile;
import electroblob.wizardry.client.renderer.entity.RenderSigil;
import electroblob.wizardry.client.renderer.entity.layers.LayerTiledOverlay;
import net.minecraft.client.renderer.entity.RenderPigZombie;
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
        RenderingRegistry.registerEntityRenderingHandler(EntityNapalm.class, RenderNapalm::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityWhirlpool.class, manager -> new RenderSigil(manager, new ResourceLocation(WizardryNextGeneration.MODID, "textures/entity/whirlpool.png"), -5F, false));

        //living
        RenderingRegistry.registerEntityRenderingHandler(EntityPigZombieConvert.class, RenderPigZombie::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRighteousDefender.class, RenderRighteousDefender::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityVampireBat.class, RenderVampireBat::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityWebspitter.class, RenderWebspitter::new);

        //projectiles
        RenderingRegistry.registerEntityRenderingHandler(EntityAcceleratedMass.class, manager -> new RenderMagicArrow(manager, new ResourceLocation(WizardryNextGeneration.MODID, "textures/entity/accelerated_mass.png"), false, 11, 5, 11, 5, true));
        RenderingRegistry.registerEntityRenderingHandler(EntityConjuredPotion.class, (manager) -> new RenderProjectile(manager, 0.6F, new ResourceLocation(WizardryNextGeneration.MODID, "textures/items/conjured_potion.png"), false));
        RenderingRegistry.registerEntityRenderingHandler(EntityFissionBlast.class, manager -> new RenderMagicArrow(manager, new ResourceLocation(WizardryNextGeneration.MODID, "textures/entity/fission_blast.png"), false, 3, 3, 3, 3, true));
        RenderingRegistry.registerEntityRenderingHandler(EntityPiercingMass.class, manager -> new RenderMagicArrow(manager, new ResourceLocation(WizardryNextGeneration.MODID, "textures/entity/piercing_mass.png"), false, 11, 5, 11, 5, true));
    }

    @Override
    public void initialiseLayers() {

        LayerTiledOverlay.initialiseLayers(LayerDivineShield::new);
        LayerTiledOverlay.initialiseLayers(LayerHolyThorns::new);
        LayerTiledOverlay.initialiseLayers(LayerSolarSentinel::new);
    }

}
