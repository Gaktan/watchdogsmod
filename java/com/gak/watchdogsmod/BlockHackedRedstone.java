package com.gak.watchdogsmod;

import java.util.Random;

import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockHackedRedstone extends BlockRedstoneWire{

    @SideOnly(Side.CLIENT)
    private IIcon field_150182_M;
    @SideOnly(Side.CLIENT)
    private IIcon field_150183_N;
    @SideOnly(Side.CLIENT)
    private IIcon field_150184_O;
    @SideOnly(Side.CLIENT)
    private IIcon field_150180_P;
	
	public BlockHackedRedstone(){
		super();
		setBlockName("hackedRedstone");
		setCreativeTab(WatchDogsMod.gakTab);
		setTickRandomly(true);
		textureName = "redstone_dust";
	}

	@Override
	public void updateTick(World world, int x, int y,
			int z, Random r) {
		super.updateTick(world, x, y, z, r);

		if(!world.isRemote){	
			world.setBlock(x, y, z, Blocks.redstone_wire);
		}
	}

	@Override
	public int isProvidingStrongPower(IBlockAccess block,
			int x, int y, int z, int p_149748_5_) {

		return 15;
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess p_149709_1_, int p_149709_2_,
			int p_149709_3_, int p_149709_4_, int p_149709_5_) {
		return 15;
	}

	public boolean canProvidePower(){
		return true;
	}

}
