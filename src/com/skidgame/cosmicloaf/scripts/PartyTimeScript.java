package com.skidgame.cosmicloaf.scripts;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import com.skidgame.cosmicloaf.assets.Assets;
import com.skidgame.cosmicloaf.components.Art;
import com.skidgame.cosmicloaf.components.ComponentID;
import com.skidgame.cosmicloaf.components.Entity;
import com.skidgame.cosmicloaf.game.SkidmoreGameWorldLoader;
import com.skidgame.cosmicloaf.game.TheCosmicLoafGame;

public class PartyTimeScript extends Script {

	public PartyTimeScript(TheCosmicLoafGame game) {
		super(game);
	}

	public void execute()
	{
		System.out.println("Executed Party time Script");
		Art art = new Art(1, 0, 1);
		
		SpriteSheet sheet = Assets.getSpriteSheets().get(1);
		Image right[] = new Image[1];
		right[0] = sheet.getSubImage(1, 0); //Look right
		art.setRight(1,  0);
		Image down[] = new Image[1];
		down[0] = sheet.getSubImage(1, 0); //Look down
		art.setStand(1,  0);
		Image left[] = new Image[1];
		left[0] = sheet.getSubImage(1, 0).getFlippedCopy(true, false); //Look left	
		Image up[] = new Image[1];
		up[0] = sheet.getSubImage(1, 0).getFlippedCopy(true, false);
		art.setUp(1,  0);
		

		
	
		
		
		
		Entity player = game.getCurrentLevel().getPlayer().get(0);
//		
		player.getComponents()[ComponentID.ART.ordinal()] = art;
		
		SkidmoreGameWorldLoader.sortSpriteSheets(game.getCurrentLevel().getEntities());
	}
	
	
}

