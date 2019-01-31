package dev.blue.rotu.ui.game.stats;

import dev.blue.rotu.gfx.Animation;
import dev.blue.rotu.ui.Button;
import dev.blue.rotu.ui.game.StatBar;

public class StatBarCompactor extends Button{
	protected StatBar[] statBars;
	protected boolean showing = true;
	protected int permaY;
	protected int permaY2;

	public StatBarCompactor(String id, int x, int y, int width, int height, int animationSpeed, Animation whileDown, Animation whileUp, StatBar... statBars) {
		super(id, false, false, 0, x, y, width, height, animationSpeed, whileDown, whileUp);
		this.statBars = statBars;
		onInitialize();
	}
	public void onInitialize() {}
}
