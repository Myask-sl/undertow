package invalid.myask.undertow.client.entity;
// Made with Blockbench 4.12.5
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelClownfishNoir extends ModelBase {
    private final ModelRenderer head;
    private final ModelRenderer body;
    private final ModelRenderer pectoralR;
    private final ModelRenderer pectoraL;
    private final ModelRenderer abdomen;
    private final ModelRenderer caudal_fin;

    public ModelClownfishNoir() {
        textureWidth = 32;
        textureHeight = 32;

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, 24.5F, -3.0F);
        head.setTextureOffset(0, 0).addBox(-1.0F, -4.5F, -4.0F, 2, 4, 3, 0.0F);
        head.setTextureOffset( 22, 16).addBox( -1.0F, -4.5F, -4.0F, 2, 4, 3, 0.25F);//shades

        ModelRenderer cigar_r1 = new ModelRenderer(this);
        cigar_r1.setRotationPoint(0.0F, 0.0F, -6.0F);
        setRotationAngle(cigar_r1, 0.2618F, 0.0F, 0.0F);
        cigar_r1.setTextureOffset(0, 20).addBox( 0.0F, -1.0F, -1.0F, 1, 1, 3, 0.0F);//cigar

        ModelRenderer hat_r1 = new ModelRenderer(this);
        hat_r1.setRotationPoint(-0.5F, -4.5F, -2.0F);
        setRotationAngle(hat_r1, 0.3491F, 0.0F, 0.0F);
        hat_r1.setTextureOffset(16, 18).addBox( 0.0F, -2.0F, -2.0F, 1, 1, 2, 0.0F);
        hat_r1.setTextureOffset(14, 16).addBox( -1.0F, -1.0F, -3.0F, 3, 1, 4, 0.0F);

        body = new ModelRenderer(this);
        body.setRotationPoint(1.5F, 24.0F, -2.0F);
        body.setTextureOffset(0, 8).addBox( -3.0F, -5.0F, -2.0F, 3, 6, 6, 0.0F);
        body.setTextureOffset(12, 0).addBox( -3.0F, -5.0F, -2.0F, 3, 6, 8, 0.25F);
        body.setTextureOffset(7, 2).addBox( -2.0F, -6.0F, -1.5F, 1, 1, 5, 0.0F);

        caudal_fin = new ModelRenderer(this);
        caudal_fin.setRotationPoint(0.0F, 24.5F, -3.0F);
        caudal_fin.setTextureOffset(0, 8).addBox( -0.5F, -5.0F, 10.0F, 1, 1, 2, 0.0F);
        caudal_fin.setTextureOffset(0, 8).addBox( -0.5F, -4.0F, 9.0F, 1, 1, 2, 0.0F);
        caudal_fin.setTextureOffset(0, 8).addBox( -0.5F, -3.0F, 10.0F, 1, 1, 2, 0.0F);
        caudal_fin.setTextureOffset(0, 11).addBox(-0.5F, -3.0F, 8.0F, 1, 1, 2, 0.0F);
        caudal_fin.setTextureOffset(0, 8).addBox( -0.5F, -2.0F, 9.0F, 1, 1, 2, 0.0F);
        caudal_fin.setTextureOffset(0, 8).addBox( -0.5F, -1.0F, 10.0F, 1, 1, 2, 0.0F);

        pectoralR = new ModelRenderer(this);
        pectoralR.setRotationPoint(-1.5F, 24.0F, -2.0F);
        setRotationAngle(pectoralR, 0.0F, 0.0F, 0.5236F);
        pectoralR.setTextureOffset(10, 29).addBox( 0.0F, -1.0F, -1.0F, 3, 1, 2, 0.0F);

        ModelRenderer sleeveR_r1 = new ModelRenderer(this);
        sleeveR_r1.setRotationPoint(3.0F, 0.0F, 0.0F);
        sleeveR_r1.setTextureOffset(10, 26).addBox( 0.0F, -1.0F, -1.0F, 3, 1, 2, 0.1F);

        pectoraL = new ModelRenderer(this);
        pectoraL.setRotationPoint(0.0F, 24.0F, 0.0F);
        pectoraL.setTextureOffset(10, 29).addBox( -3.0F, -1.0F, -1.0F, 3, 1, 2, 0.0F);

        ModelRenderer sleeveL_r1 = new ModelRenderer(this);
        sleeveL_r1.setRotationPoint(-1.5F, 0.0F, -2.0F);
        setRotationAngle(pectoraL, 0.0F, 0.0F, -0.5236F);
        sleeveL_r1.setTextureOffset(10, 23).addBox(  -3.0F, -1.0F, -1.0F, 3, 1, 2, 0.1F);

        abdomen = new ModelRenderer(this);
        abdomen.setRotationPoint(0.0F, 24.0F, 0.0F);
        abdomen.setTextureOffset(0, 24).addBox( -1.0F, -4.5F, 2.0F, 2, 5, 3, 0.0F);

        body.addChild(head);
            head.addChild(cigar_r1);
            head.addChild(hat_r1);
        body.addChild(pectoraL);
            pectoraL.addChild(sleeveL_r1);
        body.addChild(pectoralR);
            pectoralR.addChild(sleeveR_r1);
        body.addChild(abdomen);
            abdomen.addChild(caudal_fin);
    }

    @Override
    public void render(Entity entity, float armSwingT, float swingMax, float fracTick, float yaw, float pitch, float scale) {
        setRotationAngles(armSwingT, swingMax, fracTick, yaw, pitch, scale, entity);
        body.render(scale);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(float armSwingT, float swingMax, float somenumber, float yaw, float pitch, float scaleQ, Entity entity) {
        super.setRotationAngles(armSwingT, swingMax, somenumber, yaw, pitch, scaleQ, entity);
        body.rotateAngleX = (float) (pitch / (180F / Math.PI * 0.9));
        body.rotateAngleY = yaw / (180F / (float)Math.PI);
        head.rotateAngleX = (float) (pitch / (1800F / Math.PI));

        float bodySwingTotal = MathHelper.cos(armSwingT * 0.6666F) * swingMax / 4;
        head.rotateAngleY = bodySwingTotal;
        abdomen.rotateAngleY = -bodySwingTotal;
        caudal_fin.rotateAngleY = -2 * bodySwingTotal;
    }
}
