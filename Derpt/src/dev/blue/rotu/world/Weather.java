package dev.blue.rotu.world;

import java.awt.Color;
import java.awt.Graphics;

import dev.blue.rotu.Game;
import dev.blue.rotu.Thing;
import dev.blue.rotu.Window;

public class Weather extends Thing {
	private final double maxTime = 162000;
	private double darkness = 200;
	private double daytime = 27000;
	private double moonPhase = 86;
	private boolean isDay = false;
	private double beat = 1;//3
	private double second = 1;//60 seconds per minute
	private double minute = 1;//60 minutes per hour
	private double hour = 1;//15 hours in a day
	private int day = 1;
	private int month = 1;
	private int year = 1;
	private double temperature = 86;
	private WeatherState state = WeatherState.CLEAR;//Need to change map panning to where if you are already panning, the panning border is much wider than if you are not. 
	
	public Weather() {
		Game.data = day+"/"+month+"/"+year;
	}
	
	@Override
	public void update() {
		daytime++;
		if(daytime > maxTime) {//162k is 24 hours//54000 = night//108000 = day
			daytime = 0;
			day++;
			Game.data = day+"/"+month+"/"+year;
		}
		beat = daytime;
		second = beat/3;
		minute = second/60;
		hour = minute/60;
		second %= 60;
		minute %= 60;
		hour %= 15;
		if(daytime > maxTime/6 && daytime < maxTime-(maxTime/6)) {
			isDay = true;
		}else {
			isDay = false;
		}
		
		Game.data2 = (int)hour+":"+(int)minute/*+" ("+(int)second+")"*/;
		calculateDarkness();
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(new Color(0, 0, 0, (int)darkness));
		g.fillRect(0, 0, Window.width, Window.height);
	}
	
	private void calculateDarkness() {
		if(isDay) {
			if(daytime < maxTime/2) {
				darkness -= ((maxTime - daytime)/1500000);
				if(darkness < 0) {
					darkness = 0;
				}
			}else if(daytime > maxTime/2) {
				darkness += (daytime/1500000);
				if(darkness > 200) {
					darkness = 200;
				}
			}
		}
		darkness = 200 - moonPhase;
		
		if(this.state == WeatherState.STORMY) {
			darkness += 40;
		}
		
		//if(state == WeatherState.CLOUDY) {
		//	darkness += 20;
		//}
	}
	
	public enum WeatherState {
		CLEAR, RAINY, CLOUDY, FOGGY, STORMY;
	}
}
