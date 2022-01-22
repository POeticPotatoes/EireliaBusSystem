package com.eirelia.bus.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.eirelia.bus.BusSystem;
import com.eirelia.bus.commands.utility.OptionLister;
import com.eirelia.bus.commands.utility.StopFinder;
import com.eirelia.bus.objects.BusStop;

import net.md_5.bungee.api.ChatColor;


public class DeleteBus implements CommandExecutor {
	
	public DeleteBus(BusSystem main) {
		
		main.getCommand("deletebus").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
		if (!s.hasPermission("eirelia.bus.edit")) {
			s.sendMessage(ChatColor.RED + "You do not have permission to do that!");
			return false;
		}
		if (args.length == 0) {
			s.sendMessage("Invalid syntax. /deletebus [stop]");
			return true;
		}
		
		List<BusStop> stops = StopFinder.getByArgs(args);
		
		if (stops == null) {
			s.sendMessage(ChatColor.RED + "The stop you selected does not exist!");
			return true;
		}
		
		if (stops.size() == 1) {
			BusStop stop = stops.get(0);
			stop.getLine().deregisterStop(stop, s);
			return true;
		}
		
		s.sendMessage(ChatColor.GOLD + "Select which stop you wish to delete.");
		OptionLister.listStops(s, stops, "/eireliabussystem:deletebus");
		
		return true;
	}

}
