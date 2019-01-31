package dev.blue.rotu.ui.game.stats;

import dev.blue.rotu.world.entities.Creature;

public class Thirst extends Stat {
	private Creature avatar;

	public Thirst(Creature avatar) {
		super("Thirst", 1, 1, 200, 0, 200);
		this.avatar = avatar;
	}

	@Override
	public void onMinimum() {
		avatar.dmg(20, null);
	}

	@Override
	public void onMaximum() {
		
	}

	@Override
	public void onIncrease() {
		
	}

	@Override
	public void onDecrease() {
		//pulse of red on the screen? idk. 
	}
}
