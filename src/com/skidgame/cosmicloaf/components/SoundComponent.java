package com.skidgame.cosmicloaf.components;

import java.util.HashMap;
import org.newdawn.slick.Sound;
import com.skidgame.cosmicloaf.constants.SFXTypeEnum;

public class SoundComponent extends Component
{
	
	private HashMap<SFXTypeEnum, Sound> mySounds;
	public SoundComponent()
	{
		
	}
	
	
	public int getID() {
		return ComponentID.SOUND.ordinal();
	}

}
