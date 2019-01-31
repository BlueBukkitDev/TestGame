package dev.blue.rotu.gfx;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.blue.rotu.world.Location;

public class Animation {
	private BufferedImage[] images;
	private boolean isRunning = false;
	private BufferedImage frame;
	private boolean terminated;
	private Location loc;
	private int width, height, inc, counter, index;
	
	/**
	 * <p>
	 * <strong>Parameters:</strong></br>
	 * <strong>int increment </strong>(The number of ticks between frames)</br>
	 * <strong>int x </strong>(the X coordinate of the top left corner of this animation's bounding box)</br>
	 * <strong>int y </strong>(the Y coordinate of the top left corner of this animation's bounding box)</br>
	 * <strong>int width </strong>(the width of this animation's bounding box)</br>
	 * <strong>int height </strong>(the height of this animation's bounding box)</br>
	 * <strong>BufferedImage[] images </strong>(the image(s) that the render method
	 * will iterate through, allowing for dynamic textures)</br>
	 * </p>
	 **/
	public Animation(int inc, Location loc, int width, int height, BufferedImage... images) {
		this.images = images; 
		this.loc = loc;
		this.width = width; 
		this.height = height; 
		this.inc = inc; 
		counter = 0; 
		index = 0; 
		frame = images[index]; 
		terminated = false; 
	}
	
	public BufferedImage[] getImages() {
		return images;
	}
	
	public BufferedImage getFrame(int index) {
		return images[index];
	}
	
	public void render(Graphics g) {
		onRender();
		if(isRunning) {
			g.drawImage(frame, (int)loc.getX(), (int)loc.getY(), width, height, null);
		}
	}
	
	public void update() {
		frame = images[index];
		counter++;
		if(counter > inc) {
			counter = 0;
			index++;
			if(index >= images.length) {
				if(terminated) {
					cancel();
					onEnd();
				}else index = 0;
			}
		}
	}
	
	public void onRender() {}
	
	public void run() {
		isRunning = true;
		terminated = false;
	}
	
	public void end() {
		terminated = true;
	}
	
	public void cancel() {
		isRunning = false;
	}
	
	public void onEnd() {}
	
	public double getX() {
		return loc.getX();
	}
	
	public void setX(double x) {
		this.loc.setX(x);
	}
	
	public double getY() {
		return loc.getY();
	}
	
	public void setY(double y) {
		this.loc.setY(y);
	}
	
	public Location getLocation() {
		return this.loc;
	}
	
	public void setLocation(Location loc) {
		this.loc = loc;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public boolean isRunning() {
		return isRunning;
	}
}
