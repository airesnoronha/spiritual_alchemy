package com.lazydragonstudios.spiritual_alchemy.knowledge;

import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class KnowledgeEventHandler {

	@SubscribeEvent
	public static void onPlayerDie(final PlayerEvent.Clone event) {
		event.getOriginal().reviveCaps();
		var oldKnowledge = TransmutationKnowledge.get(event.getOriginal());
		var newKnowledge = TransmutationKnowledge.get(event.getEntity());
		event.getOriginal().invalidateCaps();
		newKnowledge.deserialize(oldKnowledge.serialize());
	}

}
