package com.ipdnaeip.wizardrynextgeneration.potion;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.item.ItemNewArtefact;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.Wizardry;
import electroblob.wizardry.item.ItemArtefact;
import electroblob.wizardry.potion.ICustomPotionParticles;
import electroblob.wizardry.potion.PotionMagicEffect;
import electroblob.wizardry.registry.WizardrySounds;
import electroblob.wizardry.util.ParticleBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


@Mod.EventBusSubscriber
public class PotionSolarWinds extends PotionMagicEffect implements ICustomPotionParticles {

    public PotionSolarWinds() {
        super(false, 0xFFB432, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/solar_winds.png"));
        this.setPotionName("potion." + WizardryNextGeneration.MODID + ":solar_winds");
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    public void spawnCustomParticle(World world, double x, double y, double z) {
        ParticleBuilder.create(ParticleBuilder.Type.MAGIC_FIRE).pos(x, y, z).clr(255, 180, 50).time(10).spawn(world);
    }

/*    @SubscribeEvent
    public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity instanceof EntityPlayer) {
            EntityPlayer entityPlayer = (EntityPlayer) event.getEntityLiving();
            if (entity.isPotionActive(WNGPotions.solar_winds)) {
                entity.getEntityWorld().playSound(null, entity.getPosition(), SoundEvents.ENTITY_BLAZE_BURN, SoundCategory.BLOCKS, 0.5F, entity.getEntityWorld().rand.nextFloat() * 0.2F + 0.9F);
                if (!entity.onGround && GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindJump)) {
                    if (!Wizardry.settings.replaceVanillaFallDamage) {
                        entity.fallDistance = 0.0F;
                    }
                    entity.motionY = entity.motionY < 0.5F ? entity.motionY + 0.1F : entity.motionY;
                    if (ItemNewArtefact.isNewArtefactActive(entityPlayer, WNGItems.head_ra)) {
                        entity.jumpMovementFactor = 0.05F;
                    }
                }
            }
        }
    }*/

/*    @SubscribeEvent
    public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        entity.getEntityWorld().playSound(null, entity.getPosition(), SoundEvents.ENTITY_BLAZE_BURN, SoundCategory.BLOCKS, 0.5F, entity.getEntityWorld().rand.nextFloat() * 0.2F + 0.9F);
    }

    @SubscribeEvent
    public static void onLivingJumpEvent(LivingEvent.LivingJumpEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.isPotionActive(WNGPotions.solar_winds)) {
            entity.fallDistance = 0.0F;
            entity.motionY = entity.motionY < 0.5F ? entity.motionY + 0.1F : entity.motionY;
            if (entity instanceof EntityPlayer && ItemNewArtefact.isNewArtefactActive((EntityPlayer) entity, WNGItems.head_ra)) {
                entity.jumpMovementFactor = 0.05F;
            }
        }
    }*/
}
