package com.ipdnaeip.wizardrynextgeneration.mixins.minecraft;

import com.ipdnaeip.wizardrynextgeneration.mixininterfaces.minecraft.IMixinEntityArrow;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityArrow.class)
public abstract class MixinEntityArrow implements IMixinEntityArrow {

    @Shadow
    @Final
    protected abstract ItemStack getArrowStack();

    public ItemStack wizardryNextGeneration$getArrowStack() {
        return this.getArrowStack();
    }

}
