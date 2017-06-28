package com.skidgame.cosmicloaf.components;

import java.util.HashMap;
import java.util.Hashtable;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import com.skidgame.cosmicloaf.assets.Assets;
import com.skidgame.cosmicloaf.constants.EmotionEnum;
import com.skidgame.cosmicloaf.game.Camera;
import com.skidgame.cosmicloaf.game.Game;
import com.skidgame.cosmicloaf.game.TheCosmicLoafGame;
import com.skidgame.cosmicloaf.systems.RenderSystem;

/**
 * This class is responsible for representing art to be rendered in a scene
 * 
 * @author 
 *
 */
public class Art extends Component
{
	/** The number of the sprite sheet linked to this art object */
	public int sheetNumber;
	
	/** The X coordinate of the cell in the sprite sheet to render. LOOKS DOWN */
	public int standXPixel;
	
	/** The Y coordinate of the cell in the sprite sheet to render. LOOKS DOWN */
	public int standYPixel;
	
	/** The X coordinate of the cell in the sprite sheet to render. LOOKS DOWN */
	private int altStandXPixel;
	
	/** The Y coordinate of the cell in the sprite sheet to render. LOOKS DOWN */
	private int altStandYPixel;
	
	/** looking right (flipped for left) */
	private int rightXPixel;
	
	/** looking right (flipped for left) */
	private int rightYPixel;
	
	/** To save time, the program generates left facing images rather than making the art team draw them.	 */
	private Image facingLeft;
	
	/** Looking up */
	private int upXPixel;
	
	/** Looking up */
	private int upYPixel;
	
	/** True if the main pixels are being displayed, false if alt. (for use in textures that can appear in multiple states) */
	private boolean displayingMain;
	
	/** Entities render faster if they are not animated.*/
	public boolean Animated;
	
	/** The component identification through out the system when attached to game objects */
	private ComponentID id;
	
	/** A Hahtable mapping from enum AnimationTypes to slick2D animations */
	public Hashtable<AnimationType, Animation> animations;
	
	/** An enumerated Direction representing which way the art is facing.*/
	public Direction facing;
	
	/** Represents whether or not the entity is currently moving set in the movement system.*/
	public boolean moving;
	
	/** Should play until finished.	 */
	public Animation currentAnimation;

	private boolean playOnce = false;

	public HashMap<EmotionEnum, Image> faces;
	
	/**
	 * Constructor for a basic, non-animated Art object given the x and y coordinates of the cell in the spritesheet numbered <b>sheet
	 * 
	 * @param x
	 * @param y
	 * @param sheet
	 * 
	 */
	public Art(int x, int y, int sheet)
	{
		this.standXPixel = x;
		this.standYPixel = y;
		this.sheetNumber = sheet;
		this.id = ComponentID.ART;
		animations = new Hashtable<AnimationType, Animation>();
		displayingMain = true;
		faces = new HashMap<EmotionEnum, Image>();
	}
	
	/**
	 * To be used only for temporary art.
	 */
	public Art(Animation myAnimation)
	{
		currentAnimation = myAnimation;
		playOnce = true;
	}
	
	/**
	 * Get the Art component ID number
	 * 
	 * @return int Id
	 */
	public int getID()
	{
		return ComponentID.ART.ordinal();
	}
	
