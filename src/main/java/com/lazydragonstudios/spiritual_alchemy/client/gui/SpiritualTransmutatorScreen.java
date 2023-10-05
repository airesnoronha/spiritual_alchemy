package com.lazydragonstudios.spiritual_alchemy.client.gui;

import com.lazydragonstudios.spiritual_alchemy.SpiritualAlchemy;
import com.lazydragonstudios.spiritual_alchemy.container.SpiritualTransmutatorMenu;
import com.lazydragonstudios.spiritual_alchemy.networking.SearchItemInTransmutator;
import com.lazydragonstudios.spiritual_alchemy.networking.SpiritualPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SpiritualTransmutatorScreen extends AbstractContainerScreen<SpiritualTransmutatorMenu> {

	public static final ResourceLocation GUI_TEXTURE = new ResourceLocation(SpiritualAlchemy.MOD_ID, "textures/gui/spiritual_transmutator_gui.png");

	private EditBox itemSearch;

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
		guiGraphics.pose().popPose();
	}
}
