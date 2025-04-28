package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.entity.living.EntityPackMule;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import electroblob.wizardry.data.IStoredVariable;
import electroblob.wizardry.data.Persistence;
import electroblob.wizardry.data.WizardData;
import electroblob.wizardry.entity.living.EntitySpiritHorse;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.spell.Spell;
import electroblob.wizardry.spell.SpellMinion;
import electroblob.wizardry.util.BlockUtils;
import electroblob.wizardry.util.EntityUtils;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.UUID;

public class CallPackMule extends Spell {

    private static final String POTENCY_ATTRIBUTE_MODIFIER = "potency";
    private static final UUID SPEED_BOOST_UUID = UUID.fromString("57b6fb4a-5286-44ed-aaad-f2a4ba7efe52");
    private static final UUID JUMP_BOOST_UUID = UUID.fromString("be5730c6-fa31-4163-bced-e0b3c55d7610");

    public static final IStoredVariable<UUID> UUID_KEY = IStoredVariable.StoredVariable.ofUUID("packMuleUUID", Persistence.ALWAYS);

    public CallPackMule(){
        super(WizardryNextGeneration.MODID, "call_pack_mule", SpellActions.SUMMON, false);
        addProperties(SpellMinion.SUMMON_RADIUS);
        soundValues(0.7f, 1.2f, 0.4f);
        WizardData.registerStoredVariables(UUID_KEY);
    }

    @Override
    public boolean requiresPacket(){
        return false;
    }

    @Override
    public boolean cast(World world, EntityPlayer caster, EnumHand hand, int ticksInUse, SpellModifiers modifiers){
        WizardData data = WizardData.get(caster);
        if(!world.isRemote){
            Entity oldMule = EntityUtils.getEntityByUUID(world, data.getVariable(UUID_KEY));
            BlockPos pos = BlockUtils.findNearbyFloorSpace(caster, 2, 4);
            if(pos == null) {
                return false;
            }
            AttributeModifier speed_boost_modifier = new AttributeModifier(SPEED_BOOST_UUID, POTENCY_ATTRIBUTE_MODIFIER, modifiers.get(SpellModifiers.POTENCY) - 1, EntityUtils.Operations.MULTIPLY_CUMULATIVE);
            AttributeModifier jump_boost_modifier = new AttributeModifier(JUMP_BOOST_UUID, POTENCY_ATTRIBUTE_MODIFIER, modifiers.amplified(SpellModifiers.POTENCY, 0.25f) - 1, EntityUtils.Operations.MULTIPLY_CUMULATIVE);
            if(oldMule instanceof EntityPackMule) {
                EntityPackMule mule = (EntityPackMule)oldMule;
                mule.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                mule.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(SPEED_BOOST_UUID);
                mule.getEntityAttribute(EntityPackMule.JUMP_STRENGTH).removeModifier(JUMP_BOOST_UUID);
                mule.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(speed_boost_modifier);
                mule.getEntityAttribute(EntityPackMule.JUMP_STRENGTH).applyModifier(jump_boost_modifier);
            } else {
                EntityPackMule mule = new EntityPackMule(world);
                mule.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                mule.setTamedBy(caster);
                world.spawnEntity(mule);
                mule.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(speed_boost_modifier);
                mule.getEntityAttribute(EntityPackMule.JUMP_STRENGTH).applyModifier(jump_boost_modifier);
                data.setVariable(UUID_KEY, mule.getUniqueID());
            }
        }
        this.playSound(world, caster, ticksInUse, -1, modifiers);
        return true;
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.SPELL_BOOK_WNG || item == WNGItems.SCROLL_WNG;
    }
}
