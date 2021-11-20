package com.eirelia.bus.objects;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

import com.eirelia.bus.BusSystem;
import com.eirelia.bus.gui.GUI;

import net.md_5.bungee.api.ChatColor;

public class BusStop {
	private final String name;
	private final Location loc;
	private final BusLine line;
	private Location spawn;
	private GUI gui;
	private static final List<Material> signList = List.of(Material.ACACIA_SIGN, Material.ACACIA_WALL_SIGN, Material.BIRCH_SIGN,
			Material.BIRCH_WALL_SIGN, Material.CRIMSON_SIGN, Material.CRIMSON_WALL_SIGN, Material.DARK_OAK_SIGN,
			Material.DARK_OAK_WALL_SIGN, Material.JUNGLE_SIGN, Material.JUNGLE_WALL_SIGN, Material.OAK_SIGN, Material.OAK_WALL_SIGN,
			Material.SPRUCE_SIGN, Material.SPRUCE_WALL_SIGN, Material.WARPED_SIGN, Material.WARPED_WALL_SIGN);
	
	public BusStop(Block b, CommandSender s, Location l) throws Exception {
		s.sendMessage("Creating new stop!");
		String[] arg = ((Sign) b.getState()).getLines();
		name = (arg[1] + " " + arg[2]).strip();
		String lineName = arg[3];
		if (name.isBlank() || lineName.isBlank()) throw new Exception("Invalid sign format");
		loc = b.getLocation();
		spawn = l;
		BusLine busline = BusSystem.getLineHandler().getLine(lineName);
		if (busline == null) {
			s.sendMessage(ChatColor.GREEN + "No existing line named " + lineName + ChatColor.GREEN + " was found! Creating new line...");
			busline = new BusLine(lineName);
			s.sendMessage(ChatColor.GREEN + "New line created!");
		}
		line = busline;
		line.registerStop(this, b);
		s.sendMessage("Stop created!");
	}
	
	public BusStop(Location l, ConfigurationSection data) throws Exception {
		Block b = l.getBlock();
		if (!isSign(b)) throw new Exception("Block at location " + l.toString() + " was not a sign!");
		name = data.getString("name");
		String lineName = data.getString("line");
		if (name.isBlank() || lineName.isBlank()) throw new Exception("Invalid sign format");
		loc = l;
		spawn = data.getLocation("spawn");
		BusLine busline = BusSystem.getLineHandler().getLine(lineName);
		if (busline == null) {
			busline = new BusLine(lineName);
		}
		line = busline;
		line.registerStop(this, l.getBlock());
	}
	
	public String getName() {
		return name;
	}
	
	public BusLine getLine() {
		return line;
	}
	
	public Location getLocation() {
		return loc;
	}
	
	public Location getSpawn() {
		return spawn;
	}
	
	public void setSpawn(Location l) {
		spawn = l;
	}
	
	public GUI getGUI() {
		return gui;
	}
	
	public void loadGUI() {
		gui = new GUI(this);
	}
	
	public void save() {
		ConfigurationSection section = BusSystem.getBusData().getLocation(loc);
		section.set("name", name);
		section.set("line", line.getName());
		section.set("spawn", spawn);
	}
	
	public static Boolean isSign(Block b) {
		return signList.contains(b.getType());
	}
}
