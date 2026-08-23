package com.ipdnaeip.wizardrynextgeneration.mixin.minecraft;

import com.ipdnaeip.wizardrynextgeneration.accessor.AccessorEntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.FoodStats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodStats.class)
public class MixinFoodStats {

	//Indicates healing is from natural sources
	@Inject(method = "onUpdate(Lnet/minecraft/entity/player/EntityPlayer;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;heal(F)V"))
	private void onUpdate(EntityPlayer player, CallbackInfo info) {
		((AccessorEntityLivingBase)player).wizardrynextgeneration$setNaturalHeal(true);
	}

}
