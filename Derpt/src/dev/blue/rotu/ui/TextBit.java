package dev.blue.rotu.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import dev.blue.rotu.Game;

public class TextBit {
	private Color c;
	private Font f;
	private String s;
	private int x, y;
	private TextBit[] bits;
	private Runnable hover;
	private Runnable click;
	private boolean isContainer;
	
	/**
	 *<p>
	 *<strong>Color c </strong>(The color of this piece of text)</br>
	 *<strong>Font f </strong>(The font in which this text will be displayed)</br>
	 *<strong>String s </strong>(The String to be displayed)</br>
	 *<strong>int x </strong>(The x coordinate of this String's drawn location)</br>
	 *<strong>int y </strong>(The y coordinate of this String's drawn location)</br>
	 *<strong>Runnable hover </strong>(The Runnable to be executed when the mouse hovers over the text)</br>
	 *<strong>Runnable click </strong>(The Runnable to be executed when the mouse clicks on the text)</br>
	 *</p>
	 **/
	public TextBit(Color c, Font f, String s, Runnable hover, Runnable click) {
		this.c = c;
		this.f = f;
		this.s = s;
		this.hover = hover;
		this.click = click;
		isContainer = false;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public TextBit(TextBit... bits) {
		this.bits = bits;
		isContainer = true;
	}
	
	public void onMouseHover() {
		if(hover != null) {
			hover.run();
		}
	}
	
	public void onMouseClick() {
		if(click != null) {
			click.run();
		}
	}

	public Color getC() {
		return c;
	}

	public void setC(Color c) {
		this.c = c;
	}

	public Font getF() {
		return f;
	}

	public void setF(Font f) {
		this.f = f;
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public Runnable getHover() {
		return hover;
	}

	public void setHover(Runnable hover) {
		this.hover = hover;
	}

	public Runnable getClick() {
		return click;
	}

	public void setClick(Runnable click) {
		this.click = click;
	}
	
	public TextBit[] getBits() {
		List<TextBit> bitsList = new ArrayList<TextBit>();
		for(TextBit each:bits) {
			for(TextBit every:each.getBits()) {
				bitsList.add(every);
			}
		}
		return (TextBit[]) bitsList.toArray();
	}
	
	public boolean isContainer() {
		return isContainer;
	}
	
	public int getHeight() {
		Graphics g = Game.getInstance().getGraphics();
		g.setFont(f);
		return g.getFontMetrics().getHeight();
	}
}
