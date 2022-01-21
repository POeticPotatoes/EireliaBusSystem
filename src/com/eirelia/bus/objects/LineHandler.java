package com.eirelia.bus.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bukkit.block.Block;

public class LineHandler {
	private final List<BusLine> lines = new ArrayList<BusLine>();

	public void registerLine(BusLine l) {
		lines.add(l);
	}
	
	public void deregisterLine(BusLine l) {
		lines.remove(l);
	}
	
	public BusStop getStop(Block b) {
		for (BusLine l: lines) {
			if (l.hasStop(b)) return l.getStop(b);
		}
		return null;
	}
	
	public List<BusStop> getStop(String s) {
		List<BusStop> ans = new ArrayList<BusStop>();
		for (BusLine l: lines) {
			BusStop stop = l.getStop(s);
			if (stop == null) continue;
			ans.add(stop);
		}
		return ans;
	}
	
	public BusLine getLine(String name) {
		Optional<BusLine> line = lines.stream().filter(l -> l.getName().equals(name)).findFirst();
		return line.isPresent()? line.get(): null;
	}
	
	public List<BusLine> getLines() {
		return lines;
	}
	
	public List<String> getAllStops() {
		
		List<String> ans = new ArrayList<>();
		
		for (BusLine line: lines) {
			
			List<BusStop> stops = line.getStops();
			
			for (BusStop stop: stops) {
				
				String name = stop.getName();
				if (!ans.contains(name)) ans.add(name);
			}
			
		}
		
		return ans;
	}
	
	public void save() {
		lines.stream().forEach(l -> l.save());
	}

	public void reloadGUI() {
		lines.stream().forEach(l -> l.reloadGUI());
	}
}
