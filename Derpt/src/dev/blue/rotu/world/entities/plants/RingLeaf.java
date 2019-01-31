package dev.blue.rotu.world.entities.plants;

import java.awt.Rectangle;
import java.util.Random;

import dev.blue.rotu.Main;
import dev.blue.rotu.world.Location;
import dev.blue.rotu.world.entities.Entity;
import dev.blue.rotu.world.entities.StaticEntity;
import dev.blue.rotu.world.tiles.Tile;

public class RingLeaf extends StaticEntity {
	private int maxClumpSize = 18;
	private int clumpSize;
	private boolean isEaten = false;
	private int regrowTimer = 0;

	/**
	 *Takes in a tile location. Can use decimals. 
	 **/
	public RingLeaf(Location location) {
		super("Ringleaf Plant", location, Tile.getWidth(), Tile.getWidth(), 35, "Leaf Ring", Main.getTextures().ringleafCluster(0));
		this.collisionBounds = new Rectangle((int)location.getX(), (int)location.getY(), width, height);
		Random rand = new Random();
		clumpSize = rand.nextInt(maxClumpSize)+1;
		images[0] = Main.getTextures().ringleafCluster(clumpSize);
	}
	
	@Override
	public void update() {
		regrowTimer++;
		if(regrowTimer >= 5000) {
			regrowTimer = 0;
			if(clumpSize < maxClumpSize) {
				clumpSize++;
				updateTexture();
			}
		}
		if(clumpSize > 0) {
			isEaten = false;
			isShowing = true;
		}
	}
	
	@Override
	public void dmg(double amount, Entity attacker) {
		health -= amount;
		if(health <= 0) {
			clumpSize--;
			updateTexture();
			if(clumpSize <= 0) {
				isEaten = true;
				isShowing = false;
			}else health = maxHealth;
		}
	}
	
	public int getClumpSize() {
		return clumpSize;
	}
	
	private void updateTexture() {
		images[0] = Main.getTextures().ringleafCluster(clumpSize);
	}
	
	public boolean isEaten() {
		return isEaten;
	}
	
	@Override
	public void kill() {}
}
