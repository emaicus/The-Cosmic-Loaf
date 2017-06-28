package com.skidgame.cosmicloaf.systems;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.skidgame.cosmicloaf.Level;
import com.skidgame.cosmicloaf.components.ComponentID;
import com.skidgame.cosmicloaf.components.Coordinate;
import com.skidgame.cosmicloaf.components.Entity;
import com.skidgame.cosmicloaf.components.Position;
import com.skidgame.cosmicloaf.game.Camera;
import com.skidgame.cosmicloaf.game.Region;
import com.skidgame.cosmicloaf.game.TheCosmicLoafGame;
/**
 * Handles Entity Entity Collision.
 * @author emaicus
 *
 */
public class CollisionSystem 
{
	/**
	 * Let box conversions happen in conversion methods.
	 * Returns an int array of length 4 containing the boxes that top left, top right, bottom left, bottom right are in.
	 * @param topLeftX
	 * @param topLeftY
	 * @param game
	 * @param level
	 * @return
	 */
	public static int[] convertPositionToBoxes(int topLeftX, int topLeftY, TheCosmicLoafGame game, Level level, Camera cam)
	{
		int answer[] = new int[4]; //top left, top right, bottom left, bottom right
		int boxY = topLeftY / (game.getLiteralHashDefinition()); // divide our y by (scale * height of a box) to get the box we fall into (if scale is zero, height is 16, and we are converting 15, we get 15/16 = box 0.
		int boxX = topLeftX / (game.getLiteralHashDefinition());
		answer[0] = ((boxY * level.getWidthInTiles()) + boxX);
		
		boxY = topLeftY / (game.getLiteralHashDefinition()); // divide our y by (scale * height of a box) to get the box we fall into (if scale is zero, height is 16, and we are converting 15, we get 15/16 = box 0.
		boxX = (topLeftX + game.getLiteralHashDefinition() - 1) / (game.getLiteralHashDefinition());
		answer[1] = ((boxY * level.getWidthInTiles()) + boxX);

		
		boxY = (topLeftY + game.getLiteralHashDefinition() - 1) / (game.getLiteralHashDefinition()); // divide our y by (scale * height of a box) to get the box we fall into (if scale is zero, height is 16, and we are converting 15, we get 15/16 = box 0.
		boxX = topLeftX  / (game.getLiteralHashDefinition());
		answer[2] = ((boxY * level.getWidthInTiles()) + boxX);

		
		boxY = (topLeftY + game.getLiteralHashDefinition() - 1) / (game.getLiteralHashDefinition()); // divide our y by (scale * height of a box) to get the box we fall into (if scale is zero, height is 16, and we are converting 15, we get 15/16 = box 0.
		boxX = (topLeftX + game.getLiteralHashDefinition() - 1) / (game.getLiteralHashDefinition());
		answer[3] = ((boxY * level.getWidthInTiles()) + boxX);
		
		
		return answer;
	}
	
	/**
	 * Allow camera conversions to happen in these methods.
	 * @param c
	 * @param game
	 * @param level
	 * @param cam
	 * @return
	 */
	public static int[] convertPositionToBoxes(Coordinate c, TheCosmicLoafGame game, Level level, Camera cam)
	{
		int answer[] = new int[4]; //top left, top right, bottom left, bottom right
		int boxY = c.getLiteralYValue() / (game.getLiteralHashDefinition()); // divide our y by (scale * height of a box) to get the box we fall into (if scale is zero, height is 16, and we are converting 15, we get 15/16 = box 0.
		int boxX = c.getLiteralXValue() / (game.getLiteralHashDefinition());
		answer[0] = ((boxY * level.getWidthInTiles()) + boxX);

		boxY = c.getLiteralYValue() / (game.getLiteralHashDefinition()); // divide our y by (scale * height of a box) to get the box we fall into (if scale is zero, height is 16, and we are converting 15, we get 15/16 = box 0.
		boxX = (c.getLiteralXValue() + game.getLiteralHashDefinition() - 1) / (game.getLiteralHashDefinition());
		answer[1] = ((boxY * level.getWidthInTiles()) + boxX);
		
		boxY = (c.getLiteralYValue() + game.getLiteralHashDefinition() - 1) / (game.getLiteralHashDefinition()); // divide our y by (scale * height of a box) to get the box we fall into (if scale is zero, height is 16, and we are converting 15, we get 15/16 = box 0.
		boxX = c.getLiteralXValue()  / (game.getLiteralHashDefinition());
		answer[2] = ((boxY * level.getWidthInTiles()) + boxX);
		
		boxY = (c.getLiteralYValue() + game.getLiteralHashDefinition() - 1) / (game.getLiteralHashDefinition()); // divide our y by (scale * height of a box) to get the box we fall into (if scale is zero, height is 16, and we are converting 15, we get 15/16 = box 0.
		boxX = (c.getLiteralXValue() + game.getLiteralHashDefinition() - 1) / (game.getLiteralHashDefinition());
		answer[3] = ((boxY * level.getWidthInTiles()) + boxX);
		
		
		return answer;
	}
	
