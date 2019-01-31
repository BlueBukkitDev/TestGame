package dev.blue.rotu.gfx.utils;

import java.awt.image.BufferedImage;

public class Textures {
	public BufferedImage logo;
	public BufferedImage menubg;
	public BufferedImage inputField;
	public BufferedImage button;
	public BufferedImage buttonDown;
	public BufferedImage infoBoxBG;
	
	public BufferedImage exitButton;
	public BufferedImage exitButtonHover;
	public BufferedImage minimizeButton;
	public BufferedImage minimizeButtonHover;
	public BufferedImage settingsButton;
	public BufferedImage settingsButtonHover;
	public BufferedImage profileButton;
	public BufferedImage profileButtonHover;
	
	public BufferedImage standardCursor;
	public BufferedImage blankCursor;
	public BufferedImage penCursor;
	public BufferedImage typeCursor;
	
	public BufferedImage textArea;
	public BufferedImage textArea2;
	public BufferedImage errorLog;
	
	public BufferedImage healthBar;
	public BufferedImage energyBar;
	public BufferedImage wellnessBar;
	public BufferedImage foodBar;
	public BufferedImage waterBar;
	public BufferedImage tempBar;
	public BufferedImage statBarCompactor;
	
	public BufferedImage avatar;
	
	//////////////////////////////////////
	
	public BufferedImage tileGlow;
	
	//private BufferedImage dirtTile;
	public BufferedImage dirtTLQ;
	public BufferedImage dirtBLQ;
	public BufferedImage dirtBRQ;
	public BufferedImage dirtTRQ;
	private BufferedImage dirtRoundGrassTile;
	public BufferedImage dirtTLRGQ;
	public BufferedImage dirtBLRGQ;
	public BufferedImage dirtBRRGQ;
	public BufferedImage dirtTRRGQ;
	private BufferedImage dirtEdgeVertGrassTile;
	public BufferedImage dirtTLEVGQ;
	public BufferedImage dirtBLEVGQ;
	public BufferedImage dirtBREVGQ;
	public BufferedImage dirtTREVGQ;
	private BufferedImage dirtEdgeHorizGrassTile;
	public BufferedImage dirtTLEHGQ;
	public BufferedImage dirtBLEHGQ;
	public BufferedImage dirtBREHGQ;
	public BufferedImage dirtTREHGQ;
	private BufferedImage dirtCornerGrassTile;
	public BufferedImage dirtTLCGQ;
	public BufferedImage dirtBLCGQ;
	public BufferedImage dirtBRCGQ;
	public BufferedImage dirtTRCGQ;
	
	//private BufferedImage sandTile;
	public BufferedImage sandTLQ;
	public BufferedImage sandBLQ;
	public BufferedImage sandBRQ;
	public BufferedImage sandTRQ;
	private BufferedImage sandRoundGrassTile;
	public BufferedImage sandTLRGQ;
	public BufferedImage sandBLRGQ;
	public BufferedImage sandBRRGQ;
	public BufferedImage sandTRRGQ;
	private BufferedImage sandEdgeVertGrassTile;
	public BufferedImage sandTLEVGQ;
	public BufferedImage sandBLEVGQ;
	public BufferedImage sandBREVGQ;
	public BufferedImage sandTREVGQ;
	private BufferedImage sandEdgeHorizGrassTile;
	public BufferedImage sandTLEHGQ;
	public BufferedImage sandBLEHGQ;
	public BufferedImage sandBREHGQ;
	public BufferedImage sandTREHGQ;
	private BufferedImage sandCornerGrassTile;
	public BufferedImage sandTLCGQ;
	public BufferedImage sandBLCGQ;
	public BufferedImage sandBRCGQ;
	public BufferedImage sandTRCGQ;
	
