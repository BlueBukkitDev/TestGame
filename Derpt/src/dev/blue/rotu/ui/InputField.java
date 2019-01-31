package dev.blue.rotu.ui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public abstract class InputField {
	protected String id;
	protected Rectangle bounds;
	protected int x, y, width, height;
	protected BufferedImage[] images;
	protected boolean selected = false, hovering = false;
	
	public InputField(String id, int x, int y, int width, int height, BufferedImage... images) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.images = images;
	}
	
	public abstract void render(Graphics g);
	public abstract void update();
	public abstract boolean onClick(int button, Point p);
	public abstract void onType(KeyEvent e);
	public abstract void onKeyPressed(KeyEvent e);
	public abstract void onMouseMove(Point p);
	public boolean isSelected() {
		return selected;
	}
}
