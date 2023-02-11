package com.ipdnaeip.wizardrynextgeneration.client.renderer.entity;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.entity.living.EntityWebspitter;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderWebspitter extends Render<EntityWebspitter> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(WizardryNextGeneration.MODID, "textures/entity/webspitter.png");

    public RenderWebspitter(RenderManager manager) {
        super(manager);
        }

    @Nullable
    protected ResourceLocation getEntityTexture(EntityWebspitter entity) {
        return TEXTURE;
    }
}
