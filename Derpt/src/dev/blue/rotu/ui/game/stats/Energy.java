package dev.blue.rotu.ui.game.stats;

import dev.blue.rotu.world.entities.Creature;

public class Energy extends Stat {
	//private Creature avatar;

	public Energy(Creature avatar) {
		super("Energy", 1, 1, 200, 0, 200);
		//this.avatar = avatar;
	}

	@Override
	public void onMinimum() {
		//avatar.nerfSpeed(0.01);
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
