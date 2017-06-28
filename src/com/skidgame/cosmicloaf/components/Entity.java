package com.skidgame.cosmicloaf.components;
import java.util.ArrayList;
import org.newdawn.slick.util.pathfinding.Mover;

/**
 * This is an entity class which describes a game object and what systems it implements by the components passed in
 * 
 * @author 
 *
 */
public class Entity implements Mover 
{
	/** The next ID number to be assigned to an Entity object on creation */
	private static long nextId = 0;

	/** This entity's ID number*/
	private long id; 

	/** The components assigned  to this entity*/
	private Component[] components;

	/**
	 * Create an entity with the components that make it up.
	 * Components associated with what system must act on the entity in-game
	 * 
	 * @param components
	 */
	public Entity(Component... components)
	{
		this.components = new Component[ComponentID.length];
		for(Component c : components)
		{
			this.components[c.getID()] = c;
		}
		this.id = nextId;
		nextId++;
	}
	
	public Entity(ArrayList<Component> components)
	{
		this.components = new Component[ComponentID.length];
		for(Component c : components)
		{
			this.components[c.getID()] = c;
		}
		this.id = nextId;
		nextId++;
	}

	/**
	 * Get all the components associated with this entity
	 * 
	 * @return components
	 */
	public Component[] getComponents() {
		return components;
	}

	public long getId()
	{
		return this.id;
	}


	/**
	 * Gets the entities art. MAY RETURN NULL.
	 * @return
	 */
	public Art getArt()
	{
		return (Art) components[ComponentID.ART.ordinal()];
	}

	/**
	 * Gets the entity's Position. MAY RETURN NULL.
	 * @return
	 */
	public Position getPosition()
	{
		return (Position) components[ComponentID.POSITION.ordinal()];
	}

	public void setArt(Art art)
	{
		components[ComponentID.ART.ordinal()] = art;

	}

	public InventoryComponent getInventory() {
		return (InventoryComponent) this.components[ComponentID.INVENTORY.ordinal()];
	}

	public boolean removeComponent(ComponentID componentToRemove){


		if (components[componentToRemove.ordinal()] == null)
		{
			return false;
		}
		else
		{
			components[componentToRemove.ordinal()] = null;
			return true;
		}
	}

	public void setPosition(Position object) {
		components[ComponentID.POSITION.ordinal()] = object;
		
	}

	public HealthComponent getHealth() {
		return ((HealthComponent)components[ComponentID.HEALTH.ordinal()]);
	}
	
	public MovementOptions getMovementOptions()
	{
		return (MovementOptions) components[ComponentID.MOVEMENT_OPTIONS.ordinal()];
	}

	public Collidable getCollidable() 
	{
		// TODO Auto-generated method stub
		return (Collidable) components[ComponentID.COLLIDABLE.ordinal()];	

		}
	
	public AiInput getAiComponent() {
		// TODO Auto-generated method stub
		return (AiInput) components[ComponentID.AI_INPUT.ordinal()];	
		}
	
	public  Interactible getInteractible()
	{
		return (Interactible) components[ComponentID.INTERACTIBLE.ordinal()];
	}
	
	@Override
	public String toString()
	{
		return "" + id + "With art at sheet " + ((Art)components[ComponentID.ART.ordinal()]).sheetNumber + " with coords " + ((Art)components[ComponentID.ART.ordinal()]).standXPixel + "," + ((Art)components[ComponentID.ART.ordinal()]).standYPixel;
	}
	
}


/*
 * Entity player = new Entity;
 * Position playerStart = new Position(2,3);
 * PlayerComponent IAmAPlayer = new PlayerComponent();
 * Art art = new Art(0,0,1)
 * player.add(playerStart, IAmAPlayer, playerArt);
 * 
 */
