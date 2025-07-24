package invalid.myask.undertow.client.item;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class RiptideModel extends ModelBase {

    private final ModelRenderer bone;

    public RiptideModel() {
        textureWidth = 64;
        textureHeight = 64;

        bone = new ModelRenderer(this);
        //bone.setRotationPoint(8.0F, 24.0F, -8.0F);
        bone.setTextureOffset(0, 16).addBox(-8.0F,-16.0F, -8.0F, 16, 32, 16, 0.0F);
    }

    @Override
    public void render(Entity entity, float x, float y, float z, float partialTickTime, float yaw, float scale) {
        bone.render(scale);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
