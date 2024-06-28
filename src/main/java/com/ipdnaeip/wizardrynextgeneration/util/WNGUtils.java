package com.ipdnaeip.wizardrynextgeneration.util;

import com.google.common.collect.Lists;
import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import electroblob.wizardry.entity.ICustomHitbox;
import electroblob.wizardry.util.EntityUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.CombatRules;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.*;
import java.util.function.Predicate;

public final class WNGUtils {

    private WNGUtils() {
    }

    public static String registerTag(String key) {
        return WizardryNextGeneration.MODID + "." + key;
    }

    public static boolean isEntityStill(Entity entity) {
        return entity.world.isRemote && entity.motionX == 0.0 && entity.motionZ == 0.0 && entity.onGround;
    }

    public static boolean isEntityMoving(Entity entity) {
        return entity.world.isRemote && (entity.motionX != 0.0 || entity.motionZ != 0.0 || !entity.onGround);
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

    public static boolean hasSunlight(World world, Entity entity) {
        return world.isDaytime() && world.canSeeSky(new BlockPos(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ));
    }


    public static boolean hasMoonlight(World world, Entity entity) {
        return !world.isDaytime() && world.canSeeSky(new BlockPos(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ));
    }

    /**
     * Set the damagesource to bypass armor
     * If subtract is true, the penetration value will be subtracted from the entity's armor
     * If subtract is false, the penetration value will ignore a percentage of the entity's armor
     *
     * @param entity The entity whose armor is being penetrated
     * @param damage The damage being modified
     * @param penetration The penetration value
     * @param subtract If true, the penetration value is subtracted from the entity's total armor, otherwise the it penetrates a percentage of the entity's armor
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

    public static boolean doesPlayerHaveLessThanItem(EntityPlayer player, Item item, int amount) {
        int check = 0;
        for (ItemStack stack : player.inventory.mainInventory) {
            if (stack.getItem() == item) check++;
        }
        for (ItemStack stack : player.inventory.armorInventory) {
            if (stack.getItem() == item) check++;
        }
        for (ItemStack stack : player.inventory.offHandInventory) {
            if (stack.getItem() == item) check++;
        }
        if (check < amount) {
            return true;
        }
        return false;
    }

    public static int playerExcessItems(EntityPlayer player, Item item, int amount) {
        int check = 0;
        for (ItemStack stack : player.inventory.mainInventory) {
            if (stack.getItem() == item) check++;
        }
        for (ItemStack stack : player.inventory.armorInventory) {
            if (stack.getItem() == item) check++;
        }
        for (ItemStack stack : player.inventory.offHandInventory) {
            if (stack.getItem() == item) check++;
        }
        return amount - check;
    }

    /**
     * Shorthand method to do instance check and sideonly checks for player messages
     */
    public static void sendMessage(Entity player, String translationKey, boolean actionBar, Object... args) {
        if (player instanceof EntityPlayer && !player.world.isRemote) {
            ((EntityPlayer) player).sendStatusMessage(new TextComponentTranslation(translationKey, args), actionBar);
        }
    }

}
