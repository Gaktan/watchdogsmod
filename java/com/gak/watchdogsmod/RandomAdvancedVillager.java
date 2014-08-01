package com.gak.watchdogsmod;

import io.netty.handler.codec.http.HttpHeaders.Names;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Random;

import net.minecraft.util.ResourceLocation;

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
