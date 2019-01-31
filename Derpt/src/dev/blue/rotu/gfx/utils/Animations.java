package dev.blue.rotu.gfx.utils;

import java.awt.image.BufferedImage;

public class Animations {
	public BufferedImage[] wellnessBarShrink;
	
	public void loadAnimations() {
		wellnessBarShrink = createAnimation("ui/animations/wellnessBarCollapse.png", 20);
	}
	
	private BufferedImage[] createAnimation(String path, int frames) {
		Spritesheet sheet = new Spritesheet(ImageLoader.loadImage(path), 128, 16);
		BufferedImage[] array = new BufferedImage[frames+1];
		for(int i = 1; i < frames+1; i++) {
			array[i] = sheet.getSprite(1, i);
		}
		return array;
	}
}
