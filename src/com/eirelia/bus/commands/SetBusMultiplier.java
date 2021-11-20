package com.eirelia.bus.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.eirelia.bus.BusSystem;
import com.eirelia.bus.PlayerHandler;

public class SetBusMultiplier implements CommandExecutor{
	
	public SetBusMultiplier(BusSystem main) {
		main.getCommand("setbusmultiplier").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender s, Command arg1, String arg, String[] args) {
		if (!s.hasPermission("eirelia.bus.edit")) {
			s.sendMessage(ChatColor.RED + "You do not have permission to do that!");
			return false;
		}
		float f = 0;
		try {
			f = Float.parseFloat(args[0]);
		} catch (Exception e) {
			s.sendMessage("Please input a valid value for the bus multiplier.");
			return false;
		}
		PlayerHandler.setMultiplier(f);
		s.sendMessage("Economy multiplier for bus stops has been set to: " + f);
		return true;
	}

}
