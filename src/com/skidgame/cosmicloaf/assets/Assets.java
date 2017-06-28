package com.skidgame.cosmicloaf.assets;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import com.skidgame.cosmicloaf.components.AnimationType;
import com.skidgame.cosmicloaf.components.Art;
import com.skidgame.cosmicloaf.components.ButtonComponent;
import com.skidgame.cosmicloaf.components.Coordinate;
import com.skidgame.cosmicloaf.components.Entity;
import com.skidgame.cosmicloaf.components.Position;
import com.skidgame.cosmicloaf.constants.EmotionEnum;
import com.skidgame.cosmicloaf.game.ButtonCode;
import com.skidgame.cosmicloaf.game.DialogChain;
import com.skidgame.cosmicloaf.game.DialogTree;
import com.skidgame.cosmicloaf.game.TheCosmicLoafGame;


//import org.newdawn.slick.SpriteSheet;

/**
 * This class loads in the spriteSheets necessary to run the game. 
 * @author Evan
 *
 */
public class  Assets 
{ 
	private static ArrayList<SpriteSheet> spriteSheets = new ArrayList<SpriteSheet>();
	//TODO Create a sprite sheet
	//TODO getter
	//public static ArrayList<SpriteSheet> spriteSheets = new ArrayList(); 
	public final static int talkerSheet = 0;
	public final static int talkerX = 2;
	public final static int talkerY = 0;
	public static HashMap<String, Art> loadedArt;
	
	
	public static void LoadAssets(/*int levelNumber*/)
	{
		SpriteSheet zero = null;
		SpriteSheet one = null;
		SpriteSheet destroyedDefault = null;
		SpriteSheet grass = null;
		try {
			zero = new SpriteSheet("res/testSheet.png", 32, 32);
			one = new SpriteSheet("res/sheet4.png", 32, 32);
			destroyedDefault = new SpriteSheet("res/animated.png",32,32);
			grass = new SpriteSheet("res/Grass1.png", 32, 32);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			System.out.println("Bad 1");
			e.printStackTrace();
		}
		spriteSheets.add(zero);
		spriteSheets.add(one);
		spriteSheets.add(destroyedDefault);
		spriteSheets.add(grass);
		
		
		ArrayList<String> manifests = new ArrayList<String>();
		manifests.add("res/manifests/FEMALE_SPRITESHEET.txt");
		manifests.add("res/manifests/MALE_SPRITESHEET.txt");
		manifests.add("res/manifests/TSARNA_SPRITESHEET.txt");
		loadedArt = loadArt(manifests);
	}
	/**
	 * Static method to get the sprite sheets currently loaded
	 * 
	 * @return ArrayList<SpriteSheet>
	 * 
	 * @see #LoadAssets()
	 */
	public static ArrayList<SpriteSheet> getSpriteSheets()
	{
		return spriteSheets;
	}
	public static ArrayList<Entity> loadMainMenu(TheCosmicLoafGame game)
	{
		ArrayList<Entity> MainMenu = new ArrayList<Entity>();
		Art b1Art = new Art(0,0,0);
		Position b1Pos = new Position(game, new Coordinate(0,0));
		b1Pos.setVisible(true);
		ButtonComponent b1bc = new ButtonComponent(ButtonCode.START_GAME.ordinal(), "Start Game (w: up, s: down, e: enter, esc: ingame menu)", 0, 32, 0);
		Entity button1 = new Entity(b1Art, b1Pos, b1bc);
		Art b2Art = new Art(0,0,0);
		Position b2Pos = new Position(game, new Coordinate(0,32));
		b2Pos.setVisible(true);
		ButtonComponent b2bc = new ButtonComponent(ButtonCode.LOAD_LEVEL.ordinal(), "Load Level From Tiled", 2, 32, 0);
		Entity button2 = new Entity(b2Art, b2Pos, b2bc);
		Art b3Art = new Art (5,0,0);
		Position b3Pos = new Position(game, new Coordinate(0,64));
		b3Pos.setVisible(true);
		ButtonComponent b3bc = new ButtonComponent(ButtonCode.LOAD_GAME.ordinal(), "Load Game", 0, 32, 0);
		Entity button3 = new Entity(b3Art, b3Pos, b3bc);
		MainMenu.add(button1);
		MainMenu.add(button2);
		MainMenu.add(button3);
		return MainMenu;
	}

