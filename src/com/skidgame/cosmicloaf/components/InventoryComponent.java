package com.skidgame.cosmicloaf.components;

public class InventoryComponent extends Component{

	public InventoryComponent() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getID() {
		return ComponentID.INVENTORY.ordinal();
	}

	public void pickUp(Entity pickingUp) {
		//TODO add this entity to the inventory.
		
	}

}
