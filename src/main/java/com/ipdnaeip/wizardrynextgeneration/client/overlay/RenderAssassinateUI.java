//package com.ipdnaeip.wizardrynextgeneration.client.overlay;
//
//import com.ipdnaeip.wizardrynextgeneration.registry.WNGSpells;
//import electroblob.wizardry.data.WizardData;
//import electroblob.wizardry.item.ISpellCastingItem;
//import electroblob.wizardry.spell.Transportation;
//import electroblob.wizardry.util.GeometryUtils;
//import electroblob.wizardry.util.Location;
//import java.util.Iterator;
//import java.util.List;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.gui.FontRenderer;
//import net.minecraft.client.renderer.BufferBuilder;
//import net.minecraft.client.renderer.GlStateManager;
//import net.minecraft.client.renderer.OpenGlHelper;
//import net.minecraft.client.renderer.Tessellator;
//import net.minecraft.client.renderer.GlStateManager.DestFactor;
//import net.minecraft.client.renderer.GlStateManager.SourceFactor;
//import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.ResourceLocation;
//import net.minecraft.util.math.Vec3d;
//import net.minecraftforge.client.event.RenderWorldLastEvent;
//import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
//import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//import net.minecraftforge.fml.relauncher.Side;
//
//@EventBusSubscriber({Side.CLIENT})
//public class RenderAssassinateUI {
//    private static final ResourceLocation TEXTURE = new ResourceLocation("ebwizardry", "textures/gui/transportation_marker.png");
//
//    public RenderAssassinateUI() {
//    }
//
//    @SubscribeEvent
//    public static void onRenderWorldLastEvent(RenderWorldLastEvent event) {
//        if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 0) {
//            EntityPlayer player = Minecraft.getMinecraft().player;
//            ItemStack stack = player.getHeldItemMainhand();
//            if (!(stack.getItem() instanceof ISpellCastingItem)) {
//                stack = player.getHeldItemOffhand();
//                if (!(stack.getItem() instanceof ISpellCastingItem)) {
//                    return;
//                }
//            }
//            if (((ISpellCastingItem)stack.getItem()).getCurrentSpell(stack) == WNGSpells.assassinate) {
//                GlStateManager.pushMatrix();
//                Vec3d origin = player.getPositionEyes(event.getPartialTicks());
//                GlStateManager.translate(0.0, origin.y - Minecraft.getMinecraft().getRenderManager().viewerPosY, 0.0);
//                Tessellator tessellator = Tessellator.getInstance();
//                BufferBuilder buffer = tessellator.getBuffer();
//                GlStateManager.pushMatrix();
//                GlStateManager.enableBlend();
//                GlStateManager.disableLighting();
//                GlStateManager.disableDepth();
//                OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
//                GlStateManager.blendFunc(770, 771);
//                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
//                Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);
//                buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
//                Vec3d position = GeometryUtils.getCentre(target.pos).subtract(origin);
//                double distance = position.length();
//                double distanceCap = (double)(Minecraft.getMinecraft().gameSettings.renderDistanceChunks * 16 - 8);
//                double displayDist = Math.min(distance, distanceCap);
//                double factor = displayDist / distance;
//                GlStateManager.translate(position.x * factor, position.y * factor, position.z * factor);
//                GlStateManager.rotate(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
//                GlStateManager.rotate(Minecraft.getMinecraft().getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
//                double angle = Transportation.getLookDeviationAngle(player, location.pos, event.getPartialTicks());
//                double iconSize = Transportation.getIconSize(distance);
//                double proximityFactor = Math.max(0.0, Math.pow(1.0 - angle / iconSize * angle / iconSize, 3.0));
//                iconSize *= 1.0 + 0.3 * proximityFactor;
//                iconSize *= displayDist;
//                float f = location == target ? 1.0F : 0.5F;
//                buffer.pos(-iconSize, iconSize, 0.0).tex(0.0, 0.0).color(f, 1.0F, f, f).endVertex();
//                buffer.pos(iconSize, iconSize, 0.0).tex(1.0, 0.0).color(f, 1.0F, f, f).endVertex();
//                buffer.pos(iconSize, -iconSize, 0.0).tex(1.0, 1.0).color(f, 1.0F, f, f).endVertex();
//                buffer.pos(-iconSize, -iconSize, 0.0).tex(0.0, 1.0).color(f, 1.0F, f, f).endVertex();
//                tessellator.draw();
//                GlStateManager.popMatrix();
//                if (location == target) {
//                    String label = location.pos.getX() + ", " + location.pos.getY() + ", " + location.pos.getZ();
//                    float var10002 = (float)(position.x * factor);
//                    float var10003 = (float)(position.y * factor + iconSize * 1.5);
//                    float var10004 = (float)(position.z * factor);
//                    drawLabel(Minecraft.getMinecraft().fontRenderer, label, var10002, var10003, var10004, (float)displayDist * 0.2F, 0, Minecraft.getMinecraft().getRenderManager().playerViewY, Minecraft.getMinecraft().getRenderManager().playerViewX);
//                }
//
//                GlStateManager.disableBlend();
//                GlStateManager.enableTexture2D();
//                GlStateManager.enableLighting();
//                GlStateManager.enableDepth();
//                GlStateManager.disableRescaleNormal();
//                GlStateManager.popMatrix();
//            }
//
//        }
//    }
//
//    private static void drawLabel(FontRenderer fontRendererIn, String str, float x, float y, float z, float scale, int verticalShift, float viewerYaw, float viewerPitch) {
//        GlStateManager.pushMatrix();
//        GlStateManager.translate(x, y, z);
//        GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
//        GlStateManager.rotate(-viewerYaw, 0.0F, 1.0F, 0.0F);
//        GlStateManager.rotate(viewerPitch, 1.0F, 0.0F, 0.0F);
//        GlStateManager.scale(-0.025F, -0.025F, 0.025F);
//        GlStateManager.scale(scale, scale, scale);
//        GlStateManager.disableLighting();
//        GlStateManager.depthMask(false);
//        GlStateManager.disableDepth();
//        GlStateManager.enableBlend();
//        GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
//        int i = fontRendererIn.getStringWidth(str) / 2;
//        GlStateManager.disableTexture2D();
//        Tessellator tessellator = Tessellator.getInstance();
//        BufferBuilder bufferbuilder = tessellator.getBuffer();
//        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
//        bufferbuilder.pos((double)(-i - 1), (double)(-1 + verticalShift), 0.0).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
//        bufferbuilder.pos((double)(-i - 1), (double)(8 + verticalShift), 0.0).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
//        bufferbuilder.pos((double)(i + 1), (double)(8 + verticalShift), 0.0).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
//        bufferbuilder.pos((double)(i + 1), (double)(-1 + verticalShift), 0.0).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
//        tessellator.draw();
//        GlStateManager.enableTexture2D();
//        fontRendererIn.drawString(str, -fontRendererIn.getStringWidth(str) / 2, verticalShift, 8847205);
//        GlStateManager.enableDepth();
//        GlStateManager.depthMask(true);
//        fontRendererIn.drawString(str, -fontRendererIn.getStringWidth(str) / 2, verticalShift, 8847205);
//        GlStateManager.enableLighting();
//        GlStateManager.disableBlend();
//        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
//        GlStateManager.popMatrix();
//    }
//}
//
