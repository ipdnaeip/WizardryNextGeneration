package com.ipdnaeip.wizardrynextgeneration.entity.construct;

import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGSpells;
import electroblob.wizardry.entity.construct.EntityScaledConstruct;
import electroblob.wizardry.registry.WizardrySounds;
import electroblob.wizardry.util.EntityUtils;
import electroblob.wizardry.util.ParticleBuilder;
import electroblob.wizardry.util.ParticleBuilder.Type;
import java.util.Iterator;
import java.util.List;
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
            List<EntityLivingBase> targets = EntityUtils.getLivingWithinRadius((this.width / 2.0F), this.posX, this.posY, this.posZ, this.world);
            Iterator var2 = targets.iterator();

            while(var2.hasNext()) {
                EntityLivingBase target = (EntityLivingBase)var2.next();
                target.addPotionEffect(new PotionEffect(WNGPotions.gravity, 10, (int)((damageMultiplier - 1) * 3.5)));
            }
        } else if (this.rand.nextInt(15) == 0) {
            double radius = (0.5 + this.rand.nextDouble() * 0.3) * (double)this.width / 2.0;
            float angle = this.rand.nextFloat() * 3.1415927F * 2.0F;
            ParticleBuilder.create(Type.DARK_MAGIC).pos(this.posX + radius * (double)MathHelper.cos(angle), this.posY + 0.1, this.posZ + radius * (double)MathHelper.sin(angle)).clr(85, 255, 85).vel(0.0, 0.0, 0.0).spawn(this.world);
        }
    }
    public boolean canRenderOnFire() {
        return false;
    }

}
