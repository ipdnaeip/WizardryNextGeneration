package com.ipdnaeip.wizardrynextgeneration.entity.projectile;

import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGSpells;
import com.ipdnaeip.wizardrynextgeneration.util.WNGUtils;
import electroblob.wizardry.entity.projectile.EntityBomb;
import electroblob.wizardry.util.EntityUtils;
import electroblob.wizardry.util.ParticleBuilder;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.List;

public class EntityConjuredPotion extends EntityBomb {

    public PotionEffect potionEffect;
    public boolean isLingering;
    public boolean randomEffects;
    public float durationMultiplier;

    public EntityConjuredPotion(World world) {
        super(world);
        this.potionEffect = new PotionEffect(MobEffects.INSTANT_DAMAGE);
        this.isLingering = false;
        this.randomEffects = false;
        this.durationMultiplier = 1f;
    }

    @Override
    public int getLifetime() {
        return -1;
    }

    @Override
    protected void onImpact(@Nonnull RayTraceResult rayTrace) {
        if (this.world.isRemote) {
            for(int i = 0; (float)i < 60.0F * this.blastMultiplier; ++i) {
                float f1 = MathHelper.sqrt(this.rand.nextFloat()) * 0.2F;
                float f2 = MathHelper.cos(this.rand.nextFloat() * ((float)Math.PI * 2F)) * f1;
                float f3 = MathHelper.sin(this.rand.nextFloat() * ((float)Math.PI * 2F)) * f1;
                float f4 = MathHelper.sin(this.rand.nextFloat() * ((float)Math.PI * 2F)) * f1;
                ParticleBuilder.create(ParticleBuilder.Type.SPARKLE).pos(this.posX, this.posY, this.posZ).vel(f2, f3, f4).clr(150, 255, 150).time(20).spawn(this.world);
            }
        }
        if (!this.world.isRemote) {
            this.playSound(SoundEvents.ENTITY_SPLASH_POTION_BREAK, 1.5F, this.rand.nextFloat() * 0.4F + 0.6F);
            double range = (WNGSpells.toss_potion.getProperty("blast_radius").floatValue() * this.blastMultiplier);
            int duration = (int)(WNGSpells.toss_potion.getProperty("effect_duration").floatValue() * durationMultiplier);
            Potion randomPotion = WNGUtils.getRandomPotionEffect(false, potionEffect.getPotion().isBadEffect());
            if (!isLingering) {
                List<EntityLivingBase> targets = EntityUtils.getLivingWithinRadius(range, this.posX, this.posY, this.posZ, this.world);
                for (EntityLivingBase target : targets) {
                    if (potionEffect.getPotion().isBadEffect()) {
                        MobEffects.INSTANT_DAMAGE.affectEntity(this, this.getThrower(), target, (int)((damageMultiplier - 1) / 0.4), 1);
                    } else {
                        MobEffects.INSTANT_HEALTH.affectEntity(this, this.getThrower(), target, (int)((damageMultiplier - 1) / 0.4), 1);
                    }
                    if (!randomEffects) {
                        if (potionEffect.getPotion() != MobEffects.INSTANT_DAMAGE) {
                            target.addPotionEffect(new PotionEffect(potionEffect.getPotion(), duration, (int) ((damageMultiplier - 1) / 0.4)));
                        }
                    } else {
                        target.addPotionEffect(new PotionEffect(randomPotion, duration, (int)((damageMultiplier - 1) / 0.4)));
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
                entityareaeffectcloud.addEffect(new PotionEffect(potionEffect.getPotion(), duration, (int)((damageMultiplier - 1) / 0.4)));
                entityareaeffectcloud.setColor(9895830);
                this.world.spawnEntity(entityareaeffectcloud);
            }
            this.setDead();
        }
    }


}
