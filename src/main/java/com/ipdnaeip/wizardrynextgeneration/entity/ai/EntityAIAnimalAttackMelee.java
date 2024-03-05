package com.ipdnaeip.wizardrynextgeneration.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;

public class EntityAIAnimalAttackMelee extends EntityAIAttackMelee {

    float attackDamage;

    public EntityAIAnimalAttackMelee(EntityAnimal animal, double speedIn, float attackDamage) {
        super(animal, speedIn, false);
        this.attackDamage = attackDamage;
    }

    @Override
    protected void checkAndPerformAttack(EntityLivingBase enemy, double distToEnemySqr)
    {
        double d0 = this.getAttackReachSqr(enemy);

        if (distToEnemySqr <= d0 && this.attackTick <= 0) {
            this.attackTick = 20;
            this.attacker.swingArm(EnumHand.MAIN_HAND);
            enemy.attackEntityFrom(DamageSource.causeMobDamage(this.attacker), this.attackDamage);
        }
    }

}
