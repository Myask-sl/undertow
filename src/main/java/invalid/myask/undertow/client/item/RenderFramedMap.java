package invalid.myask.undertow.client.item;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.MapData;

public class RenderFramedMap extends Render {
    public static final RenderFramedMap instance = new RenderFramedMap();

    RenderFramedMap(){
        this.renderManager = RenderManager.instance;
    }

    protected static final ResourceLocation mapBG = new ResourceLocation("textures/map/map_background.png");
    protected static final Minecraft mc = Minecraft.getMinecraft();

    public EntityItemFrame theFrame = null;

    /**
     * Render like vanilla map on frame. Set theFrame before calling.
     * @param theMap
     * @param x
     * @param y
     * @param z
     * @param yaw
     * @param fracTick
     */
    @Override
    public void doRender(Entity theMap, double x, double y, double z, float yaw, float fracTick) {
        this.renderManager.renderEngine.bindTexture(mapBG);

        Tessellator tessellator = Tessellator.instance;
        GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        float framedMapScale = 0.0078125F;
        GL11.glScalef(framedMapScale, framedMapScale, framedMapScale);
        int rot = theFrame == null ? 0 : theFrame.getRotation();
        switch (rot)
        {
            case 0:
                GL11.glTranslatef(-64.0F, -87.0F, -1.5F);
                break;
            case 1:
                GL11.glTranslatef(-66.5F, -84.5F, -1.5F);
                break;
            case 2:
                GL11.glTranslatef(-64.0F, -82.0F, -1.5F);
                break;
            case 3:
                GL11.glTranslatef(-61.5F, -84.5F, -1.5F);
        }

        GL11.glNormal3f(0.0F, 0.0F, -1.0F);
        MapData mapdata = Items.filled_map.getMapData(((EntityItem)theMap).getEntityItem(), theMap.worldObj);
        GL11.glTranslatef(0.0F, 0.0F, -1.0F);

        if (mapdata != null)
        {
            mc.entityRenderer.getMapItemRenderer().func_148250_a(mapdata, true);
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity ity) {
        return null;
    }

    public void doFurtherRender(EntityItem entitiedItem) {

    }
}
