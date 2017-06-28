package com.skidgame.cosmicloaf.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import com.skidgame.cosmicloaf.CosmicMain;
import com.skidgame.cosmicloaf.components.ComponentID;
import com.skidgame.cosmicloaf.components.Coordinate;
import com.skidgame.cosmicloaf.components.Entity;
import com.skidgame.cosmicloaf.components.Position;
import com.skidgame.cosmicloaf.systems.CollisionSystem;

public class Camera 
{
	private int HeightDeadzone;
	private int WidthDeadzone;
	private int HeightMiddle;
	private int WidthMiddle;
	private int YOffset;
	private int XOffset;
	private boolean updating;
	private Entity Tracking;
	
	/** The Width of the rendered box.	 */
	public int largeWidth;
	
	/** The height of the rendered box.	 */
	public int largeHeight;
	
	private TheCosmicLoafGame game;
	
	private double zoom;
	
	private int xAdjustSinceLastUpdate = 0;
	private int yAdjustSinceLastUpdate = 0;
	public int bufferUD;
	public int bufferLR;
	/**
	 * Creates a default camera with no tracked entity viewing 0,0.
	 */
	public Camera(TheCosmicLoafGame game)
	{
		HeightDeadzone = CosmicMain.height / 4;
		WidthDeadzone = CosmicMain.width / 4;
		HeightMiddle = CosmicMain.height / 2;
		WidthMiddle = CosmicMain.width / 2;
		YOffset = 0;
		XOffset = 0;
		updating = false;
		bufferUD = 128;
		bufferLR = 128;
		largeHeight = CosmicMain.height + bufferUD;
		largeWidth = CosmicMain.width + bufferLR;
		this.game = game;
		this.zoom = 1;
	}
	
	public Camera (int HDZ, int WDZ, int width, int height, int YOffset, int XOffset, boolean updating, Entity tracking, TheCosmicLoafGame game, int bufferUD, int bufferLR, float zoom)
	{
		HeightDeadzone = (int) (HDZ / zoom);
		WidthDeadzone = (int) (WDZ / zoom);
		HeightMiddle = (int) (height/(2 * zoom));
		WidthMiddle = (int) (width/(2 * zoom));
		this.YOffset = YOffset;
		this.XOffset = XOffset;
		this.updating = updating;
		this.Tracking = tracking;
		largeHeight = CosmicMain.height + 128;
		largeWidth = CosmicMain.width + 128;
		this.game = game;
		this.bufferLR = bufferLR;
		this.bufferUD = bufferUD;
		setZoom(zoom);
	}

	/**
	 * Centers the game camera on a certain Entity and turns off updating.
	 * @param e
	 */
	public void centerOn(Entity e)
	{
		this.updating = false;
		
		Coordinate eC = e.getPosition().getLiteralCoordinates().get(0);
		int eX = eC.getLiteralXValue();
		int eY = eC.getLiteralYValue();
		
		int xAdjust = 0;
		int yAdjust = 0;
		
		if(eX > WidthMiddle)
		{
			xAdjust = eX - (WidthMiddle);
		}
		//TODO look for errors here.
		if(eX < WidthMiddle - WidthDeadzone)
		{
			xAdjust = eX - (WidthMiddle);

		}
		
		if(eY > HeightMiddle)
		{
			yAdjust = eY - (HeightMiddle);
		}
		
		if(eY < HeightMiddle)
		{
			yAdjust = eY - (HeightMiddle);

		}
		
		if(xAdjust != 0)
		{
			
			XOffset += xAdjust;
			WidthMiddle += xAdjust;

		}

		if(yAdjust != 0)
		{
			
			YOffset += yAdjust;
			HeightMiddle += yAdjust;
		}
	}

	
	/**
	 * Centers the game camera on the tracked entity.
	 * @param e
	 */
	public void center()
	{
		
		Coordinate eC = Tracking.getPosition().getLiteralCoordinates().get(0);
		int eX = eC.getLiteralXValue();
		int eY = eC.getLiteralYValue();
		
		int xAdjust = 0;
		int yAdjust = 0;
		
		if(eX > WidthMiddle)
		{
			xAdjust = eX - (WidthMiddle);
		}
		//TODO look for errors here.
		if(eX < WidthMiddle - WidthDeadzone)
		{
			xAdjust = eX - (WidthMiddle);

		}
		
		if(eY > HeightMiddle)
		{
			yAdjust = eY - (HeightMiddle);
		}
		
		if(eY < HeightMiddle)
		{
			yAdjust = eY - (HeightMiddle);

		}
		
		if(xAdjust != 0)
		{
			
			XOffset += xAdjust;
			WidthMiddle += xAdjust;

		}

		if(yAdjust != 0)
		{
			
			YOffset += yAdjust;
			HeightMiddle += yAdjust;
		}
		
		
	}
	
