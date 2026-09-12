package com.ipdnaeip.wizardrynextgeneration.handler;

import baubles.api.BaublesApi;
import com.ipdnaeip.wizardrynextgeneration.accessor.AccessorEntityLivingBase;
import com.ipdnaeip.wizardrynextgeneration.entity.living.EntityVampireBat;
import com.ipdnaeip.wizardrynextgeneration.entity.living.EntityWebspitter;
import com.ipdnaeip.wizardrynextgeneration.item.ItemAmuletMoon;
import com.ipdnaeip.wizardrynextgeneration.item.ItemCharmBloodstone;
import com.ipdnaeip.wizardrynextgeneration.item.ItemCooldownArtefact;
import com.ipdnaeip.wizardrynextgeneration.mixin.minecraft.AccessorEntityArrow;
import com.ipdnaeip.wizardrynextgeneration.potion.PotionAnimalAllegiance;
import com.ipdnaeip.wizardrynextgeneration.potion.PotionBleed;
import com.ipdnaeip.wizardrynextgeneration.potion.PotionTaunt;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import com.ipdnaeip.wizardrynextgeneration.spell.Domesticate;
import com.ipdnaeip.wizardrynextgeneration.util.WNGUtils;
import electroblob.wizardry.Wizardry;
import electroblob.wizardry.constants.Element;
import electroblob.wizardry.constants.Tier;
import electroblob.wizardry.event.SpellCastEvent;
import electroblob.wizardry.item.ItemArtefact;
import electroblob.wizardry.item.ItemWand;
import electroblob.wizardry.registry.WizardryPotions;
import electroblob.wizardry.spell.Spell;
import electroblob.wizardry.util.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.SleepingTimeCheckEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.ArrayUtils;

import static electroblob.wizardry.item.ItemArtefact.getActiveArtefacts;


@Mod.EventBusSubscriber
public class WNGServerEvents {

    public static final String LAST_SPELL_ELEMENT = WNGUtils.registerTag("last_spell_element");

    private WNGServerEvents() {}

    @SubscribeEvent
    public static void onCheckSpawnEvent(LivingSpawnEvent.CheckSpawn event){
        EntityLivingBase entity = event.getEntityLiving();
        if (event.getSpawner() == null) {
            if (entity instanceof EntityVampireBat || entity instanceof EntityWebspitter) {
                if (!ArrayUtils.contains(Wizardry.settings.mobSpawnDimensions, event.getWorld().provider.getDimension()))
                    event.setResult(Event.Result.DENY);
            }
        }
    }

    @SubscribeEvent
    public static void onEntityJoinWorldEvent(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        PotionAnimalAllegiance.addAnimalAllegianceTasks(null, entity);
        if (entity instanceof EntityAnimal && entity.getEntityData().hasKey(Domesticate.DOMESTICATE_CASTER)) {
            Domesticate.addFollowTask((EntityAnimal)entity);
        }
    }

