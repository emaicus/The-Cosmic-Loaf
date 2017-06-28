package com.skidgame.cosmicloaf.game;

import com.skidgame.cosmicloaf.components.Coordinate;

public class Region 
{
	public Coordinate topLeft;
	public Coordinate bottomRight;
	public boolean teleport;
	public Region teleportTo;
	
	public Region(Coordinate topLeft, Coordinate bottomRight)
	{
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
	}
	
	public Region(Coordinate topLeft, Coordinate bottomRight, Region teleportTo)
	{
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
		teleport = true;
		this.teleportTo = teleportTo;
	}
}
