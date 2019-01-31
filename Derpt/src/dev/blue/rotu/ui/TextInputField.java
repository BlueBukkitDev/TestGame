package dev.blue.rotu.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

import dev.blue.rotu.Game;
import dev.blue.rotu.Main;

public class TextInputField extends InputField {

	protected boolean writable;
	protected int vertIndent = 4;
	protected int indent = 6;
	protected int timer = 0;
	protected int blinkSpeed = 16;
	protected String text1 = "";
	protected String text2 = "";
	protected String displayText = "";
	protected String preview = "";
	protected List<Character> allowed = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
			'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
			'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '1', '2', '3',
			'4', '5', '6', '7', '8', '9', '0', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '-', '+', '=',
			'~', '`', ',', '<', '.', '>', '/', '?', '\\', '|', ' ', '{', '}', '[', ']', ':', ';', '\'', '\"', '§');
	protected Graphics g;
	protected int typeIndex = 0;
	protected boolean protectedDisplay = false;
	private int aniSpeed = 0, aniTimer = 0, frameIndex = 0;
	private BufferedImage frame;
	private TextArea toWriteTo;

	/**
	 * <p>
	 * <strong>Parameters:</strong></br>
	 * <strong>========================================================</strong></br>
	 * <strong>String ID </strong>(the id by which this object can be called)</br>
	 * <strong>int x </strong>(the distance from the left side of the window that
	 * the top left corner will be drawn)</br>
	 * <strong>int y </strong>(the distance from the top side of the window that
	 * the top left corner will be drawn)</br>
	 * <strong>int width </strong>(the width of the textField)</br>
	 * <strong>int height </strong>(the height of the textField)</br>
	 * <strong>String preview </strong>(the String displayed in the text field while
	 * it is empty [OPTIONAL])</br>
	 * <strong>String value </strong>(the pre-altered String inside the text field
	 * [OPTIONAL])</br>
	 * <strong>boolean writable </strong>(whether or not the user is able to alter
	 * the text field's value)</br>
	 * <strong>boolean protectedDisplay </strong>(whether each character should be
	 * replaced with * when rendered) </br>
	 * <strong>int animationSpeed </strong>(the speed at which the images will be
	 * cycled, every update incrementing a timer by 1/animationSpeed. If set to 0,
	 * no animations occur and the first image is the displayed image.)</br>
	 * <strong>BufferedImage[] images </strong>(the image(s) that the render method
	 * will iterate through, allowing for dynamic textures)</br>
	 * </br>
	 * <strong>Supers:</strong> the id, x, y, width, height, and images to the
	 * InputField class.
	 * </p>
	 **/
	public TextInputField(String id, int x, int y, int width, int height, String preview, String value,
			boolean writable, boolean protectedDisplay, int animationSpeed, TextArea toWriteTo,
			BufferedImage... images) {
		super(id, x, y, width, height, images);
		bounds = new Rectangle(x, y, width, height);
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.images = images;
		this.writable = writable;
		this.preview = preview;
		this.text1 = value;
		this.protectedDisplay = protectedDisplay;
		this.aniSpeed = animationSpeed;
		this.toWriteTo = toWriteTo;
		frame = images[0];
	}

	@Override
	public void render(Graphics g) {

		g.drawImage(frame, x, y, width, height, null);///////// Next, render the text

		////////////////////////////////////////////////////////// DISPLAY///////////////////////////////////////////////////
		////////////////////////////////////////////////////////// TEXT//////////////////////////////////////////////////////
		if (text1.length() + text2.length() >= 1) {
			g.setFont(new Font("Arial", Font.PLAIN, 18));
			// showSelection(g);////////Shows the selection box
			g.setColor(Color.BLACK);
			if (protectedDisplay) {
				String replacement = "";
				for (int i = 0; i < displayText.length(); i++) {
					replacement += "*";
				}
				g.drawString(replacement, x + indent - 1, y + g.getFontMetrics().getHeight());
			} else
				g.drawString(displayText, x + indent - 1, y + g.getFontMetrics().getHeight());
		} else {
			if(!isSelected()) {
				g.setFont(new Font("Arial", Font.ITALIC, 18));
				g.setColor(new Color(90, 90, 90));
				g.drawString(preview, x + indent - 1, y + g.getFontMetrics().getHeight());
			}
		}
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		this.g = g;// For the Key Listening method below

		///////////////////////////////////////////////////////////// BLINKER//////////////////////////////////////////////////////////
		if (selected) {
			if (timer >= 2 * blinkSpeed) {// This properly sets the timer...
				timer = 0;
			}
			if (timer <= blinkSpeed) {// ...and this draws the line. No pun intended. ;)
				g.setColor(Color.BLACK);
				g.fillRect(x + g.getFontMetrics().stringWidth(displayText.substring(0, text1.length())) + indent - 1,
						y + vertIndent, 2, images[0].getHeight() - (vertIndent * 2));
			}
		}
		timer++;
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	}

	public String getText() {
		return text1 + text2;
	}

	public void setText(String text) {
		text1 = text;
		text2 = "";
		displayText = text1 + text2;
		typeIndex = displayText.length();
	}

	@Override
	public void update() {
		animate();
	}

	private void animate() {
		if (aniSpeed > 0) {
			aniTimer++;
			if (aniTimer >= 1000 / aniSpeed) {
				aniTimer = 0;
				frameIndex++;
				if (frameIndex > images.length) {
					frameIndex = 0;
				}
				this.frame = images[frameIndex];
			}
		}
	}

	@Override
	public boolean onClick(int button, Point p) {// My mouse has 5 buttons. Left, wheel, right, closeThumb, farThumb.
		if (bounds.contains(p)) {
			if (button == 1 || button == 3) {
				timer = 0;
				if (writable) {
					selected = true;////////////////////// update flashing bar to line up with asterisks. Can I use
									////////////////////// DisplayString?//Used it.
				}

				// Update text parts
				// iterate through the displayed text until the pixel width + x + indent >=
				// click x
				int i = getIndex(p);
				if (i < displayText.length()) {
					text1 = displayText.substring(0, i);
					text2 = displayText.substring(i, displayText.length());
					typeIndex = i;
				} else if (i >= displayText.length()) {
					text1 = displayText;
					text2 = "";
					typeIndex = displayText.length();
				}
			}
			return true;
		} else
			selected = false;
		return false;
	}

	@Override
	public void onMouseMove(Point p) {
		if (bounds.contains(p)) {
			hovering = true;
			Game.getInstance().getWindow().getMouseManager().setCursor(Main.getTextures().typeCursor);
			if(Game.getInstance().getWindow().getMouseManager().cursorController == null) {
				Game.getInstance().getWindow().getMouseManager().cursorController = this;
			}
		} else {
			hovering = false;
			if(Game.getInstance().getWindow().getMouseManager().cursorController == this) {
				Game.getInstance().getWindow().getMouseManager().cursorController = null;
			}
		}
	}

	@Override
	public void onType(KeyEvent e) {
		if (g.getFontMetrics().stringWidth(text1 + text2 + e.getKeyChar()) <= width - (indent * 2)) {//Prevents typing outside the box
			for (char each : allowed) {
				if (each == e.getKeyChar()) {
					text1 += e.getKeyChar();
					displayText = text1 + text2;
					typeIndex++;
					break;// currently, the bar won't be full. Need to make it scrollable. NEED.
				}
			}
		}
		if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {// Fix back space too for the variable position//Fixed
			if (text1.length() > 0) {
				text1 = text1.substring(0, text1.length() - 1);
				displayText = text1 + text2;
				typeIndex--;
			}
		}
		//////////////////////////////////////////////// This just deals with adjusting
		//////////////////////////////////////////////// for protected text
		if (protectedDisplay) {
			String replacement = "";
			for (int i = 0; i < displayText.length(); i++) {
				replacement += "*";
			}
			displayText = replacement;
		}
		onTypeExtra();
	}
	
	public void onTypeExtra() {}

	@Override
	public void onKeyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {/////////////// CHECK MAKE SURE YOU AREN'T TOO FAR LEFT OR RIGHT
			/////////////// INDEX ARRAY OUT OF BOUNDS EXCEPTION//Done
			if (typeIndex >= 1) {
				text1 = text1.substring(0, text1.length() - 1);
				text2 = displayText.substring(typeIndex - 1, displayText.length());
				typeIndex--;
				timer = 0;
			}
		}else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (typeIndex < displayText.length()) {
				text1 = displayText.substring(0, text1.length() + 1);// ieobe //idek what ieobe is supposed to mean.
																		// Maybe iaobe, indexatrrayoutofboundsexception.
				text2 = displayText.substring(typeIndex + 1, displayText.length());
				typeIndex++;
				timer = 0;//////////////// Setting timer to 0 on the arrow keys was a huge help of a tiny
				//////////////// thing I didn't realize was annoying and important. // Still
				//////////////// love this fix. :3
			}
		}else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			print();// As a final option; after every other operation, try to print. Just in case.
		}
	}

	public void print() {
		String message = getText();
		message = "§6§l"+Game.getInstance().getWindow().user+": §r"+message;
		if(message.contains("§")) {
			TextBit[] bits = getBits(message);
			if (toWriteTo != null) {
				if(bits != null) {
					if (Game.getInstance().getWindow().connected) {
						if (Game.getInstance().getWindow().verified) {
							toWriteTo.addLine(bits);
							Main.write(message);
							setText("");
						} else {
							System.out.println("Unverified user!");
						}
					} else {
						Game.getInstance().getWindow().attemptToConnect();
						
					}
				}
			}
		}else {
			if (toWriteTo != null) {
				if (Game.getInstance().getWindow().connected) {
					if (Game.getInstance().getWindow().verified) {
						toWriteTo.addLine(new TextBit(Color.BLACK, Main.getFonts().plain, message, null, null));
						setText("");
					} else {
						System.out.println("Unverified user!");
					}
				} else {
					Game.getInstance().getWindow().attemptToConnect();
				}
			}
		}
	}
	
	private TextBit[] getBits(String message) {
		String[] parts = message.split("§");
		TextBit[] bits = new TextBit[parts.length];
		for(int i = 1; i <  parts.length; i++) {
			TextBit bit;
			if(parts[i].startsWith("a")) {
				bit = new TextBit(new Color(0, 255, 0), Main.getFonts().plain, parts[i].substring(1), null, null);
			} else if(parts[i].startsWith("b")) {
				bit = new TextBit(Color.CYAN, Main.getFonts().plain, parts[i].substring(1), null, null);
			} else if(parts[i].startsWith("c")) {
				bit = new TextBit(Color.RED, Main.getFonts().plain, parts[i].substring(1), null, null);
			} else if(parts[i].startsWith("d")) {
				bit = new TextBit(Color.PINK, Main.getFonts().plain, parts[i].substring(1), null, null);
			} else if(parts[i].startsWith("e")) {
				bit = new TextBit(Color.YELLOW, Main.getFonts().plain, parts[i].substring(1), null, null);
			} else if(parts[i].startsWith("f")) {
				bit = new TextBit(Color.WHITE, Main.getFonts().plain, parts[i].substring(1), null, null);
			} else if(parts[i].startsWith("0")) {
				bit = new TextBit(Color.BLACK, Main.getFonts().plain, parts[i].substring(1), null, null);
			} else if(parts[i].startsWith("1")) {
				bit = new TextBit(Color.BLUE, Main.getFonts().plain, parts[i].substring(1), null, null);
			} else if(parts[i].startsWith("2")) {
				bit = new TextBit(new Color(0, 80, 0), Main.getFonts().plain, parts[i].substring(1), null, null);
			} else if(parts[i].startsWith("3")) {
				bit = new TextBit(new Color(0, 80, 80), Main.getFonts().plain, parts[i].substring(1), null, null);
			} else if(parts[i].startsWith("4")) {
				bit = new TextBit(new Color(80, 0, 0), Main.getFonts().plain, parts[i].substring(1), null, null);
			} else if(parts[i].startsWith("5")) {
				bit = new TextBit(new Color(80, 0, 80), Main.getFonts().plain, parts[i].substring(1), null, null);
			} else if(parts[i].startsWith("6")) {
				bit = new TextBit(new Color(80, 0, 80), Main.getFonts().plain, parts[i].substring(1), null, null);
			} else if(parts[i].startsWith("7")) {
				bit = new TextBit(Color.GRAY, Main.getFonts().plain, parts[i].substring(1), null, null);
			} else if(parts[i].startsWith("8")) {
				bit = new TextBit(Color.DARK_GRAY, Main.getFonts().plain, parts[i].substring(1), null, null);
			} else if(parts[i].startsWith("9")) {
				bit = new TextBit(new Color(80, 0, 200), Main.getFonts().plain, parts[i].substring(1), null, null);
			} else if(parts[i].startsWith("l")) {
				if(i > 1) {
					bit = new TextBit(bits[i-1].getC(), Main.getFonts().bold, parts[i].substring(1), null, null);//////////Get rid of modifier
				} else bit = new TextBit(Color.BLACK, Main.getFonts().bold, parts[i].substring(1), null, null);
			}else if(parts[i].startsWith("i")) {
				if(i > 1) {
					bit = new TextBit(bits[i-1].getC(), Main.getFonts().italic, parts[i].substring(1), null, null);
				} else bit = new TextBit(Color.BLACK, Main.getFonts().italic, parts[i].substring(1), null, null);
			}else if(parts[i].startsWith("r")) {
				bit = new TextBit(Color.BLACK, Main.getFonts().plain, parts[i].substring(1), null, null);
			}else {
				if(i > 1) {
					bit = new TextBit(bits[i-1].getC(), bits[i-1].getF(), parts[i], null, null);
				} else {
					bit = new TextBit(Color.BLACK, Main.getFonts().plain, "§"+parts[i], null, null);
				}
			}
			bits[i] = bit;
		}
		return bits;
	}

	private int getIndex(Point p) {
		if (bounds.contains(p)) {
			for (int i = 0; i < displayText.length(); i++) {
				if (x + indent + g.getFontMetrics().stringWidth(displayText.substring(0, i)) >= p.getX()) {
					return i;
				} else if (x + indent + g.getFontMetrics().stringWidth(displayText) < p.getX()) {
					return displayText.length();
				}
			}
		}
		return 0;
	}
}
