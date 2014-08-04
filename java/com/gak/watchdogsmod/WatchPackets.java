package com.gak.watchdogsmod;

import io.netty.buffer.ByteBuf;

import java.util.StringTokenizer;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayerMP;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class WatchPackets implements IMessage {

	private String text;

	public static int SERVER_TO_CLIENT = 0;
	public static int CLIENT_TO_SERVER = 1;

	public WatchPackets() { }

	public WatchPackets(String text) {
		this.text = text;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		text = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, text);
	}



	public static class ServerHandler implements IMessageHandler<WatchPackets, IMessage> {

		@Override
		public IMessage onMessage(WatchPackets message, MessageContext ctx) {

			EntityPlayerMP player = ctx.getServerHandler().playerEntity;
			System.out.println(String.format("Received %s from %s", message.text, ctx.getServerHandler().playerEntity.getDisplayName()));


			StringTokenizer st = new StringTokenizer(message.text, " ");
			st.nextToken();
			int id = Integer.parseInt(st.nextToken());

			EntityVillager villager = (EntityVillager) player.worldObj.getEntityByID(id);

			EntityAdvancedVillager eav = EntityAdvancedVillager.get(villager);

			if(eav != null){
				WatchDogsMod.network.sendTo(new WatchPackets(id + "\n" + eav.toString()), player);
			}
			return null;
		}
	}

	public static class ClientHandler implements IMessageHandler<WatchPackets, IMessage> {

		@Override
		public IMessage onMessage(WatchPackets message, MessageContext ctx) {

			StringTokenizer st = new StringTokenizer(message.text, "\n");
			int id = Integer.parseInt(st.nextToken());
			
			EntityVillager villager = (EntityVillager) Minecraft.getMinecraft().theWorld.getEntityByID(id);
			EntityAdvancedVillager eav = EntityAdvancedVillager.get(villager);
			
			eav.name = st.nextToken();
			eav.surname = st.nextToken();

			eav.info = st.nextToken();
			eav.age = Integer.parseInt(st.nextToken());
			eav.occupation = st.nextToken();
			
			eav.threatLevel = Integer.parseInt(st.nextToken());
			return null;
		}
	}
}
