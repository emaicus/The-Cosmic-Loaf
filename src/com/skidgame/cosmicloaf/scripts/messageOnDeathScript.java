
package com.skidgame.cosmicloaf.scripts;

import java.util.ArrayList;

import com.skidgame.cosmicloaf.components.Art;
import com.skidgame.cosmicloaf.components.Collidable;
import com.skidgame.cosmicloaf.components.Coordinate;
import com.skidgame.cosmicloaf.components.Entity;
import com.skidgame.cosmicloaf.components.HealthComponent;
import com.skidgame.cosmicloaf.components.Interactible;
import com.skidgame.cosmicloaf.components.Position;
import com.skidgame.cosmicloaf.components.Trigger;
import com.skidgame.cosmicloaf.game.TheCosmicLoafGame;

public class messageOnDeathScript extends Script{

	public messageOnDeathScript(TheCosmicLoafGame game) {
		super(game);
		// TODO Auto-generated constructor stub
	}
	
	public void execute()
	{
		System.out.println("Executing.");
		Art talkerArt = new Art(4,0,0);
		Position talkerPos = new Position(game, new Coordinate((10 * 32),(10 * 32)));
		talkerPos.setVisible(true);
		Collidable talkerCollidable = new Collidable();
		Interactible talkerInter = new Interactible(false, false, false, true, false);
		talkerInter.setMyLines("You are a terrible person.", "I hate you", "I hope you die.", "Bring me a shrubbery.");
		HealthComponent talkerHealth = new HealthComponent(false, 2);
		Entity angryGuy = new Entity(talkerCollidable, talkerPos, talkerInter, talkerHealth, talkerArt);
		System.out.println("Adding talker.");

		game.addEntity(angryGuy);
		
		Trigger bringMeAShrubbery = new Trigger();
		bringMeAShrubbery.active = true;
		bringMeAShrubbery.ratio = 1;
		bringMeAShrubbery.startOnConversation = true;
		bringMeAShrubbery.dialogNumber = 4;
		bringMeAShrubbery.triggerEntities = new ArrayList<Entity>();
		bringMeAShrubbery.triggerEntities.add(angryGuy);
		bringMeAShrubbery.executesScripts = true;
		Script mscr = new PartyTimeScript(game);
		bringMeAShrubbery.scripts = new ArrayList<Script>();
		bringMeAShrubbery.scripts.add(mscr);
		game.addTrigger(bringMeAShrubbery);	
	}

}

