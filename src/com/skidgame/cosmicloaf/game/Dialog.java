package com.skidgame.cosmicloaf.game;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import com.skidgame.cosmicloaf.components.ComponentID;
import com.skidgame.cosmicloaf.components.Entity;
import com.skidgame.cosmicloaf.components.Interactible;
import com.skidgame.cosmicloaf.constants.EmotionEnum;

public class Dialog {
	private Sound responderSound;
	private EmotionEnum responderEmotion;
	private String prompt;
	private String responderSubtitle;
	
	private Sound initiatorSound;
	private EmotionEnum initiatorEmotion;
	private String initiatorSubtitle;
	
	public Dialog(String soundPath, EmotionEnum emotion, String myPrompt, String mySubtitle)
	{
		try {
			responderSound = new Sound("dialog/" +soundPath);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		responderEmotion = emotion;
		this.prompt = myPrompt;
		this.responderSubtitle = mySubtitle;
	}
	
	public Dialog(String soundPath, EmotionEnum emotion, String myPrompt, String mySubtitle, String initiatorSoundPath, EmotionEnum initiatorEmotion, String initiatorSubtitle)
	{
		try {
			responderSound = new Sound("dialog/" +soundPath);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		responderEmotion = emotion;
		this.prompt = myPrompt;
		this.responderSubtitle = mySubtitle;
		
		try {
			initiatorSound = new Sound("dialog/" +initiatorSoundPath);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.initiatorEmotion = initiatorEmotion;
		this.initiatorSubtitle = initiatorSubtitle;
	}
	
	public void playAndRender(Entity me, TheCosmicLoafGame game)
	{
		playAndRender(me, game, null);
	}
	
	public void playAndRender(Entity responder, TheCosmicLoafGame game, Entity initiator)
	{
		//TODO add emotion, prompt, and sub.
		if(responderSound != null)
		{
			game.setCurrentDialog(this);
			game.playCurrentDialog();
		}
		
		if(responder != null && responder.getArt() != null)
		{
			if(responder.getComponents()[ComponentID.INTERACTIBLE.ordinal()] != null)
			{
				Interactible i =(Interactible)responder.getComponents()[ComponentID.INTERACTIBLE.ordinal()];
				System.out.println(i.myTree.getChains().get(0).getChain().get(0).getMyPrompt());
			}
			
			game.setTargetedEntity(responder);
		}
		
		if(initiator != null)
		{
			//TODO fix this.
			if(initiatorSound != null)
			{
				game.playCurrentDialog();
			}
			
			if(responder != null && responder.getArt() != null)
			{
				if(responder.getComponents()[ComponentID.INTERACTIBLE.ordinal()] != null)
				{
					Interactible i =(Interactible)responder.getComponents()[ComponentID.INTERACTIBLE.ordinal()];
					System.out.println(i.myTree.getChains().get(0).getChain().get(0).getMyPrompt());;
				}
				
				game.setTargetedEntity(responder);
			}
		}
	}

	public Sound getInitiatorDialog()
	{
		return initiatorSound;
		
	}
	
	public Sound getResponderDialog() 
	{
		return responderSound;
	}

	public void setMySound(Sound mySound) {
		this.responderSound = mySound;
	}

	public EmotionEnum getMyEmotion() {
		return responderEmotion;
	}

	public void setMyEmotion(EmotionEnum myEmotion) {
		this.responderEmotion = myEmotion;
	}

	public String getMyPrompt() {
		return prompt;
	}

	public void setMyPrompt(String myPrompt) {
		this.prompt = myPrompt;
	}

	public String getResponderSubtitle() {
		return responderSubtitle;
	}

	public void setMySubtitle(String mySubtitle) {
		this.responderSubtitle = mySubtitle;
	}

	public String getInitiatorSubtitle() {
		return this.initiatorSubtitle;
	}
	
	

}
