package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.spell.SpellRay;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityEvokerFangs;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class Chomp extends SpellRay {

    public Chomp() {
        super(WizardryNextGeneration.MODID, "chomp", SpellActions.POINT, false);
    }

    @Override
    protected boolean onEntityHit(World world, Entity target, Vec3d hit, EntityLivingBase caster, Vec3d origin, int ticksInUse, SpellModifiers modifiers) {
        if (target instanceof EntityLivingBase && target.onGround) {
            spawnFangs(world, caster, modifiers, origin, new Vec3d(hit.x, target.getEntityBoundingBox().minY, hit.z));
        }
        return true;
    }

    @Override
    protected boolean onBlockHit(World world, BlockPos pos, EnumFacing side, Vec3d hit, EntityLivingBase caster, Vec3d origin, int ticksInUse, SpellModifiers modifiers) {
        if (side == EnumFacing.UP) {
            spawnFangs(world, caster, modifiers, origin, hit);
            return true;
        }
        return false;
    }

    @Override
    protected boolean onMiss(World world, EntityLivingBase caster, Vec3d origin, Vec3d direction, int ticksInUse, SpellModifiers modifiers) {
        return false;
    }

    private void spawnFangs(World world, @Nullable EntityLivingBase caster, SpellModifiers modifiers, Vec3d origin, Vec3d hit) {
        Vec3d direction = hit.subtract(origin).normalize();
        float yaw = (float)MathHelper.atan2(direction.z, direction.x);
        EntityEvokerFangs fangs = new EntityEvokerFangs(world, hit.x, hit.y, hit.z, yaw, 0, caster);
        fangs.getEntityData().setFloat("ebwizardryDamageModifier", modifiers.get(SpellModifiers.POTENCY));
        world.spawnEntity(fangs);
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.SPELL_BOOK_WNG || item == WNGItems.SCROLL_WNG;
    }

}
