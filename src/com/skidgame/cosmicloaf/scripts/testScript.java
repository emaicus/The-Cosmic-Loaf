package com.skidgame.cosmicloaf.scripts;

import com.skidgame.cosmicloaf.components.ComponentID;
import com.skidgame.cosmicloaf.components.*;
import com.skidgame.cosmicloaf.game.TheCosmicLoafGame;

public class testScript extends Script {

	public testScript(TheCosmicLoafGame game) {
		super(game);
	}
	
	public void execute()
	{
		//game.gameCamera.setTracking(false);
		
		Destructible d = (Destructible)game.getCurrentLevel().getEntities().get(0).getComponents()[ComponentID.DESTRUCTIBLE.ordinal()];
		d.destruct(game.getGameCamera());
	}

}