	/**
	 * Returns an int array of length 4 containing the boxes that top left, top right, bottom left, bottom right are in.
	 * @param xToConvert
	 * @param yToConvert
	 * @param game
	 * @param worldHeight
	 * @param worldWidth
	 * @return
	 */
	public static int[] convertPositionToBox(int xToConvert, int yToConvert, TheCosmicLoafGame game, int worldHeight, int worldWidth, Camera cam)
	{
		int[] answer = new int[4];
		int scale = 1;
		int boxY = yToConvert / (scale * game.getLiteralHashDefinition()); // divide our y by (scale * height of a box) to get the box we fall into (if scale is zero, height is 16, and we are converting 15, we get 15/16 = box 0.
		int boxX = xToConvert / (scale * game.getLiteralHashDefinition());
		answer[0] = ((boxY * worldWidth) + boxX);
		
		boxY = yToConvert / (scale * game.getLiteralHashDefinition()); // divide our y by (scale * height of a box) to get the box we fall into (if scale is zero, height is 16, and we are converting 15, we get 15/16 = box 0.
		boxX = (xToConvert + game.getLiteralHashDefinition() - 1) / (scale * game.getLiteralHashDefinition());
		answer[1] = ((boxY * worldWidth) + boxX);
		
		boxY = (yToConvert + game.getLiteralHashDefinition() -1 ) / (scale * game.getLiteralHashDefinition()); // divide our y by (scale * height of a box) to get the box we fall into (if scale is zero, height is 16, and we are converting 15, we get 15/16 = box 0.
		boxX = xToConvert / (scale * game.getLiteralHashDefinition());
		answer[2] = ((boxY * worldWidth) + boxX);
		
		boxY = (yToConvert + game.getLiteralHashDefinition() -1)/ (scale * game.getLiteralHashDefinition()); // divide our y by (scale * height of a box) to get the box we fall into (if scale is zero, height is 16, and we are converting 15, we get 15/16 = box 0.
		boxX = (xToConvert + game.getLiteralHashDefinition() -1)/ (scale * game.getLiteralHashDefinition());
		answer[3] = ((boxY * worldWidth) + boxX);
		
		
		return answer;
	}
	
	public static int[] convertPositionToBoxes(int xToConvert, int yToConvert, int squareWidth, int squareHeight, int wordWidthInBoxes, Camera cam)
	{
		int[] answer = new int[4];
		int scale = 1;
		int boxY = yToConvert / (scale * squareHeight); // divide our y by (scale * height of a box) to get the box we fall into (if scale is zero, height is 16, and we are converting 15, we get 15/16 = box 0.
		int boxX = xToConvert / (scale * squareWidth);
		answer[0] = ((boxY * wordWidthInBoxes) + boxX);
		
		boxY = yToConvert / (scale * squareHeight); // divide our y by (scale * height of a box) to get the box we fall into (if scale is zero, height is 16, and we are converting 15, we get 15/16 = box 0.
		boxX = (xToConvert + squareWidth - 1) / (scale * squareWidth);
		answer[1] = ((boxY * wordWidthInBoxes) + boxX);
		
		boxY = (yToConvert + squareHeight - 1) / (scale * squareHeight); // divide our y by (scale * height of a box) to get the box we fall into (if scale is zero, height is 16, and we are converting 15, we get 15/16 = box 0.
		boxX = xToConvert / (scale * squareWidth);
		answer[2] = ((boxY * wordWidthInBoxes) + boxX);
		
		boxY = (yToConvert + squareHeight - 1) / (scale * squareHeight); // divide our y by (scale * height of a box) to get the box we fall into (if scale is zero, height is 16, and we are converting 15, we get 15/16 = box 0.
		boxX = (xToConvert + squareWidth - 1) / (scale * squareWidth);
		answer[3] = ((boxY * wordWidthInBoxes) + boxX);
		
		return answer;
	}
	
