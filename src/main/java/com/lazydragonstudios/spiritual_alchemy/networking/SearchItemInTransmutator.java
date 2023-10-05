package com.lazydragonstudios.spiritual_alchemy.networking;

import com.lazydragonstudios.spiritual_alchemy.container.SpiritualTransmutatorMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record SearchItemInTransmutator(String name) {

	public static void encode(SearchItemInTransmutator msg, FriendlyByteBuf buf) {
		buf.writeComponent(Component.literal(msg.name));
	}

	public static SearchItemInTransmutator decode(FriendlyByteBuf buf) {
		return new SearchItemInTransmutator(buf.readComponent().getString());
	}

	public static void handleMessage(SearchItemInTransmutator msg, Supplier<NetworkEvent.Context> ctxSupplier) {
		var ctx = ctxSupplier.get();
		var side = ctx.getDirection().getReceptionSide();
		if (side.isClient()) return;
		ctx.enqueueWork(() -> {
			var serverPlayer = ctx.getSender();
			if (serverPlayer.containerMenu instanceof SpiritualTransmutatorMenu transmutator) {
				transmutator.setItemName(msg.name, serverPlayer);
			}
		});
	}
}
