package com.skidgame.cosmicloaf.components;

/**
 * Abstract Component class for for all components to extend from. 
 * @author mlekena
 *
 */
public abstract class Component 
{
	/**
	 * empty constructor
	 */
	public Component()
	{
		
	}
	
	/**
	 * method for identifying this component in the system
	 * 
	 * @return
	 */
	abstract public int getID();
}
