package com.ipdnaeip.wizardrynextgeneration.util;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import electroblob.wizardry.entity.ICustomHitbox;
import electroblob.wizardry.spell.Spell;
import electroblob.wizardry.util.EntityUtils;
import electroblob.wizardry.util.MagicDamage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.CombatRules;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;

public final class WNGUtils {

    private WNGUtils() {
    }

    public static String registerTag(String key) {
        return WizardryNextGeneration.MODID + "." + key;
    }

    public static List<RayTraceResult> rayTraceMultiple2(World world, Vec3d origin, Vec3d endpoint, float aimAssist, boolean hitLiquids, boolean ignoreUncollidables, boolean returnLastUncollidable, boolean penetratesBlocks, Class<? extends Entity> entityType, Predicate<? super Entity> filter) {
        float borderSize = 1.0F + aimAssist;
        AxisAlignedBB searchVolume = (new AxisAlignedBB(origin.x, origin.y, origin.z, endpoint.x, endpoint.y, endpoint.z)).grow(borderSize, borderSize, borderSize);
        List<Entity> entities = world.getEntitiesWithinAABB(entityType, searchVolume);
        entities.removeIf(filter);
        List<RayTraceResult> result = new ArrayList<>();
        RayTraceResult rayTraceResult = world.rayTraceBlocks(origin, endpoint, hitLiquids, ignoreUncollidables, returnLastUncollidable);
        if (rayTraceResult != null && !penetratesBlocks) {
            endpoint = rayTraceResult.hitVec;
        }
        Vec3d intercept = null;
        for (Entity entity : entities) {
            float fuzziness = EntityUtils.isLiving(entity) ? aimAssist : 0.0F;
            float entityBorderSize;
            if (entity instanceof ICustomHitbox) {
                intercept = ((ICustomHitbox) entity).calculateIntercept(origin, endpoint, fuzziness);
            } else {
                AxisAlignedBB entityBounds = entity.getEntityBoundingBox();
                entityBorderSize = entity.getCollisionBorderSize();
                if (entityBorderSize != 0.0F) {
                    entityBounds = entityBounds.grow(entityBorderSize, entityBorderSize, entityBorderSize);
                }
                if (fuzziness != 0.0F) {
                    entityBounds = entityBounds.grow(fuzziness, fuzziness, fuzziness);
                }
                RayTraceResult hit = entityBounds.calculateIntercept(origin, endpoint);
                if (hit != null) {
                    intercept = hit.hitVec;
                }
            }
            if (intercept != null) {
                entityBorderSize = (float)intercept.distanceTo(origin);
                float closestHitDistance = (float)endpoint.distanceTo(origin);
                if (entityBorderSize < closestHitDistance) {
                    RayTraceResult entityResult = new RayTraceResult(entity, intercept);
                    result.add(entityResult);
                }
            }
        }
        return result;
    }
    public static List<RayTraceResult> rayTraceMultiple(World world, Vec3d origin, Vec3d endpoint, float aimAssist, boolean hitLiquids, boolean ignoreUncollidables, boolean returnLastUncollidable, boolean penetratesBlocks, Class<? extends Entity> entityType, Predicate<? super Entity> filter) {
        float borderSize = 1.0F + aimAssist;
        AxisAlignedBB searchVolume = (new AxisAlignedBB(origin.x, origin.y, origin.z, endpoint.x, endpoint.y, endpoint.z)).grow(borderSize, borderSize, borderSize);
        List<Entity> entities = world.getEntitiesWithinAABB(entityType, searchVolume);
        entities.removeIf(filter);
        List<RayTraceResult> result = new ArrayList<>();
        RayTraceResult rayTraceResult = world.rayTraceBlocks(origin, endpoint, hitLiquids, ignoreUncollidables, returnLastUncollidable);
        if (rayTraceResult != null && !penetratesBlocks) {
            endpoint = rayTraceResult.hitVec;
        }
        Vec3d intercept = null;
        for (Entity entity : entities) {
            float fuzziness = EntityUtils.isLiving(entity) ? aimAssist : 0.0F;
            float currentHitDistance;
            if (entity instanceof ICustomHitbox) {
                intercept = ((ICustomHitbox) entity).calculateIntercept(origin, endpoint, fuzziness);
            } else {
                AxisAlignedBB entityBounds = entity.getEntityBoundingBox();
                currentHitDistance = entity.getCollisionBorderSize();
                if (currentHitDistance != 0.0F) {
                    entityBounds = entityBounds.grow(currentHitDistance, currentHitDistance, currentHitDistance);
                }
                if (fuzziness != 0.0F) {
                    entityBounds = entityBounds.grow(fuzziness, fuzziness, fuzziness);
                }
                RayTraceResult hit = entityBounds.calculateIntercept(origin, endpoint);
                if (hit != null) {
                    intercept = hit.hitVec;
                }
            }
            if (intercept != null) {
                currentHitDistance = (float)intercept.distanceTo(origin);
                float closestHitDistance = (float)endpoint.distanceTo(origin);
                if (currentHitDistance < closestHitDistance) {
                    RayTraceResult entityResult = new RayTraceResult(entity, intercept);
                    result.add(entityResult);
                }
            }
        }
        return result;
    }

