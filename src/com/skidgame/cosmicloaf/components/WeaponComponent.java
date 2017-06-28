package com.skidgame.cosmicloaf.components;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Animation;

import com.skidgame.cosmicloaf.game.Camera;
import com.skidgame.cosmicloaf.game.TheCosmicLoafGame;
import com.skidgame.cosmicloaf.systems.CollisionSystem;

public class WeaponComponent extends Component
{
	public boolean ranged;
	public int damage;
	public int range;
	public int speed;
	public int cooldown;
	public Animation myAnimation;
	
	public WeaponComponent(boolean ranged, int damage, int range) {
		super();
		this.ranged = ranged;
		this.damage = damage;
		this.range = range;
	}

	@Override
	public int getID() {
		return ComponentID.WEAPON.ordinal();
	}
	
	public void attack(TheCosmicLoafGame game, Entity attacker, Camera cam)
	{
		Art art = attacker.getArt();
		Position pos = attacker.getPosition();
		
		if(art != null && pos != null)
		{
			/**
			 * The following gets the correct position to attack.
			 */
		
			Coordinate coordinate = pos.getLiteralCoordinates().get(0); //TODO assumptions being made (one square unit).
			Coordinate coord = new Coordinate(coordinate.getLiteralXValue(), coordinate.getLiteralYValue());
			Direction dir = art.facing;
			if(dir == Direction.UP)
			{
				coord.setLiteralYValue(coord.getLiteralYValue() - game.getLiteralSquareHeight());
			}
			else if(dir == Direction.DOWN)
			{
				coord.setLiteralYValue(coord.getLiteralYValue() + game.getLiteralSquareHeight());

			}
			else if(dir == Direction.LEFT)
			{
				coord.setLiteralXValue(coord.getLiteralXValue() - game.getLiteralSquareWidth());
			}
			else//(dir == Direction.RIGHT)
			{
				coord.setLiteralXValue(coord.getLiteralXValue() + game.getLiteralSquareWidth());
			}
			
			if(ranged)
			{
			
			}
			else
			{
				int[] boxes = CollisionSystem.convertPositionToBoxes(coord, game, game.getCurrentLevel(), cam);
				int toTest = boxes[0];
				HashMap<Integer, ArrayList<Entity>> hash = game.getCurrentLevel().getEntityPositions();
				ArrayList<Entity> attackList = hash.get(toTest);
				if(attackList != null) //If there is a collidable entity in the box
				{
					for(Entity attacking : attackList)
					{
						HealthComponent health = ((HealthComponent)attacking.getComponents()[ComponentID.HEALTH.ordinal()]);
						if(health != null) //If that entity is interactible.
						{
							if(!health.isInvulnerable())
							{
								if((attacking.getArt()!=null)&&(attacking.getArt().animations.get(AnimationType.DAMAGED) != null))
								{
									attacking.getArt().currentAnimation = attacking.getArt().animations.get(AnimationType.DAMAGED);
								}
								health.decreaseBy(damage, game, attacking);

							}
							else
							{
								//The thing that we attacked was invulnerable.
								//TODO play an invulnerable sound.
							}
						} 
						else
						{
							//the thing that we are attacking doesn't have health
							//TODO play a "hitting something that doesn't take damage" sound.
						}
					}
				}
				if(myAnimation != null)
				{
					Position myPosition = new Position(game, coord);
					Art myArt = new Art(myAnimation);
					Entity animation = new Entity(myPosition, myArt);
					game.addEntity(animation);
				}
				
			} 
		}//Doesn't have art or position
	}
							
						

}
