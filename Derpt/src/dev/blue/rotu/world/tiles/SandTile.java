package dev.blue.rotu.world.tiles;

import dev.blue.rotu.Main;

public class SandTile extends Tile {
	private static byte id = 2;

	public SandTile() {
		super("Sand", false, (byte)1);
		texture = Main.getTextures().sandTile;
	}
	
	public byte getID() {
		return id;
	}
	
	public static byte getSID() {
		return id;
	}
}