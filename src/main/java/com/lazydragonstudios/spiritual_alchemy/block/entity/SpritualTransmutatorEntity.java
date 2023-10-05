package com.lazydragonstudios.spiritual_alchemy.block.entity;

import com.lazydragonstudios.spiritual_alchemy.init.SpiritualAlchemyRegistries;
import com.lazydragonstudios.spiritual_alchemy.knowledge.Elements;
import com.lazydragonstudios.spiritual_alchemy.transmutation.ItemSpiritValue;
import com.lazydragonstudios.spiritual_alchemy.utils.ItemSpiritValueUtils;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.ParametersAreNonnullByDefault;
import java.math.BigDecimal;
import java.util.HashMap;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SpritualTransmutatorEntity extends BaseContainerBlockEntity {

	private final BigDecimal maxStoredEssence = new BigDecimal("15.0");

	protected NonNullList<ItemStack> items = NonNullList.withSize(10, ItemStack.EMPTY);

	protected HashMap<Elements, BigDecimal> storedEssence = new HashMap<>();

	protected SpritualTransmutatorEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
		super(pType, pPos, pBlockState);
		storedEssence.put(Elements.WATER, BigDecimal.ZERO);
		storedEssence.put(Elements.WOOD, BigDecimal.ZERO);
		storedEssence.put(Elements.FIRE, BigDecimal.ZERO);
		storedEssence.put(Elements.EARTH, BigDecimal.ZERO);
		storedEssence.put(Elements.METAL, BigDecimal.ZERO);
	}

	@Override
	public void clearContent() {
		this.items.clear();
	}

	@Override
	public int getContainerSize() {
		return 10;
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : this.items) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack getItem(int slot) {
		return this.items.get(slot);
	}

	@Override
	public ItemStack removeItem(int pSlot, int pAmount) {
		return null;
	}

	@Override
	public ItemStack removeItemNoUpdate(int pSlot) {
		return null;
	}

	@Override
	public void setItem(int slot, ItemStack stack) {
		if(slot == 0) {
			if(this.level == null) return;
			var itemSpiritValue = ItemSpiritValueUtils.SPIRIT_VALUE_BY_ITEM.get(stack.getItem());
			if(itemSpiritValue == null) return;
			this.storedEssence.put(Elements.WATER ,this.storedEssence.get(Elements.WATER).add(itemSpiritValue.waterAmount).min(maxStoredEssence));
			this.storedEssence.put(Elements.WOOD ,this.storedEssence.get(Elements.WOOD).add(itemSpiritValue.woodAmount).min(maxStoredEssence));
			this.storedEssence.put(Elements.FIRE ,this.storedEssence.get(Elements.FIRE).add(itemSpiritValue.fireAmount).min(maxStoredEssence));
			this.storedEssence.put(Elements.EARTH ,this.storedEssence.get(Elements.EARTH).add(itemSpiritValue.earthAmount).min(maxStoredEssence));
			this.storedEssence.put(Elements.METAL ,this.storedEssence.get(Elements.METAL).add(itemSpiritValue.metalAmount).min(maxStoredEssence));
		}
	}

	@Override
	public boolean stillValid(Player pPlayer) {
		return false;
	}

	@Override
	protected Component getDefaultName() {
		return null;
	}

	@Override
	protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
		return null;
	}
}
