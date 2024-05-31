package com.ipdnaeip.wizardrynextgeneration.potion;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.item.ItemArtefact;
import electroblob.wizardry.potion.PotionMagicEffect;
import electroblob.wizardry.spell.SpellBuff;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class PotionRally extends PotionMagicEffect {

    public static final float damage_increase = 0.1f;
    public static final float damage_decrease = 0.2f;
    public static final int head_rally_increase = 1;

    public PotionRally() {
        super(true, 0x0f000f, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/rally.png"));
        this.setPotionName("potion." + WizardryNextGeneration.MODID + ":rally");
    }

    @SubscribeEvent
    public static void onLivingHurtEvent(LivingHurtEvent event) {
        //Damage increases at a decreasing rate to a maximum of 2x damage
        if (event.getSource().getTrueSource() instanceof EntityLivingBase && ((EntityLivingBase)event.getSource().getTrueSource()).isPotionActive(WNGPotions.rally)) {
            event.setAmount(event.getAmount() * (2f - (float)Math.pow(1 - damage_increase, ((EntityLivingBase) event.getSource().getTrueSource()).getActivePotionEffect(WNGPotions.rally).getAmplifier() + 1)));
        }
        //Damage decreases at a decreasing rate to a minimum of 0.5x damage
        if (event.getEntityLiving().isPotionActive(WNGPotions.rally) && event.getSource() != DamageSource.OUT_OF_WORLD) {
            event.setAmount(event.getAmount() * ((1f + (float)Math.pow(1 - damage_decrease, event.getEntityLiving().getActivePotionEffect(WNGPotions.rally).getAmplifier() + 1)) / 2f));
        }
    }

}