	//private BufferedImage grassTile;
	public BufferedImage grassTLQ;
	public BufferedImage grassBLQ;
	public BufferedImage grassBRQ;
	public BufferedImage grassTRQ;
	private BufferedImage grassRoundDirtTile;
	public BufferedImage grassTLRDQ;
	public BufferedImage grassBLRDQ;
	public BufferedImage grassBRRDQ;
	public BufferedImage grassTRRDQ;
	
	public BufferedImage grassTile;
	public BufferedImage dirtTile;
	public BufferedImage sandTile;
	public BufferedImage stoneTile;
	public BufferedImage forestTile;
	public BufferedImage waterTile;
	
	public BufferedImage mytusPlant;
	public BufferedImage coralGrass;
	public BufferedImage dolorCactus;
	private BufferedImage ringLeafPlant;
	private BufferedImage ringLeafPlant2;
	private BufferedImage ringLeafPlant3;
	private BufferedImage ringLeafPlant4;
	private BufferedImage ringLeafPlant5;
	private BufferedImage ringLeafPlant6;
	private BufferedImage ringLeafPlant7;
	private BufferedImage ringLeafPlant8;
	private BufferedImage ringLeafPlant9;
	private BufferedImage ringLeafPlant10;
	private BufferedImage ringLeafPlant11;
	private BufferedImage ringLeafPlant12;
	private BufferedImage ringLeafPlant13;
	private BufferedImage ringLeafPlant14;
	private BufferedImage ringLeafPlant15;
	private BufferedImage ringLeafPlant16;
	private BufferedImage looseLeafGrass;
	private BufferedImage looseLeafGrass2;
	private BufferedImage looseLeafGrass3;
	private BufferedImage looseLeafGrass4;
	private BufferedImage looseLeafGrass5;
	private BufferedImage looseLeafGrass6;
	private BufferedImage looseLeafGrass7;
	private BufferedImage looseLeafGrass8;
	private BufferedImage looseLeafGrass9;
	public BufferedImage belinianAylflower;
	
	public BufferedImage rabbit;
	public BufferedImage rabbitDmg_1;
	public BufferedImage rabbitDmg_2;
	public BufferedImage rabbitDmg_3;
	public BufferedImage rabbitDmg_4;
	public BufferedImage rabbitDead;
	
	public BufferedImage fox;
	public BufferedImage foxDmg_1;
	public BufferedImage foxDmg_2;
	public BufferedImage foxDmg_3;
	public BufferedImage foxDmg_4;
	public BufferedImage foxDead;
	
	public BufferedImage rabbitBurrow;
	
