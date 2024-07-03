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

public class HomeTeleport extends Spell {
    public static final String TELEPORT_COUNTDOWN = "teleport_countdown";
    public static final IStoredVariable<Integer> COUNTDOWN_KEY;

    public HomeTeleport() {
        super(WizardryNextGeneration.MODID, "home_teleport", SpellActions.POINT_UP, false);
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
                //IntelliJ says bedlocation cannot be null, but it can be null!
                if (caster.getBedLocation() == null) {
                    if (!world.isRemote) caster.sendStatusMessage(new TextComponentTranslation("spell." + this.getUnlocalisedName() + ".wrongdimension"), true);
                    return false;
                }
                BlockPos pos = EntityPlayer.getBedSpawnLocation(caster.world, caster.getBedLocation(), false);
                if (pos == null) {
                    if (!world.isRemote) caster.sendStatusMessage(new TextComponentTranslation("spell." + this.getUnlocalisedName() + ".missing"), true);
                    return false;
                }
                Location destination = new Location(pos, caster.dimension);
                if (destination.dimension == caster.dimension) {
                    this.playSound(world, caster, 0, -1, modifiers);
                    caster.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 150, 0));
                    data.setVariable(COUNTDOWN_KEY, this.getProperty("teleport_countdown").intValue());
                    return true;
                }
            }
        }
        return false;
    }

    private static int update(EntityPlayer player, Integer countdown) {
        if (countdown == null) {
            return 0;
        } else {
            //IntelliJ says bedlocation cannot be null, but it can be null!
            if (!player.world.isRemote && player.getBedLocation() != null) {
                BlockPos pos = EntityPlayer.getBedSpawnLocation(player.world, player.getBedLocation(), false);
                if (pos != null) {
                Location destination = new Location(pos, player.getSpawnDimension());
                if (countdown == 1 && destination.dimension == player.dimension) {
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
                }
            }
            return countdown;
        }
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.SPELL_BOOK_WNG || item == WNGItems.SCROLL_WNG;
    }

    static {
        COUNTDOWN_KEY = IStoredVariable.StoredVariable.ofInt("tpCountdown", Persistence.NEVER).withTicker(HomeTeleport::update);
    }
}
