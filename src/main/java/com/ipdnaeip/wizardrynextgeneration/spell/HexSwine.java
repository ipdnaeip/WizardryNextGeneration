package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.entity.living.EntityPigZombieConvert;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import electroblob.wizardry.entity.living.ISummonedCreature;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.spell.SpellRay;
import electroblob.wizardry.util.ParticleBuilder;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class HexSwine extends SpellRay {

    public HexSwine() {
        super(WizardryNextGeneration.MODID, "hex_swine", SpellActions.POINT, false);
    }

    @Override
    protected boolean onEntityHit(World world, Entity target, Vec3d hit, EntityLivingBase caster, Vec3d origin, int ticksInUse, SpellModifiers modifiers) {
        if (target instanceof EntityPig && !world.isRemote) {
            EntityPigZombieConvert minion = new EntityPigZombieConvert(world);
            minion.setCaster(caster);
            minion.setLocationAndAngles(target.posX, target.posY, target.posZ, target.rotationYaw, target.rotationPitch);
            minion.setHealth(minion.getMaxHealth());
            target.setDead();
            world.spawnEntity(minion);
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
        ParticleBuilder.create(ParticleBuilder.Type.DARK_MAGIC).pos(x, y, z).vel(vx, vy, vz).clr(150, 150, 85).spawn(world);
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.spell_book_wng || item == WNGItems.scroll_wng;
    }

}
