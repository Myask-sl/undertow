package invalid.myask.undertow.client.item;

// Made with Blockbench 4.12.2
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTrident extends ModelBase {
	final ModelRenderer bb_main;

	public ModelTrident() {
		textureWidth = 32;
		textureHeight = 32;

		bb_main = new ModelRenderer(this, "main").setTextureSize(textureWidth, textureHeight);

        bb_main.offsetX = bb_main.offsetY = bb_main.offsetZ = 0;

		bb_main.setRotationPoint(0.0F, 0.0F, 0.0F);
        bb_main.setTextureOffset(0, 5).addBox(-1.0F, -14.0F, 0.0F, 1, 25, 1, 0.0F); //, false));
        bb_main.setTextureOffset(4, 0).addBox(-2.0F, 11.0F, 0.0F, 3, 2, 1, 0.0F); //, false));
        bb_main.setTextureOffset(0, 0).addBox(-3.0F, 12.0F, 0.0F, 1, 4, 1, 0.0F); //, false));
        bb_main.setTextureOffset(0, 0).addBox(1.0F, 12.0F, 0.0F, 1, 4, 1, 0.0F); //, false));
        bb_main.setTextureOffset(8, 3).addBox(-1.0F, 13.0F, 0.0F, 1, 4, 1, 0.0F); //, false));
        bb_main.setTextureOffset(4,9).addBox(-2.0F, 13.0F, 0.0F, 3, 1, 1, 0F); //for spear model
//		bb_main.cubeList.add(new ModelBox(bb_main, 0, 5, -1.0F, -1.0F, 0.0F, 1, 25, 1, 0.0F)); //, false));
//		bb_main.cubeList.add(new ModelBox(bb_main, 4, 0, -2.0F, -3.0F, 0.0F, 3, 2, 1, 0.0F)); //, false));
//		bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -3.0F, -6.0F, 0.0F, 1, 4, 1, 0.0F)); //, false));
//		bb_main.cubeList.add(new ModelBox(bb_main, 8, 3, 1.0F, -6.0F, 0.0F, 1, 4, 1, 0.0F)); //, false));
//		bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -1.0F, -7.0F, 0.0F, 1, 4, 1, 0.0F)); //, false));
	}

	@Override
	public void render(Entity entity, float x, float y, float z, float partialTickTime, float yaw, float scale) {
		bb_main.render(scale);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
