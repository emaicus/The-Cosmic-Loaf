package com.skidgame.cosmicloaf.systems;
import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.tiled.TiledMap;

import com.skidgame.cosmicloaf.CosmicMain;
import com.skidgame.cosmicloaf.assets.Assets;
import com.skidgame.cosmicloaf.components.AnimationType;
import com.skidgame.cosmicloaf.components.Art;
import com.skidgame.cosmicloaf.components.ButtonComponent;
import com.skidgame.cosmicloaf.components.Component;
import com.skidgame.cosmicloaf.components.ComponentID;
import com.skidgame.cosmicloaf.components.Direction;
import com.skidgame.cosmicloaf.components.Entity;
import com.skidgame.cosmicloaf.components.GUIDataComponent;
import com.skidgame.cosmicloaf.components.Interactible;
import com.skidgame.cosmicloaf.components.Position;
import com.skidgame.cosmicloaf.game.Camera;
import com.skidgame.cosmicloaf.game.TheCosmicLoafGame;


public class RenderSystem //extends System
{

	public static SpriteSheet using;
	
	public static void process(ArrayList<Entity> entities, ArrayList<SpriteSheet> sheets, Camera camera, TheCosmicLoafGame game)
	{
		int currentSheet = 0;
		using = sheets.get(currentSheet);
		using.startUse();
		ArrayList<Entity> hasText = new ArrayList<Entity>();

		for(Entity e : entities)
		{
			if(renderable(e))//Performs null test.
			{
				if(((Art)e.getComponents()[ComponentID.ART.ordinal()]).sheetNumber != currentSheet)
				{
					using.endUse();

					currentSheet = ((Art)e.getComponents()[ComponentID.ART.ordinal()]).sheetNumber;

					using = sheets.get(currentSheet);
					using.startUse();
				}	
				
				if(using == null)
				{
					System.out.println("Null sheet.");
				}

				render(e, camera, using, game);
				
				
			}
		}
		using.endUse();

		for(Entity e : entities)
		{
			ButtonComponent bc = ((ButtonComponent)e.getComponents()[ComponentID.BUTTON_COMPONENT.ordinal()]);

			if(bc != null && e.getPosition() != null)
			{
				if(bc.getButtonText() != null)
				{
					int eXvalue = ((Position)e.getComponents()[ComponentID.POSITION.ordinal()]).getAdjustedCoordinates(camera).get(0).getLiteralXValue();
					int eYvalue = ((Position)e.getComponents()[ComponentID.POSITION.ordinal()]).getAdjustedCoordinates(camera).get(0).getLiteralYValue();
					
					if(bc.isSelected())
					{
						TheCosmicLoafGame.ttf.drawString(eXvalue + bc.getxOffset() * ((float)camera.getZoom()) + camera.getXOffsetAdjusted(), (float) (eYvalue + bc.getyOffset() * camera.getZoom() + camera.getYOffsetAdjusted()), bc.getButtonText(), Color.yellow );	
					}
					else
					{
						TheCosmicLoafGame.ttf.drawString((float)(eXvalue + bc.getxOffset() * camera.getZoom() + camera.getXOffsetAdjusted()), (float)(eYvalue + bc.getyOffset() * camera.getZoom() + camera.getYOffsetAdjusted()), bc.getButtonText());	
					}					
				}
			}
			
		}
		
	}

	

	private static boolean renderable(Entity e) {
		boolean renderable = false;
		
		if(e == null)
		{
			return false;
		}
		if (e.getComponents()[ComponentID.POSITION.ordinal()] != null 
				&& e.getComponents()[ComponentID.ART.ordinal()] != null) {
			if(e.getPosition().isVisible())
			{
				renderable = true;
			}
		}
		return renderable;
	}
	
	private static boolean isGUITextRenderable(Entity e) {
		boolean renderable = false;
		
		if(e == null)
		{
			return false;
		}
		if (e.getComponents()[ComponentID.POSITION.ordinal()] != null 
				&& e.getComponents()[ComponentID.GUI_DATA.ordinal()] != null) {
				
				renderable = true;
				
		}
		return renderable;
	}
	
	/**
	 * Render a 2d array (most likely terrain).
	 * @param terrain
	 * @param sheets
	 */
	public static void process(Entity[][] terrain, ArrayList<SpriteSheet> sheets, Camera camera, TheCosmicLoafGame game)
	{
		int currentSheet = 0; //The current sprite sheet.
		SpriteSheet using = sheets.get(currentSheet);
		using.startUse();

		for(int i = 0; i < terrain.length; i++) //for every row.
		{
			for(int j = 0; j < terrain[i].length; j++) //for every col.
			{
				Entity e = terrain[i][j]; //get the entity in the cell.
				if(renderable(e)) //if the entity is renderable.
				{
					if(((Art)e.getComponents()[ComponentID.ART.ordinal()]).sheetNumber != currentSheet) //if the entity uses a different sheet.
					{
						using.endUse(); //stop using the current sheet, and start using the new one.
		
						currentSheet = ((Art)e.getComponents()[ComponentID.ART.ordinal()]).sheetNumber;
						using = sheets.get(currentSheet);
					}				
					
					//The values below represent the game position and sprite sheet position of our entity. 
					int eXvalue = ((Position)e.getComponents()[ComponentID.POSITION.ordinal()]).getLiteralCoordinates().get(0).getLiteralXValue();
					int eYvalue = ((Position)e.getComponents()[ComponentID.POSITION.ordinal()]).getLiteralCoordinates().get(0).getLiteralYValue();
					using.endUse();
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
			        using.startUse();
					e.getArt().renderInUse(eXvalue + camera.getXOffset(), eYvalue + camera.getYOffset(), game, camera);
					
				}
			}
		}
		using.endUse(); //Last call to end sprite sheet use.
	}
	
