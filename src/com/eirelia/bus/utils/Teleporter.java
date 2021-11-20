package com.eirelia.bus.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.eirelia.bus.BusSystem;
import com.eirelia.bus.gui.GUI;
import com.eirelia.bus.objects.BusStop;

public class Teleporter {
	private static final List<Player> teleporting = new ArrayList<Player>();
	private static final DecimalFormat df = new DecimalFormat("##.##");
	private static TeleportSettings settings;
	private final Player p;
	private final BusStop stop, current;
	private final float cost;
	
	public Teleporter(Player p, BusStop busStop, BusStop current, float cost) {
		this.p = p;
		this.stop = busStop;
		this.current = current;
		this.cost = cost;
		GUI.closeInventory(p);
	}
	
	public void startTeleport() {
		teleporting.add(p);
		new IterateText(0).run();
	}
	
	private void teleport() {
		p.teleport(stop.getSpawn());
		p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
		if (settings.blindness) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 30, 1));
		}
		BusSystem.getEconomy().withdrawPlayer(p, cost);
		p.sendMessage(ChatColor.LIGHT_PURPLE + "$" + df.format(cost) + " was deducted from your account!");
		teleporting.remove(p);
	}
	
	private void checkValid() {
		Location loc1 = p.getLocation(), loc2 = current.getLocation();
		if (loc1.getWorld().equals(loc2.getWorld())) {
			if (loc1.distance(loc2) < 10) return;
		}
		p.sendMessage(ChatColor.RED + "You left the bus stop!");
		cancelTeleport(p);
		
	}
	
	public static Boolean isTeleporting(Player p) {
		return teleporting.contains(p);
	}
	
	public static void cancelTeleport(Player p) {
		teleporting.remove(p);
		p.sendMessage(ChatColor.RED + "Your bus ride was cancelled!");
		p.playSound(p.getLocation(), Sound.ENTITY_CAT_HURT, 1.0f, 1.0f);
	}
	
	public static void loadConfig(BusSystem main) {
		settings = new TeleportSettings(main);
	}
	
	private class IterateText implements Runnable {
		private int counter;
		public IterateText(int counter) {
			this.counter = counter;
		}
		
		@Override
		public void run() {
			checkValid();
			if (!teleporting.contains(p)) return;
			p.sendMessage(settings.getText(counter, stop));
			counter ++;
			if (counter >= settings.text.size()) {
				teleport();
				return;
			}
			Bukkit.getScheduler().scheduleSyncDelayedTask(BusSystem.getInstance(), new IterateText(counter), settings.delay);
			
		}
	}
}
