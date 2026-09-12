package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.potion.PotionAnimalAllegiance;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.spell.SpellAreaEffect;
import electroblob.wizardry.util.*;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;

public class AnimalAllegiance extends SpellAreaEffect  {

    public AnimalAllegiance() {
        super(WizardryNextGeneration.MODID, "animal_allegiance", SpellActions.POINT_UP, false);
        this.addProperties(EFFECT_DURATION);
    }

    @Override
    protected boolean affectEntity(World world, Vec3d vec3d, @Nullable EntityLivingBase caster, EntityLivingBase target, int targetCount, int ticksInUse, SpellModifiers modifiers) {
        target.addPotionEffect(new PotionEffect(WNGPotions.ANIMAL_ALLEGIANCE, this.getProperty(EFFECT_DURATION).intValue()));
        if (caster != null) {
            NBTTagCompound entityNBT = target.getEntityData();
            entityNBT.setUniqueId(PotionAnimalAllegiance.ANIMAL_ALLEGIANCE_CASTER, caster.getUniqueID());
            entityNBT.setFloat(PotionAnimalAllegiance.ANIMAL_ATTACK_MULTIPLIER, modifiers.get(SpellModifiers.POTENCY));
        }
        return true;
    }

    protected boolean findAndAffectEntities(World world, Vec3d origin, @Nullable EntityLivingBase caster, int ticksInUse, SpellModifiers modifiers) {
        double radius = this.getProperty(EFFECT_RADIUS).floatValue() * modifiers.get(WizardryItems.blast_upgrade);
        List<EntityCreature> targets = EntityUtils.getEntitiesWithinRadius(radius, origin.x, origin.y, origin.z, world, EntityCreature.class);
        //Only affect acceptable animals and do not affect other entities' owned animals
        targets.removeIf(target -> (!(PotionAnimalAllegiance.isAnimal(target)) || target instanceof IEntityOwnable && ((IEntityOwnable)target).getOwner() != null && ((IEntityOwnable)target).getOwner() != caster));
        targets.sort(Comparator.comparingDouble(e -> e.getDistanceSq(origin.x, origin.y, origin.z)));
        int count = 0;
        for (EntityLivingBase target : targets) {
            this.affectEntity(world, origin, caster, target, count++, ticksInUse, modifiers);
        }
        return true;
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.SPELL_BOOK_WNG || item == WNGItems.SCROLL_WNG;
    }
}
