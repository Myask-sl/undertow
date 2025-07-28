package invalid.myask.undertow.client.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import invalid.myask.undertow.Undertow;
import invalid.myask.undertow.entities.ProjectileRiptide;

public class RenderRiptide extends Render {
    public static final RenderRiptide instance = new RenderRiptide();
    public static final ResourceLocation SWIRL_TEX = new ResourceLocation(Undertow.MODID, "textures/entity/riptide.png");
    public static final ModelRiptide whoosh = new ModelRiptide();

    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float fracTick) {
        doRender((ProjectileRiptide) entity, x, y, z, yaw, fracTick);
    }

    protected void doRender(ProjectileRiptide swirl, double x, double y, double z, float yaw, float fracTick) {
/*        this.bindEntityTexture(swirl);
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        //TODO: fixed...?
        GL11.glRotatef(swirl.ticksExisted * 24 + fracTick, 0,0,0);
        GL11.glRotatef(swirl.prevRotationPitch + (swirl.rotationPitch - swirl.prevRotationPitch) * fracTick, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(swirl.prevRotationYaw + (swirl.rotationYaw - swirl.prevRotationYaw) * fracTick, 0.0F, 1.0F, 0.0F);
//        whoosh.render(swirl, 0, 0, 0, fracTick, yaw, 0.0625F);

        GL11.glPopMatrix();*/
    }

    @Override
    protected void bindTexture(ResourceLocation texRL) {
        if (renderManager == null)
            setRenderManager(RenderManager.instance);
        if (renderManager == null || renderManager.renderEngine == null) {
            if (Minecraft.getMinecraft() != null && Minecraft.getMinecraft().getTextureManager() != null)
                Minecraft.getMinecraft().getTextureManager().bindTexture(texRL);
        }
        else
            super.bindTexture(texRL);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity swirl) {
        return getEntityTexture((ProjectileRiptide) swirl);
    }
    protected ResourceLocation getEntityTexture(ProjectileRiptide swirl) {
        return SWIRL_TEX;
    }
}
