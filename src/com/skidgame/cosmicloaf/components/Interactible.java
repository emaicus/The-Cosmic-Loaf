package com.skidgame.cosmicloaf.components;

import org.newdawn.slick.Sound;

import com.skidgame.cosmicloaf.game.DialogTree;
import com.skidgame.cosmicloaf.game.TheCosmicLoafGame;

public class Interactible extends Component
{
	public boolean openAble;
	private Art toToggle;
	public boolean pickUp;
	//TODO decide how pickups are to be handled.
	public boolean talk;
	public DialogTree myTree;
	//TODO add sound file/files to play.
	public boolean read;
	public String[] myText;

	int currentLine = -1;
	public String[] readText;
	public boolean currentlyActivated;
	public boolean wasActivated;
	
	

	public Interactible(boolean openAble, boolean pickUp, boolean talk,
			boolean read, boolean activated) {
		this.openAble = openAble;
		this.pickUp = pickUp;
		this.talk = talk;
		this.read = read;
		this.currentlyActivated = activated;
		this.wasActivated = currentlyActivated;
	}

	public boolean isActivated() {
		return currentlyActivated;
	}
	
	public boolean wasActivated()
	{
		return this.wasActivated;
	}

	public void setActivated(boolean activated) {
		this.currentlyActivated = activated;
		if(activated = true)
		{
			this.wasActivated = true;
		}
	}

	public boolean isOpenAble() {
		return openAble;
	}

	public void setOpenAble(boolean openAble) {
		this.openAble = openAble;
	}

	public boolean isPickUp() {
		return pickUp;
	}

	public void setPickUp(boolean pickUp) {
		this.pickUp = pickUp;
	}

	public boolean isTalk() {
		return talk;
	}

