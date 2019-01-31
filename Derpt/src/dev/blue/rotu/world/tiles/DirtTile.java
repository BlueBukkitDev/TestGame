package dev.blue.rotu.world.tiles;

import dev.blue.rotu.Main;

public class DirtTile extends Tile{
	private static byte id = 1;

	public DirtTile() {
		super("Dirt", false, (byte)1);
		texture = Main.getTextures().dirtTile;
	}
	
	public byte getID() {
		return id;
	}
	
	public static byte getSID() {
		return id;
	}
}
