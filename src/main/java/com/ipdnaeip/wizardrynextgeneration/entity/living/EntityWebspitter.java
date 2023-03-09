package com.ipdnaeip.wizardrynextgeneration.entity.living;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import electroblob.wizardry.entity.living.EntityIceWraith;
import electroblob.wizardry.registry.Spells;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

import javax.annotation.Nullable;
import java.util.Random;

public class EntityWebspitter extends EntitySpider {

    private static final ResourceLocation LOOT_TABLE = new ResourceLocation(WizardryNextGeneration.MODID, "entities/webspitter");
    public EntityWebspitter(World worldIn)
    {
        super(worldIn);
        this.setSize(1.4F, 0.9F);
    }

    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(4, new EntityWebspitter.AICobwebAttack(this));
    }

    protected float getSoundPitch()
    {
        return super.getSoundPitch() * 0.75F;
    }

    public boolean getCanSpawnHere()
    {
        BlockPos blockpos = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);
        if (blockpos.getY() >= this.world.getSeaLevel() || world.canSeeSky(blockpos))
        {
            return false;
        }
    return true;
    }

    @Override
    protected ResourceLocation getLootTable() {
        return LOOT_TABLE;
    }

    static class AICobwebAttack extends EntityAIBase {
        private final EntityWebspitter webspitter;
        private int attackTime = 0;

        public AICobwebAttack(EntityWebspitter webspitter) {
            this.webspitter = webspitter;
        }

        public boolean shouldExecute() {
            EntityLivingBase entitylivingbase = this.webspitter.getAttackTarget();
            return entitylivingbase != null && entitylivingbase.isEntityAlive();
        }
        public void updateTask() {
            this.attackTime--;
            EntityLivingBase entitylivingbase = this.webspitter.getAttackTarget();
            if (entitylivingbase != null) {
                double d0 = this.webspitter.getDistanceSq(entitylivingbase);
                if (d0 > 16 && d0 < 256.0) {
                    if (this.attackTime <= 0) {
                        Spells.cobwebs.cast(this.webspitter.world, this.webspitter, EnumHand.MAIN_HAND, 0, entitylivingbase, new SpellModifiers());
                        this.attackTime = 200;
                    }
                }
                super.updateTask();
            }
        }
    }
}
