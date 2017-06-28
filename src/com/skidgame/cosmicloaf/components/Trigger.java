package com.skidgame.cosmicloaf.components;

import java.util.ArrayList;

import com.skidgame.cosmicloaf.game.Camera;
import com.skidgame.cosmicloaf.game.Region;
import com.skidgame.cosmicloaf.game.TheCosmicLoafGame;
import com.skidgame.cosmicloaf.scripts.Script;
import com.skidgame.cosmicloaf.systems.CollisionSystem;

/**
 * Trigger Warning!
 * 
 * A trigger causes an event inGame to Occur.
 * @author emaicus
 *
 */
public class Trigger 
{
	/** Represents whether the event can currently take place. */
	public boolean active;
	
	/**
	 * The code below defines what causes a trigger to run.
	 */
	
	/** The entities that set off the trigger.*/
	public ArrayList<Entity> triggerEntities;
	
	/** True if the trigger starts upon the death of one or more of the triggering entities.*/
	public boolean StartOnDeath;
	
	/** True if the trigger starts upon the activation of one or more of the triggering entities.*/
	public boolean startOnActivation;
	
	/** True if the trigger starts after the character speaks to a triggering entity	 */
	public boolean startOnConversation;
	
	/** Represents the index of the array at which the dialog option takes place.	 */
	public int dialogNumber;
	
	public boolean startOnEntry;
	
	public boolean startOnExit;
	
	public Region region;
	
	/** Num entites/ratio = number of entities that must be effected for trigger to fire. */
	public int ratio;
	
	
	/**
	 * The code below defines the effects of the trigger.
	 */
	
	/**	True if one or more entities are directly effected by the trigger. */
	public boolean effectsEntities;

	/** The entities directly effected by the trigger.*/
	public ArrayList<Entity> entitiesEffected;
	
	/**	Set to true if the entities effected are to be harmed.  */
	public boolean harms;	
	
	/** Set to true if the entities effected are to be healed.	 */
	public boolean heals;
	
	/**	The amount of damage to be dealt. Set high to kill, but don't overdo it (wrap around)*/
	public int damageAmount;
	
	/**	Set to true if the entities effected are to become active.  */
	public boolean activates;
	
	/**	Set to true if the entities effected are to be spawned.  */
	public boolean spawns;
	
	/** Set to true if the entities effected are to be despawned. */
	public boolean despawns;
	
	/**	Set to true if scripts are to be executed.*/
	public boolean executesScripts;
	
	/** A list of the scripts to be executed. */
	public ArrayList<Script> scripts;
	
	/** If true, the fire method can be called.	 */
	public boolean readyToFire;
	
	/** True if the trigger has fired	 */
	public boolean Fired;

	/**
	 * Full Constructor. It may be easier to buid a trigger and then just merely set attributes about it.
	 * @param active
	 * @param triggerEntities
	 * @param startOnDeath
	 * @param startOnActivation
	 * @param ratio
	 * @param effectsEntities
	 * @param entitiesEffected
	 * @param opens
	 * @param harms
	 * @param damageAmount
	 * @param activates
	 * @param spawns
	 * @param executesScripts
	 * @param scripts
	 */
	public Trigger(boolean active, ArrayList<Entity> triggerEntities,
			boolean startOnDeath, boolean startOnActivation, int ratio,
			boolean effectsEntities, ArrayList<Entity> entitiesEffected,
			boolean heals, boolean harms, int damageAmount, boolean activates,
			boolean spawns, boolean despawns, boolean executesScripts, ArrayList<Script> scripts) {
		super();
		this.active = active;
		this.triggerEntities = triggerEntities;
		StartOnDeath = startOnDeath;
		this.startOnActivation = startOnActivation;
		this.ratio = ratio;
		this.effectsEntities = effectsEntities;
		this.entitiesEffected = entitiesEffected;
		this.heals = heals;
		this.harms = harms;
		this.damageAmount = damageAmount;
		this.activates = activates;
		this.spawns = spawns;
		this.despawns = despawns;
		this.executesScripts = executesScripts;
		this.scripts = scripts;
	}

