package com.lazydragonstudios.spiritual_alchemy.block.entity;

import com.lazydragonstudios.spiritual_alchemy.container.SpiritualTransmutatorMenu;
import com.lazydragonstudios.spiritual_alchemy.init.SpiritualAlchemyBlockEntities;
import com.lazydragonstudios.spiritual_alchemy.knowledge.Elements;
import com.lazydragonstudios.spiritual_alchemy.knowledge.TransmutationKnowledge;
import com.lazydragonstudios.spiritual_alchemy.utils.ItemSpiritValueUtils;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SpiritualTransmutatorEntity extends BaseContainerBlockEntity {

	private final BigDecimal maxStoredEssence = new BigDecimal("15.0");

	protected HashMap<Elements, BigDecimal> storedEssence = new HashMap<>();

	public SpiritualTransmutatorEntity(BlockPos pPos, BlockState pBlockState) {
		super(SpiritualAlchemyBlockEntities.SPIRITUAL_TRANSMUTATOR_TYPE.get(), pPos, pBlockState);
		storedEssence.put(Elements.WATER, BigDecimal.ZERO);
		storedEssence.put(Elements.WOOD, BigDecimal.ZERO);
		storedEssence.put(Elements.FIRE, BigDecimal.ZERO);
		storedEssence.put(Elements.EARTH, BigDecimal.ZERO);
		storedEssence.put(Elements.METAL, BigDecimal.ZERO);
	}

	@Override
	public void clearContent() {
	}

	@Override
	public int getContainerSize() {
		return 0;
	}

	@Override
	public boolean isEmpty() {
		return true;
	}

	@Override
	public ItemStack getItem(int slot) {
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack removeItem(int pSlot, int pAmount) {
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack removeItemNoUpdate(int pSlot) {
		return ItemStack.EMPTY;
	}

	@Override
	public void setItem(int slot, ItemStack stack) {
	}

	@Override
	public boolean stillValid(Player player) {
		if (this.level == null) return false;
		if (this.level.getBlockEntity(this.worldPosition) != this) {
			return false;
		} else {
			return player.distanceToSqr((double) this.worldPosition.getX() + 0.5D, (double) this.worldPosition.getY() + 0.5D, (double) this.worldPosition.getZ() + 0.5D) <= 64.0D;
		}
	}

	@Override
	protected Component getDefaultName() {
		return Component.translatable("block.spiritual_alchemy.spiritual_transmutator");
	}

	@Override
	protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
		return new SpiritualTransmutatorMenu(pContainerId, pInventory, this);
	}

	@Override
	protected void saveAdditional(CompoundTag pTag) {
		super.saveAdditional(pTag);
		pTag.putString("waterAmount", this.storedEssence.get(Elements.WATER).toPlainString());
		pTag.putString("woodAmount", this.storedEssence.get(Elements.WOOD).toPlainString());
		pTag.putString("fireAmount", this.storedEssence.get(Elements.FIRE).toPlainString());
		pTag.putString("earthAmount", this.storedEssence.get(Elements.EARTH).toPlainString());
		pTag.putString("metalAmount", this.storedEssence.get(Elements.METAL).toPlainString());
	}

	@Override
	public void load(CompoundTag pTag) {
		super.load(pTag);
		if (pTag.contains("waterAmount"))
			this.storedEssence.put(Elements.WATER, new BigDecimal(pTag.getString("waterAmount")));
		if (pTag.contains("woodAmount"))
			this.storedEssence.put(Elements.WOOD, new BigDecimal(pTag.getString("woodAmount")));
		if (pTag.contains("fireAmount"))
			this.storedEssence.put(Elements.FIRE, new BigDecimal(pTag.getString("fireAmount")));
		if (pTag.contains("earthAmount"))
			this.storedEssence.put(Elements.EARTH, new BigDecimal(pTag.getString("earthAmount")));
		if (pTag.contains("metalAmount"))
			this.storedEssence.put(Elements.METAL, new BigDecimal(pTag.getString("metalAmount")));
	}

	@Override
	public void startOpen(Player pPlayer) {
		super.startOpen(pPlayer);
	}
}
