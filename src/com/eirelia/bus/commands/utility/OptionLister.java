package com.eirelia.bus.commands.utility;

import java.util.List;

import org.bukkit.command.CommandSender;

import com.eirelia.bus.objects.BusStop;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class OptionLister {
	
	public static void listStops(CommandSender s, List<BusStop> stops, String command) {
		
		for (int i=0;i<stops.size();i++) {
			
			BusStop stop = stops.get(i);
			s.spigot().sendMessage(stopOption(i, stop, command));
		}
	}
	
	private static TextComponent stopOption(int num, BusStop stop, String command) {
		
		String name = stop.getName();
		String line = stop.getLine().getName();
	
		TextComponent ans = new TextComponent("[" + (num+1) + "] " + name + " from line " + line);
		ans.setColor(ChatColor.GOLD);
		ans.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,
				command + " " + name + " " +  "mvnw" + num));
		return ans;
		
	}
}
