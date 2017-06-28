package com.skidgame.cosmicloaf.game;

import java.util.ArrayList;

import com.skidgame.cosmicloaf.components.Entity;

public class DialogChain 
{
	boolean revolving;
	private ArrayList<Dialog> chain;
	int current;
	
	public DialogChain(boolean revolving, Dialog...dialogs)
	{
		current = 0;
		this.revolving = revolving;
		chain = new ArrayList<Dialog>();
		for(Dialog d : dialogs)
		{
			chain.add(d);
		}
	}
	
	public void playNext(Entity me, TheCosmicLoafGame game)
	{
		if(current >= chain.size())
		{
			if(revolving)
			{
				current = 0;
				chain.get(current).playAndRender(me, game);
				current++;
			}
			else
			{
				return; //We hit the end of a one time dialog chain.
			}
		}
		else
		{
			chain.get(current).playAndRender(me, game);
			current++;
		}
		
		

		
	}
	
	public ArrayList<Dialog> getChain()
	{
		return this.chain;
	}
	
	public String nextDialogPrompt()
	{
		if(current >= chain.size())
		{
			if(revolving)
			{
				current = 0;
				return chain.get(current).getMyPrompt();
			}
			else
			{
				return "Leave Me Alone!"; //We hit the end of a one time dialog chain.
			}
		}
		return chain.get(current).getMyPrompt();
	}
	
	public void setChain(ArrayList<Dialog> chain)
	{
		this.chain = chain;
	}
}
