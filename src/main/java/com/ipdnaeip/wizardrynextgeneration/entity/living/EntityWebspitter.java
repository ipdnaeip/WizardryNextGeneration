package com.ipdnaeip.wizardrynextgeneration.entity.living;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import electroblob.wizardry.Wizardry;
import electroblob.wizardry.registry.Spells;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.*;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.ArrayUtils;

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

    @SubscribeEvent
    public static void onCheckSpawnEvent(LivingSpawnEvent.CheckSpawn event){
        // We have no way of checking if it's a spawner in getCanSpawnHere() so this has to be done here instead
        if(event.getEntityLiving() instanceof EntityWebspitter && !event.isSpawner()){
            if(!ArrayUtils.contains(Wizardry.settings.mobSpawnDimensions, event.getWorld().provider.getDimension()))
                event.setResult(Event.Result.DENY);
        }
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

