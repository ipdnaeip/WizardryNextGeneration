package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.entity.projectile.EntityFissionBlast;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.spell.SpellArrow;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class FissionBlast extends SpellArrow<EntityFissionBlast> {

    public FissionBlast() {
        super(WizardryNextGeneration.MODID, "fission_blast", EntityFissionBlast::new);
        this.addProperties(DAMAGE, "shots");
    }

    @Override
    public boolean cast(World world, EntityPlayer caster, EnumHand hand, int ticksInUse, SpellModifiers modifiers) {
        for (int i = 0; i < this.getProperty("shots").floatValue() * modifiers.get(WizardryItems.blast_upgrade); i++) {
            if (!world.isRemote) {
                EntityFissionBlast projectile = this.arrowFactory.apply(world);
                projectile.aim(caster, this.calculateVelocity(projectile, modifiers, caster.getEyeHeight() - 0.1F));
                projectile.damageMultiplier = modifiers.get("potency");
                this.addArrowExtras(projectile, caster, modifiers);
                world.spawnEntity(projectile);
            }
        }
        this.playSound(world, caster, ticksInUse, -1, modifiers);
        return true;
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.spell_book_wng || item == WNGItems.scroll_wng;
    }
}