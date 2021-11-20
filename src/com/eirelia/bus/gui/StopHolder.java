package com.eirelia.bus.gui;

import com.eirelia.bus.objects.BusStop;

public class StopHolder {
	private final int cost;
	private final BusStop stop;
	
	public StopHolder(BusStop stop, int cost) {
		this.cost = cost;
		this.stop = stop;
	}
	
	public int getCost() {
		return cost;
	}

	public BusStop getStop() {
		return stop;
	}
}
