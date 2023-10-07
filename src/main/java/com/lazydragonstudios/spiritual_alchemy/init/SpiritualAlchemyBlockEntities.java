package com.lazydragonstudios.spiritual_alchemy.init;

import com.lazydragonstudios.spiritual_alchemy.SpiritualAlchemy;
import com.lazydragonstudios.spiritual_alchemy.block.entity.SpiritualTransmutatorEntity;
import net.minecraft.Util;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("ConstantConditions")
public class SpiritualAlchemyBlockEntities {

	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, SpiritualAlchemy.MOD_ID);

	public static final RegistryObject<BlockEntityType<SpiritualTransmutatorEntity>> SPIRITUAL_TRANSMUTATOR_TYPE = BLOCK_ENTITIES.register("spiritual_transmutator_type",
			() -> BlockEntityType.Builder
					.of(SpiritualTransmutatorEntity::new,
							SpiritualAlchemyBlocks.SPIRITUAL_TRANSMUTATOR.get(),
							SpiritualAlchemyBlocks.ULTIMATE_SPIRITUAL_TRANSMUTATOR.get())
					.build(Util.fetchChoiceType(References.BLOCK_ENTITY, "spiritual_transmutator_type"))
	);

}
