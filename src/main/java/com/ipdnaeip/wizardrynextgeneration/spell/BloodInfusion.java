package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.util.WNGUtils;
import electroblob.wizardry.spell.SpellBuff;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.text.TextComponentTranslation;

public class BloodInfusion extends SpellBuff {

    public static final String MINIMUM_HEALTH = "minimum_health";

    public BloodInfusion() {
        super(WizardryNextGeneration.MODID, "blood_infusion", 0.667f, 0, 0);
        this.soundValues(2f, 1f, 0.2f);
        this.addProperties(HEALTH, MINIMUM_HEALTH);
    }

    protected boolean applyEffects(EntityLivingBase caster, SpellModifiers modifiers) {
        float health = caster.getHealth();
        float absorption = caster.getAbsorptionAmount();
        float infuse = this.getProperty(HEALTH).floatValue() * modifiers.get(SpellModifiers.POTENCY) - absorption;
        float minimum_health = this.getProperty(MINIMUM_HEALTH).floatValue();
        //check if the player has enough health to infuse
        if (health <= minimum_health) {
            WNGUtils.sendMessage(caster, "spell." + this.getUnlocalisedName() + ".low_health", true);
            return false;
        }
        //check if the player can gain any more absorption
        if(infuse <= 0) {
            WNGUtils.sendMessage(caster, "spell." + this.getUnlocalisedName() + ".full_shield", true);
            return false;
        }
        //check if player can survive initial infusion
        if (health - minimum_health < infuse) {
            infuse = health - minimum_health;
        }
        //trade health for absorption
        caster.setHealth(health - infuse);
        caster.setAbsorptionAmount(infuse + absorption);
        return true;
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.SPELL_BOOK_WNG || item == WNGItems.SCROLL_WNG;
    }
}
