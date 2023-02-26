package com.ipdnaeip.wizardrynextgeneration.client.renderer.entity.layers;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.client.renderer.entity.layers.LayerTiledOverlay;
import electroblob.wizardry.registry.WizardryPotions;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class LayerSolarSentinel extends LayerTiledOverlay<EntityLivingBase> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(WizardryNextGeneration.MODID, "textures/entity/solar_sentinel_overlay.png");

    public LayerSolarSentinel(RenderLivingBase<?> renderer) {
            super(renderer);
        }

    public boolean shouldRender(EntityLivingBase entity, float partialTicks) {
        return !entity.isInvisible() && entity.isPotionActive(WNGPotions.solar_winds) && entity.isPotionActive(WNGPotions.cleansing_flames);
    }

    public ResourceLocation getTexture(EntityLivingBase entity, float partialTicks) {
            return TEXTURE;
        }

    public void doRenderLayer(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        super.doRenderLayer(entity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
        GlStateManager.disableBlend();
    }

    protected void applyTextureSpaceTransformations(EntityLivingBase entity, float partialTicks) {
        float f = (float)entity.ticksExisted + partialTicks;
        GlStateManager.translate(0.0F, f * 0.01F, 0.0F);
    }

}


