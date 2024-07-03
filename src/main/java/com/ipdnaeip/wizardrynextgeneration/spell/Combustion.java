package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.util.WNGUtils;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.spell.SpellRay;
import electroblob.wizardry.util.EntityUtils;
import electroblob.wizardry.util.MagicDamage;
import electroblob.wizardry.util.ParticleBuilder;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import java.util.List;

public class Combustion extends SpellRay {

    public Combustion() {
        super(WizardryNextGeneration.MODID, "combustion", SpellActions.POINT, false);
        this.soundValues(1.0F, 1.0F, 0.1F);
        this.addProperties(DAMAGE, BLAST_RADIUS, EFFECT_DURATION);
    }

    @Override
    protected boolean onEntityHit(World world, Entity target, Vec3d hit, EntityLivingBase caster, Vec3d origin, int ticksInUse, SpellModifiers modifiers) {
        if (target instanceof EntityLivingBase) {
            EntityLivingBase entityLivingBase = (EntityLivingBase)target;
            float targetHealth = entityLivingBase.getHealth();
            MagicDamage.DamageType damageType = MagicDamage.DamageType.FIRE;
            if (WNGUtils.canMagicDamageEntity(caster, entityLivingBase, damageType, this, ticksInUse)) {
                target.attackEntityFrom(MagicDamage.causeDirectMagicDamage(caster, damageType), this.getProperty(DAMAGE).floatValue() * modifiers.get(SpellModifiers.POTENCY));
                float overkill = this.getProperty(DAMAGE).floatValue() * modifiers.get(SpellModifiers.POTENCY) - (entityLivingBase.getHealth() - targetHealth);
                if (overkill > 0 && (entityLivingBase.getHealth() <= 0)) {
                    if (!world.isRemote) {
                        List<EntityLivingBase> targets = EntityUtils.getLivingWithinRadius(this.getProperty(BLAST_RADIUS).doubleValue() * (double) modifiers.get(WizardryItems.blast_upgrade), target.posX, target.posY, target.posZ, world);
                        for (EntityLivingBase targetEntity : targets) {
                            targetEntity.attackEntityFrom(MagicDamage.causeDirectMagicDamage(caster, damageType), Math.max(overkill - (float) targetEntity.getDistance(target.posX + 0.5, target.posY + 0.5, target.posZ + 0.5) * (overkill / this.getProperty(BLAST_RADIUS).floatValue()), 0.0F));
                            targetEntity.setFire((int) (Math.max(this.getProperty(EFFECT_DURATION).floatValue() - (float) targetEntity.getDistance(target.posX + 0.5, target.posY + 0.5, target.posZ + 0.5) * (overkill / this.getProperty(BLAST_RADIUS).floatValue()), 1F)));
                        }
                    } else {
                        world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, target.posX + 0.5, target.posY + 0.5, target.posZ + 0.5, 0.0, 0.0, 0.0);
                        world.playSound(null, target.getPosition(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 1f, 0.9f + world.rand.nextFloat() * 0.2f);
                    }
                }
            }
        }
        return true;
    }

    @Override
    protected boolean onBlockHit(World world, BlockPos pos, EnumFacing side, Vec3d hit, EntityLivingBase caster, Vec3d origin, int ticksInUse, SpellModifiers modifiers) {
        return false;
    }

    @Override
    protected boolean onMiss(World world, EntityLivingBase caster, Vec3d origin, Vec3d direction, int ticksInUse, SpellModifiers modifiers) {
        return true;
    }

    @Override
    protected void spawnParticle(World world, double x, double y, double z, double vx, double vy, double vz) {
        ParticleBuilder.create(ParticleBuilder.Type.MAGIC_FIRE).pos(x, y, z).time(20 + world.rand.nextInt(10)).spawn(world);
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.SPELL_BOOK_WNG || item == WNGItems.SCROLL_WNG;
    }

}
