package com.lazydragonstudios.spiritual_alchemy.networking;

import com.lazydragonstudios.spiritual_alchemy.knowledge.TransmutationKnowledge;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record SendTransmutationKnowledgeToPlayer(CompoundTag tag) {

	public static void encode(SendTransmutationKnowledgeToPlayer msg, FriendlyByteBuf buf) {
		buf.writeNbt(msg.tag);
	}

	public static SendTransmutationKnowledgeToPlayer decode(FriendlyByteBuf buf) {
		return new SendTransmutationKnowledgeToPlayer(buf.readNbt());
	}

	public static void handleMessage(SendTransmutationKnowledgeToPlayer msg, Supplier<NetworkEvent.Context> ctxSupplier) {
		var ctx = ctxSupplier.get();
		var dir = ctx.getDirection();
		if(dir.getReceptionSide().isServer()) return;
		ctx.enqueueWork(() -> {
			handleClientMassage(msg);
		});
	}

	@OnlyIn(Dist.CLIENT)
	 private static void handleClientMassage(SendTransmutationKnowledgeToPlayer msg) {
		var player = Minecraft.getInstance().player;
		var knowledge = TransmutationKnowledge.get(player);
		knowledge.deserialize(msg.tag);
	 }
}
