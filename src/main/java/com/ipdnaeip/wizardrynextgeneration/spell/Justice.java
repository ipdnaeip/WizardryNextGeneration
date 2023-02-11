package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.spell.SpellAreaEffect;
import electroblob.wizardry.util.EntityUtils;
import electroblob.wizardry.util.MagicDamage;
import electroblob.wizardry.util.ParticleBuilder;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class Justice extends SpellAreaEffect  {

    public Justice() {
        super(WizardryNextGeneration.MODID, "justice", SpellActions.POINT_DOWN, false);
        this.soundValues(1F, 0.6F, 0.1F);
        this.particleDensity(0.5F);
        this.targetAllies(false);
        this.alwaysSucceed(true);
        this.addProperties(EFFECT_RADIUS);
    }

    @Override
    protected boolean affectEntity(World world, Vec3d vec3d, @Nullable EntityLivingBase caster, EntityLivingBase target, int i, int i1, SpellModifiers modifiers) {
        EntityPlayer castertemp = (EntityPlayer) caster;
        if (caster != null) {
            float low_health = target.getHealth() - (target.getMaxHealth() / caster.getMaxHealth() * caster.getHealth());
            float high_health = Math.min(target.getHealth() - (target.getMaxHealth() / caster.getMaxHealth() * caster.getHealth()), (caster.getMaxHealth() - caster.getHealth()) * (1 + modifiers.get("potency")));
            if (target.getHealth() / target.getMaxHealth() <= caster.getHealth() / caster.getMaxHealth() || caster.getHealth() == caster.getMaxHealth()) {
                if (!world.isRemote)
                    castertemp.sendStatusMessage(new TextComponentTranslation("spell." + this.getUnlocalisedName() + ".no_effect"), true);
                return false;
            }
            if (target.getMaxHealth() <= caster.getMaxHealth()) {
                this.soundValues(1F, 0.6F, 0.1F);
                EntityUtils.attackEntityWithoutKnockback(target, MagicDamage.causeDirectMagicDamage(caster, MagicDamage.DamageType.RADIANT), low_health);
            }
            if (target.getMaxHealth() > caster.getMaxHealth()) {
                if (!world.isRemote)
                    castertemp.sendStatusMessage(new TextComponentTranslation("spell." + this.getUnlocalisedName() + ".high_health"), true);
                this.soundValues(1F, 0.4F, 0.1F);
                EntityUtils.attackEntityWithoutKnockback(target, MagicDamage.causeDirectMagicDamage(caster, MagicDamage.DamageType.RADIANT), high_health);
            }
        }
        return true;
    }

    @Override
    protected void spawnParticle(World world, double x, double y, double z) {
        ParticleBuilder.create(ParticleBuilder.Type.SPARKLE).pos(x, y + Math.random() * 2, z).vel(0.0, 0.0, 0.0).clr(1.0F, 1.0F, 0.3F).spawn(world);
        ParticleBuilder.create(ParticleBuilder.Type.FLASH).pos(x, y, z).clr(1.0F, 1.0F, 0.3F).spawn(world);
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.spell_book_wng || item == WNGItems.scroll_wng;
    }
}
