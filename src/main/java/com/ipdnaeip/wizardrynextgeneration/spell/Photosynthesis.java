package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.util.WNGUtils;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.spell.Spell;
import electroblob.wizardry.util.ParticleBuilder;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class Photosynthesis extends Spell {

    public Photosynthesis() {
        super(WizardryNextGeneration.MODID, "photosynthesis", SpellActions.POINT_UP, true);
        this.soundValues(1F, 1.0F, 0.4F);
        this.addProperties(HEALTH);
    }

    @Override
    protected SoundEvent[] createSounds() {
        return this.createContinuousSpellSounds();
    }

    @Override
    protected void playSound(World world, EntityLivingBase entity, int ticksInUse, int duration, SpellModifiers modifiers, String... sounds) {
        this.playSoundLoop(world, entity, ticksInUse);
    }

    @Override
    protected void playSound(World world, double x, double y, double z, int ticksInUse, int duration, SpellModifiers modifiers, String... sounds) {
        this.playSoundLoop(world, x, y, z, ticksInUse, duration);
    }

    @Override
    public boolean cast(World world, EntityPlayer caster, EnumHand hand, int ticksInUse, SpellModifiers modifiers) {
        if (world.isRemote && WNGUtils.hasSunlight(caster)) {
            System.out.println("client yes");
        }
        if (!world.isRemote && WNGUtils.hasSunlight(caster)) {
            System.out.println("server yes");
        }
        if (world.isRemote && !WNGUtils.hasSunlight(caster)) {
            System.out.println("client no");
        }
        if (!world.isRemote && !WNGUtils.hasSunlight(caster)) {
            System.out.println("server no");
        }
        if (!WNGUtils.hasSunlight(caster)) {
            WNGUtils.sendMessage(caster, "spell." + this.getUnlocalisedName() + ".no_sunlight", true);
            return false;
        }
        //check if the player can be healed and if the sun is out and visible by the player
        if (caster.getHealth() < caster.getMaxHealth() && caster.getHealth() > 0.0F && ticksInUse % 10 == 0/* && WNGUtils.hasSunlight(caster)*/) {
            caster.heal(this.getProperty(HEALTH).floatValue() * modifiers.get(SpellModifiers.POTENCY));
            this.playSound(world, caster, ticksInUse, -1, modifiers);
            if (world.isRemote) this.spawnParticles(world, caster, modifiers);
            return true;
        }
        return false;
    }

    @Override
    public boolean cast(World world, EntityLiving caster, EnumHand hand, int ticksInUse, EntityLivingBase target, SpellModifiers modifiers) {
        if (!WNGUtils.hasSunlight(caster)) {
            return false;
        }
        //check if the caster can be healed and if the sun is out and visible by the player
        if (caster.getHealth() < caster.getMaxHealth() && caster.getHealth() > 0.0F && ticksInUse % 10 == 0 && WNGUtils.hasSunlight(caster)) {
            caster.heal(this.getProperty(HEALTH).floatValue() * (1 + modifiers.get(SpellModifiers.POTENCY)));
            this.playSound(world, caster, ticksInUse, -1, modifiers);
            if (world.isRemote) this.spawnParticles(world, caster, modifiers);
            return true;
        }
        return false;
    }

    @Override
    public boolean cast(World world, double x, double y, double z, EnumFacing direction, int ticksInUse, int duration, SpellModifiers modifiers) {
        EntityLivingBase entity = WNGUtils.dispenseNearestEntity(world, x, y, z);
        if (entity != null && entity.getHealth() < entity.getMaxHealth() && entity.getHealth() > 0 && ticksInUse % 10 == 0 && WNGUtils.hasSunlight(entity)) {
            entity.heal(this.getProperty(HEALTH).floatValue() * modifiers.get(SpellModifiers.POTENCY));
            if (world.isRemote) this.spawnParticles(world, entity, modifiers);
            this.playSound(world, x - direction.getXOffset(), y - direction.getYOffset(), z - direction.getZOffset(), ticksInUse, duration, modifiers);
            return true;
        }
        return false;
    }

    @Override
    public boolean canBeCastBy(EntityLiving npc, boolean override) {
        return true;
    }

    @Override
    public boolean canBeCastBy(TileEntityDispenser dispenser) {
        return true;
    }

    public void spawnParticles(World world, EntityLivingBase caster, SpellModifiers modifiers){
        for(int i = 0; i < 10; i++){
            double x = caster.posX + world.rand.nextDouble() * 2 - 1;
            double y = caster.posY + caster.getEyeHeight() - 0.5 + world.rand.nextDouble();
            double z = caster.posZ + world.rand.nextDouble() * 2 - 1;
            ParticleBuilder.create(ParticleBuilder.Type.SPARKLE).pos(x, y, z).vel(0, 0.1, 0).clr(0, 170, 0).spawn(world);
        }
        ParticleBuilder.create(ParticleBuilder.Type.BUFF).entity(caster).clr(0, 170, 0).spawn(world);
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.SPELL_BOOK_WNG || item == WNGItems.SCROLL_WNG;
    }
}