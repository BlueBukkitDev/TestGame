package dev.blue.rotu.ui.game.stats;

import dev.blue.rotu.world.entities.Creature;

public class Health extends Stat {
	private Creature avatar;

	public Health(Creature avatar) {
		super("Health", 1, 1, 200, 0, 200);
		this.avatar = avatar;
	}

	@Override
	public void onMinimum() {
		avatar.kill();
	}

	@Override
	public void onMaximum() {
		//avatar.getWellness().allowIncrease();
	}

	@Override
	public void onIncrease() {
		//if(avatar.getType() == AvatarType.HUMAN) {
		//	avatar.buffSpeed(0.1);
		//}
	}

	@Override
	public void onDecrease() {
		
	}
}
