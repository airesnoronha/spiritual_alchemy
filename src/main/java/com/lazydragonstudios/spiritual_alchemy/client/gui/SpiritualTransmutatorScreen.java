package com.lazydragonstudios.spiritual_alchemy.client.gui;

import com.google.common.collect.ImmutableMap;
import com.lazydragonstudios.spiritual_alchemy.SpiritualAlchemy;
import com.lazydragonstudios.spiritual_alchemy.block.entity.SpiritualTransmutatorEntity;
import com.lazydragonstudios.spiritual_alchemy.container.SpiritualTransmutatorMenu;
import com.lazydragonstudios.spiritual_alchemy.knowledge.Elements;
import com.lazydragonstudios.spiritual_alchemy.networking.SearchItemInTransmutator;
import com.lazydragonstudios.spiritual_alchemy.networking.SpiritualPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class SpiritualTransmutatorScreen extends AbstractContainerScreen<SpiritualTransmutatorMenu> {

	public static final ResourceLocation GUI_TEXTURE = new ResourceLocation(SpiritualAlchemy.MOD_ID, "textures/gui/spiritual_transmutator_gui.png");

	public static final ImmutableMap<Elements, Point> BAR_LOCATION_BY_ELEMENT = ImmutableMap.of(
			Elements.FIRE, new Point(86,30),
			Elements.EARTH, new Point(135,63),
			Elements.METAL, new Point(126,121),
			Elements.WATER, new Point(47,121),
			Elements.WOOD, new Point(36,63)
	);

	private EditBox itemSearch;

	private SpiritualTransmutatorEntity transmutator;

	public SpiritualTransmutatorScreen(SpiritualTransmutatorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
		super(pMenu, pPlayerInventory, pTitle);
		this.imageWidth = 205;
		this.imageHeight = 255;
	}

	@Override
	protected void init() {
		super.init();
		this.itemSearch = new EditBox(this.font, this.leftPos + 24, this.topPos + 161, 156, 10, Component.empty());
		this.itemSearch.setBordered(false);
		this.itemSearch.setMaxLength(120);
		this.itemSearch.setResponder(this::onItemNameChanged);
		this.addRenderableWidget(this.itemSearch);
		this.transmutator = this.menu.getTransmutatorContainer();
	}

	private void onItemNameChanged(String name) {
		SpiritualPacketHandler.INSTANCE.sendToServer(new SearchItemInTransmutator(name));
		this.menu.setItemName(name, Minecraft.getInstance().player);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int pMouseX, int pMouseY) {
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
		this.renderBackground(guiGraphics);
		guiGraphics.pose().pushPose();
		guiGraphics.pose().translate(this.leftPos, this.topPos, 0);
		guiGraphics.blit(GUI_TEXTURE, 0, 0, 0, 0, 205, 255);
		this.transmutator = this.menu.getTransmutatorContainer();
		if(transmutator != null) {
			for (var element : Elements.values()) {
				int barFill = new BigDecimal("38").multiply(transmutator.getStoredEssence().get(element)).divide(transmutator.getMaxStoredEssence(), RoundingMode.HALF_UP).intValue();
				Point point = BAR_LOCATION_BY_ELEMENT.get(element);
				if(point == null) continue;
				guiGraphics.blit(GUI_TEXTURE, point.x, point.y+38-barFill, 205, 85+38-barFill, 38, barFill, 256, 256);
			}
		}
		guiGraphics.pose().popPose();
		this.renderTooltip(guiGraphics, mouseX, mouseY);
	}
}
