package com.lazydragonstudios.spiritual_alchemy.container.slots;

import com.lazydragonstudios.spiritual_alchemy.utils.ItemSpiritValueUtils;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class TransmutatorDestroySlot extends Slot {

	public TransmutatorDestroySlot(Container pContainer, int pSlot, int pX, int pY) {
		super(pContainer, pSlot, pX, pY);
	}

	@Override
	public boolean mayPlace(ItemStack pStack) {
		var item = pStack.getItem();
		return ItemSpiritValueUtils.SPIRIT_VALUE_BY_ITEM.containsKey(item);
	}



}
