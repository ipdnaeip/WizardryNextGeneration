package com.ipdnaeip.wizardrynextgeneration.entity.construct;

import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGSpells;
import com.ipdnaeip.wizardrynextgeneration.util.WNGUtils;
import electroblob.wizardry.entity.construct.EntityScaledConstruct;
import electroblob.wizardry.registry.WizardrySounds;
import electroblob.wizardry.spell.SpellBuff;
import electroblob.wizardry.util.ParticleBuilder;
import electroblob.wizardry.util.ParticleBuilder.Type;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityGravitationalField extends EntityScaledConstruct {

    public EntityGravitationalField(World world) {
        super(world);
        this.setSize(WNGSpells.gravitational_field.getProperty("effect_radius").floatValue() * 2.0F, 10F);
    }

    public void onUpdate() {
        if (this.ticksExisted % 25 == 1) {
            this.playSound(WizardrySounds.ENTITY_HEAL_AURA_AMBIENT, 0.1F, 1.0F);
        }
        super.onUpdate();
        if (!this.world.isRemote) {
            List<Entity> targets = WNGUtils.getEntitiesWithinCylinder((this.width / 2.0F), this.posX, this.posY, this.posZ, this.height * sizeMultiplier, this.world, Entity.class);
            for (Entity target : targets) {
                if (target instanceof EntityLivingBase) {
                    ((EntityLivingBase)target).addPotionEffect(new PotionEffect(WNGPotions.gravity, 10, SpellBuff.getStandardBonusAmplifier(damageMultiplier)));
                }
                //else target.motionY -= Math.pow(0.025, damageMultiplier);
            }
        } else if (this.rand.nextInt(15) == 0) {
            double radius = (0.5 + this.rand.nextDouble() * 0.3) * (double)this.width / 2.0;
            float angle = this.rand.nextFloat() * 3.1415927F * 2.0F;
            ParticleBuilder.create(Type.DARK_MAGIC).pos(this.posX + radius * (double)MathHelper.cos(angle), this.posY + 0.1, this.posZ + radius * (double)MathHelper.sin(angle)).clr(85, 255, 85).vel(0.0, 0.0, 0.0).spawn(this.world);
        }
    }

}
