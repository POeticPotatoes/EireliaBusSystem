package com.eirelia.bus.listeners;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.eirelia.bus.BusSystem;
import com.eirelia.bus.objects.BusStop;

public class BlockBreak implements Listener{
	
	public BlockBreak(BusSystem main) {
		main.getServer().getPluginManager().registerEvents(this, main);
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		Block b = e.getBlock();
		if (!BusStop.isSign(b)) return;
		BusStop stop = BusSystem.getLineHandler().getStop(b);
		if (stop == null) return;
		if (!p.hasPermission("eirelia.bus.edit")) {
			e.setCancelled(true);
			p.sendMessage(ChatColor.RED + "You do not have permission to do that!");
			return;
		}
		stop.getLine().deregisterStop(b, p);
	}
}
