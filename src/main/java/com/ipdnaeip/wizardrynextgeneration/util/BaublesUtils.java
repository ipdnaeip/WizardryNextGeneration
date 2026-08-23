package com.ipdnaeip.wizardrynextgeneration.util;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGPotions;
import electroblob.wizardry.event.SpellCastEvent;
import electroblob.wizardry.integration.baubles.WizardryBaublesIntegration;
import electroblob.wizardry.item.ItemArtefact;
import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.util.InventoryUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber
public class BaublesUtils {

	//New methods

	public static final int SLOT_COUNT = WizardryBaublesIntegration.enabled() ? 7 : 10;

	public static List<SlotItemStack> getEquippedArtefactsNew(EntityPlayer player, ItemArtefact.Type... types) {
		List<SlotItemStack> artefacts = new ArrayList<>();
		IBaublesItemHandler handler = BaublesApi.getBaublesHandler(player);
		for (int slot = 0; slot < SLOT_COUNT; slot++) {
			ItemStack stack = handler.getStackInSlot(slot);
			if (stack.getItem() instanceof ItemArtefact) {
				ItemArtefact itemArtefact = (ItemArtefact)stack.getItem();
				for (ItemArtefact.Type type : types) {
					if (itemArtefact.isEnabled() && itemArtefact.getType() == type) {
						artefacts.add(new SlotItemStack(stack, slot, handler));
					}
				}
			}
		}
		return artefacts;
	}

	public static List<SlotItemStack> getActiveArtefactsNew(EntityPlayer player, ItemArtefact.Type... types) {
		if (types.length == 0) {
			types = ItemArtefact.Type.values();
		}
		List<SlotItemStack> artefacts = new ArrayList<>();
		if (WizardryBaublesIntegration.enabled()) {
			artefacts = getEquippedArtefactsNew(player, types);
		} else {
			List<SlotItemStack> possibleArtefacts = new ArrayList<>();
			int slot = 0;
			NonNullList<ItemStack> mainInventory = player.inventory.mainInventory;
			while (slot < 9) {
				possibleArtefacts.add(new SlotItemStack(mainInventory.get(slot), slot, player.inventory));
				slot++;
			}
			possibleArtefacts.add(0, new SlotItemStack(player.getHeldItemOffhand(), 0, player.inventory.offHandInventory));
			possibleArtefacts.removeIf(slotItemStack -> slotItemStack.getItemStack() == player.getHeldItemMainhand());
			possibleArtefacts.add(0, new SlotItemStack(player.getHeldItemOffhand(), 0, player.inventory.mainInventory));
			for (SlotItemStack possibleArtefact : possibleArtefacts) {
				for (ItemArtefact.Type type : types) {
					int max = 0;
					if (possibleArtefact.getItemStack().getItem() instanceof ItemArtefact) {
						ItemStack itemStack = possibleArtefact.getItemStack();
						while (max < type.maxAtOnce) {
							ItemArtefact itemArtefact = (ItemArtefact)itemStack.getItem();
							if (itemArtefact.isEnabled() && itemArtefact.getType() == type) {
								artefacts.add(possibleArtefact);
							}
							max++;
						}
					}
				}
			}
		}
		//Event injection
		ArtefactSlotCheckEvent event = new ArtefactSlotCheckEvent(player, artefacts);
		if (MinecraftForge.EVENT_BUS.post(event)) {
			return new ArrayList<>();
		}
		return artefacts;
	}

	public static boolean isArtefactActiveNew(EntityPlayer player, Item artefact) {
		for (SlotItemStack slotItemStack : getActiveArtefactsNew(player)) {
			if (slotItemStack.itemStack.getItem() == artefact) {
				return true;
			}
		}
		return false;
	}

	//Should this return a boolean instead of being void and throwing errors?
	public static void modifyArtefact(SlotItemStack artefact) {
		if (artefact.getInventory() instanceof IInventory) {
			if (artefact.getSlot() >= 0 && artefact.getSlot() < ((IInventory)artefact.getInventory()).getSizeInventory()) {
				((IInventory)artefact.getInventory()).setInventorySlotContents(artefact.getSlot(), artefact.getItemStack());
			} else {
				throw new IllegalArgumentException("Slot is outside the the inventory bounds!");
			}
		} else if (artefact.getInventory() instanceof IItemHandlerModifiable) {
			if (artefact.getSlot() >= 0 && artefact.getSlot() < ((IItemHandlerModifiable)artefact.getInventory()).getSlots()) {
				((IItemHandlerModifiable)artefact.getInventory()).setStackInSlot(artefact.getSlot(), artefact.getItemStack());
			} else {
				throw new IllegalArgumentException("Slot is outside the the inventory bounds!");
			}
		} else {
			throw new IllegalArgumentException("Inventory must be of type IInventory or IItemHandlerModifiable into order to modify!");
		}
	}

