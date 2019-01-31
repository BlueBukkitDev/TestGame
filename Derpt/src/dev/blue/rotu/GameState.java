package dev.blue.rotu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.JFrame;

import dev.blue.rotu.gfx.Animation;
import dev.blue.rotu.gfx.Camera;
import dev.blue.rotu.gfx.utils.Proportions;
import dev.blue.rotu.managers.ButtonManager;
import dev.blue.rotu.managers.EntityManager;
import dev.blue.rotu.managers.InputFieldManager;
import dev.blue.rotu.managers.StatManager;
import dev.blue.rotu.managers.TextAreaManager;
import dev.blue.rotu.managers.ThingManager;
import dev.blue.rotu.ui.Button;
import dev.blue.rotu.ui.TextArea;
import dev.blue.rotu.ui.TextBit;
import dev.blue.rotu.ui.TextInputField;
import dev.blue.rotu.ui.game.StatBar;
import dev.blue.rotu.ui.game.stats.Energy;
import dev.blue.rotu.ui.game.stats.Health;
import dev.blue.rotu.ui.game.stats.Hunger;
import dev.blue.rotu.ui.game.stats.StatBarCompactor;
import dev.blue.rotu.ui.game.stats.Temperature;
import dev.blue.rotu.ui.game.stats.Thirst;
import dev.blue.rotu.ui.game.stats.Wellness;
import dev.blue.rotu.world.Location;
import dev.blue.rotu.world.Weather;
import dev.blue.rotu.world.World;
import dev.blue.rotu.world.entities.Creature;
import dev.blue.rotu.world.entities.Entity;
import dev.blue.rotu.world.entities.plants.LooseLeafGrass;
import dev.blue.rotu.world.entities.plants.RingLeaf;
import dev.blue.rotu.world.entities.structures.RabbitBurrow;
import dev.blue.rotu.world.tiles.Tile;

public class GameState {
	private boolean empty;
	private GameStateType type;
	private GameStateType bufferedType = null;
	private BufferedImage background;
	private InputFieldManager fieldManager = new InputFieldManager();
	private ButtonManager buttonManager = new ButtonManager();
	private TextAreaManager textAreaManager = new TextAreaManager();
	private EntityManager entityManager = new EntityManager();
	private StatManager statManager = new StatManager();
	private ThingManager thingManager = new ThingManager();
	public TextArea chatLog;
	private Camera camera = new Camera();
	public boolean isBuilding = false;
	public String usernameStatus = "NOTFOUND";
	private Button login;
	private Button register;
	private Animation loginUp;
	private Animation loginDown;
	private boolean loginButtonActive = false;
	private TextInputField user;
	private TextInputField pass;
	public TextArea errorLog;
	public TextArea infoBox;

	public GameState(GameStateType type) {
		changeGameState(type);
		loginUp = new Animation(10, new Location(Window.width / 2 - 50, Window.height / 2 - 50), 100, 40, Main.getTextures().button);
		loginDown = new Animation(10, new Location(Window.width / 2 - 50, Window.height / 2 - 50), 100, 40, Main.getTextures().buttonDown);
		login = new Button("Login", true, false, 16, Window.width / 2 - 50, Window.height / 2 - 50, 100, 40, 0, loginDown, loginUp) {
			@Override
			public void runClick() {
				if(user.getText().length() >= 4 && pass.getText().length() >= 4 && !user.getText().contains(" ") && !pass.getText().contains(" ")) {
					Main.write("LOGIN¤SEQUENCE§§LOGIN"+user.getText()+" "+pass.getText());
				}
			}
		};
		register = new Button("Register", true, false, 16, Window.width / 2 - 50, Window.height / 2 - 50, 100, 40, 0, loginDown, loginUp) {
			@Override
			public void runClick() {
				if(user.getText().length() >= 4 && pass.getText().length() >= 4 && !user.getText().contains(" ") && !pass.getText().contains(" ")) {
					Main.write("REGISTER¤SEQUENCE§§REGISTER"+user.getText()+" "+pass.getText());
				}
			}
		};
	}

