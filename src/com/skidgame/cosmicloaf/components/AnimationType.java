package com.skidgame.cosmicloaf.components;

/**
 * An enum representing types of animation.
 * @author emaicus
 *
 */
public enum AnimationType 
{

	/** Animation looking downward and standing still.	 */
	STAND, 
	
	/** Animation looking Right and standing still.	 */
	STAND_RIGHT, 
	
	/** Animation looking left and standing still.	 */
	STAND_LEFT, 
	
	/** Animation looking upward and standing still.	 */
	STAND_UP, 
	
	/** Animation looking right and moving.	 */
	WALK_RIGHT, 
	
	/** Animation looking left and moving.	 */
	WALK_LEFT, 
	
	/** Animation looking up and moving.	 */
	WALK_UP, 
	
	/** Animation looking down and moving.	 */
	WALK_DOWN, 
	
	/** An animation for BEING opened. Do not confuse with OPEN_ACTOR	 */
	OPEN, 
	
	/** An animation for BEING Closed. Do not confuse with OPEN_ACTOR	 */
	CLOSE, 
	
	/** An animation for BEING picked up. Do not confuse with PICK_UP_ACTOR	 */
	PICK_UP, 
	
	/** An animation for talking to something.	 */
	TALK, 
	
	/** An animation for reading something.	 */
	READ, 
	
	/** An animation for opening something	 */
	OPEN_ACTOR, 
	
	/** An animation for picking something up	 */
	PICK_UP_ACTOR, 
	
	/** A generic animation for being activated. Bar minimum, all interactibles should have this.	 */
	ACTIVATE, 
	
	/** A generic animation for activating. Bar minimum, all interactors should have this.	 */
	ACTIVATE_ACTOR,
	
	/** An animation for destroying an object.	 */
	DESTROYED,
	
	ATTACK_ACTOR,
	
	ATTACK_WEAPON, 
	
	DAMAGED;
}
