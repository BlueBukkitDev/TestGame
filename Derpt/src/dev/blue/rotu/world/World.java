package dev.blue.rotu.world;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import dev.blue.rotu.Game;
import dev.blue.rotu.Main;
import dev.blue.rotu.world.entities.Entity;
import dev.blue.rotu.world.entities.creatures.Fox;
import dev.blue.rotu.world.entities.creatures.Rabbit;
import dev.blue.rotu.world.entities.plants.BelinianAylflower;
import dev.blue.rotu.world.entities.plants.CoralGrass;
import dev.blue.rotu.world.entities.plants.Dolor;
import dev.blue.rotu.world.entities.plants.LooseLeafGrass;
import dev.blue.rotu.world.entities.plants.Mytus;
import dev.blue.rotu.world.entities.plants.RingLeaf;
import dev.blue.rotu.world.tiles.DirtTile;
import dev.blue.rotu.world.tiles.GrassTile;
import dev.blue.rotu.world.tiles.SandTile;
import dev.blue.rotu.world.tiles.Tile;

public class World {

	private int pixelWidth, pixelHeight;
	private static byte[][] tiles;

	public World() {
		loadTiles();
		//processTiles();
		//populateWorld();
	}

	private void loadTiles() {
		/*List<String> lines = readLines();
		tiles = new byte[lines.get(0).split(",").length][lines.size()];
		int x = 0, y = 0;
		for (String ys : lines) {
			for (String xs : ys.split(",")) {
				xs.trim();
				tiles[x][y] = Byte.parseByte(xs);
				x++;
			}
			y++;
			x = 0;
		}*/
		doThings();
		pixelWidth = tiles.length * Tile.getWidth();
		pixelHeight = tiles[0].length * Tile.getWidth();
		System.out.println("World Size: "+tiles.length+"x"+tiles[0].length+"("+tiles.length*tiles[0].length+" tiles)");
	}
	
