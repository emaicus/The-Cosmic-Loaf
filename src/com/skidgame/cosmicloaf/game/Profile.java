package com.skidgame.cosmicloaf.game;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.DisplayMode;

/**
 * Info about a player's graphics options is stored in a profile, so that, on loading, the game will use
 * the correct settings. (We'll see if this is helpful).
 * @author emaicus
 *
 */
public class Profile
{
	public int ScreenWidth;
	public int ScreenHeight;
	
	public Profile(int width, int height)
	{
		this.ScreenWidth = width;
		this.ScreenHeight = height;
	}
	
	public void setScreenWidth(int width)
	{
		this.ScreenWidth = width;
	}
	
	public void setScreenHeight(int height)
	{
		this.ScreenHeight = height;
	}
	
	public void updateProfile()
	{
		try {
			org.lwjgl.opengl.Display.setDisplayMode(new DisplayMode(ScreenWidth, ScreenHeight));
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

