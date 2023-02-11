package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGSpells;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.registry.Spells;
import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.registry.WizardrySounds;
import electroblob.wizardry.spell.Spell;
import electroblob.wizardry.util.EntityUtils;
import electroblob.wizardry.util.ParticleBuilder;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class Smokescreen extends Spell {

    public Smokescreen() {
        super(WizardryNextGeneration.MODID, "smokescreen", SpellActions.POINT_DOWN, false);
        this.soundValues(0.5F, 0.4F, 0.2F);
        this.addProperties(EFFECT_RADIUS, EFFECT_DURATION);
    }

    public boolean cast(World world, EntityPlayer caster, EnumHand hand, int ticksInUse, SpellModifiers modifiers) {
        if (world.isRemote) {
            ParticleBuilder.create(ParticleBuilder.Type.FLASH).pos(caster.posX, caster.posY, caster.posZ).scale(5.0F * modifiers.get(WizardryItems.blast_upgrade)).clr(0, 0, 0).spawn(world);
            world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, caster.posX, caster.posY, caster.posZ, 0.0, 0.0, 0.0);

            for (int i = 0; (float) i < 60.0F * this.getProperty(EFFECT_RADIUS).floatValue(); ++i) {
                float brightness = world.rand.nextFloat() * 0.1F + 0.1F;
                ParticleBuilder.create(ParticleBuilder.Type.CLOUD, world.rand, caster.posX, caster.posY, caster.posZ, (2.0F * modifiers.get(WizardryItems.blast_upgrade)), false).clr(brightness, brightness, brightness).time(80 + world.rand.nextInt(12)).shaded(true).spawn(world);
                brightness = world.rand.nextFloat() * 0.3F;
                ParticleBuilder.create(ParticleBuilder.Type.DARK_MAGIC, world.rand, caster.posX, caster.posY, caster.posZ, (2.0F * modifiers.get(WizardryItems.blast_upgrade)), false).clr(brightness, brightness, brightness).spawn(world);
            }
        }

        if (!world.isRemote) {
            double range = (WNGSpells.smokescreen.getProperty(EFFECT_RADIUS).floatValue() * modifiers.get(WizardryItems.blast_upgrade));
            List<EntityLivingBase> targets = EntityUtils.getLivingWithinRadius(range, caster.posX, caster.posY, caster.posZ, world);
            int duration = (int)(WNGSpells.smokescreen.getProperty(EFFECT_DURATION).floatValue() * modifiers.get(WizardryItems.duration_upgrade));
            Iterator var6 = targets.iterator();

            while (var6.hasNext()) {
                EntityLivingBase target = (EntityLivingBase) var6.next();
                if (target != caster) {
                    target.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, duration, 0));
                }
            }
        }
        this.playSound(world, caster, ticksInUse, -1, modifiers);
        return true;
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.spell_book_wng || item == WNGItems.scroll_wng;
    }
}
