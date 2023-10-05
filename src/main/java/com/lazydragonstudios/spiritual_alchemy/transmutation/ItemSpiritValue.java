package com.lazydragonstudios.spiritual_alchemy.transmutation;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.math.BigDecimal;

public class ItemSpiritValue {

	public final Item item;

	public final BigDecimal waterAmount;

	public final BigDecimal woodAmount;

	public final BigDecimal fireAmount;

	public final BigDecimal earthAmount;

	public final BigDecimal metalAmount;

	public static final Codec<ItemSpiritValue> CODEC = RecordCodecBuilder.create(instance -> instance.group(
					ForgeRegistries.ITEMS.getCodec().fieldOf("item").forGetter(ItemSpiritValue::getItem),
					Codec.STRING.fieldOf("waterAmount").forGetter((ItemSpiritValue itemSpiritValue) -> itemSpiritValue.getWaterAmount().toPlainString()),
					Codec.STRING.fieldOf("woodAmount").forGetter((ItemSpiritValue itemSpiritValue) -> itemSpiritValue.getWoodAmount().toPlainString()),
					Codec.STRING.fieldOf("fireAmount").forGetter((ItemSpiritValue itemSpiritValue) -> itemSpiritValue.getFireAmount().toPlainString()),
					Codec.STRING.fieldOf("earthAmount").forGetter((ItemSpiritValue itemSpiritValue) -> itemSpiritValue.getEarthAmount().toPlainString()),
					Codec.STRING.fieldOf("metalAmount").forGetter((ItemSpiritValue itemSpiritValue) -> itemSpiritValue.getMetalAmount().toPlainString())
			).apply(instance,
					(item, water, wood, fire, earth, metal) -> new ItemSpiritValue(item,
							new BigDecimal(water),
							new BigDecimal(wood),
							new BigDecimal(fire),
							new BigDecimal(earth),
							new BigDecimal(metal))
			)
	);

	public ItemSpiritValue(Item item, BigDecimal waterAmount, BigDecimal woodAmount, BigDecimal fireAmount, BigDecimal earthAmount, BigDecimal metalAmount) {
		this.item = item;
		this.waterAmount = waterAmount;
		this.woodAmount = woodAmount;
		this.fireAmount = fireAmount;
		this.earthAmount = earthAmount;
		this.metalAmount = metalAmount;
	}

	public Item getItem() {
		return item;
	}

	public BigDecimal getWaterAmount() {
		return waterAmount;
	}

	public BigDecimal getWoodAmount() {
		return woodAmount;
	}

	public BigDecimal getFireAmount() {
		return fireAmount;
	}

	public BigDecimal getEarthAmount() {
		return earthAmount;
	}

	public BigDecimal getMetalAmount() {
		return metalAmount;
	}
}
