package com.ipdnaeip.wizardrynextgeneration.mixins.ebwizardry;

import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import electroblob.wizardry.entity.construct.EntityHailstorm;
import electroblob.wizardry.entity.construct.EntityScaledConstruct;
import electroblob.wizardry.entity.projectile.EntityIceLance;
import electroblob.wizardry.item.ItemArtefact;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityHailstorm.class)
public abstract class MixinEntityHailstorm extends EntityScaledConstruct {

    public MixinEntityHailstorm(World world) {
        super(world);
    }

    @Inject(method = "onUpdate", at = @At("HEAD"), cancellable = true)
    private void onUpdate(CallbackInfo info) {
        EntityHailstorm thisEntityHailstorm = (EntityHailstorm)(Object)this;
        if (thisEntityHailstorm.getCaster() instanceof EntityPlayer && ItemArtefact.isArtefactActive((EntityPlayer) thisEntityHailstorm.getCaster(), WNGItems.CHARM_ICICLE)) {
            info.cancel();
            super.onUpdate();
            if (!thisEntityHailstorm.world.isRemote) {
                double x = thisEntityHailstorm.posX + (thisEntityHailstorm.world.rand.nextDouble() - 0.5) * (double) thisEntityHailstorm.width;
                double y = thisEntityHailstorm.posY + thisEntityHailstorm.world.rand.nextDouble() * (double) thisEntityHailstorm.height;
                double z = thisEntityHailstorm.posZ + (thisEntityHailstorm.world.rand.nextDouble() - 0.5) * (double) thisEntityHailstorm.width;
                EntityIceLance iceLance = new EntityIceLance(thisEntityHailstorm.world);
                iceLance.setPosition(x, y, z);
                iceLance.motionX = MathHelper.cos((float) Math.toRadians(thisEntityHailstorm.rotationYaw + 90.0F));
                iceLance.motionY = -0.6;
                iceLance.motionZ = MathHelper.sin((float) Math.toRadians(thisEntityHailstorm.rotationYaw + 90.0F));
                iceLance.setCaster(thisEntityHailstorm.getCaster());
                iceLance.damageMultiplier = thisEntityHailstorm.damageMultiplier;
                thisEntityHailstorm.world.spawnEntity(iceLance);
            }
        }
    }

}
