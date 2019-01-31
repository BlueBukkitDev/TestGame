package dev.blue.rotu.ui.game.stats;

import dev.blue.rotu.world.entities.Creature;

public class Hunger extends Stat {
	private Creature avatar;

	public Hunger(Creature avatar) {
		super("Hunger", 1, 1, 200, 0, 200);
		this.avatar = avatar;
	}

	@Override
	public void onMinimum() {
		avatar.dmg(10, null);
	}

	@Override
	public void onMaximum() {
		//avatar.getWellness().allowIncrease();
	}

	@Override
	public void onIncrease() {
		//if(avatar.getType() == AvatarType.HUMAN) {
		//	avatar.dmg(-2);
		//}
	}

	@Override
	public void onDecrease() {
		//pulse of red on the screen? idk. 
	}
}
