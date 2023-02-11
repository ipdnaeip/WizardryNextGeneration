package com.ipdnaeip.wizardrynextgeneration.potion;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.event.SpellCastEvent;
import electroblob.wizardry.potion.PotionMagicEffect;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionDisempowerment extends PotionMagicEffect {

    public PotionDisempowerment() {
        super(true, 0x0f000f, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/disempowerment.png"));
        this.setPotionName("potion." + WizardryNextGeneration.MODID + ":disempowerment");
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    //code by WinDanesz, modified for use
    @SubscribeEvent(priority = EventPriority.HIGHEST) // processing after electroblob.wizardry.item.ItemArtefact.onSpellCastPreEvent (EventPriority.LOW)
    public static void onSpellCastPreEvent(SpellCastEvent.Pre event) {
        float decrease = 0.15f;
        int max_level = 7;

        if (event.getCaster() != null && event.getCaster().isPotionActive(WNGPotions.disempowerment)) {
            int level = event.getCaster().getActivePotionEffect(WNGPotions.disempowerment).getAmplifier() + 1;

            SpellModifiers modifiers = event.getModifiers();
            float potency = modifiers.get(SpellModifiers.POTENCY);
            if (level >= max_level) {
                if (event.getCaster() instanceof EntityPlayer && !event.getCaster().getEntityWorld().isRemote)
                    ((EntityPlayer) event.getCaster()).sendStatusMessage(new TextComponentTranslation("potion.wizardrynextgeneration:disempowerment.failed_cast"), true);
                event.setCanceled(true);
            } else {
                modifiers.set(SpellModifiers.POTENCY, potency - (potency * (level * decrease)), false);
            }
        }
    }
}
