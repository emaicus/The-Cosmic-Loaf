package com.skidgame.cosmicloaf.systems;
import java.util.ArrayList;
import java.util.HashMap;

import com.skidgame.cosmicloaf.Level;
import com.skidgame.cosmicloaf.assets.Assets;
import com.skidgame.cosmicloaf.components.Art;
import com.skidgame.cosmicloaf.components.Component;
import com.skidgame.cosmicloaf.components.ComponentID;
import com.skidgame.cosmicloaf.components.Coordinate;
import com.skidgame.cosmicloaf.components.Direction;
import com.skidgame.cosmicloaf.components.Entity;
import com.skidgame.cosmicloaf.components.Interactor;
import com.skidgame.cosmicloaf.components.MovementOptions;
import com.skidgame.cosmicloaf.components.Position;
import com.skidgame.cosmicloaf.game.Camera;
import com.skidgame.cosmicloaf.game.TheCosmicLoafGame;

public class InteractionSystem
{
	
	/**
	 * Per entity, tests for interactions in adjacent squares and turns on a movement option if one is available.
	 * @param entities
	 * @param game
	 * @param level
	 */
	public static void process(ArrayList<Entity> entities, TheCosmicLoafGame game, Level level, Camera cam){
		ENTITYLOOP: for(Entity e: entities)
		{
			//If the entity is an interactor and has a position.
			if(e.getComponents()[ComponentID.INTERACTOR.ordinal()] != null && e.getPosition() != null && e.getMovementOptions() != null)
			{
				HashMap<Integer, ArrayList<Entity>> hash = level.getEntityPositions(); //A hash of collidable entities.
				
				Art art = e.getArt();
				Position pos = e.getPosition();
				
				if(art != null && pos != null)
				{
					int moveX;
					int moveY;
					Coordinate coordinate = pos.getLiteralCoordinates().get(0); //TODO assumptions being made.
					Coordinate coord = new Coordinate(coordinate.getLiteralXValue(), coordinate.getLiteralYValue());
					Direction dir = art.facing;
					if(dir == Direction.UP) 
					{
						moveY = -8;//- game.getLiteralSquareHeight();
						moveX = (game.getLiteralSquareWidth() /2);
						coord.setLiteralYValue(coord.getLiteralYValue() - game.getLiteralSquareHeight());
						coord.setLiteralXValue(coord.getLiteralXValue() + (game.getLiteralSquareWidth() /2));
					}
					else if(dir == Direction.DOWN)
					{
						moveY = 8;//game.getLiteralSquareHeight();
						moveX = (game.getLiteralSquareWidth() /2);
						coord.setLiteralYValue(coord.getLiteralYValue() + game.getLiteralSquareHeight());
						coord.setLiteralXValue(coord.getLiteralXValue() + (game.getLiteralSquareWidth() /2));

					}
					else if(dir == Direction.LEFT)
					{
						moveX = -8;//-game.getLiteralSquareWidth();
						moveY = (game.getLiteralSquareHeight() /2);
						coord.setLiteralXValue( coord.getLiteralXValue() - game.getLiteralSquareWidth());
						coord.setLiteralYValue(coord.getLiteralYValue() + (game.getLiteralSquareHeight() /2));

					}
					else//(dir == Direction.RIGHT)
					{
						moveX =  8;//game.getLiteralSquareWidth();
						moveY =   (game.getLiteralSquareHeight() /2);
						coord.setLiteralXValue( coord.getLiteralXValue() + game.getLiteralSquareWidth());
						coord.setLiteralYValue(coord.getLiteralYValue() + (game.getLiteralSquareHeight() /2));

					}
					
					// int[] boxes = CollisionSystem.convertPositionToBoxes(coord, game, game.getCurrentLevel(), cam);
					 //int toTest = boxes[0];
						int toTest = CollisionSystem.convertLiteralXYToBox(coord, game, level);
		//				System.out.println("Testing box " + toTest + " for an interactible entity.");
						if(/*hash.get(toTest) != null*/ CollisionSystem.collision(moveX, moveY, e, game, level)) //If there is a collidable entity in the box
						//TODO the above code is insufficient. It needs to test more than just collidable entities.
						{
					//		System.out.println("There is a collidable entity in box " + toTest);
							if(hash != null && hash.get(toTest) != null)
							{
								for(Entity ent : hash.get(toTest))
								{
									if(ent != e)
									{
					//					System.out.println("We can interact! with it");

										if(ent.getComponents()[ComponentID.INTERACTIBLE.ordinal()] != null) //If that entity is interactible.
										{
											//The code below gives this entity an interaction Option for input.
											((Interactor)e.getComponents()[ComponentID.INTERACTOR.ordinal()]).target = ent;								
											((MovementOptions)e.getComponents()[ComponentID.MOVEMENT_OPTIONS.ordinal()]).setInteractionAvailable(true);
											//	System.out.println("Interaction Available.");
											continue ENTITYLOOP;
										}
									}
								}
							}
							
							
						}
					//TODO terrain code
					
					((MovementOptions)e.getComponents()[ComponentID.MOVEMENT_OPTIONS.ordinal()]).setInteractionAvailable(false);
					((Interactor)e.getComponents()[ComponentID.INTERACTOR.ordinal()]).target = null;								
				}
			}
		}

	}

	

//	public static void interaction(Entity interactor) {
//		if(interactible(entity, interactor) == true){
//			if(entity.getComponents()[Interactible].isOpenAble = true){
//				entity,getComponents()[Art].toggleOpen();
//			}
//		}
//
//	}
}