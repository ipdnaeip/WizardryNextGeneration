package com.ipdnaeip.wizardrynextgeneration.item;

import com.ipdnaeip.wizardrynextgeneration.integration.baubles.WNGBaublesIntegration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGSpells;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemCharmHorn extends ItemCooldownArtefact {

    public ItemCharmHorn(EnumRarity rarity, Type type) {
        super(rarity, type);
        setCooldown(24000);
        addReadinessPropertyOverride();
    }

    public void performAction(EntityPlayer player, ItemStack stack) {
        if (isReady(player.world, stack)) {
            WNGSpells.summon_righteous_defender.cast(player.world, player, player.getActiveHand(), 0, new SpellModifiers());
            setLastTimeActivated(stack, player.getEntityWorld().getTotalWorldTime());
        }
    }
}
