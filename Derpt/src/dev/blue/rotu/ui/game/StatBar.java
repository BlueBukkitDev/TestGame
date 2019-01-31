package dev.blue.rotu.ui.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import dev.blue.rotu.Window;
import dev.blue.rotu.gfx.Animation;
import dev.blue.rotu.gfx.utils.Proportions;
import dev.blue.rotu.ui.Button;
import dev.blue.rotu.ui.game.stats.Stat;

public class StatBar extends Button {
	private Stat stat; 
	private int x; 
	private int y; 
	private int width; 
	private int height; 
	private boolean selected; 
	private Animation animExpand; 
	//private Animation animShrink; 
	//private Animation whileUp; 
	//private Animation whileDown; 
	//private boolean expanding, shrinking;
	private Color c; 
	private int filledX, filledY, filledWidth, filledHeight, permaWidth;
	private boolean show;
	
	/**
	 * <p>
	 * <strong>Parameters:</strong></br>
	 * <strong>Stat stat </strong>(The Stat that will be represented by this StatBar)</br>
	 * <strong>int x </strong>(the distance from the left side of the window that the top left corner will be drawn)</br>
	 * <strong>int y </strong>(the distance from the top side of the window that the top left corner will be drawn)</br>
	 * <strong>boolean selected </strong>(whether this StatBar is selected for inspection or for movement)</br>
	 * <strong>boolean small </strong>(whether the StatBar will be the small version)</br>
	 * <strong>Animation symbol </strong>(the symbol on the left that is used to visually identify a Stat)</br>
	 * <strong>Color c </strong>(the color used to fill the value of this StatBar)</br>
	 * </p>
	 **/
	public StatBar(Stat stat, int x, int y, int animationSpeed, Color c, boolean small, Animation whileDown, Animation whileUp, Animation animExpand, Animation animShrink) {
		super(stat.getID(), false, false, 12, x, y, Proportions.STAT_BAR_WIDTH, Proportions.STAT_BAR_WIDTH/8, animationSpeed, whileDown, whileUp);
		this.stat = stat;
		this.x = x;
		this.y = y;
		if(!small) {
			width = animShrink.getWidth();
			height = animShrink.getHeight();
			filledX = x + Proportions.STAT_BAR_WIDTH/8;
			filledY = y+height/4;
			filledWidth = (Proportions.STAT_BAR_WIDTH/8)*7;
			filledHeight = height/2;
		}else {
			width = (int)(animShrink.getWidth());
			height = (int)(animShrink.getHeight());
			filledX = x + Proportions.STAT_BAR_WIDTH_2/8;
			filledY = y+height/4;
			filledWidth = (Proportions.STAT_BAR_WIDTH_2/8)*7;
			filledHeight = height/2;
		}
		permaWidth = filledWidth;
		this.selected = false;
		//this.animShrink = animShrink;
		this.animExpand = animExpand;
		animExpand.run();
		this.c = c;
		this.show = true;
	}
	
	public void render(Graphics g) {
		if(show) {
			g.setColor(c);
			g.fillRect(filledX, filledY, filledWidth, filledHeight);
			animExpand.render(g);
		}
	}
	
	//private void animate() {
		
	//}
	
	@Override
	public boolean onClick(int button, Point p) {
		if(bounds.contains(p)) {
			return true;
		}
		return false;
	}
	
	public void update() {
		double perc = stat.getValue()/stat.getMax();
		filledWidth = (int)(perc*(double)permaWidth);
	}
	
	public void show() {
		this.show = true;
	}
	
	public void hide() {
		this.show = false;
	}
	
	public void shrink() {
		
	}
	
	public void expand() {
		
	}
	
/*	public void swap(StatBar statBar) {
		int x1 = this.x;
		int y1 = this.y;
		int width1 = this.width;
		int height1 = this.height;
		Animation symbol1 = this.whileUp;
		//Animation bar1 = this.bar;
		
		this.x = statBar.x;
		this.y = statBar.y;
		this.width = statBar.width;
		this.height = statBar.height;
		this.symbol.setX(statBar.symbol.getX());
		this.symbol.setY(statBar.symbol.getY());
		//this.bar.setX(statBar.bar.getX());
		//this.bar.setY(statBar.bar.getY());
		
		statBar.setX(x1);
		statBar.setY(y1);
		statBar.setHeight(height1);
		statBar.setWidth(width1);
		//statBar.getBar().setX(bar1.getX());
		//statBar.getBar().setY(bar1.getY());
		statBar.getSymbol().setX(symbol1.getX());
		statBar.getSymbol().setY(symbol1.getY());
	}*/

	public Stat getStat() {
		return stat;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width*Window.space();
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height*Window.space();
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

/*	public Animation getSymbol() {
		return symbol;
	}

	public void setSymbol(Animation symbol) {
		this.symbol = symbol;
	}*/

	//public Animation getBar() {
	//	return bar;
	//}

	//public void setBar(Animation bar) {
	//	this.bar = bar;
	//}
}
