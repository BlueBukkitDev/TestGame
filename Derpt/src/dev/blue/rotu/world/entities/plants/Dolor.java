package dev.blue.rotu.world.entities.plants;

import java.awt.Rectangle;
import java.util.Random;

import dev.blue.rotu.Main;
import dev.blue.rotu.world.Location;
import dev.blue.rotu.world.entities.Entity;
import dev.blue.rotu.world.entities.StaticEntity;

public class Dolor extends StaticEntity {
	private static int clumpSize = 3;

	/**
	 *Takes in a tile location. Can use decimals. 
	 **/
	public Dolor(Location location) {
		super("Dolor Cactus", location, 30, 30, 12, "Cactus_Slime", Main.getTextures().dolorCactus);
		this.collisionBounds = new Rectangle((int)location.getX(), (int)location.getY(), width, height);
	}
	
	public static Dolor[] generateClump(double x, double y) {
		Random rand = new Random();
		int clump = rand.nextInt(clumpSize+1);
		Dolor[] plants = new Dolor[clump];
		for(int i = 0; i < clump; i++) {
			plants[i] = new Dolor(new Location(x+rand.nextDouble(), y+rand.nextDouble()));
		}
		return plants;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dmg(double amount, Entity attacker) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void kill() {
		// TODO Auto-generated method stub
		
	}
}
