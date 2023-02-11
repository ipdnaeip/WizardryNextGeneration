package com.ipdnaeip.wizardrynextgeneration.handler;

import com.google.common.collect.Streams;
import com.ipdnaeip.wizardrynextgeneration.potion.PotionMagicWeakness;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.constants.Element;
import electroblob.wizardry.entity.projectile.EntityDart;
import electroblob.wizardry.event.SpellCastEvent;
import electroblob.wizardry.item.ISpellCastingItem;
import electroblob.wizardry.item.ItemArtefact;
import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.util.EntityUtils;
import electroblob.wizardry.util.IElementalDamage;
import electroblob.wizardry.util.MagicDamage;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Iterator;
import java.util.List;

@Mod.EventBusSubscriber
public class WNGEventHandler {

    private WNGEventHandler() {}

/*
        @SubscribeEvent
        public static void onLivingHurtEvent(LivingHurtEvent event) {
            int charges = 0;
            EntityPlayer player = (EntityPlayer)event.getSource().getTrueSource();
            if (ItemArtefact.isArtefactActive(player, WNGItems.ring_static_shock)) {
                if (event.getSource() instanceof IElementalDamage && ((IElementalDamage)event.getSource()).getType() == MagicDamage.DamageType.SHOCK) {
                    if (charges < 3) {
                        event.getEntityLiving().addPotionEffect(new PotionEffect(WNGPotions.magic_weakness, 100, charges));
                        charges++;
                    } else {
                        event.getEntityLiving().addPotionEffect(new PotionEffect(WNGPotions.magic_weakness, 100, 2));
                    }
                }
            }
        }
*/

/*        @SubscribeEvent
        public static void onLivingDeathEvent(LivingDeathEvent event) {
            EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
            if (ItemArtefact.isArtefactActive(player, WNGItems.amulet_halfmoon)) {
                if (!event.getEntityLiving().world.isRemote) {
                    EntityLivingBase entity = event.getEntityLiving();
                    if (event.getEntityLiving() instanceof EntityPlayer) {

                    }
                }
            }
        }*/

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onSpellCastPreEvent(SpellCastEvent.Pre event) {
        if (event.getCaster() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getCaster();
            SpellModifiers modifiers = event.getModifiers();
            Biome biome = player.world.getBiome(player.getPosition());
            float potency = modifiers.get("potency");

                    if (ItemArtefact.isArtefactActive(player, WNGItems.ring_9th_circle)) {
                        if (event.getSpell().getElement() == Element.ICE && BiomeDictionary.hasType(biome, BiomeDictionary.Type.NETHER)) {
                            modifiers.set("potency", 1.3F * potency, false);
                        }

                    }
                }
            }

    @SubscribeEvent
    public static void onSpellCastPostEvent(SpellCastEvent.Post event) {
        if (event.getCaster() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getCaster();
            if (event.getCaster() instanceof EntityPlayer && event.getSpell().getElement() == Element.LIGHTNING && ItemArtefact.isArtefactActive(player, WNGItems.ring_anodized)) {
                event.getCaster().addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 100, 0));
            }
        }
    }
}
