package com.lazydragonstudios.spiritual_alchemy.container;

import com.lazydragonstudios.spiritual_alchemy.container.slots.TransmutatorDestroySlot;
import com.lazydragonstudios.spiritual_alchemy.knowledge.TransmutationKnowledge;
import com.lazydragonstudios.spiritual_alchemy.transmutation.ItemSpiritValue;
import com.lazydragonstudios.spiritual_alchemy.utils.ItemSpiritValueUtils;
import net.minecraftforge.event.ItemStackedOnOtherEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ContainerEventHandler {

	@SubscribeEvent
	public static void onItemStackedOnDestroySlot(final ItemStackedOnOtherEvent event) {
		if (!(event.getSlot() instanceof TransmutatorDestroySlot)) return;
		var item = event.getStackedOnItem().getItem();
		if (!ItemSpiritValueUtils.SPIRIT_VALUE_BY_ITEM.containsKey(item)) return;
		var knowledge = TransmutationKnowledge.get(event.getPlayer());
		knowledge.getKnownTransmutations().add(ForgeRegistries.ITEMS.getKey(item));
	}
}
