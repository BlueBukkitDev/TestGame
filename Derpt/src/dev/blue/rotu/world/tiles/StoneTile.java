package dev.blue.rotu.world.tiles;

import dev.blue.rotu.Main;

public class StoneTile extends Tile {
	private static byte id = 3;

	public StoneTile() {
		super("Grass", false, (byte)1);
		texture = Main.getTextures().stoneTile;
	}
	
	public byte getID() {
		return id;
	}
	
	public static byte getSID() {
		return id;
	}
}
