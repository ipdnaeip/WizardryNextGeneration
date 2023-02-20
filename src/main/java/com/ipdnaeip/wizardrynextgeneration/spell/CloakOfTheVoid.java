package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.registry.WizardryPotions;
import electroblob.wizardry.spell.Spell;
import electroblob.wizardry.util.ParticleBuilder;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class CloakOfTheVoid extends Spell {

    public CloakOfTheVoid() {
        super(WizardryNextGeneration.MODID, "cloak_of_the_void", SpellActions.IMBUE, false);
        this.soundValues(1F, 1.0F, 0F);
        this.addProperties(EFFECT_DURATION);
    }

    @Override
    public boolean cast(World world, EntityPlayer caster, EnumHand hand, int ticksInUse, SpellModifiers modifiers) {
        caster.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, (int)(this.getProperty(EFFECT_DURATION).floatValue() * modifiers.get(WizardryItems.duration_upgrade)), 0, false, false));
        caster.addPotionEffect(new PotionEffect(WizardryPotions.muffle, (int)(this.getProperty(EFFECT_DURATION).floatValue() * modifiers.get(WizardryItems.duration_upgrade)), 0, false, false));
        this.playSound(world, caster, ticksInUse, -1, modifiers);
        if (world.isRemote) ParticleBuilder.create(ParticleBuilder.Type.BUFF).entity(caster).clr(85, 255, 85).spawn(world);
        return true;
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.spell_book_wng || item == WNGItems.scroll_wng;
    }

}
