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
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class Moonlight extends Spell {

    public Moonlight() {
        super(WizardryNextGeneration.MODID, "moonlight", SpellActions.POINT_UP, true);
        this.soundValues(1F, 1.2F, 0.4F);
        addProperties(HEALTH);
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
        if (!WNGUtils.hasMoonlight(world, caster)) {
            if(!world.isRemote) caster.sendStatusMessage(new TextComponentTranslation("spell." + this.getUnlocalisedName() + ".no_moonlight"), true);
            return false;
        }
        //check if the player can be healed and if the moon is out and visible by the player
        if (caster.getHealth() < caster.getMaxHealth() && caster.getHealth() > 0.0F && ticksInUse % 10 == 0 && WNGUtils.hasMoonlight(world, caster)) {
            caster.heal(this.getProperty("health").floatValue() * modifiers.get("potency"));
            this.playSound(world, caster, ticksInUse, -1, modifiers);
            if (world.isRemote) ParticleBuilder.create(ParticleBuilder.Type.BUFF).entity(caster).clr(204, 255, 255).spawn(world);
            return true;
        }
        return false;
    }

    @Override
    public boolean cast(World world, EntityLiving caster, EnumHand hand, int ticksInUse, EntityLivingBase target, SpellModifiers modifiers) {
        if (world.isDaytime() || !world.canSeeSky(new BlockPos(caster.posX, caster.posY + (double)caster.getEyeHeight(), caster.posZ))) {
            return false;
        }
        //check if the player can be healed and if the moon is out and visible by the player
        if (caster.getHealth() < caster.getMaxHealth() && caster.getHealth() > 0.0F && ticksInUse % 10 == 0 && !world.isDaytime() && world.canSeeSky(new BlockPos(caster.posX, caster.posY + (double)caster.getEyeHeight(), caster.posZ))) {
            caster.heal(this.getProperty("health").floatValue() * (1 + modifiers.get("potency")));
            this.playSound(world, caster, ticksInUse, -1, modifiers);
            if (world.isRemote) ParticleBuilder.create(ParticleBuilder.Type.BUFF).entity(caster).clr(204, 255, 255).spawn(world);
            return true;
        }
        return false;
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.spell_book_wng || item == WNGItems.scroll_wng;
    }
}