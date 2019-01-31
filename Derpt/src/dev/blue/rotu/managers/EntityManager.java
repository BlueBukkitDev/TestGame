package dev.blue.rotu.managers;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import dev.blue.rotu.Game;
import dev.blue.rotu.world.entities.Entity;

public class EntityManager {
	private List<Entity> entities = new ArrayList<Entity>();
	private List<Entity> bufferedRemovals = new ArrayList<Entity>();
	private List<Entity> bufferedAdditions = new ArrayList<Entity>();

	public void update() {
		for(Entity each:bufferedRemovals) {
			entities.remove(each);
		}
		for(Entity each:bufferedAdditions) {
			entities.add(each);
		}
		bufferedRemovals.clear();
		bufferedAdditions.clear();
		for(Entity each:entities) {
			each.update();
		}
	}
	
	public void render(Graphics g) {
		for(Entity each:entities) {
			if(Game.getInstance().getState().getCamera().getViewField().contains(each.getLocation())) {
				each.render(g);
			}
		}
	}
	
	public void registerField(Entity field) {
		entities.add(field);
	}
	
	public void registerFieldConcurrently(Entity field) {
		bufferedAdditions.add(field);
	}
	
	public void unregisterField(Entity field) {
		bufferedRemovals.add(field);
	}

	public List<Entity> getEntities() {
		return entities;
	}
	
	public void clear() {
		this.entities.clear();
	}
}
