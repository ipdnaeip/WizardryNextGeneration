package com.ipdnaeip.wizardrynextgeneration.client.renderer.entity.living;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.client.model.ModelVampireBat;
import com.ipdnaeip.wizardrynextgeneration.client.renderer.entity.layers.LayerVampireBatEyes;
import com.ipdnaeip.wizardrynextgeneration.entity.living.EntityVampireBat;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;


public class RenderVampireBat extends RenderLiving<EntityVampireBat> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(WizardryNextGeneration.MODID, "textures/entity/vampire_bat.png");

    public RenderVampireBat(RenderManager manager) {
        super(manager, new ModelVampireBat(), 0.25F);
        this.addLayer(new LayerVampireBatEyes<>(this));
    }

    @Override
    protected void preRenderCallback(EntityVampireBat entitylivingbaseIn, float partialTickTime)
    {
        GlStateManager.scale(0.35F, 0.35F, 0.35F);
    }

    @Override
    protected void applyRotations(EntityVampireBat entityLiving, float ageInTicks, float rotationYaw, float partialTicks)
    {
        if (entityLiving.getIsBatHanging())
        {
            GlStateManager.translate(0.0F, -0.1F, 0.0F);
        }
        else
        {
            GlStateManager.translate(0.0F, MathHelper.cos(ageInTicks * 0.3F) * 0.1F, 0.0F);
        }
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityVampireBat entity) {
        return TEXTURE;
    }
}