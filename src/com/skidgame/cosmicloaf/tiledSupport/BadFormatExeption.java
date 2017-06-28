package com.skidgame.cosmicloaf.tiledSupport;

public class BadFormatExeption extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BadFormatExeption() 
	{
		super("Hey! It looks like you passed in a tiled file saved in the wrong format. Please resave as gzip and try again!");
	}
	
	
	

}
