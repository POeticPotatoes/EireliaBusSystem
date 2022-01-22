package com.eirelia.bus.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.eirelia.bus.BusSystem;
import com.eirelia.bus.commands.utility.OptionLister;
import com.eirelia.bus.commands.utility.StopFinder;
import com.eirelia.bus.objects.BusStop;

import net.md_5.bungee.api.ChatColor;

public class ToBus implements CommandExecutor {
	
	public ToBus(BusSystem main) {
		
		main.getCommand("tobus").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
		if (!(s instanceof Player)) {
			s.sendMessage(ChatColor.RED + "You must be a player to do that!");
			return false;
		}
		if (!s.hasPermission("eirelia.bus.edit")) {
			s.sendMessage(ChatColor.RED + "You do not have permission to do that!");
			return false;
		}
		if (args.length == 0) {
			s.sendMessage("Invalid syntax. /tobus [stop]");
			return true;
		}

		List<BusStop> stops = StopFinder.getByArgs(args);
		
		if (stops == null) {
			s.sendMessage(ChatColor.RED + "The stop you selected does not exist!");
			return true;
		}
		
		if (stops.size() == 1) {
			
			BusStop stop = stops.get(0);
			Player p = (Player) s;
			
			p.teleport(stop.getSpawn());
			
			return true;
		}
		
		s.sendMessage(ChatColor.GOLD + "Select which stop you wish to delete.");
		OptionLister.listStops(s, stops, "/eireliabussystem:tobus");
		
		return true;
	}
}
