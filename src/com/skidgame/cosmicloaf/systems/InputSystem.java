package com.skidgame.cosmicloaf.systems;

import java.util.ArrayList;

import com.skidgame.cosmicloaf.Level;
import com.skidgame.cosmicloaf.components.Art;
import com.skidgame.cosmicloaf.components.ButtonComponent;
import com.skidgame.cosmicloaf.components.Component;
import com.skidgame.cosmicloaf.components.ComponentID;
import com.skidgame.cosmicloaf.components.Direction;
import com.skidgame.cosmicloaf.components.Entity;
import com.skidgame.cosmicloaf.components.Interactible;
import com.skidgame.cosmicloaf.components.Interactor;
import com.skidgame.cosmicloaf.components.MovementOptions;
import com.skidgame.cosmicloaf.components.Position;
import com.skidgame.cosmicloaf.components.WeaponComponent;
import com.skidgame.cosmicloaf.game.ButtonCode;
import com.skidgame.cosmicloaf.game.Camera;
import com.skidgame.cosmicloaf.game.TheCosmicLoafGame;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Input;

public class InputSystem
{

	
	/**
	 * Tests for input on a per entity basis. This method currently processes only player input. (It makes a lot of unneccessary tests).
	 * @param entities
	 * @param game
	 * @param level
	 */
	public static void process(ArrayList<Entity> entities, TheCosmicLoafGame game, Level level, Camera cam)
	{	

		if(entities == null)
		{
			System.out.println("Null entity.");
		}
		for(Entity e : entities) //For every entity.
			//TODO do we really need to test every entity? Should we just be storing player somewhere.
		{	
			if(e.getComponents()[ComponentID.PLAYER.ordinal()] != null && e.getComponents()[ComponentID.MOVEMENT_OPTIONS.ordinal()] != null) //If the entity is a player that can move.
			{
				MovementOptions mo = e.getMovementOptions();
				if((mo.canRun() && Keyboard.isKeyDown(mo.run))) //If the player can run and they want to run.
				{
					boolean invalidKey = true;
					if(Keyboard.isKeyDown(mo.up))
					{
						invalidKey = false;
						Movement.process(e, Direction.UP.ordinal(), game, level, (3 * TheCosmicLoafGame.walken), cam);
					}
					if(Keyboard.isKeyDown(mo.down) )
					{
						invalidKey = false;
						Movement.process(e, Direction.DOWN.ordinal(), game, level, (3 * TheCosmicLoafGame.walken), cam);
					}
					if(Keyboard.isKeyDown(mo.left) )
					{
						invalidKey = false;
						Movement.process(e, Direction.LEFT.ordinal(), game, level, (3 * TheCosmicLoafGame.walken), cam);
					}
					if(Keyboard.isKeyDown(mo.right))
					{
						invalidKey = false;
						Movement.process(e, Direction.RIGHT.ordinal(), game, level, (3 * TheCosmicLoafGame.walken), cam);
					}
					if(invalidKey) //Just so that the movement boolean gets set to off. O(1)
					{
						Movement.process(e, -7876, game, level, TheCosmicLoafGame.walken, cam);
					}
					
				}		
				else if(((MovementOptions)e.getComponents()[ComponentID.MOVEMENT_OPTIONS.ordinal()]).canWalk())
				{
					boolean invalidKey = true;
					if(Keyboard.isKeyDown(mo.up))
					{
						invalidKey = false;
						Movement.process(e, Direction.UP.ordinal(), game, level, TheCosmicLoafGame.walken, cam);
					}
					if(Keyboard.isKeyDown(mo.down) )
					{
						invalidKey = false;
						Movement.process(e, Direction.DOWN.ordinal(), game, level, TheCosmicLoafGame.walken, cam);
					}
					if(Keyboard.isKeyDown(mo.left))
					{
						invalidKey = false;
						Movement.process(e, Direction.LEFT.ordinal(), game, level, TheCosmicLoafGame.walken, cam);
					}
					if(Keyboard.isKeyDown(mo.right))
					{
						invalidKey = false;
						Movement.process(e, Direction.RIGHT.ordinal(), game, level, TheCosmicLoafGame.walken, cam);
					}
					if(invalidKey) //Just so that the movement boolean gets set to off. O(1)
					{
						Movement.process(e, -7876, game, level, TheCosmicLoafGame.walken, cam);
					}
				}		
				if(((MovementOptions)e.getComponents()[ComponentID.MOVEMENT_OPTIONS.ordinal()]).isInteractionAvailable())
				{
					if(Keyboard.isKeyDown(mo.activate) && !mo.isHeld(mo.activate))
					{
						Entity target = ((Interactor)e.getComponents()[ComponentID.INTERACTOR.ordinal()]).target;
						if(target != null)
						{
							((Interactible)target.getComponents()[ComponentID.INTERACTIBLE.ordinal()]).activate(target, e, game);
						}
						//Movement.process(e, InputSystem.right, game, level, TheCosmicLoafGame.walken * 100);
						
					}
				}
				
				if(Keyboard.isKeyDown(mo.attack) && !mo.isHeld(mo.attack))
				{
					WeaponComponent weapon = ((WeaponComponent)e.getComponents()[ComponentID.WEAPON.ordinal()]);
					if(weapon != null)
					{
						weapon.attack(game, e, cam);
					}
					else
					{
						System.out.println("In input system. Tried to attack with null weapon.");
					}
				}
				
				if(Keyboard.isKeyDown(mo.menu))
				{
					game.state = game.GAME_MENU;
				}
			}	
		}
	}


