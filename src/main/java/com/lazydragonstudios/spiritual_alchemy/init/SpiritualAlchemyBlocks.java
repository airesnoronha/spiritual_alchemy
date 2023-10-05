package com.lazydragonstudios.spiritual_alchemy.init;

import com.lazydragonstudios.spiritual_alchemy.SpiritualAlchemy;
import com.lazydragonstudios.spiritual_alchemy.block.SpiritualTransmutator;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SpiritualAlchemyBlocks {

public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SpiritualAlchemy.MOD_ID);

public static final RegistryObject<Block> SPIRITUAL_TRANSMUTATOR = BLOCKS.register("spiritual_transmutator",
		() -> new SpiritualTransmutator(BlockBehaviour.Properties.of().strength(1f))
		);

}