	/**
	 * Generates empty trigger.Manually set parts.
	 */
	public Trigger()
	{
		
	}
	
	
	private int deathCount = 0;
	private int activateCount = 0;
	private int talkedCount = 0;
	/**Updates the trigger based on conditions.
	 * 
	 * @return true if activated
	 */
	public boolean update(TheCosmicLoafGame game)
	{
		int numInOrOut = 0;
		if(active)
		{

			int amountNeeded = 0;
			
			if(ratio > 0)
			{
				amountNeeded = triggerEntities.size()/ratio;
			}
			
			if(amountNeeded <=0)
			{
				amountNeeded = 1;
			}
			
			
			

			for(Entity e : triggerEntities)
			{

				if(StartOnDeath)
				{
				//	System.out.println("Supposed to start on death.");
					if(e != null)
					{
						if(e.getHealth() != null)
						{
							if(e.getHealth().isDead())
							{
							//	System.out.println("Incremented because he is dead.");
								deathCount++;
							}
						}
						else
						{
						//	System.out.println("Incremented because he has no health.");
							deathCount++;
						}
					}
					else
					{
					//	System.out.println("Incremented because he was null.");
						deathCount++;
					}
				}
				
				if(startOnActivation)
				{
					Interactible inter = (Interactible) e.getComponents()[ComponentID.INTERACTIBLE.ordinal()];
					
					if(inter != null)
					{
						if(inter.wasActivated)
						{
							System.out.println("was activated");
							activateCount++;
						}
					}
				
				}
				
				if(startOnConversation)
				{
					System.out.println("This trigger goes off on conversation at line " + dialogNumber);
					Interactible inter = (Interactible) e.getComponents()[ComponentID.INTERACTIBLE.ordinal()];
					
					if(inter != null)
					{
						System.out.println("Target was interactible and dialogNumber was " + inter.currentLine);
						if(inter.currentLine == dialogNumber)
						{
							System.out.println("Has been talked to");
							talkedCount++;
						}
					}
					else
					{
						System.out.println("target wasn't interactible");
					}
				}
				
				if(startOnEntry)
				{
					if(CollisionSystem.enteredRegion(0, 0, e, game, game.getCurrentLevel(), region))
					{
						numInOrOut++;
					}
				}
				
				if(startOnExit)
				{
					if(!CollisionSystem.enteredRegion(0, 0, e, game, game.getCurrentLevel(), region))
					{
						numInOrOut++;
					}
				}
			}
			
			if(startOnEntry)
			{
				if(numInOrOut >= amountNeeded)
				{
					this.readyToFire = true;
				}
			}
			
			if(startOnExit)
			{
				if(numInOrOut >= amountNeeded)
				{
					this.readyToFire = true;
				}
			}
			
			if(StartOnDeath)
			{
				if (deathCount >= amountNeeded)
				{
					this.readyToFire = true;
				}
			}
			
			
			if(startOnActivation)
			{
				if (activateCount >= amountNeeded)
				{
					System.out.println("Ready to fire!");
					this.readyToFire = true;
				}
			}
			
			if(startOnConversation)
			{
				if (talkedCount >= amountNeeded)
				{
					System.out.println("Ready to fire!");
					this.readyToFire = true;
				}
			}
			 
		}
		
		return readyToFire; //ReadyToFire starts false, so this will return false until it is toggled.
	}
	
	/**
	 * Executes the trigger. Only call if readyToFire or if overriding.
	 * @param game
	 * @return
	 */
	public void fire(TheCosmicLoafGame game, Camera cam)
	{
		System.out.println("Fire!!!");

		if(effectsEntities)
		{
			System.out.println("This trigger effects entities.");
			for(Entity e : entitiesEffected)
			{
				if(harms)
				{
					//TODO need health code.
					//harm by damageAmount
				}
				if(heals)
				{
					//TODO need health code.
					//heal by damageAmount
				}
			
				
				if(activates)
				{
					Interactible inter = (Interactible) e.getComponents()[ComponentID.INTERACTIBLE.ordinal()];
					if(inter != null)
					{
						inter.activate(e, game);
					}
				}
			
				if(spawns)
				{
					game.getCurrentLevel().addEntities(entitiesEffected, game, cam);
				}
				if(despawns)
				{
					for(Entity boggart : entitiesEffected)
					{
						game.removeEntity(boggart);
					}					

				}
			}
			
		
		}
		else
		{
			System.out.println("This trigger does not effect entities.");
		}
		if(executesScripts)
		{
			for(Script scr : scripts)
			{
				System.out.println("executing.");
				scr.execute();
			}
		}
		
		
		this.Fired = true;
		this.readyToFire = false;
	}
	
	/**
	 * Resets the trigger. If prime is true, sets the trigger to active.
	 * @param prime
	 */
	public void reset(boolean prime)
	{
		this.Fired = false;
		this.readyToFire = false;
		this.active = prime;
	}

}
