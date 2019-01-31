package dev.blue.rotu.world.entities.creatures;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dev.blue.rotu.Game;
import dev.blue.rotu.Main;
import dev.blue.rotu.gfx.Camera;
import dev.blue.rotu.world.Location;
import dev.blue.rotu.world.World;
import dev.blue.rotu.world.entities.Creature;
import dev.blue.rotu.world.entities.Entity;
import dev.blue.rotu.world.tiles.Tile;

public class Fox extends Creature {
	private boolean moving = false;
	private int timer = new Random().nextInt(100);
	private boolean attacking = false;
	private Tile tile;
	private int strength;
	private int chaseTimer;
	private int initialChaseTimer;
	//private boolean eating = false;
	//private boolean scared = false;
	@Deprecated
	public Fox(Location location, int parentGeneration) {
		super("Fox", location, 60, 30, 90, 90, 800, 800, 350, "Fox", 2.9, 10000, 1000, 2800, 3, Main.getTextures().fox);
		this.tile = Tile.getTile(World.getTiles()[(int)location.getX()][(int)location.getY()]);//This will not work anymore. Thus deprecated. 
		updateCollisionBounds();
		tile.addObject(this);
		strength = 30;
		chaseTimer = 450;
		initialChaseTimer = 450;
		generation = parentGeneration+1;//fox is moving after death... needs fixed//fixed
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
	
	public int getGeneration() {
		return generation;
	}
	
	@Override
	public void runOnUpdate() {
		updateCollisionBounds();
		if(!isDead) {
			if(foodLevel <= 0) {
				dmg(0.02, null);
			}
			if(foodLevel >= maxFoodLevel) {
				heal(0.5);
			}
			if(target != null) {
				if(target.isDead()) {
					target = null;
				}
				if(target instanceof Rabbit) {
					Rabbit rabbit = (Rabbit) target;
					if(rabbit.isBurrowed()) {
						target = null;
					}
				}
			}
			if(dest != null && dest.distance(location) >= ppu) {
				moving = true;
			}else moving = false;
			if(!moving) {
				if(foodLevel > 0) {
					foodLevel -= 0.01;
				}else {
					if(!attacking) {
						if(!searching) {
							if(foodLevel < minFoodLevel) {
								searchFood();
							}
						}else if(acquireTarget()) {
							searching = false;
						}
					}
				}
				if(!attacking) {
					if(timer >= 100) {
						decideDestination();
						timer = new Random().nextInt(100);
					}else timer++;
				}else {
					if(chaseTimer > 0) {
						chaseTimer--;
						if(target != null) {
							if(timer >= 30) {
								attack(target);
								timer = 0;
							}
							timer++;
						}else {
							attacking = false;
							chaseTimer = initialChaseTimer;
						}
					}else{
						chaseTimer = initialChaseTimer;
						target = null;
						dest = location;
						moving = false;
						attacking = false;
						timer = 0;
					}
				}
			}else {
				if(foodLevel > 0) {
					foodLevel -= 0.2;
				}
			}
			adjustDestinationForEdges();
			if(Tile.getTile(World.getTiles()[(int)location.getX()][(int)location.getY()]) != tile) {
				tile.getObjects().remove(this);
				this.tile = Tile.getTile(World.getTiles()[(int)location.getX()][(int)location.getY()]);
				tile.addObject(this);
			}
		}
	}
	
	private void updateCollisionBounds() {
		Camera camera = Game.getInstance().getState().getCamera();
		if(camera.getViewField() != null) {
			double x = (location.getX()*Tile.getWidth()-camera.getViewField().getTilesFromLeft()*Tile.getWidth()-camera.getXOffset());
			double y = (location.getY()*Tile.getWidth()-camera.getViewField().getTilesFromTop()*Tile.getWidth()-camera.getYOffset()-height);
			
			this.collisionBounds = new Rectangle((int)x+width/6, (int)y+height/2-(height/6), (int)(width*0.4), height/6);
		}
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
			if(!avoiding) {
				if(attacker != null) {
					avoid(attacker);
					//for(Entity each:World.getSurroundingObjects(location, 7)) {
					//	if(each instanceof Fox) {
					//		((Fox) each).avoid(attacker);
					//	}
					//}
				}
			}
			health -= amount;
			if(health == maxHealth) {
				images[0] = Main.getTextures().fox;
			}else if(health >= maxHealth*0.75&&health < maxHealth) {
				images[0] = Main.getTextures().foxDmg_1;
			}else if(health >= maxHealth*0.5&&health < maxHealth*0.75) {
				images[0] = Main.getTextures().foxDmg_2;
			}else if(health >= maxHealth*0.25&&health < maxHealth*0.5) {
				images[0] = Main.getTextures().foxDmg_3;
			}else if(health > 0&&health < maxHealth*0.25) {
				images[0] = Main.getTextures().foxDmg_4;
			}else if(health <= 0) {
				images[0] = Main.getTextures().foxDead;
			}
			if(health <= 0) {
				kill();
			}

	}
	
	private void attack(Entity entity) {
		if(entity instanceof Rabbit) {
			Rabbit rabbit = (Rabbit) entity;
			if(rabbit.getLocation().distance(location) > 0.3) {
				setDestination(rabbit.getLocation());
				attacking = true;
				target = rabbit;
			}else {
				if(!rabbit.isDead()) {
					rabbit.dmg(strength, this);
				}else {
					rabbit.dmg(strength*3, this);
					feed(25);
				}
			}
		}
	}
	
	public void setTarget(Entity target) {
		this.target = target;
	}
	
	@Override
	public void birth() {
		pregnant = false;
		birthTimer = initialBirthTimer;
		int litterSize = new Random().nextInt(litter)+1;
		for(int i = 0; i < litterSize; i++) {
			if(location.getX() < World.getTiles().length && location.getX() > 0 && location.getY() < World.getTiles()[0].length && location.getY() > 0) {
				//World.spawnEntity(new Fox(new Location(location), generation));
			}
		}
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
		List<Entity> options = new ArrayList<Entity>();
		/*for(Entity each:World.getSurroundingObjects(location, 5)) {
			if(each instanceof Rabbit) {
				if(foodLevel < maxFoodLevel) {
					Rabbit rabbit = (Rabbit) each;
					if(!rabbit.isBurrowed()) {
						options.add(each);
						if(rabbit.isDead()) {
							options.clear();
							options.add(rabbit);
							break;
						}
					}
				}
			}else if(each instanceof Fox) {
				if(foodLevel > minFoodLevel) {
					Fox fox = (Fox) each;
					if(!fox.isDead && !isDead) {
						if(getGender() != fox.getGender()) {
							if(!fox.isPregnant() && !isPregnant()) {
								if(fox.canBreed() && canBreed()) {
									options.add(each);
								}
							}
						}
					}
				}
			}
		}*/
		Random rand = new Random();
		if(options.size() > 0) {
			Entity target = options.get(rand.nextInt(options.size()));
			if(target instanceof Rabbit) {
				attack(target);
			}else if(target instanceof Fox) {
				Fox fox = (Fox) target;
				if(fox.getGender() == Gender.MALE) {
					fox.breed(this);
				}else breed(fox);
			}
			return true;
		}
		return false;
	}
	
	@Override
	protected boolean searchFood() {
		if(!acquireTarget()) {
			Random rand = new Random();
			double x = (rand.nextInt(15)+rand.nextDouble()+10);
			double y = (rand.nextInt(15)+rand.nextDouble()+10);
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
			adjustDestinationForEdges();
			searching = true;
			return true;
		}
		return false;
	}
}
