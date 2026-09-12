package com.ipdnaeip.wizardrynextgeneration.entity.ai;

import com.ipdnaeip.wizardrynextgeneration.spell.Domesticate;
import electroblob.wizardry.util.EntityUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.world.World;

/**
 * AI class based on {@link EntityAIBase}, which makes summons follow their owners
 */
public class EntityAIAnimalFollowPlayer extends EntityAIBase {
    private final EntityCreature creature;
    private EntityPlayer owner;
    World world;
    private final double followSpeed;
    private final PathNavigate petPathfinder;
    private int timeToRecalcPath;
    float maxDist;
    float minDist;
    private float oldWaterCost;

    public EntityAIAnimalFollowPlayer(EntityAnimal summonedCreature, double followSpeedIn, float minDistIn, float maxDistIn) {
        this.creature = summonedCreature;
        this.world = summonedCreature.world;
        this.followSpeed = followSpeedIn;
        this.petPathfinder = summonedCreature.getNavigator();
        this.minDist = minDistIn;
        this.maxDist = maxDistIn;
        this.setMutexBits(3);
        if (!(summonedCreature.getNavigator() instanceof PathNavigateGround) && !(summonedCreature.getNavigator() instanceof PathNavigateFlying)) {
            throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
        }
    }

    public boolean shouldExecute() {
        if (!this.creature.getEntityData().hasKey(Domesticate.DOMESTICATE_CASTER)) {
            return false;
        }
        Entity entity = null;
        if (this.owner == null) {
            entity = EntityUtils.getEntityByUUID(this.creature.world, this.creature.getEntityData().getUniqueId(Domesticate.DOMESTICATE_CASTER));
        }
        if (!(entity instanceof EntityPlayer)) {
            return false;
        }
        this.owner = (EntityPlayer)entity;
        if (this.owner.isSpectator()) {
            return false;
        } else return !(this.creature.getDistanceSq(this.owner) < (double)(this.minDist * this.minDist));
    }

    public boolean shouldContinueExecuting() {
        return !this.petPathfinder.noPath() && this.creature.getDistanceSq(this.owner) > (double) (this.maxDist * this.maxDist);
    }

    public void startExecuting() {
        this.timeToRecalcPath = 0;
        this.oldWaterCost = this.creature.getPathPriority(PathNodeType.WATER);
        this.creature.setPathPriority(PathNodeType.WATER, 0.0F);
    }

    public void resetTask() {
        this.owner = null;
        this.petPathfinder.clearPath();
        this.creature.setPathPriority(PathNodeType.WATER, this.oldWaterCost);
    }

    public void updateTask() {
        this.creature.getLookHelper().setLookPositionWithEntity(this.owner, 10.0F, (float) this.creature.getVerticalFaceSpeed());
        if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = 10;
            this.petPathfinder.tryMoveToEntityLiving(this.owner, this.followSpeed);
        }
    }

}