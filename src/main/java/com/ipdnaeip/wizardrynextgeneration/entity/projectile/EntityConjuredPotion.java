package com.ipdnaeip.wizardrynextgeneration.entity.projectile;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGSpells;
import com.ipdnaeip.wizardrynextgeneration.util.WNGUtils;
import electroblob.wizardry.data.IStoredVariable;
import electroblob.wizardry.data.IVariable;
import electroblob.wizardry.data.Persistence;
import electroblob.wizardry.data.WizardData;
import electroblob.wizardry.entity.projectile.EntityBomb;
import electroblob.wizardry.integration.baubles.WizardryBaublesIntegration;
import electroblob.wizardry.spell.Transportation;
import electroblob.wizardry.util.EntityUtils;
import electroblob.wizardry.util.Location;
import electroblob.wizardry.util.NBTExtras;
import electroblob.wizardry.util.ParticleBuilder;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class EntityConjuredPotion extends EntityBomb {

    public PotionEffect potionEffect;
    public boolean isLingering;
    public boolean hasInstantEffect;
    public boolean randomEffects;
    public float durationMultiplier;

    public EntityConjuredPotion(World world) {
        super(world);
        this.potionEffect = null;
        this.isLingering = false;
        this.hasInstantEffect = false;
        this.randomEffects = false;
        this.durationMultiplier = 1f;
    }

    @Override
    public int getLifetime() {
        return -1;
    }

    @Override
    protected void onImpact(@Nonnull RayTraceResult rayTrace) {
        System.out.println(this.hasInstantEffect);
        if (this.world.isRemote) {
            for(int i = 0; (float)i < 60.0F * this.blastMultiplier; ++i) {
                float f1 = MathHelper.sqrt(this.rand.nextFloat()) * 0.2F;
                float f2 = MathHelper.cos(this.rand.nextFloat() * ((float)Math.PI * 2F)) * f1;
                float f3 = MathHelper.sin(this.rand.nextFloat() * ((float)Math.PI * 2F)) * f1;
                float f4 = MathHelper.sin(this.rand.nextFloat() * ((float)Math.PI * 2F)) * f1;
                ParticleBuilder.create(ParticleBuilder.Type.SPARKLE).pos(this.posX, this.posY, this.posZ).vel(f2, f3, f4).clr(150, 255, 150).time(20).spawn(this.world);
            }
            if (this.hasInstantEffect) {
                ParticleBuilder.create(ParticleBuilder.Type.SPHERE).pos(this.posX, this.posY, this.posZ).clr(150, 255, 150).scale(4.0f).spawn(this.world);
            }
        }
        if (!this.world.isRemote) {
            this.isLingering = this.getThrower() instanceof EntityPlayer && WizardryBaublesIntegration.isBaubleEquipped((EntityPlayer)this.getThrower(), WNGItems.charm_lingering);
            this.playSound(SoundEvents.ENTITY_SPLASH_POTION_BREAK, 1.5F, this.rand.nextFloat() * 0.4F + 0.6F);
            double range = (WNGSpells.potion_bomb.getProperty("blast_radius").floatValue() * this.blastMultiplier);
            int duration = (int)(WNGSpells.potion_bomb.getProperty("effect_duration").floatValue() * durationMultiplier);
            List<EntityLivingBase> targets = EntityUtils.getLivingWithinRadius(range, this.posX, this.posY, this.posZ, this.world);
            if (this.hasInstantEffect) {
                for (EntityLivingBase target : targets) {
                    if (getConjuredPotionEffect().isBadEffect()) {
                        if (getConjuredPotionEffect() == MobEffects.INSTANT_DAMAGE && !this.isLingering) {
                            MobEffects.INSTANT_DAMAGE.affectEntity(this, this.getThrower(), target, (int) ((damageMultiplier - 1) / 0.4), 1);
                        }
                        else{
                            MobEffects.INSTANT_DAMAGE.affectEntity(this, this.getThrower(), target, (int) ((damageMultiplier - 1) / 0.4), 0.5);
                        }
                    } else {
                        MobEffects.INSTANT_HEALTH.affectEntity(this, this.getThrower(), target, (int) ((damageMultiplier - 1) / 0.4), 0.5);
                    }
                }
            }
            if (!this.isLingering) {
                for (EntityLivingBase target : targets) {
                    if (getConjuredPotionEffect().isBadEffect()) {
                        MobEffects.INSTANT_DAMAGE.affectEntity(this, this.getThrower(), target, (int) ((damageMultiplier - 1) / 0.4), 0.5);
                    } else {
                        MobEffects.INSTANT_HEALTH.affectEntity(this, this.getThrower(), target, (int) ((damageMultiplier - 1) / 0.4), 0.5);
                    }
                    if (!getConjuredPotionEffect().isInstant()) {
                        target.addPotionEffect(new PotionEffect(getConjuredPotionEffect(), duration, (int) ((damageMultiplier - 1) / 0.4)));
                    }
                }
            }
            else {
                EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(this.world, this.posX, this.posY, this.posZ);
                entityareaeffectcloud.setOwner(this.getThrower());
                entityareaeffectcloud.setRadius((float)range);
                entityareaeffectcloud.setDuration(duration);
                entityareaeffectcloud.setRadiusOnUse(-0.5F);
                entityareaeffectcloud.setWaitTime(10);
                entityareaeffectcloud.setRadiusPerTick(-entityareaeffectcloud.getRadius() / (float)entityareaeffectcloud.getDuration());
                entityareaeffectcloud.addEffect(new PotionEffect(getConjuredPotionEffect(), duration / 2, (int)((damageMultiplier - 1) / 0.4)));
                entityareaeffectcloud.setColor(9895830);
                this.world.spawnEntity(entityareaeffectcloud);
            }
            this.setDead();
        }
    }

    public Potion getPotion() {
        return potionEffect == null ? MobEffects.INSTANT_DAMAGE : potionEffect.getPotion();
    }

    public Potion getConjuredPotionEffect() {
        if (randomEffects) {
            return WNGUtils.getRandomPotionEffect(false, getPotion().isBadEffect());
        } else {
            return getPotion();
        }
    }

}
