package com.lazydragonstudios.spiritual_alchemy.init;

import com.lazydragonstudios.spiritual_alchemy.SpiritualAlchemy;
import com.lazydragonstudios.spiritual_alchemy.transmutation.ItemSpiritValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DataPackRegistryEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryEvents {

	@SubscribeEvent
	public static void onCreateDatapackRegistries(final DataPackRegistryEvent.NewRegistry event) {
		SpiritualAlchemy.LOGGER.info("Creating Datapack Registries");
		event.dataPackRegistry(SpiritualAlchemyRegistries.ITEM_SPIRIT_VALUE_REGISTRY_KEY, ItemSpiritValue.CODEC);
	}

}
