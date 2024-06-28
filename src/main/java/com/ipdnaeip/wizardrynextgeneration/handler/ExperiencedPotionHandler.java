package com.ipdnaeip.wizardrynextgeneration.handler;

import com.google.common.collect.Lists;
import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.util.WNGUtils;
import electroblob.wizardry.data.IStoredVariable;
import electroblob.wizardry.data.Persistence;
import electroblob.wizardry.data.WizardData;
import electroblob.wizardry.util.NBTExtras;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.potion.Potion;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.*;

@Mod.EventBusSubscriber
public class ExperiencedPotionHandler {

    // Define a stored variable to keep track of experienced potion names
    public static final IStoredVariable<List<String>> EXPERIENCED_POTION_NAMES = new IStoredVariable.StoredVariable<>("wizardrynextgeneration.experiencedPotionNames",
            // Convert list of strings to NBTTagList
            stringList -> NBTExtras.listToNBT(stringList, NBTTagString::new),
            // Convert NBTTagList to list of strings
            (NBTTagList nbtTagList) -> new ArrayList<>(NBTExtras.NBTToList(nbtTagList, NBTTagString::getString)),
            Persistence.ALWAYS);

    private ExperiencedPotionHandler() {}

    /**
     * This method searches through all the potions that can be applied to conjured potions and experienced by the player.
     * Only includes vanilla potion effects for the time being.
     * @param context Whether to include all, good, or bad potion effects.
     * @return A list of valid potions.
     */
    public static List<Potion> getValidPotionList(ExperiencedPotionHandler.Context context) {
        Collection<Potion> potionList = ForgeRegistries.POTIONS.getValuesCollection();
        List<Potion> potionCuratedList = Lists.newArrayList();
        for (Potion potion : potionList) {
            if (Objects.requireNonNull(potion.getRegistryName()).getNamespace().equals("minecraft")) {
                if (context == Context.ALL || (context == Context.GOOD && !potion.isBadEffect()) || (context == Context.BAD && potion.isBadEffect())) {
                    potionCuratedList.add(potion);
                }
            }
        }
        return potionCuratedList;
    }

    /**
     * This selects a random potion from the list of valid potions that can be applied to conjured potions and experienced by the player.
     * @param context Whether to include all, good, or bad potion effects.
     * @return A random potion from the list of valid potions.
     */
    public static Potion getValidPotion(ExperiencedPotionHandler.Context context) {
        List<Potion> potions = getValidPotionList(context);
        Random rand = new Random();
        return potions.get(rand.nextInt(potions.size() - 1));
    }

    /**
     * Every time the player is affected by a new potion effect of the valid potion effects (see ExperiencedPotionHandler.getValidPotionList), the method stores the name of the potion effect on the player.
     * @param event
     */
    @SubscribeEvent
    public static void onPotionAddedEvent(PotionEvent.PotionAddedEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)event.getEntityLiving();
            // Get the registry name of the potion added
            String potionName = Objects.requireNonNull(event.getPotionEffect().getPotion().getRegistryName()).toString();
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
                if (!potionNameList.contains(potionName) && getValidPotionList(Context.ALL).contains(event.getPotionEffect().getPotion())) {
                    potionNameList.add(potionName);
                    // Update the wizard data with the modified potion name list
                    data.setVariable(EXPERIENCED_POTION_NAMES, potionNameList);
                    // Send a message to the player about learning the potion effect
                    WNGUtils.sendMessage(player, "entity." + WizardryNextGeneration.MODID + ":conjured_potion.effect_learned", true, new TextComponentTranslation(event.getPotionEffect().getPotion().getName()));
                }
            }
        }
    }

    public enum Context {

        ALL(),
        GOOD(),
        BAD();

    }
}