	public void loadTextures() {
		logo = ImageLoader.loadImage("Logo2.png");//"world/maps/"+name+".png"
		menubg = ImageLoader.loadImage("menu/menuBG.png");
		inputField = ImageLoader.loadImage("ui/TextInputField.png");
		infoBoxBG = ImageLoader.loadImage("ui/infoBoxBG.png");
		
		exitButton = ImageLoader.loadImage("ui/cornerButtons/exitButton.png");
		exitButtonHover = ImageLoader.loadImage("ui/cornerButtons/exitButtonHover.png");
		minimizeButton = ImageLoader.loadImage("ui/cornerButtons/minimizeButton.png");
		minimizeButtonHover = ImageLoader.loadImage("ui/cornerButtons/minimizeButtonHover.png");
		settingsButton = ImageLoader.loadImage("ui/cornerButtons/settingsButton.png");
		settingsButtonHover = ImageLoader.loadImage("ui/cornerButtons/settingsButtonHover.png");
		profileButton = ImageLoader.loadImage("ui/cornerButtons/profileButton.png");
		profileButtonHover = ImageLoader.loadImage("ui/cornerButtons/profileButtonHover.png");
		
		button = ImageLoader.loadImage("ui/button.png");
		buttonDown = ImageLoader.loadImage("ui/buttonDown.png");
		
		standardCursor = ImageLoader.loadImage("default_cursor.png");
		blankCursor = ImageLoader.loadImage("blank_cursor.png");
		penCursor = ImageLoader.loadImage("pen_cursor.png");
		typeCursor = ImageLoader.loadImage("type_cursor.png");
		
		textArea = ImageLoader.loadImage("menu/textAreaBG.png");
		textArea2 = ImageLoader.loadImage("menu/textAreaBG2.png");
		errorLog = ImageLoader.loadImage("menu/errorLog.png");
		
		healthBar = ImageLoader.loadImage("ui/healthBar.png");
		energyBar = ImageLoader.loadImage("ui/energyBar.png");
		wellnessBar = ImageLoader.loadImage("ui/wellnessBar.png");
		foodBar = ImageLoader.loadImage("ui/foodBar.png");
		waterBar = ImageLoader.loadImage("ui/waterBar.png");
		tempBar = ImageLoader.loadImage("ui/tempBar.png");
		statBarCompactor = ImageLoader.loadImage("ui/statBarCompactor.png");
		
		avatar = ImageLoader.loadImage("avatar.png");
		
		tileGlow = ImageLoader.loadImage("tiles/tileGlow.png");
		
		grassTile = ImageLoader.loadImage("tiles/grass/grass.png");
		grassTLQ = grassTile.getSubimage(0, 0, grassTile.getWidth()/2, grassTile.getHeight()/2);
		grassBLQ = grassTile.getSubimage(0, grassTile.getHeight()/2, grassTile.getWidth()/2, grassTile.getHeight()/2);
		grassBRQ = grassTile.getSubimage(grassTile.getWidth()/2, grassTile.getHeight()/2, grassTile.getWidth()/2, grassTile.getHeight()/2);
		grassTRQ = grassTile.getSubimage(grassTile.getWidth()/2, 0, grassTile.getWidth()/2, grassTile.getHeight()/2);
		grassRoundDirtTile = ImageLoader.loadImage("tiles/grass/grassRoundDirt.png");
		grassTLRDQ = grassRoundDirtTile.getSubimage(0, 0, grassRoundDirtTile.getWidth()/2, grassRoundDirtTile.getHeight()/2);
		grassBLRDQ = grassRoundDirtTile.getSubimage(0, grassRoundDirtTile.getHeight()/2, grassRoundDirtTile.getWidth()/2, grassRoundDirtTile.getHeight()/2);
		grassBRRDQ = grassRoundDirtTile.getSubimage(grassRoundDirtTile.getWidth()/2, grassRoundDirtTile.getHeight()/2, grassRoundDirtTile.getWidth()/2, grassRoundDirtTile.getHeight()/2);
		grassTRRDQ = grassRoundDirtTile.getSubimage(grassRoundDirtTile.getWidth()/2, 0, grassRoundDirtTile.getWidth()/2, grassRoundDirtTile.getHeight()/2);
		
		dirtTile = ImageLoader.loadImage("tiles/dirt/dirt.png");
		dirtTLQ = dirtTile.getSubimage(0, 0, dirtTile.getWidth()/2, dirtTile.getHeight()/2);
		dirtBLQ = dirtTile.getSubimage(0, dirtTile.getHeight()/2, dirtTile.getWidth()/2, dirtTile.getHeight()/2);
		dirtBRQ = dirtTile.getSubimage(dirtTile.getWidth()/2, dirtTile.getHeight()/2, dirtTile.getWidth()/2, dirtTile.getHeight()/2);
		dirtTRQ = dirtTile.getSubimage(dirtTile.getWidth()/2, 0, dirtTile.getWidth()/2, dirtTile.getHeight()/2);
		dirtRoundGrassTile = ImageLoader.loadImage("tiles/dirt/dirtRoundGrass.png");
		dirtTLRGQ = dirtRoundGrassTile.getSubimage(0, 0, dirtRoundGrassTile.getWidth()/2, dirtRoundGrassTile.getHeight()/2);
		dirtBLRGQ = dirtRoundGrassTile.getSubimage(0, dirtRoundGrassTile.getHeight()/2, dirtRoundGrassTile.getWidth()/2, dirtRoundGrassTile.getHeight()/2);
		dirtBRRGQ = dirtRoundGrassTile.getSubimage(dirtRoundGrassTile.getWidth()/2, dirtRoundGrassTile.getHeight()/2, dirtRoundGrassTile.getWidth()/2, dirtRoundGrassTile.getHeight()/2);
		dirtTRRGQ = dirtRoundGrassTile.getSubimage(dirtRoundGrassTile.getWidth()/2, 0, dirtRoundGrassTile.getWidth()/2, dirtRoundGrassTile.getHeight()/2);
		dirtEdgeVertGrassTile = ImageLoader.loadImage("tiles/dirt/dirtEdgeVertGrass.png");
		dirtTLEVGQ = dirtEdgeVertGrassTile.getSubimage(0, 0, dirtEdgeVertGrassTile.getWidth()/2, dirtEdgeVertGrassTile.getHeight()/2);
		dirtBLEVGQ = dirtEdgeVertGrassTile.getSubimage(0, dirtEdgeVertGrassTile.getHeight()/2, dirtEdgeVertGrassTile.getWidth()/2, dirtEdgeVertGrassTile.getHeight()/2);
		dirtBREVGQ = dirtEdgeVertGrassTile.getSubimage(dirtEdgeVertGrassTile.getWidth()/2, dirtEdgeVertGrassTile.getHeight()/2, dirtEdgeVertGrassTile.getWidth()/2, dirtEdgeVertGrassTile.getHeight()/2);
		dirtTREVGQ = dirtEdgeVertGrassTile.getSubimage(dirtEdgeVertGrassTile.getWidth()/2, 0, dirtEdgeVertGrassTile.getWidth()/2, dirtEdgeVertGrassTile.getHeight()/2);
		dirtEdgeHorizGrassTile = ImageLoader.loadImage("tiles/dirt/dirtEdgeHorizGrass.png");
		dirtTLEHGQ = dirtEdgeHorizGrassTile.getSubimage(0, 0, dirtEdgeHorizGrassTile.getWidth()/2, dirtEdgeHorizGrassTile.getHeight()/2);
		dirtBLEHGQ = dirtEdgeHorizGrassTile.getSubimage(0, dirtEdgeHorizGrassTile.getHeight()/2, dirtEdgeHorizGrassTile.getWidth()/2, dirtEdgeHorizGrassTile.getHeight()/2);
		dirtBREHGQ = dirtEdgeHorizGrassTile.getSubimage(dirtEdgeHorizGrassTile.getWidth()/2, dirtEdgeHorizGrassTile.getHeight()/2, dirtEdgeHorizGrassTile.getWidth()/2, dirtEdgeHorizGrassTile.getHeight()/2);
		dirtTREHGQ = dirtEdgeHorizGrassTile.getSubimage(dirtEdgeHorizGrassTile.getWidth()/2, 0, dirtEdgeHorizGrassTile.getWidth()/2, dirtEdgeHorizGrassTile.getHeight()/2);
		dirtCornerGrassTile = ImageLoader.loadImage("tiles/dirt/dirtCornerGrass.png");
		dirtTLCGQ = dirtCornerGrassTile.getSubimage(0, 0, dirtCornerGrassTile.getWidth()/2, dirtCornerGrassTile.getHeight()/2);
		dirtBLCGQ = dirtCornerGrassTile.getSubimage(0, dirtCornerGrassTile.getHeight()/2, dirtCornerGrassTile.getWidth()/2, dirtCornerGrassTile.getHeight()/2);
		dirtBRCGQ = dirtCornerGrassTile.getSubimage(dirtCornerGrassTile.getWidth()/2, dirtCornerGrassTile.getHeight()/2, dirtCornerGrassTile.getWidth()/2, dirtCornerGrassTile.getHeight()/2);
		dirtTRCGQ = dirtCornerGrassTile.getSubimage(dirtCornerGrassTile.getWidth()/2, 0, dirtCornerGrassTile.getWidth()/2, dirtCornerGrassTile.getHeight()/2);
		
		sandTile = ImageLoader.loadImage("tiles/sand/sand.png");
		sandTLQ = sandTile.getSubimage(0, 0, sandTile.getWidth()/2, sandTile.getHeight()/2);
		sandBLQ = sandTile.getSubimage(0, sandTile.getHeight()/2, sandTile.getWidth()/2, sandTile.getHeight()/2);
		sandBRQ = sandTile.getSubimage(sandTile.getWidth()/2, sandTile.getHeight()/2, sandTile.getWidth()/2, sandTile.getHeight()/2);
		sandTRQ = sandTile.getSubimage(sandTile.getWidth()/2, 0, sandTile.getWidth()/2, sandTile.getHeight()/2);
		sandRoundGrassTile = ImageLoader.loadImage("tiles/sand/sandRoundGrass.png");
		sandTLRGQ = sandRoundGrassTile.getSubimage(0, 0, sandRoundGrassTile.getWidth()/2, sandRoundGrassTile.getHeight()/2);
		sandBLRGQ = sandRoundGrassTile.getSubimage(0, sandRoundGrassTile.getHeight()/2, sandRoundGrassTile.getWidth()/2, sandRoundGrassTile.getHeight()/2);
		sandBRRGQ = sandRoundGrassTile.getSubimage(sandRoundGrassTile.getWidth()/2, sandRoundGrassTile.getHeight()/2, sandRoundGrassTile.getWidth()/2, sandRoundGrassTile.getHeight()/2);
		sandTRRGQ = sandRoundGrassTile.getSubimage(sandRoundGrassTile.getWidth()/2, 0, sandRoundGrassTile.getWidth()/2, sandRoundGrassTile.getHeight()/2);
		sandEdgeVertGrassTile = ImageLoader.loadImage("tiles/sand/sandEdgeVertGrass.png");
		sandTLEVGQ = sandEdgeVertGrassTile.getSubimage(0, 0, sandEdgeVertGrassTile.getWidth()/2, sandEdgeVertGrassTile.getHeight()/2);
		sandBLEVGQ = sandEdgeVertGrassTile.getSubimage(0, sandEdgeVertGrassTile.getHeight()/2, sandEdgeVertGrassTile.getWidth()/2, sandEdgeVertGrassTile.getHeight()/2);
		sandBREVGQ = sandEdgeVertGrassTile.getSubimage(sandEdgeVertGrassTile.getWidth()/2, sandEdgeVertGrassTile.getHeight()/2, sandEdgeVertGrassTile.getWidth()/2, sandEdgeVertGrassTile.getHeight()/2);
		sandTREVGQ = sandEdgeVertGrassTile.getSubimage(sandEdgeVertGrassTile.getWidth()/2, 0, sandEdgeVertGrassTile.getWidth()/2, sandEdgeVertGrassTile.getHeight()/2);
		sandEdgeHorizGrassTile = ImageLoader.loadImage("tiles/sand/sandEdgeHorizGrass.png");
		sandTLEHGQ = sandEdgeHorizGrassTile.getSubimage(0, 0, sandEdgeHorizGrassTile.getWidth()/2, sandEdgeHorizGrassTile.getHeight()/2);
		sandBLEHGQ = sandEdgeHorizGrassTile.getSubimage(0, sandEdgeHorizGrassTile.getHeight()/2, sandEdgeHorizGrassTile.getWidth()/2, sandEdgeHorizGrassTile.getHeight()/2);
		sandBREHGQ = sandEdgeHorizGrassTile.getSubimage(sandEdgeHorizGrassTile.getWidth()/2, sandEdgeHorizGrassTile.getHeight()/2, sandEdgeHorizGrassTile.getWidth()/2, sandEdgeHorizGrassTile.getHeight()/2);
		sandTREHGQ = sandEdgeHorizGrassTile.getSubimage(sandEdgeHorizGrassTile.getWidth()/2, 0, sandEdgeHorizGrassTile.getWidth()/2, sandEdgeHorizGrassTile.getHeight()/2);
		sandCornerGrassTile = ImageLoader.loadImage("tiles/sand/sandCornerGrass.png");
		sandTLCGQ = sandCornerGrassTile.getSubimage(0, 0, sandCornerGrassTile.getWidth()/2, sandCornerGrassTile.getHeight()/2);
		sandBLCGQ = sandCornerGrassTile.getSubimage(0, sandCornerGrassTile.getHeight()/2, sandCornerGrassTile.getWidth()/2, sandCornerGrassTile.getHeight()/2);
		sandBRCGQ = sandCornerGrassTile.getSubimage(sandCornerGrassTile.getWidth()/2, sandCornerGrassTile.getHeight()/2, sandCornerGrassTile.getWidth()/2, sandCornerGrassTile.getHeight()/2);
		sandTRCGQ = sandCornerGrassTile.getSubimage(sandCornerGrassTile.getWidth()/2, 0, sandCornerGrassTile.getWidth()/2, sandCornerGrassTile.getHeight()/2);
		
		stoneTile = ImageLoader.loadImage("tiles/stone/stone.png");
		forestTile = ImageLoader.loadImage("tiles/forest/forest.png");
		waterTile = ImageLoader.loadImage("tiles/water/water.png");
		
		mytusPlant = ImageLoader.loadImage("entities/plants/plants/Mytus_Plant.png");
		coralGrass = ImageLoader.loadImage("entities/plants/plants/Coral_Grass.png");
		dolorCactus = ImageLoader.loadImage("entities/plants/plants/Dolor_Cactus.png");
		looseLeafGrass = ImageLoader.loadImage("entities/plants/plants/looseleaf/LooseLeaf_Grass.png");
		looseLeafGrass2 = ImageLoader.loadImage("entities/plants/plants/looseleaf/LooseLeaf_Grass_2.png");
		looseLeafGrass3 = ImageLoader.loadImage("entities/plants/plants/looseleaf/LooseLeaf_Grass_3.png");
		looseLeafGrass4 = ImageLoader.loadImage("entities/plants/plants/looseleaf/LooseLeaf_Grass_4.png");
		looseLeafGrass5 = ImageLoader.loadImage("entities/plants/plants/looseleaf/LooseLeaf_Grass_5.png");
		looseLeafGrass6 = ImageLoader.loadImage("entities/plants/plants/looseleaf/LooseLeaf_Grass_6.png");
		looseLeafGrass7 = ImageLoader.loadImage("entities/plants/plants/looseleaf/LooseLeaf_Grass_7.png");
		looseLeafGrass8 = ImageLoader.loadImage("entities/plants/plants/looseleaf/LooseLeaf_Grass_8.png");
		looseLeafGrass9 = ImageLoader.loadImage("entities/plants/plants/looseleaf/LooseLeaf_Grass_9.png");
		ringLeafPlant = ImageLoader.loadImage("entities/plants/plants/ringleaf/RingLeaf_Plant.png");
		ringLeafPlant2 = ImageLoader.loadImage("entities/plants/plants/ringleaf/RingLeaf_Plant_2.png");
		ringLeafPlant3 = ImageLoader.loadImage("entities/plants/plants/ringleaf/RingLeaf_Plant_3.png");
		ringLeafPlant4 = ImageLoader.loadImage("entities/plants/plants/ringleaf/RingLeaf_Plant_4.png");
		ringLeafPlant5 = ImageLoader.loadImage("entities/plants/plants/ringleaf/RingLeaf_Plant_5.png");
		ringLeafPlant6 = ImageLoader.loadImage("entities/plants/plants/ringleaf/RingLeaf_Plant_6.png");
		ringLeafPlant7 = ImageLoader.loadImage("entities/plants/plants/ringleaf/RingLeaf_Plant_7.png");
		ringLeafPlant8 = ImageLoader.loadImage("entities/plants/plants/ringleaf/RingLeaf_Plant_8.png");
		ringLeafPlant9 = ImageLoader.loadImage("entities/plants/plants/ringleaf/RingLeaf_Plant_9.png");
		ringLeafPlant10 = ImageLoader.loadImage("entities/plants/plants/ringleaf/RingLeaf_Plant_10.png");
		ringLeafPlant11 = ImageLoader.loadImage("entities/plants/plants/ringleaf/RingLeaf_Plant_11.png");
		ringLeafPlant12 = ImageLoader.loadImage("entities/plants/plants/ringleaf/RingLeaf_Plant_12.png");
		ringLeafPlant13 = ImageLoader.loadImage("entities/plants/plants/ringleaf/RingLeaf_Plant_13.png");
		ringLeafPlant14 = ImageLoader.loadImage("entities/plants/plants/ringleaf/RingLeaf_Plant_14.png");
		ringLeafPlant15 = ImageLoader.loadImage("entities/plants/plants/ringleaf/RingLeaf_Plant_15.png");
		ringLeafPlant16 = ImageLoader.loadImage("entities/plants/plants/ringleaf/RingLeaf_Plant_16.png");
		belinianAylflower = ImageLoader.loadImage("entities/plants/plants/Belinian_Aylflower.png");
		
		rabbit = ImageLoader.loadImage("entities/creatures/rabbit.png");
		rabbitDmg_1 = ImageLoader.loadImage("entities/creatures/rabbit_d1.png");
		rabbitDmg_2 = ImageLoader.loadImage("entities/creatures/rabbit_d2.png");
		rabbitDmg_3 = ImageLoader.loadImage("entities/creatures/rabbit_d3.png");
		rabbitDmg_4 = ImageLoader.loadImage("entities/creatures/rabbit_d4.png");
		rabbitDead = ImageLoader.loadImage("entities/creatures/rabbit_dead.png");
		
		fox = ImageLoader.loadImage("entities/creatures/fox.png");
		foxDmg_1 = ImageLoader.loadImage("entities/creatures/fox_d1.png");
		foxDmg_2 = ImageLoader.loadImage("entities/creatures/fox_d2.png");
		foxDmg_3 = ImageLoader.loadImage("entities/creatures/fox_d3.png");
		foxDmg_4 = ImageLoader.loadImage("entities/creatures/fox_d4.png");
		foxDead = ImageLoader.loadImage("entities/creatures/fox_dead.png");
		
		rabbitBurrow = ImageLoader.loadImage("entities/other/RabbitBurrow.png");
	}
	
