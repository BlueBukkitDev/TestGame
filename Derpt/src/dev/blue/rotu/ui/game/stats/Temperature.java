package dev.blue.rotu.ui.game.stats;

import dev.blue.rotu.world.entities.Creature;

public class Temperature extends Stat {
	private Creature avatar;

	public Temperature(Creature avatar) {
		super("Temperature", 1, 1, 200, 0, 100);
		this.avatar = avatar;
	}

	@Override
	public void onMinimum() {
		avatar.dmg(20, null);
	}

	@Override
	public void onMaximum() {
		avatar.dmg(20, null);
	}

	@Override
	public void onIncrease() {
		
	}

	@Override
	public void onDecrease() {
		//pulse of red on the screen? idk. 
	}
}
