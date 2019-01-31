package dev.blue.rotu;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import dev.blue.rotu.managers.KeyManager;
import dev.blue.rotu.managers.MouseManager;

public class Window extends JFrame {
	//private JTextArea output;
	public boolean connected = false;
	public boolean verified = false;
	public String user = "Default";
	public Canvas canvas;
	private MouseManager mm;
	private KeyManager km;
	public static int width = Toolkit.getDefaultToolkit().getScreenSize().width, height = Toolkit.getDefaultToolkit().getScreenSize().height;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Window() {
		setTitle("Rotu V-"+Main.VERSION);
		Dimension d = new Dimension(width, height);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setMaximumSize(d);
		setMinimumSize(d);
		setSize(d);
		setLocationRelativeTo(null);
		setUndecorated(true);
		
		mm = new MouseManager();
		km = new KeyManager();
		
		canvas = new Canvas();
		canvas.setPreferredSize(d);
		canvas.setMaximumSize(d);
		canvas.setMinimumSize(d);
		canvas.setSize(width, height);
		canvas.setBounds(0, 0, width, height);
		canvas.addMouseListener(mm);
		canvas.addMouseMotionListener(mm);
		canvas.addKeyListener(km);
		add(canvas);
		
		addMouseListener(mm);
		addMouseMotionListener(mm);
		addKeyListener(km);
		
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (Main.reader != null) {
					Main.reader.stop();
				}
				System.exit(0);
			}
		});
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(Main.getTextures().blankCursor, new Point(17, 15), "blank"));
		setIconImage(Main.getTextures().logo);
		pack();
		setVisible(true);

		/*PrintStream out = new PrintStream(new OutputStream() {
			@Override
			public void write(int b) throws IOException {
				//output.append("" + (char) (b & 0xFF));
			}
		});
		System.setOut(out);*/
		//output.append("Please enter a server address to continue.\n");
	}

	public void attemptToConnect() {
		Main.connect();
	}

	public void retryConnection() {
		//output.append("Could not connect to server; Server is offline or does not exist.\nPlease enter a server address to continue\n");
		connected = false;
	}

	public void resetConnection() {
		//output.append("Server is offline! Please enter a server address to continue.\n");
		connected = false;
	}

	public MouseManager getMouseManager() {
		return mm;
	}

	public KeyManager getKeyManager() {
		return km;
	}
	/**
	 *1/500th of the Window's width
	 **/
	public static int microSpace() {
		return width/500;
	}
	/**
	 *microSpace x2
	 **/
	public static int tinySpace() {
		return microSpace()*2;
	}
	/**
	 *tinySpace x2
	 **/
	public static int smallSpace() {
		return tinySpace()*2;
	}
	/**
	 *tinySpace + smallSpace, or 3x tinySpace
	 **/
	public static int smallishSpace() {
		return tinySpace()+smallSpace();
	}
	/**
	 *microSpace + tinySpace + smallSpace, or 7x microSpace
	 **/
	public static int almostSpace() {
		return microSpace()+tinySpace()+smallSpace();
	}
	/**
	 *smallSpace x2
	 */
	public static int space() {
		return smallSpace()*2;
	}
}
