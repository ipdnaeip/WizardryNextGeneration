package com.ipdnaeip.wizardrynextgeneration.client.renderer.entity.layers;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.client.renderer.entity.living.RenderVampireBat;
import com.ipdnaeip.wizardrynextgeneration.entity.living.EntityVampireBat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class LayerVampireBatEyes<T extends EntityVampireBat> implements LayerRenderer<T> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(WizardryNextGeneration.MODID, "textures/entity/vampire_bat_eyes.png");

    private final RenderVampireBat mainRenderer;

    public LayerVampireBatEyes(RenderVampireBat renderer){
        this.mainRenderer = renderer;
    }

    @Override
    public void doRenderLayer(T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale){

        // Copied from LayerDruidMageEyes

        this.mainRenderer.bindTexture(TEXTURE);

        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.depthMask(!entity.isInvisible());

        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        Minecraft.getMinecraft().entityRenderer.setupFogColor(true);

        this.mainRenderer.getMainModel().render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

        Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
        int i = entity.getBrightnessForRender();
        int j = i % 65536;
        int k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);

        this.mainRenderer.setLightmap(entity);

        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
    }

    @Override
    public boolean shouldCombineTextures(){
        return false;
    }
}