	private void doThings() {
		long time = System.nanoTime();
		File file = new File("res/map.bin");
		if(file == null || !file.getName().endsWith(".bin")) {
			int width = 200;//Set default map width in Window?
			tiles = new byte[width][width];
			int lastPercent = 0;
			System.out.println("Loading tiles: 0%");
			
			for(int i = 0; i < width; i++) {
				int percentage = (int)((double)i/(double)width*100d);
				if(percentage > lastPercent+5) {
					System.out.println("Loading tiles: "+percentage+"%");
					lastPercent = percentage;
				}
				for(int j = 0; j < width; j++) {
					tiles[i][j] = 0;
				}
			}
			
			System.out.println("Loading tiles: 100% ("+((System.nanoTime()-time)/1000000000d)+")");
		}else {
			if(file.exists()) {
				try {
					int width = (int)Math.sqrt((double)file.length());
					tiles = new byte[width][width];
					byte[] all = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
					for(int i = 0; i < width; i++) {
						tiles[i] = Arrays.copyOfRange(all, i*width, (i*width)+width);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private List<String> readLines() {
		BufferedReader reader = null;
		List<String> lines = new ArrayList<String>();
		try {
			File file = new File("res/map.txt");
			reader = new BufferedReader(new FileReader(file));

			String line;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return lines;
	}
	
	/*private void populateWorld() {
		Random rand = new Random();
		for(int y = 0; y < tiles[0].length; y++) {
			for(int x = 0; x < tiles.length; x++) {
				List<Entity> objects = new ArrayList<Entity>();
				int chance = rand.nextInt(10000);
				if(tiles[x][y].getID() == GrassTile.getSID()) {//if grass
					if(chance <= 2000) {
						Mytus[] plants = Mytus.generateClump(x, y);
						for(int i = 0; i < plants.length; i++) {
							objects.add(plants[i]);
						}
					}
					chance = rand.nextInt(10000);
					if(chance <= 9500) {
						objects.add(new LooseLeafGrass(new Location(x, y)));
					}
					chance = rand.nextInt(10000);
					if(chance <= 800) {
						objects.add(new RingLeaf(new Location(x, y)));
					}
					chance = rand.nextInt(10000);
					if(chance <= 200) {
						BelinianAylflower[] plants = BelinianAylflower.generateClump(x, y);
						for(int i = 0; i < plants.length; i++) {
							objects.add(plants[i]);
						}
					}
					chance = rand.nextInt(10000);
					if(chance <= 50) {
						objects.add(new Rabbit(new Location(x, y), null, null));
					}
					chance = rand.nextInt(10000);
					if(chance <= 4) {
						objects.add(new Fox(new Location(x, y), 0));
					}
				}else if(tiles[x][y].getID() == DirtTile.getSID()) {//if dirt
					if(getSurroundingTiles(x, y).contains(GrassTile.getSID()+"")) {//If dirt, but bordering grass
						if(getSurroundingTiles(x, y).replaceFirst(GrassTile.getSID()+"", "").contains(GrassTile.getSID()+"")) {
							if(chance <= 200) {
								CoralGrass[] plants = CoralGrass.generateClump(x, y);
								for(int i = 0; i < plants.length; i++) {
									objects.add(plants[i]);
								}
							}
						}
					}else {
						if(chance <= 6) {
							Dolor[] plants = Dolor.generateClump(x, y);
							for(int i = 0; i < plants.length; i++) {
								objects.add(plants[i]);
							}
						}
					}
				}
				objects = orderEntities(objects);
				for(Entity each:objects) {
					spawnEntity(each);
				}
			}
		}
	}*/
	
	/*public static void spawnEntity(Entity entity) {
		Game.getInstance().getState().getEntityManager().registerFieldConcurrently(entity);
		tiles[(int)entity.getLocation().getX()][(int)entity.getLocation().getY()].addObject(entity);
	}*/
	
	public static List<Entity> orderEntities(List<Entity> entities){//needs updated
		List<Entity> ordered = new ArrayList<Entity>();
		final int size = entities.size();
		for(int i = 0; i < size; i++) {
			Entity chosen = entities.get(0);
			double y = chosen.getLocation().getY();
			for(Entity each:entities) {
				if(each.getLocation().getY() < y) {
					chosen = each;
					y = each.getLocation().getY();
				}
			}
			entities.remove(chosen);
			ordered.add(chosen);
		}
		return ordered;
	}
	
	/*private void processTiles() {
		for (int y = 0; y < tiles.length; y++) {
			for (int x = 0; x < tiles[0].length; x++) {
				Tile now = tiles[x][y];
				String id = now.getID() + "";
				String[] parts = getSurroundingTiles(x, y).split(",");
				String N = parts[7];
				String NW = parts[0];
				String W = parts[1];
				String SW = parts[2];
				String S = parts[3];
				String SE = parts[4];
				String E = parts[5];
				String NE = parts[6];
				boolean tl = true, bl = true, br = true, tr = true;
				boolean te = true, le = true, be = true, re = true;//true means stays as the default texture
				if (now.getID() == DirtTile.getSID() || now.getID() == SandTile.getSID()) {// dirt, therefore non-dominant. Idk how I can make this work for
														// more tiles.
					if (N.equalsIgnoreCase(GrassTile.getSID() + "") && W.equalsIgnoreCase(GrassTile.getSID() + "")) {
						tl = false;
					}
					if (W.equalsIgnoreCase(GrassTile.getSID() + "") && S.equalsIgnoreCase(GrassTile.getSID() + "")) {
						bl = false;
					}
					if (S.equalsIgnoreCase(GrassTile.getSID() + "") && E.equalsIgnoreCase(GrassTile.getSID() + "")) {
						br = false;
					}
					if (E.equalsIgnoreCase(GrassTile.getSID() + "") && N.equalsIgnoreCase(GrassTile.getSID() + "")) {
						tr = false;
					}
					if (N.equalsIgnoreCase(GrassTile.getSID() + "") && (NW.equalsIgnoreCase(GrassTile.getSID() + "") || NE.equalsIgnoreCase(GrassTile.getSID() + ""))) {// Top Edge is fuzzed
						te = false;
					}
					if (W.equalsIgnoreCase(GrassTile.getSID() + "") && (NW.equalsIgnoreCase(GrassTile.getSID() + "") || SW.equalsIgnoreCase(GrassTile.getSID() + ""))) {// Left Edge is fuzzed
						le = false;
					}
					if (S.equalsIgnoreCase(GrassTile.getSID() + "") && (SW.equalsIgnoreCase(GrassTile.getSID() + "") || SE.equalsIgnoreCase(GrassTile.getSID() + ""))) {// Bottom Edge is fuzzed
						be = false;
					}
					if (E.equalsIgnoreCase(GrassTile.getSID() + "") && (NE.equalsIgnoreCase(GrassTile.getSID() + "") || SE.equalsIgnoreCase(GrassTile.getSID() + ""))) {// Right Edge is fuzzed
						re = false;
					}
				} else if (now.getID() == GrassTile.getSID()) {
					if (N.equalsIgnoreCase(DirtTile.getSID() + "") && NW.equalsIgnoreCase(DirtTile.getSID() + "")
							&& W.equalsIgnoreCase(DirtTile.getSID() + "")) {
						tl = false;
					}
					if (W.equalsIgnoreCase(DirtTile.getSID() + "") && SW.equalsIgnoreCase(DirtTile.getSID() + "")
							&& S.equalsIgnoreCase(DirtTile.getSID() + "")) {
						bl = false;
					}
					if (S.equalsIgnoreCase(DirtTile.getSID() + "") && SE.equalsIgnoreCase(DirtTile.getSID() + "")
							&& E.equalsIgnoreCase(DirtTile.getSID() + "")) {
						br = false;
					}
					if (E.equalsIgnoreCase(DirtTile.getSID() + "") && NE.equalsIgnoreCase(DirtTile.getSID() + "")
							&& N.equalsIgnoreCase(DirtTile.getSID() + "")) {
						tr = false;
					}
				}
				BufferedImage[] images = new BufferedImage[4];
				//if (now.getDominance() == 1) {// If tl/bl/br/tr == true, it is a sharp corner. If te/le/be/re
														// == true, it is a sharp edge.
					// Only sides of rounded corners can be fuzzed, unless no sides are rounded.
					if(now.getID() == GrassTile.getSID()) {
						images[0] = Main.getTextures().grassTLQ;
						images[1] = Main.getTextures().grassBLQ;
						images[2] = Main.getTextures().grassBRQ;
						images[3] = Main.getTextures().grassTRQ;
					}else if(now.getID() == DirtTile.getSID()) {
						images[0] = Main.getTextures().dirtTLQ;
						images[1] = Main.getTextures().dirtBLQ;
						images[2] = Main.getTextures().dirtBRQ;
						images[3] = Main.getTextures().dirtTRQ;
					}else if(now.getID() == SandTile.getSID()) {
						images[0] = Main.getTextures().sandTLQ;
						images[1] = Main.getTextures().sandBLQ;
						images[2] = Main.getTextures().sandBRQ;
						images[3] = Main.getTextures().sandTRQ;
					}
					if (!tl && !bl && !br && !tr) {
						if(now.getID() == DirtTile.getSID()) {
							images[0] = Main.getTextures().dirtTLRGQ;
							images[1] = Main.getTextures().dirtBLRGQ;
							images[2] = Main.getTextures().dirtBRRGQ;
							images[3] = Main.getTextures().dirtTRRGQ;
						}else if(now.getID() == SandTile.getSID()) {
							images[0] = Main.getTextures().sandTLRGQ;
							images[1] = Main.getTextures().sandBLRGQ;
							images[2] = Main.getTextures().sandBRRGQ;
							images[3] = Main.getTextures().sandTRRGQ;
						}
					} else if (!tl && !bl) {
						if(now.getID() == DirtTile.getSID()) {
							images[0] = Main.getTextures().dirtTLRGQ;
							images[1] = Main.getTextures().dirtBLRGQ;
							images[2] = Main.getTextures().dirtBLCGQ;
							images[3] = Main.getTextures().dirtTLCGQ;
							if (!NE.equalsIgnoreCase(id)) images[3] = Main.getTextures().dirtTREHGQ;
							if (!SE.equalsIgnoreCase(id)) images[2] = Main.getTextures().dirtBREHGQ;
						}else if(now.getID() == SandTile.getSID()) {
							images[0] = Main.getTextures().sandTLRGQ;
							images[1] = Main.getTextures().sandBLRGQ;
							images[2] = Main.getTextures().sandBLCGQ;
							images[3] = Main.getTextures().sandTLCGQ;
							if (!NE.equalsIgnoreCase(id)) images[3] = Main.getTextures().sandTREHGQ;
							if (!SE.equalsIgnoreCase(id)) images[2] = Main.getTextures().sandBREHGQ;
						}
					} else if (!bl && !br) {
						if(now.getID() == DirtTile.getSID()) {
							images[0] = Main.getTextures().dirtBLCGQ;
							images[1] = Main.getTextures().dirtBLRGQ;
							images[2] = Main.getTextures().dirtBRRGQ;
							images[3] = Main.getTextures().dirtBRCGQ;
							if (!NW.equalsIgnoreCase(id)) images[0] = Main.getTextures().dirtTLEVGQ;
							if (!NE.equalsIgnoreCase(id)) images[3] = Main.getTextures().dirtTREVGQ;
						}else if(now.getID() == SandTile.getSID()) {
							images[0] = Main.getTextures().sandBLCGQ;
							images[1] = Main.getTextures().sandBLRGQ;
							images[2] = Main.getTextures().sandBRRGQ;
							images[3] = Main.getTextures().sandBRCGQ;
							if (!NW.equalsIgnoreCase(id)) images[0] = Main.getTextures().sandTLEVGQ;
							if (!NE.equalsIgnoreCase(id)) images[3] = Main.getTextures().sandTREVGQ;
						}
					} else if (!br && !tr) {
						if(now.getID() == DirtTile.getSID()) {
							images[0] = Main.getTextures().dirtTRCGQ;
							images[1] = Main.getTextures().dirtBRCGQ;
							images[2] = Main.getTextures().dirtBRRGQ;
							images[3] = Main.getTextures().dirtTRRGQ;
							if (!NW.equalsIgnoreCase(id)) images[0] = Main.getTextures().dirtTLEHGQ;
							if (!SW.equalsIgnoreCase(id)) images[1] = Main.getTextures().dirtBLEHGQ;
						}else if(now.getID() == SandTile.getSID()) {
							images[0] = Main.getTextures().sandTRCGQ;
							images[1] = Main.getTextures().sandBRCGQ;
							images[2] = Main.getTextures().sandBRRGQ;
							images[3] = Main.getTextures().sandTRRGQ;
							if (!NW.equalsIgnoreCase(id)) images[0] = Main.getTextures().sandTLEHGQ;
							if (!SW.equalsIgnoreCase(id)) images[1] = Main.getTextures().sandBLEHGQ;
						}
					} else if (!tr && !tl) {
						if(now.getID() == DirtTile.getSID()) {
							images[0] = Main.getTextures().dirtTLRGQ;
							images[1] = Main.getTextures().dirtTLCGQ;
							images[2] = Main.getTextures().dirtTRCGQ;
							images[3] = Main.getTextures().dirtTRRGQ;
							if (!SW.equalsIgnoreCase(id)) images[1] = Main.getTextures().dirtBLEVGQ;
							if (!SE.equalsIgnoreCase(id)) images[2] = Main.getTextures().dirtBREVGQ;
						}else if(now.getID() == SandTile.getSID()) {
							images[0] = Main.getTextures().sandTLRGQ;
							images[1] = Main.getTextures().sandTLCGQ;
							images[2] = Main.getTextures().sandTRCGQ;
							images[3] = Main.getTextures().sandTRRGQ;
							if (!SW.equalsIgnoreCase(id)) images[1] = Main.getTextures().sandBLEVGQ;
							if (!SE.equalsIgnoreCase(id)) images[2] = Main.getTextures().sandBREVGQ;
						}
					} else if (!tl) {
						if(now.getID() == DirtTile.getSID()) {
							images[0] = Main.getTextures().dirtTLRGQ;
							images[1] = Main.getTextures().dirtTLCGQ;
							images[3] = Main.getTextures().dirtTLCGQ;
							if (!te && !NE.equalsIgnoreCase(id)) images[3] = Main.getTextures().dirtTREHGQ;
							if (!le && !SW.equalsIgnoreCase(id)) images[1] = Main.getTextures().dirtBLEVGQ;
						}else if(now.getID() == SandTile.getSID()) {
							images[0] = Main.getTextures().sandTLRGQ;
							images[1] = Main.getTextures().sandTLCGQ;
							images[3] = Main.getTextures().sandTLCGQ;
							if (!te && !NE.equalsIgnoreCase(id)) images[3] = Main.getTextures().sandTREHGQ;
							if (!le && !SW.equalsIgnoreCase(id)) images[1] = Main.getTextures().sandBLEVGQ;
						}
					} else if (!bl) {
						if(now.getID() == DirtTile.getSID()) {
							images[0] = Main.getTextures().dirtBLCGQ;
							images[1] = Main.getTextures().dirtBLRGQ;
							images[2] = Main.getTextures().dirtBLCGQ;
							if (!le && !NW.equalsIgnoreCase(id)) images[0] = Main.getTextures().dirtTLEVGQ;
							if (!be && !SE.equalsIgnoreCase(id)) images[2] = Main.getTextures().dirtBREHGQ;
						}else if(now.getID() == SandTile.getSID()) {
							images[0] = Main.getTextures().sandBLCGQ;
							images[1] = Main.getTextures().sandBLRGQ;
							images[2] = Main.getTextures().sandBLCGQ;
							if (!le && !NW.equalsIgnoreCase(id)) images[0] = Main.getTextures().sandTLEVGQ;
							if (!be && !SE.equalsIgnoreCase(id)) images[2] = Main.getTextures().sandBREHGQ;
						}
					} else if (!br) {
						if(now.getID() == DirtTile.getSID()) {
							images[1] = Main.getTextures().dirtBRCGQ;
							images[2] = Main.getTextures().dirtBRRGQ;
							images[3] = Main.getTextures().dirtBRCGQ;
							if (!re && !NE.equalsIgnoreCase(id)) images[3] = Main.getTextures().dirtTREVGQ;
							if (!be && !SW.equalsIgnoreCase(id)) images[1] = Main.getTextures().dirtBLEHGQ;
						}else if(now.getID() == SandTile.getSID()) {
							images[1] = Main.getTextures().sandBRCGQ;
							images[2] = Main.getTextures().sandBRRGQ;
							images[3] = Main.getTextures().sandBRCGQ;
							if (!re && !NE.equalsIgnoreCase(id)) images[3] = Main.getTextures().sandTREVGQ;
							if (!be && !SW.equalsIgnoreCase(id)) images[1] = Main.getTextures().sandBLEHGQ;
						}
					} else if (!tr) {
						if(now.getID() == DirtTile.getSID()) {
							images[0] = Main.getTextures().dirtTRCGQ;
							images[2] = Main.getTextures().dirtTRCGQ;
							images[3] = Main.getTextures().dirtTRRGQ;
							if (!te && !NW.equalsIgnoreCase(id)) images[0] = Main.getTextures().dirtTLEHGQ;
							if (!re && !SE.equalsIgnoreCase(id)) images[2] = Main.getTextures().dirtBREVGQ;
						}else if(now.getID() == SandTile.getSID()) {
							images[0] = Main.getTextures().sandTRCGQ;
							images[2] = Main.getTextures().sandTRCGQ;
							images[3] = Main.getTextures().sandTRRGQ;
							if (!te && !NW.equalsIgnoreCase(id)) images[0] = Main.getTextures().sandTLEHGQ;
							if (!re && !SE.equalsIgnoreCase(id)) images[2] = Main.getTextures().sandBREVGQ;
						}
					} else if (!te && !be) {
						if(now.getID() == DirtTile.getSID()) {
							images[0] = Main.getTextures().dirtTRCGQ;
							images[1] = Main.getTextures().dirtBRCGQ;
							images[2] = Main.getTextures().dirtBLCGQ;
							images[3] = Main.getTextures().dirtTLCGQ;
							if (!NW.equalsIgnoreCase(id)) images[0] = Main.getTextures().dirtTLEHGQ;
							if (!SW.equalsIgnoreCase(id)) images[1] = Main.getTextures().dirtBLEHGQ;
							if (!SE.equalsIgnoreCase(id)) images[2] = Main.getTextures().dirtBREHGQ;
							if (!NE.equalsIgnoreCase(id)) images[3] = Main.getTextures().dirtTREHGQ;
						}else if(now.getID() == SandTile.getSID()) {
							images[0] = Main.getTextures().sandTRCGQ;
							images[1] = Main.getTextures().sandBRCGQ;
							images[2] = Main.getTextures().sandBLCGQ;
							images[3] = Main.getTextures().sandTLCGQ;
							if (!NW.equalsIgnoreCase(id)) images[0] = Main.getTextures().sandTLEHGQ;
							if (!SW.equalsIgnoreCase(id)) images[1] = Main.getTextures().sandBLEHGQ;
							if (!SE.equalsIgnoreCase(id)) images[2] = Main.getTextures().sandBREHGQ;
							if (!NE.equalsIgnoreCase(id)) images[3] = Main.getTextures().sandTREHGQ;
						}
					} else if (!le && !re) {
						if(now.getID() == DirtTile.getSID()) {
							images[0] = Main.getTextures().dirtBLCGQ;
							images[1] = Main.getTextures().dirtTLCGQ;
							images[2] = Main.getTextures().dirtTRCGQ;
							images[3] = Main.getTextures().dirtBRCGQ;
							if (!NW.equalsIgnoreCase(id)) images[0] = Main.getTextures().dirtTLEVGQ;
							if (!SW.equalsIgnoreCase(id)) images[1] = Main.getTextures().dirtBLEVGQ;
							if (!SE.equalsIgnoreCase(id)) images[2] = Main.getTextures().dirtBREVGQ;
							if (!NE.equalsIgnoreCase(id)) images[3] = Main.getTextures().dirtTREVGQ;
						}else if(now.getID() == SandTile.getSID()) {
							images[0] = Main.getTextures().sandBLCGQ;
							images[1] = Main.getTextures().sandTLCGQ;
							images[2] = Main.getTextures().sandTRCGQ;
							images[3] = Main.getTextures().sandBRCGQ;
							if (!NW.equalsIgnoreCase(id)) images[0] = Main.getTextures().sandTLEVGQ;
							if (!SW.equalsIgnoreCase(id)) images[1] = Main.getTextures().sandBLEVGQ;
							if (!SE.equalsIgnoreCase(id)) images[2] = Main.getTextures().sandBREVGQ;
							if (!NE.equalsIgnoreCase(id)) images[3] = Main.getTextures().sandTREVGQ;
						}
					} else if (!te) {
						if(now.getID() == DirtTile.getSID()) {
							images[0] = Main.getTextures().dirtTRCGQ;
							images[3] = Main.getTextures().dirtTLCGQ;
							if (!NW.equalsIgnoreCase(id)) images[0] = Main.getTextures().dirtTLEHGQ;
							if (!NE.equalsIgnoreCase(id)) images[3] = Main.getTextures().dirtTREHGQ;
						}else if(now.getID() == SandTile.getSID()) {
							images[0] = Main.getTextures().sandTRCGQ;
							images[3] = Main.getTextures().sandTLCGQ;
							if (!NW.equalsIgnoreCase(id)) images[0] = Main.getTextures().sandTLEHGQ;
							if (!NE.equalsIgnoreCase(id)) images[3] = Main.getTextures().sandTREHGQ;
						}
					} else if (!le) {
						if(now.getID() == DirtTile.getSID()) {
							images[0] = Main.getTextures().dirtBLCGQ;
							images[1] = Main.getTextures().dirtTLCGQ;
							if (!NW.equalsIgnoreCase(id)) images[0] = Main.getTextures().dirtTLEVGQ;
							if (!SW.equalsIgnoreCase(id)) images[1] = Main.getTextures().dirtBLEVGQ;
						}else if(now.getID() == SandTile.getSID()) {
							images[0] = Main.getTextures().sandBLCGQ;
							images[1] = Main.getTextures().sandTLCGQ;
							if (!NW.equalsIgnoreCase(id)) images[0] = Main.getTextures().sandTLEVGQ;
							if (!SW.equalsIgnoreCase(id)) images[1] = Main.getTextures().sandBLEVGQ;
						}
					} else if (!be) {
						if(now.getID() == DirtTile.getSID()) {
							images[1] = Main.getTextures().dirtBRCGQ;
							images[2] = Main.getTextures().dirtBLCGQ;
							if (!SW.equalsIgnoreCase(id)) images[1] = Main.getTextures().dirtBLEHGQ;
							if (!SE.equalsIgnoreCase(id)) images[2] = Main.getTextures().dirtBREHGQ;
						}else if(now.getID() == SandTile.getSID()) {
							images[1] = Main.getTextures().sandBRCGQ;
							images[2] = Main.getTextures().sandBLCGQ;
							if (!SW.equalsIgnoreCase(id)) images[1] = Main.getTextures().sandBLEHGQ;
							if (!SE.equalsIgnoreCase(id)) images[2] = Main.getTextures().sandBREHGQ;
						}
					} else if (!re) {
						if(now.getID() == DirtTile.getSID()) {
							images[2] = Main.getTextures().dirtTRCGQ;
							images[3] = Main.getTextures().dirtBRCGQ;
							if (!SE.equalsIgnoreCase(id)) images[2] = Main.getTextures().dirtBREVGQ;
							if (!NE.equalsIgnoreCase(id)) images[3] = Main.getTextures().dirtTREVGQ;
						}else if(now.getID() == SandTile.getSID()) {
							images[2] = Main.getTextures().sandTRCGQ;
							images[3] = Main.getTextures().sandBRCGQ;
							if (!SE.equalsIgnoreCase(id)) images[2] = Main.getTextures().sandBREVGQ;
							if (!NE.equalsIgnoreCase(id)) images[3] = Main.getTextures().sandTREVGQ;
						}
					}
					now.setTexture(images);
				//} else if (now.getDominance() == 2) {
					if (!tl && !bl && !br && !tr) now.setTexture(Main.getTextures().grassTLRDQ, Main.getTextures().grassBLRDQ, Main.getTextures().grassBRRDQ, Main.getTextures().grassTRRDQ);
					else if (!tl && !bl && !br) now.setTexture(Main.getTextures().grassTLRDQ, Main.getTextures().grassBLRDQ, Main.getTextures().grassBRRDQ, Main.getTextures().grassTRQ);
					else if (!bl && !br && !tr) now.setTexture(Main.getTextures().grassTLQ, Main.getTextures().grassBLRDQ, Main.getTextures().grassBRRDQ, Main.getTextures().grassTRRDQ);
					else if (!br && !tr && !tl) now.setTexture(Main.getTextures().grassTLRDQ, Main.getTextures().grassBLQ, Main.getTextures().grassBRRDQ, Main.getTextures().grassTRRDQ);
					else if (!tr && !tl && !bl) now.setTexture(Main.getTextures().grassTLRDQ, Main.getTextures().grassBLRDQ, Main.getTextures().grassBRQ, Main.getTextures().grassTRRDQ);
					else if (!tr && !bl) now.setTexture(Main.getTextures().grassTLQ, Main.getTextures().grassBLRDQ, Main.getTextures().grassBRQ, Main.getTextures().grassTRRDQ);
					else if (!tl && !br) now.setTexture(Main.getTextures().grassTLRDQ, Main.getTextures().grassBLQ, Main.getTextures().grassBRRDQ, Main.getTextures().grassTRQ);
					else if (!tl && !bl) now.setTexture(Main.getTextures().grassTLRDQ, Main.getTextures().grassBLRDQ, Main.getTextures().grassBRQ, Main.getTextures().grassTRQ);
					else if (!bl && !br) now.setTexture(Main.getTextures().grassTLQ, Main.getTextures().grassBLRDQ, Main.getTextures().grassBRRDQ, Main.getTextures().grassTRQ);
					else if (!br && !tr) now.setTexture(Main.getTextures().grassTLQ, Main.getTextures().grassBLQ, Main.getTextures().grassBRRDQ, Main.getTextures().grassTRRDQ);
					else if (!tr && !tl) now.setTexture(Main.getTextures().grassTLRDQ, Main.getTextures().grassBLQ, Main.getTextures().grassBRQ, Main.getTextures().grassTRRDQ);
					else if (!tl) now.setTexture(Main.getTextures().grassTLRDQ, Main.getTextures().grassBLQ, Main.getTextures().grassBRQ, Main.getTextures().grassTRQ);
					else if (!bl) now.setTexture(Main.getTextures().grassTLQ, Main.getTextures().grassBLRDQ, Main.getTextures().grassBRQ, Main.getTextures().grassTRQ);
					else if (!br) now.setTexture(Main.getTextures().grassTLQ, Main.getTextures().grassBLQ, Main.getTextures().grassBRRDQ, Main.getTextures().grassTRQ);
					else if (!tr) now.setTexture(Main.getTextures().grassTLQ, Main.getTextures().grassBLQ, Main.getTextures().grassBRQ, Main.getTextures().grassTRRDQ);
					else //now.setTexture(Main.getTextures().grassTLQ, Main.getTextures().grassBLQ, Main.getTextures().grassBRQ, Main.getTextures().grassTRQ);
				//}
			}
		}
	}*/

	/**
	 * Returns a string of numbers and letters representing the tiles surrounding
	 * the target tile, starting on the top left corner, and proceeding
	 * counter-clockwise.
	 **/
	/*public static String getSurroundingTiles(int x, int y) {
		String s = "";
		// Top left corner
		if (x - 1 >= 0 && y - 1 >= 0) {
			s += tiles[x - 1][y - 1].getID() + ",";
		} else
			s += "n,";
		// Left side
		if (x - 1 >= 0) {
			s += tiles[x - 1][y].getID() + ",";
		} else
			s += "n,";
		// Bottom left corner
		if (x - 1 >= 0 && y + 1 < tiles[0].length) {
			s += tiles[x - 1][y + 1].getID() + ",";
		} else
			s += "n,";
		// Bottom side
		if (y + 1 < tiles[0].length) {
			s += tiles[x][y + 1].getID() + ",";
		} else
			s += "n,";
		// Bottom right corner
		if (x + 1 < tiles.length && y + 1 < tiles[0].length) {
			s += tiles[x + 1][y + 1].getID() + ",";
		} else
			s += "n,";
		// Right side
		if (x + 1 < tiles.length) {
			s += tiles[x + 1][y].getID() + ",";
		} else
			s += "n,";
		// Top right corner
		if (x + 1 < tiles.length && y - 1 >= 0) {
			s += tiles[x + 1][y - 1].getID() + ",";
		} else
			s += "n,";
		// Top side
		if (y - 1 >= 0) {
			s += tiles[x][y - 1].getID()+",";
		} else
			s += "n,";
		return s;
	}
	public static List<Entity> getSurroundingObjects(Location location, int radius){
		List<Entity> entities = new ArrayList<Entity>();
		//int tilesSearched = 0;
		int locx = (int) location.getX();
		int locy = (int) location.getY();
		int tlx = locx-radius;
		int tly = locy-radius;
		int brx = locx+radius;
		int bry = locy+radius;
		if(tlx < 0) {
			tlx = 0;
		}
		if(tly < 0) {
			tly = 0;
		}
		if(brx > tiles.length) {
			brx = tiles.length;
		}
		if(bry > tiles[0].length) {
			bry = tiles[0].length;
		}
		
		for(int y = tly; y < bry; y++) {
			for(int x = tlx; x < brx; x++) {
				//tilesSearched++;
				for(Entity each:tiles[x][y].getObjects()) {
					if(each.getLocation().distance(location) <= radius) {
						entities.add(each);
					}
				}
			}
		}
		//System.out.println("Tiles searched: "+tilesSearched);
		//System.out.println("Entities found in radius "+radius+": "+entities.size());
		return entities;
	}
	public static List<Entity> getSurroundingObjects(int x, int y) {
		List<Entity> entities = new ArrayList<Entity>();
		//self
		if(x >= 0 && y >= 0 && x < tiles.length && y < tiles[0].length) {
			for(Entity each:tiles[x][y].getObjects()) {
				entities.add(each);
			}
		}
		// Top left corner
		if (x - 1 >= 0 && y - 1 >= 0 && x-1 < tiles.length && y-1 < tiles[0].length) {
			for(Entity each:tiles[x - 1][y - 1].getObjects()) {
				entities.add(each);
			}
		}
		// Left side
		if (x - 1 >= 0 && y >= 0 && x-1 < tiles.length && y < tiles[0].length) {
			for(Entity each:tiles[x - 1][y].getObjects()) {
				entities.add(each);
			}
		}
		// Bottom left corner
		if (x - 1 >= 0 && y+1 >= 0&& y + 1 < tiles[0].length && x-1< tiles.length && y+1 < tiles[0].length) {
			for(Entity each:tiles[x - 1][y + 1].getObjects()) {
				entities.add(each);
			}
		}
		// Bottom side
		if (x >= 0 && y+1 >= 0 && y+1 < tiles[0].length && x < tiles.length) {
			for(Entity each:tiles[x][y + 1].getObjects()) {
				entities.add(each);
			}
		}
		// Bottom right corner
		if (x+1 >= 0 && y+1 >= 0 && x+1 < tiles.length && y+1 < tiles[0].length) {
			for(Entity each:tiles[x + 1][y + 1].getObjects()) {
				entities.add(each);
			}
		}
		// Right side
		if (x+1 >= 0 && y >= 0 && x+1 < tiles.length && y < tiles[0].length) {
			for(Entity each:tiles[x + 1][y].getObjects()) {
				entities.add(each);
			}
		}
		// Top right corner
		if (x + 1 >= 0 && y - 1 >= 0 && x+1 < tiles.length && y-1 < tiles[0].length) {
			for(Entity each:tiles[x + 1][y - 1].getObjects()) {
				entities.add(each);
			}
		}
		// Top side
		if (x >= 0 && y - 1 >= 0 && x < tiles.length && y-1 < tiles[0].length) {
			for(Entity each:tiles[x][y - 1].getObjects()) {
				entities.add(each);
			}
		}
		return entities;
	}*/

	public static byte[][] getTiles() {
		return tiles;
	}

	public int getPixelWidth() {
		return pixelWidth;
	}

	public int getPixelHeight() {
		return pixelHeight;
	}
}