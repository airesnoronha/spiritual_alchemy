package com.lazydragonstudios.spiritual_alchemy.transmutation;

import com.lazydragonstudios.spiritual_alchemy.knowledge.Elements;
import com.lazydragonstudios.spiritual_alchemy.utils.ItemSpiritValueUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.math.BigDecimal;
import java.util.HashMap;

public class ItemSpiritValue {

	public final Item item;

	private final HashMap<Elements, BigDecimal> essences = new HashMap<>();

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
		this.essences.put(Elements.WATER, waterAmount);
		this.essences.put(Elements.WOOD, woodAmount);
		this.essences.put(Elements.FIRE, fireAmount);
		this.essences.put(Elements.EARTH, earthAmount);
		this.essences.put(Elements.METAL, metalAmount);
		ItemSpiritValueUtils.SPIRIT_VALUE_BY_ITEM.put(item, this);
	}

	public Item getItem() {
		return item;
	}

	public BigDecimal getWaterAmount() {
		return this.getElementAmount(Elements.WATER);
	}

	public BigDecimal getWoodAmount() {
		return this.getElementAmount(Elements.WOOD);
	}

	public BigDecimal getFireAmount() {
		return this.getElementAmount(Elements.FIRE);
	}

	public BigDecimal getEarthAmount() {
		return this.getElementAmount(Elements.EARTH);
	}

	public BigDecimal getMetalAmount() {
		return this.getElementAmount(Elements.METAL);
	}

	public BigDecimal getElementAmount(Elements element) {
		return this.essences.getOrDefault(element, BigDecimal.ZERO);
	}
}
