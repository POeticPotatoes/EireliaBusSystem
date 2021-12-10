package com.eirelia.bus.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.eirelia.bus.BusSystem;
import com.eirelia.bus.objects.BusStop;
import com.eirelia.bus.objects.LineHandler;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;


public class DeleteBus implements CommandExecutor {
	private final LineHandler lineHandler;
	
	public DeleteBus(BusSystem main) {
		main.getCommand("deletebus").setExecutor(this);
		lineHandler = BusSystem.getLineHandler();
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
		List<BusStop> stops = lineHandler.getStop(args[0]);
		if (stops.isEmpty()) {
			s.sendMessage(ChatColor.RED + "No stop named '" + args[0] + "' was found!");
		}
		if (stops.size() == 1) {
			BusStop stop = stops.get(0);
			stop.getLine().deregisterStop(stop, s);
			return true;
		}
		
		if (args.length < 2) {
			s.sendMessage(ChatColor.GOLD + "Select which Bus Stop you wish to delete.");
			for (int i = 0; i<stops.size(); i++) {
				BusStop stop = stops.get(i);
				s.spigot().sendMessage(option(i, stop.getName(), stop.getLine().getName()));
			}
			return true;
		}
		
		//in case second argument isn't an int
		try {
			BusStop stop = stops.get(Integer.parseInt(args[1]));
			stop.getLine().deregisterStop(stop, s);
		} catch (NumberFormatException e) {
			s.sendMessage("Invalid syntax. /deletebus [stop] [selection]");
			return false;
		}
		return true;
	}
	
	private TextComponent option(int num, String name, String line) {
		TextComponent ans = new TextComponent("[" + num + "] " + name + " from line " + line);
		ans.setColor(ChatColor.GOLD);
		ans.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,
				"/deletebus " + name + " " + num));
		return ans;
		
	}

}
