package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.entity.ai.EntityAIAnimalFollowPlayer;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import electroblob.wizardry.data.IStoredVariable;
import electroblob.wizardry.data.Persistence;
import electroblob.wizardry.data.WizardData;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.spell.SpellRay;
import electroblob.wizardry.util.EntityUtils;
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
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//Modified from Dan Windanesz's Covenant spell

@Mod.EventBusSubscriber
public class Domesticate extends SpellRay {

/*    public static final List<IStoredVariable<UUID>> ALLIED_ANIMAL_UUID_KEYS = new ArrayList<>();*/

    public Domesticate() {
        super(WizardryNextGeneration.MODID, "domesticate", SpellActions.SUMMON, false);
/*        for (IStoredVariable<UUID> keys : ALLIED_ANIMAL_UUID_KEYS) {
            WizardData.registerStoredVariables(keys);
        }*/
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
 /*       WizardData data = WizardData.get(player);
        for (IStoredVariable<UUID> iStoredVariable : ALLIED_ANIMAL_UUID_KEYS) {
            if (data.getVariable(iStoredVariable) == animal.getUniqueID()) {
                data.setVariable(iStoredVariable, null);
            }
        }*/
    }

    private static void allyWithAnimal(EntityPlayer player, EntityAnimal animal) {
        EntityAIAnimalFollowPlayer task = new EntityAIAnimalFollowPlayer(animal, 1f, 3, 10, player);
        animal.tasks.addTask(2, task);
/*        ALLIED_ANIMAL_UUID_KEYS.add(IStoredVariable.StoredVariable.ofUUID("alliedAnimalUUID", Persistence.ALWAYS));
        WizardData data = WizardData.get(player);
        data.setVariable(IStoredVariable.StoredVariable.ofUUID("alliedAnimalUUID", Persistence.ALWAYS), animal.getUniqueID());*/
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

/*    @SubscribeEvent
    public static void onCheckSpawnEvent(EntityJoinWorldEvent event) {
        // We have no way of checking if it's a spawner in getCanSpawnHere() so this has to be done here instead
        if (event.getEntity() instanceof EntityPlayer) {
            if (!event.getWorld().isRemote) {
                EntityPlayer player = (EntityPlayer) event.getEntity();
                WizardData data = WizardData.get(player);
                for (IStoredVariable<UUID> i : ALLIED_ANIMAL_UUID_KEYS) {
                    Entity storedAnimal = EntityUtils.getEntityByUUID(event.getWorld(), data.getVariable(i));
                    if (storedAnimal!= null && !isAlreadyFollowing((EntityAnimal) storedAnimal)) {
                    allyWithAnimal(player, (EntityAnimal) storedAnimal);
                    }
                }
            }
        }
    }*/

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.spell_book_wng || item == WNGItems.scroll_wng;
    }
}



