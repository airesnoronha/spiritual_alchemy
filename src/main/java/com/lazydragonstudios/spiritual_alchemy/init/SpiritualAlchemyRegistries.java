package com.lazydragonstudios.spiritual_alchemy.init;

import com.lazydragonstudios.spiritual_alchemy.SpiritualAlchemy;
import com.lazydragonstudios.spiritual_alchemy.transmutation.ItemSpiritValue;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class SpiritualAlchemyRegistries {

	public static final ResourceKey<Registry<ItemSpiritValue>> ITEM_SPIRIT_VALUE_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(SpiritualAlchemy.MOD_ID, "item_spirit_value"));

}
