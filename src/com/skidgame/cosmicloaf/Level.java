package com.skidgame.cosmicloaf;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

import com.skidgame.cosmicloaf.components.ComponentID;
import com.skidgame.cosmicloaf.components.Coordinate;
import com.skidgame.cosmicloaf.components.Entity;
import com.skidgame.cosmicloaf.components.Trigger;
import com.skidgame.cosmicloaf.game.Camera;
import com.skidgame.cosmicloaf.game.Game;
import com.skidgame.cosmicloaf.game.Region;
import com.skidgame.cosmicloaf.game.SkidmoreGameWorldLoader;
import com.skidgame.cosmicloaf.game.TheCosmicLoafGame;
import com.skidgame.cosmicloaf.systems.AiInputSystem;
import com.skidgame.cosmicloaf.systems.CollisionSystem;
import com.skidgame.cosmicloaf.tiledSupport.ExtendsTiledMap;

/**
 * An object which stores a list of all entities in a level, their positions (in a hash), an arrayList of entities sorted by which spriteSheet they use,
 * and a 2d array of terrain. It is capable of pathfinding.
 * @author emaicus
 *
 */
public class Level implements TileBasedMap
{
	public static final int PLAYER_ONE = 0;
	
	/** Entities in current level in sprite number order */
	private ArrayList<Entity> entityBySheet;
	
	/** Position of the entities in the level */
	private HashMap<Integer, ArrayList<Entity>> entityPositions;
	
	/** List of entities in the level */
	private ArrayList<Entity> entities;
	
	/** Terrain array for the current level */
	private Entity[][] terrain;
	
	/** Width of the level in boxes*/
	private int widthInBoxes;
	
	/** Height of level in boxes */
	private int heightInBoxes;
	public AStarPathFinder pathFinder;

	private ArrayList<Entity> players;

	private int level;
	
	private ArrayList<Trigger> triggers;
	
	private ExtendsTiledMap tiled;
	
	public HashMap<String, Region> regions;

	//TODO do we want a private int nextLevel;?
	
	/**
	 * Constructor for the level object
	 * 
	 * @param entityBySheet
	 * @param entityPositions
	 * @param entities
	 * @param terrain
	 * @param width
	 * @param height
	 */
	public Level(ArrayList<Entity> entityBySheet, HashMap<Integer, ArrayList<Entity>> entityPositions, ArrayList<Entity> entities, Entity[][] terrain, int width, int height, ArrayList<Entity> players, int level, ArrayList<Trigger> triggers, ExtendsTiledMap tiled, HashMap<String, Region> reg)
	{
		this.entityBySheet = entityBySheet;
		this.entityPositions = entityPositions;
		this.entities = entities;
		this.terrain = terrain;
		this.widthInBoxes = width;
		this.heightInBoxes = height;
		this.pathFinder = new AStarPathFinder(this, 20, false);
		System.out.println("assigning new player var with size " + players.size());
		this.players = players;
		this.level = level;
		this.triggers = triggers;
		this.tiled = tiled;
		this.regions = reg;
	}

	public ArrayList<Trigger> getTriggers()
	{
		return this.triggers;
	}
	
	/**
	 * Get the all the entities in the order of their sprite sheets
	 * @return ArrayList<Entity>
	 */
	public ArrayList<Entity> getEntityBySheet() {
		return entityBySheet;
	}
	
	/**
	 * Get the hash map of positions of the entities
	 * 
	 * @return HashMap<Integer, Entity> 
	 */
	public HashMap<Integer, ArrayList<Entity>> getEntityPositions() {
		return entityPositions;
	}

	/**
	 * Get all the entities in the level
	 * 
	 * @return ArrayList<Entity>
	 */
	public ArrayList<Entity> getEntities() {
		return entities;
	}
	
	/**
	 * Get the terrain of the level in entities
	 * 
	 * @return Entity[][]
	 */
	public Entity[][] getTerrain() {
		return terrain;
	}

	/**
	 * Get the height of the level in boxes
	 * 
	 * @return
	 */
	public int getHeightInTiles() {
		return heightInBoxes;
	}

