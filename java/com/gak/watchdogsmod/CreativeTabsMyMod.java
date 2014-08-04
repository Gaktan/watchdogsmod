package com.gak.watchdogsmod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CreativeTabsMyMod extends CreativeTabs {

	public CreativeTabsMyMod(String par2Str) {
		super(par2Str);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
		return WatchDogsMod.hackingDevice;
	}

}
