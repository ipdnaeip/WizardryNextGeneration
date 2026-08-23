package com.ipdnaeip.wizardrynextgeneration.accessor;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;

//Unsure why I cant use accessors here, causes NoClassDefFoundErrors
public interface AccessorEntityLivingBase {

	//Just seeing if this works
	static AccessorEntityLivingBase get(EntityLivingBase entity)  {
		return (AccessorEntityLivingBase)entity;
	}

/*	static Vec3d getTarget(EntityLivingBase entity) {
		return ((AccessorEntityLivingBase)entity).wizardrynextgeneration$getTarget();
	}*/

	Vec3d wizardrynextgeneration$getTarget();

	void wizardrynextgeneration$setTarget(Vec3d target);

	boolean wizardrynextgeneration$isNaturalHeal();

	void wizardrynextgeneration$setNaturalHeal(boolean isNaturalHeal);

}
