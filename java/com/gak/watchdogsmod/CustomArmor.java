package com.gak.watchdogsmod;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CustomArmor extends ItemArmor{

	public static ArmorMaterial material = EnumHelper.addArmorMaterial("Hat", 10, new int[]{3, 7, 5, 3}, 30);

	public CustomArmor(ArmorMaterial p_i45325_1_, int p_i45325_2_, int slot) {
		super(p_i45325_1_, p_i45325_2_, slot);

		if(slot == 0)
			setUnlocalizedName("hat");
		else if(slot == 1)
			setUnlocalizedName("coat");

		setCreativeTab(WatchDogsMod.gakTab);		
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		String itemName = "";
		if(armorType == 0)
			itemName = WatchDogsMod.MODID+":" + "hat"; 
		else if (armorType == 1)
			itemName = WatchDogsMod.MODID+":" + "coat"; 

		this.itemIcon = iconRegister.registerIcon(itemName);
	}


	@Override 
	@SideOnly(Side.CLIENT)
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) { 
		if (stack.getItem() == WatchDogsMod.hat) { 
			return WatchDogsMod.MODID+":armor/hat_texture.png"; 
		}
		else if (stack.getItem() == WatchDogsMod.coat) { 
			return WatchDogsMod.MODID+":armor/coat_texture.png"; 
		}
		return null; 
	}

	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) { 
		ModelBiped armorModel = null;
		if(itemStack != null){ 
			if(itemStack.getItem() instanceof CustomArmor){

				int type = ((ItemArmor)itemStack.getItem()).armorType; 
				if(type == 0){
					armorModel = WatchDogsMod.hatModel;
				}
				else if(type == 1){
					armorModel = WatchDogsMod.coatModel;
				}
			} 
		}
		if(armorModel != null){
			armorModel.isSneak = entityLiving.isSneaking();
			armorModel.isRiding = entityLiving.isRiding();
			armorModel.isChild = entityLiving.isChild();
			armorModel.heldItemRight = entityLiving.getEquipmentInSlot(0) != null ? 1 :0;
			
			if(entityLiving instanceof EntityPlayer){
				armorModel.aimedBow =((EntityPlayer)entityLiving).getItemInUseDuration() > 2;
			}
		}
		return armorModel;
	}
}
