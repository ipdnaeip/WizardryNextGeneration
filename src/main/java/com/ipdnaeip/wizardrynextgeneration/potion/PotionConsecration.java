/*package com.ipdnaeip.wizardrynextgeneration.potion;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGSounds;
import electroblob.wizardry.potion.PotionMagicEffect;
import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.registry.WizardrySounds;
import electroblob.wizardry.util.EntityUtils;
import electroblob.wizardry.util.MagicDamage;
import electroblob.wizardry.util.ParticleBuilder;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

@Mod.EventBusSubscriber
public class PotionConsecration extends PotionMagicEffect {

    static final int radius = 3;

    public PotionConsecration() {
        super(false, 0xFFD651, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/consecration.png"));
        this.setPotionName("potion." + WizardryNextGeneration.MODID + ":consecration");
    }

    @SubscribeEvent
    public static void onLivingHealEvent(LivingHealEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.isPotionActive(WNGPotions.consecration)) {
            List<EntityLivingBase> targets = EntityUtils.getLivingWithinRadius(radius, entity.posX, entity.posY, entity.posZ, entity.world);
            for (EntityLivingBase target : targets) {
                target.attackEntityFrom(MagicDamage.causeDirectMagicDamage(entity, MagicDamage.DamageType.RADIANT), (Math.max(event.getAmount() * (0.5f + 0.25F * entity.getActivePotionEffect(WNGPotions.consecration).getAmplifier())  * (radius - target.getDistance(entity)), 0.0F)));
                entity.world.playSound(null, entity.getPosition(), WNGSounds.CONSECRATION_PULSE, WizardrySounds.SPELLS, 1f, 1f);
                if (entity.world.isRemote) {
                    ParticleBuilder.create(ParticleBuilder.Type.SPHERE).pos(entity.getPositionVector()).scale(4.0F).clr(16773272).spawn(entity.world);
                }
            }
        }
    }

}*/
