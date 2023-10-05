package com.lazydragonstudios.spiritual_alchemy.capabilities;

import com.lazydragonstudios.spiritual_alchemy.SpiritualAlchemy;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AttachEventHandler {

	@SubscribeEvent
	public static void onAttachingCapabilities(AttachCapabilitiesEvent<Entity> event) {
		if(event.getObject().getType() != EntityType.PLAYER) return;
		event.addCapability(new ResourceLocation(SpiritualAlchemy.MODID, "transmutation_knowledge"), new TransmutationKnowledgeProvider());
	}

	@SubscribeEvent
	public static void onAttachingCapabilities(AttachCapabilitiesEvent<Item> event) {
		if(event.getObject().getType() != EntityType.PLAYER) return;
		event.
		event.addCapability(new ResourceLocation(SpiritualAlchemy.MODID, "transmutation_knowledge"), new TransmutationKnowledgeProvider());
	}
}
