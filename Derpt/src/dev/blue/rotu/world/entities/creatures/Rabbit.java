package dev.blue.rotu.world.entities.creatures;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dev.blue.rotu.Game;
import dev.blue.rotu.Main;
import dev.blue.rotu.Window;
import dev.blue.rotu.gfx.Camera;
import dev.blue.rotu.world.Location;
import dev.blue.rotu.world.World;
import dev.blue.rotu.world.entities.Creature;
import dev.blue.rotu.world.entities.Entity;
import dev.blue.rotu.world.entities.plants.RingLeaf;
import dev.blue.rotu.world.entities.structures.RabbitBurrow;
import dev.blue.rotu.world.tiles.Tile;

public class Rabbit extends Creature {//Next is changing breed timer to not be reset at birth, but to use age
	private int timer = new Random().nextInt(100);
	private boolean burrowed = false;
	private int strength;
	//private boolean eating = false;
	//private boolean scared = false;
	@Deprecated
	public Rabbit(Location location, Rabbit father, Rabbit mother) {
		super("Rabbit", location, 20, 20, 40, generateMaxHealth(father, mother), 600, 600, 100, "Rabbit", 1.4, 2000, 10000, 1000, 6, Main.getTextures().rabbit);
		this.tile = Tile.getTile(World.getTiles()[(int)location.getX()][(int)location.getY()]);
		if(father == null || mother == null) {
			generation = 1;
		}else if(father.getGeneration() > mother.getGeneration()) {
			this.generation = father.getGeneration()+1;
		}else this.generation = mother.getGeneration()+1;
		updateCollisionBounds();
		strength = 4;
		initialppu = 1.5;
	}
	
	public static double generateMaxHealth(Rabbit father, Rabbit mother) {
		if(father == null || mother == null) {
			return 40;
		}else return (father.getMaxHealth()+mother.getMaxHealth())/2+Creature.getMutatedHealth();
	}
	/*@Override
	public void runOnMove() {
		
	}*/
	
	/*@Override
	public void runOnRender(Graphics g) {
		Camera camera = Game.getInstance().getState().getCamera();
		double x = (dest.getX()*Tile.getWidth()-camera.getViewField().getTilesFromLeft()*Tile.getWidth()-camera.getXOffset())-width/2;
		double y = (dest.getY()*Tile.getWidth()-camera.getViewField().getTilesFromTop()*Tile.getWidth()-camera.getYOffset())-height;
		g.setColor(Color.RED);
		g.fillOval((int)x, (int)y, 3, 3);
	}*/
	
	@Override
	public void runOnUpdate() {
		updateCollisionBounds();
		if(!isDead) {
			if(foodLevel <= 0) {
				if(!burrowed) {
					dmg(0.05, null);
				}
			}
			if(foodLevel >= maxFoodLevel) {
				if(!burrowed) {
					heal(0.5);
				}
			}
			//if(World.getTiles()[(int)location.getX()][(int)location.getY()] != tile) {
			//	tile.getObjects().remove(this);
			//	this.tile = World.getTiles()[(int)location.getX()][(int)location.getY()];
			//	tile.addObject(this);
			//}
			if(burrowed) {
				if(!pregnant) {
					unburrow();
				}
			}
			if(target != null) {
				if(target instanceof RingLeaf) {
					RingLeaf ringleaf = (RingLeaf) target;
					if(ringleaf.isEaten()) {
						target = null;
					}
				}else if(target.isDead()) {
					target = null;
				}
			}
			if(dest.distance(location) >= ppu) {
				moving = true;
			}else moving = false;
			if(!moving) {
				if(foodLevel > 0) {
					if(!burrowed) {
						foodLevel -= 0.01;
					}
				}
				if(avoiding) {
					if(ppu == initialppu) {
						ppu*=2;
					}
					decideDestination();
				}else {
					ppu = initialppu;
					if(!attacking) {
						if(!burrowed) {
							if(timer >= 100) {//Will wait up to 100 ticks to decide a new destination. 
								decideDestination();
								timer = new Random().nextInt(100);
							}else timer++;
						}
					}else {
						if(target != null) {
							if(timer >= 30) {
								attack(target);
								timer = 0;
							}
							timer++;
						}else attacking = false;
					}
				}
			}else {
				if(foodLevel > 0) {
					foodLevel -= 0.2;
				}
			}
		}
	}
	
	private void updateCollisionBounds() {
		Camera camera = Game.getInstance().getState().getCamera();
		if(camera.getViewField() != null) {
			double x = (location.getX()*Tile.getWidth()-camera.getViewField().getTilesFromLeft()*Tile.getWidth()-camera.getXOffset());
			double y = (location.getY()*Tile.getWidth()-camera.getViewField().getTilesFromTop()*Tile.getWidth()-camera.getYOffset()-height);
			
			this.collisionBounds = new Rectangle((int)x-width/2+Window.tinySpace(), (int)y+height/2-(height/6), width-(Window.tinySpace()+Window.microSpace()), height/6);
		}
	}
	
	public void setHealth(double health) {
		this.health = health;
	}
	
	public boolean isBurrowed() {
		return burrowed;
	}
	
	public boolean attemptToBurrow() {
		//for(Entity each:World.getSurroundingObjects(location, 8)) {
		//	if(each instanceof RabbitBurrow) {
		//		attack(each);
		//		return true;
		//	}
		//}
		return false;
	}
	