	public static void processMenu(ArrayList<Entity> menu, TheCosmicLoafGame theCosmicLoafGame, Entity player) {
	
		if(menu.size() > 0)
		{
			
			MovementOptions mo = player.getMovementOptions();
			if(TheCosmicLoafGame.menuOptionSelected > menu.size())
			{
				
				TheCosmicLoafGame.menuOptionSelected = 0;
			}
		
			if(TheCosmicLoafGame.menuOptionSelected < 0)
			{
				TheCosmicLoafGame.menuOptionSelected = menu.size() - 1;
			}
			
			//We are guaranteed that Something valid is selected.
			
			if(Keyboard.isKeyDown(mo.up) && !mo.isHeld(mo.up))
			{
				if(TheCosmicLoafGame.menuOptionSelected - 1 >= 0)
				{
					((ButtonComponent)menu.get(TheCosmicLoafGame.menuOptionSelected).getComponents()[ComponentID.BUTTON_COMPONENT.ordinal()]).toggleSelected(false);

					TheCosmicLoafGame.menuOptionSelected -= 1;
					((ButtonComponent)menu.get(TheCosmicLoafGame.menuOptionSelected).getComponents()[ComponentID.BUTTON_COMPONENT.ordinal()]).toggleSelected(true);
				
				}
			}
			else if(Keyboard.isKeyDown(mo.down) && !mo.isHeld(mo.down))
			{
				if(TheCosmicLoafGame.menuOptionSelected + 1 < menu.size())
				{
					((ButtonComponent)menu.get(TheCosmicLoafGame.menuOptionSelected).getComponents()[ComponentID.BUTTON_COMPONENT.ordinal()]).toggleSelected(false);

					TheCosmicLoafGame.menuOptionSelected += 1;
					((ButtonComponent)menu.get(TheCosmicLoafGame.menuOptionSelected).getComponents()[ComponentID.BUTTON_COMPONENT.ordinal()]).toggleSelected(true);
				}
			}
			else if(Keyboard.isKeyDown(mo.activate) && !mo.isHeld(mo.activate))
			{
				executeButton((ButtonComponent) menu.get(TheCosmicLoafGame.menuOptionSelected).getComponents()[ComponentID.BUTTON_COMPONENT.ordinal()], theCosmicLoafGame);
			}
			
		}
		else
		{
			System.out.println("No menu options. We're stuck.");
		}
	}

	private static void executeButton(ButtonComponent component, TheCosmicLoafGame tclg) 
	{
		
		if(component.getButtonCode() == ButtonCode.START_GAME.ordinal())
		{
			TheCosmicLoafGame.menuOptionSelected = 0;
			tclg.state = tclg.IN_GAME;
			tclg.loadLevel(1, tclg.getSaveFile()); //TODO right now, this always loads level 1. Use the info in component to load last level.
		}
		if(component.getButtonCode() == ButtonCode.RESUME_GAME.ordinal())
		{
			TheCosmicLoafGame.menuOptionSelected = 0;
			tclg.state = tclg.IN_GAME;
		}
		if(component.getButtonCode() == ButtonCode.EXIT_GAME.ordinal())
		{
			tclg.closeRequested();
		}
		if(component.getButtonCode() == ButtonCode.SAVE_GAME.ordinal())
		{
			tclg.saveGame();
		}
		if(component.getButtonCode() == ButtonCode.LOAD_GAME.ordinal())
		{
			tclg.loadGame();
		}
		if(component.getButtonCode() == ButtonCode.LOAD_LEVEL.ordinal())
		{
			tclg.loadLevel(component.getAttribute(), tclg.getSaveFile());
			tclg.state = tclg.IN_GAME;
		}
		if(component.getButtonCode() == ButtonCode.DIALOG_BUTTON.ordinal())
		{
			int chosenChain = component.getAttribute();
			tclg.getCurrentDialogTree().getChains().get(chosenChain).playNext(tclg.getTargetedEntity(), tclg);
			tclg.inDialogMenu = false;
		}
		

		
	}
	
	
	
	public static void update(ArrayList<Entity> players)
	{
		for(Entity e : players)
		{
			if(e.getMovementOptions() != null)
			{
				e.getMovementOptions().update();
			}
		}
	}
	

	

}
