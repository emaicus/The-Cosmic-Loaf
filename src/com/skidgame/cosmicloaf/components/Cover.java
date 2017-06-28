package com.skidgame.cosmicloaf.components;

/**
 * This class make an game object coverable. Allowing game objects able to be take cover by it
 * 
 * @author 
 *
 */
public class Cover extends Component
{
	private boolean isCover;
	private ComponentID id;
	
	/**
	 * Creates an object of type Cover with an initial cover value <code>b</code>.
	 * 
	 * @param b
	 * 		the cover value of this Entity.
	 */
	public Cover(boolean b)
	{
		this.isCover = b;
	}

	/**
	 * Checks whether the Entity is cover or not.
	 * 
	 * @return
	 * 		the cover value of this Entity.
	 */
	public boolean isCover() 
	{
		return isCover;
	}

	/**
	 * Sets the cover value of this Entity to the value of <code>b</code>.
	 * 
	 * @param b
	 * 		the new cover value of this Entity.
	 */
	public void setCover(boolean b) 
	{
		this.isCover = b;
	}
	
	@Override
	public int getID()
	{
		return id.ordinal();
	}
}
