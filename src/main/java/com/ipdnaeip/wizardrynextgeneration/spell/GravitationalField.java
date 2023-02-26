package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.entity.construct.EntityGravitationalField;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import electroblob.wizardry.spell.SpellConstructRanged;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class GravitationalField extends SpellConstructRanged<EntityGravitationalField> {

    public GravitationalField() {
        super(WizardryNextGeneration.MODID, "gravitational_field", EntityGravitationalField::new, false);
        this.soundValues(1F, 1F, 0F);
        this.addProperties(EFFECT_RADIUS);
    }

    @Override
    public boolean cast(World world, EntityPlayer caster, EnumHand hand, int ticksInUse, SpellModifiers modifiers) {
        super.cast(world, caster, hand, ticksInUse, modifiers);
        return true;
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.spell_book_wng || item == WNGItems.scroll_wng;
    }
}

