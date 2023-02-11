package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.spell.Spell;
import electroblob.wizardry.util.ParticleBuilder;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class SolarSentinel extends Spell {

    public SolarSentinel() {
        super(WizardryNextGeneration.MODID, "solar_sentinel", SpellActions.IMBUE, false);
        this.soundValues(1F, 1.0F, 0F);
        this.addProperties(EFFECT_DURATION);
    }

    @Override
    public boolean cast(World world, EntityPlayer caster, EnumHand hand, int ticksInUse, SpellModifiers modifiers) {
        caster.addPotionEffect(new PotionEffect(WNGPotions.solar_winds, (int)(this.getProperty(EFFECT_DURATION).floatValue() * modifiers.get(WizardryItems.duration_upgrade)), 0));
        caster.addPotionEffect(new PotionEffect(WNGPotions.cleansing_flames, (int)(this.getProperty(EFFECT_DURATION).floatValue() * modifiers.get(WizardryItems.duration_upgrade)), (int)((modifiers.get("potency") - 1) * 3.5), false, false));
        this.playSound(world, caster, ticksInUse, -1, modifiers);
        ParticleBuilder.create(ParticleBuilder.Type.BUFF).entity(caster).clr(255, 180, 50).spawn(world);
        return true;
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.spell_book_wng || item == WNGItems.scroll_wng;
    }

}
