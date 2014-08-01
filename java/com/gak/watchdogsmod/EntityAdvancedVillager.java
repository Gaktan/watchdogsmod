package com.gak.watchdogsmod;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.ibm.icu.util.StringTokenizer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.resources.data.PackMetadataSection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.living.LivingEvent;

public class EntityAdvancedVillager implements IExtendedEntityProperties{

	public final static String EXT_PROP_NAME = "AdvancedVillager";

	public String name, surname, occupation, info;
	private final EntityVillager villager;
	private boolean renderInfos;
	public int threatLevel;
	public int age;
	private static final ResourceLocation target = new ResourceLocation(WatchDogsMod.MODID+ ":textures/gui/target.png");

	public EntityAdvancedVillager(EntityVillager villager) {
		this.villager = villager;

		RandomAdvancedVillager rdv = new RandomAdvancedVillager();

		name = rdv.names.get(new Random().nextInt(rdv.names.size()));
		surname = rdv.getRandom(rdv.surnames);
		occupation = professionToString(villager.getEntityData().getInteger("Profession"));
		info = rdv.getRandom(rdv.infos);
		
		age = new Random().nextInt(30-11) + 11;
		
		threatLevel = new Random().nextInt(100);

		occupation = professionToString(0);
		renderInfos = false;
	}
	
	public static void setNewTarget(EntityVillager villager){
		
		double x = villager.posX;
		double y = villager.posY;
		double z = villager.posZ;
		double radius = 10;
		
		double x1 = new Random().nextInt((int) ((x+radius)-(x-radius)+1)) + (x-radius);
		double y1 = new Random().nextInt((int) ((y+2)-(y)+1)) + y;
		double z1 = new Random().nextInt((int) ((z+radius)-(z-radius)+1)) + (z-radius);
		
		EntityZombie zombie = new EntityZombie(villager.worldObj);
		zombie.setLocationAndAngles(x1, y1, z1, MathHelper.wrapAngleTo180_float(new Random().nextFloat() * 360.0F), 0.0F);
		zombie.rotationYawHead = zombie.rotationYaw;
		zombie.renderYawOffset = zombie.rotationYaw;
		
		villager.worldObj.spawnEntityInWorld(zombie);
		zombie.playLivingSound();
		
		zombie.tasks.addTask(1, new EntityAIAttackOnCollide(villager, EntityVillager.class, 0, false));
		zombie.targetTasks.addTask(1, new EntityAINearestAttackableTarget(villager, EntityVillager.class, 0, false));
	}

	public static final void register(EntityVillager villager){
		villager.registerExtendedProperties(EntityAdvancedVillager.EXT_PROP_NAME, new EntityAdvancedVillager(villager));
	}

	public static final EntityAdvancedVillager get(EntityVillager villager){
		return (EntityAdvancedVillager) villager.getExtendedProperties(EXT_PROP_NAME);
	}

	@Override
	public void init(Entity entity, World world) {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {

		NBTTagCompound properties = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);

		name = properties.getString("Name");
		surname = properties.getString("Surname");
		occupation = professionToString(compound.getInteger("Profession"));
		info = properties.getString("Info");
		
		if(compound.getInteger("Age") < 0)
			age = new Random().nextInt(10);
		else
			age = new Random().nextInt(30-11) + 11;
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = new NBTTagCompound();

		properties.setString("Name", name);
		properties.setString("Surname", surname);
		properties.setString("Info", info);

		compound.setTag(EXT_PROP_NAME, properties);
	}

