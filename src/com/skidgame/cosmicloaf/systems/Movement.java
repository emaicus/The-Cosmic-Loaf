package com.skidgame.cosmicloaf.systems;

import java.util.ArrayList;
import java.util.HashMap;

import com.skidgame.cosmicloaf.Level;
import com.skidgame.cosmicloaf.components.AnimationType;
import com.skidgame.cosmicloaf.components.Coordinate;
import com.skidgame.cosmicloaf.components.Direction;
import com.skidgame.cosmicloaf.components.Art;
import com.skidgame.cosmicloaf.components.Entity;
import com.skidgame.cosmicloaf.components.ComponentID;
import com.skidgame.cosmicloaf.components.MovementOptions;
import com.skidgame.cosmicloaf.components.Position;
import com.skidgame.cosmicloaf.game.Camera;
import com.skidgame.cosmicloaf.game.Region;
import com.skidgame.cosmicloaf.game.TheCosmicLoafGame;


public class Movement
{
	private HashMap<Integer, Entity>  entities;
	
	public Movement(HashMap<Integer, Entity>  entities)
	{
		 this.entities = entities;
	}
	
	/**
	 * This method will move the Entity in the direction given if it is possible.
	 * 
	 * @param origin
	 * 		the Entity that wants to be moved
	 * @param direction
	 * 		the direction to move the Entity of
	 * 
	 * Touched by Evan.
	 * Switch statement doesn't work for variables, unfortunately. For this reason, I converted the switch
	 * to an equivalent if, else if, else block.
	 */
	public static void process(Entity origin, int direction, TheCosmicLoafGame game, Level level, int walkens, Camera cam)
	{
		//TODO decide if this should be an if, else if or not.
		//TODO Find a way to limit input to some time lower than 60 per second.
		Art art = origin.getArt();
		
		if(direction == Direction.LEFT.ordinal())
		{
			//TODO call collision in each of these. Or in the moveUp/down methods, your choice.
			Movement.move(origin, -walkens * TheCosmicLoafGame.walken, 0, game, level, cam);
			art.facing = Direction.LEFT;
			art.moving = true;
			if(art.animations.get(AnimationType.WALK_LEFT) != null)
			{
				art.currentAnimation = art.animations.get(AnimationType.WALK_LEFT);
			}
		}
		else if(direction == Direction.RIGHT.ordinal())
		{
			Movement.move(origin, walkens * TheCosmicLoafGame.walken, 0, game, level, cam);
			art.facing = Direction.RIGHT;
			art.moving = true;
			if(art.animations.get(AnimationType.WALK_RIGHT) != null)
			{
				art.currentAnimation = art.animations.get(AnimationType.WALK_RIGHT);
			}
		}
		else if(direction == Direction.UP.ordinal())
		{
			Movement.move(origin, 0, -walkens * TheCosmicLoafGame.walken, game, level, cam);
			art.facing = Direction.UP;
			art.moving = true;
			if(art.animations.get(AnimationType.WALK_UP) != null)
			{
				art.currentAnimation = art.animations.get(AnimationType.WALK_UP);
			}
		}
		else if(direction == Direction.DOWN.ordinal())
		{
			Movement.move(origin, 0, walkens * TheCosmicLoafGame.walken, game, level, cam);
			art.facing = Direction.DOWN;
			art.moving = true;
			if(art.animations.get(AnimationType.WALK_DOWN) != null)
			{
				art.currentAnimation = art.animations.get(AnimationType.WALK_DOWN);
			}
		}
		else
		{
			art.moving = false;
		}

	}
	
