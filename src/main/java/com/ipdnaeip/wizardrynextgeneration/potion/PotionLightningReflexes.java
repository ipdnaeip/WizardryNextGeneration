package com.ipdnaeip.wizardrynextgeneration.potion;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import electroblob.wizardry.potion.PotionMagicEffect;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.ResourceLocation;

public class PotionLightningReflexes extends PotionMagicEffect {


    public PotionLightningReflexes() {
        super(false, 0x96FFC0, new ResourceLocation(WizardryNextGeneration.MODID, "textures/gui/potion_icons/lightning_reflexes.png"));
        this.setPotionName("potion." + WizardryNextGeneration.MODID + ":lightning_reflexes");
        this.registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_SPEED, "6a4e13a4-b6dd-4a16-b9a6-0fcdcd04cd2c", 0.3D, 2);
    }

}