	public static int[] convertBoxToPosition(int box, TheCosmicLoafGame game, Level level, Camera cam) {
   		int[] position = new int[2];
   		position[0] = (box%(level.getWidthInTiles())) * game.getLiteralHashDefinition();
   		position[1] = (box/level.getHeightInTiles()) * game.getLiteralHashDefinition(); 
   		return position;
   	}
	
	public static int[] convertBoxToPosition(int box, TheCosmicLoafGame game, int levelWidth, int levelHeight, Camera cam) {
   		int[] position = new int[2];
   		position[0] = (box%levelWidth) *  game.getLiteralHashDefinition();
   		position[1] = (box/levelHeight) * game.getLiteralHashDefinition(); 
   		return position;
   	}
	
	
	
	public static ArrayList<Integer> getBoxesAdjacentToPosition(Position p, TheCosmicLoafGame game, Level level, Camera cam)
	{
		//TODO known error, returns boxes within the position as well as adjacent to it.
		ArrayList<Integer> adjacentBoxes = new ArrayList<Integer>();
		ArrayList<Integer> internalBoxes = new ArrayList<Integer>();
		
		ArrayList<Coordinate> coords = p.getLiteralCoordinates();
		
		for(Coordinate coord : coords) //find all internal boxes
		{
			int[] boxes = convertPositionToBoxes(coord, game, level, cam);
			for(int i : boxes)
			{
				internalBoxes.add(i);

			}
		}
		
		for(Coordinate coord : coords) //find all internal and adjacent boxes.
		{
			ArrayList<Integer> ints = getBoxesAdjacentToCoordinate(coord, game, level, cam);
			for(Integer i : ints)
			{
				adjacentBoxes.add(i);
			}
		}
		
		for(int i : internalBoxes)
		{
			if(adjacentBoxes.contains(i))
			{
				adjacentBoxes.remove((Integer)i);
			}
		}
		
		return adjacentBoxes;
		
	}
	
	public static ArrayList<Integer> getBoxesAdjacentToCoordinate(Coordinate c, TheCosmicLoafGame game, Level level, Camera cam)
	{
		//The Boxes below
		int boxY = c.getLiteralYValue() + game.getLiteralHashDefinition() ; //add Hash def to get one above
		int boxX = c.getLiteralXValue(); //normal
		int[] belowBoxes = convertPositionToBoxes(boxX, boxY, game, level, cam);
		//The boxes to the Right
		boxY -= game.getLiteralHashDefinition()  ; //return to normal
		boxX += game.getLiteralHashDefinition() ;  //add hash to get one to the right
		int[] rightBoxes = convertPositionToBoxes(boxX, boxY, game, level, cam);
		//The boxes above 
		boxY -= game.getLiteralHashDefinition()  ; //subtract hash to go one up.
		boxX -= game.getLiteralHashDefinition()  ; //subtract to get back to normal
		int[] aboveBoxes = convertPositionToBoxes(boxX, boxY, game, level, cam);
		//The boxes to the left.
		boxY += game.getLiteralHashDefinition()  ; //add to get back to normal
		boxX -= game.getLiteralHashDefinition()  ; //subtract to get one to the left.
		int[] leftBoxes = convertPositionToBoxes(boxX, boxY, game, level, cam);
		
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		for(int i : belowBoxes)
		{
			map.put(i, i);
		}
		
		for(int i : aboveBoxes)
		{
			map.put(i, i);
		}
		
		for(int i : rightBoxes)
		{
			map.put(i, i);
		}
		
		for(int i : leftBoxes)
		{
			map.put(i, i);
		}
		Collection<Integer> col = map.values();
		ArrayList<Integer> list = new ArrayList<Integer>(col);		
		return list;
	}
	
	/**
	 * returns a 2d array, x, y
	 */
	public static int[] convertBoxToCoordinatePair(int box, Level level)
	{
		
		int xY[] = new int[2];
		xY[0] = box % level.getWidthInTiles();
		xY[1] = box / level.getWidthInTiles();
		//System.out.println(box + " is " + xY[0] + "," + xY[1] + " in a world with width " + level.getWidthInTiles());
		return xY;
	}
	
	/**
	 * returns a Coordinate
	 */
	public static Coordinate convertBoxToCoordinate(int box, TheCosmicLoafGame game, Level level, Camera cam)
	{
		
		Coordinate C;
		int[] xY = convertBoxToPosition(box, game, level, cam);
		C = new Coordinate(xY[0], xY[1]);

		
		return C;
	}
	
