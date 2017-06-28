package com.skidgame.cosmicloaf.components;

public class ButtonComponent extends Component
{
	private int buttonCode; //buttonCode associates to a ButtonCode (enum in cosmicloaf.game
	private String buttonText;
	private int attribute;
	private boolean selected;
	private int xOffset; //Offsets for text
	private int yOffset;
	
	private ComponentID id;
	
	public ButtonComponent(int buttonCode, String buttonText, int attr, int xOffset, int yOffset)
	{
		this.buttonCode = buttonCode;
		this.buttonText = buttonText;
		this.attribute = attr;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.id = ComponentID.BUTTON_COMPONENT;
	}
	
	public int getxOffset()
	{
		return this.xOffset;
	}
	
	public int getyOffset()
	{
		return this.yOffset;
	}
	
	public int getButtonCode() {
		return buttonCode;
	}



	public String getButtonText() {
		return buttonText;
	}



	public int getAttribute() {
		return attribute;
	}



	public int getID()
	{
		return id.ordinal();
	}
	
	public boolean isSelected() {
		return this.selected;
		}
	
	public void toggleSelected(boolean set)
	{
		this.selected = set;
	}
	
}
