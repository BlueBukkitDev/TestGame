package dev.blue.rotu.managers;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import dev.blue.rotu.Game;
import dev.blue.rotu.Main;
import dev.blue.rotu.ui.Button;
import dev.blue.rotu.ui.InputField;
import dev.blue.rotu.ui.TextArea;
import dev.blue.rotu.world.Location;
import dev.blue.rotu.world.tiles.Tile;

public class MouseManager implements MouseListener, MouseMotionListener {
	
	public Point point = new Point(0, 0);
	//private Point lastPoint = point;
	private BufferedImage cursor = Main.getTextures().standardCursor;
	public Object cursorController = null;
	public boolean isInFrame = false;
	public Object clickedObject = null;
	
	public void render(Graphics g) {
		if(cursorController == null && isInFrame) {
			cursor = Main.getTextures().standardCursor;
		}
		g.drawImage(cursor, (int)point.getX()-17, (int)point.getY()-15, 48, 48, null);
	}
	
	public void setCursor(BufferedImage image) {
		this.cursor = image;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		point = e.getPoint();
		/*if(clickedObject == null && isInFrame) {//for moving the window. 
			Game.getInstance().getWindow().setLocation(new Point((int)(Game.getInstance().getWindow().getLocation().getX() + point.getX() - lastPoint.getX()), (int)(Game.getInstance().getWindow().getY() + point.getY() - lastPoint.getY())));
		}*/
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		point = e.getPoint();
		if(!Game.getInstance().getState().isBuilding) {//NPE sometimes on Initialization
			for(InputField each:Game.getInstance().getState().getFieldManager().getInputFields()) {
				each.onMouseMove(point);
			}
			for(TextArea each:Game.getInstance().getState().getTextAreaManager().getTextAreas()) {
				each.onMouseMove(point);
			}
			for(Button each:Game.getInstance().getState().getButtonManager().getButtons()) {
				each.onMouseMove(e.getPoint());
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		for(InputField each:Game.getInstance().getState().getFieldManager().getInputFields()) {
			if(each.onClick(e.getButton(), e.getPoint())) {
				return;
			}
		}
		if(Tile.inspected != null) {
			Tile.inspected.setBeingInspected(false, null);
			Tile.inspected = null;
		}
		if(e.getClickCount() == 2) {
			if(e.getButton() == MouseEvent.BUTTON1) {
				try {
					Tile tile = Game.getInstance().getState().getCamera().getTileAt(e.getPoint());
					Location location = Game.getInstance().getState().getCamera().getTileLocationAt(e.getPoint());
					tile.setBeingInspected(true, location);
				}catch(NullPointerException ex) {
					System.out.println("Caught an NPE");
					ex.printStackTrace();
					return;
				}
			}
		}
		//if(e.getButton() == MouseEvent.BUTTON3) {
			//if(Game.getInstance().getState().getEntityManager().getAvatar() != null) {
				//Game.getInstance().getState().getEntityManager().getAvatar().setDestination(new Location(e.getPoint().getX(), e.getPoint().getY()));//Disappears. Needs to move... I guess set the destination as a function of the viewfield. 
			//}
		//}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		isInFrame = true;
		cursor = Main.getTextures().standardCursor;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		isInFrame = false;
		cursor = Main.getTextures().blankCursor;
		for(Button each:Game.getInstance().getState().getButtonManager().getButtons()) {
			each.stopHovering();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//lastPoint = e.getPoint();
		for(Button each:Game.getInstance().getState().getButtonManager().getButtons()) {
			if(each.onMouseDown(e.getButton(), e.getPoint())) {
				break;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for(Button each:Game.getInstance().getState().getButtonManager().getButtons()) {
			if(each.onMouseUp(e.getButton(), e.getPoint())) {
				break;
			}
		}
	}
}
