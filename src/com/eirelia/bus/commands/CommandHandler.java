package com.eirelia.bus.commands;

import com.eirelia.bus.BusSystem;

public class CommandHandler {
	public CommandHandler(BusSystem main) {
		new SetBusMultiplier(main);
		new CancelBus(main);
		new LoadConfig(main);
		new CreateBus(main);
	}
}
