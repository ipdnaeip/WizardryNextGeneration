package com.ipdnaeip.wizardrynextgeneration.handler;

import baubles.api.BaublesApi;
import com.ipdnaeip.wizardrynextgeneration.item.ItemAmuletMoon;
import com.ipdnaeip.wizardrynextgeneration.item.ItemCharmHorn;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGConstants;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.constants.Element;
import electroblob.wizardry.event.SpellCastEvent;
import electroblob.wizardry.item.ItemArtefact;
import electroblob.wizardry.registry.WizardryPotions;
import electroblob.wizardry.spell.Spell;
import electroblob.wizardry.util.EntityUtils;
import electroblob.wizardry.util.IElementalDamage;
import electroblob.wizardry.util.MagicDamage;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static electroblob.wizardry.item.ItemArtefact.getActiveArtefacts;


@Mod.EventBusSubscriber
public class WNGEventHandler {

    private WNGEventHandler() {}

    @SubscribeEvent
    public static void onLivingHurtEvent(LivingHurtEvent event) {
        EntityPlayer player;
        EntityLivingBase entity;
        //dealt by the player
        if (event.getSource().getTrueSource() instanceof EntityPlayer) {
            player = (EntityPlayer) event.getSource().getTrueSource();
            entity = event.getEntityLiving();
            if (ItemArtefact.isArtefactActive(player, WNGItems.head_raijin)) {
                if (event.getSource() instanceof IElementalDamage && ((IElementalDamage) event.getSource()).getType() == MagicDamage.DamageType.SHOCK) {
                    if (event.getAmount() > (entity.getMaxHealth() / 2)) {
                        entity.addPotionEffect(new PotionEffect(WizardryPotions.paralysis, (int) (event.getAmount() * 10), 0));
                    }
                }
            }
            if (ItemArtefact.isArtefactActive(player, WNGItems.ring_static_shock)) {
                if (event.getSource() instanceof IElementalDamage && ((IElementalDamage) event.getSource()).getType() == MagicDamage.DamageType.SHOCK) {
                    if (entity.isPotionActive(WNGPotions.shock_weakness)) {
                        entity.addPotionEffect(new PotionEffect(WNGPotions.shock_weakness, 100, Math.max(entity.getActivePotionEffect(WNGPotions.shock_weakness).getAmplifier() + 1, 2)));
                    } else {
                        entity.addPotionEffect(new PotionEffect(WNGPotions.shock_weakness, 100, 0));
                    }
                }
            }
        }
        //dealt to the player
        if (event.getEntityLiving() instanceof EntityPlayer) {
            player = (EntityPlayer) event.getEntityLiving();
            if (ItemArtefact.isArtefactActive(player, WNGItems.amulet_moon)) {
                ItemStack amulet = BaublesApi.getBaublesHandler(player).getStackInSlot(0);
                if (event.getAmount() > player.getHealth() && ((ItemAmuletMoon)amulet.getItem()).isReady(player.getEntityWorld(), amulet)) {
                    event.setCanceled(true);
                    ((ItemAmuletMoon)amulet.getItem()).performAction(player, amulet);
                }
            }
            if (ItemArtefact.isArtefactActive(player, WNGItems.charm_horn)) {
                ItemStack charm = BaublesApi.getBaublesHandler(player).getStackInSlot(6);
                if (player.getHealth() / player.getMaxHealth() <= 0.2F && EntityUtils.getEntitiesWithinRadius(4, player.posX, player.posY, player.posZ, player.world, EntityMob.class).size() >= 3) {
                    ((ItemCharmHorn)charm.getItem()).performAction(player, charm);
                }
            }
            if (ItemArtefact.isArtefactActive(player, WNGItems.head_thorns)) {
                if (player.world.rand.nextFloat() < 0.15f && event.getSource().getTrueSource() instanceof EntityLivingBase) {
                    event.getSource().getTrueSource().attackEntityFrom(DamageSource.causeThornsDamage(player), 1 + player.world.rand.nextInt(3));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)event.getEntity();
            if (ItemArtefact.isArtefactActive(player, WNGItems.head_hashashin)) {
                if (player.isSneaking() && player.getBrightness() <= 0.5F) {
                    player.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 5, 0));
                }
            } if (ItemArtefact.isArtefactActive(player, WNGItems.body_hashashin)) {
                if (player.isSneaking() && player.getBrightness() <= 0.5F) {
                    player.addPotionEffect(new PotionEffect(WizardryPotions.muffle, 5, 0));
                }
            }  if (ItemArtefact.isArtefactActive(player, WNGItems.belt_potion)) {
                if (player.ticksExisted % 10 == 0) {
                    ItemStack belt = BaublesApi.getBaublesHandler(player).getStackInSlot(3);
                    PotionEffect[] potionEffects = player.getActivePotionEffects().toArray(new PotionEffect[0]);
                    for (PotionEffect effect : potionEffects) {
                        if (belt.getMaxDamage() - belt.getItemDamage() >= effect.getAmplifier() + 1) {
                            if (!effect.getPotion().isBadEffect())
                                player.addPotionEffect(new PotionEffect(effect.getPotion(), effect.getDuration() + 10, effect.getAmplifier()));
                            if (player.ticksExisted % 20 == 0) belt.damageItem(effect.getAmplifier() + 1, player);
                        }
                    }
                }
            } if (ItemArtefact.isArtefactActive(player, WNGItems.amulet_moon)) {
                ItemStack amulet = BaublesApi.getBaublesHandler(player).getStackInSlot(0);
                if (!player.world.isDaytime() && player.world.provider.getMoonPhase(player.world.getWorldTime()) == 0) {
                    ItemAmuletMoon.setHasBeenFullMoon(amulet, true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onSpellCastPreEvent(SpellCastEvent.Pre event) {
        if (event.getCaster() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getCaster();
            Spell spell = event.getSpell();
            SpellModifiers modifiers = event.getModifiers();

            for(ItemArtefact artefact : getActiveArtefacts(player)) {

                Biome biome = player.world.getBiome(player.getPosition());
                float potency = modifiers.get(SpellModifiers.POTENCY);
                float cost = modifiers.get(SpellModifiers.COST);

                if (artefact == WNGItems.ring_9th_circle) {
                    if (spell.getElement() == Element.ICE && BiomeDictionary.hasType(biome, BiomeDictionary.Type.NETHER)) {
                        modifiers.set(SpellModifiers.POTENCY, 1.3F * potency, false);
                    }
                }
                else if (artefact == WNGItems.ring_nullification) {
                    if (spell.getElement() == Element.SORCERY) {
                        if (player.getEntityWorld().rand.nextFloat() <= 0.25F && !event.getSpell().isContinuous) {
                            modifiers.set(SpellModifiers.POTENCY, 0.0F, false);
                        }
                        if (event.getSpell().isContinuous) {
                            modifiers.set(SpellModifiers.POTENCY, 0.75F * cost, false);
                        }
                    }
                }
                else if (artefact ==  WNGItems.ring_rainbow) {
                    NBTTagCompound entityNBT = player.getEntityData();
                    if (spell.getElement() != Element.fromName(entityNBT.getString(WNGConstants.LAST_SPELL_ELEMENT), null)) {
                        modifiers.set(SpellModifiers.POTENCY, 1.3F * potency, false);
                    }
                }
                else if (artefact == WNGItems.ring_void) {
                    if (spell.getElement() == Element.SORCERY && BiomeDictionary.hasType(biome, BiomeDictionary.Type.END)) {
                        modifiers.set(SpellModifiers.POTENCY, 1.3F * potency, false);
                    }
                }
                else if (artefact == WNGItems.charm_dice) {
                    modifiers.set(SpellModifiers.POTENCY, (0.8F + player.getEntityWorld().rand.nextFloat() * 0.8F) * potency, false);
                }
                else if (artefact == WNGItems.charm_yang) {
                    if (spell.getElement() == Element.HEALING && player.getBrightness() > 0.5f) {
                        modifiers.set(SpellModifiers.POTENCY, 1.3F * potency, false);
                    }
                }
                else if (artefact == WNGItems.charm_yin) {
                    if (spell.getElement() == Element.NECROMANCY && player.getBrightness() <= 0.5f) {
                        modifiers.set(SpellModifiers.POTENCY, 1.3F * potency, false);
                    }
                }
                else if (artefact == WNGItems.charm_yin_yang) {
                    if (spell.getElement() == Element.HEALING && player.getBrightness() > 0.5f) {
                        modifiers.set(SpellModifiers.POTENCY, 1.3F * potency, false);
                    }
                    else if (spell.getElement() == Element.NECROMANCY && player.getBrightness() <= 0.5f) {
                        modifiers.set(SpellModifiers.POTENCY, 1.3F * potency, false);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onSpellCastPostEvent(SpellCastEvent.Post event) {
        if (event.getCaster() != null) {
            EntityLivingBase caster = event.getCaster();
            NBTTagCompound entityNBT = caster.getEntityData();
            entityNBT.setString(WNGConstants.LAST_SPELL_ELEMENT, event.getSpell().getElement().getName());
        }
        if (event.getCaster() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getCaster();
            if (ItemArtefact.isArtefactActive(player, WNGItems.ring_anodized)) {
                if (event.getCaster() instanceof EntityPlayer && event.getSpell().getElement() == Element.LIGHTNING) {
                    event.getCaster().addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 50, 0));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onProjectileImpactArrowEvent(ProjectileImpactEvent.Arrow event) {
        if (!event.getArrow().world.isRemote) {
            if (event.getArrow().shootingEntity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) event.getArrow().shootingEntity;
                if (ItemArtefact.isArtefactActive(player, WNGItems.body_artemis) && !player.isCreative()) {
                    //ItemStack arrow = ((EntityTippedArrow)event.getArrow()).getArrowStack();
                    if (event.getRayTraceResult().entityHit instanceof EntityLivingBase) {
                        if (player.world.rand.nextFloat() < 0.5F) {
                            player.addItemStackToInventory(new ItemStack(Items.ARROW, 1));
                        }
                    }
                    else {
                        player.addItemStackToInventory(new ItemStack(Items.ARROW, 1));
                        event.getArrow().setDead();
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLootingLevelEvent(LootingLevelEvent event) {
        if (event.getDamageSource().getTrueSource() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)event.getDamageSource().getTrueSource();
            for(ItemArtefact artefact : getActiveArtefacts(player)) {
                if (artefact == WNGItems.ring_looting) {
                    event.setLootingLevel(event.getLootingLevel() + 1);
                }
            }
        }
    }

}

