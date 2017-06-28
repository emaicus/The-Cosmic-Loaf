/**
 * 
 */
package com.skidgame.cosmicloaf.game;

import java.awt.Font;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import com.skidgame.cosmicloaf.CosmicMain;
import com.skidgame.cosmicloaf.Level;
import com.skidgame.cosmicloaf.systems.AiInputSystem;
import com.skidgame.cosmicloaf.systems.InputSystem;
import com.skidgame.cosmicloaf.systems.InteractionSystem;
import com.skidgame.cosmicloaf.systems.RenderSystem;
//import com.skidgame.cosmicloaf.systems.AiInputSystem;
import com.skidgame.cosmicloaf.assets.Assets;
import com.skidgame.cosmicloaf.components.Coordinate;
import com.skidgame.cosmicloaf.components.Entity;
import com.skidgame.cosmicloaf.components.GUIDataComponent;
import com.skidgame.cosmicloaf.components.GUIElementTypes;
import com.skidgame.cosmicloaf.components.Position;
import com.skidgame.cosmicloaf.components.Trigger;
import com.skidgame.cosmicloaf.constants.EmotionEnum;
import com.skidgame.cosmicloaf.game.SkidmoreGameWorldLoader;

/**
 * @author Theko Lekena
 *
 */

public class TheCosmicLoafGame extends Game{

	public final byte MAIN_MENU = 0;
	public final byte IN_GAME = 1;
	public final byte GAME_MENU = 2;
	public final byte CUTSCENE = 3;
	
	public int currentWidth;
	public int currentHeight;
	
	private final int SQUARE_HEIGHT = 32;
	private final int SQUARE_WIDTH = 32;
	private Level currentLevel;
	public final static int walken = 1;
	private final int HASH_DEFINITION = 32; 
	public byte state;
	private SaveFile sf;
	
	public ArrayList<Entity> mainMenu = new ArrayList<Entity>();
	public ArrayList<Entity> gameMenu = new ArrayList<Entity>();
	public static int menuOptionSelected;
	
	public static Font font;
	public static TrueTypeFont ttf;
	
	public GUI gui;
	private Camera gameCamera;
	private Camera menuCamera;
//	private Camera testCamera;

	public boolean displayTextBox = false;
	private String currentText = "";
	
	public Sound currentMusic;
	
	/** The dialog tree that we are currently evaluating	 */
	private DialogTree currentDialogTree;
	
	/**The dialog that is currently playing	 */
	private Dialog currentDialog; //Only one voice can play at a time (seems reasonable, actually).

	/**The Entity that was clicked on. */
	private Entity responder;
	
	/**The Entity that clicked interacted */
	private Entity initiator;
	
	/**Freezes the player if necessary (Not used by dialog)	 */
	public boolean freezePlayer;
	
	/**states whether or not the player is evaluating a dialog menu	 */
	public boolean inDialogMenu;
	
	/**The dialog menu the player is evaluating.	 */
	private ArrayList<Entity> currentDialogMenu;
	
	/**Keeps track of the switch between initiator and responder talking.	 */
	private boolean initiatorTalking;
	
	/** Menu Mike is a brave and valiant Entity, who enables initial menu interaction.	 */
	private Entity MenuMike;
	
	private ArrayList<Entity> mikesArrayList = new ArrayList<Entity>();
	
	/** Flushed once per tick.*/
	private ArrayList<Entity> toDestroy = new ArrayList<Entity>();
	
	/** Added once per tick. */
	private ArrayList<Entity> toAdd = new ArrayList<Entity>();
	
