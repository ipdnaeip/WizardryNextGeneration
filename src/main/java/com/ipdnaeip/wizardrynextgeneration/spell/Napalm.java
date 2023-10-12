package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.entity.construct.EntityNapalm;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import electroblob.wizardry.spell.SpellConstructRanged;
import net.minecraft.item.Item;

public class Napalm extends SpellLivingConstructRanged<EntityNapalm> {

    public Napalm() {
        super(WizardryNextGeneration.MODID, "napalm", EntityNapalm::new, false);
        this.soundValues(1F, 1F, 0F);
        this.addProperties(EFFECT_RADIUS, EFFECT_DURATION);
        this.allowOverlap = true;
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.spell_book_wng || item == WNGItems.scroll_wng;
    }
}
