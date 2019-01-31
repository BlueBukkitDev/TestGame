package dev.blue.rotu.world.entities.plants;

import java.awt.Rectangle;
import java.util.Random;

import dev.blue.rotu.Main;
import dev.blue.rotu.world.Location;
import dev.blue.rotu.world.entities.Entity;
import dev.blue.rotu.world.entities.StaticEntity;

public class BelinianAylflower extends StaticEntity {
	private static int clumpSize = 2;

	/**
	 *Takes in a tile location. Can use decimals. 
	 **/
	public BelinianAylflower(Location location) {
		super("Belinian Aylflower", location, 40, 60, 12, "Aylflower", Main.getTextures().belinianAylflower);
		this.collisionBounds = new Rectangle((int)width/4, (int)(height*0.9), width/2, (int)(height*0.1));
	}
	
	public static BelinianAylflower[] generateClump(double x, double y) {
		Random rand = new Random();
		int clump = rand.nextInt(clumpSize+1);
		BelinianAylflower[] plants = new BelinianAylflower[clump];
		for(int i = 0; i < clump; i++) {
			plants[i] = new BelinianAylflower(new Location(x+rand.nextDouble(), y+rand.nextDouble()));
		}
		return plants;
	}

	@Override
	public void update() {
		
	}

	@Override
	public void dmg(double amount, Entity attacker) {
		
	}

	@Override
	public void kill() {
		
	}
}
