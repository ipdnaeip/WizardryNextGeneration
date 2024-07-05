package com.ipdnaeip.wizardrynextgeneration.potion;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.potion.ICustomPotionParticles;
import electroblob.wizardry.potion.PotionMagicEffect;
import electroblob.wizardry.util.ParticleBuilder;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionCleansingFlames extends PotionMagicEffect implements ICustomPotionParticles {

    public PotionCleansingFlames() {
        super(false, 0xFFC83C, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/cleansing_flames.png"));
    }

    public void spawnCustomParticle(World world, double x, double y, double z) {
        ParticleBuilder.create(ParticleBuilder.Type.MAGIC_FIRE).pos(x, y, z).clr(255, 200, 60).time(10).spawn(world);
    }

    @SubscribeEvent
    public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.isPotionActive(WNGPotions.CLEANSING_FLAMES)) {
            if (entity.isBurning()) {
                entity.extinguish();
            }
            if (entity.ticksExisted % 20 == 0) {
                entity.getEntityWorld().playSound(null, entity.getPosition(), SoundEvents.ENTITY_BLAZE_BURN, SoundCategory.PLAYERS, 0.5F, entity.getEntityWorld().rand.nextFloat() * 0.2F + 0.9F);
            }
            if (entity.ticksExisted % 20 / (entity.getActivePotionEffect(WNGPotions.CLEANSING_FLAMES).getAmplifier() + 1) == 0)  {
                entity.heal(0.5F);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingAttackEvent(LivingAttackEvent event) {
        if (event.getEntityLiving().isPotionActive(WNGPotions.CLEANSING_FLAMES) && event.getSource().isFireDamage()) {
            event.setCanceled(true);
        }
    }

}
