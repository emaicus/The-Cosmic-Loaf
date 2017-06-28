package com.skidgame.cosmicloaf.game;

import java.util.ArrayList;


public class DialogTree {
	private ArrayList<DialogChain> chains;
	
	public DialogTree(DialogChain...chains)
	{
		this.chains = new ArrayList<DialogChain>();
		for(DialogChain dc: chains)
		{
			this.chains.add(dc);
		}
	}
	
	public ArrayList<DialogChain> getChains()
	{
		return this.chains;
	}
}
