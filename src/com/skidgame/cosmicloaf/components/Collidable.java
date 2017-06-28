package com.skidgame.cosmicloaf.components;

import java.util.ArrayList;

public class Collidable extends Component
{
	boolean flagForRemoval;
	public Collidable()
	{
		flagForRemoval = false;
	}
	public boolean flagForRemoval()
	{
		if (!flagForRemoval){
			flagForRemoval = true;
			return flagForRemoval;
		}
		return false;
	}
	public int getID() {
		return ComponentID.COLLIDABLE.ordinal();
	}

}
