package com.ipdnaeip.wizardrynextgeneration.item;

import com.ipdnaeip.wizardrynextgeneration.registry.WNGSpells;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemCharmHorn extends ItemCooldownArtefact {

    public ItemCharmHorn(EnumRarity rarity, Type type) {
        super(rarity, type);
        this.setCooldown(24000);
        this.addReadinessPropertyOverride();
    }

    public void action(EntityPlayer player, ItemStack stack) {
        WNGSpells.summon_righteous_defender.cast(player.world, player, player.getActiveHand(), 0, new SpellModifiers());
    }
}
