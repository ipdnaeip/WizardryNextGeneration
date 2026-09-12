package com.ipdnaeip.wizardrynextgeneration.mixin.minecraft;

import baubles.api.BaublesApi;
import com.ipdnaeip.wizardrynextgeneration.accessor.AccessorEntityLivingBase;
import com.ipdnaeip.wizardrynextgeneration.potion.PotionRally;
import com.ipdnaeip.wizardrynextgeneration.potion.PotionTaunt;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import com.llamalad7.mixinextras.sugar.Local;
import electroblob.wizardry.item.ItemArtefact;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase implements AccessorEntityLivingBase {

	@Unique
	Vec3d wizardrynextgeneration$target;

	//Indicates if healing is from natural sources; used for determining if the bleed effect should be removed by healing
	@Unique
	boolean wizardrynextgeneration$isNaturalHeal;

	@Override
	public Vec3d wizardrynextgeneration$getTarget() {
		return this.wizardrynextgeneration$target;
	}

	@Override
	public void wizardrynextgeneration$setTarget(Vec3d target) {
		this.wizardrynextgeneration$target = target;
	}

	@Override
	public boolean wizardrynextgeneration$isNaturalHeal() {
		return this.wizardrynextgeneration$isNaturalHeal;
	}

	@Override
	public void wizardrynextgeneration$setNaturalHeal(boolean isNaturalHeal) {
		this.wizardrynextgeneration$isNaturalHeal = isNaturalHeal;
	}

	@Inject(method = "Lnet/minecraft/entity/EntityLivingBase;addPotionEffect(Lnet/minecraft/potion/PotionEffect;)V", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fml/common/eventhandler/EventBus;post(Lnet/minecraftforge/fml/common/eventhandler/Event;)Z", shift = At.Shift.AFTER), remap = false)
	private void addPotionEffect(PotionEffect potionEffect, CallbackInfo info, @Local PotionEffect oldPotionEffect) {
		EntityLivingBase entityLivingBase = (EntityLivingBase)(Object)this;
		//Prevents the number from desyncing on the client
		if (!entityLivingBase.world.isRemote) {
			//oldPotionEffect can be null
			if (potionEffect.getPotion() == WNGPotions.BLEED && oldPotionEffect != null && oldPotionEffect.getPotion() == WNGPotions.BLEED) {
				((AccessorPotionEffect)potionEffect).wizardrynextgeneration$setAmplifier(potionEffect.getAmplifier() + oldPotionEffect.getAmplifier() + 1);
			}
			if (entityLivingBase instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) entityLivingBase;
				List<ItemArtefact> artefacts = ItemArtefact.getActiveArtefacts(player);
				for (ItemArtefact artefact : artefacts) {
					if (artefact == WNGItems.HEAD_RALLY) {
						if (potionEffect.getPotion() == WNGPotions.RALLY) {
							((AccessorPotionEffect)potionEffect).wizardrynextgeneration$setAmplifier(potionEffect.getAmplifier() + PotionRally.HEAD_RALLY_INCREASE);
						}
					}
					if (artefact == WNGItems.BELT_POTION) {
						if (!potionEffect.getPotion().isBadEffect()) {
							if (potionEffect != null) {
								ItemStack belt = BaublesApi.getBaublesHandler(player).getStackInSlot(3);
								int effectCost = (int)(potionEffect.getDuration() / 20f * (0.5f * potionEffect.getAmplifier() + 1f));
								int damage = Math.min(effectCost, belt.getMaxDamage() - belt.getItemDamage());
								//Increases potion effects by up to 50% depending on item durability
								int durationIncrease = (int)((float)damage / effectCost * 0.5f * potionEffect.getDuration());
								belt.setItemDamage(belt.getItemDamage() + damage);
								((AccessorPotionEffect)potionEffect).wizardrynextgeneration$setDuration(potionEffect.getDuration() + durationIncrease);
							}
						}
					}
				}
			}
		}
	}

	@Inject(method = "addPotionEffect(Lnet/minecraft/potion/PotionEffect;)V", at = @At(value = "RETURN"))
	private void addPotionEffect2(PotionEffect potionEffect, CallbackInfo info) {
		EntityLivingBase entityLivingBase = (EntityLivingBase)(Object)this;
		if (entityLivingBase.isPotionApplicable(potionEffect) && potionEffect.getPotion() == WNGPotions.TAUNT) {
			//Need this so that the effect uses the greater of the two amplifiers if the effect is reapplied
			PotionEffect newPotionEffect = entityLivingBase.getActivePotionEffect(WNGPotions.TAUNT);
			if (newPotionEffect != null) {
				//When taunt is applied or reapplied, the entity taunts targets around it, using the new amplifier
				PotionTaunt.tauntEnemies(entityLivingBase, PotionTaunt.getRadiusForAmplifier(newPotionEffect.getAmplifier()), true);
			}
		}
	}

}
