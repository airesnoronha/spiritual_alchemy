package com.lazydragonstudios.spiritual_alchemy.init;

import com.lazydragonstudios.spiritual_alchemy.SpiritualAlchemy;
import com.lazydragonstudios.spiritual_alchemy.container.SpiritualTransmutatorMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SpiritualAlchemyMenuTypes {
	public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, SpiritualAlchemy.MOD_ID);

	public static RegistryObject<MenuType<SpiritualTransmutatorMenu>> SPIRITUAL_TRANSMUTATOR = MENU_TYPES.register("spiritual_transmutator", () -> IForgeMenuType.create(SpiritualTransmutatorMenu::create));

}
