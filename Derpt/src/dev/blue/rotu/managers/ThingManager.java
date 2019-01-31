package dev.blue.rotu.managers;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import dev.blue.rotu.Thing;

public class ThingManager {
	private List<Thing> fields = new ArrayList<Thing>();
	private List<Thing> bufferedRemovals = new ArrayList<Thing>();
	private List<Thing> bufferedAdditions = new ArrayList<Thing>();

	public void update() {
		for(Thing each: fields) {
			each.update();
		}
	}
	
	public void render(Graphics g) {
		for(Thing each:fields) {
			each.render(g);
		}
	}
	
	public void registerField(Thing field) {
		fields.add(field);
	}
	
	public void registerFieldConcurrently(Thing field) {
		bufferedAdditions.add(field);
	}
	
	public void unregisterFieldConcurrently(Thing field) {
		bufferedRemovals.add(field);
	}
	
	public void unregisterField(Thing field) {
		fields.remove(field);
	}

	public List<Thing> getInputFields() {
		return fields;
	}
	
	public void clear() {
		this.fields.clear();
	}
}