	//Testing implementation

	@SubscribeEvent
	public static void onSpellCastEventPre(SpellCastEvent.Pre event) {
		if (event.getCaster() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)event.getCaster();
			//Iterating through the artefacts
			for (SlotItemStack artefact : getActiveArtefactsNew(player)) {
				ItemStack stack = artefact.getItemStack();
				Item item = stack.getItem();
				if (item == WizardryItems.ring_arcane_frost) {
					//Can see that the artefact is active after removed
					System.out.println("ring_arcane_frost active!");
				} if (item == WizardryItems.ring_evoker) {
					modifyArtefact(new SlotItemStack(new ItemStack(WizardryItems.ring_arcane_frost), artefact.getSlot(), artefact.getInventory()));
				} if (item == Items.DIAMOND_HELMET) {
					player.addPotionEffect(new PotionEffect(WNGPotions.CLEANSING_FLAMES, 50));
					stack.setItemDamage(stack.getItemDamage() + 1);
					modifyArtefact(artefact);
				}
			}
		}
	}

	@SubscribeEvent
	public static void onArtefactSlotCheckEvent(ArtefactSlotCheckEvent event) {
		EntityPlayer player = event.getPlayer();
		event.addArtefact(WizardryItems.ring_arcane_frost);
		if (player.onGround) {
			event.removeArtefact(WizardryItems.ring_arcane_frost);
		}
		int slot = EntityEquipmentSlot.HEAD.getIndex();
		if (player.inventory.armorInventory.get(slot).getItem() == Items.DIAMOND_HELMET) {
			event.addArtefact(WizardryItems.ring_arcane_frost);
		}
	}

	//For EBW

	public static boolean isArtefactActive(EntityPlayer player, Item artefact){
		if(!(artefact instanceof ItemArtefact)) {
			throw new IllegalArgumentException("Not an artefact!");
		}
		return getActiveArtefacts(player).contains(artefact);
	}

	public static List<ItemArtefact> getActiveArtefacts(EntityPlayer player, ItemArtefact.Type... types){
		if (types.length == 0) types = ItemArtefact.Type.values();
		List<ItemArtefact> artefacts;
		if (WizardryBaublesIntegration.enabled()) {
			artefacts = WizardryBaublesIntegration.getEquippedArtefacts(player, types);
			artefacts.removeIf(i -> !i.isEnabled()); // Remove artefacts that are disabled in the config
		} else {
			artefacts = new ArrayList<>();
			for(ItemArtefact.Type type : types){
				artefacts.addAll(InventoryUtils.getPrioritisedHotbarAndOffhand(player).stream()
						.filter(s -> s.getItem() instanceof ItemArtefact)
						.map(s -> (ItemArtefact)s.getItem())
						.filter(i -> type == i.getType() && i.isEnabled())
						.limit(type.maxAtOnce)
						.collect(Collectors.toList()));
			}
		}
		List<SlotItemStack> slotItemStacks = new ArrayList<>();
		for (ItemArtefact artefact : artefacts) {
			slotItemStacks.add(new SlotItemStack(new ItemStack(artefact)));
		}
		ArtefactSlotCheckEvent event = new ArtefactSlotCheckEvent(player, slotItemStacks);
		if (MinecraftForge.EVENT_BUS.post(event)) {
			return new ArrayList<>();
		}
		artefacts.clear();
		for (SlotItemStack slotItemStack : slotItemStacks) {
			Item item = slotItemStack.itemStack.getItem();
			if (item instanceof ItemArtefact) {
				artefacts.add((ItemArtefact)item);
			}
		}
		return artefacts;
	}

	//Helper class for artefacts

	public static final class SlotItemStack {

		private final ItemStack itemStack;
		//Set to -1 to disable modification
		private final int slot;
		@Nullable
		private final Object inventory;

		public SlotItemStack(ItemStack itemStack, int slot, @Nullable Object inventory) {
			if (itemStack == null) {
				//Cant be null!
				this.itemStack = ItemStack.EMPTY;
			} else {
				//Make the ItemStack a copy so you cant modify it
				this.itemStack = itemStack.copy();
			}
			this.slot = slot;
			this.inventory = inventory;
		}

		public SlotItemStack(ItemStack itemStack) {
			this(itemStack, -1, null);
		}

		public ItemStack getItemStack() {
			return this.itemStack;
		}

		public int getSlot() {
			return this.slot;
		}

		@Nullable
		public Object getInventory() {
			return this.inventory;
		}

		public boolean isModifiable() {
			return this.slot >= 0;
		}

	}

}
