package com.gak.watchdogsmod.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.gak.watchdogsmod.WatchDogsMod;

public class GuiList extends GuiScreen{

	public final int xSizeOfTexture = 256;
	public final int ySizeOfTexture = 86;

	public String []text;
	
	private static final ResourceLocation texture = new ResourceLocation(WatchDogsMod.MODID+ ":textures/gui/monitorGUI.png");

	public GuiList(){
		super();
		text = new String[6];
	}

	public void actionPerformed(GuiButton button){

		switch(button.id){
		case 0: 
			mc.displayGuiScreen(new GuiSelectString(this));
		}
	}

	@Override
	public void drawScreen(int x, int y, float f) {
		drawDefaultBackground();

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(texture);
		
		int lengthY = ySizeOfTexture/4;

		int posX = (width - xSizeOfTexture) / 2;
		int posY = (height - ySizeOfTexture) / 2;

		drawTexturedModalRect(posX, posY, 0, 0, xSizeOfTexture, ySizeOfTexture);
		
		FontRenderer fontRenderer = RenderManager.instance.getFontRenderer();
		
		int i;
		for(i = 0; i < text.length-1 ; i++){
			//drawString(fontRenderer, text[i], posX+5, posY+5, );
			fontRenderer.drawString(text[i], posX+5, posY+5, 0x333333);
			posY += 13;
		}

		String str = text[i];
		int c = isInteger(str);

		/*
		 * Big thing to handle the threat level
		 * #ugly as fuck
		 */
		if(!str.equals("")){
			int threat = 0;
			if(c == 3)
				text[i] = "Threat / " + str;
			else if(c == 1 || c == 2){
				threat = Integer.parseInt(""+str.charAt(9));
				if(c == 2){
					threat *= 10;
					threat += Integer.parseInt(""+str.charAt(10));
				}
				
				int color = 0x333333;
				if(threat > 85)
					color = 0xffff16;
				fontRenderer.drawString(text[i], posX+5, posY+5, color);
			}
		}

		super.drawScreen(x, y, f);
	}

	@Override
	public boolean doesGuiPauseGame(){
		return true;
	}

	@Override
	protected void keyTyped(char par1, int par2){
		if (par2 == 1 || par2 == this.mc.gameSettings.keyBindInventory.getKeyCode()){
			this.mc.thePlayer.closeScreen();
		}
	}

	@Override
	public void initGui() {
		buttonList.clear();

		int posX = (this.width - xSizeOfTexture) / 2 + 8;
		int posY = (this.height - ySizeOfTexture) / 2 - 10;

		buttonList.add(new GuiButton(0, posX, posY+ 96, 90, 20, "Select villager"));
	}

	public void setText(String text, int index){
		this.text[index] = text;
	}

	public void setText(String []text){
		this.text = text;
	}

	/**
	 * dis is ugly
	 * @param s
	 * @return 3 for full int, 2 for 2 digits int, 1 for 1 digit int, 0 for not number
	 */
	public int isInteger(String s){
		try{
			Integer.parseInt(s);

		}catch(NumberFormatException e){

			try{
				Integer.parseInt(""+s.charAt(10));

			}catch(StringIndexOutOfBoundsException e2){
				return 1;

			}catch(NumberFormatException e2){
				return 0;

			}
			return 2;
		}
		return 3;
	}
}
