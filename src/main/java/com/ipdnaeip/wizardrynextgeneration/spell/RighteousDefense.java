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
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class RighteousDefense extends Spell {

    public RighteousDefense() {
        super(WizardryNextGeneration.MODID, "righteous_defense", SpellActions.IMBUE, false);
        this.soundValues(1F, 1.0F, 0F);
        this.addProperties(EFFECT_DURATION);
    }

    @Override
    public boolean cast(World world, EntityPlayer caster, EnumHand hand, int ticksInUse, SpellModifiers modifiers) {
        caster.addPotionEffect(new PotionEffect(WNGPotions.taunt, (int)(this.getProperty(EFFECT_DURATION).floatValue() * modifiers.get(WizardryItems.duration_upgrade)), (int)((modifiers.get("potency") - 1) * 3.5)));
        this.playSound(world, caster, ticksInUse, -1, modifiers);
        if (world.isRemote) ParticleBuilder.create(ParticleBuilder.Type.BUFF).entity(caster).clr(1.0F, 1.0F, 0.3F).spawn(world);
        return true;
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.spell_book_wng || item == WNGItems.scroll_wng;
    }

}
