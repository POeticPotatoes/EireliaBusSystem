package com.eirelia.bus.imported;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class FileHandler {
	private File file;
	private FileConfiguration data;
	private String path;
	protected static JavaPlugin main;
	
	public FileHandler(JavaPlugin main, String path) {
		this.path = path;
		
		if (FileHandler.main == null) FileHandler.main = main;
	}
	
	public Boolean loadFile() {
		file = new File(main.getDataFolder(), path);
		data = YamlConfiguration.loadConfiguration(file);
		return file.exists();
	}
	
	public FileConfiguration loadResource() {
		InputStream stream = main.getResource(path);

		data = YamlConfiguration.loadConfiguration(new InputStreamReader(stream));
		return data;
	}
	
	public FileConfiguration getFileConfiguration() {
		return data;
	}
	
	public Boolean saveFile() {
		try {
			data.save(file);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
