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
	
	public BusLine getLine(String name) {
		Optional<BusLine> line = lines.stream().filter(l -> l.getName().equals(name)).findFirst();
		if (line.isPresent()) return line.get();
		return null;
	}
	
	public List<BusLine> getLines() {
		return lines;
	}
	
	public void save() {
		lines.stream().forEach(l -> l.save());
	}

	public void reloadGUI() {
		lines.stream().forEach(l -> l.reloadGUI());
	}
}
