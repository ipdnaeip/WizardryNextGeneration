package com.ipdnaeip.wizardrynextgeneration.mixins.minecraft;

import baubles.api.BaublesApi;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import electroblob.wizardry.item.ItemArtefact;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PotionEffect.class)
public abstract class MixinPotionEffect {

    @Shadow @Final private Potion potion;

    @Shadow private int duration;

    @Shadow private int amplifier;

    @Inject(method = "onUpdate", at = @At("HEAD"))
    private void onUpdate(EntityLivingBase entityLivingBase, CallbackInfoReturnable<Boolean> info) {
        if (entityLivingBase instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entityLivingBase;
            if (ItemArtefact.isArtefactActive(player, WNGItems.belt_potion)) {
                if (!this.potion.isBadEffect()) {
                    ItemStack belt = BaublesApi.getBaublesHandler(player).getStackInSlot(3);
                    if (belt.getMaxDamage() - belt.getItemDamage() >= this.amplifier + 1) {
                        this.duration += 1;
                        if (player.ticksExisted % 20 == 0) {
                            belt.damageItem(this.amplifier + 1, player);
                        }
                    }
                }
            }
        }
    }
}
