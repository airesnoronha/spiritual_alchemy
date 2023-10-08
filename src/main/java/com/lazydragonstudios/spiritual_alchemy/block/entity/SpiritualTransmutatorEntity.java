package com.lazydragonstudios.spiritual_alchemy.block.entity;

import com.lazydragonstudios.spiritual_alchemy.block.SpiritualTransmutator;
import com.lazydragonstudios.spiritual_alchemy.container.SpiritualTransmutatorMenu;
import com.lazydragonstudios.spiritual_alchemy.init.SpiritualAlchemyBlockEntities;
import com.lazydragonstudios.spiritual_alchemy.knowledge.Elements;
import com.lazydragonstudios.spiritual_alchemy.utils.ItemSpiritValueUtils;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SpiritualTransmutatorEntity extends BaseContainerBlockEntity {

	private final BigDecimal maxStoredEssence;

	protected HashMap<Elements, BigDecimal> storedEssence = new HashMap<>();

	public SpiritualTransmutatorEntity(BlockPos pPos, BlockState pBlockState) {
		this(pPos, pBlockState, pBlockState.getBlock() instanceof SpiritualTransmutator transmutator ? transmutator.getMaxStoredEssence() : new BigDecimal("128"));
	}

	public SpiritualTransmutatorEntity(BlockPos pPos, BlockState pBlockState, BigDecimal maxStoredEssence) {
		super(SpiritualAlchemyBlockEntities.SPIRITUAL_TRANSMUTATOR_TYPE.get(), pPos, pBlockState);
		this.maxStoredEssence = maxStoredEssence;
		storedEssence.put(Elements.WATER, BigDecimal.ZERO);
		storedEssence.put(Elements.WOOD, BigDecimal.ZERO);
		storedEssence.put(Elements.FIRE, BigDecimal.ZERO);
		storedEssence.put(Elements.EARTH, BigDecimal.ZERO);
		storedEssence.put(Elements.METAL, BigDecimal.ZERO);
	}

	public void addEssenceForStack(ItemStack itemStack) {
		var item = itemStack.getItem();
		var values = ItemSpiritValueUtils.SPIRIT_VALUE_BY_ITEM.get(item);
		if (values == null) return;
		for (var element : Elements.values()) {
			BigDecimal multiplicand = BigDecimal.valueOf(itemStack.getCount());
			if (itemStack.isDamageableItem()) {
				multiplicand = BigDecimal.valueOf(itemStack.getMaxDamage() - itemStack.getDamageValue()).divide(BigDecimal.valueOf(itemStack.getMaxDamage()), RoundingMode.HALF_DOWN).setScale(4);
			}
			this.storedEssence.put(element, this.storedEssence.getOrDefault(element, BigDecimal.ZERO).add(values.getElementAmount(element).multiply(multiplicand)).min(this.maxStoredEssence));
		}
		this.setChanged();
		if (this.level != null && !this.level.isClientSide) {
			this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 2);
		}
	}

	public void removeEssenceForStack(ItemStack itemStack) {
		var item = itemStack.getItem();
		var values = ItemSpiritValueUtils.SPIRIT_VALUE_BY_ITEM.get(item);
		if (values == null) return;
		for (var element : Elements.values()) {
			this.storedEssence.put(element, this.storedEssence.getOrDefault(element, BigDecimal.ZERO).subtract(values.getElementAmount(element).multiply(BigDecimal.valueOf(itemStack.getCount()))).max(BigDecimal.ZERO));
		}
		if (this.level != null && !this.level.isClientSide) {
			this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 2);
		}
	}

	public BigDecimal getMaxStoredEssence() {
		return maxStoredEssence;
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

	public HashMap<Elements, BigDecimal> getStoredEssence() {
		return storedEssence;
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

	@Nullable
	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag() {
		var tag = super.getUpdateTag();
		tag.putString("waterAmount", this.storedEssence.get(Elements.WATER).toPlainString());
		tag.putString("woodAmount", this.storedEssence.get(Elements.WOOD).toPlainString());
		tag.putString("fireAmount", this.storedEssence.get(Elements.FIRE).toPlainString());
		tag.putString("earthAmount", this.storedEssence.get(Elements.EARTH).toPlainString());
		tag.putString("metalAmount", this.storedEssence.get(Elements.METAL).toPlainString());
		return tag;
	}

	@Override
	public void handleUpdateTag(CompoundTag tag) {
		super.handleUpdateTag(tag);
		if (tag.contains("waterAmount"))
			this.storedEssence.put(Elements.WATER, new BigDecimal(tag.getString("waterAmount")));
		if (tag.contains("woodAmount"))
			this.storedEssence.put(Elements.WOOD, new BigDecimal(tag.getString("woodAmount")));
		if (tag.contains("fireAmount"))
			this.storedEssence.put(Elements.FIRE, new BigDecimal(tag.getString("fireAmount")));
		if (tag.contains("earthAmount"))
			this.storedEssence.put(Elements.EARTH, new BigDecimal(tag.getString("earthAmount")));
		if (tag.contains("metalAmount"))
			this.storedEssence.put(Elements.METAL, new BigDecimal(tag.getString("metalAmount")));
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