	public static ArrayList<Entity> loadGameMenu(TheCosmicLoafGame game) { 
		ArrayList<Entity> gameMenu = new ArrayList<Entity>();

		Art b1Art = new Art(6,0,1);
		Position b1Pos = new Position(game, new Coordinate(0,0));
		b1Pos.setVisible(true);
		ButtonComponent b1bc = new ButtonComponent(ButtonCode.RESUME_GAME.ordinal(), "Resume Game", 0, 32, 0);
		Entity button1 = new Entity(b1Art, b1Pos, b1bc);
		Art b2Art = new Art(2,0,1);
		Position b2Pos = new Position(game, new Coordinate(0,32));
		b2Pos.setVisible(true);
		ButtonComponent b2bc = new ButtonComponent(ButtonCode.SAVE_GAME.ordinal(), "Save Game", 0, 32, 0);
		Entity button2 = new Entity(b2Art, b2Pos, b2bc);
		Art b3Art = new Art(3,0,1);
		Position b3Pos = new Position(game, new Coordinate(0,64));
		b3Pos.setVisible(true);
		ButtonComponent b3bc = new ButtonComponent(ButtonCode.EXIT_GAME.ordinal(), "Exit Game", 0, 32, 0);
		Entity button3 = new Entity(b3Art, b3Pos, b3bc);
		gameMenu.add(button1);
		gameMenu.add(button2);
		gameMenu.add(button3);
		return gameMenu;
	}

