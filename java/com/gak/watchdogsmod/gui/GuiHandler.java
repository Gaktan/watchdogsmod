package com.gak.watchdogsmod.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.gak.watchdogsmod.WatchDogsMod;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

public class GuiHandler implements IGuiHandler{

	public GuiHandler(){
		NetworkRegistry.INSTANCE.registerGuiHandler(WatchDogsMod.instance, this);
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		return getCommonGuiElement(ID);
	}
	
	public Object getCommonGuiElement(int ID){
		switch(ID){
		default:
			return null;
		case 0:
			GuiList gui = new GuiList();
			String []text = {	"Click the button bellow to see all the villagers",
								"currently monitored. Double click on their name",
								"to access their informations.", 
								""};
			gui.setText(text);
			return gui;
		}
	}
}
