package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.entity.ai.EntityAIAnimalFollowPlayer;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.spell.SpellRay;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;

//Modified from Dan Windanesz's Covenant spell

@Mod.EventBusSubscriber
public class Domesticate extends SpellRay {

    public Domesticate() {
        super(WizardryNextGeneration.MODID, "domesticate", SpellActions.SUMMON, false);
    }

    @Override
    protected boolean onEntityHit(World world, Entity target, Vec3d hit, @Nullable EntityLivingBase caster, Vec3d origin, int ticksInUse, SpellModifiers modifiers) {
        if (caster instanceof EntityPlayer && target instanceof EntityAnimal) {
            EntityPlayer player = (EntityPlayer) caster;
            EntityAnimal animal = (EntityAnimal) target;
            if (!world.isRemote) {
                if (isAlreadyFollowing(animal)) {
                    endAlliance(player, animal);
                    player.sendStatusMessage(new TextComponentTranslation("spell." + WizardryNextGeneration.MODID + ":domesticate.no_longer_following", animal.getDisplayName()), false);
                }
                else {
                    allyWithAnimal(player, animal);
                    player.sendStatusMessage(new TextComponentTranslation("spell." + WizardryNextGeneration.MODID + ":domesticate.following", animal.getDisplayName()), false);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected boolean onBlockHit(World world, BlockPos pos, EnumFacing side, Vec3d hit, @Nullable EntityLivingBase caster, Vec3d origin, int ticksInUse, SpellModifiers modifiers) {
        return false;
    }

    @Override
    protected boolean onMiss(World world, @Nullable EntityLivingBase caster, Vec3d origin, Vec3d direction, int ticksInUse, SpellModifiers modifiers) {
        return false;
    }

    @Override
    public boolean canBeCastBy(TileEntityDispenser dispenser) {
        return false;
    }

    @Override
    public boolean canBeCastBy(EntityLiving npc, boolean override) {
        return false;
    }

    private static void endAlliance(EntityPlayer player, EntityAnimal animal) {
        for (EntityAITasks.EntityAITaskEntry entityaitasks$entityaitaskentry : animal.tasks.taskEntries) {
            EntityAIBase entityAIBase = entityaitasks$entityaitaskentry.action;
            if (entityAIBase instanceof EntityAIAnimalFollowPlayer) {
                animal.tasks.removeTask(entityAIBase);
            }
        }
    }

    private static void allyWithAnimal(EntityPlayer player, EntityAnimal animal) {
        EntityAIAnimalFollowPlayer task = new EntityAIAnimalFollowPlayer(animal, 1f, 3, 10, player);
        animal.tasks.addTask(2, task);
    }

    private static boolean isAlreadyFollowing(EntityAnimal animal) {
        for (EntityAITasks.EntityAITaskEntry entityaitasks$entityaitaskentry : animal.tasks.taskEntries) {
            EntityAIBase entityAIBase = entityaitasks$entityaitaskentry.action;
            if (entityAIBase instanceof EntityAIAnimalFollowPlayer) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.SPELL_BOOK_WNG || item == WNGItems.SCROLL_WNG;
    }
}