    @SubscribeEvent
    public static void onLivingAttackEvent(LivingAttackEvent event) {
        if (event.getEntityLiving().isPotionActive(WNGPotions.CLEANSING_FLAMES) && event.getSource().isFireDamage()) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onLivingHealEvent(LivingHealEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        //Non-natural heals remove the bleed effect
        if (entity.isPotionActive(WNGPotions.BLEED) && !((AccessorEntityLivingBase)entity).wizardrynextgeneration$isNaturalHeal()) {
            entity.removePotionEffect(WNGPotions.BLEED);
        }
        ((AccessorEntityLivingBase)entity).wizardrynextgeneration$setNaturalHeal(false);
    }

    @SubscribeEvent
    public static void onLivingHurtEvent(LivingHurtEvent event) {
        EntityPlayer player;
        EntityLivingBase entity;
        DamageSource damageSource = event.getSource();
        //dealt by the player
        if (event.getSource().getTrueSource() instanceof EntityPlayer) {
            player = (EntityPlayer)event.getSource().getTrueSource();
            entity = event.getEntityLiving();
            for (ItemArtefact artefact : getActiveArtefacts(player)) {
                if (artefact == WNGItems.HEAD_RAIJIN) {
                    //
                    if (event.getSource() instanceof IElementalDamage && ((IElementalDamage)event.getSource()).getType() == MagicDamage.DamageType.SHOCK) {
                        if (event.getAmount() > (entity.getMaxHealth() / 2)) {
                            entity.addPotionEffect(new PotionEffect(WizardryPotions.paralysis, (int)(event.getAmount() * 5)));
                        }
                    }
                }
                if (artefact == WNGItems.RING_STATIC_SHOCK) {
                    if (damageSource instanceof IElementalDamage && ((IElementalDamage)damageSource).getType() == MagicDamage.DamageType.SHOCK) {
                        PotionEffect potionEffect = entity.getActivePotionEffect(WNGPotions.VULNERABILITY_SHOCK);
                        if (potionEffect != null) {
                            entity.addPotionEffect(new PotionEffect(WNGPotions.VULNERABILITY_SHOCK, 100, Math.max(potionEffect.getAmplifier() + 1, 2)));
                        } else {
                            entity.addPotionEffect(new PotionEffect(WNGPotions.VULNERABILITY_SHOCK, 100, 0));
                        }
                    }
                }
            }
        }
        //dealt to the player
        if (event.getEntityLiving() instanceof EntityPlayer) {
            player = (EntityPlayer)event.getEntityLiving();
            for (ItemArtefact artefact : getActiveArtefacts(player)) {
                if (artefact == WNGItems.AMULET_MOON) {
                    ItemStack amulet = BaublesApi.getBaublesHandler(player).getStackInSlot(0);
                    if (ItemCooldownArtefact.isReady(player.getEntityWorld(), amulet) && event.getAmount() > player.getHealth()) {
                        event.setCanceled(true);
                        ItemCooldownArtefact.performAction(player, amulet);
                    }
                }
                if (artefact == WNGItems.CHARM_HORN) {
                    ItemStack charm = BaublesApi.getBaublesHandler(player).getStackInSlot(6);
                    if (ItemCooldownArtefact.isReady(player.world, charm) && player.getHealth() / player.getMaxHealth() <= 0.2F && EntityUtils.getEntitiesWithinRadius(4, player.posX, player.posY, player.posZ, player.world, EntityLiving.class).stream().filter(e -> e instanceof IMob).count() >= 3) {
                        ItemCooldownArtefact.performAction(player, charm);
                    }
                }
                if (artefact == WNGItems.HEAD_THORNS) {
                    if (player.world.rand.nextFloat() < 0.15f && event.getSource().getTrueSource() instanceof EntityLivingBase) {
                        event.getSource().getTrueSource().attackEntityFrom(DamageSource.causeThornsDamage(player), 1 + player.world.rand.nextInt(3));
                    }
                }
            }
        }
    }

    //Animal won't attack the caster
    @SubscribeEvent
    public static void onLivingSetAttackTargetEvent(LivingSetAttackTargetEvent event) {
        if (event.getEntityLiving() instanceof EntityCreature && PotionAnimalAllegiance.isAnimal((EntityCreature)event.getEntityLiving())) {
            EntityCreature animal = (EntityCreature)event.getEntityLiving();
            if (animal.isPotionActive(WNGPotions.ANIMAL_ALLEGIANCE) && event.getTarget() != null) {
                if (event.getTarget() == EntityUtils.getEntityByUUID(animal.world, animal.getEntityData().getUniqueId(PotionAnimalAllegiance.ANIMAL_ALLEGIANCE_CASTER))) {
                    animal.setAttackTarget(null);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)event.getEntity();
            for (ItemArtefact artefact : getActiveArtefacts(player)) {
                if (artefact == WNGItems.AMULET_MOON) {
                    ItemStack amulet = BaublesApi.getBaublesHandler(player).getStackInSlot(0);
                    if (!ItemAmuletMoon.hasFullMoon(amulet)) {
                        //Recharges the artefact if it has been a full moon, still is additionally cooldown dependent
                        if (!player.world.isDaytime() && player.world.provider.getMoonPhase(player.world.getWorldTime()) == 0) {
                            ItemAmuletMoon.setFullMoon(amulet, true);
                            WNGUtils.sendMessage(player, "item." + WNGItems.AMULET_MOON.getRegistryName().toString() + ".seenmoon", true);
                        }
                    }
                }
                else if (artefact == WNGItems.CHARM_BLOODSTONE) {
                    if (player.ticksExisted % ItemCharmBloodstone.TICKS_IN_BETWEEN == 0 && player.shouldHeal()) {
                        ItemStack stack = BaublesApi.getBaublesHandler(player).getStackInSlot(6);
                        int cost;
                        if (ItemCharmBloodstone.isActive(stack)) {
                            cost = ItemCharmBloodstone.CHARGE_PER_USE * ItemCharmBloodstone.ACTIVE_COST_MULTIPLIER;
                            if (stack.getMaxDamage() - stack.getItemDamage() > cost) {
                                player.heal(1f);
                                stack.damageItem(cost, player);
                            }
                        } else if (player.ticksExisted % (ItemCharmBloodstone.TICKS_IN_BETWEEN * ItemCharmBloodstone.ACTIVE_TIME_MULTIPLIER) == 0) {
                            cost = ItemCharmBloodstone.CHARGE_PER_USE;
                            if (stack.getMaxDamage() - stack.getItemDamage() > cost) {
                                player.heal(1f);
                                stack.damageItem(cost, player);
                            }
                        }
                    }
                }
                else if (artefact == WNGItems.BODY_HASHASHIN) {
                    if (player.isSneaking() && player.getBrightness() <= 0.5F) {
                        player.addPotionEffect(new PotionEffect(WizardryPotions.muffle, 1, 0, true, true));
                    }
                }
                else if (artefact == WNGItems.HEAD_HASHASHIN) {
                    if (player.isSneaking() && player.getBrightness() <= 0.5F) {
                        player.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 1, 0, true, true));
                    }
                }
            }
        }
        PotionEffect potionEffect = entity.getActivePotionEffect(WNGPotions.CLEANSING_FLAMES);
        if (potionEffect != null) {
            if (entity.isBurning()) {
                entity.extinguish();
            }
            if (potionEffect.getDuration() % 20 == 0) {
                entity.getEntityWorld().playSound(null, entity.getPosition(), SoundEvents.ENTITY_BLAZE_BURN, SoundCategory.PLAYERS, 0.5F, entity.getEntityWorld().rand.nextFloat() * 0.2F + 0.9F);
            }
            if (potionEffect.getDuration() % (20 / (potionEffect.getAmplifier() + 1)) == 0)  {
                entity.heal(0.5F);
            }
        }
    }

