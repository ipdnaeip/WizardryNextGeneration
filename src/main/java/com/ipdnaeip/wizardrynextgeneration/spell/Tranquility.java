package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGSpells;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.spell.SpellAreaEffect;
import electroblob.wizardry.util.EntityUtils;
import electroblob.wizardry.util.MagicDamage;
import electroblob.wizardry.util.ParticleBuilder;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class Tranquility extends SpellAreaEffect  {

    public Tranquility() {
        super(WizardryNextGeneration.MODID, "tranquility", SpellActions.POINT_DOWN, false);
        this.soundValues(1F, 0.8F, 0.2F);
        this.particleDensity(0.5F);
        this.targetAllies(false);
        this.alwaysSucceed(true);
        this.addProperties(EFFECT_DURATION);
    }

    @Override
    protected boolean affectEntity(World world, Vec3d vec3d, @Nullable EntityLivingBase caster, EntityLivingBase target, int i, int i1, SpellModifiers modifiers) {
        target.addPotionEffect(new PotionEffect(WNGPotions.pacify, (int)(WNGSpells.pacify.getProperty(EFFECT_DURATION).floatValue() * modifiers.get(WizardryItems.duration_upgrade)), 0));
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
