package com.lazydragonstudios.spiritual_alchemy.capabilities;

import com.lazydragonstudios.spiritual_alchemy.knowledge.TransmutationKnowledge;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TransmutationKnowledgeProvider implements ICapabilitySerializable<CompoundTag> {
	public static Capability<TransmutationKnowledge> PROVIDER = CapabilityManager.get(new CapabilityToken<>() {	});

	public TransmutationKnowledge instance = new TransmutationKnowledge();


	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
		return PROVIDER.orEmpty(cap, LazyOptional.of(() -> instance));
	}

	@Override
	public CompoundTag serializeNBT() {
		return instance.serialize();
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		instance.deserialize(nbt);
	}
}
