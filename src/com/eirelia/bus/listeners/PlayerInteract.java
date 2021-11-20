package com.eirelia.bus.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import com.eirelia.bus.BusSystem;
import com.eirelia.bus.gui.GUI;
import com.eirelia.bus.objects.BusStop;
import com.eirelia.bus.utils.BusCreator;
import com.eirelia.bus.utils.Teleporter;

public class PlayerInteract implements Listener{
	
	public PlayerInteract(BusSystem main) {
		main.getServer().getPluginManager().registerEvents(this, main);
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Block b = e.getClickedBlock();
		switch(e.getAction()) {
		case RIGHT_CLICK_BLOCK:
			if (!p.hasPermission("eirelia.bus.use")) return;
			if (!BusStop.isSign(b) || Teleporter.isTeleporting(p)) return;
			
			BusStop stop = BusSystem.getLineHandler().getStop(b);
			
			if (stop == null) return;
			GUI gui = stop.getGUI();
			gui.openInventory(p);
			return;
			
		case LEFT_CLICK_BLOCK:
			if(handleCreator(p,b.getLocation().add(0.5, 0, 0.5))) e.setCancelled(true);
			return;
		case LEFT_CLICK_AIR:
			if(handleCreator(p,p.getLocation())) e.setCancelled(true);
			return;
		default:
			return;
		}
	}
	
	private Boolean handleCreator(Player p, Location l) {
		if (!p.hasPermission("eirelia.bus.edit")) return false;
		if (!BusCreator.isCreating(p)) return false;
		BusCreator creator = BusCreator.getCreator(p);
		try {
			creator.register(l, p);
		} catch (Exception e1) {
			p.sendMessage(ChatColor.RED + "Error: " + e1.getMessage());
		}
		return true;
	}
}
