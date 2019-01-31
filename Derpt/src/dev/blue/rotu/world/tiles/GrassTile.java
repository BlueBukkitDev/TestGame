package dev.blue.rotu.world.tiles;

import dev.blue.rotu.Main;

public class GrassTile extends Tile {
	private static byte id = 0;

	public GrassTile() {
		super("Grass", false, (byte)2);
		texture = Main.getTextures().grassTile;
	}
	
	public byte getID() {
		return id;
	}
	
	public static byte getSID() {
		return id;
	}
}
