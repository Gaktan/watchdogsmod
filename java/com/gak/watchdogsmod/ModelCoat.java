package com.gak.watchdogsmod;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;


public class ModelCoat extends ModelBiped{
	ModelRenderer leftArm;
	ModelRenderer rightArm;
	ModelRenderer body1;
	ModelRenderer body2;
	ModelRenderer body3;
	ModelRenderer body4;

	public ModelCoat(float f){
		super(f, 0, 64, 64);
		textureWidth = 64;
		textureHeight = 64;

		leftArm = new ModelRenderer(this, 0, 32);
		leftArm.addBox(0F, 0F, 0F, 5, 10, 5);
		leftArm.setRotationPoint(-1.5F, -2.5F, -2.5F);
		leftArm.setTextureSize(64, 64);
		leftArm.mirror = true;
		setRotation(leftArm, 0F, 0F, 0F);

		rightArm = new ModelRenderer(this, 0, 32);
		rightArm.addBox(0F, 0F, 0F, 5, 10, 5);
		rightArm.setRotationPoint(-3.5F, -2.5F, -2.5F);
		rightArm.setTextureSize(64, 64);
		rightArm.mirror = false;
		setRotation(rightArm, 0F, 0F, 0F);

		//Body 1
		body1 = new ModelRenderer(this, 20, 32);
		body1.addBox(0F, 0F, 0F, 4, 16, 5);
		body1.setRotationPoint(-4.5F, -0.5F, -2.5F);
		body1.setTextureSize(64, 64);
		setRotation(body1, 0F, 0F, 0F);

		body2 = new ModelRenderer(this, 16, 47);
		body2.addBox(0F, 0F, 0F, 1, 2, 1);
		body2.setRotationPoint(-0.5F, 2.5F, -2.5F);
		body2.setTextureSize(64, 64);
		setRotation(body2, 0F, 0F, 0F);

		body3 = new ModelRenderer(this, 38, 32);
		body3.addBox(0F, 0F, 0F, 1, 10, 1);
		body3.setRotationPoint(-0.5F, -0.5F, 1.5F);
		body3.setTextureSize(64, 64);
		setRotation(body3, 0F, 0F, 0F);

		//Body 2
		body4 = new ModelRenderer(this, 20, 32);
		body4.mirror = true;
		body4.addBox(0F, 0F, 0F, 4, 16, 5);
		body4.setRotationPoint(0.5F, -0.5F, -2.5F);
		body4.setTextureSize(64, 64);
		setRotation(body4, 0F, 0F, 0F);

		bipedBody.addChild(body1);
		bipedBody.addChild(body2);
		bipedBody.addChild(body3);
		bipedBody.addChild(body4);

		bipedLeftArm.addChild(leftArm);
		bipedRightArm.addChild(rightArm);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5){
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z){
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

}
