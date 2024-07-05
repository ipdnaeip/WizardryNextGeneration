package com.ipdnaeip.wizardrynextgeneration.potion;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.event.SpellCastEvent;
import electroblob.wizardry.potion.PotionMagicEffect;
import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionFocus extends PotionMagicEffect {

    public static final float cost_increase = 0.5f;
    public static final float cooldown_reduction = 0.5f;
    
    public PotionFocus() {
        super(false, 0xBEBE82, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/focus.png"));
    }

    @SubscribeEvent
    public static void onSpellCastPreEvent(SpellCastEvent.Pre event) {
        if (event.getCaster() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getCaster();
            if (player.isPotionActive(WNGPotions.FOCUS)) {
                SpellModifiers modifiers = event.getModifiers();
                float multiplier = ((1f + (float)Math.pow(1 - cooldown_reduction, player.getActivePotionEffect(WNGPotions.FOCUS).getAmplifier() + 1)) / 2f);
                modifiers.set(WizardryItems.cooldown_upgrade, multiplier, false);
                multiplier = (2f - (float)Math.pow(1 - cost_increase, player.getActivePotionEffect(WNGPotions.FOCUS).getAmplifier() + 1));
                modifiers.set(SpellModifiers.COST, multiplier, false);
            }
        }
    }

}