	/**
	 * returns a Coordinate
	 */
	public static Coordinate convertBoxToCoordinate(int box, TheCosmicLoafGame game, int levelWidth, int levelHeight, Camera cam)
	{
		
		Coordinate C;
		int[] xY = convertBoxToPosition(box, game, levelWidth, levelHeight, cam);
		C = new Coordinate(xY[0], xY[1]);

		
		return C;
	}
	
	
	/**
	 * Converts box x and box y into a standard box. X and Y are NOT PIXEL COORDINATES.
	 * @param x
	 * @param y
	 * @return
	 */
	public static int convertLiteralXYToBox(int x, int y, TheCosmicLoafGame game, Level level)
	{
		int boxY = y / (game.getLiteralHashDefinition()); // divide our y by (scale * height of a box) to get the box we fall into (if scale is zero, height is 16, and we are converting 15, we get 15/16 = box 0.
		int boxX = x / (game.getLiteralHashDefinition());
		return ((boxY * level.getWidthInTiles()) + boxX);
	}
	
	/**
	 * Converts Coordinate box x and box y into a standard box. X and Y are NOT PIXEL COORDINATES.
	 * @param x
	 * @param y
	 * @return
	 */
	public static int convertLiteralXYToBox(Coordinate c, TheCosmicLoafGame game, Level level)
	{
		int y = c.getLiteralYValue();
		int x = c.getLiteralXValue();
		int boxY = y / (game.getLiteralHashDefinition()); // divide our y by (scale * height of a box) to get the box we fall into (if scale is zero, height is 16, and we are converting 15, we get 15/16 = box 0.
		int boxX = x / (game.getLiteralHashDefinition());
		return ((boxY * level.getWidthInTiles()) + boxX);
	}
	
	/**
	 * Converts box x and box y into a standard box. X and Y are NOT PIXEL COORDINATES.
	 * @param x
	 * @param y
	 * @return
	 */
	public static int convertLiteralXYToBox(int x, int y, TheCosmicLoafGame game, int widthInTiles)
	{
		int boxY = y / (game.getLiteralHashDefinition()); // divide our y by (scale * height of a box) to get the box we fall into (if scale is zero, height is 16, and we are converting 15, we get 15/16 = box 0.
		int boxX = x / (game.getLiteralHashDefinition());
		return ((boxY * widthInTiles) + boxX);
	}
	
