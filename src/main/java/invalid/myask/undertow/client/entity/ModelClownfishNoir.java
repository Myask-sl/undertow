package invalid.myask.undertow.client.entity;
// Made with Blockbench 4.12.5
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelClownfishNoir extends ModelBase {
    private final ModelRenderer body;
    private final ModelRenderer head;
    private final ModelRenderer headoverlay;
    private final ModelRenderer cigar;
    private final ModelRenderer hat;
    private final ModelRenderer abdomen;
    private final ModelRenderer caudal_fin;
    private final ModelRenderer pectoralR;
    private final ModelRenderer sleeveR;
    private final ModelRenderer pectoraL;
    private final ModelRenderer sleeveL;
    private final ModelRenderer bodyoverlay;

    public ModelClownfishNoir() {
        textureWidth = 32;
        textureHeight = 32;

        body = new ModelRenderer(this);
        body.setRotationPoint(1.5F, 0.0F, -2.0F);
        body.cubeList.add(new ModelBox(body, 0, 8, -3.0F, -5.0F, -2.0F, 3, 6, 6, 0.0F));
        body.cubeList.add(new ModelBox(body, 7, 2, -2.0F, -6.0F, -1.5F, 1, 1, 5, 0.0F));

        head = new ModelRenderer(this);
        head.setRotationPoint(-1.5F, 0.5F, -1.0F);
        body.addChild(head);
        head.cubeList.add(new ModelBox(head, 0, 0, -1.0F, -4.5F, -4.0F, 2, 4, 3, 0.0F));

        headoverlay = new ModelRenderer(this);
        headoverlay.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.addChild(headoverlay);
        headoverlay.cubeList.add(new ModelBox(headoverlay, 22, 16, -1.0F, -4.5F, -4.0F, 2, 4, 3, 0.25F));

        cigar = new ModelRenderer(this);
        cigar.setRotationPoint(0.0F, -0.5F, -4.0F);
        head.addChild(cigar);
        setRotationAngle(cigar, 0.2182F, 0.0F, 0.0F);
        cigar.cubeList.add(new ModelBox(cigar, 0, 20, 0.0F, -1.0F, -3.0F, 1, 1, 3, 0.0F));

        hat = new ModelRenderer(this);
        hat.setRotationPoint(-0.5F, -4.5F, -2.0F);
        head.addChild(hat);
        setRotationAngle(hat, 0.3491F, 0.0F, 0.0F);
        hat.cubeList.add(new ModelBox(hat, 14, 16, -1.0F, -1.0F, -3.0F, 3, 1, 4, 0.0F));
        hat.cubeList.add(new ModelBox(hat, 16, 18, 0.0F, -2.0F, -2.0F, 1, 1, 2, 0.0F));

        abdomen = new ModelRenderer(this);
        abdomen.setRotationPoint(-2.0F, -4.0F, -3.0F);
        body.addChild(abdomen);
        abdomen.cubeList.add(new ModelBox(abdomen, 0, 24, -0.5F, -0.5F, 7.0F, 2, 5, 3, 0.0F));

        caudal_fin = new ModelRenderer(this);
        caudal_fin.setRotationPoint(0.5F, 4.5F, 2.0F);
        abdomen.addChild(caudal_fin);
        caudal_fin.cubeList.add(new ModelBox(caudal_fin, 0, 8, -0.5F, -5.0F, 10.0F, 1, 1, 2, 0.0F));
        caudal_fin.cubeList.add(new ModelBox(caudal_fin, 0, 8, -0.5F, -4.0F, 9.0F, 1, 1, 2, 0.0F));
        caudal_fin.cubeList.add(new ModelBox(caudal_fin, 0, 8, -0.5F, -3.0F, 10.0F, 1, 1, 2, 0.0F));
        caudal_fin.cubeList.add(new ModelBox(caudal_fin, 0, 11, -0.5F, -3.0F, 8.0F, 1, 1, 2, 0.0F));
        caudal_fin.cubeList.add(new ModelBox(caudal_fin, 0, 8, -0.5F, -2.0F, 9.0F, 1, 1, 2, 0.0F));
        caudal_fin.cubeList.add(new ModelBox(caudal_fin, 0, 8, -0.5F, -1.0F, 10.0F, 1, 1, 2, 0.0F));

        pectoralR = new ModelRenderer(this);
        pectoralR.setRotationPoint(0.0F, 0.0F, 0.0F);
        body.addChild(pectoralR);
        setRotationAngle(pectoralR, 0.0F, 0.0F, 0.5236F);
        pectoralR.cubeList.add(new ModelBox(pectoralR, 10, 29, 0.0F, -1.0F, -1.0F, 3, 1, 2, 0.0F));

        sleeveR = new ModelRenderer(this);
        sleeveR.setRotationPoint(0.0F, 0.0F, 0.0F);
        pectoralR.addChild(sleeveR);
        sleeveR.cubeList.add(new ModelBox(sleeveR, 10, 26, 0.0F, -1.0F, -1.0F, 3, 1, 2, 0.1F));

        pectoraL = new ModelRenderer(this);
        pectoraL.setRotationPoint(-3.0F, 0.0F, 0.0F);
        body.addChild(pectoraL);
        setRotationAngle(pectoraL, 0.0F, 0.0F, -0.5236F);
        pectoraL.cubeList.add(new ModelBox(pectoraL, 10, 29, -3.0F, -1.0F, -1.0F, 3, 1, 2, 0.0F));

        sleeveL = new ModelRenderer(this);
        sleeveL.setRotationPoint(0.0F, 0.0F, 0.0F);
        pectoraL.addChild(sleeveL);
        sleeveL.cubeList.add(new ModelBox(sleeveL, 10, 23, -3.0F, -1.0F, -1.0F, 3, 1, 2, 0.1F));

        bodyoverlay = new ModelRenderer(this);
        bodyoverlay.setRotationPoint(0.0F, 0.0F, 0.0F);
        body.addChild(bodyoverlay);
        bodyoverlay.cubeList.add(new ModelBox(bodyoverlay, 12, 0, -3.0F, -5.0F, -2.0F, 3, 6, 8, 0.25F));
    }

    public ModelClownfishNoir(boolean noir) {
        this();
        setAllNoirVisible(noir);
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

    public void setSuitVisible(boolean vis) {
        bodyoverlay.showModel = vis;
        sleeveL.showModel = vis;
        sleeveR.showModel = vis;
    }
    public void setShadesVisible(boolean vis) {
        headoverlay.showModel = vis;
    }
    public void setHatVisible(boolean vis) {
        hat.showModel = vis;
    }
    public void setCigarVisible(boolean vis) {
        cigar.showModel = vis;
    }

    public void setAllNoirVisible(boolean vis) {
        setSuitVisible(vis);
        setHatVisible(vis);
        setShadesVisible(vis);
        setCigarVisible(vis);
    }
}
