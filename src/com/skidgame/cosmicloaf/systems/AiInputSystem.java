package com.skidgame.cosmicloaf.systems;

import java.util.ArrayList;

import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.Path.Step;

import com.skidgame.cosmicloaf.Level;
import com.skidgame.cosmicloaf.components.AiInput;
import com.skidgame.cosmicloaf.components.ComponentID;
import com.skidgame.cosmicloaf.components.Coordinate;
import com.skidgame.cosmicloaf.components.Direction;
import com.skidgame.cosmicloaf.components.Entity;
import com.skidgame.cosmicloaf.game.Camera;
import com.skidgame.cosmicloaf.game.TheCosmicLoafGame;

/**
 * 
 * @author gameClub
 *
 */
public class AiInputSystem 
{
//	//TODO this code is currently broken. It needs more testing.
	/**
	 * Updates the current path for every ai Entity.
	 * @param entities
	 * @param level
	 * @param cam
	 */
	public static void updatePaths(ArrayList<Entity> entities, Level level, Camera cam, TheCosmicLoafGame game)
	{
		for(Entity e : entities) //For every entity passed in.
		{
			if(e.getPosition() != null && e.getComponents()[ComponentID.AI_INPUT.ordinal()] != null) //If that entity has a position and is an ai.
			{
				AiInput entityAi = ((AiInput)e.getComponents()[ComponentID.AI_INPUT.ordinal()]);
				if(entityAi.isAlert()) //If they are alert.
				{
					/**
					 * Line 1) gets the entity position
					 * Line 2) gets player Position
					 * Line 3) gets path from entity to player
					 * Line 4) sets entity's current path to be the path we computed in 3.
					 */
					int ourBox = CollisionSystem.convertLiteralXYToBox(e.getPosition().getLiteralCoordinates().get(0), game, level);
					int[] ourAiBox = convertToAiBox(ourBox, level);
					
					int playerBox = CollisionSystem.convertLiteralXYToBox(level.getPlayer().get(0).getPosition().getLiteralCoordinates().get(0), game, level);
					int[] playerAiBox = convertToAiBox(entityAi.getTargetBox(e, level, game), level);

					
					Path p = level.pathFinder.findPath(e, ourAiBox[0], ourAiBox[1], playerAiBox[0], playerAiBox[1]);
					entityAi.setPathToTarget(p);//Get path to player.
				}
				else if(entityAi.isPatrol())
				{
					int ourBox = CollisionSystem.convertLiteralXYToBox(e.getPosition().getLiteralCoordinates().get(0), game, level);
					int[] ourAiBox = convertToAiBox(ourBox, level);
					int[] target = convertToAiBox(entityAi.getTargetBox(e, level, game), level);
					Path p = level.pathFinder.findPath(e, ourAiBox[0], ourAiBox[1], target[0], target[1]);
					entityAi.setPathToTarget(p); //Sets the AI's Path.
					
					if(p != null)
					{
						if(p.getLength() > 0)
						{
							System.out.println("Valid path.");
						}
						else
						{
							System.out.println("There!");
						}
					}
					else
					{
						System.out.println("Null path.");
					}
				}
			}
		}
			//Steps are x y boxes. We will convert and move.
	}
	
	/**
	 * Moves AI entities based on their current path.
	 * @param entities
	 * @param game
	 * @param level
	 * @param cam
	 */
	public static void process(ArrayList<Entity> entities, TheCosmicLoafGame game, Level level, Camera cam)
	{
		//System.out.println("looking for the next sport to process");
		for(Entity e : entities)
		{
			if(e.getPosition() != null && e.getAiComponent() != null)
			{
				Path p = e.getAiComponent().getPathToTarget();
				if(p != null && p.getLength() >= 0)
				{	
					//Step toTake = p.getStep(1); //TODO check if valid. Gets first step on path. Path should be reevaluated every tick, so this should always be ok. Inefficient, though.
					//int boxToMoveTo = convertFromAiBox(toTake.getX(), toTake.getY(), level);
					//int boxToMoveTowards = boxToMoveTo;
					//TODO right now, this defaults to teleportation.
					Direction direction;
					
					if(p.getStep(0).getX() < p.getStep(1).getX())
					{
						direction = Direction.RIGHT;
					}
					else if(p.getStep(0).getX() > p.getStep(1).getX())
					{
						direction = Direction.LEFT;
					}
					else if(p.getStep(0).getY() < p.getStep(1).getY())
					{
						direction = Direction.DOWN;
					}
					else //(p.getStep(0).getY() > p.getStep(1).getY())
					{
						direction = Direction.UP;
					}
					Movement.process(e, direction.ordinal(), game, level, 2, cam);
				}
				else{
				}
			}
		}
			//Steps are x y boxes. We will convert and move.
	}
	
	/**
	 * Takes in the x and y coords of the box that an ai provide and converts it to work with our system.
	 * @param x
	 * @param y
	 * @param level
	 * @return
	 */
	public static int convertFromAiBox(int x, int y, Level level)
	{
		return (y* level.getWidthInTiles()) + x;
	}
	
	/**
	 * Takes in one of our boxes and converts it to an ai's box. This is placed in an array, [0] is x, [1] is y.
	 * @param box
	 * @param level
	 * @return int[] ==> [0] = x, [1] = y
	 */
	public static int[] convertToAiBox(int box, Level level)
	{
		int array[] = new int [2];
		array[0] = box % level.getWidthInTiles();
		array[1] = box / level.getHeightInTiles();
		return array;
	}
}