package dev.blue.rotu.world.entities;

import java.awt.image.BufferedImage;

import dev.blue.rotu.world.Location;

public abstract class StaticEntity extends Entity{
	@SuppressWarnings("unused")
	private int harvestState = 0;

	public StaticEntity(String ID, Location location, int width, int height, int health, String itemDrop, BufferedImage... images) {
		super(ID, location, width, height, health, health, itemDrop, images);
	}
}
