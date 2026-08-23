package com.ipdnaeip.wizardrynextgeneration.mixin.minecraft;

import net.minecraft.potion.PotionEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PotionEffect.class)
public interface AccessorPotionEffect {

	@Accessor("amplifier")
	void wizardrynextgeneration$setAmplifier(int amplifier);

	@Accessor("duration")
	void wizardrynextgeneration$setDuration(int duration);

}
