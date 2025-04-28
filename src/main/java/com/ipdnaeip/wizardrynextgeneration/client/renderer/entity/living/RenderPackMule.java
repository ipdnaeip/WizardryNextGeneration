package com.ipdnaeip.wizardrynextgeneration.client.renderer.entity.living;

import net.minecraft.client.renderer.entity.RenderAbstractHorse;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.util.ResourceLocation;

public class RenderPackMule extends RenderAbstractHorse {

    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/horse/mule.png");

    public RenderPackMule(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    protected ResourceLocation getEntityTexture(AbstractHorse entity) {
        return TEXTURE;
    }
}