	public BufferedImage ringleafCluster(int size) {
		switch(size){
		case 1:return ringLeafPlant;
		case 2:return ringLeafPlant2;
		case 3:return ringLeafPlant3;
		case 4:return ringLeafPlant4;
		case 5:return ringLeafPlant5;
		case 6:return ringLeafPlant6;
		case 7:return ringLeafPlant7;
		case 8:return ringLeafPlant8;
		case 9:return ringLeafPlant9;
		case 10:return ringLeafPlant10;
		case 11:return ringLeafPlant11;
		case 12:return ringLeafPlant12;
		case 13:return ringLeafPlant13;
		case 14:return ringLeafPlant14;
		case 15:return ringLeafPlant15;
		case 16:return ringLeafPlant16;
		default:return ringLeafPlant;
		}
	}
	public BufferedImage looseleafCluster(int size) {
		switch(size){
		case 1:return looseLeafGrass;
		case 2:return looseLeafGrass2;
		case 3:return looseLeafGrass3;
		case 4:return looseLeafGrass4;
		case 5:return looseLeafGrass5;
		case 6:return looseLeafGrass6;
		case 7:return looseLeafGrass7;
		case 8:return looseLeafGrass8;
		case 9:return looseLeafGrass9;
		default:return looseLeafGrass;
		}
	}
}
