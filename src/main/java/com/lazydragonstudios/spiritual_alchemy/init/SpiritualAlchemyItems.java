package com.lazydragonstudios.spiritual_alchemy.init;

import com.lazydragonstudios.spiritual_alchemy.SpiritualAlchemy;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SpiritualAlchemyItems {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SpiritualAlchemy.MOD_ID);

	public static final RegistryObject<Item> SPIRITUAL_TRANSMUTATOR = ITEMS.register("spiritual_transmutator",
			() -> new BlockItem(SpiritualAlchemyBlocks.SPIRITUAL_TRANSMUTATOR.get(), new Item.Properties()));

}
