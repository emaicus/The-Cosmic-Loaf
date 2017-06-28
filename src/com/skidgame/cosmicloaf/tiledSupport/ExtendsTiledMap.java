package com.skidgame.cosmicloaf.tiledSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import com.skidgame.cosmicloaf.assets.Assets;
import com.skidgame.cosmicloaf.components.Art;
import com.skidgame.cosmicloaf.components.Collidable;
import com.skidgame.cosmicloaf.components.Component;
import com.skidgame.cosmicloaf.components.Coordinate;
import com.skidgame.cosmicloaf.components.Entity;
import com.skidgame.cosmicloaf.components.HealthComponent;
import com.skidgame.cosmicloaf.components.Interactible;
import com.skidgame.cosmicloaf.components.Position;
import com.skidgame.cosmicloaf.components.Trigger;
import com.skidgame.cosmicloaf.constants.EmotionEnum;
import com.skidgame.cosmicloaf.game.Dialog;
import com.skidgame.cosmicloaf.game.DialogChain;
import com.skidgame.cosmicloaf.game.DialogTree;
import com.skidgame.cosmicloaf.game.TheCosmicLoafGame;
import com.skidgame.cosmicloaf.scripts.PartyTimeScript;
import com.skidgame.cosmicloaf.scripts.Script;
import com.skidgame.cosmicloaf.systems.CollisionSystem;


public class ExtendsTiledMap extends TiledMap{
	public ArrayList<Entity> loadedEntities = new ArrayList<Entity>();
	public Position playerStart;
	public ArrayList<Trigger> myTriggers ;

