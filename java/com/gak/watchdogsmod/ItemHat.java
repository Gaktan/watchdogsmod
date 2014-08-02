package com.gak.watchdogsmod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;

public class ItemHat extends ItemArmor{

	public static ArmorMaterial material = EnumHelper.addArmorMaterial("Hat", 10, new int[]{3, 7, 5, 3}, 30);

	public ItemHat(ArmorMaterial p_i45325_1_, int p_i45325_2_, int p_i45325_3_) {
		super(p_i45325_1_, p_i45325_2_, p_i45325_3_);

		setUnlocalizedName("hat");
		setCreativeTab(WatchDogsMod.gakTab);		
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		String itemName = WatchDogsMod.MODID+":" + "hat"; 
		this.itemIcon = iconRegister.registerIcon(itemName);
	}


	@Override 
	@SideOnly(Side.CLIENT)
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) { 
		if (stack.getItem() == WatchDogsMod.hat) { 
			return WatchDogsMod.MODID+":armor/hat_texture.png"; 
		} 
		return null; 
	}

	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) { 
		ModelBiped armorModel = null;
		if(itemStack != null){ 
			if(itemStack.getItem() instanceof ItemHat){

				int type = ((ItemArmor)itemStack.getItem()).armorType; 
				if(type == 0){
					armorModel = WatchDogsMod.hatModel;
				}
			} 
		}
		return armorModel;
	}
}
