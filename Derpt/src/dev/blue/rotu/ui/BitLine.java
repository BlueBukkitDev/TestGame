package dev.blue.rotu.ui;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class BitLine {
	private List<TextBit> bits = new ArrayList<TextBit>();
	private int x, y;
	private int index;
	@SuppressWarnings("unused")
	private int width, height, maxWidth;//Use MaxWidth to make multiple bitlines
	
	public BitLine(int maxWidth) {
		this.maxWidth = maxWidth;
		index = x;
	}
	
	public void addBit(TextBit bit) {
		if(bit != null) {
			if(bit.getS().length() > 0) {
				bits.add(bit);
				int i = 0;
				for(TextBit each:bits) {
					int h = each.getHeight();
					if(h > i) {
						i = h;
					}
				}
				if(i > height) {
					this.height = i;
				}
			}
		}
	}
	
	public void render(Graphics g) {
		index = x;
		for(TextBit each:bits) {
			if(each.isContainer()) {
				for(TextBit every:each.getBits()) {
					g.setColor(every.getC());
					g.setFont(every.getF());
					g.drawString(every.getS(), index, y);//Does this render from the top left? If so might cause a rendering problem with placement. 
					index += g.getFontMetrics().stringWidth(every.getS());
				}
			}else {
				g.setColor(each.getC());
				g.setFont(each.getF());
				g.drawString(each.getS(), index, y);//Does this render from the top left? If so might cause a rendering problem with placement. 
				index += g.getFontMetrics().stringWidth(each.getS());
			}
		}
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
	
	public int getHeight() {
		return height;
	}
	
	
}
