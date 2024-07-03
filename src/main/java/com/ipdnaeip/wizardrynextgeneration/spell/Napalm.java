package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.entity.construct.EntityNapalm;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import net.minecraft.item.Item;

public class Napalm extends SpellLivingConstructRanged<EntityNapalm> {

    public Napalm() {
        super(WizardryNextGeneration.MODID, "napalm", EntityNapalm::new, false);
        this.soundValues(1F, 1F, 0F);
        this.addProperties(EFFECT_RADIUS, EFFECT_DURATION);
        this.requiresFloor = true;
        this.allowOverlap = true;
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.SPELL_BOOK_WNG || item == WNGItems.SCROLL_WNG;
    }
}
