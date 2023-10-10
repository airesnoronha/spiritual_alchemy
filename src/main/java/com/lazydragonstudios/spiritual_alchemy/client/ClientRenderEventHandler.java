package com.lazydragonstudios.spiritual_alchemy.client;

import com.google.common.collect.ImmutableMap;
import com.lazydragonstudios.spiritual_alchemy.SpiritualAlchemy;
import com.lazydragonstudios.spiritual_alchemy.knowledge.Elements;
import com.lazydragonstudios.spiritual_alchemy.knowledge.TransmutationKnowledge;
import com.lazydragonstudios.spiritual_alchemy.utils.ItemSpiritValueUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientRenderEventHandler {

	public static final ResourceLocation WATER_TEXTURE_LOCATION = new ResourceLocation(SpiritualAlchemy.MOD_ID, "textures/elements/water.png");

	public static final ResourceLocation WOOD_TEXTURE_LOCATION = new ResourceLocation(SpiritualAlchemy.MOD_ID, "textures/elements/wood.png");

	public static final ResourceLocation FIRE_TEXTURE_LOCATION = new ResourceLocation(SpiritualAlchemy.MOD_ID, "textures/elements/fire.png");

	public static final ResourceLocation EARTH_TEXTURE_LOCATION = new ResourceLocation(SpiritualAlchemy.MOD_ID, "textures/elements/earth.png");

	public static final ResourceLocation METAL_TEXTURE_LOCATION = new ResourceLocation(SpiritualAlchemy.MOD_ID, "textures/elements/metal.png");

	public static final ImmutableMap<Elements, ResourceLocation> TEXTURE_BY_ELEMENT = ImmutableMap.of(
			Elements.WATER, WATER_TEXTURE_LOCATION,
			Elements.WOOD, WOOD_TEXTURE_LOCATION,
			Elements.FIRE, FIRE_TEXTURE_LOCATION,
			Elements.EARTH, EARTH_TEXTURE_LOCATION,
			Elements.METAL, METAL_TEXTURE_LOCATION
	);

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onRenderToolTips(final RenderTooltipEvent.Pre event) {
		var item = event.getItemStack().getItem();
		var values = ItemSpiritValueUtils.SPIRIT_VALUE_BY_ITEM.get(item);
		if (values == null) return;
		if (Minecraft.getInstance().player == null) return;
		TransmutationKnowledge knowledge = TransmutationKnowledge.get(Minecraft.getInstance().player);
		var itemLocation = ForgeRegistries.ITEMS.getKey(item);
		if (!knowledge.getKnownTransmutations().contains(itemLocation)) return;
		HashMap<Elements, BigDecimal> displayValues = new HashMap<>();
		for (var element : Elements.values()) {
			if (values.getElementAmount(element).compareTo(BigDecimal.ZERO) <= 0) continue;
			displayValues.put(element, values.getElementAmount(element));
		}
		var count = displayValues.size();
		int stepSize = 30;
		int width = 6 + count * stepSize;
		int tooltipX = event.getX() - width - 5;
		int tooltipY = event.getY() + 5;
		TooltipRenderUtil.renderTooltipBackground(event.getGraphics(), tooltipX, tooltipY, width, 33, 400);
		event.getGraphics().pose().pushPose();
		event.getGraphics().pose().translate(tooltipX + 3, tooltipY + 3, 401);
		for (var element : displayValues.keySet()) {
			RenderSystem.enableBlend();
			event.getGraphics().blit(TEXTURE_BY_ELEMENT.get(element), (stepSize-16)/2, 0, 0, 0, 16, 16, 16, 16);
			event.getGraphics().drawCenteredString(event.getFont(), values.getElementAmount(element).setScale(2, RoundingMode.HALF_DOWN).toPlainString(), stepSize/2, 17, 0xFFffFFff);
			event.getGraphics().pose().translate(stepSize, 0, 0);
			RenderSystem.disableBlend();
		}
		event.getGraphics().pose().popPose();
	}
}
