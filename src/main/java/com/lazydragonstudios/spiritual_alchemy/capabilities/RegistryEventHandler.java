package com.lazydragonstudios.spiritual_alchemy.capabilities;

import com.lazydragonstudios.spiritual_alchemy.knowledge.TransmutationKnowledge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryEventHandler {

	@SubscribeEvent
	public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
		event.register(TransmutationKnowledge.class);
	}

}
