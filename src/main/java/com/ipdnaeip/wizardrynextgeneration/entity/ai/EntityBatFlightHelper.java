package com.ipdnaeip.wizardrynextgeneration.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.util.math.MathHelper;

public class EntityBatFlightHelper extends EntityMoveHelper {


    public EntityBatFlightHelper(EntityLiving entity) {
        super(entity);
    }

    @Override
    public void onUpdateMoveHelper() {
        double flySpeed = this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).getAttributeValue();
        if (this.action == EntityMoveHelper.Action.MOVE_TO) {
            this.action = EntityMoveHelper.Action.WAIT;
            double dx = this.posX - this.entity.posX;
            double dy = this.posY - this.entity.posY;
            double dz = this.posZ - this.entity.posZ;
            double d0 = dx * dx + dy * dy + dz * dz;
            d0 = MathHelper.sqrt(d0);

            if (d0 < this.entity.getEntityBoundingBox().getAverageEdgeLength()) {
                this.entity.setMoveForward(0);
            }
            else {
                this.entity.motionX += dx / d0 * this.speed;
                this.entity.motionY += dy / d0 * this.speed;
                this.entity.motionZ += dz / d0 * this.speed;
                this.entity.rotationYaw = -((float)MathHelper.atan2(this.entity.motionX, this.entity.motionZ)) * (180F / (float)Math.PI);
                this.entity.setMoveForward((float)flySpeed);
            }
        }
    }
}
