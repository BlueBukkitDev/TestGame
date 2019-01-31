package dev.blue.rotu;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import dev.blue.rotu.comm.Reader;
import dev.blue.rotu.gfx.utils.Animations;
import dev.blue.rotu.gfx.utils.Fonts;
import dev.blue.rotu.gfx.utils.Textures;

public class Main {
	private static Socket connection;
	private static ObjectOutputStream output;
	private static ObjectInputStream input;
	public static Reader reader;
	public static Window window;
	public static Game game;
	public static final String VERSION = "0.1.17";
	private static Textures textures;
	private static Animations animations;
	private static Fonts fonts;
	private static String IPAddress = "127.0.0.1";

	public static void main(String[] args) {
		System.out.println("Starting client!");
		textures = new Textures();
		textures.loadTextures();
		animations = new Animations();
		animations.loadAnimations();
		fonts = new Fonts();
		fonts.load();
		game = new Game();
		game.start();
		window = game.getWindow();
		connect();
	}

	public static void connect() {
		try {
			connection = new Socket(InetAddress.getByName(IPAddress), 11111);
			setupStreams();
			System.out.println("Connected to " + connection.getInetAddress().getHostName());
			window.connected = true;
			reader = new Reader(input);
			reader.start();
		} catch(ConnectException e) {
			window.resetConnection();
			System.out.println("Connection reset! You are no longer connected to the server.");
		} catch (IOException e) {
			e.printStackTrace();
			window.connected = false;
			window.retryConnection();
			System.out.println("Retrying connection...");
		}
	}

	private static void setupStreams() {
		try {
			output = new ObjectOutputStream(connection.getOutputStream());
			output.flush();
			input = new ObjectInputStream(connection.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void write(String message) {
		try {
			output.writeUTF(message);
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void sendFile(File file) {
		Path path = Paths.get(file.getPath());
		try {
			byte[] data = Files.readAllBytes(path);
			output.write(data);
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Textures getTextures() {
		return textures;
	}
	
	public static Animations getAnimations() {
		return animations;
	}
	
	public static Fonts getFonts() {
		return fonts;
	}
}
