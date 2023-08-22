package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.registry.WizardryPotions;
import electroblob.wizardry.spell.SpellRay;
import electroblob.wizardry.util.EntityUtils;
import electroblob.wizardry.util.MagicDamage;
import electroblob.wizardry.util.ParticleBuilder;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
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
        //checks the vector of your position relative to the target's position, checks the eyesight vector of the target and sees if you're within its vision range
        Vec3d casterVecTemp = new Vec3d(target.posX - caster.posX, target.posY - caster.posY, target.posZ - caster.posZ);
        Vec3d casterVec = casterVecTemp.normalize();
        Vec3d targetVec = target.getLookVec();
        double angle = Math.acos(casterVec.dotProduct(targetVec));
        //check if you're out of the target's vision range or if the target has not noticed you
        if ((target instanceof EntityMob && ((EntityMob) target).getAttackTarget() != caster) || angle < 1) {
            damage = damage * getProperty(MULTIPLIER_TAG).floatValue();
        }
        target.attackEntityFrom(MagicDamage.causeDirectMagicDamage(caster, MagicDamage.DamageType.WITHER), damage);
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

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.spell_book_wng || item == WNGItems.scroll_wng;
    }
}
