package com.ipdnaeip.wizardrynextgeneration.potion;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import electroblob.wizardry.potion.PotionMagicEffect;
import net.minecraft.util.ResourceLocation;

public class PotionAcrobatics extends PotionMagicEffect {

	public PotionAcrobatics() {
		super(false, 0xFFE293, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/acrobatics.png"));
	}

}
