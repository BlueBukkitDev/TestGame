package dev.blue.rotu.gfx;

import java.awt.Point;

import dev.blue.rotu.Window;
import dev.blue.rotu.world.Location;
import dev.blue.rotu.world.World;
import dev.blue.rotu.world.tiles.Tile;

public class ViewField {
	private Point focus;
	private Tile[][] view;
	public static int width = Window.width/Tile.getWidth()+3;
	public static int height = Window.height/Tile.getWidth()+2;
	public Location topLeft;//Topleft shouldn't be a tile location.//Glitches every time to cross a tile border.//When left side of screen reaches Right side of tile, viewfield grabs from the previous tile to the left
	private byte[][] allTiles;
	private double tilesFromLeft, tilesFromTop;
	//private Tile focusTile;

	public ViewField(Camera camera) {
		this.focus = camera.getFocus();
		allTiles = World.getTiles();
		view = new Tile[width][height];
		calculateTopLeft();
		update(focus);
		//Might throw an error because the ViewField isn't built and the Camera might not have a tile picked yet.  
	}
	
	public void update(Point focus) {
		this.focus = focus;
		calculateTopLeft();
		//if(focusTile == null || focusTile != getFocusTile()) {
		buildView();
		//}
	}
	
	public void calculateTopLeft() {
		topLeft = new Location((int)(Math.ceil(focus.getX()/Tile.getWidth())-ViewField.width/2)-1, (int)(Math.ceil(focus.getY()/Tile.getWidth())-ViewField.height/2)-1);//Verified math
		if(((int)(Math.ceil(focus.getX()/Tile.getWidth())-ViewField.width/2)-1) < 0) {
			topLeft.setX(0);
		}
		if(((int)(Math.ceil(focus.getY()/Tile.getWidth())-ViewField.height/2)-1) < 0) {
			topLeft.setY(0);
		}
	}

	public Tile[][] getView() {
		return this.view;
	}
	
	public Tile getFocusTile() {
		return Tile.getTile(World.getTiles()[(int) Math.ceil(focus.getX()/40)][(int) Math.ceil(focus.getY()/40)]);
	}
	
	public void setFocus(Point p) {
		this.focus = p;
	}

	public double getTilesFromLeft() {
		return tilesFromLeft;
	}

	public double getTilesFromTop() {
		return tilesFromTop;
	}
	
	public boolean contains(Location location) {
		return (location.getX() >= topLeft.getX() && location.getX() < topLeft.getX()+width && location.getY() >= topLeft.getY() && location.getY() < topLeft.getY()+height);
	}
	
	/*private void buildViewg() {
		for(int y = (int)topLeft.getY(), i = 0; i < width; i++, y++) {
			view[i] = Arrays.copyOfRange(allTiles[y], (int)topLeft.getX(), (int)topLeft.getX()+height);
		}
		tilesFromLeft = topLeft.getX();
		tilesFromTop = topLeft.getY();
	}*/

	private void buildView() {
		//this.focusTile = getFocusTile();
		int newX = 0, newY = 0;
		for(int y = (int) topLeft.getY(); newY < height && y < allTiles[0].length; y++, newY++) {
			for(int x = (int) topLeft.getX(); newX < width && x < allTiles.length; x++, newX++) {//do we need to add half the screen or otherwise offset tiles?
				view[newX][newY] = Tile.getTile(allTiles[x][y]);//?? Is this gonna work? //aiobe //38? Instead of 26? //Got an aiobe at 200... width of map. 
			}
			newX = 0;
		}
		tilesFromLeft = topLeft.getX();
		tilesFromTop = topLeft.getY();
		//1024, 768);//How many tiles wide and high? //26 wide, 20 high //ended up using a math function
		//if I use an [][] of integers instead  of tiles, it'll be cheaper and easier to iterate through and find my renderable array of tiles. Then I can change it to a tile[][] and use that. 
		//Wrong, because then I'd be creating a new array every time the player moved. Sucky idea. #3:00AMideas
	}
}
//The issue is that regardless of where the focus is located, the viewfield always grabs the same tiles. //Fixed 12/16/2018