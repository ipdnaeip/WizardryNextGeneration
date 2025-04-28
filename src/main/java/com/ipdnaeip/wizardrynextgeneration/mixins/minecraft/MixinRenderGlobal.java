package com.ipdnaeip.wizardrynextgeneration.mixins.minecraft;

import electroblob.wizardry.util.EntityUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.Entity;
import net.minecraft.init.MobEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderGlobal.class)
public abstract class MixinRenderGlobal {

    @Inject(method = "Lnet/minecraft/client/renderer/RenderGlobal;isOutlineActive(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/Entity;Lnet/minecraft/client/renderer/culling/ICamera;)Z", at = @At("HEAD"), cancellable = true)
    public void injectAura(Entity entity, Entity viewer, ICamera camera, CallbackInfoReturnable info) {
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        if (player.isPotionActive(MobEffects.RESISTANCE) && entity != player && EntityUtils.isLiving(entity)) {
            if (player.getDistance(entity) <= 20 + player.getActivePotionEffect(MobEffects.RESISTANCE).getAmplifier() * 10) {
                info.setReturnValue(true);
            }
        }
    }

}