	public void render(Graphics g) {// Time to build the gamestate to start truly rendering everything. :3=
		if (!empty) {
			g.setColor(Color.CYAN);
			g.fillRect(0, 0, Window.width, Window.height);
			if (background != null) {
				g.drawImage(background, 0, 0, Window.width, Window.height, null);// Using HEIGHT instead of height was a problem
			}
			camera.render(g);
			entityManager.render(g);
			thingManager.render(g);
			fieldManager.render(g);
			textAreaManager.render(g);
			buttonManager.render(g);
			statManager.render(g);
			g.setColor(new Color(0, 120, 120));
			g.drawRect(0, 0, Window.width-1, Window.height-1);
			g.drawRect(1, 1, Window.width-3, Window.height-3);
			g.drawRect(2, 2, Window.width-5, Window.height-5);
		}
	}

	public void update() {
		if(usernameStatus.equalsIgnoreCase("NOTFOUND") && !loginButtonActive) {
			buttonManager.hideButton("Login");
			buttonManager.revealButton("Register");
			loginButtonActive = true;
		} else if(usernameStatus.equalsIgnoreCase("FOUND") && loginButtonActive) {
			buttonManager.hideButton("Register");
			buttonManager.revealButton("Login");
			loginButtonActive = false;
		}
		if (bufferedType != null) {
			buildState(bufferedType);
			bufferedType = null;
			isBuilding = false;
			return;
		}
		if (!empty) {
			camera.update();
			entityManager.update();
			thingManager.update();
			fieldManager.update();
			buttonManager.update();
			if(Tile.inspected != null) {
				doTileInspection(Tile.inspected, Tile.inspectionLocation);
			}
			textAreaManager.update();
			statManager.update();
		}
	}

	public GameStateType getType() {
		return type;
	}

	private void clearState() {
		empty = true;
		fieldManager.clear();
		thingManager.clear();
		buttonManager.clear();
		textAreaManager.clear();
		statManager.clear();
		entityManager.clear();
		chatLog = null;
		background = null;
		camera.dispose();
	}

	public InputFieldManager getFieldManager() {
		return fieldManager;
	}

	public void setFieldManager(InputFieldManager manager) {
		this.fieldManager = manager;
	}
	
	public ThingManager getThingManager() {
		return thingManager;
	}
	
	public void setThingManager(ThingManager manager) {
		this.thingManager = manager;
	}

	public ButtonManager getButtonManager() {
		return buttonManager;
	}

	public void setButtonManager(ButtonManager buttonManager) {
		this.buttonManager = buttonManager;
	}

	public TextAreaManager getTextAreaManager() {
		return textAreaManager;
	}

	public void setTextAreaManager(TextAreaManager textAreaManager) {
		this.textAreaManager = textAreaManager;
	}
	
	public StatManager getStatManager() {
		return statManager;
	}

