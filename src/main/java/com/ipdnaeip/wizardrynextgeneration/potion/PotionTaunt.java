package com.ipdnaeip.wizardrynextgeneration.potion;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.entity.living.ISummonedCreature;
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
public class PotionTaunt extends PotionMagicEffect {

    public PotionTaunt() {
        super(false, 0xAA6464, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/taunt.png"));
    }

    @SubscribeEvent
    public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.isPotionActive(WNGPotions.TAUNT)) {
            List<EntityLivingBase> targets = EntityUtils.getLivingWithinRadius(8 + (4 * entity.getActivePotionEffect(WNGPotions.TAUNT).getAmplifier()), entity.posX, entity.posY, entity.posZ, entity.getEntityWorld());
            for (EntityLivingBase targetEntity : targets) {
                if (targetEntity != entity && targetEntity instanceof IMob && targetEntity instanceof EntityLiving) {
                    if (!(targetEntity instanceof ISummonedCreature) || ((ISummonedCreature) targetEntity).getCaster() instanceof IMob) {
                        ((EntityLiving)targetEntity).setAttackTarget(entity);
                    }
                }
            }
        }
    }
}
