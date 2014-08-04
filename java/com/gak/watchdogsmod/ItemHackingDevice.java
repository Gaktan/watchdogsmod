package com.gak.watchdogsmod;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemHackingDevice extends Item{

	public ItemHackingDevice(int maxStackSize, CreativeTabs tab, String name){
		setMaxStackSize(maxStackSize);
		setCreativeTab(tab);
		setNoRepair();
		setUnlocalizedName(name);
		setTextureName(WatchDogsMod.MODID + ":" + "phone");
	}



	@Override
	public boolean itemInteractionForEntity(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, EntityLivingBase par3EntityLivingBase) {

		ItemStack heldItem = par2EntityPlayer.getHeldItem();
		if (heldItem != null && heldItem.getItem() == WatchDogsMod.hackingDevice) {
			if (par3EntityLivingBase instanceof EntityVillager) {
				
				EntityVillager villager = (EntityVillager) par3EntityLivingBase;

				EntityAdvancedVillager adv = EntityAdvancedVillager.get(villager);
				
				if(adv != null){
					if(par2EntityPlayer.worldObj.isRemote){
						adv.setRenderInfos(true);
					}
					else{
						if(EntityAdvancedVillager.get(villager).threatLevel > 85){
							EntityAdvancedVillager.setNewTarget(villager);
						}
					}
				}	
			}
		}

		return super.itemInteractionForEntity(par1ItemStack, par2EntityPlayer,
				par3EntityLivingBase);
	}

	@Override
	public boolean onItemUse(ItemStack tool,
			EntityPlayer player, World world, int x, int y,
			int z, int par7, float xFloat, float yFloat, float zFloat)
	{
		if(!player.worldObj.isRemote){
			if (!player.canPlayerEdit(x, y, z, par7, tool))//can the player edit this block? if he cant then dont do anything
			{
				return false;
			}
			Block target = world.getBlock(x, y, z);
			if (target == Blocks.redstone_wire){
				world.setBlock(x, y, z, WatchDogsMod.hackedRedstone);
			}
			else if(target == WatchDogsMod.hackedRedstone){
				world.setBlock(x, y, z, Blocks.redstone_wire);
			}
			else if(target == Blocks.iron_door){
				int pos = world.getBlockMetadata(x, y, z);
				
				if(pos == 8){//Top door
					return false;
				}
				int next = 0;
				String sound;
				if(pos < 4){//if closed
					next = pos + 4;
					sound = "door_open";
				}
					
					
				else{//if open
					next = pos - 4;
					sound = "door_close";
				}
					
				
				world.setBlockMetadataWithNotify(x, y, z, next, Block.getIdFromBlock(Blocks.iron_door));
				player.playSound(sound, 1, 1);
			}

			return true;
		}
		return false;
	}
}
