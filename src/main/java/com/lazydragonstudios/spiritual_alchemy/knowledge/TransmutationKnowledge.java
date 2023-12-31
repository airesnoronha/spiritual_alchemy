package com.lazydragonstudios.spiritual_alchemy.knowledge;

import com.lazydragonstudios.spiritual_alchemy.capabilities.TransmutationKnowledgeProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

import java.util.LinkedList;

public class TransmutationKnowledge {

	public static TransmutationKnowledge get(LivingEntity entity) {
		var capOpt = entity.getCapability(TransmutationKnowledgeProvider.PROVIDER).resolve();
		return capOpt.orElse(new TransmutationKnowledge());
	}

	private final LinkedList<ResourceLocation> knownTransmutations;

	public TransmutationKnowledge() {
		this.knownTransmutations = new LinkedList<>();
	}

	public CompoundTag serialize() {
		CompoundTag tag = new CompoundTag();
		ListTag knownList = new ListTag();
		this.knownTransmutations.forEach(kt -> knownList.add(StringTag.valueOf(kt.toString())));
		tag.put("knowledge", knownList);
		return tag;
	}

	public void deserialize(CompoundTag tag) {
		if (tag.contains("knowledge")) {
			this.knownTransmutations.clear();
			ListTag knownList = (ListTag) tag.get("knowledge");
			if (knownList == null) return;
			knownList.forEach(ktTag -> this.knownTransmutations.add(new ResourceLocation(ktTag.getAsString())));
			this.knownTransmutations.sort(ResourceLocation::compareTo);
		}
	}

	public LinkedList<ResourceLocation> getKnownTransmutations() {
		return this.knownTransmutations;
	}

}
