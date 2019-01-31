package dev.blue.rotu.world.entities.plants;

import java.awt.Rectangle;
import java.util.Random;

import dev.blue.rotu.Main;
import dev.blue.rotu.world.Location;
import dev.blue.rotu.world.entities.Entity;
import dev.blue.rotu.world.entities.StaticEntity;

public class Mytus extends StaticEntity {
	private static int clumpSize = 3;

	/**
	 *Takes in a tile location. Can use decimals. 
	 **/
	public Mytus(Location location) {
		super("Mytus Plant", location, 24, 60, 12, "Mytus_Leaf", Main.getTextures().mytusPlant);
		this.collisionBounds = new Rectangle((int)location.getX(), (int)(location.getY()-height*0.9), width, (int)(height*0.1));
	}

	public static Mytus[] generateClump(double x, double y) {
		Random rand = new Random();
		int clump = rand.nextInt(clumpSize+1);
		Mytus[] plants = new Mytus[clump];
		for(int i = 0; i < clump; i++) {
			plants[i] = new Mytus(new Location(x+rand.nextDouble(), y+rand.nextDouble()));
		}
		return plants;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dmg(double amount, Entity Attacker) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void kill() {
		// TODO Auto-generated method stub
		
	}
}
