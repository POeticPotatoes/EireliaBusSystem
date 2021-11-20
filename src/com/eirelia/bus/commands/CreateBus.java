package com.eirelia.bus.commands;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.eirelia.bus.BusSystem;
import com.eirelia.bus.objects.BusStop;
import com.eirelia.bus.utils.BusCreator;

public class CreateBus implements CommandExecutor{
	
	public CreateBus(BusSystem main) {
		main.getCommand("createbus").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender s, Command arg1, String arg, String[] args) {
		if (!s.hasPermission("eirelia.bus.edit")) {
			s.sendMessage(ChatColor.RED + "You do not have permission to do that!");
			return false;
		}
		if (!(s instanceof Player)) {
			s.sendMessage(ChatColor.RED + "You must be a player to use this command!");
			return false;
		}
		Player p = (Player) s;
		if (BusCreator.isCreating(p)) {
			p.sendMessage(ChatColor.RED + "You are already creating a new stop!");
			return false;
		}
		
		Block b = p.rayTraceBlocks(6).getHitBlock();
		if (!BusStop.isSign(b)) {
			p.sendMessage(ChatColor.GRAY + "Look at the sign you wish to turn into a bus stop and run this command again!");
			return false;
		}
		try {
			new BusCreator(b, p);
		} catch (Exception e1) {
			p.sendMessage(ChatColor.RED + "Error: " + e1.getMessage());
		}
		return true;
	}
	
}
