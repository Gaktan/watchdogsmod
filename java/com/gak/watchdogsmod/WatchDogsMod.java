package com.gak.watchdogsmod;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;

import com.gak.watchdogsmod.gui.GuiHandler;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLLoadEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;


@Mod(modid = WatchDogsMod.MODID, version = WatchDogsMod.VERSION, name = WatchDogsMod.NAME)
public class WatchDogsMod
{	
    public static final String MODID = "Watchdogsmod";
    public static final String NAME = "Watch Dogs Mod";
    public static final String MCVERSION = "1.7.10";
    public static final String VERSION = MCVERSION + "-" + "0.2";

    @Instance(value = MODID)
    public static WatchDogsMod instance;
    
    public static SimpleNetworkWrapper network;
    
    public static CreativeTabs gakTab = new CreativeTabsMyMod("Gak");
    
	public static Item hackingDevice;
	public static Item hat;
	public static Item coat;
	
	public static Block monitoringDevice;
	public static Block hackedRedstone;
	
	public static ModelBiped hatModel;
	public static ModelBiped coatModel;
	
    @EventHandler
    public void load(FMLLoadEvent event){
    	instance = this;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent preInitEvent) {
    	
    	preInitEvent.getModMetadata().version=VERSION;
    	
    	MinecraftForge.EVENT_BUS.register(new MyEventHandler());
    	
    	network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
        network.registerMessage(WatchPackets.ServerHandler.class, WatchPackets.class, 0, Side.SERVER);
        network.registerMessage(WatchPackets.ClientHandler.class, WatchPackets.class, 0, Side.CLIENT);
    	
        //Items
    	hackingDevice = new ItemHackingDevice(1, gakTab, "HackingDevice");
    	hat = new CustomArmor(CustomArmor.material, 4, 0);
    	coat = new CustomArmor(CustomArmor.material, 4, 1);
    	//Register
    	GameRegistry.registerItem(hackingDevice, hackingDevice.getUnlocalizedName());
        GameRegistry.registerItem(hat, hat.getUnlocalizedName());
        GameRegistry.registerItem(coat, coat.getUnlocalizedName());
    	
    	//Blocks
    	monitoringDevice = new BlockMonitor(Material.rock);
    	hackedRedstone = new BlockHackedRedstone();
    	//Register
    	GameRegistry.registerBlock(monitoringDevice, monitoringDevice.getUnlocalizedName());
    	GameRegistry.registerBlock(hackedRedstone, hackedRedstone.getUnlocalizedName());
    	
    	
    	//Misc
    	hatModel = new ModelHat(1);
    	coatModel = new ModelCoat(1);
    	
    	RandomAdvancedVillager rav = new RandomAdvancedVillager();
    	rav.loadStrings(rav.names, "names.txt");
    	rav.loadStrings(rav.surnames, "surnames.txt");
    	rav.loadStrings(rav.infos, "infos.txt");   
    	
    	new GuiHandler();
    }
}