	/**
   	 * Returns true if a collision has taken place.
   	 * @param start coordinate
   	 * @param end coordinate
   	 * @param game
   	 * @param level
   	 * @return
   	 */
	public static boolean collision(int xMovement, int yMovement, Entity poller, TheCosmicLoafGame game, Level level)	{
		
		boolean collided = false;
	
   		HashMap<Integer, ArrayList<Entity>> positionHash = level.getEntityPositions(); //Gets a list of each position mapping to whatever is at that position
   		Coordinate[] currentCorners = poller.getPosition().getCorners(); //Gets the list of squares poller occupies
   		/**
   		 * This is where all of the movement occurs. 
   		 * Each position of the entity is moved individually
   		 * If any of them hit a single square which is not null or the entity itself, change canMove to false
   		 * Otherwise leave it as is
   		 */

   		ArrayList<Entity> checkingList = new ArrayList<Entity>();
   		
   		OUR_TEST: for (int i = 0; i < currentCorners.length; i++) 
   		{
   			Coordinate corner = currentCorners[i];
   	//		System.out.println("This corners coords are " + corner.getLiteralXValue() + "," + corner.getLiteralYValue());

   			int pollX = corner.getLiteralXValue() + xMovement; 
   			int pollY = corner.getLiteralYValue() + yMovement;
   			int pollBo = convertLiteralXYToBox(pollX, pollY, game, level);
   	//		System.out.println(corner.getLiteralXValue() + " + " + xMovement + " is " + pollX);

   			if(pollX < 0 || pollY < 0 || pollX > (level.getWidthInTiles() * game.getLiteralSquareWidth()) || 	pollY > (level.getHeightInTiles() * game.getLiteralSquareWidth()))
   			{
   				return true;
   			}
   			
   				checkingList = positionHash.get(pollBo);
   				if(checkingList != null)
   				{
   					for(Entity checking : checkingList)
   					{
   						
   						if (checking != null && checking != poller) //we have a potential collision!
   	   	   				{
   		   		//			System.out.println("We're moving into a square that isn't empty.");

   	   	   					Coordinate[] check = checking.getPosition().getCorners(); 	   					

   	   	   					int rightX = check[CornerEnum.BOTTOMRIGHT.ordinal()].getLiteralXValue();
   	   	   					int leftX = check[CornerEnum.TOPLEFT.ordinal()].getLiteralXValue();
   	   	   					int topY = check[CornerEnum.TOPLEFT.ordinal()].getLiteralYValue();
   	   	   					int bottomY = check[CornerEnum.BOTTOMLEFT.ordinal()].getLiteralYValue();
   	   	   					
   	   	   					
   	   	   				//	if(i == CornerEnum.BOTTOMRIGHT.ordinal()){System.out.println("Bottom Right");}
   	   	   				//	if(i == CornerEnum.TOPLEFT.ordinal()){System.out.println("Top Left");}
   	   	   				//	if(i == CornerEnum.TOPRIGHT.ordinal()){System.out.println("Top Right");}
   	   	   				//	if(i == CornerEnum.BOTTOMLEFT.ordinal()){System.out.println("Bottom L");}
 	   					
   	   	   					/**
   	   	   					 * THIS IS THE FIRST PART OF COLLISION CODE.
   	   	   					 * First, treat ourselves like we're moving.
   	   	   					 */
   	   	   					if((pollX <= rightX) && (pollX >= leftX)) //If our corner falls between the the other boxes x corners.
   	   	   					{
   	   	   					//	System.out.println("Bad X");
   	   	   						if(((topY <= pollY) && (pollY <= bottomY))) //if our corner falls between the other boxes y corners.
   	   	   						{
   	   	   						//	System.out.println("Bad Y");
   	   	   							collided = true;
   	   	   							break OUR_TEST;
   	   	   						}
   	   	   						
   	   	   					}
   	   	   						
   	   	   				}
   	   				}
   				}	
   		}
   		
   		if(checkingList != null && checkingList.size() > 0)
   		{
   			//TODO fix getting stuck.
   			THEIR_TEST: for(Entity checking: checkingList)
   			{
   				if(checking == poller)
   				{
   					continue;
   				}
   		//		System.out.println("There is an entity in the square that we are moving into.");
   				
	   					/**
	   					 * THIS IS THE SECOND PART OF THE COLLISION CODE.
	   					 * Now, we treat them like their moving. This makes sure small entity collisions happen.
	   					 */
	   					for(Coordinate c : checking.getPosition().getCorners())
	   					{
	   						
	   						int ourRightX = currentCorners[CornerEnum.TOPRIGHT.ordinal()].getLiteralXValue() +xMovement;
	   						int ourLeftX = currentCorners[CornerEnum.TOPLEFT.ordinal()].getLiteralXValue() + xMovement;
	   						int ourBottomY = currentCorners[CornerEnum.BOTTOMLEFT.ordinal()].getLiteralYValue() + yMovement;
	   						int ourTopY = currentCorners[CornerEnum.TOPLEFT.ordinal()].getLiteralYValue() + yMovement;
	   						int theirPollX = c.getLiteralXValue();
	   						int theirPollY = c.getLiteralYValue();
	   			//			System.out.println("X: " + ourLeftX + " <= " + theirPollX + " <= " + ourRightX);
	   			//			System.out.println("Y: " + ourTopY + " <= " + theirPollY + " <= " + ourBottomY);

	   						if((theirPollX <= ourRightX) && (theirPollX >= ourLeftX)) //If our corner falls between the the other boxes x corners.
	   						{
	   			//				System.out.println("Bad X!!!");
	   							if(((ourTopY <= theirPollY) && (theirPollY <= ourBottomY))) //if our corner falls between the other boxes y corners.
	   							{
	   			//					System.out.println("Bad Y!!!");
	   								collided = true;
	   								break THEIR_TEST;
	   							}
	   						}
	   					}
   			}
   		}
   		
   		return collided;
	}
	
//	/**
//   	 * Returns true if a collision has taken place.
//   	 * @param start coordinate
//   	 * @param end coordinate
//   	 * @param game
//   	 * @param level
//   	 * @return
//   	 */
//	public static boolean collision(int xMovement, int yMovement, Entity poller, TheCosmicLoafGame game, Level level)	{
//		System.out.println("IN NEW COLLISION");
//		boolean collided = false;
//	
//   		HashMap<Integer, ArrayList<Entity>> positionHash = level.getEntityPositions(); //Gets a list of each position mapping to whatever is at that position
//   		Coordinate[] currentCorners = poller.getPosition().getCorners(); //Gets the list of squares poller occupies
//   		/**
//   		 * This is where all of the movement occurs. 
//   		 * Each position of the entity is moved individually
//   		 * If any of them hit a single square which is not null or the entity itself, change canMove to false
//   		 * Otherwise leave it as is
//   		 */
//   		
//
//   		
//   		
//   		
//   	//		System.out.println("This corners coords are " + corner.getLiteralXValue() + "," + corner.getLiteralYValue());
//
//   			int pollX = currentCorners[CornerEnum.TOPLEFT.ordinal()].getLiteralXValue() + xMovement;
//   			int pollWidth = (currentCorners[CornerEnum.TOPRIGHT.ordinal()].getLiteralXValue() + xMovement) - pollX;
//   			int pollY = currentCorners[CornerEnum.TOPLEFT.ordinal()].getLiteralYValue() + yMovement;
//   			int pollHeight = (currentCorners[CornerEnum.BOTTOMRIGHT.ordinal()].getLiteralYValue() + yMovement) - pollY;
//   			int pollBo = convertLiteralXYToBox(pollX, pollY, game, level);
//   	//		System.out.println(corner.getLiteralXValue() + " + " + xMovement + " is " + pollX);
//
//   			if(pollX < 0 || pollY < 0 || pollX > (level.getWidthInTiles() * game.getLiteralSquareWidth()) || 	pollY > (level.getHeightInTiles() * game.getLiteralSquareWidth()))
//   			{
//   				return true;
//   			}
//   			
//   				ArrayList<Entity> checkingList = positionHash.get(pollBo);
//   				if(checkingList != null)
//   				{
//   					for(Entity checking : checkingList)
//   					{
//   						
//   						if (checking != null && checking != poller) //we have a potential collision!
//   	   	   				{
//   		   					System.out.println("We're moving into a square that isn't empty.");
//
//   	   	   					Coordinate[] check = checking.getPosition().getCorners(); 	   					
//
//   	   	   					int rightX = check[CornerEnum.BOTTOMRIGHT.ordinal()].getLiteralXValue();
//   	   	   					int leftX = check[CornerEnum.TOPLEFT.ordinal()].getLiteralXValue();
//   	   	   					int checkWidth = rightX - leftX;
//   	   	   					int topY = check[CornerEnum.TOPLEFT.ordinal()].getLiteralYValue();
//   	   	   					int bottomY = check[CornerEnum.BOTTOMLEFT.ordinal()].getLiteralYValue();
//   	   	   					int checkHeight = bottomY - topY;
//   	   	   					
//   	   	   				System.out.println("mover width: " + pollWidth + " height " + pollHeight);
//   	   	   				System.out.println("checker width: " + checkWidth + " height " + checkHeight);
//
//   	   	   					Rectangle mover = new Rectangle(pollX, pollY, pollWidth, pollHeight);
//   	   	
//   	   	   					Rectangle checker = new Rectangle(leftX, topY, checkWidth, checkHeight);
//   	    					System.out.println(checker.getMaxX() + " vs " + rightX);
//   	   	   					System.out.println(checker.getMaxY() + " vs " + bottomY);
//   	   	   					System.out.println(checker.getMinX() + " vs " + leftX);
//   	   	   					System.out.println(checker.getMinY() + " vs " + topY);
//   	   	   					if(mover.intersects(checker))
//   	   	   					{
//   	   	   						return true;
//   	   	   					}
//   	   	   					
////   	   	   					if((pollX <= rightX) && (pollX >= leftX)) //THIS IS THE COLLISION CODE.
////   	   	   					{
////   	   	   						System.out.println("Bad X");
////   	   	   						if(((topY <= pollY) && (pollY <= bottomY))) //|| ((topY == pollY) && (pollY == bottomY)))
////   	   	   						{
////   	   	   							System.out.println("Bad Y");
////   	   	   							collided = true;
////   	   	   							break OUTER;
////   	   	   						}
////   	   	   						else
////   	   	   						{
////   	   	   						}
////   	   	   					}
////   	   	   					else
////   	   	   					{
////   	   	   					}
//   	   	   					
//   	   	   					
//   	   	   				}
//   	   				}
//   				}
//   			
//   				
//   			
//   		
//   		
//   		return collided;
//	}
	
