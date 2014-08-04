package com.gak.watchdogsmod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class RandomAdvancedVillager {

	public static ArrayList <String>names = new ArrayList<String>();

	public static ArrayList <String>surnames = new ArrayList<String>();

	public static ArrayList <String>infos = new ArrayList<String>();

	public static String getRandom(ArrayList <String> str) {
		return str.get((int) (Math.random() * str.size()));
	}

	public static void loadStrings(ArrayList <String> stringList, String strFile){
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					WatchDogsMod.class.getResourceAsStream("/assets/watchdogsmod/" + strFile)));
			String line = br.readLine();

			while (line != null) {
				stringList.add(line);

				line = br.readLine();  
			}
			br.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
