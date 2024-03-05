package com.ipdnaeip.wizardrynextgeneration.entity.ai;

import com.google.common.base.Predicate;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;

import javax.annotation.Nullable;

public class EntityAIAnimalNearestAttackTarget extends EntityAINearestAttackableTarget {

    public EntityAIAnimalNearestAttackTarget(EntityCreature creature, Class classTarget, int chance, boolean checkSight, boolean onlyNearby, @Nullable final Predicate targetSelector) {
        super(creature, classTarget, chance, checkSight, onlyNearby, targetSelector);
    }
}
