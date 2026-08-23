package com.ipdnaeip.wizardrynextgeneration.mixin.minecraft;

import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EntityArrow.class)
public interface AccessorEntityArrow {

    @Invoker("getArrowStack")
    ItemStack wizardrynextgeneration$getArrowStack();
}