	/**
	 * If the camera is currently set to update, this method makes sure that the tracked entity is within its
	 * suggested bounds.
	 */
	public void update()
	{
		if(updating)
		{
			if(Tracking != null)
			{
				
		
			//TODO currently works only with simple player shape.
			Coordinate playerCoordinate = Tracking.getPosition().getLiteralCoordinates().get(0);
//			int playerX = playerCoordinate.getScaledXValue(this);
//			int playerY = playerCoordinate.getScaledYValue(this);
			
			int playerX = playerCoordinate.getLiteralXValue();
			int playerY = playerCoordinate.getLiteralYValue();
			
			int xAdjust = 0;
			int yAdjust = 0;
			
//			System.out.println("Scaled x is " + playerX + " and we are testing to see if it is bigger than " + (WidthMiddle + WidthDeadzone));
//			int WidthMiddle = this.WidthMiddle * zoom;
//			int WidthDeadzone = this.WidthDeadzone * zoom;
//			int HeightDeadzone = this.HeightDeadzone * zoom;
//			int HeightMiddle = this.HeightMiddle * zoom;
			
			if(playerX > WidthMiddle + WidthDeadzone)
			{
				xAdjust = playerX - (WidthMiddle + WidthDeadzone);

			}
			//TODO look for errors here.
			if(playerX < WidthMiddle - WidthDeadzone)
			{
				xAdjust = playerX - (WidthMiddle - WidthDeadzone);


			}
			
			if(playerY > HeightMiddle + HeightDeadzone)
			{
				yAdjust = playerY - (HeightMiddle + HeightDeadzone);

			}
			
			if(playerY < HeightMiddle - HeightDeadzone)
			{
				yAdjust = playerY - (HeightMiddle - HeightDeadzone);

			}
			
			if(xAdjust != 0)
			{
				//System.out.println("Player X is at " + playerX + " and should be below " + (WidthMiddle + WidthDeadzone) + " or above " + (WidthMiddle - WidthDeadzone));
				//System.out.println("Adjusting " + xAdjust + " to compensate. All entities to be rendered at " + xAdjust);
				XOffset += xAdjust;
				WidthMiddle += xAdjust;

			}

			if(yAdjust != 0)
			{
				//System.out.println("Player Y is at " + playerY + " and should be above " + (HeightMiddle + HeightDeadzone) + " or " + (HeightMiddle - HeightDeadzone));
				//System.out.println("Adjusting " + yAdjust + " to compensate.");
				YOffset += yAdjust;
				HeightMiddle += yAdjust;
			}
			
			xAdjustSinceLastUpdate += xAdjust;
			yAdjustSinceLastUpdate += yAdjust;
			
			if(xAdjustSinceLastUpdate >= bufferLR || yAdjustSinceLastUpdate >= bufferUD || xAdjustSinceLastUpdate <= -bufferLR || yAdjustSinceLastUpdate <= -bufferUD)
			{
				System.out.println("UDPATING!!!!!!!!!!!!!!!");
				computeVisible();
				xAdjustSinceLastUpdate = 0;
				yAdjustSinceLastUpdate = 0;
			}

		}
		}
		
		
	}
	
	/**
	 * Changes the entity being tracked.
	 * @param e
	 */
	public void track(Entity e)
	{
		this.Tracking = e;
	}
	
	/**
	 * Toggles whether or not the camera tracks its target.
	 * @param tracking
	 */
	public void setTracking(boolean tracking)
	{
		this.updating = tracking;
	}
	
	/**
	 * Decides whether or not an entity can be viewed by the camera. <b> Needs a spot check <b> 
	 */
	public void computeVisible()
	{
		System.out.println("Computing visible");
		HashMap<Integer, ArrayList<Entity>> hash = game.getCurrentLevel().getEntityPositions();
		int levelX = XOffset % game.getLiteralHashDefinition(); //bring x level.
		levelX = XOffset - (game.getLiteralHashDefinition() - levelX);
		int levelY = YOffset % game.getLiteralHashDefinition(); //bring y level.
		levelY = YOffset - (game.getLiteralHashDefinition() - levelY);
		
		/**
		 * The code for Entities in hashmap.
		 */
		for(int x = XOffset - game.getLiteralHashDefinition(); x <= levelX + largeWidth + game.getLiteralHashDefinition(); x += game.getLiteralSquareWidth())
		{
		//	System.out.println("outer for");
			for(int y = levelY - game.getLiteralHashDefinition(); y <= levelY + largeHeight + game.getLiteralHashDefinition(); y+= game.getLiteralSquareHeight())
			{
				if(game.getCurrentLevel() == null)
				{
			//		System.out.println("Current level is null in camera.");
				}
			//	System.out.println("inner for. Converting " + x + ", " + y);
				int[] box = CollisionSystem.convertPositionToBoxes(x, y, game, game.getCurrentLevel(), this);
			//	System.out.println("Box zero is " + box[0]);
				if(x == XOffset - 32 || x ==  levelX + largeWidth + game.getLiteralHashDefinition() || y== levelY - game.getLiteralHashDefinition() || y== levelY + largeHeight + game.getLiteralHashDefinition())
				{
					if(hash.get(box[0]) != null)
					{
						for(Entity e : hash.get(box[0]))
						{
							if(e.getPosition() != null)
							{
							//	hash.get(box[0]).getPosition().setVisible(false);
							}
						}
						
					}
				}
				else
				{
					if(hash.get(box[0]) != null)
					{
						for(Entity e : hash.get(box[0]))
						{
							if(e.getPosition() != null)
							{
								e.getPosition().setVisible(true);
							}
						}
						
					}
				}
			}
		}
			
		/**
		 * Terrain visibility is handled directly by the render class.
		 */
	}
	
	
	public int getHeightDeadzone() {
		return HeightDeadzone;
	}

	public int getWidthDeadzone() {
		return WidthDeadzone;
	}

	public int getHeightMiddle() {
		return HeightMiddle;
	}

	public int getWidthMiddle() {
		return WidthMiddle;
	}

	public int getYOffset() {
		return -YOffset;
	}

	public int getXOffset() {
		return -XOffset;
	}
	
	public double getZoom()
	{
		return this.zoom;
	}
	
	public void setZoom(float zoom2)
	{
		if(zoom2 > 0)
		{
			this.zoom = zoom2;
		}
		else
		{
			this.zoom = 1;
		}
	}
	
	public int getXOffsetAdjusted()
	{
		return (int) (this.XOffset * zoom);
	}
	
	public int getYOffsetAdjusted()
	{
		return (int) (this.YOffset * zoom);
	}


}
