package com.eirelia.bus.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.eirelia.bus.BusSystem;
import com.eirelia.bus.utils.Teleporter;

public class CancelBus implements CommandExecutor{
	
	public CancelBus(BusSystem main) {
		main.getCommand("cancelbus").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender s, Command arg1, String arg, String[] args) {
		if (!(s instanceof Player)) {
			s.sendMessage(ChatColor.RED + "You must be a player to use this command!");
			return false;
		}
		if (!s.hasPermission("eirelia.bus.use")) {
			s.sendMessage(ChatColor.RED + "You do not have permission to do that!");
			return false;
		}
		
		Player p = (Player) s;
		
		if (!Teleporter.isTeleporting(p)) {
			s.sendMessage(ChatColor.RED + "You are not on a bus at the moment!");
			return false;
		}
		
		Teleporter.cancelTeleport(p);
		return true;
	}

}
