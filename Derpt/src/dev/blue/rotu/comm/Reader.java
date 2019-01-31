package dev.blue.rotu.comm;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketException;

import dev.blue.rotu.Game;
import dev.blue.rotu.GameStateType;
import dev.blue.rotu.Main;

public class Reader implements Runnable {
	private Thread thread;
	private ObjectInputStream input;
	private boolean running;

	public Reader(ObjectInputStream input) {
		thread = new Thread(this);
		this.input = input;
		running = false;
	}

	public synchronized void start() {
		running = true;
		thread.start();
	}

	public synchronized void stop() {
		running = false;
	}

	@Override
	public void run() {
		String lastMessage = "";
		String message = "";
		try {
			while (running) {
				if(lastMessage != (message = input.readUTF())) {
					if(message.equals("It's Tuesday 31415926535")) {
						Main.window.resetConnection();
					}else if(message.startsWith("LOGIN又EQUENCE壯")){
						String[] parts = message.split("壯");
						if(parts[1].equals("FAILURE_USER")) { 
							if(Game.getInstance().getState().errorLog != null) {
								Game.getInstance().getState().errorLog.clear();
								Game.getInstance().getState().errorLog.logError("Username does not exist!");
								System.out.println("Username does not exist!");
							}
						}else if(parts[1].equals("FAILURE_PASS")) {
							if(Game.getInstance().getState().errorLog != null) {
								Game.getInstance().getState().errorLog.clear();
								Game.getInstance().getState().errorLog.logError("Incorrect password!");
								System.out.println("Incorrect password!");
							}
						}else if(parts[1].equals("SUCCESS")) {
							if(Game.getInstance().getState().errorLog != null) {
								Game.getInstance().getState().errorLog.clear();
								Game.getInstance().getState().errorLog.logInfo("You have been logged in!");
								System.out.println("You have been logged in!");
								Game.getInstance().getState().changeGameState(GameStateType.MAIN_MENU_STATE);
							}
							Main.window.verified = true;//Now do update
						}
					}else if(message.startsWith("REGISTER又EQUENCE壯")){
						String[] parts = message.split("壯");
						if(parts[1].equals("FAILURE_USER")) {
							if(Game.getInstance().getState().errorLog != null) {
								Game.getInstance().getState().errorLog.clear();
								Game.getInstance().getState().errorLog.logError("Username has already been taken!");
								System.out.println("Username has already been taken!");
							}
						}else if(parts[1].equals("SUCCESS")) {
							Game.getInstance().getState().changeGameState(GameStateType.MAIN_MENU_STATE);
							Game.getInstance().getState().errorLog.clear();
							Game.getInstance().getState().errorLog.logInfo("Your account has been registered!");
							System.out.println("Your account has been registered!");
							Main.window.verified = true;//Also update
						}
					}else if(message.startsWith("TEST下SER壯")){
						Game.getInstance().getState().errorLog.clear();
						String[] parts = message.split("壯");
						Game.getInstance().getState().usernameStatus = parts[1];
					} else {
						if(Game.getInstance().getState().chatLog != null) {
							Game.getInstance().getState().chatLog.logIncoming(message);
							lastMessage = message;
						}
					}
				}
			}
		} catch (SocketException e) {
			if(e.getMessage().contains("Connection reset")) {
				Main.window.connected = false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*private void update() {
		Main.write("VERSION又EQUENCE壯"+Main.VERSION);
	}*/
}
