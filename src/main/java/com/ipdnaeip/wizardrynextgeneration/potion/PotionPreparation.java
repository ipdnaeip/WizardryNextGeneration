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
        this.setPotionName("potion." + WizardryNextGeneration.MODID + ":preparation");
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @SubscribeEvent
    public static void onSpellCastPreEvent(SpellCastEvent.Pre event) {
        if (event.getCaster() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getCaster();
            SpellModifiers modifiers = event.getModifiers();
            if (player.isPotionActive(WNGPotions.preparation)) {
                float multiplier = (float)(1 / (player.getActivePotionEffect(WNGPotions.preparation).getAmplifier() + 2));
                modifiers.set("cost", multiplier, false);
                modifiers.set("chargeup", multiplier, false);
            }
        }
    }

    @SubscribeEvent
    public static void onSpellCastPostEvent(SpellCastEvent.Post event) {
        if (event.getCaster() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getCaster();
            if (event.getSpell() != WNGSpells.preparation) {
                player.removePotionEffect(WNGPotions.preparation);
            }
        }
    }

}
