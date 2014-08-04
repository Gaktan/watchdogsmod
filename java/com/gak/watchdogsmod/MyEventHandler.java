package com.gak.watchdogsmod;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.player.PlayerEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class MyEventHandler{

	public MyEventHandler() {
		// TODO Auto-generated constructor stub
	}

	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event){

		Entity e = event.entity;
		if(e != null){
			if (e instanceof EntityVillager){
				EntityVillager villager = (EntityVillager) e;
				EntityAdvancedVillager.register(villager);
			}
		}
	}
	@SubscribeEvent
	public void onPlayerStartTracking(PlayerEvent.StartTracking event){

		Entity e = event.target;
		EntityPlayerMP player = (EntityPlayerMP) event.entityPlayer;

		if (e instanceof EntityVillager){
			EntityVillager villager = (EntityVillager) e;
			if(!player.worldObj.isRemote){
				EntityAdvancedVillager eav = EntityAdvancedVillager.get(villager);
				WatchDogsMod.network.sendTo(new WatchPackets(eav.toString()), player);
			}
		}
	}

	@SubscribeEvent
	public void onVillagerRender(RenderLivingEvent.Post event){
		if(event.entity.worldObj.isRemote){
			if (event.entity instanceof EntityVillager) {

				EntityVillager villager = (EntityVillager) event.entity;

				EntityAdvancedVillager adv = EntityAdvancedVillager.get(villager);
				if(adv == null)
					return;
				if(adv.isRenderingInfos()){
					EntityAdvancedVillager.renderLabel(villager, adv.toStringList(), villager.posX, villager.posY, villager.posZ, 5);
				}
			}
		}
	}
}
