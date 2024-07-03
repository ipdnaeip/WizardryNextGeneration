package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.spell.SpellBuff;
import electroblob.wizardry.util.ParticleBuilder;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;


public class Frenzy extends SpellBarrage {

    public Frenzy() {
        super(WizardryNextGeneration.MODID, "frenzy", SpellActions.POINT, false);
        this.soundValues(1F, 0.1F, 0.1F);
        this.addProperties(EFFECT_RADIUS, EFFECT_DURATION);
    }

    @Override
    protected void barrageEffect(World world, EntityLivingBase target, EntityLivingBase caster, int ticksInUse, SpellModifiers modifiers) {
        target.addPotionEffect(new PotionEffect(WNGPotions.frenzy, this.getProperty(EFFECT_DURATION).intValue(), SpellBuff.getStandardBonusAmplifier(modifiers.get(SpellModifiers.POTENCY))));
    }

    /*@Override
    protected boolean onEntityHit(World world, Entity target, Vec3d hit, EntityLivingBase caster, Vec3d origin, int ticksInUse, SpellModifiers modifiers) {
        if (!world.isRemote && target instanceof EntityLivingBase) {
            double range = (WNGSpells.frenzy.getProperty(EFFECT_RADIUS).floatValue() * modifiers.get(WizardryItems.blast_upgrade));
            List<EntityLivingBase> targets = EntityUtils.getLivingWithinRadius(range, target.posX, target.posY, target.posZ, world);
            for (EntityLivingBase targetEntity : targets) {
                if (targetEntity != caster) {
                    targetEntity.addPotionEffect(new PotionEffect(WNGPotions.frenzy, this.getProperty(EFFECT_DURATION).intValue(), (int) ((modifiers.get(SpellModifiers.POTENCY) - 1) * 3.5)));
                }
            }
        }
        return true;
    }

    @Override
    protected boolean onBlockHit(World world, BlockPos pos, EnumFacing side, Vec3d hit, EntityLivingBase caster, Vec3d origin, int ticksInUse, SpellModifiers modifiers) {
        if (!world.isRemote) {
            double range = (WNGSpells.frenzy.getProperty(EFFECT_RADIUS).floatValue() * modifiers.get(WizardryItems.blast_upgrade));
            List<EntityLivingBase> targets = EntityUtils.getLivingWithinRadius(range, pos.getX(), pos.getY(), pos.getZ(), world);
            Iterator var6 = targets.iterator();

            while (var6.hasNext()) {
                EntityLivingBase targetEntity = (EntityLivingBase) var6.next();
                if (targetEntity != caster) {
                    targetEntity.addPotionEffect(new PotionEffect(WNGPotions.frenzy, (int)(this.getProperty(EFFECT_DURATION).floatValue() * modifiers.get(WizardryItems.duration_upgrade)), (int)((modifiers.get(SpellModifiers.POTENCY) -1) * 3.5)));
                }
            }
        }
        return true;
    }

    @Override
    protected boolean onMiss(World world, EntityLivingBase caster, Vec3d origin, Vec3d direction, int ticksInUse, SpellModifiers modifiers) {
        return false;
    }*/

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