	/**Added once per tick.	 */
	private ArrayList<Trigger> triggersToAdd = new ArrayList<Trigger>();

	
	public TheCosmicLoafGame()
	{

		super();
		Assets.LoadAssets(); 
		font = new Font("Verdana", Font.BOLD, 16);
	    ttf = new TrueTypeFont(font, true);
		this.state = MAIN_MENU;
		mainMenu = Assets.loadMainMenu(this);
		gameMenu = Assets.loadGameMenu(this);
		sf = new SaveFile();
		Keyboard.enableRepeatEvents(true);
		menuCamera = new Camera(this);
		MenuMike = SkidmoreGameWorldLoader.loadMenuMike();
		mikesArrayList.add(MenuMike);
		currentWidth = CosmicMain.width;
		currentHeight = CosmicMain.height;
		try {
			currentMusic = new Sound("SFX/Market_night_m.wav");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void loadLevel(int level, SaveFile saveFile)
	{
		gameCamera = new Camera(CosmicMain.height / 4, CosmicMain.width / 4, CosmicMain.width, CosmicMain.height, 0, 0, true, null, this, 128, 128, 1f);
		currentLevel = SkidmoreGameWorldLoader.loadLevel(level, this, saveFile, gameCamera, true);
		loadGUI(level);
		gameCamera.track(currentLevel.getPlayer().get(0));
		gameCamera.center();
		gameCamera.computeVisible();
				
	}
	
	

	
	
	public void getInput()
	{
		
		if(this.state == IN_GAME)
		{
			if(!inDialogMenu)
			{
				InteractionSystem.process(currentLevel.getEntities(), this, currentLevel, gameCamera);
				InputSystem.process(currentLevel.getEntities(), this, currentLevel, gameCamera);
				AiInputSystem.updatePaths(currentLevel.getEntities(), currentLevel, gameCamera, this);
			}
			else
			{
				InputSystem.processMenu(currentDialogMenu, this, currentLevel.getPlayer().get(0));
			}
		}
		else if(this.state == MAIN_MENU)
		{
			if(currentLevel == null)
			{
				InputSystem.processMenu(mainMenu, this, MenuMike);
			}
			else
			{
				InputSystem.processMenu(mainMenu, this, currentLevel.getPlayer().get(0));
			}
		}
		else if(this.state == GAME_MENU)
		{
			InputSystem.processMenu(gameMenu, this, currentLevel.getPlayer().get(0));
		}
		
		
	}
	
	public void update()
	{
		if(currentLevel == null)
		{
			InputSystem.update(mikesArrayList);
		}
		else
		{
			InputSystem.update(currentLevel.getPlayer());
			AiInputSystem.process(currentLevel.getEntities(), this, currentLevel, gameCamera);
		}
		if(this.state == IN_GAME)
		{
			if(triggersToAdd.size() != 0)
			{
				for(Trigger t : triggersToAdd)
				{
					currentLevel.getTriggers().add(t);
				}
				triggersToAdd = new ArrayList<Trigger>();
			}
			gameCamera.update();
		//	testCamera.update();
			gui.update(this, menuCamera);
			updateTriggers();
			
			if(toDestroy.size() > 0)
			{
				flushEntities();
			}
			
			if(toAdd.size() > 0)
			{
				performAddEntities();
			}
			
			soundUpdate();
		}
		
	//TODO Fix AI.
	//	aiInputSystem.updatePaths(currentLevel.getEntities(), currentLevel);
	}
	
	


	

	private void updateTriggers() {
		for(Trigger trig : currentLevel.getTriggers())
		{
			trig.update(this);
			//System.out.println("Testing");
			if(trig.readyToFire && !trig.Fired)
			{
				System.out.println("Fire!");
				trig.fire(this, gameCamera);
				trig.active = false; //For now, we just turn off a trigger after it fires.
			}	
		}
	}


	public void render()
	{
		if(this.state == IN_GAME)
		{

			
			
				RenderSystem.renderTiledMap(this, gameCamera);
				RenderSystem.renderTerrain(currentLevel.getTerrain(), Assets.getSpriteSheets(), gameCamera, this);
				RenderSystem.process(currentLevel.getEntityBySheet(), Assets.getSpriteSheets(), gameCamera, this);
				
				handleDialogAndGUI();
				
				 ARBShaderObjects.glUseProgramObjectARB(CosmicMain.program);
			        GL11.glLoadIdentity();
			        GL11.glTranslatef(0.0f, 0.0f, -10.0f);
			        GL11.glColor3f(1.0f, 1.0f, 1.0f);//white
			 
			        GL11.glBegin(GL11.GL_QUADS);
			        GL11.glVertex3f(-1.0f, 1.0f, 0.0f);
			        GL11.glVertex3f(1.0f, 1.0f, 0.0f);
			        GL11.glVertex3f(1.0f, -1.0f, 0.0f);
			        GL11.glVertex3f(-1.0f, -1.0f, 0.0f);
			        GL11.glEnd();
			        ARBShaderObjects.glUseProgramObjectARB(0);

				
			
		}
		else if(this.state == MAIN_MENU)
		{
			RenderSystem.process(mainMenu, Assets.getSpriteSheets(), menuCamera, this);
		}
		else if(this.state == GAME_MENU)
		{
			RenderSystem.process(gameMenu, Assets.getSpriteSheets(), menuCamera, this);
		}
	}

	/**
	 * Renders/play's dialog and renders the GUI (unfortunately, the GUI must be rendered in a very specific order,
	 * so this must be done all together in one method).
	 */
	private void handleDialogAndGUI() 
	{
		if(currentDialog!= null && currentDialog.getInitiatorDialog() != null)
		{
			if((!currentDialog.getInitiatorDialog().playing()) && initiatorTalking)
			{
				initiatorTalking = false;
				if(currentDialog.getResponderDialog() != null)
				{
					currentDialog.getInitiatorDialog().stop();

					currentDialog.getResponderDialog().play();
					currentText = currentDialog.getResponderSubtitle();
				}
			}
			
			if (currentDialog.getInitiatorDialog().playing() && initiatorTalking)
			{
				if(currentDialog.getResponderDialog() != null && currentDialog.getResponderDialog().playing())
				{
					System.out.println("They are both talking at the same time.");
//					currentDialog.getResponderDialog().stop();
				}
			}
			
			if(currentDialog.getInitiatorDialog().playing() && !initiatorTalking)
			{
				currentDialog.getInitiatorDialog().stop();
			}
			
			
		}
		
		float widthFloat = currentWidth;
		float scale = (float) ((widthFloat/1920));
		float headScale = scale * .65f;
		float boxHeight = (float) (400 * scale *.85);
		
		if(currentDialog!= null && ((currentDialog.getInitiatorDialog() != null && currentDialog.getInitiatorDialog().playing()) || (currentDialog.getResponderDialog() != null && currentDialog.getResponderDialog().playing())))
		{
			
			Graphics g = CosmicMain.myGraphics;					
			Rectangle r = new Rectangle(0, currentHeight - boxHeight, currentWidth, boxHeight);
			Color c = new Color(0, 0, 0, .75f);
			g.setColor(c);
			g.fill(r);
		}
		
		RenderSystem.renderGUIText(gui.getElements(), menuCamera/* displayTextBox, currentText*/);
		
		if(inDialogMenu)
		{
			RenderSystem.process(currentDialogMenu, Assets.getSpriteSheets(), menuCamera, this);
		}
		
		
		if(currentDialog!= null && ((currentDialog.getInitiatorDialog() != null && currentDialog.getInitiatorDialog().playing()) || (currentDialog.getResponderDialog() != null && currentDialog.getResponderDialog().playing())))
		{

			Image myFace = responder.getArt().faces.get(currentDialog.getMyEmotion());
			if(myFace == null)
			{
				myFace = responder.getArt().faces.get(EmotionEnum.NEUTRAL);
				if(myFace != null)
				{
					if(currentDialog.getResponderDialog().playing())
					{
				//		System.out.println("Render 1");
						myFace.draw((currentWidth- (myFace.getWidth() *headScale)), currentHeight - (((float)myFace.getHeight())* headScale), headScale);
					}
					else
					{
				//		System.out.println("Render 2");
						myFace.draw((currentWidth- (myFace.getWidth() *headScale)), currentHeight - (((float)myFace.getHeight())* headScale), headScale, Color.gray);
					}
				}
			}
			else
			{
				if(currentDialog.getResponderDialog().playing())
				{
				//	System.out.println("Render 3");
					myFace.draw((currentWidth- (myFace.getWidth() *headScale)), currentHeight - (((float)myFace.getHeight())* headScale), headScale);
				}
				else
				{
				//	System.out.println("Render 4");
					myFace.draw((currentWidth- (myFace.getWidth() *headScale)), currentHeight - (((float)myFace.getHeight())* headScale), headScale, Color.gray);
				}
			}
			
			if(initiator != null)
			{
				Image playerFace = initiator.getArt().faces.get(currentDialog.getMyEmotion());
				if(playerFace == null)
				{
					System.out.println("Trying to get emotion");
					playerFace = initiator.getArt().faces.get(EmotionEnum.NEUTRAL);
					if(playerFace != null)
					{
						if(currentDialog.getInitiatorDialog().playing())
						{
							playerFace.draw(0, currentHeight - (((float)playerFace.getHeight())* headScale), headScale);
						}
						else 
						{
							playerFace.draw(0, currentHeight - (((float)playerFace.getHeight())* headScale), headScale, Color.gray);
						}
					}
				}
				else
				{
					if(currentDialog.getInitiatorDialog() != null && currentDialog.getInitiatorDialog().playing())
					{
						playerFace.draw(0, currentHeight - (((float)playerFace.getHeight())* headScale), headScale);
					}
					else 
					{
						playerFace.draw(0, currentHeight - (((float)playerFace.getHeight())* headScale), headScale, Color.gray);
					}				
				}
			}
			
			
		//	currentText = currentDialog.getResponderSubtitle();
		//	System.out.println(currentText);
			displayTextBox = true;
			
		}
		else
		{
			if(currentDialog != null)
			{
				this.wipeDialogMenu();
			}
		}
		
	}


	public void soundUpdate()
	{
		if(currentMusic != null)
		{
			if(!currentMusic.playing())
			{
				currentMusic.playAt(1f,.15f, 1, 1, 1);
			}
		}
		
		
		
	}
	
	
	public Level getCurrentLevel()
	{
		return this.currentLevel;
	}
	
	public void saveGame()
	{
		sf.writeOutFile("res/save.txt", this, gameCamera);
	}
	
	public void loadGame()
	{
		sf.readInFile("res/save.txt");
		System.out.println(sf.getCurrentLevel());
		System.out.println(sf.getMainCharacterBox());
		loadLevel(sf.getCurrentLevel(), sf);
		state = IN_GAME;

	}
	private void loadGUI(int level) {
		ArrayList<Entity> elements = new ArrayList<Entity>();
		switch (level)
		{
			case 1:
			{
				Position p = new Position(this, new Coordinate(0,0));
				GUIDataComponent gdc = new GUIDataComponent("0", GUIElementTypes.PLAYER_BOX_COORD);
				Entity e = new Entity(p, gdc);
				GUIDataComponent textBox = new GUIDataComponent("test text", GUIElementTypes.TEXT_BOX);
				Entity e2 = new Entity(textBox);
				Position fpsPos = new Position(this, new Coordinate(0, 32));
				GUIDataComponent fps = new GUIDataComponent("0", GUIElementTypes.FPS, 0, System.nanoTime());
				Entity e3 = new Entity(fps, fpsPos);
				elements.add(e);
				elements.add(e2);
				elements.add(e3);
				break;
			}
			default:
			{
				Position p = new Position(this, new Coordinate(0,0));
				GUIDataComponent gdc = new GUIDataComponent("0", GUIElementTypes.PLAYER_BOX_COORD);
				Entity e = new Entity(p, gdc);
				GUIDataComponent textBox = new GUIDataComponent("test text", GUIElementTypes.TEXT_BOX);
				Entity e2 = new Entity(textBox);
				elements.add(e);
				elements.add(e2);
				break;
			}		
		}
		
		gui = new GUI(elements);
	}

	public void closeRequested() 
	{
		//TODO request save.
		System.exit(0);
	}
	
	public SaveFile getSaveFile()
	{
		return this.sf;
	}


	public void removeEntity(Entity me) {
		toDestroy.add(me);
	}
	
	
	private void flushEntities()
	{
		this.currentLevel.removeEntities(toDestroy, this, gameCamera);
		toDestroy = new ArrayList<Entity>();
	}


	public void addEntity(Entity e) {
		toAdd.add(e);	
	}
	
	private void performAddEntities()
	{
		System.out.println("Performing add entities.");
		currentLevel.addEntities(toAdd, this, gameCamera);
		toAdd = new ArrayList<Entity>();
	}
	
	public void addTrigger(Trigger t)
	{
		triggersToAdd.add(t);
	}
	
	public int getAdjustedHashDefinitions(Camera c)
	{
		return (int) (this.HASH_DEFINITION * c.getZoom());
	}
	
	public int getAdjustedSquareWidth(Camera c)
	{
		return (int) (this.SQUARE_WIDTH * c.getZoom());
	}
	
	public int getAdjustedSquareHeight(Camera c)
	{
		return (int) (this.SQUARE_HEIGHT * c.getZoom());
	}


	public Camera getGameCamera() {
		return this.gameCamera;
	}


	public int getLiteralSquareHeight() {
		return this.SQUARE_HEIGHT;
	}
	public int getLiteralSquareWidth() {
		return this.SQUARE_WIDTH;
	}


	public int getLiteralHashDefinition() {
		return this.HASH_DEFINITION;
	}
	
	public void processDialogOptions(Entity target)
	{
		processDialogOptions(target, null);
	}
	
	public void processDialogOptions(Entity target, Entity targeting)
	{
		this.responder = target;
		DialogTree tree = target.getInteractible().getMyTree();
		currentDialogMenu = Assets.generateDialogMenu(tree, this);
		this.currentDialogTree = tree;
		this.inDialogMenu = true;
		this.initiator = targeting;
	}
	
	public void setCurrentDialog(Dialog dialog)
	{
		if(currentDialog != null)
		{
			if(currentDialog.getInitiatorDialog() != null && currentDialog.getInitiatorDialog().playing())
			{
				this.currentDialog.getInitiatorDialog().stop();
			}
			if(currentDialog.getResponderDialog() != null && currentDialog.getResponderDialog().playing())
			{
				this.currentDialog.getResponderDialog().stop();
			}
		}
		currentDialog = dialog;
	}
	
	public void playCurrentDialog()
	{
		System.out.println("Playing dialog.");
		if(currentDialog != null)
		{
			if(currentDialog.getInitiatorDialog() != null && currentDialog.getInitiatorDialog().playing())
			{
				this.currentDialog.getInitiatorDialog().stop();
				System.out.println("Stopped initiator");
			}
			if(currentDialog.getResponderDialog() != null && currentDialog.getResponderDialog().playing())
			{
				this.currentDialog.getResponderDialog().stop();
				System.out.println("Stopped responder");
			}
		}
		initiatorTalking = false;

		
		if(currentDialog.getInitiatorDialog() != null)
		{
			if(!this.currentDialog.getInitiatorDialog().playing())
			{
				this.currentDialog.getInitiatorDialog().play();
				initiatorTalking = true;
				this.currentText = currentDialog.getInitiatorSubtitle();
			}
		}
		else if(currentDialog.getResponderDialog() != null)
		{
			if(!this.currentDialog.getResponderDialog().playing())
			{
				currentDialog.getResponderDialog().play();
				this.currentText = currentDialog.getResponderSubtitle();

			}
		}
	}
	
	public DialogTree getCurrentDialogTree()
	{
		return this.currentDialogTree;
	}
	
	public Entity getTargetedEntity()
	{
		return this.responder;
	}
	
	public void setTargetedEntity(Entity e)
	{
		this.responder = e;
	}
	
	public void wipeDialogMenu()
	{
//		if(currentDialog != null)
//		{
//			if(currentDialog.getInitiatorDialog() != null)
//			{
//				if(currentDialog.getInitiatorDialog().playing())
//				{
//					currentDialog.getInitiatorDialog().stop();
//				}
//			}
//			if(currentDialog.getResponderDialog() != null)
//			{
//				if(currentDialog.getResponderDialog().playing())
//				{
//					currentDialog.getResponderDialog().stop();
//				}
//			}
//		}
		
		//currentDialogTree = null;
		
		//currentDialog = null; //Only one voice can play at a time (seems reasonable, actually).

		//responder = null;
		
		//initiator = null;
		
		
		//inDialogMenu = false;
		
		//currentDialogMenu = null;
		
		currentText = "";
	}

	public String getCurrentText()
	{
		return this.currentText;
	}
	
	public void setCurrentText(String currentText)
	{
		this.currentText = currentText;
	}
}

