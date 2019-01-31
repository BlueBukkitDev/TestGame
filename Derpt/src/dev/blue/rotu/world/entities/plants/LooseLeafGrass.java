package dev.blue.rotu.world.entities.plants;

import java.awt.Rectangle;
import java.util.Random;

import dev.blue.rotu.Main;
import dev.blue.rotu.world.Location;
import dev.blue.rotu.world.entities.Entity;
import dev.blue.rotu.world.entities.StaticEntity;
import dev.blue.rotu.world.tiles.Tile;

public class LooseLeafGrass extends StaticEntity {
	private int maxClumpSize = 9;
	private int clumpSize;

	/**
	 *Takes in a tile location. Can use decimals. 
	 **/
	public LooseLeafGrass(Location location) {
		super("Looseleaf Grass", location, (int)(Tile.getWidth()*1.5), (int)(Tile.getWidth()*1.5), 12, "LooseLeaf_Grass", Main.getTextures().looseleafCluster(0));
		this.collisionBounds = new Rectangle((int)location.getX(), (int)location.getY(), width, height);
		Random rand = new Random();
		clumpSize = rand.nextInt(maxClumpSize)+1;
		images[0] = Main.getTextures().looseleafCluster(clumpSize);
	}
	
	public int getClumpSize() {
		return clumpSize;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dmg(double amount, Entity attacker ) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void kill() {
		// TODO Auto-generated method stub
		
	}
}