	public static void renderTiledMap(TheCosmicLoafGame game, Camera cam)
	{
		TiledMap map = game.getCurrentLevel().getTiledMap();
		if(map!= null)
		{
			//The below code is magic. Stare at it in its beauty. STARE AT IT!
			map.render(-cam.getXOffsetAdjusted()%32, -cam.getYOffsetAdjusted()%32, cam.getXOffsetAdjusted() / game.getLiteralSquareWidth(), cam.getYOffsetAdjusted() /game.getLiteralSquareHeight(), (cam.largeWidth/game.getLiteralSquareWidth()) + 2, (cam.largeHeight/game.getLiteralSquareHeight()) + 2, false);

		
		}
	}
	
	
	/**
	 * A specific method for rendering terrain. Renders the terrain directly around a player and a little extra.
	 * <p> This is MUCH more efficient than checking every piece of terrain to determine visibility.
	 * @param terrain
	 * @param sheets
	 * @param cam
	 * @param game
	 */
	public static void renderTerrain(Entity[][] terrain, ArrayList<SpriteSheet> sheets, Camera cam, TheCosmicLoafGame game)
	{
		/**
		 * The code for terrain.
		 */
       

        if(terrain.length > 0)
		{
			int currentSheet = 0; //The current sprite sheet.
			using = sheets.get(currentSheet);
			using.startUse();
//			
//			for(int i = 0; i < terrain.length; i++)
//			{
//				for(int j = 0; j < terrain[i].length; j++)
//				{
//					Entity e = terrain[i][j];
//					int eXvalue = ((Position)e.getComponents()[ComponentID.POSITION.ordinal()]).getLiteralCoordinates().get(0).getLiteralXValue();
//					int eYvalue = ((Position)e.getComponents()[ComponentID.POSITION.ordinal()]).getLiteralCoordinates().get(0).getLiteralYValue();
//
//					
//					e.getArt().renderInUse(eXvalue + cam.getXOffset(), eYvalue + cam.getYOffset(), game, cam);
//				
//				}
//			}
			
			int adjust = cam.getXOffsetAdjusted() % game.getAdjustedSquareWidth(cam); //The left over pixels.
			int levelX = cam.getXOffsetAdjusted() - (game.getAdjustedSquareWidth(cam) - adjust); //levelX is now a neat divisor.
			levelX /= game.getAdjustedSquareWidth(cam);
			int yadj = cam.getYOffsetAdjusted() % game.getAdjustedSquareHeight(cam); //same for y
			int levelY = cam.getYOffsetAdjusted() - (game.getAdjustedSquareHeight(cam) - yadj);
			levelY /= game.getAdjustedSquareWidth(cam);

			int largeWidthAsBox = cam.largeWidth/game.getAdjustedSquareWidth(cam);
			int largeHeightAsBox = cam.largeHeight/game.getAdjustedSquareHeight(cam);
			
			int bufferUDAsBox = cam.bufferUD/game.getAdjustedSquareHeight(cam);
			int bufferLRAsBox = cam.bufferLR/game.getAdjustedSquareWidth(cam);
		
			for(int x = levelX - bufferLRAsBox; x <= levelX + largeWidthAsBox + bufferLRAsBox; x += 1) //go from current offset to size of large box + a little extra.
			{
				if(x >= 0 && x < game.getCurrentLevel().getWidthInTiles()) //Out of bounds check
				{
					for(int y = levelY - bufferUDAsBox; y <= levelY + largeHeightAsBox + bufferUDAsBox; y+= 1) //go from current offset to size of large box + a little extra
					{		
						if(y >= 0 && y < game.getCurrentLevel().getHeightInTiles()) //Out of bounds check
						{
							if(game.getCurrentLevel().getTerrain()[x][y] != null) 
							{
								Entity e = terrain[x][y]; //get the entity in the cell.
								if(renderable(e)) //if the entity is renderable (has position, art, and is visible).
								{
									if(e.getArt().sheetNumber != currentSheet) //if the entity uses a different sheet,
									{
										/*System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
										System.out.println("current sheet = " + currentSheet);
										System.out.println("sheetNumber is = " + ((Art)e.getComponents()[ComponentID.ART.ordinal()]).sheetNumber );*/
									//	System.out.println("Swapping from " + currentSheet);
										using.endUse(); //stop using the current sheet, and start using the new one.
						
										currentSheet = e.getArt().sheetNumber;
									//	System.out.println("Swapping to " + currentSheet);
										using = sheets.get(currentSheet);
										using.startUse();
									}				
									
									//The values below represent the game position and sprite sheet position of our entity. 
									int eXvalue = ((Position)e.getComponents()[ComponentID.POSITION.ordinal()]).getLiteralCoordinates().get(0).getLiteralXValue();
									int eYvalue = ((Position)e.getComponents()[ComponentID.POSITION.ordinal()]).getLiteralCoordinates().get(0).getLiteralYValue();

									
									e.getArt().renderInUse(eXvalue + cam.getXOffset(), eYvalue + cam.getYOffset(), game, cam);
								}
							}
						
						}
					}//end inner for
				}//end if
			} //End outer for
			
			using.endUse(); //when we are done, stop using the spritesheet.
		}
		
		
	}
	
	
	public static void process(Entity[] toRender, ArrayList<SpriteSheet> sheets, TheCosmicLoafGame game, Camera cam)
	{
		int currentSheet = 0; //The current sprite sheet.
		SpriteSheet using = sheets.get(currentSheet);
		using.startUse();

		for(int i = 0; i < toRender.length; i++) //for every row.
		{
			Entity e = toRender[i]; //get the entity in the cell.
				if(renderable(e)) //if the entity is renderable.
				{
					if(((Art)e.getComponents()[ComponentID.ART.ordinal()]).sheetNumber != currentSheet) //if the entity uses a different sheet.
					{
						using.endUse(); //stop using the current sheet, and start using the new one.
		
						currentSheet = ((Art)e.getComponents()[ComponentID.ART.ordinal()]).sheetNumber;
						using = sheets.get(currentSheet);
					}				
					
					//The values below represent the game position and sprite sheet position of our entity. 
					int eXvalue = ((Position)e.getComponents()[ComponentID.POSITION.ordinal()]).getLiteralCoordinates().get(0).getLiteralXValue();
					int eYvalue = ((Position)e.getComponents()[ComponentID.POSITION.ordinal()]).getLiteralCoordinates().get(0).getLiteralYValue();

					//TODO this code is broken. Add code to get top left corner.
					e.getArt().renderInUse(eXvalue + cam.getXOffset(), eYvalue + cam.getYOffset(), game, cam);

				}
			
		}
		using.endUse(); //Last call to end sprite sheet use.
	}
	