	public void setStatManager(StatManager statManager) {
		this.statManager = statManager;
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public void buildState(GameStateType type) {
		clearState();
		this.type = type;
		/*
		 * if(type == GameStateType.CHAT_STATE) { background =
		 * Main.getTextures().menubg; TextArea area = new TextArea("Messages", 0, 0,
		 * Window.width, Window.height-30, 0, 10, Main.getTextures().textArea2);
		 * textAreaManager.registerField(area); chatLog = area;
		 * 
		 * TextInputField chat = new TextInputField("Chat", 0, Window.height-30,
		 * Window.width-60, 30, "Type message here...", "", true, false, 0, area,
		 * Main.getTextures().inputField); fieldManager.registerField(chat);
		 * 
		 * Animation down = new Animation(10, Window.width/2-50, Window.height/2+150,
		 * 100, 40, Main.getTextures().buttonDown); Animation up = new Animation(10,
		 * Window.width/2-50, Window.height/2+150, 100, 40, Main.getTextures().button);
		 * animationManager.registerAnimation(down);
		 * animationManager.registerAnimation(up);
		 * 
		 * Button send = new Button("Send", Window.width-60, Window.height-30, 60, 30,
		 * 0, down, up) {
		 * 
		 * @Override public void runClick() { if(chat.getText().length() >= 1) {
		 * chat.print(); String message = chat.getText(); chat.setText("");//Time to
		 * split up the message into all the bits area.addLine(new TextBit(Color.BLACK,
		 * new Font("Helvetica", Font.PLAIN, 14), message, null, null)); } } };
		 * buttonManager.registerField(send); }else
		 */if (type == GameStateType.LOGIN_STATE) {
			 buildLoginUI();
		 } else if (type == GameStateType.MAIN_MENU_STATE) {
			buildMenuUI();
		} else if (type == GameStateType.GAME_STATE) {
			buildGameUI();
		}
		empty = false;
	}
	
	private void buildLoginUI() {
			background = Main.getTextures().menubg;
			
			user = new TextInputField("Username", Window.width/2-100, Window.height/2-150, 200, 30, "Username", "", true, false, 0, null, Main.getTextures().inputField) {
				@Override
				public void onTypeExtra() {
					Main.write("TEST¤USER§§"+getText());
				}
			};
			pass = new TextInputField("Password", Window.width/2-100, Window.height/2-118, 200, 30, "Password", "", true, true, 0, null, Main.getTextures().inputField) {
				@Override
				public void onTypeExtra() {
					errorLog.clear();
				}
			};
			
			errorLog = new TextArea("ErrorLog", Window.width/2-100, Window.height / 2 - 70, 200, 30, 0, 0, false, Main.getTextures().errorLog);
			
			Animation exitAnim = new Animation(10, new Location(Window.width-Proportions.CORNER_BUTTON_WIDTH-Window.tinySpace(), Window.tinySpace()), Proportions.CORNER_BUTTON_WIDTH, Proportions.CORNER_BUTTON_WIDTH, Main.getTextures().exitButton);
			Animation exitAnimHover = new Animation(10, new Location(Window.width-Proportions.CORNER_BUTTON_WIDTH-Window.tinySpace(), Window.tinySpace()), Proportions.CORNER_BUTTON_WIDTH, Proportions.CORNER_BUTTON_WIDTH, Main.getTextures().exitButtonHover);
			Animation minimizeAnim = new Animation(10, new Location(Window.width-(Proportions.CORNER_BUTTON_WIDTH*2)-Window.smallSpace(), Window.tinySpace()), Proportions.CORNER_BUTTON_WIDTH, Proportions.CORNER_BUTTON_WIDTH, Main.getTextures().minimizeButton);
			Animation minimizeAnimHover = new Animation(10, new Location(Window.width-(Proportions.CORNER_BUTTON_WIDTH*2)-Window.smallSpace(), Window.tinySpace()), Proportions.CORNER_BUTTON_WIDTH, Proportions.CORNER_BUTTON_WIDTH, Main.getTextures().minimizeButtonHover);
			
			Button exit = new Button("Exit", false, false, 12, Window.width-Proportions.CORNER_BUTTON_WIDTH-Window.tinySpace(), Window.tinySpace(), Proportions.CORNER_BUTTON_WIDTH, Proportions.CORNER_BUTTON_WIDTH, 0, exitAnim, exitAnim) {
				@Override
				public void runClick() {
					Game.getInstance().stop();
				}
				@Override
				public void runOnHover() {
					setWhileDownAnim(exitAnimHover);
					setWhileUpAnim(exitAnimHover);
				}
				@Override
				public void runOnStopHover() {
					setWhileDownAnim(exitAnim);
					setWhileUpAnim(exitAnim);
				}
			};
			Button minimize = new Button("Minimize", false, false, 12, (Window.width-Proportions.CORNER_BUTTON_WIDTH*2)-Window.tinySpace(), Window.tinySpace(), Proportions.CORNER_BUTTON_WIDTH, Proportions.CORNER_BUTTON_WIDTH, 0, minimizeAnim, minimizeAnim) {
				@Override
				public void runClick() {
					Game.getInstance().getWindow().setExtendedState(JFrame.ICONIFIED);
				}
				@Override
				public void runOnHover() {
					setWhileDownAnim(minimizeAnimHover);
					setWhileUpAnim(minimizeAnimHover);
				}
				@Override
				public void runOnStopHover() {
					setWhileDownAnim(minimizeAnim);
					setWhileUpAnim(minimizeAnim);
				}
			};
			
			fieldManager.registerField(user);
			fieldManager.registerField(pass);
			
			textAreaManager.registerField(errorLog);
			
			buttonManager.registerButton(login);
			buttonManager.hideButton("Login");
			buttonManager.registerButton(register);
			buttonManager.registerButton(minimize);
			buttonManager.registerButton(exit);
	}
	
	private void buildMenuUI() {//////////////////////Start building some menu, and then we can go to gameplay. 
		background = Main.getTextures().menubg;
		
		Animation exitAnim = new Animation(10, new Location(Window.width-Proportions.CORNER_BUTTON_WIDTH-Window.tinySpace(), Window.tinySpace()), Proportions.CORNER_BUTTON_WIDTH, Proportions.CORNER_BUTTON_WIDTH, Main.getTextures().exitButton);
		Animation exitAnimHover = new Animation(10, new Location(Window.width-Proportions.CORNER_BUTTON_WIDTH-Window.tinySpace(), Window.tinySpace()), Proportions.CORNER_BUTTON_WIDTH, Proportions.CORNER_BUTTON_WIDTH, Main.getTextures().exitButtonHover);
		Animation minimizeAnim = new Animation(10, new Location(Window.width-(Proportions.CORNER_BUTTON_WIDTH*2)-Window.smallSpace(), Window.tinySpace()), Proportions.CORNER_BUTTON_WIDTH, Proportions.CORNER_BUTTON_WIDTH, Main.getTextures().minimizeButton);
		Animation minimizeAnimHover = new Animation(10, new Location(Window.width-(Proportions.CORNER_BUTTON_WIDTH*2)-Window.smallSpace(), Window.tinySpace()), Proportions.CORNER_BUTTON_WIDTH, Proportions.CORNER_BUTTON_WIDTH, Main.getTextures().minimizeButtonHover);
		Animation settingsAnim = new Animation(10, new Location(Window.width-(Proportions.CORNER_BUTTON_WIDTH*3)-Window.smallSpace(), Window.tinySpace()), Proportions.CORNER_BUTTON_WIDTH, Proportions.CORNER_BUTTON_WIDTH, Main.getTextures().settingsButton);
		Animation settingsAnimHover = new Animation(10, new Location(Window.width-(Proportions.CORNER_BUTTON_WIDTH*3)-Window.smallSpace(), Window.tinySpace()), Proportions.CORNER_BUTTON_WIDTH, Proportions.CORNER_BUTTON_WIDTH, Main.getTextures().settingsButtonHover);
		Animation profileAnim = new Animation(10, new Location(Window.width-(Proportions.CORNER_BUTTON_WIDTH*4)-Window.smallishSpace(), Window.tinySpace()), Proportions.CORNER_BUTTON_WIDTH, Proportions.CORNER_BUTTON_WIDTH, Main.getTextures().profileButton);
		Animation profileAnimHover = new Animation(10, new Location(Window.width-(Proportions.CORNER_BUTTON_WIDTH*4)-Window.smallishSpace(), Window.tinySpace()), Proportions.CORNER_BUTTON_WIDTH, Proportions.CORNER_BUTTON_WIDTH, Main.getTextures().profileButtonHover);
		Animation playAnim = new Animation(10, new Location(Window.width/2-Window.space()*4, Window.height/2-Window.smallSpace()*2), Window.space()*8, Window.smallSpace()*4, Main.getTextures().button);
		
		Button play = new Button("Play", true, false, 16, Window.width/2-Window.space()*4, Window.height/2-Window.smallSpace()*2, Window.space()*8, Window.smallSpace()*4, 0, playAnim, playAnim) {
			@Override
			public void runClick() {
				changeGameState(GameStateType.GAME_STATE);
			}
		};
		Button exit = new Button("Exit", false, false, 12, Window.width-Proportions.CORNER_BUTTON_WIDTH-Window.tinySpace(), Window.tinySpace(), Proportions.CORNER_BUTTON_WIDTH, Proportions.CORNER_BUTTON_WIDTH, 0, exitAnim, exitAnim) {
			@Override
			public void runClick() {
				Game.getInstance().stop();
			}
			@Override
			public void runOnHover() {
				setWhileDownAnim(exitAnimHover);
				setWhileUpAnim(exitAnimHover);
			}
			@Override
			public void runOnStopHover() {
				setWhileDownAnim(exitAnim);
				setWhileUpAnim(exitAnim);
			}
		};
		Button minimize = new Button("Minimize", false, false, 12, (Window.width-Proportions.CORNER_BUTTON_WIDTH*2)-Window.tinySpace(), Window.tinySpace(), Proportions.CORNER_BUTTON_WIDTH, Proportions.CORNER_BUTTON_WIDTH, 0, minimizeAnim, minimizeAnim) {
			@Override
			public void runClick() {
				Game.getInstance().getWindow().setExtendedState(JFrame.ICONIFIED);
			}
			@Override
			public void runOnHover() {
				setWhileDownAnim(minimizeAnimHover);
				setWhileUpAnim(minimizeAnimHover);
			}
			@Override
			public void runOnStopHover() {
				setWhileDownAnim(minimizeAnim);
				setWhileUpAnim(minimizeAnim);
			}
		};
		Button settings = new Button("Settings", false, false, 12, Window.width-(Proportions.CORNER_BUTTON_WIDTH*3)-Window.smallSpace(), Window.tinySpace(), Proportions.CORNER_BUTTON_WIDTH, Proportions.CORNER_BUTTON_WIDTH, 0, settingsAnim, settingsAnim) {
			@Override
			public void runClick() {
				System.out.println("Settings");
			}
			@Override
			public void runOnHover() {
				setWhileDownAnim(settingsAnimHover);
				setWhileUpAnim(settingsAnimHover);
			}
			@Override
			public void runOnStopHover() {
				setWhileDownAnim(settingsAnim);
				setWhileUpAnim(settingsAnim);
			}
		};
		Button profile = new Button("Profile", false, false, 12, Window.width-(Proportions.CORNER_BUTTON_WIDTH*4)-Window.smallishSpace(), Window.tinySpace(), Proportions.CORNER_BUTTON_WIDTH, Proportions.CORNER_BUTTON_WIDTH, 0, profileAnim, profileAnim) {
			@Override
			public void runClick() {
				System.out.println("Profile");
			}
			@Override
			public void runOnHover() {
				setWhileDownAnim(profileAnimHover);
				setWhileUpAnim(profileAnimHover);
			}
			@Override
			public void runOnStopHover() {
				setWhileDownAnim(profileAnim);
				setWhileUpAnim(profileAnim);
			}
		};
		
		buttonManager.registerButton(play);
		buttonManager.registerButton(profile);
		buttonManager.registerButton(settings);
		buttonManager.registerButton(minimize);
		buttonManager.registerButton(exit);
	}
	
	public void doTileInspection(Tile tile, Location location) {
		infoBox.clear();
		if(tile != null) {
			infoBox.activate();
			infoBox.addLine("§6§lTerrain: §3§l"+tile.getName());
			infoBox.addLine("§6§lLocation: §8§l(§3§l"+(int)location.getX()+","+(int)location.getY()+"§8§l)");
			HashMap<String, Integer> objects = new HashMap<String, Integer>();
			for(Entity each:tile.getObjects()) {
				if(each.isShowing()) {
					String toAdd = each.getID();
					if(each instanceof Creature) {
						Creature creature = (Creature) each;
						toAdd += "§8§l("+creature.getGender().toString()+") ";
						toAdd += "(gen"+creature.getGeneration()+") ";
						toAdd += creature.getHealthAsString()+" ("+creature.getMaxHealth()+")";
					}
					if(each instanceof RingLeaf) {
						RingLeaf ringleaf = (RingLeaf) each;
						toAdd += "§8§l(x"+ringleaf.getClumpSize()+") ";
					}else if(each instanceof LooseLeafGrass) {
						LooseLeafGrass grass = (LooseLeafGrass) each;
						toAdd += "§8§l(x"+grass.getClumpSize()+") ";
					}else if(each instanceof RabbitBurrow) {
						RabbitBurrow burrow = (RabbitBurrow) each;
						toAdd += "§8§l("+burrow.getRabbitCount()+")"+"("+burrow.getHealth()+")";
					}
					if(objects.containsKey(toAdd)) {
						objects.put(toAdd, objects.get(toAdd)+1);
					}else objects.put(toAdd, 1);
				}
			}
			if(objects.keySet().size() > 0) {
				infoBox.addLine("§6§lObjects: ");
				for(String each:objects.keySet()) {
					infoBox.addLine("§3§l"+each);
				}
			}else infoBox.addLine("§6§lObjects: §3§lnone");
		}else infoBox.deactivate();
	}
	
	private void buildGameUI() {
		camera.build(new World(), new Point(2000, 900));//Doesn't matter where your point is. It places you in the top left corner. //Fixed
		background = null;
		
		Animation healthBarAnim = new Animation(10, new Location(3, 3), Proportions.STAT_BAR_WIDTH, Proportions.STAT_BAR_HEIGHT, Main.getTextures().healthBar);
		Animation energyBarAnim = new Animation(10, new Location(3, 3+Proportions.STAT_BAR_HEIGHT), Proportions.STAT_BAR_WIDTH, Proportions.STAT_BAR_HEIGHT, Main.getTextures().energyBar);
		Animation wellnessBarAnim = new Animation(10, new Location(3, 3+(Proportions.STAT_BAR_HEIGHT*2)), Proportions.STAT_BAR_WIDTH, Proportions.STAT_BAR_HEIGHT, Main.getTextures().wellnessBar);
		
		Animation foodBarAnim = new Animation(10, new Location(3+Proportions.STAT_BAR_HEIGHT, 3+(Proportions.STAT_BAR_HEIGHT*3)), Proportions.STAT_BAR_WIDTH_2, Proportions.STAT_BAR_HEIGHT_2, Main.getTextures().foodBar);
		Animation waterBarAnim = new Animation(10, new Location(3+Proportions.STAT_BAR_HEIGHT, 3+(Proportions.STAT_BAR_HEIGHT*3)+(Proportions.STAT_BAR_HEIGHT_2)), Proportions.STAT_BAR_WIDTH_2, Proportions.STAT_BAR_HEIGHT_2, Main.getTextures().waterBar);
		Animation tempBarAnim = new Animation(10, new Location(3+Proportions.STAT_BAR_HEIGHT, 3+(Proportions.STAT_BAR_HEIGHT*3)+(Proportions.STAT_BAR_HEIGHT_2*2)), Proportions.STAT_BAR_WIDTH_2, Proportions.STAT_BAR_HEIGHT_2, Main.getTextures().tempBar);
		
		Animation mainBarCompactorAnim = new Animation(10, new Location(3, 3+(Proportions.STAT_BAR_HEIGHT*3)), Proportions.STAT_BAR_HEIGHT, Proportions.STAT_BAR_HEIGHT*2, Main.getTextures().statBarCompactor);
		
		Animation exitAnim = new Animation(10, new Location(Window.width-Proportions.CORNER_BUTTON_WIDTH-Window.tinySpace(), Window.tinySpace()), Proportions.CORNER_BUTTON_WIDTH, Proportions.CORNER_BUTTON_WIDTH, Main.getTextures().exitButton);
		Animation exitAnimHover = new Animation(10, new Location(Window.width-Proportions.CORNER_BUTTON_WIDTH-Window.tinySpace(), Window.tinySpace()), Proportions.CORNER_BUTTON_WIDTH, Proportions.CORNER_BUTTON_WIDTH, Main.getTextures().exitButtonHover);
		Animation minimizeAnim = new Animation(10, new Location(Window.width-(Proportions.CORNER_BUTTON_WIDTH*2)-Window.smallSpace(), Window.tinySpace()), Proportions.CORNER_BUTTON_WIDTH, Proportions.CORNER_BUTTON_WIDTH, Main.getTextures().minimizeButton);
		Animation minimizeAnimHover = new Animation(10, new Location(Window.width-(Proportions.CORNER_BUTTON_WIDTH*2)-Window.smallSpace(), Window.tinySpace()), Proportions.CORNER_BUTTON_WIDTH, Proportions.CORNER_BUTTON_WIDTH, Main.getTextures().minimizeButtonHover);
		

		StatBar healthBar = new StatBar(new Health(null), 3, 3, 10, Color.RED, false, healthBarAnim, healthBarAnim, healthBarAnim, healthBarAnim);
		StatBar energyBar = new StatBar(new Energy(null), 3, 3+Proportions.STAT_BAR_HEIGHT, 10, Color.YELLOW, false, energyBarAnim, energyBarAnim, energyBarAnim, energyBarAnim);
		StatBar wellnessBar = new StatBar(new Wellness(null), 3, 3+(Proportions.STAT_BAR_HEIGHT*2), 10, Color.GREEN, false, wellnessBarAnim, wellnessBarAnim, wellnessBarAnim, wellnessBarAnim);
		
		StatBar foodBar = new StatBar(new Hunger(null), 3+Proportions.STAT_BAR_HEIGHT, 3+(Proportions.STAT_BAR_HEIGHT*3), 10, new Color(120, 80, 20), true, foodBarAnim, foodBarAnim, foodBarAnim, foodBarAnim);
		StatBar waterBar = new StatBar(new Thirst(null), 3+Proportions.STAT_BAR_HEIGHT, 3+(Proportions.STAT_BAR_HEIGHT*3)+(Proportions.STAT_BAR_HEIGHT_2), 10, Color.CYAN, true, waterBarAnim, waterBarAnim, waterBarAnim, waterBarAnim);
		StatBar tempBar = new StatBar(new Temperature(null), 3+Proportions.STAT_BAR_HEIGHT, 3+(Proportions.STAT_BAR_HEIGHT*3)+(Proportions.STAT_BAR_HEIGHT_2*2), 10, Color.YELLOW, true, tempBarAnim, tempBarAnim, tempBarAnim, tempBarAnim);
		
		Button compactor1 = new StatBarCompactor("SBC1", 3, 3+(Proportions.STAT_BAR_HEIGHT*3), Proportions.STAT_BAR_HEIGHT, Proportions.STAT_BAR_HEIGHT*2, 10, mainBarCompactorAnim, mainBarCompactorAnim, healthBar, energyBar, wellnessBar, foodBar, waterBar, tempBar) {
			@Override
			public void onInitialize() {
				permaY = y;
			}
			@Override
			public void runClick() {
				if(showing) {
					this.setY(statBars[0].getY());
					this.getWhileUpAnim().setY(statBars[0].getY());
					this.getWhileDownAnim().setY(statBars[0].getY());
					showing = false;
					for(StatBar each:statBars) {
						each.hide();
					}
				}else {
					this.setY(permaY);
					this.getWhileUpAnim().setY(permaY);
					this.getWhileDownAnim().setY(permaY);
					showing = true;
					for(StatBar each:statBars) {
						each.show();
					}
				}
			}
		};
		Button exit = new Button("Exit", false, false, 12, Window.width-Proportions.CORNER_BUTTON_WIDTH-Window.tinySpace(), Window.tinySpace(), Proportions.CORNER_BUTTON_WIDTH, Proportions.CORNER_BUTTON_WIDTH, 0, exitAnim, exitAnim) {
			@Override
			public void runClick() {
				Game.getInstance().stop();
			}
			@Override
			public void runOnHover() {
				setWhileDownAnim(exitAnimHover);
				setWhileUpAnim(exitAnimHover);
			}
			@Override
			public void runOnStopHover() {
				setWhileDownAnim(exitAnim);
				setWhileUpAnim(exitAnim);
			}
		};
		Button minimize = new Button("Minimize", false, false, 12, (Window.width-Proportions.CORNER_BUTTON_WIDTH*2)-Window.tinySpace(), Window.tinySpace(), Proportions.CORNER_BUTTON_WIDTH, Proportions.CORNER_BUTTON_WIDTH, 0, minimizeAnim, minimizeAnim) {
			@Override
			public void runClick() {
				Game.getInstance().getWindow().setExtendedState(JFrame.ICONIFIED);
			}
			@Override
			public void runOnHover() {
				setWhileDownAnim(minimizeAnimHover);
				setWhileUpAnim(minimizeAnimHover);
			}
			@Override
			public void runOnStopHover() {
				setWhileDownAnim(minimizeAnim);
				setWhileUpAnim(minimizeAnim);
			}
		};
		
		TextArea fps = new TextArea("fps", Window.width-(Proportions.CORNER_BUTTON_WIDTH*4), Proportions.CORNER_BUTTON_WIDTH*2+Window.smallSpace(), (int)(Proportions.CORNER_BUTTON_WIDTH*1.5), Proportions.CORNER_BUTTON_WIDTH, 0, 0, false, Main.getTextures().blankCursor) {
			@Override
			public void runOnUpdate() {
				clear();
				addLine(new TextBit(Color.ORANGE, Main.getFonts().custom(16, Font.BOLD), "FPS: "+Game.frames, null, null));
			}
		};
		fps.addLine(new TextBit(Color.ORANGE, Main.getFonts().custom(16, Font.BOLD), "FPS: "+Game.frames, null, null));
		TextArea tps = new TextArea("tps", Window.width-(Proportions.CORNER_BUTTON_WIDTH*4), Proportions.CORNER_BUTTON_WIDTH*2+Window.smallSpace()+fps.getHeight(), (int)(Proportions.CORNER_BUTTON_WIDTH*1.5), Proportions.CORNER_BUTTON_WIDTH, 0, 0, false, Main.getTextures().blankCursor) {
			@Override
			public void runOnUpdate() {
				clear();
				addLine(new TextBit(Color.ORANGE, Main.getFonts().custom(16, Font.BOLD), "TPS: "+Game.ticks, null, null));
			}
		};
		tps.addLine(new TextBit(Color.ORANGE, Main.getFonts().custom(16, Font.BOLD), "TPS: "+Game.ticks, null, null));
		
		infoBox = new TextArea("Info Box", (int)(Window.width/2.0-Proportions.STAT_BAR_WIDTH*.75), Window.tinySpace(), (int)(Proportions.STAT_BAR_WIDTH*1.5), (int)(Proportions.STAT_BAR_WIDTH/1.2), 0, 40, false, Main.getTextures().infoBoxBG);
		infoBox.setUnalterableFontSize(14);
		infoBox.deactivate();
		
		Weather weather = new Weather();
		
		thingManager.registerField(weather);
		
		statManager.registerStatBar(healthBar);
		statManager.registerStatBar(energyBar);
		statManager.registerStatBar(wellnessBar);
		statManager.registerStatBar(foodBar);
		statManager.registerStatBar(waterBar);
		statManager.registerStatBar(tempBar);
		
		textAreaManager.registerField(fps);
		textAreaManager.registerField(tps);
		textAreaManager.registerField(infoBox);
		
		buttonManager.registerButton(compactor1);
		buttonManager.registerButton(minimize);
		buttonManager.registerButton(exit);
	}

	public void changeGameState(GameStateType type) {
		isBuilding = true;
		this.bufferedType = type;
	}
}