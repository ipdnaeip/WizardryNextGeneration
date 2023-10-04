package com.ipdnaeip.wizardrynextgeneration.entity.ai;


import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.util.math.MathHelper;

public class EntityFlightHelper extends EntityMoveHelper {


    public EntityFlightHelper(EntityLiving entityLiving) {
        super(entityLiving);
    }

    @Override
    public void onUpdateMoveHelper() {
        if (this.action == EntityMoveHelper.Action.MOVE_TO) {
            double d0 = this.posX - this.entity.posX;
            double d1 = this.posY - this.entity.posY;
            double d2 = this.posZ - this.entity.posZ;
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;
            d3 = MathHelper.sqrt(d3);

            if (d3 < this.entity.getEntityBoundingBox().getAverageEdgeLength()) {
                this.action = EntityMoveHelper.Action.WAIT;
                this.entity.motionX = 0;
                this.entity.motionY = 0;
                this.entity.motionZ = 0;
            }
            else {
                this.entity.motionX += d0 / d3 * 0.05D * this.speed;
                this.entity.motionY += d1 / d3 * 0.05D * this.speed;
                this.entity.motionZ += d2 / d3 * 0.05D * this.speed;

                if (this.entity.getAttackTarget() == null) {
                    this.entity.rotationYaw = -((float)MathHelper.atan2(this.entity.motionX, this.entity.motionZ)) * (180F / (float)Math.PI);
                }
                else {
                    double d4 = this.entity.getAttackTarget().posX - this.entity.posX;
                    double d5 = this.entity.getAttackTarget().posZ - this.entity.posZ;
                    this.entity.rotationYaw = -((float)MathHelper.atan2(d4, d5)) * (180F / (float)Math.PI);
                }
                this.entity.renderYawOffset = this.entity.rotationYaw;
            }
        }
    }
}
