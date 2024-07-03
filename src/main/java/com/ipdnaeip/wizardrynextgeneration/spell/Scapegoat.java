package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.spell.SpellBuff;
import electroblob.wizardry.spell.SpellRay;
import electroblob.wizardry.util.ParticleBuilder;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Scapegoat extends SpellRay {

    public Scapegoat() {
        super(WizardryNextGeneration.MODID, "scapegoat", SpellActions.POINT, false);
        this.soundValues(1F, 0.1F, 0.1F);
        this.addProperties(EFFECT_DURATION);
    }

    @Override
    protected boolean onEntityHit(World world, Entity target, Vec3d hit, EntityLivingBase caster, Vec3d origin, int ticksInUse, SpellModifiers modifiers) {
        if (target instanceof EntityLivingBase) {
            EntityLivingBase targetEntity = (EntityLivingBase)target;
            targetEntity.addPotionEffect(new PotionEffect(WNGPotions.taunt, (int) (this.getProperty(EFFECT_DURATION).floatValue() * modifiers.get(WizardryItems.duration_upgrade)), SpellBuff.getStandardBonusAmplifier(modifiers.get(SpellModifiers.POTENCY))));
            return true;
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
    protected void spawnParticle(World world, double x, double y, double z, double vx, double vy, double vz) {
        ParticleBuilder.create(ParticleBuilder.Type.SPARKLE).pos(x, y, z).vel(0.0, 0.0, 0.0).clr(0.9F, 0.1F, 0.0F).spawn(world);
        ParticleBuilder.create(ParticleBuilder.Type.DARK_MAGIC).pos(x, y, z).vel(0.0, 0.0, 0.0).clr(0.9F, 0.1F, 0.0F).spawn(world);
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.SPELL_BOOK_WNG || item == WNGItems.SCROLL_WNG;
    }

}
