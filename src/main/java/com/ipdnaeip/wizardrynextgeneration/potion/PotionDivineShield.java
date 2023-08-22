package com.ipdnaeip.wizardrynextgeneration.potion;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGSpells;
import electroblob.wizardry.potion.ICustomPotionParticles;
import electroblob.wizardry.potion.PotionMagicEffect;
import electroblob.wizardry.registry.WizardryPotions;
import electroblob.wizardry.registry.WizardrySounds;
import electroblob.wizardry.util.EntityUtils;
import electroblob.wizardry.util.ParticleBuilder;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionDivineShield extends PotionMagicEffect {

    public PotionDivineShield() {
        super(false, 0xFFD651, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/divine_shield.png"));
        this.setPotionName("potion." + WizardryNextGeneration.MODID + ":divine_shield");
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onLivingHurtEvent(LivingHurtEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.isPotionActive(WNGPotions.divine_shield)) {
            event.setCanceled(true);
            entity.removePotionEffect(WNGPotions.divine_shield);
            entity.world.playSound(null, entity.posX, entity.posY, entity.posZ, SoundEvents.ITEM_SHIELD_BLOCK, SoundCategory.PLAYERS, 1.25F, 1.0F);
            entity.world.playSound(null, entity.posX, entity.posY, entity.posZ, WizardrySounds.ENTITY_SHIELD_DEFLECT, SoundCategory.PLAYERS, 1.25F, 1.0F);
/*            EntityUtils.playSoundAtPlayer((EntityPlayer)event.getEntityLiving(), SoundEvents.ITEM_SHIELD_BLOCK, 1.25F, 1.0F);
            EntityUtils.playSoundAtPlayer((EntityPlayer)event.getEntityLiving(), WizardrySounds.ENTITY_SHIELD_DEFLECT, 1.25F, 1.0F);*/
        }
    }

}
