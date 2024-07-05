package com.ipdnaeip.wizardrynextgeneration.potion;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import com.ipdnaeip.wizardrynextgeneration.util.WNGUtils;
import electroblob.wizardry.event.SpellCastEvent;
import electroblob.wizardry.potion.PotionMagicEffect;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionDisempowerment extends PotionMagicEffect {

    public static final float POTENCY_DECREASE = 0.15f;
    public static final int MAX_LEVEL = 7;

    public PotionDisempowerment() {
        super(true, 0x0f000f, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/disempowerment.png"));
    }

    //code by WinDanesz, modified for use
    @SubscribeEvent(priority = EventPriority.HIGHEST) // processing after electroblob.wizardry.item.ItemArtefact.onSpellCastPreEvent (EventPriority.LOW)
    public static void onSpellCastPreEvent(SpellCastEvent.Pre event) {
        EntityLivingBase entity = event.getCaster();
        if (entity != null && entity.isPotionActive(WNGPotions.DISEMPOWERMENT)) {
            int level = entity.getActivePotionEffect(WNGPotions.DISEMPOWERMENT).getAmplifier() + 1;
            SpellModifiers modifiers = event.getModifiers();
            float potency = modifiers.get(SpellModifiers.POTENCY);
            if (level >= MAX_LEVEL) {
                WNGUtils.sendMessage(entity, "potion.wizardrynextgeneration:disempowerment.failed_cast", true);
                event.setCanceled(true);
            } else {
                modifiers.set(SpellModifiers.POTENCY, potency - (potency * (level * POTENCY_DECREASE)), false);
            }
        }
    }
}
