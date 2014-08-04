package com.gak.watchdogsmod.gui;

import java.util.StringTokenizer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.EntityVillager;

import com.gak.watchdogsmod.EntityAdvancedVillager;

class GuiSlotList extends GuiSlot{

	protected Minecraft mc;

	protected int slotHeight;

	private String[] strings;

	private GuiList gui;

	public GuiSlotList(GuiList gui){
		super(gui.mc, gui.width, gui.height, 32, gui.height, 24);
		this.slotHeight = 24;
		this.mc = gui.mc;
		this.gui = gui;

		strings = EntityAdvancedVillager.getAllVillagersNames(mc.thePlayer.worldObj);
	}

	/**
	 * Gets the size of the current slot list.
	 */
	protected int getSize(){
		return this.strings.length;
	}

	/**
	 * the element in the slot that was clicked, boolean for wether it was double clicked or not
	 */
	protected void elementClicked(int index, boolean twice, int par3, int par4){
		if(twice){
			StringTokenizer st = new StringTokenizer(strings[index], ",");
			int id = Integer.parseInt(st.nextToken());
			
			EntityVillager villager = (EntityVillager) mc.thePlayer.worldObj.getEntityByID(id);
			EntityAdvancedVillager eav = EntityAdvancedVillager.get(villager);
			
			this.gui.setText(eav.toStringList(villager));
			this.mc.displayGuiScreen(gui);
		}
	}

	/**
	 * returns true if the element passed in is currently selected
	 */
	protected boolean isSelected(int par1){
		return false;
	}

	/**
	 * return the height of the content being scrolled
	 */
	protected int getContentHeight(){
		return this.getSize() * 12;
	}

	protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5Tessellator, int par6, int par7){
		FontRenderer fontRenderer = RenderManager.instance.getFontRenderer();
		fontRenderer.setBidiFlag(true);
		this.gui.drawCenteredString(fontRenderer, strings[par1], this.gui.width / 2, par3 + 1, 16777215);
	}

	@Override
	protected void drawBackground(){
		this.gui.drawDefaultBackground();
	}
}