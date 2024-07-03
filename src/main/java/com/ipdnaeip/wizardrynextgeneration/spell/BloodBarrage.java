package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.util.WNGUtils;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.util.EntityUtils;
import electroblob.wizardry.util.MagicDamage;
import electroblob.wizardry.util.ParticleBuilder;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class BloodBarrage extends SpellBarrage {

    public static final String HEAL_FACTOR = "heal_factor";

    public BloodBarrage() {
        super(WizardryNextGeneration.MODID, "blood_barrage", SpellActions.POINT, false);
        this.addProperties(DAMAGE, HEAL_FACTOR);
    }

    @Override
    protected void barrageEffect(World world, EntityLivingBase target, EntityLivingBase caster, int ticksInUse, SpellModifiers modifiers) {
        MagicDamage.DamageType damageType = MagicDamage.DamageType.WITHER;
        if (WNGUtils.canMagicDamageEntity(caster, target, damageType, this, ticksInUse)) {
            float health = target.getHealth();
            EntityUtils.attackEntityWithoutKnockback(target, MagicDamage.causeDirectMagicDamage(caster, damageType), getProperty(DAMAGE).floatValue() * modifiers.get(SpellModifiers.POTENCY));
            if (world.isRemote)
                ParticleBuilder.create(ParticleBuilder.Type.CLOUD, world.rand, target.posX, target.posY + target.getEyeHeight() - 0.5f, target.posZ, 0, false).clr(170, 0, 0).time(10 + world.rand.nextInt(5)).shaded(true).spawn(world);
            health -= target.getHealth();
            if (caster != null) caster.heal(health * getProperty(HEAL_FACTOR).floatValue());
        }
    }

    @Override
    protected void spawnParticle(World world, double x, double y, double z, double vx, double vy, double vz) {
        ParticleBuilder.create(ParticleBuilder.Type.DARK_MAGIC).pos(x, y, z).clr(170, 0, 0).time(20 + world.rand.nextInt(10)).spawn(world);
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.SPELL_BOOK_WNG || item == WNGItems.SCROLL_WNG;
    }
}
