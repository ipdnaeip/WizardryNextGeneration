package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.spell.Spell;
import electroblob.wizardry.spell.SpellBuff;
import electroblob.wizardry.util.ParticleBuilder;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class AntiGravitationalPush extends Spell {

    public AntiGravitationalPush() {
        super(WizardryNextGeneration.MODID, "anti_gravitational_push", SpellActions.IMBUE, true);
        this.soundValues(1F, 1.0F, 0F);
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
        if (world.isRemote && ticksInUse % 10 == 0) ParticleBuilder.create(ParticleBuilder.Type.BUFF).entity(caster).clr(85, 255, 85).spawn(world);
        this.playSound(world, caster, ticksInUse, -1, modifiers);
        return true;
    }

    public void finishCasting(World world, @Nullable EntityLivingBase caster, double x, double y, double z, @Nullable EnumFacing direction, int duration, SpellModifiers modifiers) {
        if (caster != null) {
            caster.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, (int) modifiers.get(WizardryItems.duration_upgrade) * Math.min(duration * 2, 100), SpellBuff.getStandardBonusAmplifier(modifiers.get(SpellModifiers.POTENCY))));
        }
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.SPELL_BOOK_WNG || item == WNGItems.SCROLL_WNG;
    }

}
