package com.ipdnaeip.wizardrynextgeneration.spell;



import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.spell.Spell;
import electroblob.wizardry.util.ParticleBuilder;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class Meditate extends Spell {

    public Meditate() {
        super(WizardryNextGeneration.MODID, "meditate", SpellActions.IMBUE, true);
        this.soundValues(1F, 1.0F, 0.4F);
        addProperties(HEALTH);
    }

    @Override
    public boolean canBeCastBy(TileEntityDispenser dispenser) {
        return false;
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
        System.out.println(caster.motionX + " " + caster.motionZ + " " + caster.onGround);
        if (caster.motionX != 0.0 || caster.motionZ != 0.0 || !caster.onGround) {
            if (!world.isRemote) caster.sendStatusMessage(new TextComponentTranslation("spell." + this.getUnlocalisedName() + ".moving"), true);
            return false;
        }
        //check if the player can be healed and if the player is standing still
        if (caster.getHealth() < caster.getMaxHealth() && caster.getHealth() > 0.0F && ticksInUse % 10 == 0) {
            caster.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 30, 0));
            caster.heal(this.getProperty("health").floatValue() * (1 + modifiers.get("potency")));
            this.playSound(world, caster, ticksInUse, -1, modifiers);
            if (world.isRemote) ParticleBuilder.create(ParticleBuilder.Type.BUFF).entity(caster).clr(170, 250, 250).spawn(world);
            return true;
        }
        return false;
    }

/*    @Override
    public boolean cast(World world, EntityPlayer caster, EnumHand hand, int ticksInUse, SpellModifiers modifiers) {
        //check if the player can be healed and if the player is standing still
        System.out.println(caster.motionX + " " + caster.motionZ + " " + caster.onGround);
        if (caster.getHealth() < caster.getMaxHealth() && caster.getHealth() > 0.0F && ticksInUse % 10 == 0) {
            while (caster.motionX == 0 && caster.motionZ == 0 && caster.onGround) {
                caster.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 30, 0));
                caster.heal(this.getProperty("health").floatValue() * (1 + modifiers.get("potency")));
                this.playSound(world, caster, ticksInUse, -1, modifiers);
                if (world.isRemote) ParticleBuilder.create(ParticleBuilder.Type.BUFF).entity(caster).clr(170, 250, 250).spawn(world);
                return true;
            }
            if (!world.isRemote) caster.sendStatusMessage(new TextComponentTranslation("spell." + this.getUnlocalisedName() + ".moving"), true);
            return false;
        }
    return false;
    }*/


    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.spell_book_wng || item == WNGItems.scroll_wng;
    }
}