	/**
	 * This method will move the Entity in a vector Direction.
	 * @param origin
	 * 		the Entity that wants to be moved
	 */
	private static void move(Entity origin, int xMovement, int yMovement, TheCosmicLoafGame game, Level level, Camera cam)
	{

		HashMap<Integer, ArrayList<Entity>> hash = level.getEntityPositions();

		
		if(!CollisionSystem.collision(xMovement, yMovement, origin, game, level))
		{
			Region teleportTo = CollisionSystem.enteredTeleportRegion(xMovement, yMovement, origin, game, level);
			if(teleportTo != null)
			{
				xMovement = teleportTo.topLeft.getLiteralXValue() - origin.getPosition().getCorners()[CornerEnum.TOPLEFT.ordinal()].getLiteralXValue();
				yMovement = teleportTo.topLeft.getLiteralYValue() - origin.getPosition().getCorners()[CornerEnum.TOPLEFT.ordinal()].getLiteralYValue();

				System.out.println("teleportTo was not null!");
				//int box = CollisionSystem.convertLiteralXYToBox(teleportTo.bottomRight.getLiteralXValue() / 2, teleportTo.bottomRight.getLiteralYValue()/2, game, game.getCurrentLevel().getWidthInTiles());
				//moveEntityToBox(origin, box, game, level, true, cam);
				for(Coordinate c : origin.getPosition().getLiteralCoordinates()) //For all of the entity's coordinates
				{
					int [] boxesOccupiedByC = CollisionSystem.convertPositionToBoxes(c, game, level, cam); //Get all of the boxes the coordinate falls into when expanded.
				
					ArrayList<Integer> purge = new ArrayList<Integer>();
					for(int i : boxesOccupiedByC) //TODO check here. Is it possible for two things to be in the same box?
					{
						
						
						if(hash.get(i) != null)
						{
							if(!(hash.get(i).size() == 1)) //If there are more than one entities (us) in the box
							{
								hash.get(i).remove(origin);
							}
							else
							{
								hash.remove(i); //We were the only one in the box, so we can nuke it from orbit.
							}
						}
						
					}
					
					c.setLiteralXValue(c.getLiteralXValue() + xMovement); //Increment the value of the coordinate.
					c.setLiteralYValue(c.getLiteralYValue() + yMovement); //Increment the value of the coordinate.
					
					boxesOccupiedByC = CollisionSystem.convertPositionToBoxes(c, game, level, cam);
	
					for(int i : boxesOccupiedByC) //Put the entity back into the hash.
					{
						if(!(hash.get(i) == null))
						{
							hash.get(i).add(origin);
						}
						else
						{
							ArrayList<Entity> list = new ArrayList<Entity>();
							list.add(origin);
							hash.put(i, list);
	
						}
					}
				}
			}
			else
			{
				for(Coordinate c : origin.getPosition().getLiteralCoordinates()) //For all of the entity's coordinates
				{
					int [] boxesOccupiedByC = CollisionSystem.convertPositionToBoxes(c, game, level, cam); //Get all of the boxes the coordinate falls into when expanded.
				
					ArrayList<Integer> purge = new ArrayList<Integer>();
					for(int i : boxesOccupiedByC) //TODO check here. Is it possible for two things to be in the same box?
					{
						
						
						if(hash.get(i) != null)
						{
							if(!(hash.get(i).size() == 1)) //If there are more than one entities (us) in the box
							{
								hash.get(i).remove(origin);
							}
							else
							{
								hash.remove(i); //We were the only one in the box, so we can nuke it from orbit.
							}
						}
						
					}
					
					c.setLiteralXValue(c.getLiteralXValue() + xMovement); //Increment the value of the coordinate.
					c.setLiteralYValue(c.getLiteralYValue() + yMovement); //Increment the value of the coordinate.
					
					boxesOccupiedByC = CollisionSystem.convertPositionToBoxes(c, game, level, cam);
	
					for(int i : boxesOccupiedByC) //Put the entity back into the hash.
					{
						if(!(hash.get(i) == null))
						{
							hash.get(i).add(origin);
						}
						else
						{
							ArrayList<Entity> list = new ArrayList<Entity>();
							list.add(origin);
							hash.put(i, list);
	
						}
					}
				}
			}

		}
		
	}
	
	/**
	 * This method will teleport an entity to a box.
	 * Currently only suitable for 32x32 entities.
	 * @param mover
	 * @param box
	 * @param game
	 * @param level
	 */
	public static void moveEntityToBox(Entity mover, int box, TheCosmicLoafGame game, Level level, boolean forceTeleportation, Camera cam)
	{
		//TODO check this code for errors. It isn't quite working in the ai System.
		if(mover.getMovementOptions() != null && mover.getPosition() != null)
		{
			if(mover.getMovementOptions().canTeleport() || forceTeleportation)
			{
				if(CollisionSystem.checkSingleBoxCollision(box, level) == false)
				{
				//	System.out.println("Ai is not going to collide.");
					int[] coords = CollisionSystem.convertBoxToCoordinatePair(box, level);
					Position p = new Position(game, new Coordinate(coords[0], coords[1]));
					HashMap<Integer, ArrayList<Entity>> hash = level.getEntityPositions();
					
					//TODO this for loop is for when we are using larger than 32x32 entities. This code needs an update.
					for(Coordinate c : mover.getPosition().getLiteralCoordinates())
					{
						int[] boxes = CollisionSystem.convertPositionToBoxes(c, game, level, cam);
						for(int i : boxes)
						{
							hash.remove(i);
						}
					}
					
					if(! (hash.get(box) == null))
					{
						hash.get(box).add(mover);
					}
					else
					{
						ArrayList<Entity> list = new ArrayList<Entity>();
						list.add(mover);
						hash.put(box, list);

					}
					mover.getComponents()[ComponentID.POSITION.ordinal()] = p;
					
				}
				else
				{
					int coords[] = CollisionSystem.convertBoxToCoordinatePair(box, level);
				//	System.out.println("Ai is going to collide, box" + coords[0] +","+ coords[1]);
				}
			}
		}
	}
}
