package com.ipdnaeip.wizardrynextgeneration.entity.projectile;

import com.ipdnaeip.wizardrynextgeneration.handler.ExperiencedPotionHandler;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGSpells;
import electroblob.wizardry.entity.projectile.EntityBomb;
import electroblob.wizardry.integration.baubles.WizardryBaublesIntegration;
import electroblob.wizardry.util.EntityUtils;
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
import net.minecraft.world.World;

import javax.annotation.Nonnull;
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
                float length = MathHelper.sqrt(this.rand.nextFloat()) * 0.2F;
                float x = MathHelper.cos(this.rand.nextFloat() * ((float)Math.PI * 2F)) * length;
                float y = MathHelper.sin(this.rand.nextFloat() * ((float)Math.PI * 2F)) * length;
                float z = MathHelper.sin(this.rand.nextFloat() * ((float)Math.PI * 2F)) * length;
                ParticleBuilder.create(ParticleBuilder.Type.SPARKLE).pos(this.posX, this.posY, this.posZ).vel(x, y, z).clr(150, 255, 150).time(20).spawn(this.world);
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
        return this.potionEffect == null ? MobEffects.INSTANT_DAMAGE : potionEffect.getPotion();
    }

    public Potion getConjuredPotionEffect() {
        if (this.randomEffects) {
            if (this.getPotion().isBadEffect()) {
                return ExperiencedPotionHandler.getValidPotion(ExperiencedPotionHandler.Context.BAD);
            } else {
                return ExperiencedPotionHandler.getValidPotion(ExperiencedPotionHandler.Context.GOOD);
            }
            //return WNGUtils.getRandomPotionEffect(false, this.getPotion().isBadEffect());
        } else {
            return getPotion();
        }
    }

}
