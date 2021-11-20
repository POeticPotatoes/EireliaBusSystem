package com.eirelia.bus;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import com.eirelia.bus.imported.LocationFile;
import com.eirelia.bus.objects.BusStop;

public class BusData extends LocationFile{
	private FileConfiguration data;
	
	public BusData(BusSystem main) {
		super(main, "bus_data.yml", null);
		loadFile();
		data = getFileConfiguration();
	}
	
	public void init() {
		Set<Location> locations = this.parseLocations();
		for (Location l: locations) {
			try {
				new BusStop(l, data.getConfigurationSection(parseLocation(l)));
			} catch (Exception e) {
				Bukkit.getLogger().warning("Did not manage to load bus stop at " + l.toString());
			}
		}
		BusSystem.getLineHandler().reloadGUI();
	}
	
	public void saveData() {
		for (World w: Bukkit.getWorlds()) {
			data.set(w.getName(), null);
		}
		BusSystem.getLineHandler().save();
		this.saveFile();
	}

	public ConfigurationSection getLocation(Location loc) {
		String path = parseLocation(loc);
		ConfigurationSection ans = data.getConfigurationSection(path);
		if (ans == null) ans = data.createSection(path);
		return ans;
	}
}
