package com.skidgame.cosmicloaf.components;

import java.util.ArrayList;

import com.skidgame.cosmicloaf.game.Camera;
import com.skidgame.cosmicloaf.game.TheCosmicLoafGame;
import com.skidgame.cosmicloaf.systems.CornerEnum;

public class Position extends Component
{
	private ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
	public ComponentID id;
	public boolean complexPosition;
	boolean visible;
	private Coordinate[] corners;
	
	/**
	 * Provide the coordinates of the top left pixel of all tile sized squares occupied by the Position.
	 * @param game
	 * @param coord
	 */
	public Position(TheCosmicLoafGame game, Coordinate... coord)
	{
		corners = new Coordinate[4];
		int interval = game.getLiteralSquareWidth() / game.getLiteralHashDefinition();
		
		for(Coordinate c : coord)
		{
			for(int i = 0; i < interval; i++)
			{
				for(int j = 0; j < interval; j++)
				{
					Coordinate temp = new Coordinate(c.getLiteralXValue() + (i * game.getLiteralHashDefinition()), c.getLiteralYValue() + (j * game.getLiteralHashDefinition()));
					coordinates.add(temp);
					
					if(i == 0 && j == 0)
					{
						Coordinate upperRight = new Coordinate(temp.getLiteralXValue() + game.getLiteralSquareWidth(), temp.getLiteralYValue());
						Coordinate lowerLeft = new Coordinate(temp.getLiteralXValue(), temp.getLiteralYValue() + game.getLiteralSquareHeight());
						Coordinate lowerRight = new Coordinate(temp.getLiteralXValue() + game.getLiteralSquareWidth(), temp.getLiteralYValue() + game.getLiteralSquareHeight());
						corners[CornerEnum.TOPLEFT.ordinal()] = temp;
						corners[CornerEnum.TOPRIGHT.ordinal()] = upperRight;
						corners[CornerEnum.BOTTOMLEFT.ordinal()] = lowerLeft;
						corners[CornerEnum.BOTTOMRIGHT.ordinal()] = lowerRight;
						coordinates.add(upperRight);
						coordinates.add(lowerLeft);
						coordinates.add(lowerRight);
						
					}
				}
			}
			
		}
		id = ComponentID.POSITION;
		this.visible = false;
	}
	
	/**
	 * Provide the coordinates of the top left pixel of all tile sized squares occupied by the Position.
	 * @param game
	 * @param coord
	 */
	public Position(TheCosmicLoafGame game, int xStart, int xEnd, int yStart, int yEnd, Coordinate... coord)
	{
		corners = new Coordinate[4];
		int interval = game.getLiteralSquareWidth() / game.getLiteralHashDefinition();
		
		for(Coordinate c : coord)
		{
			for(int i = 0; i < interval; i++)
			{
				for(int j = 0; j < interval; j++)
				{
					Coordinate upperLeft = new Coordinate(c.getLiteralXValue() + (i * game.getLiteralHashDefinition()), c.getLiteralYValue() + (j * game.getLiteralHashDefinition()));
					upperLeft.setLiteralXValue(upperLeft.getLiteralXValue() + xStart);
					upperLeft.setLiteralYValue(upperLeft.getLiteralYValue() + yStart);
					coordinates.add(upperLeft);
					
					if(i == 0 && j == 0)
					{
						Coordinate upperRight = new Coordinate(upperLeft.getLiteralXValue() + (xEnd - xStart), upperLeft.getLiteralYValue());
						Coordinate lowerLeft = new Coordinate(upperLeft.getLiteralXValue(), upperLeft.getLiteralYValue() + (yEnd - yStart));
						Coordinate lowerRight = new Coordinate(upperLeft.getLiteralXValue() + (xEnd - xStart), upperLeft.getLiteralYValue() + (yEnd - yStart)); //I think I may have to get the width between these.
						corners[CornerEnum.TOPLEFT.ordinal()] = upperLeft;
						corners[CornerEnum.TOPRIGHT.ordinal()] = upperRight;
						corners[CornerEnum.BOTTOMLEFT.ordinal()] = lowerLeft;
						corners[CornerEnum.BOTTOMRIGHT.ordinal()] = lowerRight;
						coordinates.add(upperRight);
						coordinates.add(lowerLeft);
						coordinates.add(lowerRight);
						
					}
				}
			}
			
		}
		id = ComponentID.POSITION;
		this.visible = false;
	}
	
	public int getID() {
		return ComponentID.POSITION.ordinal();
	}
	
	public ArrayList<Coordinate> getAdjustedCoordinates(Camera cam)
	{
		ArrayList<Coordinate> updatedCoordinates = new ArrayList<Coordinate>();
		for(Coordinate c : coordinates)
		{
			updatedCoordinates.add(new Coordinate((int)(c.getLiteralXValue() * cam.getZoom()), (int)(c.getLiteralYValue() * cam.getZoom())));
		}
		return updatedCoordinates;
	}
	
	public ArrayList<Coordinate> getLiteralCoordinates()
	{		
		
		return this.coordinates;
	}

	public boolean isVisible() {
		return this.visible;
	}

	public void setVisible(boolean b) {
		this.visible = b;
	}
	
	public Coordinate[] getCorners()
	{
		return this.corners;
	}
	
	
}
