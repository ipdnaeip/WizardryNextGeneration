package com.ipdnaeip.wizardrynextgeneration.spell;


import com.ipdnaeip.wizardrynextgeneration.entity.construct.EntityLivingMagicConstruct;
import com.ipdnaeip.wizardrynextgeneration.entity.construct.EntityLivingScaledConstruct;
import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.spell.Spell;
import electroblob.wizardry.util.BlockUtils;
import electroblob.wizardry.util.SpellModifiers;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpellLivingConstruct<T extends EntityLivingMagicConstruct> extends Spell {
    protected final Function<World, T> constructFactory;
    protected final boolean permanent;
    protected boolean requiresFloor;
    protected boolean allowOverlap;

    public SpellLivingConstruct(String modID, String name, EnumAction action, Function<World, T> constructFactory, boolean permanent) {
        super(modID, name, action, false);
        this.requiresFloor = false;
        this.allowOverlap = false;
        this.constructFactory = constructFactory;
        this.permanent = permanent;
        this.npcSelector((e, o) -> true);
        if (!permanent) {
            this.addProperties("duration");
        }
    }

    @Override
    public boolean requiresPacket() {
        return false;
    }

    @Override
    public boolean canBeCastBy(TileEntityDispenser dispenser) {
        return true;
    }

    public SpellLivingConstruct<T> floor(boolean requiresFloor) {
        this.requiresFloor = requiresFloor;
        return this;
    }

    public SpellLivingConstruct<T> overlap(boolean allowOverlap) {
        this.allowOverlap = allowOverlap;
        return this;
    }

    public boolean cast(World world, EntityPlayer caster, EnumHand hand, int ticksInUse, SpellModifiers modifiers) {
        if (!caster.onGround && this.requiresFloor) {
            return false;
        } else if (!this.spawnConstruct(world, caster.posX, caster.posY, caster.posZ, caster.onGround ? EnumFacing.UP : null, caster, modifiers)) {
            return false;
        } else {
            this.playSound(world, caster, ticksInUse, -1, modifiers);
            return true;
        }
    }

    @Override
    public boolean cast(World world, EntityLiving caster, EnumHand hand, int ticksInUse, EntityLivingBase target, SpellModifiers modifiers) {
        if (target == null || !caster.onGround && this.requiresFloor) {
            return false;
        } else if (!this.spawnConstruct(world, caster.posX, caster.posY, caster.posZ, caster.onGround ? EnumFacing.UP : null, caster, modifiers)) {
            return false;
        } else {
            this.playSound(world, caster, ticksInUse, -1, modifiers);
            return true;
        }
    }

    @Override
    public boolean cast(World world, double x, double y, double z, EnumFacing direction, int ticksInUse, int duration, SpellModifiers modifiers) {
        Integer floor = (int)y;
        if (this.requiresFloor) {
            floor = BlockUtils.getNearestFloor(world, new BlockPos(x, y, z), 1);
            direction = EnumFacing.UP;
        }
        if (floor != null) {
            if (!this.spawnConstruct(world, x, (double)floor, z, direction, null, modifiers)) {
                return false;
            } else {
                this.playSound(world, x - (double)direction.getXOffset(), y - (double)direction.getYOffset(), z - (double)direction.getZOffset(), ticksInUse, duration, modifiers);
                return true;
            }
        } else {
            return false;
        }
    }

    protected boolean spawnConstruct(World world, double x, double y, double z, @Nullable EnumFacing side, @Nullable EntityLivingBase caster, SpellModifiers modifiers) {
        if (!world.isRemote) {
            T construct = this.constructFactory.apply(world);
            construct.setPosition(x, y, z);
            construct.setCaster(caster);
            construct.lifetime = this.permanent ? -1 : (int)(this.getProperty("duration").floatValue() * modifiers.get(WizardryItems.duration_upgrade));
            construct.damageMultiplier = modifiers.get("potency");
            if (construct instanceof EntityLivingScaledConstruct) {
                ((EntityLivingScaledConstruct)construct).setSizeMultiplier(modifiers.get(WizardryItems.blast_upgrade));
            }
            this.addConstructExtras(construct, side, caster, modifiers);
            if (!this.allowOverlap && !world.getEntitiesWithinAABB(construct.getClass(), construct.getEntityBoundingBox()).isEmpty()) {
                return false;
            }
            world.spawnEntity(construct);
        }
        return true;
    }

    protected void addConstructExtras(T construct, EnumFacing side, @Nullable EntityLivingBase caster, SpellModifiers modifiers) {
    }
}

