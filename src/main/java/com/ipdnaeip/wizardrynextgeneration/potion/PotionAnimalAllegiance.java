package com.ipdnaeip.wizardrynextgeneration.potion;

import com.google.common.base.Predicate;
import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.entity.ai.EntityAIAnimalAttackMelee;
import com.ipdnaeip.wizardrynextgeneration.entity.ai.EntityAIAnimalNearestAttackTarget;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGConstants;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.Wizardry;
import electroblob.wizardry.entity.living.ISummonedCreature;
import electroblob.wizardry.potion.PotionMagicEffect;
import electroblob.wizardry.util.EntityUtils;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber
public class PotionAnimalAllegiance extends PotionMagicEffect {

    public PotionAnimalAllegiance() {
        super(false, 0xFFE293, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/animal_alliance.png"));
        this.setPotionName("potion." + WizardryNextGeneration.MODID + ":animal_allegiance");
    }

    @SubscribeEvent
    public static void onLivingSetAttackTargetEvent(LivingSetAttackTargetEvent event) {
        if (event.getEntityLiving().isPotionActive(WNGPotions.animal_allegiance) && event.getEntityLiving() instanceof EntityLiving && event.getTarget() != null) {
            EntityLiving entity = (EntityLiving)event.getEntityLiving();
            if (event.getTarget() == EntityUtils.getEntityByUUID(entity.world, entity.getEntityData().getUniqueId(WNGConstants.ANIMAL_ALLEGIANCE_CASTER))) {
                ((EntityLiving) event.getEntityLiving()).setAttackTarget(null);
            }
        }
    }

    @SubscribeEvent
    public static void onPotionAddedEvent(PotionEvent.PotionAddedEvent event) {
        if (event.getEntityLiving() instanceof EntityAnimal) {
            EntityAnimal entity = (EntityAnimal)event.getEntityLiving();
            float attackDamage = (float)(entity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
            attackDamage *= entity.getEntityData().hasKey(WNGConstants.ANIMAL_ATTACK_MULTIPLIER) ? entity.getEntityData().getFloat(WNGConstants.ANIMAL_ATTACK_MULTIPLIER) : 1f;
            entity.tasks.addTask(0, new EntityAIAnimalAttackMelee(entity, 1.5, attackDamage));
            entity.targetTasks.addTask(0, new EntityAIAnimalNearestAttackTarget(entity, EntityLivingBase.class, 0, false, true, getNewTargetSelector((EntityLivingBase)(EntityUtils.getEntityByUUID(entity.world, entity.getEntityData().getUniqueId(WNGConstants.ANIMAL_ALLEGIANCE_CASTER))))));
        }
    }

    @SubscribeEvent
    public static void onPotionExpiryEvent(PotionEvent.PotionExpiryEvent event) {
        if (event.getEntityLiving() instanceof EntityAnimal) {
            EntityAnimal entity = (EntityAnimal) event.getEntityLiving();
            List<EntityAITasks.EntityAITaskEntry> tasksToRemove = new ArrayList<>();
            for (EntityAITasks.EntityAITaskEntry entityAITaskEntry : entity.tasks.taskEntries) {
                if (entityAITaskEntry.action instanceof EntityAIAnimalAttackMelee || entityAITaskEntry.action instanceof EntityAIAnimalNearestAttackTarget) {
                    tasksToRemove.add(entityAITaskEntry);
                }
            }
            // Remove the tasks after iteration
            for (EntityAITasks.EntityAITaskEntry taskEntry : tasksToRemove) {
                entity.tasks.removeTask(taskEntry.action);
            }
        }
    }

    @SubscribeEvent
    public static void onPotionRemoveEvent(PotionEvent.PotionRemoveEvent event) {
        if (event.getEntityLiving() instanceof EntityAnimal) {
            EntityAnimal entity = (EntityAnimal)event.getEntityLiving();
            List<EntityAITasks.EntityAITaskEntry> tasksToRemove = new ArrayList<>();
            for (EntityAITasks.EntityAITaskEntry entityAITaskEntry : entity.tasks.taskEntries) {
                if (entityAITaskEntry.action instanceof EntityAIAnimalAttackMelee || entityAITaskEntry.action instanceof EntityAIAnimalNearestAttackTarget) {
                    tasksToRemove.add(entityAITaskEntry);
                }
            }
            // Remove the tasks after iteration
            for (EntityAITasks.EntityAITaskEntry taskEntry : tasksToRemove) {
                entity.tasks.removeTask(taskEntry.action);
            }
        }
    }

    private static Predicate<Entity> getNewTargetSelector(@Nullable EntityLivingBase caster) {
        return entity -> {
            if (caster == null) {
                return entity instanceof IMob && !entity.isInvisible();
            } else {
                return entity != caster && entity instanceof IMob || entity instanceof ISummonedCreature || entity instanceof EntityLiving && ((EntityLiving) entity).getAttackTarget() == caster || Arrays.asList(Wizardry.settings.summonedCreatureTargetsWhitelist).contains(EntityList.getKey(entity.getClass())) && !Arrays.asList(Wizardry.settings.summonedCreatureTargetsBlacklist).contains(EntityList.getKey(entity.getClass()));
            }
        };
    }
}
