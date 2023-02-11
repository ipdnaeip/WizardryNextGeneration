package com.ipdnaeip.wizardrynextgeneration.potion;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGSpells;
import electroblob.wizardry.potion.ICustomPotionParticles;
import electroblob.wizardry.potion.PotionMagicEffect;
import electroblob.wizardry.registry.WizardryPotions;
import electroblob.wizardry.registry.WizardrySounds;
import electroblob.wizardry.util.EntityUtils;
import electroblob.wizardry.util.ParticleBuilder;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionDivineShield extends PotionMagicEffect implements ICustomPotionParticles {

    public PotionDivineShield() {
        super(false, 0xFFD651, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/divine_shield.png"));
        this.setPotionName("potion." + WizardryNextGeneration.MODID + ":divine_shield");
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    public void spawnCustomParticle(World world, double x, double y, double z) {
    ParticleBuilder.create(ParticleBuilder.Type.FLASH).pos(x, y, z).clr(255, 255, 0).time(10).spawn(world);
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onLivingHurtEvent(LivingHurtEvent event) {
        if (event.getEntityLiving().isPotionActive(WNGPotions.divine_shield)) {
            event.setCanceled(true);
            event.getEntityLiving().removePotionEffect(WNGPotions.divine_shield);
            EntityUtils.playSoundAtPlayer((EntityPlayer)event.getEntityLiving(), SoundEvents.ITEM_SHIELD_BLOCK, 1.25F, 1.0F);
            EntityUtils.playSoundAtPlayer((EntityPlayer)event.getEntityLiving(), WizardrySounds.ENTITY_SHIELD_DEFLECT, 1.25F, 1.0F);
        }
    }

}
