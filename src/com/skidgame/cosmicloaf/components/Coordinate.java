package com.skidgame.cosmicloaf.components;

import com.skidgame.cosmicloaf.game.Camera;

public class Coordinate
{
	private int xValue;
	private int yValue;	
	
	public Coordinate(int x, int y)
	{
		this.xValue = x;
		this.yValue = y;
	}
	
	public String toString()
	{
		return "(" + xValue + "," + yValue + ")";
	}
	
	public int getLiteralXValue()
	{
		return this.xValue;
	}
	
	public int getLiteralYValue()
	{
		return this.yValue;
	}
	
	public int getScaledXValue(Camera cam)
	{
		return (int) (this.xValue * cam.getZoom());
	}
	
	public int getScaledYValue(Camera cam)
	{
		return (int) (this.yValue * cam.getZoom());
	}

	public void setLiteralYValue(int i) {
		this.yValue = i;
		
	}
	
	public void setLiteralXValue(int x)
	{
		this.xValue = x;
	}
	
}
