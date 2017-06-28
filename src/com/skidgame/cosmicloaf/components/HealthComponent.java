package com.skidgame.cosmicloaf.components;

import com.skidgame.cosmicloaf.game.TheCosmicLoafGame;

public class HealthComponent extends Component {
	
	public boolean invulnerable;
	public int maxHealth;
	public int currentHealth;
	
	public HealthComponent(boolean invulnerable, int maxHealth)
	{
		this(invulnerable, maxHealth, maxHealth);
	}

	public HealthComponent(boolean invulnerable, int maxHealth, int currentHealth)
	{
		this.maxHealth = maxHealth;
		this.invulnerable = invulnerable;
		this.currentHealth = currentHealth;
	}

	public boolean isInvulnerable() {
		return this.invulnerable;
	}

	public void decreaseBy(int damage, TheCosmicLoafGame game, Entity me) {
		currentHealth -= damage;
		
		if(currentHealth <= 0)
		{
			game.removeEntity(me);
		}
	}
	
	public void increaseBy(int damageHealed)
	{
		this.currentHealth += damageHealed;
		
		if(this.currentHealth > this.maxHealth)
		{
			this.currentHealth = this.maxHealth;
		}
	}

	@Override
	public int getID() {
		return ComponentID.HEALTH.ordinal();
	}

	public boolean isDead() {
	//	System.out.println("Current Health is " + currentHealth);
		if(currentHealth <= 0)
		{
		//	System.out.println("Current Health was less than zero, returning true");
			return true;
		}
		else
		{
		//	System.out.println("Current health was greater than zero, returning false.");
			return false;
		}
	}

	

}
