package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.spell.SpellRay;
import electroblob.wizardry.util.EntityUtils;
import electroblob.wizardry.util.MagicDamage;
import electroblob.wizardry.util.ParticleBuilder;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;


public class Equality extends SpellRay {

    public Equality() {
        super(WizardryNextGeneration.MODID, "equality", SpellActions.POINT, false);
        this.aimAssist(0.3F);
        this.soundValues(1F, 0.6F, 0.1F);
        this.addProperties();
    }
    @Override
    protected boolean onEntityHit(World world, Entity target, Vec3d hit, EntityLivingBase caster, Vec3d origin, int ticksInUse, SpellModifiers modifiers) {
        if (target instanceof EntityLivingBase) {
            EntityLivingBase enemy = (EntityLivingBase)target;
            if (caster != null) {
                //l = tC - (tM/cM) * cC)
                float low_health = enemy.getHealth() - (enemy.getMaxHealth() / caster.getMaxHealth() * caster.getHealth());
                //float high_health = (float)Math.pow(enemy.getHealth() - (enemy.getMaxHealth() / caster.getMaxHealth() * caster.getHealth()), 0.65);
                //float high_health = (float)Math.sqrt(enemy.getHealth() - (enemy.getMaxHealth() / caster.getMaxHealth() * caster.getHealth()));
                float high_health = Math.min(enemy.getHealth() - (enemy.getMaxHealth() / caster.getMaxHealth() * caster.getHealth()), (caster.getMaxHealth() - caster.getHealth()) * (1 + modifiers.get("potency")));
                if (enemy.getHealth() / enemy.getMaxHealth() <= caster.getHealth() / caster.getMaxHealth() || caster.getHealth() == caster.getMaxHealth()) {
                    if (!world.isRemote && caster instanceof EntityPlayer) {
                        ((EntityPlayer) caster).sendStatusMessage(new TextComponentTranslation("spell." + this.getUnlocalisedName() + ".no_effect"), true);
                    }
                    return false;
                }
                if (enemy.getMaxHealth() <= caster.getMaxHealth()) {
                    this.soundValues(1F, 0.6F, 0.1F);
                    EntityUtils.attackEntityWithoutKnockback(enemy, MagicDamage.causeDirectMagicDamage(caster, MagicDamage.DamageType.RADIANT), low_health);
                }
                if (enemy.getMaxHealth() > caster.getMaxHealth()) {
                    if (!world.isRemote && caster instanceof EntityPlayer) {
                        ((EntityPlayer) caster).sendStatusMessage(new TextComponentTranslation("spell." + this.getUnlocalisedName() + ".high_health"), true);
                    }
                    this.soundValues(1F, 0.4F, 0.1F);
                    EntityUtils.attackEntityWithoutKnockback(enemy, MagicDamage.causeDirectMagicDamage(caster, MagicDamage.DamageType.RADIANT), high_health);
                }
            }
            return true;
        }
        return false;
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
    protected void spawnParticle(World world, double x, double y, double z, double vx, double vy, double vz) {
        ParticleBuilder.create(ParticleBuilder.Type.SPARKLE).pos(x, y, z).vel(0.0, 0.1, 0.0).clr(1.0F, 1.0F, 0.3F).spawn(world);
        ParticleBuilder.create(ParticleBuilder.Type.FLASH).pos(x, y, z).clr(1.0F, 1.0F, 0.3F).spawn(world);
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.spell_book_wng || item == WNGItems.scroll_wng;
    }
}
