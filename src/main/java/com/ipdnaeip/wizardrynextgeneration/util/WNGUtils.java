package com.ipdnaeip.wizardrynextgeneration.util;

import com.google.common.collect.Lists;
import electroblob.wizardry.entity.ICustomHitbox;
import electroblob.wizardry.util.EntityUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.*;
import java.util.function.Predicate;

public final class WNGUtils {

    private WNGUtils() {
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

    public static <T extends Entity> List<T> getEntitiesWithinCylinder(double radius, double x, double y, double z, double height, World world, Class<T> entityType) {
        AxisAlignedBB aabb = new AxisAlignedBB(x - radius, y, z - radius, x + radius, y + height, z + radius);
        List<T> entityList = world.getEntitiesWithinAABB(entityType, aabb);
        for(T entity : entityList) {
            if (entity.getDistance(x, entity.posY, z) > radius) {
                entityList.remove(entity);
                break;
            }
        }
        return entityList;
    }

    public static boolean hasSunlight(World world, Entity entity) {
        return world.isDaytime() && world.canSeeSky(new BlockPos(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ));
    }


    public static boolean hasMoonlight(World world, Entity entity) {
        return !world.isDaytime() && world.canSeeSky(new BlockPos(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ));
    }

    public static Potion getRandomPotionEffect(boolean all, boolean isBad) {
        List<Potion> potionList = ForgeRegistries.POTIONS.getValues();
        List<Potion> potionCuratedList = Lists.newArrayList();
        for (Potion potion : potionList) {
            if (potion.getRegistryName().getNamespace().matches("minecraft")) {
                if (all) {
                    potionCuratedList.add(potion);
                } else if (isBad && potion.isBadEffect()) {
                    potionCuratedList.add(potion);
                } else if (!isBad && !potion.isBadEffect()) {
                    potionCuratedList.add(potion);
                }
            }
        }
        return potionCuratedList.get((int)(Math.random() * (potionCuratedList.size() - 1)));
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

}
