package com.eirelia.bus.objects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.eirelia.bus.BusSystem;

public class BusLine {
	private final String name;
	private final Map<Block, BusStop> stops = new HashMap<Block, BusStop>();
	
	public BusLine(String name) {
		this.name = name;
		BusSystem.getLineHandler().registerLine(this);
	}
	
	public void reloadGUI() {
		for (Block bl: stops.keySet()) {
			stops.get(bl).loadGUI();
		}
	}
	
	public void registerStop(BusStop stop, Block b) {
		stops.put(b, stop);
		reloadGUI();
	}
	
	public void deregisterStop(Block b, Player p) {
		p.sendMessage(ChatColor.GOLD + "Bus stop " + stops.get(b).getName() + " will be removed from line " + name);
		stops.remove(b);
		reloadGUI();
		if (!stops.isEmpty()) return;
		p.sendMessage(ChatColor.GOLD + "Line " + name + ChatColor.GOLD + " is empty! It will be deleted.");
		BusSystem.getLineHandler().deregisterLine(this);
	}
	
	public String getName() {
		return name;
	}
	
	public Boolean hasStop(Block b) {
		return stops.containsKey(b);
	}
	
	public BusStop getStop(Block b) {
		return stops.get(b);
	}
	
	public BusStop getStop(String name) {
		for (Block b: stops.keySet()) {
			if (stops.get(b).getName().equals(name)) return stops.get(b);
		}
		return null;
	}
	
	public List<BusStop> getStops() {
		return stops.keySet().stream().map(b -> stops.get(b)).collect(Collectors.toList());
	}
	
	public Map<Block, BusStop> getMap() {
		return stops;
	}
	
	public void save() {
		stops.keySet().stream().forEach(b -> stops.get(b).save());
	}
}