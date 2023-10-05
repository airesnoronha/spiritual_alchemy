package com.lazydragonstudios.spiritual_alchemy.networking;

import com.lazydragonstudios.spiritual_alchemy.SpiritualAlchemy;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

public class SpiritualPacketHandler {

	private static final String PROTOCOL_VERSION = "1";

	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
			new ResourceLocation(SpiritualAlchemy.MOD_ID, "networking"),
			() -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals
	);

	@SuppressWarnings("UnusedAssignment")
	public static void registerMessages() {
		int serverMessagesID = 100;
		INSTANCE.registerMessage(serverMessagesID++, SearchItemInTransmutator.class, SearchItemInTransmutator::encode, SearchItemInTransmutator::decode, SearchItemInTransmutator::handleMessage, Optional.of(NetworkDirection.PLAY_TO_SERVER));
	}

}
