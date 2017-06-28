package com.skidgame.cosmicloaf.scripts;


import com.skidgame.cosmicloaf.game.TheCosmicLoafGame;

/**
 * All scripts must contain an execute method. Try to avoid using Scripts. They are for VERY specific events.
 * @author emaicus
 *
 */
public abstract class Script 
{
	TheCosmicLoafGame game;
	
	public Script(TheCosmicLoafGame game)
	{
		this.game = game;
	}
	
	public void execute()
	{
	}
}
