package com.ipdnaeip.wizardrynextgeneration.potion;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.potion.PotionMagicEffect;
import electroblob.wizardry.registry.WizardryPotions;
import electroblob.wizardry.util.MagicDamage;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionMagicWeakness extends PotionMagicEffect {

    public PotionMagicWeakness() {
        super(true, 0x0f000f, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/magic_weakness.png"));
        this.setPotionName("potion." + WizardryNextGeneration.MODID + ":magic_weakness");
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onLivingHurtEvent(LivingHurtEvent event) {
        float increase = 0.15F;

        if (event.getEntityLiving().isPotionActive(WNGPotions.magic_weakness) && event.getSource().isMagicDamage()) {
            int multiplier = event.getEntityLiving().getActivePotionEffect(WNGPotions.magic_weakness).getAmplifier() + 1;
                event.setAmount(event.getAmount() * (1 + (increase * multiplier)));
        }
    }

}
