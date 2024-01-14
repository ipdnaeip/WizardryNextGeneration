package com.ipdnaeip.wizardrynextgeneration.spell;



import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.util.WNGUtils;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.spell.Spell;
import electroblob.wizardry.util.ParticleBuilder;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class Meditate extends Spell {

    public Meditate() {
        super(WizardryNextGeneration.MODID, "meditate", SpellActions.IMBUE, true);
        this.soundValues(1F, 1.0F, 0.2F);
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
        System.out.println(WNGUtils.isEntityStill(caster) + " s m " + WNGUtils.isEntityMoving(caster));
        if (WNGUtils.isEntityMoving(caster)) {
            caster.sendStatusMessage(new TextComponentTranslation("spell." + this.getUnlocalisedName() + ".moving"), true);
            return false;
        }
        //check if the player can be healed and if the player is standing still
        if (WNGUtils.isEntityStill(caster) && caster.getHealth() < caster.getMaxHealth() && caster.getHealth() > 0.0F && ticksInUse % 10 == 0) {
            //caster.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 30, 0));
            caster.heal(this.getProperty("health").floatValue() * (1 + modifiers.get("potency")));
            this.playSound(world, caster, ticksInUse, -1, modifiers);
            ParticleBuilder.create(ParticleBuilder.Type.BUFF).entity(caster).clr(170, 250, 250).spawn(world);
            return true;
        }
        return false;
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.spell_book_wng || item == WNGItems.scroll_wng;
    }
}