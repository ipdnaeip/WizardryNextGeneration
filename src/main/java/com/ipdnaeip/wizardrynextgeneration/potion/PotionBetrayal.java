package com.ipdnaeip.wizardrynextgeneration.potion;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.potion.PotionMagicEffect;
import electroblob.wizardry.util.EntityUtils;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Iterator;
import java.util.List;

@Mod.EventBusSubscriber
public class PotionBetrayal extends PotionMagicEffect {

    public PotionBetrayal() {
        super(true, 0xAA0000, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/betrayal.png"));
        this.setPotionName("potion." + WizardryNextGeneration.MODID + ":betrayal");
    }

    @SubscribeEvent
    public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.isPotionActive(WNGPotions.betrayal)) {
            List<EntityLivingBase> targets = EntityUtils.getLivingWithinRadius(8 + (4 * entity.getActivePotionEffect(WNGPotions.betrayal).getAmplifier()), entity.posX, entity.posY, entity.posZ, entity.getEntityWorld());
            for (EntityLivingBase targetEntity : targets) {
                if (targetEntity != entity && entity instanceof IMob && targetEntity instanceof IMob && entity instanceof EntityLiving) {
                    ((EntityLiving) entity).setAttackTarget(targetEntity);
                }
            }
        }
    }
}
