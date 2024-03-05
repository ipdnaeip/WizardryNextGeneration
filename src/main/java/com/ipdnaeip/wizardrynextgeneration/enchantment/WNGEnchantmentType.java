package com.ipdnaeip.wizardrynextgeneration.enchantment;

import com.google.common.base.Predicate;
import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShield;
import net.minecraftforge.common.util.EnumHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WNGEnchantmentType {

    private static final List<EnumEnchantmentType> enchantmentTypes = new ArrayList();
    public static final EnumEnchantmentType SHIELD = createEnchantmentType("shield", item -> item instanceof ItemShield);

    private WNGEnchantmentType() {
    }

    private static EnumEnchantmentType createEnchantmentType(String name, Predicate<Item> delegate) {
        return createEnchantmentType(WizardryNextGeneration.MODID, name, delegate);
    }

    public static EnumEnchantmentType createEnchantmentType(String modID, String name, Predicate<Item> delegate) {
        EnumEnchantmentType enchantmentType = EnumHelper.addEnchantmentType(modID + "$" + name, delegate);
        enchantmentTypes.add(enchantmentType);
        return enchantmentType;
    }

    public static List<EnumEnchantmentType> getEnchantmentTypes() {
        return Collections.unmodifiableList(enchantmentTypes);
    }

}
