package com.ipdnaeip.wizardrynextgeneration.potion;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.potion.PotionMagicEffect;
import electroblob.wizardry.util.EntityUtils;
import electroblob.wizardry.util.ParticleBuilder;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

@Mod.EventBusSubscriber
public class PotionVeneficium extends PotionMagicEffect {

    public PotionVeneficium() {
        super(false, 0x00AA00, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/veneficium.png"));
    }

    @SubscribeEvent
    public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.isPotionActive(WNGPotions.VENEFICIUM)) {
            if (entity.getHealth() > 1F && entity.ticksExisted % 10 == 0) {
                entity.attackEntityFrom(DamageSource.MAGIC, 1F);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDeathEvent(LivingDeathEvent event) {
        if (event.getSource().getTrueSource() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
            EntityLivingBase entity = event.getEntityLiving();
            if (entity.isPotionActive(WNGPotions.VENEFICIUM)) {
                List<EntityLivingBase> targets = EntityUtils.getLivingWithinRadius(4 + (2 * entity.getActivePotionEffect(WNGPotions.VENEFICIUM).getAmplifier()), entity.posX, entity.posY, entity.posZ, entity.getEntityWorld());
                for (EntityLivingBase targetEntity : targets) {
                    if (targetEntity != player) {
                        if (player.getEntityWorld().isRemote) {
                            for (int i = 0; (float) i < 60.0F; ++i) {
                                ParticleBuilder.create(ParticleBuilder.Type.CLOUD, player.getEntityWorld().rand, entity.posX, entity.posY + entity.getEyeHeight() - 0.5F, entity.posZ, (2.0F), false).clr(0, 100, 0).fade(0, 170, 0).time(10 + player.getEntityWorld().rand.nextInt(10)).shaded(true).spawn(player.getEntityWorld());
                            }
                        }
                        targetEntity.addPotionEffect(new PotionEffect(WNGPotions.VENEFICIUM, entity.getActivePotionEffect(WNGPotions.VENEFICIUM).getDuration(), entity.getActivePotionEffect(WNGPotions.VENEFICIUM).getAmplifier()));
                    }
                }
            }
        }
    }


}
