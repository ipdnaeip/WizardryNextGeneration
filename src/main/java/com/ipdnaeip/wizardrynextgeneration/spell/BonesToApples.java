package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.spell.Spell;
import electroblob.wizardry.util.InventoryUtils;
import electroblob.wizardry.util.ParticleBuilder;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class BonesToApples extends Spell {

    public static final String BONES_CONVERTED = "bones_converted";

    public BonesToApples() {
        super(WizardryNextGeneration.MODID,"bones_to_apples", SpellActions.IMBUE, false);
        this.addProperties(BONES_CONVERTED);
        this.soundValues(1.0F, 1F, 0.0F);
    }

    public boolean cast(World world, EntityPlayer caster, EnumHand hand, int ticksInUse, SpellModifiers modifiers) {
        int usesLeft = (int)(this.getProperty(BONES_CONVERTED).floatValue() * modifiers.get("potency"));
        int i;
        for(i = 0; i < caster.inventory.getSizeInventory() && usesLeft > 0; ++i) {
            ItemStack stack = caster.inventory.getStackInSlot(i);
            if (!stack.isEmpty() && !world.isRemote && stack.getItem() == Items.BONE) {
                    if (stack.getCount() <= usesLeft) {
                        ItemStack stack2 = new ItemStack(Items.APPLE, stack.getCount() * 2);
                        if (InventoryUtils.doesPlayerHaveItem(caster, Items.APPLE)) {
                            caster.inventory.addItemStackToInventory(stack2);
                            caster.inventory.setInventorySlotContents(i, ItemStack.EMPTY);
                        } else {
                            caster.inventory.setInventorySlotContents(i, stack2);
                        }

                        usesLeft -= stack.getCount();
                    } else {
                        caster.inventory.decrStackSize(i, usesLeft);
                        caster.inventory.addItemStackToInventory(new ItemStack(Items.APPLE, usesLeft * 2));
                        usesLeft = 0;
                    }
                }
            }
        this.playSound(world, caster, ticksInUse, -1, modifiers);
        if (world.isRemote) {
            for(i = 0; i < 10; ++i) {
                ParticleBuilder.create(ParticleBuilder.Type.SPARKLE).entity(caster).vel(0.0, 0.1, 0.0).clr(0, 170, 0).spawn(world);
            }
        }

        return usesLeft < 5;
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.spell_book_wng || item == WNGItems.scroll_wng;
    }
}
