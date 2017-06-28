package com.skidgame.cosmicloaf.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import com.skidgame.cosmicloaf.components.ComponentID;
import com.skidgame.cosmicloaf.components.Entity;
import com.skidgame.cosmicloaf.components.GUIDataComponent;
import com.skidgame.cosmicloaf.components.GUIElementTypes;
import com.skidgame.cosmicloaf.components.Position;
import com.skidgame.cosmicloaf.systems.CollisionSystem;

public class SaveFile 
{
	public boolean newSave;
	private int MainCharacterBox;
	private int CurrentLevel;
	
	
	public SaveFile()
	{
		CurrentLevel = 1;
		newSave = true;
	}
	
	public void readInFile(String FilePath)
	{
		newSave = false;
		FileReader scan;
		BufferedReader buffer = null;
		String box = "";
		String level = "";
		try {
			scan = new FileReader(FilePath);
			buffer = new BufferedReader(scan);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			box = buffer.readLine();
			level = buffer.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MainCharacterBox = Integer.parseInt(box);
		CurrentLevel = Integer.parseInt(level);
		
		
		try {
			buffer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeOutFile(String filePath, TheCosmicLoafGame game, Camera cam)
	{
		String toFile = "";
		
		Entity player = game.getCurrentLevel().getPlayer().get(0);
		Position p = ((Position)player.getComponents()[ComponentID.POSITION.ordinal()]);
		int[] coords = CollisionSystem.convertPositionToBoxes(p.getLiteralCoordinates().get(0), game, game.getCurrentLevel(), cam);
		
		toFile += coords[0] +"\n";
		toFile += game.getCurrentLevel().getLevel();
	
		
		
		
		
		
		
		BufferedWriter buffer = null;
		FileWriter fileWrite = null;
		
		try {
			fileWrite = new FileWriter(filePath);
			buffer = new BufferedWriter(fileWrite);
			buffer.write(toFile);
			buffer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public int getCurrentLevel() {
		// TODO Auto-generated method stub
		return this.CurrentLevel;
	}
	
	public int getMainCharacterBox() {
		// TODO Auto-generated method stub
		return this.MainCharacterBox;
	}
	
	
	
}
