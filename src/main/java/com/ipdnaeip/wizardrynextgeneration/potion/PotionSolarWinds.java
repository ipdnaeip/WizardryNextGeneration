package com.ipdnaeip.wizardrynextgeneration.potion;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import electroblob.wizardry.potion.ICustomPotionParticles;
import electroblob.wizardry.potion.PotionMagicEffect;
import electroblob.wizardry.util.ParticleBuilder;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber
public class PotionSolarWinds extends PotionMagicEffect implements ICustomPotionParticles {

    //It doesn't look like I can feasibly allow NPCs to use this effect

    public PotionSolarWinds() {
        super(false, 0xFFB432, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/solar_winds.png"));
    }

    public void spawnCustomParticle(World world, double x, double y, double z) {
        ParticleBuilder.create(ParticleBuilder.Type.MAGIC_FIRE).pos(x, y, z).clr(255, 180, 50).time(10).spawn(world);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return duration % 20 == 0;
    }

    @Override
    public void performEffect(EntityLivingBase entity, int strength) {
        super.performEffect(entity, strength);
        entity.getEntityWorld().playSound(null, entity.getPosition(), SoundEvents.ENTITY_BLAZE_BURN, SoundCategory.BLOCKS, 0.5F, entity.getEntityWorld().rand.nextFloat() * 0.2F + 0.9F);
    }

/*    @SubscribeEvent
    public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity instanceof EntityPlayer) {
            EntityPlayer entityPlayer = (EntityPlayer) event.getEntityLiving();
            if (entity.isPotionActive(WNGPotions.SOLAR_WINDS)) {
                boolean flying = false;
                entity.getEntityWorld().playSound(null, entity.getPosition(), SoundEvents.ENTITY_BLAZE_BURN, SoundCategory.BLOCKS, 0.5F, entity.getEntityWorld().rand.nextFloat() * 0.2F + 0.9F);
                if (!entity.onGround && GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindJump)) {
                    entity.motionY = entity.motionY < 0.5F ? entity.motionY + 0.1F : entity.motionY;
                    flying = true;
                }
                if (flying) {
                    if (!Wizardry.settings.replaceVanillaFallDamage) {
                        entity.fallDistance = 0.0F;
                    }
                    if (ItemArtefact.isArtefactActive(entityPlayer, WNGItems.HEAD_RA)) {
                        entity.jumpMovementFactor = 0.05F;
                    }
                }
            }
        }
    }*/
}
