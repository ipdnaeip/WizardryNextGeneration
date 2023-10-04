package com.ipdnaeip.wizardrynextgeneration.client.renderer.entity.living;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.client.model.ModelBatMob;
import com.ipdnaeip.wizardrynextgeneration.entity.living.EntityBatMob;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;


public class RenderBatMob extends RenderLiving<EntityBatMob> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(WizardryNextGeneration.MODID, "textures/entity/bat_mob.png");

    public RenderBatMob(RenderManager manager) {
        super(manager, new ModelBatMob(), 0.25F);
    }

    protected void preRenderCallback(EntityBatMob entitylivingbaseIn, float partialTickTime)
    {
        GlStateManager.scale(0.35F, 0.35F, 0.35F);
    }

    protected void applyRotations(EntityBatMob entityLiving, float ageInTicks, float rotationYaw, float partialTicks)
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
    protected ResourceLocation getEntityTexture(EntityBatMob entity) {
        return TEXTURE;
    }
}