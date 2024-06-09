package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.util.WNGUtils;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.spell.Spell;
import electroblob.wizardry.spell.SpellBuff;
import electroblob.wizardry.util.ParticleBuilder;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;

public class LunarSalve extends SpellBuff {

    public LunarSalve() {
        super(WizardryNextGeneration.MODID, "lunar_salve", 0.8f, 1f, 1f);
        this.soundValues(1F, 1.0F, 0F);
        this.addProperties();
    }

    @Override
    protected boolean applyEffects(EntityLivingBase caster, SpellModifiers modifiers) {
        if (!WNGUtils.hasMoonlight(caster.world, caster) && caster instanceof EntityPlayer) {
            if(!caster.world.isRemote) ((EntityPlayer)caster).sendStatusMessage(new TextComponentTranslation("spell." + this.getUnlocalisedName() + ".no_moonlight"), true);
            return false;
        }
        else if (!caster.getActivePotionEffects().isEmpty() && WNGUtils.hasMoonlight(caster.world, caster)) {
            ItemStack milk = new ItemStack(Items.MILK_BUCKET);
            boolean flag = false;
            for (PotionEffect effect : caster.getActivePotionEffects()) {
                if (effect.isCurativeItem(milk) && effect.getPotion().isBadEffect()) {
                    caster.removePotionEffect(effect.getPotion());
                    flag = true;
                    break;
                }
            }
            return flag;
        } else {
            return false;
        }
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.spell_book_wng || item == WNGItems.scroll_wng;
    }

}
