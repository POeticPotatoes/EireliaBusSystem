package com.eirelia.bus.commands.utility;

import java.util.List;

import com.eirelia.bus.BusSystem;
import com.eirelia.bus.objects.BusStop;
import com.eirelia.bus.objects.LineHandler;

public class StopFinder {
	
	private static final LineHandler lineHandler = BusSystem.getLineHandler();
	
	public static List<BusStop> getByArgs(String[] args) {

		StringBuilder refBuilder = new StringBuilder(args[0]);
		Integer index = null;
		
		for (int i=1;i<args.length;i++) {
			String add = args[i];
			
			if (add.length()>4 && add.substring(0, 4).equals("mvnw")) {
				try {
					
					index = Integer.parseInt(add.substring(4));
					break;
					
				} catch (NumberFormatException e) {}
			}
			
			refBuilder.append(" ");
			refBuilder.append(args[i]);
		}
		
		String ref = refBuilder.toString();
		List<BusStop> stops = lineHandler.getStop(ref);
		
		if (stops.isEmpty()) return null;
		if (index != null) return List.of(stops.get(index));
		
		return stops;
	}
}
