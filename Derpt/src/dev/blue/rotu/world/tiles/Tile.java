package dev.blue.rotu.world.tiles;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import dev.blue.rotu.Game;
import dev.blue.rotu.Window;
import dev.blue.rotu.world.Location;
import dev.blue.rotu.world.entities.Entity;

public abstract class Tile {
	private static final int width = Window.space()*3;
	private String name;
	private boolean isBeingInspected;
	private boolean collides;
	private BufferedImage topLeft, bottomLeft, bottomRight, topRight;
	protected BufferedImage texture;
	public static Tile inspected = null;
	public static Location inspectionLocation = null;
	protected byte dominance;
	private List<Entity> objects = new ArrayList<Entity>();
	
	public Tile(String name, boolean collides, byte dominance) {
		this.collides = collides;
		this.name = name;
		this.dominance = dominance;
	}
	
	public String getName() {
		return name;
	}
	
	public int getDominance() {
		return dominance;
	}
	
	public static Tile getTile(byte ID) {
		switch(ID) {
		case 0:return new GrassTile();
		case 1:return new DirtTile();
		case 2:return new SandTile();
		case 3:return new StoneTile();
		case 4:return new ForestTile();
		case 5:return new WaterTile();
		default:return new GrassTile();
		}
	}
	
	public void addObject(Entity object) {
		this.objects.add(object);
	}
	
	public List<Entity> getObjects(){
		return objects;
	}
	
	public void setObjects(List<Entity> objects) {
		this.objects = objects;
	}
	
	public void setTexture(BufferedImage topLeft, BufferedImage bottomLeft, BufferedImage bottomRight, BufferedImage topRight) {
		this.topLeft = topLeft; 
		this.bottomLeft = bottomLeft; 
		this.bottomRight = bottomRight; 
		this.topRight = topRight;
	}
	/**
	 *Takes in an array of 4 items. 
	 **/
	public void setTexture(BufferedImage[] images) {
		if(images.length >= 4) {
			this.topLeft = images[0];
			this.bottomLeft = images[1];
			this.bottomRight = images[2];
			this.topRight = images[3];
		}
	}
	
	public abstract byte getID();
	
	public BufferedImage getTexture() {
		return texture;
	}
	
	public BufferedImage[] getTextures() {
		return new BufferedImage[] {topLeft, bottomLeft, bottomRight, topRight};
	}
	
	public boolean getCollides() {
		return collides;
	}
	
	public static int getWidth() {
		return width;
	}

	public boolean isBeingInspected() {
		return isBeingInspected;
	}

	public void setBeingInspected(boolean isBeingInspected, Location location) {
		this.isBeingInspected = isBeingInspected;
		if(isBeingInspected == false) {
			Game.getInstance().getState().doTileInspection(null, location);
			Tile.inspectionLocation = null;
			Tile.inspected = null;
		}else {
			Tile.inspectionLocation = location;
			Tile.inspected = this;
			Game.getInstance().getState().doTileInspection(this, location);
		}
	}
}