	@SideOnly(Side.CLIENT)
	public static void renderLabel(EntityLiving par1EntityLiving, String []par2Str, double par3, double par5, double par7, int par9){

		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;

		double d3 = par1EntityLiving.getDistanceSqToEntity(player);

		if (d3 <= (double)(par9 * par9))
		{

			Tessellator tessellator = Tessellator.instance;

			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDepthMask(true);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			//GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

			double diffX = (par1EntityLiving.prevPosX + (par1EntityLiving.posX - par1EntityLiving.prevPosX)) - (player.prevPosX + (player.posX - player.prevPosX)); 
			double diffY = (par1EntityLiving.prevPosY + (par1EntityLiving.posY - par1EntityLiving.prevPosY)) - (player.prevPosY + (player.posY - player.prevPosY)); 
			double diffZ = (par1EntityLiving.prevPosZ + (par1EntityLiving.posZ - par1EntityLiving.prevPosZ)) - (player.prevPosZ + (player.posZ - player.prevPosZ));

			double x = Math.cos(Math.toRadians(par1EntityLiving.rotationYawHead));
			double z = Math.sin(Math.PI - Math.toRadians(par1EntityLiving.rotationYawHead));

			x = Math.toDegrees(x) / 1000;
			z = Math.toDegrees(z) / 1000;

			diffX += x;
			diffZ += z;
			
			if(par1EntityLiving.isChild()){
				diffY -= 1;
				GL11.glScaled(0.5, 0.5, 0.5);
			}

			Minecraft.getMinecraft().renderEngine.bindTexture(target);

			GL11.glPushMatrix();
			GL11.glTranslated(diffX, diffY + 1.75, diffZ);
			GL11.glRotatef(90, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(player.rotationYaw, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(player.rotationPitch, 1, 0, 0);

			GL11.glScaled(0.75, 0.75, 0.75);

			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(-1, 1, 1, 0, 0);//your +1 everywhere in y is redundant btw it should be included in a glTranslated
			tessellator.addVertexWithUV(-1, 1, -1, 0, 1);
			tessellator.addVertexWithUV(1, 1, -1, 1, 1);
			tessellator.addVertexWithUV(1, 1, 1, 1, 0);

			tessellator.addVertexWithUV(-1, 1, 1, 0, 0);
			tessellator.addVertexWithUV(1, 1, 1, 1, 0);
			tessellator.addVertexWithUV(1, 1, -1, 1, 1);
			tessellator.addVertexWithUV(-1, 1, -1, 0, 1);
			tessellator.draw();

			GL11.glDisable(GL11.GL_TEXTURE_2D);

			GL11.glRotatef(player.rotationPitch, -1, 0, 0);
			GL11.glRotatef(player.rotationYaw, 0.0F, 0.0F, -1.0F);
			GL11.glRotatef(90, -1.0F, 0.0F, 0.0F);

			x *= 25;
			z *= 25;

			GL11.glTranslated(x, 0, z);
			GL11.glNormal3f(0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-par1EntityLiving.rotationYawHead + 175, 0.0F, 1.0F, 0.0F);
			GL11.glRotated(-180, 0, 0, 1);
			GL11.glScaled(0.014, 0.014, 0.014);
			GL11.glNormal3f(0.0F, 1.0F, 0.0F);

			FontRenderer fontrenderer = RenderManager.instance.getFontRenderer();

			int value = -1;

			for(int i = 0; i < par2Str.length-1; i++){
				int j = fontrenderer.getStringWidth(par2Str[i]) / 2;

				tessellator.startDrawingQuads();
				if(i < 2)
					tessellator.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F);
				else
					tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
				tessellator.addVertex((double)(-j - 1), (double)(value), 0.0D);
				tessellator.addVertex((double)(-j - 1), (double)(value+9), 0.0D);
				tessellator.addVertex((double)(j + 1), (double)(value+9), 0.0D);
				tessellator.addVertex((double)(j + 1), (double)(value), 0.0D);

				tessellator.draw();

				value += 9;
			}

			GL11.glEnable(GL11.GL_TEXTURE_2D);

			fontrenderer.drawString(par2Str[0], -fontrenderer.getStringWidth(par2Str[0]) / 2, 0, 0);
			GL11.glTranslated(0, 9, 0);
			fontrenderer.drawString(par2Str[1], -fontrenderer.getStringWidth(par2Str[1]) / 2, 0, 0);
			GL11.glTranslated(0, 9, 0);
			for(int i = 2; i < par2Str.length-1; i++){
				fontrenderer.drawString(par2Str[i], -fontrenderer.getStringWidth(par2Str[i]) / 2, 0, 553648127);
				GL11.glTranslated(0, 9, 0);
			}
			
			if(Integer.parseInt(par2Str[par2Str.length-1]) > 85){
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				
				GL11.glTranslated(0, -9*9, 0);
				
				String str = "Threat level : " + par2Str[par2Str.length-1] + "%";
				int j = fontrenderer.getStringWidth(str) / 2;
				
				tessellator.startDrawingQuads();

				tessellator.setColorRGBA(246, 255, 22, 255);
				tessellator.addVertex((double)(-j - 1), (double)(value), 0.0D);
				tessellator.addVertex((double)(-j - 1), (double)(value+9), 0.0D);
				tessellator.addVertex((double)(j + 1), (double)(value+9), 0.0D);
				tessellator.addVertex((double)(j + 1), (double)(value), 0.0D);
				tessellator.draw();
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				
				GL11.glTranslated(0, 9*4, 0);
				
				fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, 0, 0);		
			}
			
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_BLEND);
			//GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

			GL11.glPopMatrix();
		}
		else
			get((EntityVillager) par1EntityLiving).renderInfos = false;
	}


	public String professionToString(int profession){
		switch(profession){
		case 0:
			return "Farmer";
		case 1:
			return "Librarian";
		case 2:
			return "Priest";
		case 3:
			return "Blacksmith";
		case 4:
			return "Butcher";
		case 5:
			return "Generic";
		}
		return "";
	}

	public boolean isRenderingInfos() {
		return renderInfos;
	}

	public void setRenderInfos(boolean renderInfos) {
		this.renderInfos = renderInfos;
	}
	

	@Override
	public String toString() {		
		return villager.getEntityId() + "\n" + name + "\n" + surname + "\n" + info + "\n" + age + "\n" + occupation + "\n" + threatLevel;
	}

	public String[] toStringList() {
		String[] str = {name.toUpperCase() + " " + surname.toUpperCase(), info.toUpperCase(), 
				"age / " + age, "occupation / " + occupation, ""+threatLevel};
		return str;
	}


}
