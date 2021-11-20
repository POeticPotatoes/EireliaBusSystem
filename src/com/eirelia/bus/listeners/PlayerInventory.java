package com.eirelia.bus.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import com.eirelia.bus.BusSystem;
import com.eirelia.bus.PlayerHandler;
import com.eirelia.bus.gui.GUI;
import com.eirelia.bus.gui.StopHolder;
import com.eirelia.bus.utils.Teleporter;

public class PlayerInventory implements Listener{
	
	public PlayerInventory(BusSystem main) {
		main.getServer().getPluginManager().registerEvents(this, main);
		Bukkit.getOnlinePlayers().stream().forEach(p -> p.closeInventory());
	}
	
	@EventHandler
	public void onCloseInventory(InventoryCloseEvent e) {
		if (!(e.getPlayer() instanceof Player)) return;
		Player p = (Player) e.getPlayer();
		if (GUI.isViewing(p)) GUI.deregister(p);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (!(e.getWhoClicked() instanceof Player)) return;
		Player p = (Player) e.getWhoClicked();
		if (!(GUI.isViewing(p)) || Teleporter.isTeleporting(p)) return;
		e.setCancelled(true);
		
		if (e.getAction() != InventoryAction.PICKUP_ALL) return;
		GUI gui = GUI.getGUI(p);
		StopHolder holder = gui.getStopHolder(e.getRawSlot());
		float cost = holder.getCost() * PlayerHandler.multiplier();
		
		double balance = BusSystem.getEconomy().getBalance(p);
		if (balance < cost) {
			p.sendMessage(ChatColor.RED + "You do not have enough money to travel there!");
			return;
		}
		Teleporter t = new Teleporter(p, holder.getStop(), gui.getStop(), cost);
		t.startTeleport();
	}
}
