package com.eirelia.bus.utils;

import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import com.eirelia.bus.BusSystem;
import com.eirelia.bus.objects.BusStop;

public class TeleportSettings{
	public int delay = 40;
	public Boolean blindness = true;
	public List<String> text = List.of("Welcome to Eirelia city's bus service~", 
			"You'll be transported to your destination in a bit...", 
			"All done! Come and use us again!");
	
	
	@SuppressWarnings("unchecked")
	public TeleportSettings(BusSystem main) {
		FileConfiguration config = main.getConfig();
		ConfigurationSection section = config.getConfigurationSection("travel_settings");
		if (section == null) return;
		if (section.contains("delay")) delay = section.getInt("delay") * 20;
		if (section.contains("blindness")) blindness = section.getBoolean("blindness");
		if (section.contains("text")) text = (List<String>) section.getList("text");
	}


	public String getText(int i, BusStop stop) {
		return text.get(i)
				.replaceAll("@stop", stop.getName())
				.replaceAll("@line", stop.getLine().getName());
	}
}
