package com.eirelia.bus;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.eirelia.bus.commands.CommandHandler;
import com.eirelia.bus.objects.LineHandler;

import net.milkbowl.vault.economy.Economy;

public class BusSystem extends JavaPlugin{
	private static final LineHandler lineHandler = new LineHandler();
	private static BusData busData;
	private static Economy econ;
	private static BusSystem instance;
	private static PlayerHandler playerHandler;
	
	@Override
	public void onEnable() {
		if (!setupEconomy() ) {
            Bukkit.getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
		instance = this;
		saveDefaultConfig();
		
		//commands
		new CommandHandler(this);
		
		//listeners
		playerHandler = new PlayerHandler(this);
		
		//data
		busData = new BusData(this);
		busData.init();
	}
	
	@Override
	public void onDisable() {
		if (busData!= null) busData.saveData();
		saveConfig();
	}
	
	public boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
	
	public void updateConfig() {
		reloadConfig();
		playerHandler.reloadConfig();
		lineHandler.reloadGUI();
	}
	
	public static LineHandler getLineHandler() {
		return lineHandler;
	}
	
	public static BusData getBusData() {
		return busData;
	}
	
	
	public static Economy getEconomy() {
        return econ;
    }
	
	public static BusSystem getInstance() {
		return instance;
	}
}
