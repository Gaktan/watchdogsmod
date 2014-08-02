package com.gak.watchdogsmod;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


@Mod(modid = WatchDogsMod.MODID, version = WatchDogsMod.VERSION, name = WatchDogsMod.NAME)
public class WatchDogsMod
{	
    public static final String MODID = "Watchdogsmod";
    public static final String NAME = "Watch Dogs Mod";
    public static final String VERSION = "0.1";
    
    public static SimpleNetworkWrapper network;
    
    public static CreativeTabs gakTab = new CreativeTabsMyMod("Gak");
    
	public static Item hackingDevice;
	public static Item hat;
	
	public static ModelBiped hatModel;
	
    @EventHandler
    public void init(FMLInitializationEvent event){
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent preInitEvent) {
    	
    	MinecraftForge.EVENT_BUS.register(new MyEventHandler());
    	
    	network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
        network.registerMessage(WatchPackets.ServerHandler.class, WatchPackets.class, 0, Side.SERVER);
        network.registerMessage(WatchPackets.ClientHandler.class, WatchPackets.class, 0, Side.CLIENT);
    	
    	hackingDevice = new ItemHackingDevice(1, gakTab, "HackingDevice");
    	hat = new ItemHat(ItemHat.material, 4, 0);
    	
    	hatModel = new ModelHat(1);
    	
    	RandomAdvancedVillager rav = new RandomAdvancedVillager();
    	rav.loadStrings(rav.names, "names.txt");
    	rav.loadStrings(rav.surnames, "surnames.txt");
    	rav.loadStrings(rav.infos, "infos.txt");
        
        GameRegistry.registerItem(hackingDevice, hackingDevice.getUnlocalizedName());
        GameRegistry.registerItem(hat, hat.getUnlocalizedName());
    }
}
