package dev.blue.rotu.world.entities.plants;

import java.awt.Rectangle;
import java.util.Random;

import dev.blue.rotu.Main;
import dev.blue.rotu.world.Location;
import dev.blue.rotu.world.entities.Entity;
import dev.blue.rotu.world.entities.StaticEntity;

public class CoralGrass extends StaticEntity {
	private static int clumpSize = 4;

	/**
	 *Takes in a tile location. Can use decimals. 
	 **/
	public CoralGrass(Location location) {
		super("Coral Grass", location, 40, 30, 12, "Coral_Grass", Main.getTextures().coralGrass);
		this.collisionBounds = new Rectangle((int)location.getX(), (int)location.getY(), width, height);
	}
	
	public static CoralGrass[] generateClump(double x, double y) {
		Random rand = new Random();
		int clump = rand.nextInt(clumpSize+1);
		CoralGrass[] plants = new CoralGrass[clump];
		for(int i = 0; i < clump; i++) {
			plants[i] = new CoralGrass(new Location(x+rand.nextDouble(), y+rand.nextDouble()));
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
