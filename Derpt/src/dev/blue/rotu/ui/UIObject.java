package dev.blue.rotu.ui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public abstract class UIObject {
	protected boolean selected = false, hovering = false;
	protected String id;
	protected Rectangle bounds;
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	
	protected void runClick() {}
	
	protected void runMouseDown() {}
	
	protected void runMouseUp() {}
	
	protected void runOnHover() {}
	
	protected void runOnStopHover() {}
	
	public abstract void render(Graphics g);
	public abstract void update();
	public abstract boolean onClick(int button, Point p);
	public abstract void onType(KeyEvent e);
	public abstract void onKeyPressed(KeyEvent e);
	public abstract void onMouseMove(Point p);
	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
		this.bounds = new Rectangle(x, y, width, height);
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
		this.bounds = new Rectangle(x, y, width, height);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
		this.bounds = new Rectangle(x, y, width, height);
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
		this.bounds = new Rectangle(x, y, width, height);
	}

	public void setHovering(boolean hovering) {
		this.hovering = hovering;
		if(hovering = false) {
			runOnStopHover();
		}else if(hovering = true) {
			runOnHover();
		}
	}
	
	public void stopHovering() {
		this.hovering = false;
		runOnStopHover();
	}
	protected boolean isHovering() {
		return hovering;
	}
	public boolean isSelected() {
		return selected;
	}
}
