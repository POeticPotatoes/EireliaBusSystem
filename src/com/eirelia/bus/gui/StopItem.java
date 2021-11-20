package com.eirelia.bus.gui;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.eirelia.bus.PlayerHandler;
import com.eirelia.bus.objects.BusStop;

public class StopItem extends ItemStack{
	private static final DecimalFormat df = new DecimalFormat("##.##");
	private static final Material type = Material.POPPED_CHORUS_FRUIT;
	private float multiplier = PlayerHandler.multiplier();
	
	static {
		df.setRoundingMode(RoundingMode.DOWN);
	}
	
	public StopItem(BusStop b, int i) {
		super(type);
		ItemMeta meta = getItemMeta();
		meta.setDisplayName(b.getName());
		meta.setLore(List.of("$" + df.format(i * multiplier)));
		this.setItemMeta(meta);
	}
}
