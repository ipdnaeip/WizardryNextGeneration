package com.ipdnaeip.wizardrynextgeneration.potion;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.potion.ICustomPotionParticles;
import electroblob.wizardry.potion.PotionMagicEffect;
import electroblob.wizardry.util.EntityUtils;
import electroblob.wizardry.util.ParticleBuilder;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Iterator;
import java.util.List;

@Mod.EventBusSubscriber
public class PotionFrenzy extends PotionMagicEffect {

    public PotionFrenzy() {
        super(true, 0xAA0000, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/frenzy.png"));
        this.setPotionName("potion." + WizardryNextGeneration.MODID + ":frenzy");
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @SubscribeEvent
    public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.isPotionActive(WNGPotions.frenzy)) {
            List<EntityLivingBase> targets = EntityUtils.getLivingWithinRadius(5 + (2.5 * entity.getActivePotionEffect(WNGPotions.frenzy).getAmplifier()), entity.posX, entity.posY, entity.posZ, entity.getEntityWorld());
            Iterator var6 = targets.iterator();
            while (var6.hasNext()) {
                EntityLivingBase targetEntity = (EntityLivingBase) var6.next();
/*                if (targetEntity != entity && entity instanceof EntityMob) {
                    ((EntityMob) entity).setAttackTarget(targetEntity);
                }*/
                if (targetEntity != entity && entity instanceof EntityLiving) {
                    ((EntityLiving) entity).setAttackTarget(targetEntity);
                }
            }
        }
    }
}
