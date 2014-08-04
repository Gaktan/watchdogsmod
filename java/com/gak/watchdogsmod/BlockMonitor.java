package com.gak.watchdogsmod;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockMonitor extends Block{

	protected BlockMonitor(Material material) {
		super(material);

		setHardness(3);
		setStepSound(Block.soundTypeMetal);
		setBlockName("monitoringDevice");
		setCreativeTab(WatchDogsMod.gakTab);
		setHarvestLevel("pickaxe", 0);
		setBlockTextureName(WatchDogsMod.MODID +":monitoringDevice");
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int metadata, float what, float these, float are) {

		Block block = world.getBlock(x, y, z);
		if (block == null || player.isSneaking()) {
			return false;
		}

		player.openGui(WatchDogsMod.instance, 0, world, x, y, z);
		return true;
	}
}
