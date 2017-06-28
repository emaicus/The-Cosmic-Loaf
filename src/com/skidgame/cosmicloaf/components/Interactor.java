package com.skidgame.cosmicloaf.components;

public class Interactor extends Component
{
	
	public Entity target;
	
	public Interactor()
	{
	}

	@Override
	public int getID() {
		return ComponentID.INTERACTOR.ordinal();
	}
	
}
