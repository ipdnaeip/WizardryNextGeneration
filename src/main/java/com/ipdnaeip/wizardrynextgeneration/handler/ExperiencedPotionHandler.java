package com.ipdnaeip.wizardrynextgeneration.handler;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.util.WNGUtils;
import electroblob.wizardry.data.IStoredVariable;
import electroblob.wizardry.data.Persistence;
import electroblob.wizardry.data.WizardData;
import electroblob.wizardry.util.NBTExtras;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class ExperiencedPotionHandler {

    // Define a stored variable to keep track of experienced potion names
    public static final IStoredVariable<List<String>> EXPERIENCED_POTION_NAMES = new IStoredVariable.StoredVariable<>("wizardrynextgeneration.experiencedPotionNames",
            // Convert list of strings to NBTTagList
            stringList -> NBTExtras.listToNBT(stringList, NBTTagString::new),
            // Convert NBTTagList to list of strings
            (NBTTagList nbtTagList) -> new ArrayList<>(NBTExtras.NBTToList(nbtTagList, NBTTagString::getString)),
            Persistence.ALWAYS);

/*    // Define a stored variable to keep track of experienced potion names
    public static final IStoredVariable<List<String>> EXPERIENCED_POTION_NAMES = new IStoredVariable.StoredVariable<List<String>, NBTTagList>("wizardrynextgeneration.experiencedPotionNames",
            // Convert list of strings to NBTTagList
            stringList -> NBTExtras.listToNBT(stringList, NBTTagString::new),
            // Convert NBTTagList to list of strings
            (NBTTagList nbtTagList) -> new ArrayList<>(NBTExtras.NBTToList(nbtTagList, NBTTagString::getString)),
            Persistence.ALWAYS).setSynced();*/

    private ExperiencedPotionHandler() {}

    @SubscribeEvent
    public static void onPotionAddedEvent(PotionEvent.PotionAddedEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)event.getEntityLiving();
            // Get the registry name of the potion added
            String potionName = event.getPotionEffect().getPotion().getRegistryName().toString();
            // Check if the player has wizard data
            if (WizardData.get(player) != null) {
                WizardData data = WizardData.get(player);
                // Get the list of experienced potion names from wizard data
                List<String> potionNameList = data.getVariable(EXPERIENCED_POTION_NAMES);
                // If the list is null, initialize it
                if (potionNameList == null) {
                    data.setVariable(EXPERIENCED_POTION_NAMES, potionNameList = new ArrayList<>());
                }
                // If the potion name is not already in the list, add it
                if (!potionNameList.contains(potionName)) {
                    potionNameList.add(potionName);
                    // Update the wizard data with the modified potion name list
                    data.setVariable(EXPERIENCED_POTION_NAMES, potionNameList);
                    // Send a message to the player about learning the potion effect
                    WNGUtils.sendMessage(player, "entity." + WizardryNextGeneration.MODID + ":conjured_potion.effect_learned", true, new TextComponentTranslation(event.getPotionEffect().getPotion().getName()));
                }
            }
        }
    }

    static {
        WizardData.registerStoredVariables(EXPERIENCED_POTION_NAMES);
    }
}
