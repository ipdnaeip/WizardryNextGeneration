package com.ipdnaeip.wizardrynextgeneration.item;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import electroblob.wizardry.item.ItemSpellBook;
import electroblob.wizardry.spell.Spell;
import net.minecraft.util.ResourceLocation;

public class ItemWNGSpellBook extends ItemSpellBook {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/spell_hud/spell_book_wng.png");

    @Override
    public ResourceLocation getGuiTexture(Spell spell){
        return GUI_TEXTURE;
    }
}
