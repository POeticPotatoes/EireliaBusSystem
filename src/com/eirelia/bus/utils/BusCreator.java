package com.eirelia.bus.utils;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.eirelia.bus.BusSystem;
import com.eirelia.bus.objects.BusStop;

public class BusCreator {
	private static final Map<Player, BusCreator> playerlist = new HashMap<Player, BusCreator>();
	private final Block sign;
	private final int timelimit;
	private Location spawn = null;
	
	public BusCreator(Block b, Player p) {
		sign = b;
		if (BusSystem.getLineHandler().getStop(b) != null) {
			p.sendMessage(ChatColor.RED + "This sign is already registered as a bus stop!");
			timelimit = 0;
			return;
		}
		playerlist.put(p, this);
		p.sendMessage(ChatColor.GREEN + "Creating bus stop! Left-click the block players should spawn on.");
		
		timelimit = Bukkit.getScheduler().scheduleSyncDelayedTask(BusSystem.getInstance(), new Runnable() {
			@Override
			public void run() {
				if (!playerlist.containsKey(p)) return;
				p.sendMessage(ChatColor.RED + "You took too long to set up the bus stop!");
				playerlist.remove(p);
			}
		}, 400);
	}
	
	public void register(Location l, Player p) throws Exception {
		if (!sign.getWorld().equals(l.getWorld()) || l.distance(sign.getLocation()) > 20) {
			throw new Exception("Location is too far away from the bus stop!");
		}
		if (spawn == null) {
			if (l.getBlock().getType() == Material.AIR) {
				p.sendMessage(ChatColor.GREEN + "Left-click the block players should spawn on.");
				return;
			}
			spawn = l.add(0, 1, 0);
			p.sendMessage(ChatColor.GREEN + "Spawn location set! Left click the direction you wish the player to face.");
			return;
		}
		spawn.setDirection(p.getLocation().getDirection());
		createStop(p);
	}
	
	private void createStop(Player p) throws Exception {
		new BusStop(sign, p, spawn);
		playerlist.remove(p);
		Bukkit.getScheduler().cancelTask(timelimit);
	}
	
	public static Boolean isCreating(Player p) {
		return playerlist.containsKey(p);
	}
	
	public static BusCreator getCreator(Player p) {
		return playerlist.get(p);
	}
}
