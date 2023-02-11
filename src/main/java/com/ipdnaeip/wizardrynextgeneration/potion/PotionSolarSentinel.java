package com.ipdnaeip.wizardrynextgeneration.potion;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.item.ItemArtefact;
import electroblob.wizardry.potion.ICustomPotionParticles;
import electroblob.wizardry.potion.PotionMagicEffect;
import electroblob.wizardry.util.ParticleBuilder;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionSolarSentinel extends PotionMagicEffect implements ICustomPotionParticles {

    public PotionSolarSentinel() {
        super(false, 0xFFB432, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/solar_sentinel.png"));
        this.setPotionName("potion." + WizardryNextGeneration.MODID + ":solar_sentinel");
        this.registerPotionAttributeModifier(SharedMonsterAttributes.ARMOR, "56a095be-82dd-43fd-8b67-13f51591eb8e", 8.0, 0);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    public void spawnCustomParticle(World world, double x, double y, double z) {
        ParticleBuilder.create(ParticleBuilder.Type.MAGIC_FIRE).pos(x, y, z).clr(255, 180, 50).time(10).spawn(world);
    }

    @SubscribeEvent
    public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity instanceof EntityPlayer) {
            EntityPlayer entityPlayer = (EntityPlayer) event.getEntityLiving();
            if (entity.isPotionActive(WNGPotions.solar_sentinel)) {
                entity.getEntityWorld().playSound(null, entity.getPosition(), SoundEvents.ENTITY_BLAZE_BURN, SoundCategory.BLOCKS, 0.5F, entity.getEntityWorld().rand.nextFloat() * 0.2F + 0.9F);
                if (!entity.onGround && entity.isSneaking()) {
                    entity.motionY += 0.1;
                    if (ItemArtefact.isArtefactActive(entityPlayer, WNGItems.head_ra)) {
                        entity.jumpMovementFactor = 0.2F;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurtEvent(LivingHurtEvent event) {
        if (event.getEntityLiving().isPotionActive(WNGPotions.solar_sentinel) && event != null && event.getSource().isFireDamage()) {
            event.setCanceled(true);
        }
    }

}
