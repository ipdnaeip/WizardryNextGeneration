package com.ipdnaeip.wizardrynextgeneration.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

import javax.annotation.Nonnull;


public class EnchantmentPhalanx extends Enchantment {

    public static final float MOVEMENT_SPEED_PER_LEVEL = 0.75f;

    public EnchantmentPhalanx() {
        super(Rarity.RARE, WNGEnchantmentType.SHIELD, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel() { return 4; }

    @Nonnull
    public String getName() {
        return "enchantment." + this.getRegistryName();
    }

}
