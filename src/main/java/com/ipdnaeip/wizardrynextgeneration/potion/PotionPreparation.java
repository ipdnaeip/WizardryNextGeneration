package com.ipdnaeip.wizardrynextgeneration.potion;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGSpells;
import electroblob.wizardry.event.SpellCastEvent;
import electroblob.wizardry.potion.PotionMagicEffect;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionPreparation extends PotionMagicEffect {

    public PotionPreparation() {
        super(false, 0xBEBE82, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/preparation.png"));
    }

    @SubscribeEvent
    public static void onSpellCastPreEvent(SpellCastEvent.Pre event) {
        if (event.getCaster() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getCaster();
            if (player.isPotionActive(WNGPotions.PREPARATION)) {
                SpellModifiers modifiers = event.getModifiers();
                float multiplier = (2f / (player.getActivePotionEffect(WNGPotions.PREPARATION).getAmplifier() + 3f));
                modifiers.set(SpellModifiers.COST, multiplier, false);
                modifiers.set(SpellModifiers.CHARGEUP, multiplier, false);
            }
        }
    }
    @SubscribeEvent
    public static void onSpellCastPostEvent(SpellCastEvent.Post event) {
        if (event.getCaster() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getCaster();
            if (player.isPotionActive(WNGPotions.PREPARATION) && event.getSpell() != WNGSpells.PREPARATION) {
                player.removePotionEffect(WNGPotions.PREPARATION);
            }
        }
    }

}
