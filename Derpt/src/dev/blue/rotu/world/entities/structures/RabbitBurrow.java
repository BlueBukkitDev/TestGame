package dev.blue.rotu.world.entities.structures;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import dev.blue.rotu.Game;
import dev.blue.rotu.Main;
import dev.blue.rotu.world.Location;
import dev.blue.rotu.world.World;
import dev.blue.rotu.world.entities.Entity;
import dev.blue.rotu.world.entities.StaticEntity;
import dev.blue.rotu.world.entities.creatures.Fox;
import dev.blue.rotu.world.entities.creatures.Rabbit;

public class RabbitBurrow extends StaticEntity {
	private List<Rabbit> rabbits = new ArrayList<Rabbit>();
	private int rabbitCount;
	private Rabbit mother, father;
	int spawnTimer = 2000;

	public RabbitBurrow(Location location, Rabbit mother, Rabbit father) {
		super("Rabbit Burrow", location, 80, 80, 100, "Rabbit Burrow", Main.getTextures().rabbitBurrow);
		this.collisionBounds = new Rectangle((int)location.getX(), (int)location.getY(), width, height);
		this.mother = mother;
		this.father = father;
	}
	
	public List<Rabbit> getRabbits(){
		return rabbits;
	}
	
	public void addRabbit(Rabbit rabbit) {
		rabbits.add(rabbit);
		rabbit.setHealth(rabbit.getMaxHealth());
		rabbit.setFoodLevel(rabbit.getMaxFoodLevel());
		rabbitCount++;
	}
	
	public int getRabbitCount() {
		return rabbitCount;
	}

	@Override
	public void update() {
		spawnTimer--;
		if(spawnTimer <= 0) {
			spawnTimer = 2000;//foxes now need a way to die. 
			Rabbit rabbit = new Rabbit(new Location(location), mother, father);
			//World.spawnEntity(rabbit);
			rabbit.setBurrowed();
			addRabbit(rabbit);
			dmg(15, null);
		}
		//for(Entity each:World.getSurroundingObjects(location, 6)) {
		//	if(each instanceof Fox) {
		//		return;
		//	}
		//}
		for(Rabbit each:rabbits) {
			each.unburrow();
			rabbitCount--;
		}
		rabbits.clear();
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
		this.isShowing = false;
		Game.getInstance().getState().getEntityManager().unregisterField(this);
	}
}
