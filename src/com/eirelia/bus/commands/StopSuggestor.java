package com.eirelia.bus.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.eirelia.bus.BusSystem;
import com.eirelia.bus.objects.LineHandler;

public class StopSuggestor implements TabCompleter {
	
	private final LineHandler lineHandler = BusSystem.getLineHandler();
	
	public StopSuggestor(BusSystem main) {
		
		main.getCommand("deletebus").setTabCompleter(this);
		main.getCommand("tobus").setTabCompleter(this);
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		
		return lineHandler.getAllStops();
	}

}
