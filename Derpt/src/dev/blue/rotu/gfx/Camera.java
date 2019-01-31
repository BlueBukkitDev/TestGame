package dev.blue.rotu.gfx;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import dev.blue.rotu.Game;
import dev.blue.rotu.Main;
import dev.blue.rotu.Window;
import dev.blue.rotu.world.Location;
import dev.blue.rotu.world.World;
import dev.blue.rotu.world.tiles.Tile;

public class Camera {
	private World world;
	private Point focus, destination;
	private boolean built = false;
	private boolean panning = false;
	protected short zoom = 0;
	private ViewField viewField;
	private int initialPanBorder = 3;
	private int panBorder = 3;
	private int xOffset = 0, yOffset = 0;
	private int correctedXOffset = 0, correctedYOffset = 0;
	
	public void build(World world, Point focus) {
		this.world = world;
		this.focus = focus;
		adjustFocusForEdges();
		viewField = new ViewField(this);
		
		built = true;
		Game.getInstance().setPanSpeed(12);//6
	}
	//moveX and moveY are for panning purposes only. 
	public double moveX() {
		Point p = Game.getInstance().getMouseManager().point;
		if(panning) {
			if(panBorder == initialPanBorder) {
				panBorder *= 22;
			}
		}else panBorder = initialPanBorder;
		if(p.getX() >= Window.width-panBorder) {
			//System.out.println("Panning Right");
			panning = true;
			return Game.getInstance().getPanSpeed();
		}else if(p.getX() <= panBorder) {
			//System.out.println("Panning Left");
			panning = true;
			return Game.getInstance().getPanSpeed()*-1;
		}else if(p.getY() >= Window.height-panBorder) {
			//System.out.println("Panning Down");
			panning = true;
			return Game.getInstance().getPanSpeed()/(Window.width/2/(p.getX()-Window.width/2));
		}else if(p.getY() <= panBorder) {
			//System.out.println("Panning Up");
			panning = true;
			return (Game.getInstance().getPanSpeed())/(Window.width/2/(p.getX()-Window.width/2));
		}
		panning = false;
		return 0;
	}
	
	public double moveY() {
		Point p = Game.getInstance().getMouseManager().point;
		if(panning) {
			if(panBorder == initialPanBorder) {
				panBorder *= 22;
			}
		}else panBorder = initialPanBorder;
		if(p.getY() >= Window.height-panBorder) {
			//System.out.println("Panning Down");
			panning = true;
			return Game.getInstance().getPanSpeed();
		}else if(p.getY() <= panBorder) {
			//System.out.println("Panning Up");
			panning = true;
			return Game.getInstance().getPanSpeed()*-1;
		}else if(p.getX() >= Window.width-panBorder) {
			//System.out.println("Panning Right");
			panning = true;
			return Game.getInstance().getPanSpeed()/(Window.height/2/(p.getY()-Window.height/2));
		}else if(p.getX() <= panBorder) {
			//System.out.println("Panning Left");
			panning = true;
			return (Game.getInstance().getPanSpeed())/(Window.height/2/(p.getY()-Window.height/2));
		}
		panning = false;
		return 0;
	}
	
	public Tile getFocusTile() {
		return Tile.getTile(World.getTiles()[(int) Math.ceil(focus.getX()/40)][(int) Math.ceil(focus.getY()/40)]);
	}
	
	public Tile getTileAt(Point p) {
		return Tile.getTile(World.getTiles()[(int)(Math.ceil((p.getX()+xOffset)/Tile.getWidth())+getViewField().getTilesFromLeft())-1][(int)(Math.ceil((p.getY()+yOffset)/Tile.getWidth())+getViewField().getTilesFromTop())-1]);
	}
	
	public Location getTileLocationAt(Point p) {
		return new Location((int)(Math.ceil((p.getX()+xOffset)/Tile.getWidth())+getViewField().getTilesFromLeft())-1, (int)(Math.ceil((p.getY()+yOffset)/Tile.getWidth())+getViewField().getTilesFromTop())-1);
	}
	
