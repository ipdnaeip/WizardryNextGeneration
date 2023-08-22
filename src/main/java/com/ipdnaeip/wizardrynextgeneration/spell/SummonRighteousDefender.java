package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.entity.living.EntityRighteousDefenderMinion;
import com.ipdnaeip.wizardrynextgeneration.entity.living.EntityWebspitterMinion;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import electroblob.wizardry.entity.living.ISummonedCreature;
import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.spell.SpellMinion;
import electroblob.wizardry.util.BlockUtils;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Function;

public class SummonRighteousDefender extends SpellMinion<EntityRighteousDefenderMinion> {

    public SummonRighteousDefender() {
        super(WizardryNextGeneration.MODID, "summon_righteous_defender", EntityRighteousDefenderMinion::new);
    }

    @Override
    protected boolean spawnMinions(World world, EntityLivingBase caster, SpellModifiers modifiers) {
        if (!world.isRemote) {
            for(int i = 0; i < this.getProperty("minion_count").intValue(); ++i) {
                int range = this.getProperty("summon_radius").intValue();
                BlockPos pos = BlockUtils.findNearbyFloorSpace(caster, range, range * 2);
                if (this.flying) {
                    if (pos != null) {
                        pos = pos.up(2);
                    } else {
                        pos = caster.getPosition().north(world.rand.nextInt(range * 2) - range).east(world.rand.nextInt(range * 2) - range);
                    }
                } else if (pos == null) {
                    return false;
                }

                EntityRighteousDefenderMinion minion = this.createMinion(world, caster, modifiers);
                minion.setPosition((double)pos.getX() + 0.5, (double)pos.getY(), (double)pos.getZ() + 0.5);
                ((ISummonedCreature)minion).setCaster(caster);
                ((ISummonedCreature)minion).setLifetime((int)(this.getProperty("minion_lifetime").floatValue() * modifiers.get(WizardryItems.duration_upgrade)));
                IAttributeInstance attribute = minion.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
                attribute.applyModifier(new AttributeModifier("potency", (double)(modifiers.get("potency") - 1.0F), 2));
                minion.taunt_strength = (int)((modifiers.get("potency") - 1) * 3.5);
                minion.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(new AttributeModifier("minion_health", (double)(modifiers.get("minion_health") - 1.0F), 2));
                minion.setHealth(minion.getMaxHealth());
                this.addMinionExtras(minion, pos, caster, modifiers, i);
                world.spawnEntity(minion);
            }
        }

        return true;
    }


    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.spell_book_wng || item == WNGItems.scroll_wng;
    }
}
