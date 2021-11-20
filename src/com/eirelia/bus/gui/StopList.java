package com.eirelia.bus.gui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.block.Block;

import com.eirelia.bus.objects.BusStop;

public class StopList {
	private BusStop stop;
	private static int worldCost = 5000;
	
	public StopList(BusStop stop) {
		this.stop = stop;
	}
	
	public List<StopHolder> getSortedStops() {
		List<StopHolder> ans = new ArrayList<StopHolder>();
		Map<Block, BusStop> map = stop.getLine().getMap();
		Location loc1 = stop.getLocation();
		Location loc2;
		for (Block b: map.keySet()) {
			BusStop s = map.get(b);
			if (s.equals(stop)) continue;
			loc2 = s.getLocation();
			int distance;
			if (!loc1.getWorld().equals(loc2.getWorld())) {
				distance = worldCost;
			} else distance = (int) Math.round(loc2.distance(loc1));
			ans.add(new StopHolder(s, distance));
		}
		if (ans.size() == 0) return null;
		ans.sort(new StopSort());
		return ans;
	}

	private class StopSort implements Comparator<StopHolder>{

		@Override
		public int compare(StopHolder o1, StopHolder o2) {
			return o1.getCost() - o2.getCost();
		}

	}
}