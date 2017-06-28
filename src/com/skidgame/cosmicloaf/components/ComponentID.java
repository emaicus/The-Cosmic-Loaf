/**
 * 
 */
package com.skidgame.cosmicloaf.components;

/**
 * Enum class that for all the component ID's that are in the system
 * 
 * @author Theko Lekena
 * 
 * @NOTE Should a new Component be created, a new component ID should be added to the list with the same name as the component class. 
 * Follow the consistent style endorsed below
 *
 */
public enum ComponentID 
{
	/** The Id for the ART class component */
	ART,
	/** The Id for the POSITION class component */
	POSITION,
	/** The Id for the PLAYER class component */
	PLAYER,
	/** The Id for the INTERACTABLE class component */
	INTERACTIBLE,
	/** The Id for the COLLIDABLE class component */
	COLLIDABLE,
	/** The Id for the MOVEMENT_OPTIONS class component */
	MOVEMENT_OPTIONS,
	/** The Id for the INTERACTOR class component */
	INTERACTOR,
	/** The Id for the AI_INPUT class component */
	AI_INPUT, 
	
	/** The Id for the BUTTON_COMPONENT class component */
	BUTTON_COMPONENT, 
	
	/** The Id for the GUI_DATA class component */
	GUI_DATA, 
	
	/** The Id for the INVENTORY class component	 */
	INVENTORY,
	
	/** The Id for the DESTRUCTIBLE class component */
	DESTRUCTIBLE, 
	
	/** The Id for the WEAPON class component	 */
	WEAPON, 
	
	/** The Id for the Health class component	 */
	HEALTH, 
	
	/**Indicates that this entity makes sounds.	 */
	SOUND, 
	
	/** Indicates that this entity has dialog.	 */
	DIALOG;

	/** The number of components known in the system*/	
	public static final int length = ComponentID.values().length;
	
	
}