	/**
	 * Merely checks if a single box contains a collidable entity.
	 * @param boxToMoveTo
	 * @param level
	 * @return
	 */
	public static boolean checkSingleBoxCollision(int boxToMoveTo, Level level)
	{
		if(level.getEntityPositions().get(boxToMoveTo) != null)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * This method removes and entity e from the Collision System
	 * 
	 * @param e
	 * @param g
	 * @return
	 */
	public static boolean removeEntityFromCollidables(Entity e, TheCosmicLoafGame g, Camera cam)
	{
		HashMap<Integer, ArrayList<Entity>> map = g.getCurrentLevel().getEntityPositions();
		ArrayList<Coordinate> currentCoordinates = ((Position)e.getComponents()[ComponentID.POSITION.ordinal()]).getLiteralCoordinates();
		for (Coordinate cc : currentCoordinates){
			int posX = cc.getLiteralXValue();
			int posY = cc.getLiteralYValue();
			int[] posBox = convertPositionToBoxes(posX,posY,g,g.getCurrentLevel(), cam);
			for (int i : posBox)
			{
				return map.get(i).remove(e);
				//return map.remove(i,e); // removes the value at that key i only if it has the value e
			}
		}
		
		return false;
	}

	/**
	 * Returns the region that you are supposed to teleport to.
	 * @param xMovement
	 * @param yMovement
	 * @param poller
	 * @param game
	 * @param level
	 * @return
	 */
	public static Region enteredTeleportRegion(int xMovement, int yMovement, Entity poller, TheCosmicLoafGame game, Level level) 
	{
		Region teleportTo = null;
		
   		HashMap<Integer, ArrayList<Entity>> positionHash = level.getEntityPositions(); //Gets a list of each position mapping to whatever is at that position
   		Coordinate[] currentCorners = poller.getPosition().getCorners(); //Gets the list of squares poller occupies
   		/**
   		 * This is where all of the movement occurs. 
   		 * Each position of the entity is moved individually
   		 * If any of them hit a single square which is not null or the entity itself, change canMove to false
   		 * Otherwise leave it as is
   		 */

   		Collection<Region> checkingList = null;
   		
   		OUR_TEST: for (int i = 0; i < currentCorners.length; i++) 
   		{
   			Coordinate corner = currentCorners[i];

   			int pollX = corner.getLiteralXValue() + xMovement; 
   			int pollY = corner.getLiteralYValue() + yMovement;
   			int pollBo = convertLiteralXYToBox(pollX, pollY, game, level);

   			
   				checkingList = game.getCurrentLevel().regions.values();
   				if(checkingList != null)
   				{
   					for(Region region : checkingList)
   					{
   						if(region.teleportTo == null)
   						{
   							continue;
   						}
   						
   						if (region != null) //we have a potential collision!
   	   	   				{

   	   	   					int rightX = region.bottomRight.getLiteralXValue();
   	   	   					int leftX = region.topLeft.getLiteralXValue();
   	   	   					int topY = region.topLeft.getLiteralXValue();
   	   	   					int bottomY = region.bottomRight.getLiteralXValue();
   	   	   					 	   					
   	   	   					/**
   	   	   					 * THIS IS THE FIRST PART OF COLLISION CODE.
   	   	   					 * First, treat ourselves like we're moving.
   	   	   					 */
   	   	   					if((pollX <= rightX) && (pollX >= leftX)) //If our corner falls between the the other boxes x corners.
   	   	   					{
   	   	   						if(((topY <= pollY) && (pollY <= bottomY))) //if our corner falls between the other boxes y corners.
   	   	   						{
   	   	   							teleportTo = region.teleportTo;
   	   	   							break OUR_TEST;
   	   	   						}
   	   	   						
   	   	   					}
   	   	   						
   	   	   				}
   	   				}
   				}	
   		}
   		return teleportTo;
	}
	
	/**
	 * Returns a list of all regions an entity is currently in.
	 * @param xMovement
	 * @param yMovement
	 * @param poller
	 * @param game
	 * @param level
	 * @return
	 */
	public static ArrayList<Region> regionsImIn(int xMovement, int yMovement, Entity poller, TheCosmicLoafGame game, Level level) 
	{
		ArrayList<Region> enteredRegions = new ArrayList<Region>();
		
   		HashMap<Integer, ArrayList<Entity>> positionHash = level.getEntityPositions(); //Gets a list of each position mapping to whatever is at that position
   		Coordinate[] currentCorners = poller.getPosition().getCorners(); //Gets the list of squares poller occupies
   		/**
   		 * This is where all of the movement occurs. 
   		 * Each position of the entity is moved individually
   		 * If any of them hit a single square which is not null or the entity itself, change canMove to false
   		 * Otherwise leave it as is
   		 */

   		Collection<Region> checkingList = null;
   		
   		OUR_TEST: for (int i = 0; i < currentCorners.length; i++) 
   		{
   			Coordinate corner = currentCorners[i];

   			int pollX = corner.getLiteralXValue() + xMovement; 
   			int pollY = corner.getLiteralYValue() + yMovement;
   			int pollBo = convertLiteralXYToBox(pollX, pollY, game, level);

   			
   				checkingList = game.getCurrentLevel().regions.values();
   				if(checkingList != null)
   				{
   					for(Region region : checkingList)
   					{
   						if (region != null) //we have a potential collision!
   	   	   				{

   	   	   					int rightX = region.bottomRight.getLiteralXValue();
   	   	   					int leftX = region.topLeft.getLiteralXValue();
   	   	   					int topY = region.topLeft.getLiteralXValue();
   	   	   					int bottomY = region.bottomRight.getLiteralXValue();
   	   	   					 	   					
   	   	   					/**
   	   	   					 * THIS IS THE FIRST PART OF COLLISION CODE.
   	   	   					 * First, treat ourselves like we're moving.
   	   	   					 */
   	   	   					if((pollX <= rightX) && (pollX >= leftX)) //If our corner falls between the the other boxes x corners.
   	   	   					{
   	   	   						if(((topY <= pollY) && (pollY <= bottomY))) //if our corner falls between the other boxes y corners.
   	   	   						{
   	   	   							enteredRegions.add(region);
   	   	   							break OUR_TEST;
   	   	   						}
   	   	   						
   	   	   					}
   	   	   						
   	   	   				}
   	   				}
   				}	
   		}
   		return enteredRegions;
	}
	
	/**
	 * Returns true if an entity is in the region affiliated with a given name.
	 * @param xMovement
	 * @param yMovement
	 * @param poller
	 * @param game
	 * @param level
	 * @return
	 */
	public static boolean enteredNamedRegion(int xMovement, int yMovement, Entity poller, TheCosmicLoafGame game, Level level, String regionName) 
	{
		Region reg = level.regions.get(regionName);
		
		if(reg != null)
		{
			return enteredRegion( xMovement,  yMovement,  poller,  game,  level, reg);
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Returns true if an entity is in a specified Region.
	 * @param xMovement
	 * @param yMovement
	 * @param poller
	 * @param game
	 * @param level
	 * @return
	 */
	public static boolean enteredRegion(int xMovement, int yMovement, Entity poller, TheCosmicLoafGame game, Level level, Region region) 
	{
		boolean entered = false;
		Coordinate[] currentCorners = poller.getPosition().getCorners(); //Gets the list of squares poller occupies
   		/**
   		 * This is where all of the movement occurs. 
   		 * Each position of the entity is moved individually
   		 * If any of them hit a single square which is not null or the entity itself, change canMove to false
   		 * Otherwise leave it as is
   		 */
   		
   		OUR_TEST: for (int i = 0; i < currentCorners.length; i++) 
   		{
   			Coordinate corner = currentCorners[i];

   			int pollX = corner.getLiteralXValue() + xMovement; 
   			int pollY = corner.getLiteralYValue() + yMovement;
   			
   			
   						if (region != null) //we have a potential collision!
   	   	   				{

   	   	   					int rightX = region.bottomRight.getLiteralXValue();
   	   	   					int leftX = region.topLeft.getLiteralXValue();
   	   	   					int topY = region.topLeft.getLiteralXValue();
   	   	   					int bottomY = region.bottomRight.getLiteralXValue();
   	   	   					 	   					
   	   	   					/**
   	   	   					 * THIS IS THE FIRST PART OF COLLISION CODE.
   	   	   					 * First, treat ourselves like we're moving.
   	   	   					 */
   	   	   					if((pollX <= rightX) && (pollX >= leftX)) //If our corner falls between the the other boxes x corners.
   	   	   					{
   	   	   						if(((topY <= pollY) && (pollY <= bottomY))) //if our corner falls between the other boxes y corners.
   	   	   						{
   	   	   							entered = true;
   	   	   							break OUR_TEST;
   	   	   						}
   	   	   						
   	   	   					}
   	   	   						
   	   	   				}
   	   				
   					
   		}
   		return entered;
	}
	
}
