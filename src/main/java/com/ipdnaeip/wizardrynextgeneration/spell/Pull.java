package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import electroblob.wizardry.Wizardry;
import electroblob.wizardry.item.ItemArtefact;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.spell.SpellRay;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Pull extends SpellRay {

    public Pull() {
        super(WizardryNextGeneration.MODID, "pull", SpellActions.POINT, false);
        this.soundValues(1F, 0.8F, 0.2F);
        this.addProperties(EFFECT_STRENGTH);
    }

    protected boolean onEntityHit(World world, Entity target, Vec3d hit, EntityLivingBase caster, Vec3d origin, int ticksInUse, SpellModifiers modifiers) {
        if (target instanceof EntityPlayer) {
            return Wizardry.settings.playersMoveEachOther && !ItemArtefact.isArtefactActive((EntityPlayer) target, WizardryItems.amulet_anchoring);
        } else {
            float multiplier = this.getProperty(EFFECT_STRENGTH).floatValue() * modifiers.get(SpellModifiers.POTENCY);
            target.motionX = multiplier * (origin.x - target.posX) / 5;
            target.motionY = multiplier * (origin.y - target.posY) / 5;
            target.motionZ = multiplier * (origin.z - target.posZ) / 5;
        }
        return true;
    }

    protected boolean onBlockHit(World world, BlockPos pos, EnumFacing side, Vec3d hit, EntityLivingBase caster, Vec3d origin, int ticksInUse, SpellModifiers modifiers) {
        return false;
    }

    protected boolean onMiss(World world, EntityLivingBase caster, Vec3d origin, Vec3d direction, int ticksInUse, SpellModifiers modifiers) {
        return false;
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.SPELL_BOOK_WNG || item == WNGItems.SCROLL_WNG;
    }

}
