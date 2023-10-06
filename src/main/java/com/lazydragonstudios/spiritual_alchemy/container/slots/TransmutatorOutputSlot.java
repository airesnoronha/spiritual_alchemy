package com.lazydragonstudios.spiritual_alchemy.container.slots;

import com.lazydragonstudios.spiritual_alchemy.block.entity.SpiritualTransmutatorEntity;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;
import java.util.function.BiConsumer;

public class TransmutatorOutputSlot extends OutputSlot {

	private BiConsumer<Player, ItemStack> onTakeItemResponder;

	public TransmutatorOutputSlot(Container pContainer, int pSlot, int pX, int pY, BiConsumer<Player, ItemStack> onTakeItemResponder) {
		super(pContainer, pSlot, pX, pY);
		this.onTakeItemResponder = onTakeItemResponder;
	}

	@Override
	public Optional<ItemStack> tryRemove(int pCount, int pDecrement, Player pPlayer) {
		var itemStack = this.container.getItem(this.getSlotIndex());
		pCount = Math.min(pCount, itemStack.getMaxStackSize());
		return super.tryRemove(pCount, pDecrement, pPlayer);
	}

	@Override
	public void onTake(Player pPlayer, ItemStack pStack) {
		super.onTake(pPlayer, pStack);
		onTakeItemResponder.accept(pPlayer, pStack);
	}

	@Override
	public int getMaxStackSize(ItemStack pStack) {
		return Integer.MAX_VALUE;
	}
}
