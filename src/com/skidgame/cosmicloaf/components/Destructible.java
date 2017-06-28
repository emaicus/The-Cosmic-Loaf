/**
 * 
 */
package com.skidgame.cosmicloaf.components;

/**
 * Hey this file is great!!!!!!
 */

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;

import com.skidgame.cosmicloaf.assets.Assets;
import com.skidgame.cosmicloaf.game.Camera;
import com.skidgame.cosmicloaf.game.TheCosmicLoafGame;
import com.skidgame.cosmicloaf.systems.CollisionSystem;

/**
 * This is a component that to make destructible game 
 * 
 * @author mlekena
 *
 */
public class Destructible extends Component{

	/** Reference to the game object to retrieve the level */
	TheCosmicLoafGame game;
	
	/** Reference to the entity to be destroyed*/
	Entity refEntity;
	
	/** A flag to determine whether to create a default render animation when entity is destroyed*/
	boolean defaultRender;

	/**
	 * Constructor for a destructible component that takes in an Art object to render, and ItemDrop 
	 * object to create a item game object to place and a boolean flag for whether the entity is collidable 
	 * or not.
	 * 
	 * @param game
	 * @param entity
	 * @param drop
	 * @param collidable
	 */
	public Destructible(TheCosmicLoafGame game, Entity entity)
	{
		this.game = game;
		this.refEntity = entity;
		//if the entity has no destroyed animation set then make a default transparent animation
		if (refEntity.getArt().animations.get(AnimationType.DESTROYED) == null)
		{
			refEntity.getArt().animations.put(AnimationType.DESTROYED,createDefaultAnimation());
			defaultRender = true;
		}
		
		
	}

	/**
	 * switches switches to the destroy animation and removes the entity from the current level
	 * 
	 * @see com.skidgame.cosmicloaf.Level.removeEntity()
	 */
	public void destruct(Camera cam)
	{
		if (defaultRender)
		{
			//TODO potential refactor on spritesheets hashmap refactor in Assets
			refEntity.getArt().sheetNumber = 2; //set the spritesheet number to the defaultDestroyed
			//set the standPixels to the Default image
			refEntity.getArt().setStand(0, 0);
		}
		else
		{
			//TODO potential edit on confirmation on getters in Art
			refEntity.getArt().currentAnimation = refEntity.getArt().animations.get(AnimationType.DESTROYED);
		}
		
		game.getCurrentLevel().removeEntity(refEntity, game, cam);


	}
	@Override
	public int getID() {
		return ComponentID.DESTRUCTIBLE.ordinal();
	}

	private Animation createDefaultAnimation()
	{
		Animation defaultAni = null;
		Image defaultImg[] = new Image[1];
		defaultImg[0] = Assets.getSpriteSheets().get(2).getSubImage(0, 0); // 2 is the destroyedDefault resource emptyAnimation
		defaultAni = new Animation(defaultImg,100,true);
		return defaultAni;
	}

}