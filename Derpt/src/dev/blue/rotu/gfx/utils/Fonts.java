package dev.blue.rotu.gfx.utils;

import java.awt.Font;

public class Fonts {
	public Font plain;
	public Font italic;
	public Font bold;
	
	public void load() {
		plain = new Font("Helvetica", Font.PLAIN, 14);
		italic = new Font("Helvetica", Font.ITALIC, 14);
		bold = new Font("Helvetica", Font.BOLD, 14);
	}
	
	public Font custom(int size, int type) {
		return new Font("Helvetica", type, size);
	}
}
