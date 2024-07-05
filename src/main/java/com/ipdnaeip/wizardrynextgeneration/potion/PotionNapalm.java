package com.ipdnaeip.wizardrynextgeneration.potion;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.potion.PotionMagicEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionNapalm extends PotionMagicEffect {

    public PotionNapalm() {
        super(true, 0xD86631, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/napalm.png"));
    }

    @SubscribeEvent
    public static void onLivingHurtEvent(LivingHurtEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.isPotionActive(WNGPotions.NAPALM) && event.getSource().isFireDamage()) {
            entity.setFire(5 * entity.getActivePotionEffect(WNGPotions.NAPALM).getAmplifier() + 1);
            entity.removePotionEffect(WNGPotions.NAPALM);
        }
    }
}
