package dev.blue.rotu.world.entities;

import java.awt.image.BufferedImage;
import java.util.Random;

import dev.blue.rotu.Game;
import dev.blue.rotu.gfx.Animation;
import dev.blue.rotu.world.Location;
import dev.blue.rotu.world.World;
import dev.blue.rotu.world.tiles.Tile;

public abstract class Creature extends Entity {
	protected Location dest;
	protected double ppu;
	protected double initialppu;
	protected Tile tile;
	protected Gender gender;
	protected boolean pregnant;
	protected Creature father;
	protected boolean moving = false;
	protected boolean attacking = false;
	protected boolean avoiding = false;
	protected boolean searching = false;
	protected int birthTimer;
	protected int breedTimer;
	protected final int initialBirthTimer;
	protected final int initialBreedTimer;
	protected int corpseDecayTimer;
	protected int litter;
	protected Entity target;
	protected int generation;
	protected double foodLevel, maxFoodLevel, minFoodLevel; 
	//private Animation walkN;
	//private Animation walkNW;
	//private Animation walkW;
	//private Animation walkSW;
	//private Animation walkS;
	//private Animation walkSE;
	//private Animation walkE;
	//private Animation walkNE;
	protected Animation ambient;

	public Creature(String ID, Location location, int width, int height, double health, double maxHealth, double foodLevel, double maxFoodLevel, double minFoodLevel, String itemDrop, double ppu, int birthTimer, int breedTimer, int corpseDecayTimer, int litter, 
			/*Animation walkN, Animation walkNW, Animation walkW, Animation walkSW, Animation walkS, Animation walkSE, Animation walkE, Animation walkNE,*/ BufferedImage... images){
		super(ID, location, width, height, health, maxHealth, itemDrop, images);
		//this.ppu = ppu;
		//this.walkN = walkN;
		//this.walkNW = walkNW;
		//this.walkW = walkW;
		//this.walkSW = walkSW;
		//this.walkS = walkS;
		//this.walkSE = walkSE;
		//this.walkE = walkE;
		//this.walkNE = walkNE;
		//this.ambient = ambient;
		this.foodLevel = foodLevel;
		this.maxFoodLevel = maxFoodLevel;
		this.minFoodLevel = minFoodLevel;
		if(new Random().nextInt(2) == 0) {
			gender = Gender.FEMALE;
		}else gender = Gender.MALE;
		pregnant = false;
		this.birthTimer = birthTimer;
		this.breedTimer = new Random().nextInt(breedTimer);
		initialBirthTimer = birthTimer;
		initialBreedTimer = breedTimer;
		this.corpseDecayTimer = corpseDecayTimer;
		this.litter = litter;
		this.ppu = ppu;
		this.initialppu = ppu;
		this.dest = location;
	}
	
	public static double getMutatedHealth() {
		Random rand = new Random();
		int control = rand.nextInt(2);
		if(control == 0) {
			control = -1;
		}else control = 1;
		return ratDec((rand.nextInt(2)+rand.nextDouble())*control);
	}
	
	private static double ratDec(double d) {
		return Math.ceil(d*10)/10;
	}
	
	public void setFoodLevel(double amount) {
		this.foodLevel = amount;
	}
	
	public double getFoodLevel() {
		return foodLevel;
	}
	
	public double getMaxFoodLevel() {
		return maxFoodLevel;
	}
	
	public double getMinFoodLevel() {
		return minFoodLevel;
	}
	
	public Gender getGender() {
		return gender;
	}
	
	public int getGeneration() {
		return generation;
	}
	
	public boolean isPregnant() {
		return pregnant;
	}
	
	public void impregnate() {
		this.pregnant = true;
	}
	
	public boolean canBreed() {
		return breedTimer <= 0;
	}
	
	public void breed(Creature target) {
		if(getGender() == Gender.MALE && target.getGender() == Gender.FEMALE) {
			target.impregnate();
			target.father = this;
			this.breedTimer = initialBreedTimer;
		}
	}
	
	public abstract void birth();
	
	public void setDestination(Location dest) {
		this.dest = dest;
	}
	
	public Location getDestination() {
		return dest;
	}
	
	public void move() {
		adjustDestinationForEdges();
		double distx = dest.getX() - this.getLocation().getX();
		double disty = dest.getY() - this.getLocation().getY();
		double distHypot = Math.hypot(distx, disty);
	
		double control = distHypot/ppu;
		distx /= control;
		disty /= control;
		
		this.getLocation().add(distx/Tile.getWidth(), disty/Tile.getWidth());
	}

