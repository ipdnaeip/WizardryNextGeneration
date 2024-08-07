package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.util.WNGUtils;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.spell.SpellRay;
import electroblob.wizardry.util.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;


public class Assassinate extends SpellRay {

    public static final String MULTIPLIER_TAG = "unseen_multiplier";

    public Assassinate() {
        super(WizardryNextGeneration.MODID, "assassinate", SpellActions.POINT, false);
        this.aimAssist(0.3F);
        this.addProperties(DAMAGE, MULTIPLIER_TAG);
    }

    @Override
    protected boolean onEntityHit(World world, Entity target, Vec3d hit, EntityLivingBase caster, Vec3d origin, int ticksInUse, SpellModifiers modifiers) {
        float damage = getProperty(DAMAGE).floatValue() * modifiers.get(SpellModifiers.POTENCY);
        MagicDamage.DamageType damageType = MagicDamage.DamageType.WITHER;
        if (WNGUtils.canMagicDamageEntity(caster, target, damageType, this, ticksInUse)) {
            if (canAssassinate(caster, target)) {
                damage = damage * getProperty(MULTIPLIER_TAG).floatValue();
            }
            target.attackEntityFrom(MagicDamage.causeDirectMagicDamage(caster, damageType), damage);
        }
        return true;
    }

    @Override
    protected boolean onBlockHit(World world, BlockPos pos, EnumFacing side, Vec3d hit, EntityLivingBase caster, Vec3d origin, int ticksInUse, SpellModifiers modifiers) {
        return false;
    }

    @Override
    protected boolean onMiss(World world, EntityLivingBase caster, Vec3d origin, Vec3d direction, int ticksInUse, SpellModifiers modifiers) {
        return false;
    }

    @Override
    public boolean canBeCastBy(TileEntityDispenser dispenser) {
        return false;
    }

    public static boolean canAssassinate(EntityLivingBase caster, Entity target) {
        //checks the vector of your position relative to the target's position, checks the eyesight vector of the target and sees if you're within its vision range
        Vec3d casterVecTemp = new Vec3d(target.posX - caster.posX, target.posY - caster.posY, target.posZ - caster.posZ);
        Vec3d casterVec = casterVecTemp.normalize();
        Vec3d targetVec = target.getLookVec();
        double angle = Math.acos(casterVec.dotProduct(targetVec));
        //check if you're out of the target's vision range or if the target has not noticed you
        return (target instanceof IMob && target instanceof EntityLiving && ((EntityLiving) target).getAttackTarget() != caster) || angle < 1;
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.SPELL_BOOK_WNG || item == WNGItems.SCROLL_WNG;
    }
}
