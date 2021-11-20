package com.eirelia.bus;

import com.eirelia.bus.listeners.BlockBreak;
import com.eirelia.bus.listeners.PlayerInteract;
import com.eirelia.bus.listeners.PlayerInventory;
import com.eirelia.bus.utils.Teleporter;

public class PlayerHandler {
	private static float multiplier;
	private static BusSystem main;
	
	public PlayerHandler(BusSystem main) {
		PlayerHandler.main = main;
		loadConfig();
		
		Teleporter.loadConfig(main);
		
		//listeners
		new PlayerInteract(main);
		new BlockBreak(main);
		new PlayerInventory(main);
		
		//utils
	}
	
	public static float multiplier() {
		return multiplier;
	}
	
	public static void setMultiplier(float val) {
		main.getConfig().set("economy_multiplier", String.valueOf(val));
		multiplier = val;
		BusSystem.getLineHandler().reloadGUI();
	}
	
	public void reloadConfig() {
		loadConfig();
		Teleporter.loadConfig(main);
	}
	
	private static void loadConfig() {
		String raw = main.getConfig().getString("economy_multiplier");
		if (raw == null) {
			multiplier = 0.01f;
			main.getConfig().set("economy_multiplier", "0.01");
		} else multiplier = Float.parseFloat(raw);
	}
}
