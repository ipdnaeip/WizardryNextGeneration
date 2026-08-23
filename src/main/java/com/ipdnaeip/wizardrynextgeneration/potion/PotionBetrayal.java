package com.ipdnaeip.wizardrynextgeneration.potion;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import com.ipdnaeip.wizardrynextgeneration.util.WNGUtils;
import electroblob.wizardry.potion.PotionMagicEffect;
import electroblob.wizardry.util.EntityUtils;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

@Mod.EventBusSubscriber
public class PotionBetrayal extends PotionMagicEffect {

    public PotionBetrayal() {
        super(true, 0xAA0000, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/betrayal.png"));
    }

    @SubscribeEvent
    public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity instanceof EntityLiving) {
            EntityLiving entityLiving = (EntityLiving)entity;
            if (entityLiving.isPotionActive(WNGPotions.BETRAYAL) && !(entityLiving.getAttackTarget() instanceof IMob)) {
                List<EntityLivingBase> targets = EntityUtils.getLivingWithinRadius(8 + (4 * entityLiving.getActivePotionEffect(WNGPotions.BETRAYAL).getAmplifier()), entityLiving.posX, entityLiving.posY, entityLiving.posZ, entityLiving.world);
                targets.sort(WNGUtils.compareClosestEntity(entityLiving));
                for (EntityLivingBase targetEntity : targets) {
                    if (targetEntity != entityLiving && entityLiving instanceof IMob && targetEntity instanceof IMob) {
                        entityLiving.setAttackTarget(targetEntity);
                        break;
                    }
                }
            }
        }
    }
}