	public void setTalk(boolean talk) {
		this.talk = talk;
	}
	
	
	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}
	
	public void setMyLines(String... lines)
	{
		System.out.println("Setting readlines with first line" + lines[0]);
		readText = lines;
	}
	

	@Override
	public int getID() {
		
		return ComponentID.INTERACTIBLE.ordinal();
	}
	
	/**
	 * Use this version if you don't want the interactor to be animated.
	 * 
	 * Causes an interaction to happen.
	 * 
	 * Unfortunately, interactible requires a reference to the entity that holds it.
	 * @param me
	 */
	public void activate(Entity me, TheCosmicLoafGame game)
	{
		System.out.println("Interactible... ACTIVATE!");
		//TODO finish this method as more systems/components are finished.
		if(openAble)
		{
			if(me.getArt() != null)
			{
				if(currentlyActivated) //already open.
				{
					if(me.getArt().animations.get(AnimationType.CLOSE) != null)
					{
						me.getArt().currentAnimation = me.getArt().animations.get(AnimationType.CLOSE);
						me.getArt().toggle();
					}
					else
					{
						me.getArt().toggle();
					}
				}
				else
				{
					if(me.getArt().animations.get(AnimationType.OPEN) != null)
					{
						me.getArt().currentAnimation = me.getArt().animations.get(AnimationType.OPEN);
						me.getArt().toggle();
					}
					else
					{
						me.getArt().toggle();	
					}
				}
			}
		}
		if(pickUp) //TODO makes the assumption that the main character picks it up.
		{
			if(me.getArt().animations.get(AnimationType.PICK_UP) != null)
			{
				me.getArt().currentAnimation = me.getArt().animations.get(AnimationType.PICK_UP);
				game.removeEntity(me);
				game.getCurrentLevel().getPlayer().get(0).getInventory().pickUp(me);
			}
			else
			{
				game.removeEntity(me);
				game.getCurrentLevel().getPlayer().get(0).getInventory().pickUp(me);
			}
		}
		if(talk)
		{
			if(me.getArt().animations.get(AnimationType.TALK) != null)
			{
				me.getArt().currentAnimation = me.getArt().animations.get(AnimationType.TALK);
				if(myTree != null)
				{
					this.myTree.getChains().get(0).playNext(me, game);
				}			
				//TODO: Play sound file!
			}
			else
			{
				if(myTree != null)
				{
					this.myTree.getChains().get(0).playNext(me, game);
				}

				//TODO: Play sound file!
			}
			
		}
		if(read)
		{
			if(me.getArt().animations.get(AnimationType.READ) != null)
			{
				me.getArt().currentAnimation = me.getArt().animations.get(AnimationType.READ);
				//TODO: Output text to GUI.
			}
			else
			{
				//TODO: Output text to GUI.
			}
		}
		
		if(currentlyActivated)
		{
			currentlyActivated = false;
		}
		else
		{
			currentlyActivated = true;
		}
				
		wasActivated = true;
	}
	
	/**
	 * Use this version if you want interactor to be animated if possible.
	 * 
	 * Causes an interaction to happen.
	 * 
	 * Unfortunately, interactible requires a reference to the entity that holds it.
	 * @param me, Interactor
	 */
	public void activate(Entity me, Entity interactor, TheCosmicLoafGame game)
	{
		System.out.println("Interactible... ACTIVATE!");
		Interactible myInteractible = this;
		//TODO finish this method as more systems/components are finished.
		if(openAble)
		{
			if(me.getArt() != null)
			{
				if(currentlyActivated) //already open.
				{
					if(me.getArt().animations.get(AnimationType.CLOSE) != null)
					{
						me.getArt().currentAnimation = me.getArt().animations.get(AnimationType.CLOSE);
						me.getArt().toggle();
					}
					else
					{
						me.getArt().toggle();
					}
				}
				else
				{
					if(me.getArt().animations.get(AnimationType.OPEN) != null)
					{
						me.getArt().currentAnimation = me.getArt().animations.get(AnimationType.OPEN);
						me.getArt().toggle();
					}
					else
					{
						me.getArt().toggle();	
					}
				}
			}
		}
		if(pickUp) //TODO makes the assumption that the main character picks it up.
		{
			if(me.getArt().animations.get(AnimationType.PICK_UP) != null)
			{
				me.getArt().currentAnimation = me.getArt().animations.get(AnimationType.PICK_UP);
				game.removeEntity(me);
				game.getCurrentLevel().getPlayer().get(0).getInventory().pickUp(me);
			}
			else
			{
				game.removeEntity(me);
				game.getCurrentLevel().getPlayer().get(0).getInventory().pickUp(me);
			}
		}
		if(talk)
		{	
			if(me.getArt().animations.get(AnimationType.TALK) != null)
			{
				me.getArt().currentAnimation = me.getArt().animations.get(AnimationType.TALK);
				
				if(myTree != null)
				{
					//this.myTree.getChains().get(0).playNext(me, game);
					game.processDialogOptions(me, interactor);
				}	
				//TODO: Play sound file!
//				game.currentText = myInteractible.getNextReadText();
//				game.displayTextBox = true;
			}
			else
			{
				
				if(myTree != null)
				{
					game.processDialogOptions(me, interactor);

					//this.myTree.getChains().get(0).playNext(me, game);
				}
			
				//TODO: Play sound file!
//				game.currentText = myInteractible.getNextReadText();
//				game.displayTextBox = true;
			}
		}
		if(read)
		{
			System.out.println("Current line is " + currentLine);

			if(me.getArt().animations.get(AnimationType.READ) != null)
			{
				me.getArt().currentAnimation = me.getArt().animations.get(AnimationType.READ);
				game.setCurrentText(myInteractible.getNextReadText());
				game.displayTextBox = true;
			}
			else
			{
				game.setCurrentText(myInteractible.getNextReadText());
				game.displayTextBox = true;
			}
		}
		
		if(currentlyActivated)
		{
			currentlyActivated = false;
		}
		else
		{
			currentlyActivated = true;
		}
						
		wasActivated = true;
		
		boolean played = false;
		
		/**
		 * The code necessary to play the proper animations for the interacting entity.
		 */
		if(interactor.getArt() != null) 
		{
			if(openAble)
			{
				if(interactor.getArt().animations.get(AnimationType.OPEN_ACTOR) != null)
				{
					interactor.getArt().currentAnimation = interactor.getArt().animations.get(AnimationType.OPEN_ACTOR);
					played = true;
				}
			}
			if(pickUp) //TODO makes the assumption that the main character picks it up.
			{
				if(interactor.getArt().animations.get(AnimationType.PICK_UP_ACTOR) != null)
				{
					interactor.getArt().currentAnimation = interactor.getArt().animations.get(AnimationType.PICK_UP_ACTOR);
					played = true;
				}
			}
			if(talk)
			{
				if(interactor.getArt().animations.get(AnimationType.TALK) != null)
				{
					interactor.getArt().currentAnimation = interactor.getArt().animations.get(AnimationType.TALK);
					played = true;
				}
			}
			if(read)
			{
				if(interactor.getArt().animations.get(AnimationType.READ) != null)
				{
					interactor.getArt().currentAnimation = interactor.getArt().animations.get(AnimationType.READ);
					played = true;
				}
			}
			
			if(played == false)
			{
				if(interactor.getArt().animations.get(AnimationType.ACTIVATE_ACTOR) != null)
				{
					interactor.getArt().currentAnimation = interactor.getArt().animations.get(AnimationType.ACTIVATE_ACTOR);
					played = true;
				}
			}
		}
	}

	private String getNextReadText() {

		if((currentLine > (readText.length - 2)) || currentLine < 0) //validity test
		{
			currentLine = 0;
		}
		else
		{
			currentLine++;
		}
		
		if(readText.length == 0) //empty test
		{
			return "";
		}
		String nextString = readText[currentLine];
		return nextString;
	}

	public void setTree(DialogTree tree) {

		this.myTree = tree;
	}

	public DialogTree getMyTree() {
		return myTree;
	}
}
