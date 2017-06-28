package com.skidgame.cosmicloaf.components;

import java.util.ArrayList;

public class Inventory extends Component
{
	private int money;
	ArrayList<Entity> inventory = new ArrayList<Entity>();
	private ComponentID id;
	
	/**
	 * Creates an object of type Inventory with an initial amount of money <code>m</code> and inventory <code>entities</code>.
	 * 
	 * @param m
	 * 		the amount of money in the Inventory Component.
	 * @param entities
	 * 		the entities in the Inventory Component.
	 */
	public Inventory(int m, Entity... entities)
	{
		this.money = m;
		
		for(Entity e : entities)
			this.inventory.add(e);
	}
	
	/**
	 * Gets the amount of money in this Inventory Component.
	 * 
	 * @return
	 * 		the amount of money in this Inventory Component.
	 */
	public int getMoney()
	{
		return this.money;
	}
	
	/**
	 * Gets the inventory in this Inventory Component.
	 * 
	 * @return
	 * 		the inventory in this Inventory Component.
	 */
	public ArrayList<Entity> getInventory()
	{
		return this.inventory;
	}
	
	/**
	 * Gets the Entity in the inventory at index <code>i</code>.
	 * 
	 * @param i
	 * 		the index of the Entity to be obtained.
	 * @return
	 * 		the Entity at index <code>i</code>.
	 */
	public Entity get(int i)
	{
		return this.inventory.get(i);
	}
	
	/**
	 * Sets the money in this Inventory Component to the value of <code>m</code>.
	 * 
	 * @param m
	 * 		the new value of the money in this Inventory Component.
	 */
	public void setMoney(int m)
	{
		this.money = m;
	}
	
	/**
	 * Adds the specified amount of money <code>m</code> to this Inventory Component's money.
	 * 
	 * @param m
	 * 		the amount of money to be added.
	 */
	public void addMoney(int m)
	{
		this.money += m;
	}
	
	/**
	 * Adds the specified Entity <code>e</code> to this Inventory Component's inventory.
	 * 
	 * @param e
	 * 		the Entity to be added.
	 */
	public void addEntity(Entity e)
	{
		this.inventory.add(e);
	}
	
	/**
	 * Removes the specified Entity <code>e</code> from this Inventory Component's inventory.
	 * 
	 * @param e
	 * 		the Entity to be removed.
	 */
	public void removeEntity(Entity e)
	{
		for(int i = 0; i < this.inventory.size(); i++)
		{
			if(this.inventory.get(i).equals(e))
			{
				this.inventory.remove(i);
				break;
			}
		}
	}
	
	@Override
	public int getID()
	{
		return id.ordinal();
	}
}
