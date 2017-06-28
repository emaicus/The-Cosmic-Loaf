package com.skidgame.cosmicloaf.game;

import java.util.ArrayList;

import com.skidgame.cosmicloaf.Level;
import com.skidgame.cosmicloaf.components.ButtonComponent;
import com.skidgame.cosmicloaf.components.Component;
import com.skidgame.cosmicloaf.components.ComponentID;
import com.skidgame.cosmicloaf.components.Coordinate;
import com.skidgame.cosmicloaf.components.Entity;
import com.skidgame.cosmicloaf.components.GUIDataComponent;
import com.skidgame.cosmicloaf.components.GUIElementTypes;
import com.skidgame.cosmicloaf.components.Position;
import com.skidgame.cosmicloaf.systems.CollisionSystem;

/**
 * A simple Graphical Overlay.
 * @author emaicus
 *
 */
public class GUI 
{
	private Entity[] elements;
	
	public GUI(ArrayList<Entity> elements)
	{
		this.elements = new Entity[GUIElementTypes.length];
		for(Entity c : elements)
		{
			if(c.getComponents()[ComponentID.GUI_DATA.ordinal()] != null)
			{
				this.elements[((GUIDataComponent)c.getComponents()[ComponentID.GUI_DATA.ordinal()]).getDataType()] = c;
			}
		}
	}
	
	public Entity[] getElements()
	{
		return elements;
	}
	
	public void update(TheCosmicLoafGame game, Camera cam)
	{
		//right now this just updates the box that they are in.
		Level currentLevel = game.getCurrentLevel();
		Entity player = currentLevel.getPlayer().get(0);
		if(elements[GUIElementTypes.PLAYER_BOX_COORD.ordinal()] != null)
		{	

			if(player != null)
			{
				Position p = ((Position)player.getComponents()[ComponentID.POSITION.ordinal()]);
				int[] coords = CollisionSystem.convertPositionToBoxes(p.getLiteralCoordinates().get(0), game, currentLevel, cam);
				((GUIDataComponent)elements[GUIElementTypes.PLAYER_BOX_COORD.ordinal()].getComponents()[ComponentID.GUI_DATA.ordinal()]).setData(""+coords[0]); //upper left x.
			}
			else
			{
				System.out.println("Player was null.");

			}
		}
		
		if(game.displayTextBox == true && elements[GUIElementTypes.TEXT_BOX.ordinal()] != null)
		{
			int boxY = cam.getHeightDeadzone() + cam.getHeightMiddle() + 32;
			int boxX = cam.getWidthDeadzone();
			elements[GUIElementTypes.TEXT_BOX.ordinal()].setPosition(new Position(game, new Coordinate(boxX, boxY)));
			((GUIDataComponent)elements[GUIElementTypes.TEXT_BOX.ordinal()].getComponents()[ComponentID.GUI_DATA.ordinal()]).setData(game.getCurrentText());
		}
		else if(game.displayTextBox == false && elements[GUIElementTypes.TEXT_BOX.ordinal()] != null)
		{
			elements[GUIElementTypes.TEXT_BOX.ordinal()].setPosition(null);
		}
		
		if(elements[GUIElementTypes.FPS.ordinal()] != null)
		{
            GUIDataComponent fpsDataComp = ((GUIDataComponent)elements[GUIElementTypes.FPS.ordinal()].getComponents()[ComponentID.GUI_DATA.ordinal()]);

			long lastTimeStamp = (long)fpsDataComp.getNumData()[0];
			long now = System.nanoTime();
		//	System.out.println(now + " - " + lastTimeStamp + " = " + (long)(now - lastTimeStamp));
			fpsDataComp.getNumData()[1]++;
			long frameCount = (long) fpsDataComp.getNumData()[1];
			long duration = (long) (now - lastTimeStamp);
		 //   System.out.println("Duration " + duration);
		    
          

		    
		        if(duration > 1000000000)
		        {

		            long fps =  (long) (frameCount); /// (duration / 1000.0));
		            fpsDataComp.setData("" + fps);
		            fpsDataComp.getNumData()[0] = now;
		            fpsDataComp.getNumData()[1] = 0;
		        }
		        
		}
		
		if(elements[GUIElementTypes.INVENTORY.ordinal()] != null)
		{
			
		}
		
	}
	
}
