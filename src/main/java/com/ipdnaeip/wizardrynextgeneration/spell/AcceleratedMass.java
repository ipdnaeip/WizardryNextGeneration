package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.entity.projectile.EntityAcceleratedMass;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import electroblob.wizardry.spell.SpellArrow;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class AcceleratedMass extends SpellArrow<EntityAcceleratedMass> {

    public static final String MAX_MULTIPLIER = "max_multiplier";
    public static final String ACCELERATION = "acceleration";

    public AcceleratedMass() {
        super(WizardryNextGeneration.MODID, "accelerated_mass", EntityAcceleratedMass::new);
        this.addProperties(DAMAGE, MAX_MULTIPLIER, ACCELERATION);
    }

    @Override
    public boolean cast(World world, EntityPlayer caster, EnumHand hand, int ticksInUse, SpellModifiers modifiers) {
        super.cast(world, caster, hand, ticksInUse, modifiers);
        return true;
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.SPELL_BOOK_WNG || item == WNGItems.SCROLL_WNG;
    }
}