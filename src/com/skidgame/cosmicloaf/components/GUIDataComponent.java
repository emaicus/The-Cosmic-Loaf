package com.skidgame.cosmicloaf.components;


public class GUIDataComponent extends Component
{
	private String data;
	private float[] numData;
	private GUIElementTypes dataType;
	
	public GUIDataComponent(String data, GUIElementTypes dataType)
	{
		this.data = data;
		this.dataType = dataType;
	}
	
	public GUIDataComponent(String data, GUIElementTypes dataType, float... numData){
		this.data = data;
		this.dataType = dataType;
		this.numData = numData;
	}
	
	
	
	public void setData(String data)
	{
		this.data = data;
	}
	
	public String getData()
	{
		return this.data;
	}
	
	public int getDataType()
	{
		return this.dataType.ordinal();
	}
	
	public String getDataAsString()
	{
		String s = "" + this.data;
		return s;
	}
	
	
	public int getID() {
		return ComponentID.GUI_DATA.ordinal();
	}

	public float[] getNumData() {
		return numData;
	}

	public void setNumData(float[] num) {
		this.numData = num;
	}

	public void setDataType(GUIElementTypes dataType) {
		this.dataType = dataType;
	}

}
