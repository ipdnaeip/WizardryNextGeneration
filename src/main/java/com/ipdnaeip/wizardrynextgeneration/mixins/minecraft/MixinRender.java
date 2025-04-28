package com.ipdnaeip.wizardrynextgeneration.mixins.minecraft;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Render.class)
public class MixinRender {

/*    @Inject(method = "Lnet/minecraft/client/renderer/entity/Render;renderEntityOnFire(Lnet/minecraft/entity/Entity;DDDF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;pushMatrix()V"))
    public void injectFire(Entity entity, double x, double y, double z, float partialTicks, CallbackInfo info, TextureMap texturemap, TextureAtlasSprite textureatlassprite, TextureAtlasSprite textureatlassprite1) {
        textureatlassprite = texturemap.getAtlasSprite("wizardrynextgeneration:blocks/magic_fire_layer_0");
        textureatlassprite1 = texturemap.getAtlasSprite("wizardrynextgeneration:blocks/magic_fire_layer_1");
    }*/

/*    @ModifyVariable(method = "Lnet/minecraft/client/renderer/entity/Render;renderEntityOnFire(Lnet/minecraft/entity/Entity;DDDF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;pushMatrix()V"))
    public TextureAtlasSprite injectFireTexture(TextureAtlasSprite textureatlassprite) {
        return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("wizardrynextgeneration:blocks/magic_fire_layer_0");
    }

    @ModifyVariable(method = "Lnet/minecraft/client/renderer/entity/Render;renderEntityOnFire(Lnet/minecraft/entity/Entity;DDDF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;pushMatrix()V"))
    public TextureAtlasSprite injectFireTexture1(TextureAtlasSprite textureatlassprite) {
        return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("wizardrynextgeneration:blocks/magic_fire_layer_1");
    }*/

/*
    @Inject(method = "Lnet/minecraft/client/renderer/entity/Render;renderEntityOnFire(Lnet/minecraft/entity/Entity;DDDF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;color(FFFF)V"))
    public void injectFire(Entity entity, double x, double y, double z, float partialTicks, CallbackInfo info) {
    }

    @ModifyArgs(method = "Lnet/minecraft/client/renderer/entity/Render;renderEntityOnFire(Lnet/minecraft/entity/Entity;DDDF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;color(FFFF)V"))
    public void modifyFireColor(Args args) {
        args.set(0, 0f);
        args.set(1, 0.f);
        args.set(2, 0.5f);
    }
*/

}