	/**
	 * Pass in the current spritesheet and render from it. GIVE LITERAL X AND Y
	 * @param inUse
	 * @param X
	 * @param Y
	 */
	public void renderInUse(int x, int y, TheCosmicLoafGame game, Camera cam)
	{
		int xAdjust = (int) (x * cam.getZoom());
		int yAdjust = (int) (y * cam.getZoom());
		
		if(facing == Direction.DOWN)
		{
			if(cam.getZoom() != 1)
			{
				Image img = RenderSystem.using.getSubImage(standXPixel, standYPixel);
				RenderSystem.using.endUse();
				img.draw(xAdjust, yAdjust, (float)cam.getZoom());
				RenderSystem.using.startUse();

			}
			else
			{
				RenderSystem.using.renderInUse(xAdjust, yAdjust, standXPixel, standYPixel);
			}
		}
		else if(facing == Direction.UP)
		{
			if(cam.getZoom() != 1)
			{
				Image img = RenderSystem.using.getSubImage(upXPixel, upYPixel);
				RenderSystem.using.endUse();
				img.draw(xAdjust, yAdjust, (float)cam.getZoom());
				RenderSystem.using.startUse();

			}
			else
			{
				RenderSystem.using.renderInUse(xAdjust, yAdjust, upXPixel, upYPixel);
			}
		}
		else if(facing == Direction.RIGHT)
		{
			if(cam.getZoom() != 1)
			{
				Image img = RenderSystem.using.getSubImage(rightXPixel, rightYPixel);
				RenderSystem.using.endUse();
				img.draw(xAdjust, yAdjust, (float)cam.getZoom());
				RenderSystem.using.startUse();

			}
			else
			{
				RenderSystem.using.renderInUse(xAdjust, yAdjust, rightXPixel, rightYPixel);
			}
		}
		else if(facing == Direction.LEFT)
		{
			if(facingLeft == null)
			{
				facingLeft = Assets.getSpriteSheets().get(sheetNumber).getSubImage(rightXPixel, rightYPixel);
				facingLeft = facingLeft.getFlippedCopy(true, false);
			}
			RenderSystem.using.endUse();
			facingLeft.draw(xAdjust, yAdjust, (float)cam.getZoom());
			RenderSystem.using.startUse();
		}
		else //Standing(basic)
		{
			if(displayingMain)
			{
				if(cam.getZoom() != 1)
				{
					Image img = RenderSystem.using.getSubImage(standXPixel, standYPixel);
					RenderSystem.using.endUse();
					img.draw(xAdjust, yAdjust, (float)cam.getZoom());
					RenderSystem.using.startUse();

				}
				else
				{
					RenderSystem.using.renderInUse(xAdjust, yAdjust, standXPixel, standYPixel);
				}
			}
			else
			{
				if(cam.getZoom() != 1)
				{
					Image img = RenderSystem.using.getSubImage(standXPixel, standYPixel);
					RenderSystem.using.endUse();
					img.draw(xAdjust, yAdjust, (float)cam.getZoom());
					RenderSystem.using.startUse();
				}
				else
				{
					RenderSystem.using.renderInUse(xAdjust, yAdjust, altStandXPixel, altStandYPixel);
				}
			}
		}
	}
	
	/**
	 * INEFFICENT! renders the art at a specific location. GIVE LITERAL X AND Y
	 */
	public void render(int x, int y, TheCosmicLoafGame game, Camera cam)
	{
		int xAdjust = (int) (x * cam.getZoom());
		int yAdjust = (int) (y * cam.getZoom());
//		SpriteSheet inUse = Assets.getSpriteSheets().get(sheetNumber);
//		RenderSystem.using = inUse;
//		RenderSystem.using.startUse();
		RenderSystem.using.endUse();
		currentAnimation.draw(xAdjust, yAdjust, game.getAdjustedSquareWidth(cam), game.getAdjustedSquareHeight(cam));
		RenderSystem.using.startUse();
	}
	
	/**	 
	 * Toggles between main and regular standing animation.
	 */
	public void toggle()
	{
		this.displayingMain = !displayingMain;
	}
	
	/** Renders the next frame of the current Animation. GIVE LITERAL X AND Y
	 *	 
	 * @param eXvalue
	 * @param eYvalue
	 */
	public void renderCurrentAnimation(int X, int Y, Entity me, TheCosmicLoafGame game, Camera cam) {

		int xAdjust = (int) (X * cam.getZoom());
		int yAdjust = (int) (Y * cam.getZoom());
		if(currentAnimation != null)
		{
			
			RenderSystem.using.endUse();
			currentAnimation.draw(xAdjust, yAdjust, game.getAdjustedSquareWidth(cam), game.getAdjustedSquareHeight(cam));
			RenderSystem.using.startUse();
		}
		else
		{
			if(playOnce)
			{
				
				RenderSystem.using.endUse();
				currentAnimation.draw(xAdjust, yAdjust, game.getAdjustedSquareWidth(cam),  game.getAdjustedSquareHeight(cam));
				RenderSystem.using.startUse();
				if(currentAnimation.isStopped())
				{
					game.removeEntity(me);
				}
			}
		}
	}
	
	public void setRight(int x, int y)
	{
		this.rightXPixel = x;
		this.rightYPixel = y;
	}
	
	public void setUp(int x, int y)
	{
		System.out.println("Setting");
		this.upXPixel = x;
		this.upYPixel = y;
	}
	
	public void setStand(int x, int y)
	{
		this.standXPixel = x;
		this.standYPixel = y;
	}
	
	public void setAlt(int x, int y)
	{
		this.altStandXPixel = x;
		this.altStandYPixel = y;
	}
}
