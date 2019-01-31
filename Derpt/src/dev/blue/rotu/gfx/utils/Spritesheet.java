package dev.blue.rotu.gfx.utils;

import java.awt.image.BufferedImage;

public class Spritesheet {
	
	private BufferedImage sheet;
	int spriteWidth, spriteHeight;
	
	public Spritesheet(BufferedImage sheet, int spriteWidth, int spriteHeight) {
		this.sheet = sheet;
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
	}
	
	public BufferedImage getSprite(int xIndex, int yIndex) {
		return this.sheet.getSubimage((xIndex-1)*spriteWidth, (yIndex-1)*spriteHeight, spriteWidth, spriteHeight);
	}

	public BufferedImage getSheet() {
		return sheet;
	}
	public void setSheet(BufferedImage sheet) {
		this.sheet = sheet;
	}
	public int getSpriteWidth() {
		return spriteWidth;
	}
	public void setSpriteWidth(int spriteWidth) {
		this.spriteWidth = spriteWidth;
	}
	public int getSpriteHeight() {
		return spriteHeight;
	}
	public void setSpriteHeight(int spriteHeight) {
		this.spriteHeight = spriteHeight;
	}
}
