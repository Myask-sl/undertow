package invalid.myask.undertow.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;

public class RenderDumbMap extends Render implements IItemRenderer {
    public static RenderDumbMap instance = new RenderDumbMap();

    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float fracTick) {

    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return null;
    }


    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type == ItemRenderType.FIRST_PERSON_MAP;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        Minecraft mc = Minecraft.getMinecraft();
        World w;
        if (data.length > 0 && data[0] instanceof EntityPlayer p)
            w = p.getEntityWorld();
        else w = mc.theWorld;
        mc.entityRenderer.getMapItemRenderer().func_148250_a(Items.filled_map.getMapData(item, mc.theWorld), true);
        //it is so easy as to pretend it's on an itemframe (true).
    }
}
