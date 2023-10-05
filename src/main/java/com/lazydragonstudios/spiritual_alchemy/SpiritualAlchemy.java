package com.lazydragonstudios.spiritual_alchemy;

import com.lazydragonstudios.spiritual_alchemy.init.SpiritualAlchemyBlockEntities;
import com.lazydragonstudios.spiritual_alchemy.init.SpiritualAlchemyBlocks;
import com.lazydragonstudios.spiritual_alchemy.init.SpiritualAlchemyItems;
import com.lazydragonstudios.spiritual_alchemy.init.SpiritualAlchemyMenuTypes;
import com.lazydragonstudios.spiritual_alchemy.networking.SpiritualPacketHandler;
import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SpiritualAlchemy.MOD_ID)
public class SpiritualAlchemy
{
    public static final String MOD_ID = "spiritual_alchemy";

    public static final Logger LOGGER = LogUtils.getLogger();

    public SpiritualAlchemy()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);

        SpiritualAlchemyItems.ITEMS.register(modEventBus);
        SpiritualAlchemyBlocks.BLOCKS.register(modEventBus);
        SpiritualAlchemyBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        SpiritualAlchemyMenuTypes.MENU_TYPES.register(modEventBus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }



    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("Registering messages. Check your transmission talisman!");
        SpiritualPacketHandler.registerMessages();
    }

}
