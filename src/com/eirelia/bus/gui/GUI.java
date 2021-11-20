package com.eirelia.bus.gui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.eirelia.bus.objects.BusStop;

public class GUI {
	private Inventory INV;
	private BusStop stop;
	private List<StopHolder> stops;
	private static Map<Player, GUI> viewing = new HashMap<Player, GUI>();
	private static final int size = 18;
	
	public GUI(BusStop stop) {
		this.stop = stop;
		INV = Bukkit.createInventory(null, size, ChatColor.DARK_PURPLE + "Line " + stop.getLine().getName());
		stops = new StopList(stop).getSortedStops();
		if (stops == null) return;
		for (StopHolder holder: stops) {
			ItemStack i = new StopItem(holder.getStop(), holder.getCost());
			INV.addItem(i);
		}
	}
	
	public void openInventory(Player p) {
		p.openInventory(INV);
		register(p);
	}
	
	public void register(Player p) {
		viewing.put(p, this);
	}
	
	public BusStop getStop() {
		return stop;
	}
	
	public StopHolder getStopHolder(int i) {
		if (i >= size) return null;
		return stops.get(i);
	}
	
	public static void closeInventory(Player p) {
		p.closeInventory();
	}
	
	public static void deregister(Player p) {
		viewing.remove(p);
	}
	
	public static Boolean isViewing(Player p) {
		return viewing.containsKey(p);
	}
	
	public static GUI getGUI(Player p) {
		return viewing.get(p);
	}
}
