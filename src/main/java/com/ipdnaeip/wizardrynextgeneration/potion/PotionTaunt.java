package com.ipdnaeip.wizardrynextgeneration.potion;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.entity.living.ISummonedCreature;
import electroblob.wizardry.potion.PotionMagicEffect;
import electroblob.wizardry.util.AllyDesignationSystem;
import electroblob.wizardry.util.EntityUtils;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

@Mod.EventBusSubscriber
public class PotionTaunt extends PotionMagicEffect {

    public PotionTaunt() {
        super(false, 0xAA6464, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/taunt.png"));
    }

    public static void tauntEnemies(EntityLivingBase taunter, double radius, boolean forceAttack) {
        List<EntityLiving> tauntedEntities = EntityUtils.getEntitiesWithinRadius(radius, taunter.posX, taunter.posY, taunter.posZ, taunter.world, EntityLiving.class);
        for (EntityLiving tauntedEntity : tauntedEntities) {
            if (forceAttack || tauntedEntity.getAttackTarget() == null) {
                if (AllyDesignationSystem.isValidTarget(tauntedEntity, taunter) && EntitySelectors.CAN_AI_TARGET.test(tauntedEntity) && tauntedEntity.canEntityBeSeen(taunter)) {
                    tauntedEntity.setAttackTarget(taunter);
                }
            }
        }
    }

    public static double getRadiusForAmplifier(int amplifier) {
        return 8 + 4 * amplifier;
    }

    @SubscribeEvent
    public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.isPotionActive(WNGPotions.TAUNT)) {
            tauntEnemies(entity, getRadiusForAmplifier(entity.getActivePotionEffect(WNGPotions.TAUNT).getAmplifier()), false);
        }
    }
}