    @SubscribeEvent
    public static void onLootingLevelEvent(LootingLevelEvent event) {
        if (event.getDamageSource().getTrueSource() instanceof EntityLivingBase) {
            EntityLivingBase entity = (EntityLivingBase)event.getDamageSource().getTrueSource();
            if (entity.getHeldItemMainhand().getItem() instanceof ItemWand && WandHelper.getUpgradeLevel(entity.getHeldItemMainhand(), WNGItems.UPGRADE_LOOTING) > 0) {
                event.setLootingLevel(Math.max(event.getLootingLevel(), WandHelper.getUpgradeLevel(entity.getHeldItemMainhand(), WNGItems.UPGRADE_LOOTING)));
            }
            else if (entity.getHeldItemOffhand().getItem() instanceof ItemWand && WandHelper.getUpgradeLevel(entity.getHeldItemOffhand(), WNGItems.UPGRADE_LOOTING) > 0) {
                event.setLootingLevel(Math.max(event.getLootingLevel(), WandHelper.getUpgradeLevel(entity.getHeldItemOffhand(), WNGItems.UPGRADE_LOOTING)));
            }
            if (entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer)entity;
                for(ItemArtefact artefact : getActiveArtefacts(player)) {
                    if (artefact == WNGItems.RING_LOOTING) {
                        event.setLootingLevel(event.getLootingLevel() + 1);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onSleepingTimeCheckEvent(SleepingTimeCheckEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        for (ItemArtefact artefact : getActiveArtefacts(player)) {
            if (artefact == WNGItems.HEAD_SLEEPING_CAP) {
                event.setResult(Event.Result.ALLOW);
            }
        }
    }

    @SubscribeEvent
    public static void onSpellCastPreEvent(SpellCastEvent.Pre event) {
        Spell spell = event.getSpell();
        SpellModifiers modifiers = event.getModifiers();
        float potency = modifiers.get(SpellModifiers.POTENCY);
        float cost = modifiers.get(SpellModifiers.COST);
        if (event.getCaster() instanceof EntityLivingBase) {
            EntityLivingBase entity = event.getCaster();
            if (entity.getHeldItemMainhand().getItem() instanceof ItemWand && WandHelper.getUpgradeLevel(entity.getHeldItemMainhand(), WNGItems.UPGRADE_CHARGEUP) > 0) {
                modifiers.set(SpellModifiers.CHARGEUP, (1f - 0.25f * (float)(WandHelper.getUpgradeLevel(entity.getHeldItemMainhand(), WNGItems.UPGRADE_CHARGEUP))), false);
            }
            else if (entity.getHeldItemOffhand().getItem() instanceof ItemWand && WandHelper.getUpgradeLevel(entity.getHeldItemOffhand(), WNGItems.UPGRADE_CHARGEUP) > 0) {
                modifiers.set(SpellModifiers.CHARGEUP, (1f - 0.25f * (float)(WandHelper.getUpgradeLevel(entity.getHeldItemOffhand(), WNGItems.UPGRADE_CHARGEUP))), false);
            }
            if (event.getCaster() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer)event.getCaster();
                Biome biome = player.world.getBiome(player.getPosition());
                for (ItemArtefact artefact : getActiveArtefacts(player)) {
                    if (artefact == WNGItems.CHARM_DICE) {
                        potency *= 0.8F + (player.getEntityWorld().rand.nextFloat() * 0.8F);
                    } else if (artefact == WNGItems.CHARM_PYRAMID) {
                        float multiplier = 1f;
                        if (spell.getTier() == Tier.NOVICE) {
                            multiplier = 1.3f;
                        } else if (spell.getTier() == Tier.APPRENTICE) {
                            multiplier = 1.2f;
                        } else if (spell.getTier() == Tier.ADVANCED) {
                            multiplier = 1.1f;
                        }
                        potency *= multiplier;
                    } else if (artefact == WNGItems.CHARM_YANG) {
                        if (spell.getElement() == Element.HEALING && player.getBrightness() > 0.5f) {
                            potency *= 1.3f;
                        }
                    } else if (artefact == WNGItems.CHARM_YIN) {
                        if (spell.getElement() == Element.NECROMANCY && player.getBrightness() <= 0.5f) {
                            potency *= 1.3f;
                        }
                    } else if (artefact == WNGItems.CHARM_YIN_YANG) {
                        if (spell.getElement() == Element.HEALING && player.getBrightness() > 0.5f) {
                            potency *= 1.3f;
                        } else if (spell.getElement() == Element.NECROMANCY && player.getBrightness() <= 0.5f) {
                            potency *= 1.3f;
                        }
                    } else if (artefact == WNGItems.RING_9TH_CIRCLE) {
                        if (spell.getElement() == Element.ICE && BiomeDictionary.hasType(biome, BiomeDictionary.Type.NETHER)) {
                            potency *= 1.3f;
                        }
                    } else if (artefact == WNGItems.RING_NULLIFICATION) {
                        if (spell.getElement() == Element.SORCERY) {
                            if (player.getEntityWorld().rand.nextFloat() <= 0.25F && !event.getSpell().isContinuous) {
                                cost *= 0;
                            }
                            if (event.getSpell().isContinuous) {
                                cost *= 0.70f;
                            }
                        }
                    } else if (artefact == WNGItems.RING_RAINBOW) {
                        NBTTagCompound entityNBT = player.getEntityData();
                        if (spell.getElement() != Element.fromName(entityNBT.getString(LAST_SPELL_ELEMENT), null)) {
                            potency *= 1.3f;
                        }
                    } else if (artefact == WNGItems.RING_VOID) {
                        if (spell.getElement() == Element.SORCERY && BiomeDictionary.hasType(biome, BiomeDictionary.Type.END)) {
                            potency *= 1.3f;
                        }
                    }
                }
            }
            modifiers.set(SpellModifiers.POTENCY, potency, true);
            modifiers.set(SpellModifiers.COST, cost, true);
        }
    }

    @SubscribeEvent
    public static void onSpellCastPostEvent(SpellCastEvent.Post event) {
        if (event.getCaster() != null) {
            EntityLivingBase caster = event.getCaster();
            NBTTagCompound entityNBT = caster.getEntityData();
            entityNBT.setString(LAST_SPELL_ELEMENT, event.getSpell().getElement().getName());
        }
        if (event.getCaster() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getCaster();
            if (ItemArtefact.isArtefactActive(player, WNGItems.RING_ANODIZED)) {
                if (event.getSpell().getElement() == Element.LIGHTNING) {
                    player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 50, 0));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPotionAddedEvent(PotionEvent.PotionAddedEvent event) {
        PotionEffect potionEffect = event.getPotionEffect();
        Potion potion = potionEffect.getPotion();
        EntityLivingBase entity = event.getEntityLiving();
        //Make surrounding creatures attack the taunter on the effect being added
        PotionAnimalAllegiance.addAnimalAllegianceTasks(event.getPotionEffect(), event.getEntityLiving());
        if (potion == WNGPotions.TAUNT) {
            PotionTaunt.tauntEnemies(entity, PotionTaunt.getRadiusForAmplifier(potionEffect.getAmplifier()), true);
        }
    }

    @SubscribeEvent
    public static void onPotionApplicableEvent(PotionEvent.PotionApplicableEvent event) {
        PotionEffect potionEffect = event.getPotionEffect();
        Potion potion = potionEffect.getPotion();
        EntityLivingBase entity = event.getEntityLiving();
        if (potion == WNGPotions.ACROBATICS) {
            //Acrobatics only affects players
            if (!(entity instanceof EntityPlayer)) {
                event.setResult(Event.Result.DENY);
            }
        }
        else if (potion == WNGPotions.BETRAYAL) {
            //Betrayal only affects IMobs
            if (!(entity instanceof IMob)) {
                event.setResult(Event.Result.DENY);
            }
        }
        else if (potion == WNGPotions.BLEED) {
            if (!PotionBleed.canBleed(entity)) {
                event.setResult(Event.Result.DENY);
            }
        }
        else if (potion == WNGPotions.SOLAR_WINDS) {
            //Only players can fly with this effect!
            if (!(entity instanceof EntityPlayer)) {
                event.setResult(Event.Result.DENY);
            }
        }
    }

    @SubscribeEvent
    public static void onPotionExpiryEvent(PotionEvent.PotionExpiryEvent event) {
        PotionAnimalAllegiance.removeAnimalAllegianceTasks(event.getPotionEffect(), event.getEntityLiving());
    }

    @SubscribeEvent
    public static void onPotionRemoveEvent(PotionEvent.PotionRemoveEvent event) {
        PotionAnimalAllegiance.removeAnimalAllegianceTasks(event.getPotionEffect(), event.getEntityLiving());
    }

    @SubscribeEvent
    public static void onProjectileImpactArrowEvent(ProjectileImpactEvent.Arrow event) {
        EntityArrow arrow = event.getArrow();
        if (!arrow.world.isRemote) {
            if (arrow.shootingEntity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer)arrow.shootingEntity;
                if (ItemArtefact.isArtefactActive(player, WNGItems.BODY_ARTEMIS) && !player.isCreative()) {
                    ItemStack stack = ((AccessorEntityArrow)arrow).wizardrynextgeneration$getArrowStack();
                    if (event.getRayTraceResult().entityHit instanceof EntityLivingBase) {
                        if (player.world.rand.nextFloat() < 0.5F) {
                            player.addItemStackToInventory(stack);
                        }
                    }
                    else {
                        player.addItemStackToInventory(stack);
                        arrow.setDead();
                    }
                }
            }
        }
    }

}

