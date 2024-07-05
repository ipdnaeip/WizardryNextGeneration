package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import com.ipdnaeip.wizardrynextgeneration.util.WNGUtils;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.spell.SpellRay;
import electroblob.wizardry.util.EntityUtils;
import electroblob.wizardry.util.MagicDamage;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;


public class FleshFeed extends SpellRay {

    public static final String SATURATION_MODIFIER = "saturation_modifier";

    public FleshFeed() {
        super(WizardryNextGeneration.MODID, "flesh_feed", SpellActions.POINT, false);
        this.soundValues(1F, 0.7F, 0F);
        this.aimAssist(0.3F);
        this.addProperties(DAMAGE, SATURATION_MODIFIER, EFFECT_DURATION);
    }
    @Override
    protected boolean onEntityHit(World world, Entity target, Vec3d hit, EntityLivingBase caster, Vec3d origin, int ticksInUse, SpellModifiers modifiers) {
        float damage = getProperty(DAMAGE).floatValue() * modifiers.get(SpellModifiers.POTENCY);
        MagicDamage.DamageType damageType = MagicDamage.DamageType.WITHER;
        if (target instanceof EntityAnimal && caster != null && caster.getHealth() > 0 && caster.getHealth() < caster.getMaxHealth()) {
            if (WNGUtils.canMagicDamageEntity(caster, target, damageType, this, ticksInUse)) {
                EntityUtils.attackEntityWithoutKnockback(target, MagicDamage.causeDirectMagicDamage(caster, damageType), damage);
                ((EntityAnimal) target).addPotionEffect(new PotionEffect(WNGPotions.BLEED, getProperty(EFFECT_DURATION).intValue(), 0));
                ((EntityPlayer) caster).getFoodStats().addStats((int) damage, getProperty(SATURATION_MODIFIER).floatValue());
                caster.heal(damage);
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean onBlockHit(World world, BlockPos pos, EnumFacing side, Vec3d hit, EntityLivingBase caster, Vec3d origin, int ticksInUse, SpellModifiers modifiers) {
        return false;
    }
    @Override
    protected boolean onMiss(World world, EntityLivingBase caster, Vec3d origin, Vec3d direction, int ticksInUse, SpellModifiers modifiers) {
        return false;
    }

    @Override
    public boolean canBeCastBy(TileEntityDispenser dispenser) {
        return false;
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.SPELL_BOOK_WNG || item == WNGItems.SCROLL_WNG;
    }
}