	public ExtendsTiledMap(String ref, String tileSetsLocation, TheCosmicLoafGame game)	throws SlickException
	{

		
		super(ref, tileSetsLocation);

		
		
		
		HashMap<String, Trigger> triggers = new HashMap<String, Trigger>();
		myTriggers = new ArrayList<Trigger>();

	//	int layer = 0; 
		for(int layer = 0; layer < this.getLayerCount(); layer++)
		{
			for(int i = 0; i < this.getWidth(); i++) 
			{
			    for(int j = 0; j < this.getHeight(); j++) 
			    {
			    	ArrayList<Component> toAdd = new ArrayList<Component>();
			        int tileID = getTileId(i, j, layer);
			        String value = getTileProperty(tileID, "playerStart", "false");
			        /**
			         * Determining if this is the player's start position
			         */
			        if(value.equals("true"))
			        {
			        	Position pos = new Position(game, new Coordinate((i * game.getLiteralSquareWidth()),(j * game.getLiteralSquareWidth())));
			        	pos.setVisible(true);
			        	playerStart = pos;
			        }

		        	
		        	
			        value = getTileProperty(tileID, "collidable", "false");
			        /**
			         * Determining if the square is collidable
			         * form "true" or section:startX,EndX,StartY,EndY
			         */
			        if(value.equals("true"))
			        {
						Collidable c = new Collidable();
						Position pos = new Position(game, new Coordinate((i * game.getLiteralSquareWidth()),(j * game.getLiteralSquareWidth())));
			        	pos.setVisible(true);
			        	toAdd.add(pos);
						toAdd.add(c);
			        }
			        if(value.contains("section"))
			        {
			        	System.out.println("About to parse: " + value);
			        	String[] vals = value.split(":");
			        	
			        	String[] numbers = vals[1].split(",");
			        	int startX = Integer.parseInt(numbers[0]);
			        	int endX = Integer.parseInt(numbers[1]);
			        	int startY = Integer.parseInt(numbers[2]);
			        	int endY = Integer.parseInt(numbers[3]);
			        	
			        	Collidable c = new Collidable();
						Position pos = new Position(game, startX, endX, startY, endY, new Coordinate((i * game.getLiteralSquareWidth()),(j * game.getLiteralSquareWidth())));
			        	pos.setVisible(true);
			        	toAdd.add(pos);
						toAdd.add(c);

			        }
			       
//			        value = getTileProperty(tileID, "art", "false");
//			        if(!value.equals("false"))
//			        {
//			        	if(value.equals("talker"))
//			        	{
//			        		
//			        	}
//			        	else
//			        	{
//			        		String[] vals = value.split(",");
//			        		//TODO code to find specific spriteSheets and boxes.
//			        	}
//			        }
			        
			        /**
			         * Determining if the square is interactible.
			         */
			        value = getTileProperty(tileID, "interaction", "false");
			        if(!value.equals("false"))
			        {
		        		String[] vals = value.split("~");

			        	if(vals[0].equals("read"))
			        	{
			        		String[] lines = vals[1].split("@");
			        		if(lines != null)
			        		{
			        			Interactible inter = new Interactible(false, false, false,
			        					true, false);
			        			inter.setMyLines(lines);
			        			toAdd.add(inter);
			        		}
			        	}
//			        	if(vals[0].equals("talk"))
//			        	{
//			        		String[] lines = vals[1].split("@");
//			        		if(lines != null)
//			        		{
//			        			Interactible inter = new Interactible(false, false, true, false, false);
//			        			inter.setMyLines(lines);
//			        			toAdd.add(inter);
//			        		}
//			        	}
			        }
			        
//			        value = getTileProperty(tileID, "health", "false");
//			        
//			        if(!value.equals("false"))
//			        {
//			        	boolean invulnerable = false;
//			        	String[] vals = value.split(",");
//			        	if(vals[0].equals("true"))
//			        	{
//			        		invulnerable = true;
//			        	}
//			        	
//			        	int health = Integer.parseInt(vals[1]);
//			        	HealthComponent hc = new HealthComponent(invulnerable, health);
//			        	toAdd.add(hc);
//			        }
//			        
			        Entity billy = null;
			        /**
			         * Building the entity
			         */
			        if(toAdd.size() > 0)
			        {
			        	Position pos = new Position(game, new Coordinate((i * game.getLiteralSquareWidth()),(j * game.getLiteralSquareWidth())));
			        	pos.setVisible(true);
			        	toAdd.add(pos);
			        	System.out.println("We have a position!!!");
			        	billy = new Entity(toAdd);
				        loadedEntities.add(billy);
				    }
			        	
			        /**
			         * Triggering Entity
			         */
			        value = getTileProperty(tileID, "triggeringEntity", "false");
			        if(!value.equals("false"))
			        {
			        	
			        	String[] vals = value.split("@");
			        	
			        	System.out.println("Loading trigger named " + vals[0]);
			        	
			        	if(triggers.get(vals[0]) == null)
			        	{
			        		Trigger trig = new Trigger();
			        		trig.ratio = 1;
			        		triggers.put(vals[0], trig);
			        		myTriggers.add(trig);
			        	}
			        	
			        	Trigger temp = triggers.get(vals[0]);
			        	if(vals[1].equalsIgnoreCase("active"))
			        	{
			        		temp.active = true;
			        	}
			        	
//			        	if(vals[2].equalsIgnoreCase("startOnDeath"))
//			        	{
//			        		temp.StartOnDeath = true;
//			        	}
			        	
			        	if(vals[3].equalsIgnoreCase("startOnConversation"))
			        	{
			        		int dialogNum = Integer.parseInt(vals[4]);
			        		temp.startOnConversation = true;
			        		temp.dialogNumber = dialogNum;
			        	}
			        	
			        	if(vals[3].equalsIgnoreCase("startOnActivation"))
			        	{
			        		temp.startOnActivation = true;
			        	}
			        	
			        	if(temp.triggerEntities == null)
			        	{
			        		temp.triggerEntities = new ArrayList<Entity>();
			        	}
			        	
			        	
			        	if(billy!= null)
			        	{
			        		temp.triggerEntities.add(billy);
			        	}
			    		
			        }
			        
			        /**
			         * Triggering Square
			         */
 			        
			        value = getTileProperty(tileID, "triggeringSquare", "false");
			        if(!value.equals("false"))
			        {
			        	
			        	String[] vals = value.split("@");
			        	
			        	System.out.println("Loading trigger named " + vals[0]);
			        	
			        	if(triggers.get(vals[0]) == null)
			        	{
			        		Trigger trig = new Trigger();
			        		trig.ratio = 1;
			        		triggers.put(vals[0], trig);
			        		myTriggers.add(trig);
			        	}
			        	
			        	Trigger temp = triggers.get(vals[0]);
			        	
			        	if(vals[1].equalsIgnoreCase("active"))
			        	{
			        		temp.active = true;
			        	}
			        	
			        	if(vals[2].equalsIgnoreCase("startOnEntry"))
			        	{
			        		temp.startOnEntry = true;
			        	}
			        	
			        	if(vals[2].equalsIgnoreCase("startOnExit"))
			        	{
			        		temp.startOnExit = true;

			        	}
			        	
			        	if(billy!= null)
			        	{
			        		temp.triggerEntities.add(billy);
			        	}
			    		
			        }
			        
			        /**
			         * affectedEntity
			         */
 			        value = getTileProperty(tileID, "affectedEntity", "false");
			        if(!value.equals("false"))
			        {
			        	
			        	String[] vals = value.split("@");
			        	
			        	System.out.println("Loading trigger named " + vals[0]);
			        	
			        	if(triggers.get(vals[0]) == null)
			        	{
			        		Trigger trig = new Trigger();
			        		trig.ratio = 1;
			        		triggers.put(vals[0], trig);
			        		myTriggers.add(trig);
			        	}
			        	
			        	Trigger temp = triggers.get(vals[0]);
			        	
			        	if(vals[1].equalsIgnoreCase("true"))
			        	{
			        		temp.harms = true;
			        		int amt = Integer.parseInt(vals[3]);
			        		temp.damageAmount = amt;
			        	}
			        	
			        	if(vals[2].equalsIgnoreCase("true"))
			        	{
			        		temp.harms = true;
			        		int amt = Integer.parseInt(vals[3]);
			        		temp.damageAmount = amt;
			        	}
			        	
			        	if(vals[4].equalsIgnoreCase("true"))
			        	{
			        		temp.activates = true;
			        	}
			        	
			        	if(vals[5].equalsIgnoreCase("spawns"))
			        	{
			        		temp.spawns = true;
			        	}
			        	
			        	if(vals[5].equalsIgnoreCase("despawns"))
			        	{
			        		temp.despawns = true;
			        	}
			        	
			        	if(vals[6].equalsIgnoreCase("true"))
			        	{
			        		temp.executesScripts = true;
			        		//TODO add code to make this work.
			        	}
			        	
			        	if(billy!= null)
			        	{
			        		temp.entitiesEffected.add(billy);
			        	}
			    		
			        }
			    }
			}	
		}

		/**
		 * Object Groups
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 */
		System.out.println("There were " + this.getObjectGroupCount() + " object groups.");
		int objectGroupCount = this.getObjectGroupCount();
		for( int group = 0; group < objectGroupCount; group++ ) // gi = object group index
		{
		    int objectCount = this.getObjectCount(group);
		    for( int object = 0; object < objectCount; object++ ) // oi = object index
		    {
		    	ArrayList<Component> toAdd = new ArrayList<Component>();
		       
		    	String value = this.getObjectProperty(group, object, "playerStart", "false");
		    	System.out.println("Value on playerStart was " + value);
		    	int x = this.getObjectX(group, object);
		    	int y = this.getObjectY(group, object);
		       
		    	if(value.equalsIgnoreCase("true"))
		        {
		        	Position pos = new Position(game, new Coordinate(x,y));
		        	pos.setVisible(true);
		        	playerStart = pos;
		        }

	        	
	        	
		    	value = this.getObjectProperty(group, object, "collidable", "false");
		        if(value.equals("true"))
		        {
					Collidable c = new Collidable();
					
					toAdd.add(c);
					
		        }
		       
		        value = this.getObjectProperty(group, object, "art", "false");
		        if(!value.equalsIgnoreCase("false"))
		        {
		        	if(value.equalsIgnoreCase("dummy"))
		        	{
		        		Art newArt = new Art(Assets.talkerX, Assets.talkerY, Assets.talkerSheet);
		        		toAdd.add(newArt);
		        	}
		        	else
		        	{
		        		String[] vals = value.split(",");
		        		//TODO code to find specific spriteSheets and boxes.
		        	}
		        }
		        
		        value = this.getObjectProperty(group, object, "interaction", "false");
		        if(!value.equalsIgnoreCase("false"))
		        {
	        		String[] vals = value.split("~");

		        	if(vals[0].equalsIgnoreCase("read"))
		        	{
		        		String[] lines = vals[1].split("@");
		        		if(lines != null)
		        		{
		        			Interactible inter = new Interactible(false, false, false,
		        					true, false);
		        			inter.setMyLines(lines);
		        			toAdd.add(inter);
		        		}
		        	}
		        	if(vals[0].equalsIgnoreCase("talk"))
		        	{
		        		String[] dialog = vals[1].split("@");
		        		if(dialog != null)
		        		{
		        			Interactible inter = new Interactible(false, false, true, false, false);
		        		//	inter.setMyLines(lines);
		        			DialogChain d = new DialogChain(true);
		        			ArrayList<Dialog> chainToBe = new ArrayList<Dialog>();
		        			for(String s : dialog)
		        			{
		        				String[] parts = s.split("#");
		        				String filePath = parts[0];
		        				String emotion = parts[1];
		        				String prompt = parts[2];
		        				String subs = parts[3];
		        				EmotionEnum myEmotion = EmotionEnum.NEUTRAL;
		        				if(emotion.equalsIgnoreCase("NEUTRAL"))
		        				{
		        					myEmotion = EmotionEnum.NEUTRAL;
		        				}
			        			chainToBe.add(new Dialog(filePath,myEmotion, prompt, subs));
		        			}
		        			d.setChain(chainToBe);
		        			DialogTree dt = new DialogTree(d);
		        			inter.setTree(dt);
		        			toAdd.add(inter);
		        		}
		        	}
		        	if(vals[0].equalsIgnoreCase("talk2"))
		        	{
		        		String[] dialog = vals[1].split("@");
		        		if(dialog != null)
		        		{
		        			Interactible inter = new Interactible(false, false, true, false, false);
		        		//	inter.setMyLines(lines);
		        			DialogChain d = new DialogChain(true);
		        			ArrayList<Dialog> chainToBe = new ArrayList<Dialog>();
		        			for(String s : dialog)
		        			{
		        				String[] parts = s.split("#");
		        				String ifilePath = parts[0];
		        				String iemotion = parts[1];
		        				String isubs = parts[2];
		        				String rfilePath = parts[3];
		        				String remotion = parts[4];
		        				String prompt = parts[5];
		        				String rsubs = parts[6];
		        				EmotionEnum iEmotionEnum = EmotionEnum.NEUTRAL;
		        				EmotionEnum rEmotionEnum = EmotionEnum.NEUTRAL;
		        				if(iemotion.equalsIgnoreCase("NEUTRAL"))
		        				{
		        					iEmotionEnum = EmotionEnum.NEUTRAL;
		        				}
		        				if(remotion.equalsIgnoreCase("NEUTRAL"))
		        				{
		        					rEmotionEnum = EmotionEnum.NEUTRAL;
		        				}
			        			chainToBe.add(new Dialog(rfilePath, rEmotionEnum, prompt, rsubs, ifilePath, iEmotionEnum, isubs));

		        			}
		        			d.setChain(chainToBe);
		        			DialogTree dt = new DialogTree(d);
		        			inter.setTree(dt);
		        			toAdd.add(inter);
		        		}
		        	}
		        }
		        
		        value = this.getObjectProperty(group, object, "health", "carrot");
		        if(!value.equals("carrot"))
		        {
		        	boolean invulnerable = false;
		        	String[] vals = value.split(",");
		        	if(vals[0].equals("true"))
		        	{
		        		invulnerable = true;
		        	}
		        	
		        	int health = Integer.parseInt(vals[1]);
		        	HealthComponent hc = new HealthComponent(invulnerable, health);
		        	toAdd.add(hc);
		        }
		        

		        Entity billy = null;
		        if(toAdd.size() > 0)
		        {
		        	Position pos = new Position(game, new Coordinate(x,y));
		        	pos.setVisible(true);
					toAdd.add(pos);
					billy = new Entity(toAdd);
			        loadedEntities.add(billy);
		        }
		        
		        
		        /**
		         * Triggering Entity
		         */
		        value = this.getObjectProperty(group, object,"triggeringEntity", "false");
		        if(!value.equals("false"))
		        {
		        	
		        	String[] vals = value.split("@");
		        	
		        	System.out.println("Loading trigger named " + vals[0]);
		        	
		        	if(triggers.get(vals[0]) == null)
		        	{
		        		Trigger trig = new Trigger();
		        		trig.ratio = 1;
		        		triggers.put(vals[0], trig);
		        		myTriggers.add(trig);
		        	}
		        	
		        	Trigger temp = triggers.get(vals[0]);
		        	if(vals[1].equalsIgnoreCase("active"))
		        	{
		        		temp.active = true;
		        	}
		        	
		        	if(vals[2].equalsIgnoreCase("startOnDeath"))
		        	{
		        		temp.StartOnDeath = true;
		        	}
		        	
		        	System.out.println("Start on conversation is written " +  vals[2]);
		        	if(vals[2].equalsIgnoreCase("startOnConversation"))
		        	{
		        		int dialogNum = Integer.parseInt(vals[3]);
		        		temp.startOnConversation = true;
		        		temp.dialogNumber = dialogNum;
		        	}
		        	
		        	if(vals[2].equalsIgnoreCase("startOnActivation"))
		        	{
		        		temp.startOnActivation = true;
		        	}
		        	
		        	if(temp.triggerEntities == null)
		        	{
		        		temp.triggerEntities = new ArrayList<Entity>();
		        	}
		        	
		        	
		        	if(billy!= null)
		        	{
		        		temp.triggerEntities.add(billy);
		        	}
		    		
		        }
		        
		        /**
		         * Triggering Square
		         */
			        
		        value = this.getObjectProperty(group, object, "triggeringSquare", "false");
		        if(!value.equals("false"))
		        {
		        	
		        	String[] vals = value.split("@");
		        	
		        	System.out.println("Loading trigger named " + vals[0]);
		        	
		        	if(triggers.get(vals[0]) == null)
		        	{
		        		Trigger trig = new Trigger();
		        		trig.ratio = 1;
		        		triggers.put(vals[0], trig);
		        		myTriggers.add(trig);
		        	}
		        	
		        	Trigger temp = triggers.get(vals[1]);
		        	
		        	if(vals[1].equalsIgnoreCase("active"))
		        	{
		        		temp.active = true;
		        	}
		        	
		        	if(vals[2].equalsIgnoreCase("startOnEntry"))
		        	{
		        		temp.startOnEntry = true;
		        	}
		        	
		        	if(vals[2].equalsIgnoreCase("startOnExit"))
		        	{
		        		temp.startOnExit = true;

		        	}
		        	
		        	if(temp.triggerEntities == null)
		        	{
		        		temp.triggerEntities = new ArrayList<Entity>();
		        	}
		        	
		        	if(billy!= null)
		        	{
		        		temp.triggerEntities.add(billy);
		        	}
		    		
		        }
		        
		        /**
		         * affectedEntity
		         */
		        
			    value = this.getObjectProperty(group, object, "affectedEntity", "false");
		        System.out.println("affectedEntity was spelled " + value);
			    if(!value.equals("false"))
		        {
		        	System.out.println("Setting up triggering entity");
		        	String[] vals = value.split("@");
		        	
		        	System.out.println("Affected entity for trigger named " + vals[0]);
		        	
		        	if(triggers.get(vals[0]) == null)
		        	{
		        		Trigger trig = new Trigger();
		        		trig.ratio = 1;
		        		triggers.put(vals[0], trig);
		        		myTriggers.add(trig);
		        	}
		        	
		        	Trigger temp = triggers.get(vals[0]);
		        	
		        	if(vals[1].equalsIgnoreCase("true"))
		        	{
		        		temp.harms = true;
		        		int amt = Integer.parseInt(vals[3]);
		        		temp.damageAmount = amt;
		        	}
		        	
		        	if(vals[2].equalsIgnoreCase("true"))
		        	{
		        		temp.heals = true;
		        		int amt = Integer.parseInt(vals[3]);
		        		temp.damageAmount = amt;
		        	}
		        	
		        	if(vals[4].equalsIgnoreCase("true"))
		        	{
		        		temp.activates = true;
		        	}
		        	
		        	if(vals[5].equalsIgnoreCase("spawns"))
		        	{
		        		temp.spawns = true;
		        		loadedEntities.remove(billy);
		        	}
		        	if(vals[5].equalsIgnoreCase("despawns"))
		        	{
		        		temp.despawns = true;
		        	}
		        	
		        	if(vals[6].equalsIgnoreCase("true"))
		        	{
		        		temp.executesScripts = true;
		        		//TODO add code to make this work.
		        	}
		        	
		        	if(temp.entitiesEffected == null)
		        	{
		        		temp.entitiesEffected = new ArrayList<Entity>();
		        	}
		        	
		        	if(billy!= null)
		        	{
		        		temp.effectsEntities = true;
		        		temp.entitiesEffected.add(billy);
		        	}
		    		
		        }
		    }
		        
		        
		        
	       
		    
		}
		
		
		
	}

	
}
