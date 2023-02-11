package com.ipdnaeip.wizardrynextgeneration.potion;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.potion.ICustomPotionParticles;
import electroblob.wizardry.potion.PotionMagicEffect;
import electroblob.wizardry.util.ParticleBuilder;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionCleansingFlames extends PotionMagicEffect implements ICustomPotionParticles {

    public PotionCleansingFlames() {
        super(false, 0xFFC83C, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/cleansing_flames.png"));
        this.setPotionName("potion." + WizardryNextGeneration.MODID + ":cleansing_flames");
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    public void spawnCustomParticle(World world, double x, double y, double z) {
        ParticleBuilder.create(ParticleBuilder.Type.MAGIC_FIRE).pos(x, y, z).clr(255, 200, 60).time(10).spawn(world);
    }

    @SubscribeEvent
    public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.isBurning()) {
            entity.extinguish();
        }
        if (entity.isPotionActive(WNGPotions.cleansing_flames)) {
            //entity.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 10, 0, false, false));
            if (entity.ticksExisted % 20 / (entity.getActivePotionEffect(WNGPotions.cleansing_flames).getAmplifier() + 1) == 0)  {
                entity.getEntityWorld().playSound(null, entity.getPosition(), SoundEvents.ENTITY_BLAZE_BURN, SoundCategory.BLOCKS, 0.5F, entity.getEntityWorld().rand.nextFloat() * 0.2F + 0.9F);
                entity.heal(0.5F);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurtEvent(LivingHurtEvent event) {
        if (event.getEntityLiving().isPotionActive(WNGPotions.cleansing_flames) && event.getSource().isFireDamage()) {
            event.setCanceled(true);
        }
    }

}
