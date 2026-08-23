package com.ipdnaeip.wizardrynextgeneration.mixin.minecraft;

import com.ipdnaeip.wizardrynextgeneration.accessor.AccessorTileEntityDispenser;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(TileEntityDispenser.class)
public class MixinTileEntityDispenser implements AccessorTileEntityDispenser {

	@Unique
	Vec3d wizardrynextgeneration$target;

	@Override
	public Vec3d wizardrynextgeneration$getTarget() {
		return this.wizardrynextgeneration$target;
	}

	@Override
	public void wizardrynextgeneration$setTarget(Vec3d target) {
		this.wizardrynextgeneration$target = target;
	}
}
