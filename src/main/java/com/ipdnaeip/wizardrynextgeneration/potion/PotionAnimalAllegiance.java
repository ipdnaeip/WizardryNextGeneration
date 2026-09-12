package com.ipdnaeip.wizardrynextgeneration.potion;

import com.google.common.base.Predicate;
import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.entity.ai.EntityAIAnimalAttackMelee;
import com.ipdnaeip.wizardrynextgeneration.entity.ai.EntityAIAnimalNearestAttackTarget;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import com.ipdnaeip.wizardrynextgeneration.util.WNGUtils;
import electroblob.wizardry.potion.PotionMagicEffect;
import electroblob.wizardry.util.AllyDesignationSystem;
import electroblob.wizardry.util.EntityUtils;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class PotionAnimalAllegiance extends PotionMagicEffect {

    public static final String ANIMAL_ALLEGIANCE_CASTER = WNGUtils.registerTag("animal_allegiance_caster");
    public static final String ANIMAL_ATTACK_MULTIPLIER = WNGUtils.registerTag("animal_attack_multiplier");

    public PotionAnimalAllegiance() {
        super(false, 0xFFE293, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/animal_alliance.png"));
    }

    public static void addAnimalAllegianceTasks(@Nullable PotionEffect potionEffect, Entity entity) {
        if (entity instanceof EntityCreature && isAnimal((EntityCreature)entity)) {
            EntityCreature animal = (EntityCreature)entity;
            boolean animalAllegianceActive = false;
            if (potionEffect != null) {
                if (potionEffect.getPotion() == WNGPotions.ANIMAL_ALLEGIANCE) {
                    animalAllegianceActive = true;
                }
            } else {
                if (animal.isPotionActive(WNGPotions.ANIMAL_ALLEGIANCE)) {
                    animalAllegianceActive = true;
                }
            }
            if (animalAllegianceActive) {
                //This can be null!
                @Nullable IAttributeInstance iattributeinstance = animal.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
                @SuppressWarnings("ConstantConditions")
                double attackDamage = iattributeinstance == null ? SharedMonsterAttributes.ATTACK_DAMAGE.getDefaultValue() : iattributeinstance.getAttributeValue();
                attackDamage *= animal.getEntityData().hasKey(ANIMAL_ATTACK_MULTIPLIER) ? animal.getEntityData().getFloat(ANIMAL_ATTACK_MULTIPLIER) : 1;
                animal.tasks.addTask(0, new EntityAIAnimalAttackMelee(animal, 1.5, (float)attackDamage));
                animal.targetTasks.addTask(0, new EntityAIAnimalNearestAttackTarget(animal, EntityLivingBase.class, 0, false, true, getNewTargetSelector(animal)));
            }
        }
    }

    public static void removeAnimalAllegianceTasks(@Nullable PotionEffect potionEffect, EntityLivingBase entity) {
        if (entity instanceof EntityCreature && isAnimal((EntityCreature)entity)) {
            if (potionEffect != null && potionEffect.getPotion() == WNGPotions.ANIMAL_ALLEGIANCE) {
                EntityAnimal animal = (EntityAnimal)entity;
                animal.tasks.taskEntries.removeIf(task -> task.action instanceof EntityAIAnimalAttackMelee);
                animal.targetTasks.taskEntries.removeIf(task -> task.action instanceof EntityAIAnimalNearestAttackTarget);
                animal.getEntityData().removeTag(ANIMAL_ALLEGIANCE_CASTER);
                animal.getEntityData().removeTag(ANIMAL_ATTACK_MULTIPLIER);
            }
        }
    }

    //Determines if the animal can be affected
    public static boolean isAnimal(EntityCreature entity) {
        return entity instanceof EntityAnimal;
    }

    //This seems really inefficient, look at fixing
    public static Predicate<Entity> getNewTargetSelector(EntityCreature animal) {
        return target -> {
			Entity owner = EntityUtils.getEntityByUUID(animal.world, animal.getEntityData().getUniqueId(ANIMAL_ALLEGIANCE_CASTER));
			if (target instanceof IMob && AllyDesignationSystem.isValidTarget(owner, target)) {
				return true;
			} else return owner instanceof EntityLivingBase && ((EntityLivingBase)owner).getRevengeTarget() == target;
		};
	}

}
