package com.eirelia.bus.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.eirelia.bus.BusSystem;

public class LoadConfig implements CommandExecutor{
	
	public LoadConfig(BusSystem main) {
		main.getCommand("reloadbusconfig").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender s, Command arg1, String arg, String[] args) {
		if (!s.hasPermission("eirelia.bus.admin")) {
			s.sendMessage(ChatColor.RED + "You do not have permission to do that!");
			return false;
		}
		
		s.sendMessage(ChatColor.RED + "Updating config, you will have to restart the server for it to take effect.");
		BusSystem.getInstance().updateConfig();
		s.sendMessage(ChatColor.GREEN + "Config reloaded.");
		return true;
	}
}