	public static ArrayList<Entity> generateDialogMenu(DialogTree tree, TheCosmicLoafGame game) {
		ArrayList<Entity> dialogMenu = new ArrayList<Entity>();

		int i = 0;
		for(DialogChain currentChain : tree.getChains())
		{
			// Art tempArt = new Art(6,0,1);
			Position b1Pos = new Position(game, new Coordinate(0,96 + 32*i));
			b1Pos.setVisible(true);
			ButtonComponent b1bc = new ButtonComponent(ButtonCode.DIALOG_BUTTON.ordinal(), currentChain.nextDialogPrompt(), i, 32, 0);
			Entity button1 = new Entity( b1Pos, b1bc);
			dialogMenu.add(button1);
			i++;
		}
		return dialogMenu;
	}
	/**
	 * Takes in an arrayList of spritesheets and an arrayList of url's to text file manifest. Each line of a manifest
	 * represents the name of a specific art set, and will be used as a mapping for the Hashmap that is to be returned.
	 * This way, we can automate the process of creating loading animated art into the game.
	 * @param sheets
	 * @param manifests
	 * @return 
	 * @return
	 */
	public static HashMap<String, Art> loadArt(ArrayList<String> manifests)
	{
		HashMap<String, Art> artHash = new HashMap<String, Art>();
		for(String manifest : manifests)
		{
			BufferedReader reader = null; //Build a reader to read through the manifest.
			try {
				FileReader f = new FileReader(manifest);
				reader = new BufferedReader(f);
				System.out.println("Opened");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.err.println("Failed to open. " + manifest);
				e.printStackTrace();
			}
			
			System.out.println(manifest);
			String[] pathStart = manifest.split("[.]"); //will split res/manifest/PATH.txt into manifest/PATH and txt
			String baseName[] = pathStart[0].split("/"); //will split res/manifest/PATH into res manifest and PATH
			String finalPath = "res/spritesheets/" + baseName[2] + ".png"; //creates the string res/spritesheets/PATH.png 

			System.err.println(finalPath);
			finalPath.trim();
			SpriteSheet temp = null;
			try {
				temp = new SpriteSheet(finalPath, 32, 32); //Find the right spritesheet based on the manifest name.
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				System.err.println("Couldn't find " + finalPath);
				e.printStackTrace();
			}
			int tempNum = 0;
			if(temp!= null)
			{
				spriteSheets.add(temp);
				tempNum = spriteSheets.indexOf(temp);
			}
			int spritesToLoad = 0;
			ArrayList<String> spriteInfo = new ArrayList<String>();
			int xDimension = 0;
			int yDimension = 0;
			try {
				String spriteNumber = reader.readLine().split(":")[1]; //turns Size:# into #
				spritesToLoad = Integer.parseInt(spriteNumber);
				String xDim = reader.readLine().split(":")[1]; //turns xDimension:# into #
				xDimension = Integer.parseInt(xDim);
				String yDim = reader.readLine().split(":")[1]; //turns yDimension:# into # 
				yDimension = Integer.parseInt(yDim);

				
				
				for(int i = 0; i < spritesToLoad; i++) //this will generate a bunch of data strings of the form spriteName~face1Type:face1Path@face2Type:faceTwoPath@etc.
				{
					String test = "";
					while(!test.equals("<Sprite>"))
					{
						test = reader.readLine().trim();;
					}
					String data = reader.readLine() + "~"; //This should be the name.
					while(!test.equals("<Faces>"))
					{
						test = reader.readLine().trim();;
					}
					test = reader.readLine().trim();;
					while(!test.equals("</Faces>"))
					{
						if(test != "")
						{
							data+=test + "@";
						}
							
						test = reader.readLine().trim();;

					}
					spriteInfo.add(data);
					reader.readLine(); //Skips </Sprite>
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			for(int i = 0; i < spritesToLoad; i++) //Now its time to build the art using the arraylist of string info and the info that we got from the manifest
			{
				for(int x = 0; x < xDimension; x++)
				{
					for(int y = 0; y < yDimension; y++)
					{
						//TODO wrong vals
						int xIndent = x * 3;
						int yIndent = y * 4;
						Art tempArt = new Art(xIndent +0, yIndent +0, tempNum); 

						Image right[] = new Image[3];
						right[0] = temp.getSubImage(xIndent + 0, yIndent + 2); //Look right
						right[1] = temp.getSubImage(xIndent +1, yIndent + 2); //walk right 1
						right[2] = temp.getSubImage(xIndent +2, yIndent + 2); //walk right 2
						tempArt.setRight(xIndent + 0, yIndent + 2);
						Image down[] = new Image[3];
						down[0] = temp.getSubImage(xIndent +0, yIndent +0); //Look down
						down[1] = temp.getSubImage(xIndent +1, yIndent +0); //walk down 1
						down[2] = temp.getSubImage(xIndent +2, yIndent +0); //walk down 2
						tempArt.setStand(xIndent +0, yIndent +0);
						Image left[] = new Image[3];
						left[0] = temp.getSubImage(xIndent +0, yIndent +3); //Look left
						left[1] = temp.getSubImage(xIndent +1, yIndent +3); //walk left 1
						left[2] = temp.getSubImage(xIndent +2, yIndent +3); //walk left 2
						Image up[] = new Image[3];
						up[0] = temp.getSubImage(xIndent +0, yIndent +1);
						up[1] = temp.getSubImage(xIndent +1, yIndent +1);
						up[2] = temp.getSubImage(xIndent +2, yIndent +1);
						tempArt.setUp(xIndent +0, yIndent +1);
						Animation WalkRight = new Animation(right, 100, true);
						WalkRight.setLooping(false);
						Animation WalkLeft =   new Animation(left, 100, true);
						WalkLeft.setLooping(false);
						Animation WalkUp =  new Animation(up, 100, true);
						WalkUp.setLooping(false);
						Animation WalkDown =  new Animation(down, 100, true);
						WalkDown.setLooping(false);
						
						tempArt.animations.put(AnimationType.WALK_RIGHT, WalkRight);
						tempArt.animations.put(AnimationType.WALK_LEFT, WalkLeft);
						tempArt.animations.put(AnimationType.WALK_UP, WalkUp);
						tempArt.animations.put(AnimationType.WALK_DOWN, WalkDown);
						
						String info[] = spriteInfo.get(i).split("~");//now [0] is name [1] is faces.
						String name = info[0];
						String facePaths[] = info[1].split("@");//splits into individual paths
						for(String path: facePaths)
						{
							if(path.trim().equals(""))
							{
								continue;
							}
							String[] typeAndPath = path.split(":"); //[0] is type, [1] is path
							String pathLiteral = typeAndPath[1];
							String type = typeAndPath[0];
							Image headshot = null;
							try {
								headshot = new Image(pathLiteral);
							} catch (SlickException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							EmotionEnum myEmotion = null;
							switch (type)
							{
								case "Neutral":
								{
									myEmotion = EmotionEnum.NEUTRAL;
									break;
								}
								default:
								{
									myEmotion = EmotionEnum.NEUTRAL;
									System.err.println("BAD FAICIAL FEATURE " + type + " IN " + name);
									System.exit(0);
									break;
								}
							}
							tempArt.faces.put(myEmotion, headshot);
							artHash.put(name, tempArt);
						}
						
					}
					
				}
			}
		}
		return artHash;
	}
} 
