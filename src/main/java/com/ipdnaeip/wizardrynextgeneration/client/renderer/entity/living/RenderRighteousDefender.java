package com.ipdnaeip.wizardrynextgeneration.client.renderer.entity.living;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.entity.living.EntityRighteousDefender;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.util.ResourceLocation;

public class RenderRighteousDefender extends RenderBiped<EntityRighteousDefender> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(WizardryNextGeneration.MODID, "textures/entity/righteous_defender.png");

    public RenderRighteousDefender(RenderManager manager) {
        super(manager, new ModelBiped(), 0.5F);
        this.addLayer(new LayerBipedArmor(this));
    }

    protected ResourceLocation getEntityTexture(EntityRighteousDefender entity) {
        return TEXTURE;
    }
}
