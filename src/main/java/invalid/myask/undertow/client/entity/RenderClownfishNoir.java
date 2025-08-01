package invalid.myask.undertow.client.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

import invalid.myask.undertow.Undertow;

public class RenderClownfishNoir extends RenderLiving {
    protected static ResourceLocation NOIRFISH = new ResourceLocation(Undertow.MODID, "textures/entity/fish/clownfish_noir.png");

    public RenderClownfishNoir(ModelBase mainModel, float shadowSize) {
        super(mainModel, shadowSize);
    }

    @Override
    public void doRender(EntityLiving fish, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
        super.doRender(fish, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity fish) {
        return NOIRFISH;
    }
}
