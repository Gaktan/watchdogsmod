package com.gak.watchdogsmod;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelHat extends ModelBiped
{
	//fields
	ModelRenderer Shape1;
	ModelRenderer Shape5;
	ModelRenderer Shape3;
	ModelRenderer Shape2;
	ModelRenderer Shape4;

	public ModelHat(float f)
	{
		super(f, 0, 64, 64);
		textureWidth = 64;
		textureHeight = 64;

		Shape1 = new ModelRenderer(this, 0, 32);
		Shape1.addBox(0F, 0F, 0F, 10, 2, 10);
		Shape1.setRotationPoint(-5F, -9F, -5F);
		Shape1.setTextureSize(64, 64);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);
		
		Shape5 = new ModelRenderer(this, 0, 58);
		Shape5.addBox(0F, 0F, 0F, 8, 1, 1);
		Shape5.setRotationPoint(-4F, -8F, -6F);
		Shape5.setTextureSize(64, 64);
		Shape5.mirror = true;
		setRotation(Shape5, 0F, 0F, 0F);
		
		Shape3 = new ModelRenderer(this, 40, 36);
		Shape3.addBox(0F, 0F, 0F, 1, 1, 2);
		Shape3.setRotationPoint(-4F, -7F, -6F);
		Shape3.setTextureSize(64, 64);
		Shape3.mirror = true;
		setRotation(Shape3, 0F, 0F, 0F);
		
		Shape2 = new ModelRenderer(this, 40, 32);
		Shape2.addBox(0F, 0F, 0F, 1, 1, 2);
		Shape2.setRotationPoint(3F, -7F, -6F);
		Shape2.setTextureSize(64, 64);
		Shape2.mirror = true;
		setRotation(Shape2, 0F, 0F, 0F);
		
		Shape4 = new ModelRenderer(this, 0, 44);
		Shape4.addBox(0F, 0F, 0F, 9, 3, 9);
		Shape4.setRotationPoint(-4.5F, -2F, -4.5F);
		Shape4.setTextureSize(64, 64);
		Shape4.mirror = true;
		setRotation(Shape4, 0F, 0F, 0F);
		
		bipedHead.addChild(Shape1);
		bipedHead.addChild(Shape2);
		bipedHead.addChild(Shape3);
		bipedHead.addChild(Shape4);
		bipedHead.addChild(Shape5);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}
	
	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}

