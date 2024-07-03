package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import electroblob.wizardry.spell.SpellBuff;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.text.TextComponentTranslation;

public class BloodInfusion extends SpellBuff {

    public BloodInfusion() {
        super(WizardryNextGeneration.MODID, "blood_infusion", 0.667f, 0, 0);
        this.soundValues(2f, 1f, 0.2f);
        this.addProperties(HEALTH);
    }

    protected boolean applyEffects(EntityLivingBase caster, SpellModifiers modifiers) {
        float infuse = this.getProperty(HEALTH).floatValue() * (1 + modifiers.get(SpellModifiers.POTENCY));
        //check if player can survive initial infusion
        if (caster.getHealth() <= infuse) {
            infuse = Math.min(caster.getHealth() + caster.getAbsorptionAmount() -0.5F, infuse);
        }
        if (caster.getHealth() <= 0.5F) {
            if(!caster.world.isRemote && caster instanceof EntityPlayer) ((EntityPlayer)caster).sendStatusMessage(new TextComponentTranslation("spell." + this.getUnlocalisedName() + ".low_health"), true);
            return false;
        }
        if(infuse <= caster.getAbsorptionAmount()) {
            if(!caster.world.isRemote && caster instanceof EntityPlayer) ((EntityPlayer)caster).sendStatusMessage(new TextComponentTranslation("spell." + this.getUnlocalisedName() + ".full_shield"), true);
            return false;
        }
        caster.setHealth(caster.getHealth() + caster.getAbsorptionAmount() - infuse);
        caster.setAbsorptionAmount(infuse);
        return true;
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.SPELL_BOOK_WNG || item == WNGItems.SCROLL_WNG;
    }
}