	public void render(Graphics g) {
		if(built) {
			//Game.data = "(xOffset: "+xOffset+"|yOffset: "+yOffset+"|Focus: "+focus.x+","+focus.y+"|Tile width: "+Tile.getWidth()+")";
			int renderLastX = ViewField.width;
			int renderLastY = ViewField.height;
			boolean doingRenderLast = false;
			for(int y = 0; y < ViewField.height; y++) {
				for(int x = 0; x < ViewField.width; x++) {
					/*g.drawImage(viewField.getView()[x][y].getTexture()[0], x*Tile.getWidth()-xOffset, y*Tile.getWidth()-yOffset, Tile.getWidth()/2, Tile.getWidth()/2, null);
					g.drawImage(viewField.getView()[x][y].getTexture()[1], x*Tile.getWidth()-xOffset, y*Tile.getWidth()-yOffset+(Tile.getWidth()/2), Tile.getWidth()/2, Tile.getWidth()/2, null);
					g.drawImage(viewField.getView()[x][y].getTexture()[2], x*Tile.getWidth()-xOffset+(Tile.getWidth()/2), y*Tile.getWidth()-yOffset+(Tile.getWidth()/2), Tile.getWidth()/2, Tile.getWidth()/2, null);
					g.drawImage(viewField.getView()[x][y].getTexture()[3], x*Tile.getWidth()-xOffset+(Tile.getWidth()/2), y*Tile.getWidth()-yOffset, Tile.getWidth()/2, Tile.getWidth()/2, null);*/
					g.drawImage(viewField.getView()[x][y].getTexture(), x*Tile.getWidth()-xOffset+correctedXOffset, y*Tile.getWidth()-yOffset+correctedYOffset, Tile.getWidth(), Tile.getWidth(), null);
					if(viewField.getView()[x][y].isBeingInspected()) {
						renderLastX = x; renderLastY = y;
						doingRenderLast = true;
					}
				}
			}
			if(doingRenderLast) {
				g.drawImage(Main.getTextures().tileGlow, renderLastX*Tile.getWidth()-xOffset-(Tile.getWidth()/9), renderLastY*Tile.getWidth()-yOffset-(Tile.getWidth()/9), 
				Tile.getWidth()+(Tile.getWidth()/9)*2, Tile.getWidth()+(Tile.getWidth()/9)*2, null);
			}
			g.setColor(Color.WHITE);
			g.fillOval((int)(focus.getX()-2-(Tile.getWidth()*getViewField().getTilesFromLeft())-xOffset), (int)(focus.getY()-2-(Tile.getWidth()*getViewField().getTilesFromTop())-yOffset), 4, 4);
			g.setColor(Color.RED);
			g.fillOval(Window.width/2-2, Window.height/2-2, 4, 4);
			//int space = (Window.width/2-2) - (int)(focus.getX()-2-(Tile.getWidth()*getViewField().getTilesFromLeft())-xOffset);
			//System.out.println(space+"!="+xOffset);
			//System.out.println("space - TileWidth = "+(Tile.getWidth()-space));
			//System.out.println("Tile Width: "+Tile.getWidth());
			//System.out.println("xOffset = space: "+((Window.width/2-2) - (int)(focus.getX()-2-(Tile.getWidth()*getViewField().getTilesFromLeft())-xOffset) == xOffset));
			/*for(int y = 0; y < ViewField.height; y++) {
				for(int x = 0; x < ViewField.width; x++) {
					for(Entity each:viewField.getView()[x][y].getObjects()) {
						each.render(g);
						Game.renderedObjects++;
					}
				}
			}*/
			//Divide the camera focus by 40 to find how off from the tile the focus is, and use that to render partial tiles by offsetting each edge tile based on that remainder. 
			//System.out.println("Rendering camera O.o ");
		}
	}
	
	public void update() {
		if(built) {
			Point last = focus;
			focus = new Point((int)(focus.x+moveX()), (int)(focus.y+moveY()));
			adjustFocusForEdges();
			xOffset = focus.x % (Tile.getWidth());
			if(xOffset == 0) {
				//xOffset = Tile.getWidth();
			}
			yOffset = focus.y % (Tile.getWidth());
			if(yOffset == 0) {
				//yOffset = Tile.getWidth();
			}
			correctedXOffset = Window.width/2 - (int)(focus.getX()-(Tile.getWidth()*getViewField().getTilesFromLeft())-xOffset);
			correctedYOffset = Window.height/2 - (int)(focus.getY()-(Tile.getWidth()*getViewField().getTilesFromTop())-yOffset);
			//System.out.println("focus.x = "+focus.x+"\nmoveX() = "+moveX()+"\ndistance = "+focus.distance(last));
			if(focus.distance(last) > 0.5) {
				//System.out.println("Updating ViewField!");
				viewField.update(focus);
			}
		}
	}
	
	public void adjustFocusForEdges() {//Should work? Maybe? Verify before testing. 
		//int x = (int) (tile.getLoc().getX()), y = (int) (tile.getLoc().getY());//If map.getWidth - x > Window.getWidth, continue.//Needs to be in tiles, not pixels. 
		int x = (int) focus.getX();
		int y = (int) focus.getY();//Focus is in the wrong spot. That is why the tiles are all rendered weirdly offset. 
		boolean adjusted = false;
		
		
		//Prevent going too far to the left
		if(x <= Window.width/2) {
			x = (Window.width/2)+1;//+1 is to counter any movement past the allowable range. 
			adjusted = true;
		}else //Prevent going too far to the right
			if(x >= world.getPixelWidth() - Window.width/2) {
				x = (world.getPixelWidth() - Window.width/2)-1;
				adjusted = true;
			}
		//prevent going too far up
		if(y <= Window.height/2) {
			y = (Window.height/2)+1;
			adjusted = true;
		}else //Prevent going too far down
			if(y >= world.getPixelHeight() - Window.height/2) {
				y = (world.getPixelHeight() - Window.height/2)-1;
				adjusted = true;
			}
		
		if(adjusted) {
			focus = new Point(x, y);
		}
	}
	
	public void dispose() {
		this.built = false;
		this.world = null;
		this.focus = null;
		this.destination = null;
	}

	public World getWorld() {
		return world;
	}
	
	public ViewField getViewField() {
		return viewField;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public Point getFocus() {
		return focus;
	}

	public void setFocus(Point focus) {
		this.focus = focus;
	}

	public Point getDestination() {
		return destination;
	}

	public void setDestination(Point destination) {
		this.destination = destination;
	}
	
	public double getXOffset() {
		return xOffset;
	}
	
	public double getYOffset() {
		return yOffset;
	}
	
	public Point topLeft() {
		int x = focus.x;
		int y = focus.y;
		x -= Window.width/2;
		y -= Window.height/2;
		return new Point(x, y);
	}
	
	public Point topRight() {
		int x = focus.x;
		int y = focus.y;
		x += Window.width/2;
		y -= Window.height/2;
		return new Point(x, y);
	}
	
	public Point bottomLeft() {
		int x = focus.x;
		int y = focus.y;
		x -= Window.width/2;
		y += Window.height/2;
		return new Point(x, y);
	}
	
	public Point bottomRight() {
		int x = focus.x;
		int y = focus.y;
		x += Window.width/2;
		y += Window.height/2;
		return new Point(x, y);
	}
}
