package com.ipdnaeip.wizardrynextgeneration.util;

import electroblob.wizardry.entity.ICustomHitbox;
import electroblob.wizardry.util.EntityUtils;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public final class WNGEntityUtils {

    private WNGEntityUtils() {
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


}
