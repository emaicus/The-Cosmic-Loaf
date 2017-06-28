package com.skidgame.cosmicloaf.tiledSupport;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TiledCleaner 
{
	public static void clean(String path) throws IOException, BadFormatExeption
	{
		BufferedReader reader = null;
		try {
			FileReader f = new FileReader(path);
			 reader = new BufferedReader(f);
			 System.out.println("Opened");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Failed to open.");
			e.printStackTrace();
		}
		
		ArrayList<String> lines = new ArrayList<String>();
		
		String line = null;
		
		line = reader.readLine();
		
		while(line != null)
		{
			if(line.contains("data encoding") && !line.contains("gzip"))
			{
				throw new BadFormatExeption();
			}
			//System.out.println(line);
			if(line.contains("tile id=") && line.contains("terrain="))
			{
				//TODO have to check next line so we can be sure it terminates correctly.
			//	System.out.println("contains tile id= and terrain=");
				String split[] = line.split(" terrain");
				if(split[1].endsWith("/>"))
				{
					line = reader.readLine();
					continue;

				}
				else
				{
				
					line = split[0] + ">";

				}
				//System.out.println("Became: " + line);
			}
			
			if(!line.contains("xml") || line.contains("map version"))
			{
				String pat = ".*?[0-9]+([.][0-9]+)"; 
				Pattern pattern = Pattern.compile(pat);
				Matcher match = pattern.matcher(line);
				System.out.println(line);
				while(match.find()) //checks to see if we have a double (Slick will try to parse it as an int).
				{
					int i = 1;
				
					line = line.substring(0, match.start(1)) + line.subSequence(match.end(1), line.length());
					match = pattern.matcher(line);


				}
			}
			
			
			
			lines.add(line);
			line = reader.readLine();
			
		}
		
		BufferedWriter writer = null;
		System.out.println(path);
		String[] pathParts = path.split("[.]");
		System.out.println(pathParts[0]);
		System.out.println(pathParts[1]);
		String newPath = pathParts[0] + "Clean." + pathParts[1];
			FileWriter fileWriter = new FileWriter(newPath);
			writer = new BufferedWriter(fileWriter);
		
		boolean skip = false;
		if(writer!=null)
		{
			for(String nextLine : lines)
			{
				
				if(nextLine.contains("objectgroup name") && !nextLine.contains("width"))
				{
					String[] subsequence = nextLine.split("[>]");
					nextLine = subsequence[0] + " width=\"50\" height=\"50\">";
				}
				
				if(nextLine.contains("object id=") && !nextLine.contains("width"))
				{
					String[] subsequence = nextLine.split("[>]");
					nextLine = subsequence[0] + " width=\"50\" height=\"50\">";
				}
				
				if(nextLine.contains("objectgroup draworder"))
				{
					skip = true;
				}
				if(nextLine.contains("<terraintypes>"))
				{
					 skip = true;
				}
				
				
				
				if(!skip)
				{
					writer.write(nextLine + "\n"); //TODO prints one extra newline.
				}
				if(nextLine.contains("</terraintypes>"))
				{
					skip = false;
				}
				if(nextLine.contains("</objectgroup>"))
				{
					skip = false;
				}
				
				
			}
		}
		
		writer.close();
		reader.close();
		
		
	}
	
//	public static void main (String... args)
//	{
//		
//			try {
//				clean("levels/Alley_test.tmx");
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		
//		
//	}
}
