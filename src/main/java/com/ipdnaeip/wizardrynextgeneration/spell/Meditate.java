package com.ipdnaeip.wizardrynextgeneration.spell;



import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.util.WNGUtils;
import electroblob.wizardry.data.IVariable;
import electroblob.wizardry.data.Persistence;
import electroblob.wizardry.data.WizardData;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.spell.Charge;
import electroblob.wizardry.spell.Spell;
import electroblob.wizardry.util.ParticleBuilder;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class Meditate extends Spell {

    public static final String BLINDNESS_DURATION = "blindness_duration";
    public static final IVariable<Double> PREVIOUS_X = new IVariable.Variable<>(Persistence.NEVER);
    public static final IVariable<Double> PREVIOUS_Y = new IVariable.Variable<>(Persistence.NEVER);
    public static final IVariable<Double> PREVIOUS_Z = new IVariable.Variable<>(Persistence.NEVER);

    public Meditate() {
        super(WizardryNextGeneration.MODID, "meditate", SpellActions.IMBUE, true);
        this.soundValues(1F, 1.0F, 0.2F);
        addProperties(HEALTH, BLINDNESS_DURATION);
    }

    @Override
    protected SoundEvent[] createSounds() {
        return this.createContinuousSpellSounds();
    }

    @Override
    protected void playSound(World world, EntityLivingBase entity, int ticksInUse, int duration, SpellModifiers modifiers, String... sounds) {
        this.playSoundLoop(world, entity, ticksInUse);
    }

    @Override
    protected void playSound(World world, double x, double y, double z, int ticksInUse, int duration, SpellModifiers modifiers, String... sounds) {
        this.playSoundLoop(world, x, y, z, ticksInUse, duration);
    }

    @Override
    public boolean cast(World world, EntityPlayer caster, EnumHand hand, int ticksInUse, SpellModifiers modifiers) {
        if (!isPlayerStill(caster)) {
            WNGUtils.sendMessage(caster, "spell." + this.getUnlocalisedName() + ".moving", true);
            return false;
        }
        //check if the player can be healed and if the player is standing still
        if (isPlayerStill(caster) && caster.getHealth() < caster.getMaxHealth() && caster.getHealth() > 0.0F && ticksInUse % 10 == 0) {
            if (!world.isRemote) caster.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, this.getProperty(BLINDNESS_DURATION).intValue()));
            caster.heal(this.getProperty(HEALTH).floatValue() * modifiers.get(SpellModifiers.POTENCY));
            this.playSound(world, caster, ticksInUse, -1, modifiers);
            if (world.isRemote) this.spawnParticles(world, caster, modifiers);
            return true;
        }
        return false;
    }

    @SubscribeEvent
    public static void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            EntityPlayer player = event.player;
            WizardData data = WizardData.get(player);
            data.setVariable(PREVIOUS_X, player.posX);
            data.setVariable(PREVIOUS_Y, player.posY);
            data.setVariable(PREVIOUS_Z, player.posZ);
        }
    }

    public static boolean isPlayerStill(EntityPlayer player) {
        WizardData data = WizardData.get(player);
        if (data != null) {
            System.out.println((data.getVariable(PREVIOUS_X) == player.posX) + " " + (data.getVariable(PREVIOUS_Y) == player.posY) + " " + ((data.getVariable(PREVIOUS_Z) == player.posZ)));
            if (data.getVariable(PREVIOUS_X) != player.posX) {
                return false;
            } else if (data.getVariable(PREVIOUS_Y) != player.posY) {
                return false;
            } else if (data.getVariable(PREVIOUS_Z) != player.posZ) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    protected void spawnParticles(World world, EntityLivingBase caster, SpellModifiers modifiers){
        for(int i = 0; i < 10; i++){
            double x = caster.posX + world.rand.nextDouble() * 2 - 1;
            double y = caster.posY + caster.getEyeHeight() - 0.5 + world.rand.nextDouble();
            double z = caster.posZ + world.rand.nextDouble() * 2 - 1;
            ParticleBuilder.create(ParticleBuilder.Type.SPARKLE).pos(x, y, z).vel(0, 0.1, 0).clr(170, 250, 250).spawn(world);
        }
        ParticleBuilder.create(ParticleBuilder.Type.BUFF).entity(caster).clr(170, 250, 250).spawn(world);
    }

    @Override
    public boolean applicableForItem(Item item) {
        return item == WNGItems.SPELL_BOOK_WNG || item == WNGItems.SCROLL_WNG;
    }
}