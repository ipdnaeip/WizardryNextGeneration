package com.ipdnaeip.wizardrynextgeneration.client.renderer.entity.construct;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.entity.construct.EntityNapalm;
import electroblob.wizardry.client.DrawingUtils;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class RenderNapalm extends Render<EntityNapalm> {
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[10];

    public RenderNapalm(RenderManager renderManager) {
        super(renderManager);
        for(int i = 0; i < 10; ++i) {
            TEXTURES[i] = new ResourceLocation(WizardryNextGeneration.MODID, "textures/entity/napalm/napalm_" + i + ".png");
        }
    }

    @Override
    public void doRender(EntityNapalm entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableLighting();
        //OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
        GlStateManager.blendFunc(770, 771);
        float yOffset = 0.0F;
        GlStateManager.translate((float)x, (float)y + yOffset, (float)z);
        this.bindTexture(TEXTURES[entity.textureIndex]);
        float f6 = 1.0F;
        float f7 = 0.5F;
        float f8 = 0.5F;
        GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
        float s = entity.width * DrawingUtils.smoothScaleFactor(entity.lifetime, entity.ticksExisted, partialTicks, 10, 50);
        GlStateManager.scale(s, s, s);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(0.0F - f7, 0.0F - f8, 0.01).tex(0.0, 1.0).endVertex();
        buffer.pos(f6 - f7, 0.0F - f8, 0.01).tex(1.0, 1.0).endVertex();
        buffer.pos(f6 - f7, 1.0F - f8, 0.01).tex(1.0, 0.0).endVertex();
        buffer.pos(0.0F - f7, 1.0F - f8, 0.01).tex(0.0, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
    }

    protected ResourceLocation getEntityTexture(EntityNapalm entity) {
        return null;
    }
}
