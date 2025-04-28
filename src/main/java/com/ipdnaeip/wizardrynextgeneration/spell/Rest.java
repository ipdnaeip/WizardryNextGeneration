package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGSpells;
import com.ipdnaeip.wizardrynextgeneration.util.WNGUtils;
import electroblob.wizardry.data.IVariable;
import electroblob.wizardry.data.Persistence;
import electroblob.wizardry.data.WizardData;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.spell.Spell;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.entity.player.SleepingLocationCheckEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class Rest extends Spell {

    public static final IVariable<Boolean> RESTING = new IVariable.Variable<>(Persistence.NEVER);
    public static final IVariable<SpellModifiers> REST_MODIFIERS = new IVariable.Variable<>(Persistence.NEVER);

    public Rest() {
        super(WizardryNextGeneration.MODID, "rest", SpellActions.IMBUE, false);
        this.addProperties(Spell.HEALTH);
    }

    @Override
    public boolean cast(World world, EntityPlayer caster, EnumHand hand, int ticksInUse, SpellModifiers modifiers) {
        WizardData data =  WizardData.get(caster);
        if (data != null) {
            EntityPlayer.SleepResult sleepResult = caster.trySleep(caster.getPosition());
            if (sleepResult == EntityPlayer.SleepResult.OTHER_PROBLEM) {
                return false;
            } else if (sleepResult == EntityPlayer.SleepResult.NOT_POSSIBLE_NOW) {
                WNGUtils.sendMessage(caster, "tile.bed.noSleep", true);
                return false;
            } else if (sleepResult == EntityPlayer.SleepResult.NOT_SAFE) {
                WNGUtils.sendMessage(caster, "tile.bed.notSafe", true);
                return false;
            } else {
                data.setVariable(RESTING, true);
                data.setVariable(REST_MODIFIERS, modifiers);
                return true;
            }
        }
        return true;
    }

    public static boolean isResting(EntityPlayer player) {
        WizardData data = WizardData.get(player);
        if (data != null) {
            Boolean resting = data.getVariable(RESTING);
            return resting != null && resting;
        }
        return false;
    }

    @SubscribeEvent
    public static void onPlayerSleepInBedEvent(PlayerSleepInBedEvent event) {
        if (isResting(event.getEntityPlayer())) {
            event.setResult(EntityPlayer.SleepResult.OK);
        }
    }

    @SubscribeEvent
    public static void onSleepingLocationCheckEvent(SleepingLocationCheckEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        if (isResting(player)) {
            event.setResult(Event.Result.ALLOW);
            if (player.ticksExisted % 10 == 0) {
                SpellModifiers modifiers = WizardData.get(player).getVariable(REST_MODIFIERS);
                if(modifiers == null) {
                    modifiers = new SpellModifiers();
                }
                player.heal(WNGSpells.REST.getProperty(HEALTH).floatValue() * modifiers.get(SpellModifiers.POTENCY));
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerWakeUpEvent(PlayerWakeUpEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        if (isResting(player)) {
                WizardData.get(player).setVariable(RESTING, false);
        }
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.SPELL_BOOK_WNG || item == WNGItems.SCROLL_WNG;
    }

}