	public static void renderGUIText(Entity[] guiElements, Camera camera/* boolean displayText, String text*/)
	{

		for(Entity e : guiElements) //for every row.
		{
				if(isGUITextRenderable(e)) //performs null test.
				{
					if(((GUIDataComponent)e.getComponents()[ComponentID.GUI_DATA.ordinal()]) != null) //if the entity uses a different sheet.
					{
						int eXvalue = ((Position)e.getComponents()[ComponentID.POSITION.ordinal()]).getAdjustedCoordinates(camera).get(0).getLiteralXValue();
						int eYvalue = ((Position)e.getComponents()[ComponentID.POSITION.ordinal()]).getAdjustedCoordinates(camera).get(0).getLiteralYValue();
						TheCosmicLoafGame.ttf.drawString(eXvalue + camera.getXOffsetAdjusted(), eYvalue + camera.getYOffsetAdjusted(), ((GUIDataComponent)e.getComponents()[ComponentID.GUI_DATA.ordinal()]).getDataAsString(), Color.white );
					}				
				}
			
		}
	}
	
	/**takes in a pre-checked entity and renders it with the appropriate direction
	 * @param using */
	private static void render(Entity e, Camera camera, SpriteSheet using, TheCosmicLoafGame game)
	{
		boolean rendered = false;
		if(e.getPosition() == null)
		{
			System.out.println("Null position");
			Interactible i = ((Interactible)e.getComponents()[ComponentID.INTERACTIBLE.ordinal()]);
			if(i != null)
			{
				System.out.println("Entity was interactible");
				System.out.println(i.readText[0]);
			}
		}
	
		Art art = e.getArt();
		int eXvalue = ((Position)e.getComponents()[ComponentID.POSITION.ordinal()]).getLiteralCoordinates().get(0).getLiteralXValue();
		int eYvalue = ((Position)e.getComponents()[ComponentID.POSITION.ordinal()]).getLiteralCoordinates().get(0).getLiteralYValue();

			if(art.currentAnimation != null)
			{
				if(art.currentAnimation.isStopped())
				{
					art.currentAnimation.restart();
					art.currentAnimation = null;
				}
				else
				{
					art.renderCurrentAnimation(eXvalue + camera.getXOffset(), eYvalue + camera.getYOffset(), e, game, camera);
					rendered = true;
				}
			}

		
		if(!rendered) //render the default
		{
			art.renderInUse(eXvalue + camera.getXOffset(), eYvalue + camera.getYOffset(), game, camera);
		}
	}
	
}
