package com.lazydragonstudios.spiritual_alchemy.container;

import com.lazydragonstudios.spiritual_alchemy.block.entity.SpiritualTransmutatorEntity;
import com.lazydragonstudios.spiritual_alchemy.client.gui.SpiritualTransmutatorScreen;
import com.lazydragonstudios.spiritual_alchemy.container.slots.TransmutatorDestroySlot;
import com.lazydragonstudios.spiritual_alchemy.container.slots.TransmutatorOutputSlot;
import com.lazydragonstudios.spiritual_alchemy.init.SpiritualAlchemyMenuTypes;
import com.lazydragonstudios.spiritual_alchemy.knowledge.Elements;
import com.lazydragonstudios.spiritual_alchemy.knowledge.TransmutationKnowledge;
import com.lazydragonstudios.spiritual_alchemy.networking.SendTransmutationKnowledgeToPlayer;
import com.lazydragonstudios.spiritual_alchemy.networking.SpiritualPacketHandler;
import com.lazydragonstudios.spiritual_alchemy.transmutation.ItemSpiritValue;
import com.lazydragonstudios.spiritual_alchemy.utils.ItemSpiritValueUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class SpiritualTransmutatorMenu extends AbstractContainerMenu {

	public static SpiritualTransmutatorMenu create(int id, Inventory inventory, FriendlyByteBuf buf) {
		return new SpiritualTransmutatorMenu(id, inventory, inventory.player.level().getBlockEntity(buf.readBlockPos()));
	}

	private final BlockEntity container;

	private String searchName;

	private SimpleContainer itemContainer = new SimpleContainer(12);

	private int[] barFills2 = new int[5];

	private Player lastInteractingPlayer = null;

	public SpiritualTransmutatorMenu(int pContainerId, Inventory playerInventory, BlockEntity container) {
		super(SpiritualAlchemyMenuTypes.SPIRITUAL_TRANSMUTATOR.get(), pContainerId);
		this.container = container;

		this.searchName = "";

		this.addSlot(new TransmutatorDestroySlot(this.itemContainer, 0, 97, 95));
		this.addSlot(new TransmutatorOutputSlot(this.itemContainer, 1, 97, 10, this::onRemoveItemStack));
		this.addSlot(new TransmutatorOutputSlot(this.itemContainer, 2, 61, 19, this::onRemoveItemStack));
		this.addSlot(new TransmutatorOutputSlot(this.itemContainer, 3, 132, 19, this::onRemoveItemStack));
		this.addSlot(new TransmutatorOutputSlot(this.itemContainer, 4, 31, 41, this::onRemoveItemStack));
		this.addSlot(new TransmutatorOutputSlot(this.itemContainer, 5, 161, 41, this::onRemoveItemStack));
		this.addSlot(new TransmutatorOutputSlot(this.itemContainer, 6, 17, 68, this::onRemoveItemStack));
		this.addSlot(new TransmutatorOutputSlot(this.itemContainer, 7, 176, 68, this::onRemoveItemStack));
		this.addSlot(new TransmutatorOutputSlot(this.itemContainer, 8, 13, 95, this::onRemoveItemStack));
		this.addSlot(new TransmutatorOutputSlot(this.itemContainer, 9, 181, 95, this::onRemoveItemStack));
		this.addSlot(new TransmutatorOutputSlot(this.itemContainer, 10, 18, 123, this::onRemoveItemStack));
		this.addSlot(new TransmutatorOutputSlot(this.itemContainer, 11, 176, 123, this::onRemoveItemStack));

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
		if (playerInventory.player instanceof ServerPlayer serverPlayer) {
			SpiritualPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new SendTransmutationKnowledgeToPlayer(TransmutationKnowledge.get(serverPlayer).serialize()));
		}
		this.itemContainer.addListener(this::onItemContainerChanged);
		this.refreshResultSlots(playerInventory.player);
	}

	private void tryToLearnTransmutation(ItemStack stack, Player player) {
		var item = stack.getItem();
		if (!ItemSpiritValueUtils.SPIRIT_VALUE_BY_ITEM.containsKey(item)) return;
		var knowledge = TransmutationKnowledge.get(player);
		knowledge.getKnownTransmutations().add(ForgeRegistries.ITEMS.getKey(item));
	}

	private void onItemContainerChanged(Container container) {
		var stackAt0 = container.getItem(0);
		if (stackAt0.isEmpty()) return;
		container.setItem(0, ItemStack.EMPTY);
		if (!(this.container instanceof SpiritualTransmutatorEntity transmutator)) return;
		transmutator.addEssenceForStack(stackAt0);
		tryToLearnTransmutation(stackAt0, this.lastInteractingPlayer);
		this.refreshResultSlots(this.lastInteractingPlayer);
	}

	private void onRemoveItemStack(Player player, ItemStack stack) {
		if (!(this.container instanceof SpiritualTransmutatorEntity transmutator)) return;
		transmutator.removeEssenceForStack(stack);
		this.refreshResultSlots(player);
	}

	public SpiritualTransmutatorEntity getTransmutatorContainer() {
		if (this.container instanceof SpiritualTransmutatorEntity transmutator) return transmutator;
		return null;
	}

	private void clearItems() {
		this.itemContainer.clearContent();
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
		return knownItems.stream().filter(item ->
				item.getName(item.getDefaultInstance()).getString(120).toLowerCase().matches("(.*)" + this.searchName.toLowerCase() + "(.*)")
						&& this.getItemCountByEssence(item) > 0
		).collect(Collectors.toList());
	}

	public int getItemCountByEssence(Item item) {
		if (!(this.container instanceof SpiritualTransmutatorEntity transmutator)) return 0;
		if (!(ItemSpiritValueUtils.SPIRIT_VALUE_BY_ITEM.containsKey(item))) return 0;
		ItemSpiritValue values = ItemSpiritValueUtils.SPIRIT_VALUE_BY_ITEM.get(item);
		var storedEssence = transmutator.getStoredEssence();
		HashMap<Elements, Integer> countByElement = new HashMap<>();
		for (var element : Elements.values()) {
			if (values.getElementAmount(element).compareTo(BigDecimal.ZERO) > 0) {
				countByElement.put(element, storedEssence.get(element).divide(values.getElementAmount(element), RoundingMode.HALF_DOWN).intValue());
			}
		}
		int outputCount = 0x7fffffff;
		if (countByElement.isEmpty()) outputCount = 0;
		for (var element : countByElement.keySet()) {
			outputCount = Math.min(outputCount, countByElement.get(element));
		}
		return outputCount;
	}

	public ItemStack getResultAtSlot(int slotIndex, Player player, @Nullable List<Item> matchingName) {
		if (slotIndex == 0) return ItemStack.EMPTY;
		if (matchingName == null) {
			matchingName = getMatchingItemsSet(player);
		}
		Item item = matchingName.get(slotIndex - 1);
		ItemStack outputStack = item.getDefaultInstance();
		outputStack.setCount(this.getItemCountByEssence(item));
		return outputStack;
	}

	public void refreshResultSlots(Player player) {
		this.clearItems();
		this.lastInteractingPlayer = player;
		var matchingName = getMatchingItemsSet(player);
		for (int i = 1; i < Math.min(matchingName.size() + 1, 12); i++) {
			this.slots.get(i).set(this.getResultAtSlot(i, player, matchingName));
		}
	}

	@Override
	public void slotsChanged(Container pContainer) {
		super.slotsChanged(pContainer);
	}

	@Override
	protected boolean moveItemStackTo(ItemStack pStack, int pStartIndex, int pEndIndex, boolean pReverseDirection) {
		return super.moveItemStackTo(pStack, pStartIndex, pEndIndex, pReverseDirection);
	}

	@Override
	public ItemStack quickMoveStack(Player player, int slot) {
		var resultStack = ItemStack.EMPTY;
		var selectedSlot = this.slots.get(slot);
		if (!selectedSlot.hasItem()) return resultStack;
		var slotStack = selectedSlot.getItem();
		resultStack = slotStack.copy();
		if (slot >= 1 && slot <= 11) {
			if (!this.moveItemStackTo(slotStack, 12, 48, true)) return ItemStack.EMPTY;
			selectedSlot.onQuickCraft(slotStack, resultStack);
		}
		if (slot > 11) {
			if (!this.moveItemStackTo(slotStack, 0, 1, false)) return ItemStack.EMPTY;
		}

		if (slotStack.isEmpty()) {
			selectedSlot.set(ItemStack.EMPTY);
		} else {
			selectedSlot.setChanged();
		}

		if (slotStack.getCount() == resultStack.getCount()) {
			return ItemStack.EMPTY;
		}

		selectedSlot.onTake(player, resultStack);

		return resultStack;
	}

	@Override
	public boolean stillValid(Player pPlayer) {
		if (!(this.container instanceof SpiritualTransmutatorEntity transmutator)) return false;
		return transmutator.stillValid(pPlayer);
	}

}
