package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
        import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
        import electroblob.wizardry.registry.WizardryPotions;
        import electroblob.wizardry.spell.SpellBuff;
        import net.minecraft.init.MobEffects;
        import net.minecraft.item.Item;

public class CloakOfTheVoid extends SpellBuff {

    public CloakOfTheVoid() {
        super(WizardryNextGeneration.MODID, "cloak_of_the_void", 0.333f, 1f, 0.333f, () -> MobEffects.INVISIBILITY, ()-> WizardryPotions.muffle);
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.SPELL_BOOK_WNG || item == WNGItems.SCROLL_WNG;
    }
}
