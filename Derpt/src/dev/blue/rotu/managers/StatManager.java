package dev.blue.rotu.managers;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import dev.blue.rotu.ui.game.StatBar;

public class StatManager {
	private List<StatBar> stats = new ArrayList<StatBar>();
	
	public void update() {
		for(StatBar each: stats) {
			each.update();
		}
	}
	
	public void render(Graphics g) {
		for(int i = stats.size(); i > 0; i--) {
			stats.get(i-1).render(g);//i-1 because it's using fields.size instead of an array
		}
	}
	
	public void registerStatBar(StatBar stat) {
		stats.add(0, stat);
	}
	
	public void unregisterStatBar(StatBar stat) {
		stats.remove(stat);
	}

	public List<StatBar> getStatBars() {
		return stats;
	}
	
	public void clear() {
		this.stats.clear();
	}
}
