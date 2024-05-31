package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGConstants;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.spell.SpellAreaEffect;
import electroblob.wizardry.util.*;
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
import java.util.Iterator;
import java.util.List;

public class AnimalAllegiance extends SpellAreaEffect  {

    public AnimalAllegiance() {
        super(WizardryNextGeneration.MODID, "animal_allegiance", SpellActions.POINT_UP, false);
        this.addProperties(EFFECT_DURATION);
    }

    @Override
    protected boolean affectEntity(World world, Vec3d vec3d, @Nullable EntityLivingBase caster, EntityLivingBase target, int targetCount, int ticksInUse, SpellModifiers modifiers) {
        target.addPotionEffect(new PotionEffect(WNGPotions.animal_allegiance, this.getProperty(EFFECT_DURATION).intValue(), 0));
        if (caster != null) {
            NBTTagCompound entityNBT = target.getEntityData();
            entityNBT.setUniqueId(WNGConstants.ANIMAL_ALLEGIANCE_CASTER, caster.getUniqueID());
        }
        return true;
    }

    protected boolean findAndAffectEntities(World world, Vec3d origin, @Nullable EntityLivingBase caster, int ticksInUse, SpellModifiers modifiers) {
        double radius = this.getProperty("effect_radius").floatValue() * modifiers.get(WizardryItems.blast_upgrade);
        List<EntityLivingBase> targets = EntityUtils.getLivingWithinRadius(radius, origin.x, origin.y, origin.z, world);
        targets.removeIf(target -> (!(target instanceof EntityAnimal) || target instanceof IEntityOwnable && ((IEntityOwnable)target).getOwner() != null && ((IEntityOwnable)target).getOwner() != caster));
        for (EntityLivingBase target : targets) {
            this.affectEntity(world, origin, caster, target, 0, ticksInUse, modifiers);
        }
        return true;
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.spell_book_wng || item == WNGItems.scroll_wng;
    }
}
