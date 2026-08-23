package com.ipdnaeip.wizardrynextgeneration.mixin.minecraft;

import com.ipdnaeip.wizardrynextgeneration.accessor.AccessorEntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer {

	//Indicates healing is from natural sources
	@Inject(method = "onLivingUpdate()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;heal(F)V", ordinal = 0))
	private void onLivingUpdate(CallbackInfo info) {
		((AccessorEntityLivingBase)this).wizardrynextgeneration$setNaturalHeal(true);
	}

}
