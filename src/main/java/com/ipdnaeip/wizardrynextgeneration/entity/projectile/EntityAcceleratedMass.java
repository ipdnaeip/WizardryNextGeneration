package com.ipdnaeip.wizardrynextgeneration.entity.projectile;

import com.ipdnaeip.wizardrynextgeneration.registry.WNGAdvancementTriggers;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGSpells;
import com.ipdnaeip.wizardrynextgeneration.spell.AcceleratedMass;
import electroblob.wizardry.entity.projectile.EntityMagicArrow;
import electroblob.wizardry.spell.Spell;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityAcceleratedMass extends EntityMagicArrow {

    double damage = WNGSpells.accelerated_mass.getProperty(Spell.DAMAGE).doubleValue();
    double damage_multiplier = WNGSpells.accelerated_mass.getProperty(AcceleratedMass.MAX_MULTIPLIER).doubleValue();
    double acceleration = WNGSpells.accelerated_mass.getProperty(AcceleratedMass.ACCELERATION).doubleValue();

    public EntityAcceleratedMass(World world) {
            super(world);
        }

    public double getDamage() {
            return damage;
        }

    @Override
    public boolean doGravity() { return true; }

    @Override
    public boolean doDeceleration() {
            return false;
        }

    @Override
    public boolean doOverpenetration() {
        return true;
    }

    @Override
    public void aim(EntityLivingBase caster, float speed) {
        this.setCaster(caster);
        this.setLocationAndAngles(caster.posX, caster.posY + (double) caster.getEyeHeight() - 0.1, caster.posZ, caster.rotationYaw, caster.rotationPitch);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.motionX = (-MathHelper.sin(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F));
        this.motionY = (-MathHelper.sin(this.rotationPitch / 180.0F * 3.1415927F));
        this.motionZ = (MathHelper.cos(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F));
        this.shoot(this.motionX, this.motionY, this.motionZ, speed * 1.5F, 0F);
    }

    @Override
    public void onEntityHit(EntityLivingBase entityHit) {
        this.playSound(SoundEvents.BLOCK_ANVIL_PLACE, 0.5F, 0.7F + (this.rand.nextFloat() * 0.1F));
        if (damage == WNGSpells.accelerated_mass.getProperty(Spell.DAMAGE).doubleValue() * damage_multiplier && this.getCaster() instanceof EntityPlayer) {
            WNGAdvancementTriggers.accelerated_mass_max_damage.triggerFor((EntityPlayer)(this.getCaster()));
        }
    }

    @Override
    public void onBlockHit(RayTraceResult hit) {
        this.playSound(SoundEvents.BLOCK_ANVIL_LAND, 0.5F, 1.0F + (this.rand.nextFloat() * 0.1F));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.motionX *= acceleration;
        this.motionY *= acceleration;
        this.motionZ *= acceleration;
        damage = Math.min(damage * acceleration, WNGSpells.accelerated_mass.getProperty(Spell.DAMAGE).doubleValue() * damage_multiplier);
    }

    public int getLifetime() {
            return 100;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        return super.isInRangeToRenderDist(distance / 2);
    }

}
