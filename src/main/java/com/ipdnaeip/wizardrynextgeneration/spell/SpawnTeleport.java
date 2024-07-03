package com.ipdnaeip.wizardrynextgeneration.spell;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import electroblob.wizardry.data.IStoredVariable;
import electroblob.wizardry.data.Persistence;
import electroblob.wizardry.data.WizardData;
import electroblob.wizardry.item.ItemArtefact;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.packet.PacketTransportation;
import electroblob.wizardry.packet.WizardryPacketHandler;
import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.spell.Spell;
import electroblob.wizardry.util.Location;
import electroblob.wizardry.util.SpellModifiers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class SpawnTeleport extends Spell {
    public static final String TELEPORT_COUNTDOWN = "teleport_countdown";
    public static final IStoredVariable<Integer> COUNTDOWN_KEY;

    public SpawnTeleport() {
        super(WizardryNextGeneration.MODID, "spawn_teleport", SpellActions.POINT_UP, false);
        this.addProperties(TELEPORT_COUNTDOWN);
        WizardData.registerStoredVariables(COUNTDOWN_KEY);
    }

    public boolean requiresPacket() {
        return false;
    }

    @Override
    public boolean cast(World world, EntityPlayer caster, EnumHand hand, int ticksInUse, SpellModifiers modifiers) {
        WizardData data = WizardData.get(caster);
        if (world.isRemote) {
            this.playSound(world, caster, ticksInUse, -1, modifiers);
        }
        if (data != null) {
            Integer countdown = data.getVariable(COUNTDOWN_KEY);
            if (countdown == null || countdown == 0) {
                BlockPos pos = caster.world.getSpawnPoint();
                Location destination = new Location(pos, caster.dimension);
                if (destination.dimension == 0) {
                    this.playSound(world, caster, 0, -1, modifiers);
                    caster.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 150, 0));
                    data.setVariable(COUNTDOWN_KEY, this.getProperty(TELEPORT_COUNTDOWN).intValue());
                    return true;
                } else {
                    if (!world.isRemote) caster.sendStatusMessage(new TextComponentTranslation("spell." + this.getUnlocalisedName() + ".wrongdimension"), true);
                    return false;
                }
            }
        }
        return false;
    }

    private static int update(EntityPlayer player, Integer countdown) {
        if (countdown == null) {
            return 0;
        } else {
            BlockPos pos = player.world.provider.getRandomizedSpawnPoint();
            Location destination = new Location(pos, player.getSpawnDimension());
            if (countdown == 1) {
                Entity mount = player.getRidingEntity();
                if (mount != null) {
                    player.dismountRidingEntity();
                }
                player.setPositionAndUpdate((double) destination.pos.getX() + 0.5, destination.pos.getY(), (double) destination.pos.getZ() + 0.5);
                boolean teleportMount = mount != null && ItemArtefact.isArtefactActive(player, WizardryItems.charm_mount_teleporting);
                if (teleportMount) {
                    mount.setPositionAndUpdate((double) destination.pos.getX() + 0.5, destination.pos.getY(), (double) destination.pos.getZ() + 0.5);
                    player.startRiding(mount);
                }
                player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 50, 0));
                IMessage msg = new PacketTransportation.Message(destination.pos, teleportMount ? null : player);
                WizardryPacketHandler.net.sendToDimension(msg, player.world.provider.getDimension());
            }
            if (countdown > 0) {
                countdown--;
            }
            return countdown;
        }
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.SPELL_BOOK_WNG || item == WNGItems.SCROLL_WNG;
    }


    static {
        COUNTDOWN_KEY = IStoredVariable.StoredVariable.ofInt("tpCountdown", Persistence.NEVER).withTicker(SpawnTeleport::update);
    }
}
