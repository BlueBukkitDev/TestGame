package dev.blue.rotu.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import dev.blue.rotu.Game;
import dev.blue.rotu.Main;

public class TextArea {
	private String id;
	private Rectangle bounds;
	private int x, y, width, height;
	private BufferedImage[] images;
	private BufferedImage frame;
	private int frameIndex;
	private boolean hovering = false;
	private int aniSpeed = 0;
	private int aniTimer = 0;
	private List<BitLine> lines;
	private int lastLineY;
	private Graphics g;
	private int cushion;
	private boolean isActive;//Used only to exist or not exist without destroying the instantiation in the GameState
	private int unalterableFontSize = 0;
	
	/**
	 * <p>
	 * <strong>Parameters:</strong></br>
	 * <strong>String id </strong>(an ID by which this button can be taken out of the ButtonManager easily. Not required.)</br>
	 * <strong>int x </strong>(the X coordinate of the top left corner of this button's bounding box)</br>
	 * <strong>int y </strong>(the Y coordinate of the top left corner of this button's bounding box)</br>
	 * <strong>int width </strong>(the width of this button's bounding box)</br>
	 * <strong>int height </strong>(the height of this button's bounding box)</br>
	 * <strong>int animationSpeed </strong>(the speed at which the images will be cycled, every update 
	 * incrementing a timer by 1/animationSpeed. If set to 0, no animations occur and the first image is the displayed image.)</br>
	 * <strong>int cushion </strong>(The distance between the text and the edges of this TextArea)</br>
	 * <strong>BufferedImage[] images </strong>(the image(s) that the render method
	 * will iterate through, allowing for dynamic textures)</br>
	 * </p>
	 **/
	public TextArea(String id, int x, int y, int width, int height, int animationSpeed, int cushion, boolean cushionTop, BufferedImage... backgrounds) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.images = backgrounds;
		this.bounds = new Rectangle(x, y, width, height);
		this.aniSpeed = animationSpeed;
		this.frame = images[0];
		this.cushion = cushion;
		if(cushionTop) {
			this.lastLineY = y + cushion;
		}else this.lastLineY = y;
		this.lines = new ArrayList<BitLine>();
		isActive = true;
	}
	
	public void render(Graphics g) {
		if(isActive) {
			this.g = g;
			g.drawImage(frame, x, y, width, height, null);
			for(BitLine each:lines) {
				each.render(g);
			}
		}
	}
	
	public void logInfo(String message) {
		addLine(new TextBit(Color.BLUE, Main.getFonts().bold, "INFO: ", null, null), new TextBit(Color.BLACK, Main.getFonts().plain, message, null, null));
	}
	
	public void logError(String message) {
		addLine(new TextBit(Color.RED, Main.getFonts().bold, "ERROR: ", null, null), new TextBit(Color.RED, Main.getFonts().plain, message, null, null));
	}
	
	public void setUnalterableFontSize(int size) {
		unalterableFontSize = size;
	}
	
	public void logIncoming(String message) {
		if(message.contains("§")) {
			TextBit[] bits = getBits(message);
			if (this != null) {
				if(bits != null) {
					if (Game.getInstance().getWindow().connected) {
						if (Game.getInstance().getWindow().verified) {
							this.addLine(bits);
						}
					}
				}
			}
		}else {
			if (this != null) {
				if (Game.getInstance().getWindow().connected) {
					if (Game.getInstance().getWindow().verified) {
						this.addLine(new TextBit(Color.BLACK, Main.getFonts().plain, message, null, null));
					}
				}
			}
		}
	}
	
	public void logMessage(String message) {
		message = "§6§l"+Game.getInstance().getWindow().user+": §r"+message;
		if(message.contains("§")) {
			TextBit[] bits = getBits(message);
			if (this != null) {
				if(bits != null) {
					if (Game.getInstance().getWindow().connected) {
						if (Game.getInstance().getWindow().verified) {
							this.addLine(bits);
							Main.write(message);
						} else {
							System.out.println("Unverified user!");
						}
					} else {
						Game.getInstance().getWindow().attemptToConnect();
						
					}
				}
			}
		}else {
			if (this != null) {
				if (Game.getInstance().getWindow().connected) {
					if (Game.getInstance().getWindow().verified) {
						this.addLine(new TextBit(Color.BLACK, Main.getFonts().plain, message, null, null));
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
	
	public void addLine(String string) {
		TextBit[] bits = getBits(string);
		addLine(bits);
	}
	
	public void addLine(TextBit... bits) {//Build this list of TextBits in the Window class in the event listener for the TextInputField... or GameState. 
		BitLine line = new BitLine(width - cushion*2);//Should be done with this method?
		Font font = null;
		for(TextBit each:bits) {
			if(each != null) {
				if(unalterableFontSize > 0) {
					if(each.getF() != null) {
						each.setF(each.getF().deriveFont((float)unalterableFontSize));
					}else each.setF(Main.getFonts().custom(12, Font.PLAIN));
				}
				if(font == null) {
					font = each.getF();
				}
				line.addBit(each);//Had forgotten to add bits before testing the height... XD Derp
			}
		}
		if(lastLineY == y) {
			lastLineY += Game.getInstance().getGraphics().getFontMetrics(font).getHeight();
		}
		line.setY(lastLineY);
		line.setX(x+cushion);
		lastLineY += line.getHeight();
		this.lines.add(line);
	}
	
	public void update() {
		runOnUpdate();
		animate();
	}
	
	public void runOnUpdate() {}
	
	public void onMouseMove(Point p) {
		if (bounds.contains(p)) {
			hovering = true;
		} else {
			hovering = false;
		}
	}
	
	private void animate() {
		if(aniSpeed > 0) {
			aniTimer++;
			if(aniTimer >= 1000/aniSpeed) {
				aniTimer = 0;
				frameIndex++;
				if(frameIndex > images.length) {
					frameIndex = 0;
				}
				this.frame = images[frameIndex];
			}
		}
	}
	
	public void run() {}
	
	public boolean onClick(int button, Point p) {
		if(this.bounds.contains(p)) {
			run();
			return true;
		}else return false;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public BufferedImage[] getImages() {
		return images;
	}

	public void setImages(BufferedImage[] images) {
		this.images = images;
	}

	public boolean isHovering() {
		return hovering;
	}

	public void setHovering(boolean hovering) {
		this.hovering = hovering;
	}

	public List<BitLine> getLines() {
		return lines;
	}

	public Graphics getGraphics() {
		return g;
	}
	
	public void deactivate() {
		isActive = false;
	}
	
	public void activate() {
		isActive = true;
	}

	public void clear() {
		lines.clear();
		lastLineY = y+cushion;
	}
}