	private void spawnBurrow(int babies) {
		/*RabbitBurrow burrow = null;
		for(Entity each:World.getSurroundingObjects(location, 6)) {
			if(each instanceof RabbitBurrow) {
				burrow = (RabbitBurrow) each;
			}
		}
		if(burrow == null) {
			burrow = new RabbitBurrow(new Location(location), this, (Rabbit)father);
			World.spawnEntity(burrow);
		}
		for(int i = 0; i < babies; i++) {
			burrow.addRabbit(new Rabbit(location, this, (Rabbit)father));
		}*/
	}
	
	public void unburrow() {
		//for(Entity each:World.getSurroundingObjects(location, 6)) {
		//	if(each instanceof Fox) {
		//		return;
		//	}
		//}
		isShowing = true;
		burrowed = false;
	}
	
	public void heal(double amount) {
		if(amount+health <= maxHealth) {
			dmg(amount*-1, null);
		}else dmg((maxHealth-health)*-1, null);
	}
	
	public void feed(double amount) {
		if(amount+foodLevel < maxFoodLevel) {
			foodLevel += amount;
		}else foodLevel = maxFoodLevel;
	}
	
	@Override
	public void dmg(double amount, Entity attacker) {
		if(!burrowed) {
			if(!avoiding) {
				if(attacker != null) {
					avoid(attacker);
					//for(Entity each:World.getSurroundingObjects(location, 4)) {
					//	if(each instanceof Rabbit) {
					//		((Rabbit) each).avoid(attacker);
					//	}
					//}
				}
			}
			health -= amount;
			if(health == maxHealth) {
				images[0] = Main.getTextures().rabbit;
			}else if(health >= maxHealth*0.75&&health < maxHealth) {
				images[0] = Main.getTextures().rabbitDmg_1;
			}else if(health >= maxHealth*0.5&&health < maxHealth*0.75) {
				images[0] = Main.getTextures().rabbitDmg_2;
			}else if(health >= maxHealth*0.25&&health < maxHealth*0.5) {
				images[0] = Main.getTextures().rabbitDmg_3;
			}else if(health > 0&&health < maxHealth*0.25) {
				images[0] = Main.getTextures().rabbitDmg_4;
			}else if(health <= 0) {
				images[0] = Main.getTextures().rabbitDead;
			}
			if(health <= 0) {
				if(!isDead) {
					kill();
				}else {
					corpseDecayTimer -= amount;
				}
			}
		}else {
			if(attacker instanceof Fox) {
				((Fox) attacker).setTarget(null);
			}
		}
	}
	
	@Override
	public boolean runOnAvoid() {
		if(attemptToBurrow()) {
			return false;
		}
		return true;
	}
	
	public int getGeneration() {
		return generation;
	}
	
	private void attack(Entity entity) {
		if(entity.getLocation().distance(location) > 0.2) {
			setDestination(entity.getLocation());
			attacking = true;
			target = entity;
		}else {
			if(entity instanceof RingLeaf) {
				RingLeaf ringleaf = (RingLeaf) entity;
				entity.dmg(strength, this);
				foodLevel += 8;
				if(ringleaf.isEaten()) {//Ate something
					attacking = false;
					decideDestination();
				}
			}else if(entity instanceof RabbitBurrow) {
				setBurrowed();
				RabbitBurrow burrow = (RabbitBurrow) entity;
				burrow.addRabbit(this);
			}
		}
	}
	
	public void setBurrowed() {
		moving = false;
		attacking = false;
		avoiding = false;
		isShowing = false;
		burrowed = true;
		ppu = initialppu;
		target = null;
		dest = location;
	}
	
	@Override
	public void birth() {
		pregnant = false;
		birthTimer = initialBirthTimer;
		int litterSize = new Random().nextInt(litter)+1;
		spawnBurrow(litterSize);
	}
	
	public void decideDestination() {
		if(!acquireTarget()) {
			Random rand = new Random();
			double x = (rand.nextInt(5)+rand.nextDouble());
			double y = (rand.nextInt(5)+rand.nextDouble());
			int option = rand.nextInt(4);
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
		}
	}
	
	@Override
	public boolean acquireTarget() {
		if(avoiding) {
			avoiding = false;
			ppu = initialppu;
		}
		List<Entity> options = new ArrayList<Entity>();
		/*for(Entity each:World.getSurroundingObjects(location, 3)) {
			if(each instanceof RingLeaf) {
				if(foodLevel < maxFoodLevel) {
					RingLeaf ringleaf = (RingLeaf) each;
					if(!ringleaf.isEaten()) {
						options.add(each);
					}
				}
			}else if(each instanceof Rabbit) {
				if(foodLevel > minFoodLevel) {
					Rabbit rabbit = (Rabbit) each;
					if(!rabbit.isDead && !isDead) {
						if(getGender() != rabbit.getGender()) {
							if(!rabbit.isPregnant() && !isPregnant()) {
								if(rabbit.canBreed() && canBreed()) {
									options.add(each);
								}
							}
						}
					}
				}
			}else if(each instanceof Fox) {
				options.clear();
				options.add(each);
				break;
			}
		}*/
		Random rand = new Random();
		if(options.size() > 0) {
			Entity target = options.get(rand.nextInt(options.size()));
			if(target instanceof RingLeaf) {
				attack(target);
			}else if(target instanceof Rabbit) {
				Rabbit rabbit = (Rabbit) target;
				if(rabbit.getGender() == Gender.MALE) {
					rabbit.breed(this);
				}else breed(rabbit);
			}else if(target instanceof Fox) {
				avoid(target);
			}
			return true;
		}
		return false;
	}
	
	@Override
	protected boolean searchFood() {
		return false;
	}
}
