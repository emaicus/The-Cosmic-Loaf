package com.skidgame.cosmicloaf.components;

import org.lwjgl.input.Keyboard;


public class MovementOptions extends Component
{
	private boolean canRun;
	private boolean canWalk;
	private boolean canTeleport;
	private boolean interactionAvailable = false;
	
	public int up = Keyboard.KEY_W;
	public int down = Keyboard.KEY_S;
	public int left = Keyboard.KEY_A;
	public int right = Keyboard.KEY_D;
	public int activate = Keyboard.KEY_E;
	public int attack = Keyboard.KEY_SPACE;
	public int menu = Keyboard.KEY_ESCAPE;
	public int run = Keyboard.KEY_LSHIFT;
	
	public boolean upPressed = false; //Pressed is a single click. Down is for holding.
	public boolean upHeld = false;
	public boolean downPressed = false; 
	public boolean downHeld = false;
	public boolean leftPressed = false; 
	public boolean leftHeld = false;
	public boolean rightPressed = false; 
	public boolean rightHeld = false;
	public boolean activatePressed = false; 
	public boolean activateHeld = false;
	public boolean attackPressed = false; 
	public boolean attackHeld = false;
	public boolean menuPressed = false; 
	public boolean menuHeld = false;
	public boolean runPressed = false; 
	public boolean runHeld = false;
	
	
	
	public MovementOptions(boolean walk, boolean run, boolean canTeleport)
	{
		this.canWalk = walk;
		this.canRun = run;
		this.canTeleport = canTeleport;
	}

	public boolean canRun()
	{
		return this.canRun;
	}
	
	public boolean canWalk()
	{
		return this.canWalk;
	}
	
	public boolean isInteractionAvailable()
	{
		return interactionAvailable;
	}
	
	public void setInteractionAvailable(boolean interactionAvailable)
	{
		this.interactionAvailable = interactionAvailable;
	}
	
	@Override
	public int getID() {
		return ComponentID.MOVEMENT_OPTIONS.ordinal();
	}

	public boolean canTeleport() {
		// TODO Auto-generated method stub
		return this.canTeleport;
	}
	
	
	
	
	public void bindUp(int key)
	{
		up = key;
	}
	
	public void bindDown(int key)
	{
		down = key;
	}
	
	public void bindLeft(int key)
	{
		left = key;
	}
	
	public void bindRight(int key)
	{
		right = key;
	}
	
	public void bindActivate(int key)
	{
		activate = key;
	}
	
	public void bindAttack(int key)
	{
		attack = key;
	}
	
	public void bindMenu(int key)
	{
		menu = key;
	}
	
	
	/**Tells if a key is currently pressed. Supported keys: up, down, activate
	 * 
	 */
	public boolean isPressed(int key)
	{
		if(key  == up)
		{
			if(upPressed) {return true;} else {return false;}
		}
		if(key  == down)
		{
			if(downPressed) {return true;} else {return false;}
		}
		if(key  == activate)
		{
			if(activatePressed) {return true;} else {return false;}
		}
		if(key  == attack)
		{
			if(attackPressed) {return true;} else {return false;}
		}
		return false;

	}
	
	/**
	 * Tells if a key has been held down. Supported keys: up, down, activate
	 * @param key
	 * @return
	 */
	public boolean isHeld(int key)
	{
		
		if(key  == up)
		{
			if(upHeld) 
			{
				return true;
			} 
			else 
			{
				return false;
			}
		}
		if(key  == down)
		{
			if(downHeld) 
			{ 
				return true;
			} 
			else 
			{ 
				return false;
			}
		}	
		if(key  == activate)
		{
			if(activateHeld) 
			{ 
				return true;
			} 
			else 
			{ 
				return false;
			}
		}	
		if(key  == attack)
		{
			if(attackHeld) 
			{ 
				return true;
			} 
			else 
			{ 
				return false;
			}
		}	
		return false;
	}
	
	public void update()
	{
		Keyboard.next();
		int key = Keyboard.getEventKey();
		if(!Keyboard.getEventKeyState()) //key was released
		{
			key = -2;
		}
		if(key  == up)
		{
			if(upPressed = true){upHeld = true;}
			upPressed = true;
			
		}
		else
		{
			upPressed = false;
			upHeld = false;
		}
		if(key  == down)
		{
			if(downPressed = true){downHeld = true;}
			downPressed = true;
		}
		else
		{
			downPressed = false;
			downHeld = false;
		}
		if(key  == activate)
		{
			if(activatePressed = true){activateHeld = true;}
			activatePressed = true;
		}
		else
		{
			activatePressed = false;
			activateHeld = false;
		}
		if(key  == attack)
		{
			if(attackPressed = true){attackHeld = true;}
			attackPressed = true;
		}
		else
		{
			attackPressed = false;
			attackHeld = false;
		}
	}
}