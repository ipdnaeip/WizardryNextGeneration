package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.entity.projectile.EntityFissionBlast;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import electroblob.wizardry.entity.living.ISpellCaster;
import electroblob.wizardry.entity.projectile.EntityMagicArrow;
import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.spell.SpellArrow;
import electroblob.wizardry.util.EntityUtils;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class FissionBlast extends SpellArrow<EntityFissionBlast> {

    public FissionBlast() {
        super(WizardryNextGeneration.MODID, "fission_blast", EntityFissionBlast::new);
        this.addProperties(DAMAGE, "shots");
    }

    @Override
    public boolean cast(World world, EntityPlayer caster, EnumHand hand, int ticksInUse, SpellModifiers modifiers) {
        if (!world.isRemote) {
            for (int i = 0; i < this.getProperty("shots").floatValue() * modifiers.get(WizardryItems.blast_upgrade); i++) {
                EntityFissionBlast projectile = this.arrowFactory.apply(world);
                projectile.aim(caster, this.calculateVelocity(projectile, modifiers, caster.getEyeHeight() - 0.1F));
                projectile.damageMultiplier = modifiers.get("potency");
                this.addArrowExtras(projectile, caster, modifiers);
                world.spawnEntity(projectile);
            }
        }
        this.playSound(world, caster, ticksInUse, -1, modifiers);
        return true;
    }

    @Override
    public boolean cast(World world, EntityLiving caster, EnumHand hand, int ticksInUse, EntityLivingBase target, SpellModifiers modifiers) {
        if (target != null) {
            if (!world.isRemote) {
                for (int i = 0; i < this.getProperty("shots").floatValue() * modifiers.get(WizardryItems.blast_upgrade); i++) {
                    EntityFissionBlast projectile = this.arrowFactory.apply(world);
                    projectile.aim(caster, target, this.calculateVelocity(projectile, modifiers, caster.getEyeHeight() - 0.1F), (float)(Math.random() * 3F) + 3F);
                    projectile.damageMultiplier = modifiers.get("potency");
                    this.addArrowExtras(projectile, caster, modifiers);
                    world.spawnEntity(projectile);
                }
            }
            caster.swingArm(hand);
            this.playSound(world, caster, ticksInUse, -1, modifiers);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean cast(World world, double x, double y, double z, EnumFacing direction, int ticksInUse, int duration, SpellModifiers modifiers) {
        if (!world.isRemote) {
            for (int i = 0; i < this.getProperty("shots").floatValue() * modifiers.get(WizardryItems.blast_upgrade); i++) {
                EntityFissionBlast projectile = this.arrowFactory.apply(world);
                projectile.setPosition(x, y, z);
                Vec3i vec = direction.getDirectionVec();
                projectile.shoot(vec.getX(), vec.getY(), vec.getZ(), this.calculateVelocity(projectile, modifiers, 0.375F), (float)(Math.random() * 3F) + 3F);
                projectile.damageMultiplier = modifiers.get("potency");
                this.addArrowExtras(projectile, null, modifiers);
                world.spawnEntity(projectile);
            }
        }
        this.playSound(world, x - (double)direction.getXOffset(), y - (double)direction.getYOffset(), z - (double)direction.getZOffset(), ticksInUse, duration, modifiers);
        return true;
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.spell_book_wng || item == WNGItems.scroll_wng;
    }
}