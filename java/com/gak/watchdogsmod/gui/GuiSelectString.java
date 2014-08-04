package com.gak.watchdogsmod.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StringTranslate;

public class GuiSelectString extends GuiScreen{
	
	/** This GUI's parent GUI. */
	protected GuiList parentGui;

	protected GuiSlotList list;

	public GuiSelectString(GuiList parent)
	{
		this.parentGui = parent;
		this.list = new GuiSlotList(parent);
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	public void initGui()
	{
		StringTranslate var1 = new StringTranslate();
		this.buttonList.add(new GuiButton(0, 25, this.height - 38, 120, 20, var1.translateKey("gui.cancel")));
	}
	/**
	 * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
	 */
	protected void actionPerformed(GuiButton par1GuiButton)
	{
		if (par1GuiButton.enabled)
		{
			switch (par1GuiButton.id)
			{
			case 0:
				this.mc.displayGuiScreen(this.parentGui);
			}
		}
	}

	public void drawScreen(int par1, int par2, float par3)
	{
		this.list.drawScreen(par1, par2, par3);
		super.drawScreen(par1, par2, par3);
	}
}