	public void pathFinderVisited(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	/**@param int tx, int ty (x and y coord to poll)
	 * @return true if tile at tx, ty is blocked
	 * 
	 */
	public boolean blocked(PathFindingContext context, int tx, int ty) {
		/**The problem with the pathfinder is that it is polling from the upper left point on its head
		 * so it is walking directly into things with the other positions.
		 */
		//CollisionSystem.collision(xMovement, yMovement, context.getMover(), game, level)
		System.out.println("They want " + tx + "," + ty);
		//int box = (ty * widthInBoxes) + tx;
		//System.out.println("We are providing " + box);
		//TODO make this work
		int box = AiInputSystem.convertFromAiBox(tx, ty, this);
		ArrayList<Entity> player = entityPositions.get(box);
		System.out.println("We say that " + tx + "," + ty + " is equivalent to " + box);
		System.out.println("The player is entity " + players.get(0));

		
		if(player != null)
		{
			for(Entity e : player)
			{
				if(e == players.get(0))//TODO add multiple player support
				{
					System.out.println(box + " was not blocked");
					return false;
				}
			}
		}
		
		
		
		if(terrain[tx][ty].getCollidable() == null) //if there is no terrain blocking the way
		{
			if(entityPositions.get(box) == null) //if there is no entity in the square we are looking at
			{
				System.out.println(box + " was not blocked");
				return false;
			}
			else//otherwise, if there is an entity
			{
				for(Entity e : entityPositions.get(box))
				{
					if(e.getCollidable() != null) //if that entity isn't collidable
					{
						System.out.println(box + " was blocked because entity " + e);
						return true;
					}
				}
				System.out.println(box + " was not blocked");
				return false;
				
			}
			
		}
		//else It was collidable
		System.out.println(box + " was blocked because terrain");
		return true;
		
		
		
		
//		
//		//tx and ty in the form (1,2)
//		
//		int box = (ty*width) + tx;
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

	public float getCost(PathFindingContext context, int tx, int ty) {
		
		return 1; //TODO add costs if necessary.
	}

	public int getWidthInTiles() {
		return this.widthInBoxes;
	}
	
	public int getLevel()
	{
		return this.level;
	}
	
	public ArrayList<Entity> getPlayer()
	{
		return this.players;
	}
	
	/**
	 * Inefficient. Avoid when possible.
	 * @param e
	 * @param game
	 */
	public void addEntity(Entity e, TheCosmicLoafGame game, Camera cam)
	{
		ArrayList<Entity> a = new ArrayList<Entity>();
		a.add(e);
		addEntities(a, game, cam);
	}
	
	public void addEntities(ArrayList<Entity> array, TheCosmicLoafGame game, Camera cam)
	{
		System.out.println("had " + entities.size() + " entities.");
		for(Entity e : array) //for every entity
		{
			for(Coordinate c :  e.getPosition().getLiteralCoordinates()) //for every coordinate it occupies
			{
				for(int pos : CollisionSystem.convertPositionToBoxes(c, game, this, cam)) //convert that coord to a box
				{
					if(entityPositions.get(pos) == null)
					{
						ArrayList<Entity> thisList = new ArrayList<Entity>();
						thisList.add(e);
						entityPositions.put(pos, thisList); //and map the entity to that box
					}
					else
					{
						if(!CollisionSystem.collision(0, 0, e, game, this))
						{
							entityPositions.get(pos).add(e);
						}
						else
						{
							System.out.println("Tried to add entity at an occupied position (Level line 218)");
						}
					}
				}
			}
			
			System.out.println("Adding an entity to the game");
			entities.add(e);

		}

		entityBySheet = SkidmoreGameWorldLoader.sortSpriteSheets(entities);
		
		
	}
	
	public void removeEntities(ArrayList<Entity> toRemove, TheCosmicLoafGame game, Camera cam)
	{
		for(Entity e : toRemove)
		{
			removeEntity(e, game, cam);
		}
	}

	public void removeEntity(Entity me, TheCosmicLoafGame game, Camera cam) {
		//TODO remove more of the entity if necessary.
		if(me == null)
		{
			System.out.println("Null entity passed in.");
		}
		if(me.getPosition() == null)
		{
			System.out.println("ERROR: Given null  position");
			return;

		}
		ArrayList<Coordinate> coords = me.getPosition().getLiteralCoordinates();
		for(Coordinate c : coords)
		{
			int[] boxes = CollisionSystem.convertPositionToBoxes(c, game, this, cam);
			for(int key : boxes)
			{
				this.entityPositions.remove(key);
			}
		}
		
		me.getComponents()[ComponentID.POSITION.ordinal()] = null;
		
		if(entities.remove(me))
		{
			System.out.println("Remove successful.");
		}
		else
		{
			System.out.println("Remove unsuccessful");
		}
		
		if(entityBySheet.remove(me))
		{
			System.out.println("Remove successful.");
		}
		else
		{
			System.out.println("Remove unsuccessful");
		}
	}

	public TiledMap getTiledMap() {

		return this.tiled;
	}
	
	
}
