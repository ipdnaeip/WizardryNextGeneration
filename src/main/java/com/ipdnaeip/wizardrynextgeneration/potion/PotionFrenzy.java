package com.ipdnaeip.wizardrynextgeneration.potion;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import com.ipdnaeip.wizardrynextgeneration.util.WNGUtils;
import electroblob.wizardry.potion.PotionMagicEffect;
import electroblob.wizardry.util.EntityUtils;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

@Mod.EventBusSubscriber
public class PotionFrenzy extends PotionMagicEffect {

    public PotionFrenzy() {
        super(true, 0xAA0000, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/frenzy.png"));
    }

    @SubscribeEvent
    public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity instanceof EntityLiving) {
            EntityLiving attacker = (EntityLiving)entity;
            if (attacker.isPotionActive(WNGPotions.FRENZY) && attacker.getAttackTarget() == null) {
                List<EntityLivingBase> targets = EntityUtils.getLivingWithinRadius(8 + (4 * attacker.getActivePotionEffect(WNGPotions.FRENZY).getAmplifier()), attacker.posX, attacker.posY, attacker.posZ, attacker.world);
                targets.sort(WNGUtils.compareClosestEntity(attacker));
                for (EntityLivingBase target : targets) {
                    if (target != attacker && EntitySelectors.CAN_AI_TARGET.test(target) && entity.canEntityBeSeen(target)) {
                        attacker.setAttackTarget(target);
                        break;
                    }
                }
            }
        }
    }
}
