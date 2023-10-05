package com.lazydragonstudios.spiritual_alchemy.client;

import com.lazydragonstudios.spiritual_alchemy.client.gui.SpiritualTransmutatorScreen;
import com.lazydragonstudios.spiritual_alchemy.init.SpiritualAlchemyMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetupEventHandler {
	@SubscribeEvent
	public static  void onClientSetup(final FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			MenuScreens.register(SpiritualAlchemyMenuTypes.SPIRITUAL_TRANSMUTATOR.get(), SpiritualTransmutatorScreen::new);
		});
	}
}