    public static boolean hasSunlight(Entity entity) {
        return entity.world.isDaytime() && entity.world.canSeeSky(new BlockPos(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ));
    }

    public static boolean hasMoonlight(Entity entity) {
        return !entity.world.isDaytime() && entity.world.canSeeSky(new BlockPos(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ));
    }

    /**
     * Set the damagesource to bypass armor
     * If subtract is true, the penetration value will be subtracted from the entity's armor
     * If subtract is false, the penetration value will ignore a percentage of the entity's armor
     *
     * @param entity The entity whose armor is being penetrated
     * @param damage The damage being modified
     * @param penetration The penetration value
     * @param subtract If true, the penetration value is subtracted from the entity's total armor, otherwise it penetrates a percentage of the entity's armor
     * @return The damage amount post armor and penetration calculations
     */
    public static float penetrationDamage(EntityLivingBase entity, float damage, float penetration, boolean subtract) {
        float armor = (float)entity.getTotalArmorValue();
        if (subtract) {
            armor = penetration < armor ? armor - penetration : 0f;
        }
        else {
            armor = penetration < 1f ? armor -= armor * penetration : 0;
        }
        damage = CombatRules.getDamageAfterAbsorb(damage, armor, (float)entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
        return damage;
    }

    /**
     * Shorthand method to do instance check and sideonly checks for player messages
     */
    public static void sendMessage(Entity player, String translationKey, boolean actionBar, Object... args) {
        if (player instanceof EntityPlayer && !player.world.isRemote) {
            ((EntityPlayer)player).sendStatusMessage(new TextComponentTranslation(translationKey, args), actionBar);
        }
    }

    /**
     * Checks if an entity is immune to the DamageType and sends the player the spell resist message.
     *
     * @param caster     The caster (only sends the message if the caster is a player).
     * @param target     The target Entity.
     * @param damageType The DamageType to check for.
     * @param spell      The Spell being used.
     * @param ticksInUse The tick of the spell, used for not spamming with continuous spells.
     * @return whether the target can be damaged by the MagicDamage.
     */
    public static boolean canMagicDamageEntity(EntityLivingBase caster, Entity target, MagicDamage.DamageType damageType, Spell spell, int ticksInUse) {
        if (MagicDamage.isEntityImmune(damageType, target)) {
            if (!spell.isContinuous || ticksInUse == 1) {
                WNGUtils.sendMessage(caster, "spell.resist", true, target.getName(), spell.getNameForTranslationFormatted());
            }
            return false;
        }
        return true;
    }

    /**
     * Modified from SpellBuff to allow for continuous buff spells to return the nearest entity to a dispenser.
     *
     * @param world The world of the affected entity.
     * @param x The x coordinate in front of the dispenser.
     * @param y The y coordinate in front of the dispenser.
     * @param z The z coordinate in front of the dispenser.
     * @return the nearest entity to the dispenser.
     */
    @Nullable
    public static EntityLivingBase dispenseNearestEntity(World world, double x, double y, double z) {
        // Gets a 1x1x1 bounding box corresponding to the block in front of the dispenser
        AxisAlignedBB boundingBox = new AxisAlignedBB(new BlockPos(x, y, z));
        List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, boundingBox);
        float distance = -1;
        EntityLivingBase nearestEntity = null;
        // Finds the nearest entity within the bounding box
        for(EntityLivingBase entity : entities){
            float newDistance = (float)entity.getDistance(x, y, z);
            if(distance == -1 || newDistance < distance){
                distance = newDistance;
                nearestEntity = entity;
            }
        }
        return nearestEntity;
    }

}