	@Override
	public void update() {
		if(isDead) {
			if(dest.distance(location) > 0.1) {
				dest = new Location(location.getX(), location.getY());
			}
			if(corpseDecayTimer <= 0) {
				Game.getInstance().getState().getEntityManager().unregisterField(this);
				if(tile != null) {
					tile.getObjects().remove(this);
				}
			}else corpseDecayTimer--;
		}else {
			if(pregnant) {
				if(birthTimer > 0) {
					birthTimer--;
				}
				if(birthTimer == 0) {
					birth();
				}
			}else {
				if(breedTimer > 0) {
					breedTimer--;
				}
			}
		}
		runOnUpdate();
		if(dest != null) {
			if(dest.distance(getLocation()) > 0.1) {
				if(target == null) {
					acquireTarget();
				}
				move();
				if(tile != null) {
					tile.setObjects(World.orderEntities(tile.getObjects()));//Change this. Shouldn't need a null check. 
				}
				runOnMove();
				//this.walkN.setLocation(loc);
				//this.walkNW.setLocation(loc);
				//this.walkW.setLocation(loc);
				//this.walkSW.setLocation(loc);
				//this.walkS.setLocation(loc);
				//this.walkSE.setLocation(loc);
				//this.walkE.setLocation(loc);
				//this.walkNE.setLocation(loc);
				//this.ambient.getLocation().add(moveX, moveY);
				//System.out.println("PPU = "+ppu+", distance = "+dest.distance(getLocation())+", moveX/moveY = "+moveY+","+moveY);
			}
		}
	}
	
	protected abstract boolean acquireTarget();
	protected abstract boolean searchFood();
	
	public void runOnMove() {}
	
	public void runOnUpdate() {}
	
	protected void avoid(Entity enemy) {
		if(!runOnAvoid()) {
			
		}
		avoiding = true;
		attacking = false;
		if(ppu == initialppu) {
			ppu *= 2;
		}
		Random rand = new Random();
		double x = (rand.nextInt(5)+rand.nextDouble());
		double y = (rand.nextInt(5)+rand.nextDouble());
		int option = 0;
		if(enemy.getLocation().getX() > location.getX()&&enemy.getLocation().getY() > location.getY()) {//Enemy is SE
			option = 2;
		}else if(enemy.getLocation().getX() > location.getX()&&enemy.getLocation().getY() < location.getY()){//Enemy is NE
			option = 1;
		}else if(enemy.getLocation().getX() < location.getX()&&enemy.getLocation().getY() < location.getY()){//Enemy is NW
			option = 0;
		}else if(enemy.getLocation().getX() < location.getX()&&enemy.getLocation().getY() > location.getY()){//Enemy is SW
			option = 3;
		}
		if(option == 0) {
			x = location.getX() + x;//SE
			y = location.getY() + y;
		}else if(option == 1) {
			x = location.getX() - x;//SW
			y = location.getY() + y;
		}else if(option == 2) {
			x = location.getX() - x;//NW
			y = location.getY() - y;
		}else if(option == 3) {
			x = location.getX() + x;//NE
			y = location.getY() - y;
		}
		setDestination(new Location(x, y));
		adjustDestinationForEdges();
	}
	/**
	 *returns whether to continue with avoiding. 
	 **/
	protected boolean runOnAvoid() {
		return true;
	}

	@Override
	public void dmg(double amount, Entity attacker) {
		health -= amount;
		if(health <= 0) {
			kill();
		}
	}

	@Override
	public void kill() {
		this.isDead = true;
		this.dest = new Location(location.getX(), location.getY());
	}
	/*I need to make the avatar spawn in based on the map location by adding the viewfield's X or camera's X to the current spawn X so that it gets offset. 
	 *I need to make it so that the avatar doesn't go flying off the screen if you click and then move the screen. Something gets brought to infinity pretty fast. 
	 *I need to limit the creature rendering to scrictly within the viewfield. 
	 **/
	
	protected void adjustDestinationForEdges() {
		Random rand = new Random();
		if(dest.getX() < 1 || dest.getY() < 1) {
			setDestination(new Location(location.getX()+rand.nextInt(6), location.getY()+rand.nextInt(6)));
			adjustDestinationForEdges();
		}else if(dest.getX() > World.getTiles().length-1 || dest.getY() > World.getTiles()[0].length-1) {
			setDestination(new Location(location.getX()-rand.nextInt(6), location.getY()-rand.nextInt(6)));
			adjustDestinationForEdges();
		}
	}
	
	public enum Gender {
		MALE, FEMALE
	}
}
