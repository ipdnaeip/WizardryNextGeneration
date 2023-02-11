package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGSpells;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.spell.SpellRay;
import electroblob.wizardry.util.EntityUtils;
import electroblob.wizardry.util.MagicDamage;
import electroblob.wizardry.util.ParticleBuilder;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class SapIntellect extends SpellRay {

    public SapIntellect() {
        super(WizardryNextGeneration.MODID, "sap_intellect", SpellActions.POINT, false);
        this.aimAssist(0.6F);
        this.soundValues(1.0F, 1.0F, 0.2F);
        this.addProperties(DAMAGE, EFFECT_DURATION);
    }

    @Override
    protected boolean onEntityHit(World world, Entity target, Vec3d hit, EntityLivingBase caster, Vec3d origin, int ticksInUse, SpellModifiers modifiers) {

        if (EntityUtils.isLiving(target)) {
            if (world.isRemote) {
                ParticleBuilder.create(ParticleBuilder.Type.LIGHTNING).entity(caster).pos(caster != null ? origin.subtract(caster.getPositionVector()) : origin).target(target).clr(170, 0, 170).spawn(world);
                ParticleBuilder.spawnShockParticles(world, target.posX, target.posY + (double)(target.height / 2.0F), target.posZ);
            }

            if (MagicDamage.isEntityImmune(MagicDamage.DamageType.SHOCK, target)) {
                if (!world.isRemote && caster instanceof EntityPlayer) {
                    ((EntityPlayer)caster).sendStatusMessage(new TextComponentTranslation("spell.resist", new Object[]{target.getName(), this.getNameForTranslationFormatted()}), true);
                }
            } else {
                target.attackEntityFrom(MagicDamage.causeDirectMagicDamage(caster, MagicDamage.DamageType.SHOCK), getProperty(DAMAGE).floatValue() * modifiers.get(SpellModifiers.POTENCY));
                if (target instanceof EntityLivingBase) {
                    EntityLivingBase targetEntity = (EntityLivingBase) target;
                    targetEntity.addPotionEffect(new PotionEffect(WNGPotions.disempowerment, (int)(WNGSpells.sap_intellect.getProperty(EFFECT_DURATION).floatValue() * modifiers.get(WizardryItems.duration_upgrade)),(int)((modifiers.get(SpellModifiers.POTENCY) -1) * 3.5), false, false));
                }
            }
            return true;
        } else {
            return false;
        }
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
    public boolean applicableForItem(Item item) {
        return item == WNGItems.spell_book_wng || item == WNGItems.scroll_wng;
    }
}
