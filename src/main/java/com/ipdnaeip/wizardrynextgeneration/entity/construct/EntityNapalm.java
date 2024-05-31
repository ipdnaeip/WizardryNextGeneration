package com.ipdnaeip.wizardrynextgeneration.entity.construct;

import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGSpells;
import com.ipdnaeip.wizardrynextgeneration.util.WNGUtils;
import electroblob.wizardry.spell.SpellBuff;
import electroblob.wizardry.util.BlockUtils;
import electroblob.wizardry.util.EntityUtils;
import electroblob.wizardry.util.ParticleBuilder;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import java.util.List;

public class EntityNapalm extends EntityLivingScaledConstruct {

    public int textureIndex;

    public EntityNapalm(World world) {
        super(world);
        this.textureIndex = this.rand.nextInt(10);
        this.setSize(WNGSpells.napalm.getProperty("effect_radius").floatValue(), 0.2F);
    }

    @Override
    public void onUpdate() {
        boolean burningEntity = false;
        super.onUpdate();
        if (this.isBurning()) {
            this.explode();
        }
        if (!this.world.isRemote) {
            List<EntityLivingBase> targets = EntityUtils.getEntitiesWithinCylinder(this.width / 2, this.posX, this.posY, this.posZ, this.height, this.world, EntityLivingBase.class);
            for (EntityLivingBase target : targets) {
                if (isValidTarget(target)) {
                    if (!target.isPotionActive(WNGPotions.napalm)) {
                        target.addPotionEffect(new PotionEffect(WNGPotions.napalm, WNGSpells.napalm.getProperty("effect_duration").intValue(), SpellBuff.getStandardBonusAmplifier(damageMultiplier)));
                    }
                    if (target.isBurning()) {
                        burningEntity = true;
                    }
                }
            }   if (burningEntity) {
            this.explode();
            }
        } else if (this.rand.nextInt(15) == 0) {
            double radius = this.rand.nextDouble() * this.width / 2;
            float angle = this.rand.nextFloat() * 3.1415927F * 2.0F;
            ParticleBuilder.create(ParticleBuilder.Type.DARK_MAGIC).pos(this.posX + radius * (double) MathHelper.cos(angle), this.posY, this.posZ + radius * (double)MathHelper.sin(angle)).clr(0xD86631).spawn(this.world);
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source.isFireDamage()) {
            this.explode();
        }
        return false;
    }

    public void explode() {
        if (!world.isRemote) {
            this.world.newExplosion(this, this.posX, this.posY, this.posZ, this.width, false, false);
            this.setDead();
            if (EntityUtils.canDamageBlocks(null, world)) {
                double radius = this.width / 2 + 1;
                for (int i = -((int) radius); i <= (int) radius; ++i) {
                    for (int j = -((int) radius); j <= (int) radius; ++j) {
                        BlockPos pos = (new BlockPos(this)).add(i, 0, j);
                        Integer y = BlockUtils.getNearestSurface(world, new BlockPos(pos), EnumFacing.UP, (int) radius, true, BlockUtils.SurfaceCriteria.NOT_AIR_TO_AIR);
                        if (y != null) {
                            pos = new BlockPos(pos.getX(), y, pos.getZ());
                            double dist = this.getPositionVector().distanceTo(new Vec3d(this.getPositionVector().x + (double) i, (double) y, this.getPositionVector().z + (double) j));
                            if ((double) world.rand.nextInt((int) (dist * 2.0) + 1) < radius && dist < radius && BlockUtils.canPlaceBlock(null, world, pos)) {
                                world.setBlockState(pos, Blocks.FIRE.getDefaultState());
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean isInRangeToRenderDist(double distance) {
        return true;
    }
}

