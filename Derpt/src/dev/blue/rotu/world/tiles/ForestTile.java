package dev.blue.rotu.world.tiles;

import dev.blue.rotu.Main;

public class ForestTile extends Tile {
	private static byte id = 4;

	public ForestTile() {
		super("Forest", false, (byte)2);
		texture = Main.getTextures().forestTile;
	}
	
	public byte getID() {
		return id;
	}
	
	public static byte getSID() {
		return id;
	}
}
