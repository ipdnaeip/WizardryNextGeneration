package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.spell.Spell;
import electroblob.wizardry.util.ParticleBuilder;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class BloodInfusion extends Spell {

    public BloodInfusion() {
        super(WizardryNextGeneration.MODID, "blood_infusion", SpellActions.POINT_UP, false);
        this.soundValues(2F, 1F, 0.2F);
        addProperties(HEALTH);
    }

    @Override
    public boolean canBeCastBy(TileEntityDispenser dispenser) {
        return false;
    }

    @Override
    public boolean cast(World world, EntityPlayer caster, EnumHand hand, int ticksInUse, SpellModifiers modifiers) {
        float infuse = this.getProperty(HEALTH).floatValue() * (1 + modifiers.get("potency"));
        //check if player can survive initial infusion
        if (caster.getHealth() <= infuse) {
           infuse = Math.min(caster.getHealth() + caster.getAbsorptionAmount() -0.5F, infuse);
        }
        if (caster.getHealth() <= 0.5F) {
            if(!world.isRemote) caster.sendStatusMessage(new TextComponentTranslation("spell." + this.getUnlocalisedName() + ".low_health"), true);
            return false;
        }
        if(infuse <= caster.getAbsorptionAmount()) {
            if(!world.isRemote) caster.sendStatusMessage(new TextComponentTranslation("spell." + this.getUnlocalisedName() + ".full_shield"), true);
            return false;
        }
        caster.setHealth(caster.getHealth() + caster.getAbsorptionAmount() - infuse);
        caster.setAbsorptionAmount(infuse);
        this.playSound(world, caster, ticksInUse, -1, modifiers, new String[0]);
        if (world.isRemote) ParticleBuilder.create(ParticleBuilder.Type.BUFF).entity(caster).clr(170, 0, 0).spawn(world);
        return true;
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.spell_book_wng || item == WNGItems.scroll_wng;
    }
}
