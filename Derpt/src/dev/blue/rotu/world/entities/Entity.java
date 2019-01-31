package dev.blue.rotu.world.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import dev.blue.rotu.Game;
import dev.blue.rotu.gfx.Camera;
import dev.blue.rotu.world.Location;
import dev.blue.rotu.world.entities.plants.BelinianAylflower;
import dev.blue.rotu.world.entities.structures.RabbitBurrow;
import dev.blue.rotu.world.tiles.Tile;

public abstract class Entity {
	/**
	 *A tile location
	 **/
	public Location location;
	protected int width, height;
	protected double health, maxHealth;
	protected Rectangle collisionBounds;//Might make this lines or an array later
	protected String itemDrop;
	protected String ID;
	protected boolean isShowing;
	protected boolean invulnerable;
	protected boolean isDead;
	protected BufferedImage[] images;
	int xoffset, yoffset;
	
	public Entity(String ID, Location location, int width, int height, double health, double maxHealth, String itemDrop, BufferedImage... images) {
		this.ID = ID;
		this.location = location;
		this.width = width;
		this.height = height;
		this.health = health;
		this.itemDrop = itemDrop;
		this.isShowing = true;
		this.images = images;
		Random rand = new Random();
		xoffset = rand.nextInt(10);
		yoffset = rand.nextInt(10);
		this.maxHealth = maxHealth;
	}
	
	public abstract void update();
	
	public boolean render(Graphics g) {
		Camera camera = Game.getInstance().getState().getCamera();
		double x = (location.getX()*Tile.getWidth()-camera.getViewField().getTilesFromLeft()*Tile.getWidth()-camera.getXOffset());
		double y = (location.getY()*Tile.getWidth()-camera.getViewField().getTilesFromTop()*Tile.getWidth()-camera.getYOffset()-height+collisionBounds.getHeight());
		if(isShowing) {
			/*if(this instanceof LooseLeafGrass) {
				x += Tile.getWidth()*.75;
				y += Tile.getWidth()*.75;
			}*/
			g.drawImage(images[0], (int)(x-collisionBounds.getWidth()/2), (int)y-height/2, width, height, null);//Newborn rabbits do not render//fixed, and all rabbits render on top of entities. 
			/*if(this instanceof Creature || this instanceof RabbitBurrow) {
				g.setColor(Color.RED);
				g.fillRect((int)x, (int)y, 4, 4);
				g.drawRect((int)collisionBounds.getX(), (int)collisionBounds.getY(), (int)collisionBounds.getWidth(), (int)collisionBounds.getHeight());
			}*/
			runOnRender(g);
			return true;
		}
		runOnRender(g);
		return false;
	}
	
	public boolean isDead() {
		return isDead;
	}
	
	public void runOnRender(Graphics g) {}
	public abstract void dmg(double amount, Entity attacker);
	public abstract void kill();
	
	/**
	 *Returns a tile location with a possible decimal value
	 **/
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public double getHealth() {
		return health;
	}
	
	public double getMaxHealth() {
		return health;
	}
	
	public String getHealthAsString() {
		String hp = "";
		int i = 0;
		for(; i < health; i += maxHealth/9) {
			hp += "§c§l♥";
		}
		for(; i < maxHealth; i += maxHealth/9) {
			hp += "§8§l♥";
		}
		return hp;
	}
	
	public void setMaxHealth(double health) {
		this.maxHealth = health;
	}

	protected void setHealth(double health) {
		this.health = health;
	}

	protected int getWidth() {
		return width;
	}

	protected void setWidth(int width) {
		this.width = width;
	}

	protected int getHeight() {
		return height;
	}

	protected void setHeight(int height) {
		this.height = height;
	}

	protected Rectangle getCollisionBounds() {
		return collisionBounds;
	}

	protected void setCollisionBounds(Rectangle collisionBounds) {
		this.collisionBounds = collisionBounds;
	}

	protected String getItemDrop() {
		return itemDrop;
	}

	protected void setItemDrop(String itemDrop) {
		this.itemDrop = itemDrop;
	}

	public String getID() {
		return ID;
	}

	protected void setID(String iD) {
		ID = iD;
	}

	protected boolean isInvulnerable() {
		return invulnerable;
	}

	protected void setInvulnerable(boolean invulnerable) {
		this.invulnerable = invulnerable;
	}

	public boolean isShowing() {
		return isShowing;
	}
}
