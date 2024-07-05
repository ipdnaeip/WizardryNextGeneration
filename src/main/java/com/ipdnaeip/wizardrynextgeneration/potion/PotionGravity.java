package com.ipdnaeip.wizardrynextgeneration.potion;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.potion.PotionMagicEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionGravity extends PotionMagicEffect {

    public PotionGravity() {
        super(true, 0x55FF55, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/gravity.png"));
    }

    @SubscribeEvent
    public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity instanceof EntityPlayer) {
            EntityPlayer entityPlayer = (EntityPlayer) event.getEntityLiving();
            if (entity.isPotionActive(WNGPotions.GRAVITY) && !entityPlayer.capabilities.isFlying) {
                entity.motionY -= 0.025 * (entity.getActivePotionEffect(WNGPotions.GRAVITY).getAmplifier() + 1);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingFallEvent(LivingFallEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.isPotionActive(WNGPotions.GRAVITY)) {
            event.setDamageMultiplier(entity.getActivePotionEffect(WNGPotions.GRAVITY).getAmplifier() + 2);
        }
    }

}
