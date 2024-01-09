package com.ipdnaeip.wizardrynextgeneration.handler;

import com.ipdnaeip.wizardrynextgeneration.integration.baubles.WNGBaublesIntegration;
import com.ipdnaeip.wizardrynextgeneration.item.ItemAmuletMoon;
import com.ipdnaeip.wizardrynextgeneration.item.ItemCharmHorn;
import com.ipdnaeip.wizardrynextgeneration.item.ItemNewArtefact;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.constants.Element;
import electroblob.wizardry.event.SpellCastEvent;
import electroblob.wizardry.item.ItemArtefact;
import electroblob.wizardry.registry.WizardryPotions;
import electroblob.wizardry.util.EntityUtils;
import electroblob.wizardry.util.IElementalDamage;
import electroblob.wizardry.util.MagicDamage;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


@Mod.EventBusSubscriber
public class WNGEventHandler {

    private WNGEventHandler() {}

    @SubscribeEvent
    public static void onLivingHurtEvent(LivingHurtEvent event) {
        EntityPlayer player;
        if (event.getSource().getTrueSource() instanceof EntityPlayer) {
            player = (EntityPlayer) event.getSource().getTrueSource();
            EntityLivingBase entity = event.getEntityLiving();
            if (ItemNewArtefact.isNewArtefactActive(player, WNGItems.head_raijin)) {
                if (event.getSource() instanceof IElementalDamage && ((IElementalDamage) event.getSource()).getType() == MagicDamage.DamageType.SHOCK) {
                    if (event.getAmount() > (entity.getMaxHealth() / 2)) {
                        entity.addPotionEffect(new PotionEffect(WizardryPotions.paralysis, (int) (event.getAmount() * 10), 0));
                    }
                }
            }
            if (ItemArtefact.isArtefactActive(player, WNGItems.ring_static_shock)) {
                if (event.getSource() instanceof IElementalDamage && ((IElementalDamage) event.getSource()).getType() == MagicDamage.DamageType.SHOCK) {
                    if (entity.isPotionActive(WNGPotions.shock_weakness)) entity.addPotionEffect(new PotionEffect(WNGPotions.shock_weakness, 100, entity.getActivePotionEffect(WNGPotions.shock_weakness).getAmplifier() + 1));
                    else entity.addPotionEffect(new PotionEffect(WNGPotions.shock_weakness, 100, 0));
                }
            }
        }
        if (event.getEntityLiving() instanceof EntityPlayer) {
            player = (EntityPlayer) event.getEntityLiving();
            if (ItemArtefact.isArtefactActive(player, WNGItems.amulet_moon)) {
                ItemStack amulet = WNGBaublesIntegration.getAmuletSlotItemStack(player);
                if (event.getAmount() > player.getHealth() && ((ItemAmuletMoon)amulet.getItem()).isReady(player.getEntityWorld(), amulet)) {
                    event.setCanceled(true);
                    ((ItemAmuletMoon)amulet.getItem()).performAction(player, amulet);
                }
            }
            if (ItemArtefact.isArtefactActive(player, WNGItems.charm_horn)) {
                ItemStack charm = WNGBaublesIntegration.getCharmSlotItemStack(player);
                if (player.getHealth() / player.getMaxHealth() <= 0.2F && EntityUtils.getEntitiesWithinRadius(4, player.posX, player.posY, player.posZ, player.world, EntityMob.class).size() >= 3) {
                    ((ItemCharmHorn)charm.getItem()).performAction(player, charm);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onSpellCastPostEvent(SpellCastEvent.Post event) {
        if (event.getCaster() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getCaster();
            if (ItemArtefact.isArtefactActive(player, WNGItems.ring_anodized)) {
                if (event.getCaster() instanceof EntityPlayer && event.getSpell().getElement() == Element.LIGHTNING) {
                    event.getCaster().addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 100, 0));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)event.getEntity();
            if (ItemNewArtefact.isNewArtefactActive(player, WNGItems.head_hashashin)) {
                if (player.isSneaking() && player.getBrightness() < 0.5F) {
                    player.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 5, 0));
                }
            }
            if (ItemNewArtefact.isNewArtefactActive(player, WNGItems.body_hashashin)) {
                if (player.isSneaking() && player.getBrightness() < 0.5F) {
                    player.addPotionEffect(new PotionEffect(WizardryPotions.muffle, 5, 0));
                }
            }
            if (ItemNewArtefact.isNewArtefactActive(player, WNGItems.body_hashashin) && ItemNewArtefact.isNewArtefactActive(player, WNGItems.head_hashashin)) {
                if (player.isSneaking() && player.getBrightness() < 0.5F) {
                    player.motionX *= 1.25F;
                    player.motionZ *= 1.25F;
                }
            }
            if (ItemNewArtefact.isNewArtefactActive(player, WNGItems.belt_potion)) {
                PotionEffect[] potionEffects = player.getActivePotionEffects().toArray(new PotionEffect[0]);
                if (player.ticksExisted % 10 ==0 ) {
                    for (int i = 0; i < potionEffects.length; i++) {
                        if (WNGBaublesIntegration.getBeltSlotItemStack(player).getMaxDamage() - WNGBaublesIntegration.getBeltSlotItemStack(player).getItemDamage() >= potionEffects[i].getAmplifier() + 1) {
                            if (potionEffects[i].getPotion().isBeneficial()) player.addPotionEffect(new PotionEffect(potionEffects[i].getPotion(), potionEffects[i].getDuration() + 10, potionEffects[i].getAmplifier()));
                            if (player.ticksExisted % 20 == 0) WNGBaublesIntegration.getBeltSlotItemStack(player).damageItem(potionEffects[i].getAmplifier() + 1, player);
                        }
                    }
                }
            }
            if (ItemArtefact.isArtefactActive(player, WNGItems.amulet_moon)) {
                ItemStack amulet = WNGBaublesIntegration.getAmuletSlotItemStack(player);
                if (!player.world.isDaytime() && player.world.provider.getMoonPhase(player.world.getWorldTime()) == 0) {
                    ItemAmuletMoon.SetHasBeenFullMoon(amulet, true);
                }
            }
        }
    }

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
            } else if (ItemArtefact.isArtefactActive(player, WNGItems.ring_nullification)) {
                if (event.getSpell().getElement() == Element.SORCERY) {
                    if (player.getEntityWorld().rand.nextFloat() <= 0.25F && !event.getSpell().isContinuous) {
                        modifiers.set("cost", 0.0F, false);
                    }
                    if (event.getSpell().isContinuous) {
                        modifiers.set("cost", 0.75F, false);
                    }
                }
            } else if (ItemArtefact.isArtefactActive(player, WNGItems.ring_void)) {
                if (event.getSpell().getElement() == Element.SORCERY && BiomeDictionary.hasType(biome, BiomeDictionary.Type.END)) {
                    modifiers.set("potency", 1.3F * potency, false);
                }
            } else if (ItemArtefact.isArtefactActive(player, WNGItems.charm_dice)) {
                modifiers.set("potency", (0.8F + player.getEntityWorld().rand.nextFloat() * 0.8F) * potency, false);
            }
        }
    }

    @SubscribeEvent
    public static void onProjectileImpactArrowEvent(ProjectileImpactEvent.Arrow event) {
        if (!event.getArrow().world.isRemote) {
            if (event.getArrow().shootingEntity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) event.getArrow().shootingEntity;
                if (ItemNewArtefact.isNewArtefactActive(player, WNGItems.body_artemis) && !player.isCreative()) {
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
            if (ItemArtefact.isArtefactActive(player, WNGItems.ring_looting)) {
                event.setLootingLevel(event.getLootingLevel() + 1);
            }
        }
    }

}

