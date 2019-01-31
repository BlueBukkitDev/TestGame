package dev.blue.rotu.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import dev.blue.rotu.Game;
import dev.blue.rotu.gfx.Animation;

public class Button extends UIObject {
	private Animation whileDown, whileUp;
	private int tooltipTimer = 0;
	private boolean showTooltip = false;
	private boolean useTooltip = false;
	private boolean showingClicked = false;
	private boolean showID;
	private int fontSize;
	
	/**
	 * <p>
	 * <strong>Parameters:</strong></br>
	 * <strong>String id </strong>(an ID by which this button can be taken out of the ButtonManager easily. Not required.)</br>
	 * <strong>boolean showID </strong>(whether to draw the ID of the button on the button)</br>
	 * <strong>int fontSize </strong>(the font size to use if showID is true)</br>
	 * <strong>int x </strong>(the X coordinate of the top left corner of this button's bounding box)</br>
	 * <strong>int y </strong>(the Y coordinate of the top left corner of this button's bounding box)</br>
	 * <strong>int width </strong>(the width of this button's bounding box)</br>
	 * <strong>int height </strong>(the height of this button's bounding box)</br>
	 * <strong>Runnable onClick </strong>(an input of what exactly will occur when onClick() is called from the MouseManager)</br>
	 * <strong>int animationSpeed </strong>(the speed at which the images will be cycled, every update 
	 * incrementing a timer by 1/animationSpeed. If set to 0, no animations occur and the first image is the displayed image.)</br>
	 * <strong>BufferedImage[] images </strong>(the image(s) that the render method
	 * will iterate through, allowing for dynamic textures)</br>
	 * <pre>Button button = new Button("", false, false, 0, 0, 0, 10, 10, 0, down, up);</pre>
	 * </p>
	 **/
	public Button(String id, boolean showID, boolean useTooltip, int fontSize, int x, int y, int width, int height, int animationSpeed, Animation whileDown, Animation whileUp) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.whileDown = whileDown;
		this.whileUp = whileUp;
		this.bounds = new Rectangle(x, y, width, height);
		this.showID = showID;
		this.fontSize = fontSize;
		this.useTooltip = useTooltip;
	}
	@Override
	public void render(Graphics g) {
		animate();
		whileUp.render(g);
		whileDown.render(g);
		//g.drawImage(Main.getTextures().button, x, y, width, height, null);
		if(showID) {
			g.setFont(new Font("Helvetica", Font.BOLD, fontSize));
			g.setColor(Color.BLACK);
			g.drawString(id, x+(width/2-g.getFontMetrics().stringWidth(id)/2), (int)(y+height/2+g.getFontMetrics().getHeight()/3.5));
		}
		if(showTooltip && useTooltip) {
			g.setFont(new Font("Helvetica", Font.PLAIN, 12));
			g.setColor(Color.GRAY);
			g.drawString(id, x, y+g.getFontMetrics().getHeight()*2);
		}
	}
	@Override
	public void update() {
		if(hovering) {
			tooltipTimer++;
		} else {
			if(showingClicked) {
				showingClicked = false;
			}
		}
		if(tooltipTimer >= 50) {
			showTooltip = true;
		}
	}
	
	private void animate() {
		if(showingClicked) {
			if(!whileDown.isRunning()) {
				whileUp.end();
				whileDown.run();
			}
		}else {
			if(!whileUp.isRunning()) {
				whileDown.end();
				whileUp.run();
			}
		}
	}
	
	@Override
	public void onMouseMove(Point p) {
		if (bounds.contains(p)) {
			if(hovering == false) {
				hovering = true;
				runOnHover();
			}
		} else
			if(hovering == true) {
				hovering = false;
				showTooltip = false;
				tooltipTimer = 0;
				runOnStopHover();
			}
	}
	
	@Override
	public boolean onClick(int button, Point p) {
		if(this.bounds.contains(p)) {
			runClick();
			return true;
		}else return false;
	}
	
	public boolean onMouseDown(int button, Point p) {
		if(this.bounds.contains(p)) {
			Game.getInstance().getMouseManager().clickedObject = this;
			runMouseDown();
			showingClicked = true;
			return true;
		}else return false;
	}
	
	public boolean onMouseUp(int button, Point p) {
		if(Game.getInstance().getMouseManager().clickedObject == this) {
			Game.getInstance().getMouseManager().clickedObject = null;
		}
		if(showingClicked) {
			showingClicked = false;
			if(this.bounds.contains(p)) {
				runMouseUp();
				onClick(button, p);
				return true;
			}else return false;
		}else return false;
	}
	
	@Override
	public void onType(KeyEvent e) {
		
	}

	@Override
	public void onKeyPressed(KeyEvent e) {
		
	}

	public Animation getWhileUpAnim() {
		return whileUp;
	}
	public Animation getWhileDownAnim() {
		return whileDown;
	}
	public void setWhileUpAnim(Animation whileUp) {
		this.whileUp = whileUp;
	}
	public void setWhileDownAnim(Animation whileDown) {
		this.whileDown = whileDown;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
