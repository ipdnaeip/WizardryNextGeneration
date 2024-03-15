package com.ipdnaeip.wizardrynextgeneration.handler;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import electroblob.wizardry.data.IStoredVariable;
import electroblob.wizardry.data.Persistence;
import electroblob.wizardry.data.WizardData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class ExperiencedPotionHandler {

    public static final IStoredVariable<List<String>> EXPERIENCED_POTION_NAMES = new IStoredVariable.StoredVariable<>("wizardrynextgeneration.experiencedPotionNames", , , Persistence.ALWAYS);

    private ExperiencedPotionHandler() {}

    @SubscribeEvent
    public static void onPotionAddedEvent(PotionEvent.PotionAddedEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)event.getEntityLiving();
            String potionName = event.getPotionEffect().getEffectName();
            if (WizardData.get(player) != null) {
                WizardData data = WizardData.get(player);
                List<String> potionNameList = data.getVariable(EXPERIENCED_POTION_NAMES);
                if (potionNameList.isEmpty()) {
                    data.setVariable(EXPERIENCED_POTION_NAMES, potionNameList = new ArrayList<>(0));
                }
                if (!potionNameList.contains(potionName)) {
                    potionNameList.add(potionName);
                    //data.setVariable(POTION_EFFECTS, potionEffects);
                    if (!player.world.isRemote) {
                        player.sendStatusMessage(new TextComponentTranslation("entity." + WizardryNextGeneration.MODID + ":conjured_potion.effect_learned", new TextComponentTranslation(potionName)), true);
                    }
                }
            }
        }
    }

}
