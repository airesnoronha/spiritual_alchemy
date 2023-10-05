package com.lazydragonstudios.spiritual_alchemy.client.gui;

import com.lazydragonstudios.spiritual_alchemy.container.SpiritualTransmutatorMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class SpiritualTransmutatorScreen extends AbstractContainerScreen<SpiritualTransmutatorMenu> {

	public SpiritualTransmutatorScreen(SpiritualTransmutatorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
		super(pMenu, pPlayerInventory, pTitle);
	}

	@Override
	protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {

	}
}
