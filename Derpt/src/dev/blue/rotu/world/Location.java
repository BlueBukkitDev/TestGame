package dev.blue.rotu.world;

public class Location {
	double x, y;
	public Location(double x, double y) {
		this.x = x; 
		this.y = y;
	}
	public Location(Location location) {
		this.x = location.getX(); 
		this.y = location.getY();
	}
	
	public Location subtract(double x, double y) {
		this.x -= x;
		this.y -= y;
		return new Location(this.x, this.y);
	}
	
	public Location add(double x, double y) {
		this.x += x;
		this.y += y;
		return new Location(this.x, this.y);
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public double distance(Location loc) {
		if(loc == null) {
			return 0;
		}
		return Math.hypot(Math.abs(loc.getX()-x), Math.abs(loc.getY()-y));
	}
}
