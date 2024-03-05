package com.ipdnaeip.wizardrynextgeneration.potion;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.potion.PotionMagicEffect;
import electroblob.wizardry.util.IElementalDamage;
import electroblob.wizardry.util.MagicDamage;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionShockWeakness extends PotionMagicEffect {

    public PotionShockWeakness() {
        super(true, 0x00AAAA, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/shock_weakness.png"));
        this.setPotionName("potion." + WizardryNextGeneration.MODID + ":shock_weakness");
    }

    @SubscribeEvent
    public static void onLivingHurtEvent(LivingHurtEvent event) {
        float increase = 0.15F;
        if (event.getEntityLiving().isPotionActive(WNGPotions.shock_weakness) && event.getSource() instanceof IElementalDamage && ((IElementalDamage) event.getSource()).getType() == MagicDamage.DamageType.SHOCK) {
            int multiplier = event.getEntityLiving().getActivePotionEffect(WNGPotions.shock_weakness).getAmplifier() + 1;
            event.setAmount(event.getAmount() * (1 + (increase * multiplier)));
        }
    }

}
