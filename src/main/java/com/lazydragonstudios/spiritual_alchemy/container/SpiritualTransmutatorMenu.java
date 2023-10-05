package com.lazydragonstudios.spiritual_alchemy.container;

import com.lazydragonstudios.spiritual_alchemy.block.entity.SpiritualTransmutatorEntity;
import com.lazydragonstudios.spiritual_alchemy.container.slots.TransmutatorDestroySlot;
import com.lazydragonstudios.spiritual_alchemy.container.slots.TransmutatorOutputSlot;
import com.lazydragonstudios.spiritual_alchemy.init.SpiritualAlchemyMenuTypes;
import com.lazydragonstudios.spiritual_alchemy.knowledge.TransmutationKnowledge;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

public class SpiritualTransmutatorMenu extends AbstractContainerMenu {

	public static SpiritualTransmutatorMenu create(int id, Inventory inventory, FriendlyByteBuf buf) {
		return new SpiritualTransmutatorMenu(id, inventory, new SimpleContainer(0));
	}

	private String searchName;

	private SimpleContainer itemContainer = new SimpleContainer(12);

	public SpiritualTransmutatorMenu(int pContainerId, Inventory playerInventory, Container container) {
		super(SpiritualAlchemyMenuTypes.SPIRITUAL_TRANSMUTATOR.get(), pContainerId);
		this.container = container;

		this.addSlot(new TransmutatorDestroySlot(this.itemContainer, 0, 97, 95));
		this.addSlot(new TransmutatorOutputSlot(this.itemContainer, 1, 97, 10));
		this.addSlot(new TransmutatorOutputSlot(this.itemContainer, 2, 61, 19));
		this.addSlot(new TransmutatorOutputSlot(this.itemContainer, 3, 132, 19));
		this.addSlot(new TransmutatorOutputSlot(this.itemContainer, 4, 31, 41));
		this.addSlot(new TransmutatorOutputSlot(this.itemContainer, 5, 161, 41));
		this.addSlot(new TransmutatorOutputSlot(this.itemContainer, 6, 16, 67));
		this.addSlot(new TransmutatorOutputSlot(this.itemContainer, 7, 175, 67));
		this.addSlot(new TransmutatorOutputSlot(this.itemContainer, 8, 13, 95));
		this.addSlot(new TransmutatorOutputSlot(this.itemContainer, 9, 181, 95));
		this.addSlot(new TransmutatorOutputSlot(this.itemContainer, 10, 18, 123));
		this.addSlot(new TransmutatorOutputSlot(this.itemContainer, 11, 176, 123));

		//inventory
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 22 + j * 18, 174 + i * 18));
			}
		}
		//hot bar
		for (int k = 0; k < 9; ++k) {
			this.addSlot(new Slot(playerInventory, k, 22 + k * 18, 232));
		}

	}

	public String getItemName() {
		return this.searchName;
	}

	public void setItemName(String itemName, Player player) {
		this.searchName = itemName;
		this.refreshResultSlots(player);
	}

	private List<Item> getMatchingItemsSet(Player player) {
		var knowledge = TransmutationKnowledge.get(player);
		var knownItems = knowledge.getKnownTransmutations().stream().map(ForgeRegistries.ITEMS::getValue).collect(Collectors.toSet());
		return knownItems.stream().filter(item -> item.getName(item.getDefaultInstance()).getString(120).toLowerCase().matches("(.*)" + this.searchName.toLowerCase() + "(.*)")).collect(Collectors.toList());
	}

	public ItemStack getResultAtSlot(int slotIndex, Player player, @Nullable List<Item> matchingName) {
		if (slotIndex == 0) return ItemStack.EMPTY;
		if (matchingName == null) {
			matchingName = getMatchingItemsSet(player);
		}
		return matchingName.get(slotIndex - 1).getDefaultInstance();
	}

	public void refreshResultSlots(Player player) {
		var matchingName = getMatchingItemsSet(player);
		for (int i = 1; i < Math.min(matchingName.size() + 1, 12); i++) {
			this.slots.get(i).set(this.getResultAtSlot(i, player, matchingName));
		}
	}

	private final Container container;

	@Override
	public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
		return null;
	}

	@Override
	public boolean stillValid(Player pPlayer) {
		return this.container.stillValid(pPlayer);
	}

}
