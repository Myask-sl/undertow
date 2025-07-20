package invalid.myask.undertow.client;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;

import invalid.myask.undertow.ducks.IUndertowPosableEntity;
import invalid.myask.undertow.mixins.MixinMinecraft_getTimer;

import static invalid.myask.undertow.ducks.IUndertowPosableEntity.FULL_TILT;

public class PoseHelper {

    private static float SCALE = 1.5F;

    public static void poseRotate(EntityLivingBase entity, boolean renderRiptide) {
        IUndertowPosableEntity iUPO = ((IUndertowPosableEntity) entity);

        int tiltick = iUPO.undertow$getTilt(), flyTick = iUPO.undertow$getFlyingTicks();
        float fracTick = ((MixinMinecraft_getTimer) Minecraft.getMinecraft()).getTimer().renderPartialTicks,
            theRatio = Float.min(tiltick + fracTick, FULL_TILT) / FULL_TILT;
        if (iUPO.undertow$smallPosed() == 1) {
            GL11.glTranslated(0, -iUPO.undertow$getCurrentYDifference(), 0);
        }

        if (iUPO.undertow$isCrawling() || iUPO.undertow$isSwimming() || iUPO.undertow$rippingTide()
            || iUPO.undertow$getTilt() > 0) {
            float adj_angle = iUPO.undertow$isCrawling() ? 0 : entity.rotationPitch;//unlike model elements needs no  * 180 / (float) Math.PI;
            adj_angle += 90;
            adj_angle *= theRatio;
            GL11.glTranslated(0, -iUPO.undertow$getCurrentYDifference() * theRatio, 0);

            GL11.glRotatef(adj_angle, 1, 0, 0); //this.bipedBody.rotateAngleX += adj_angle;
            if (iUPO.undertow$rippingTide()) {
                GL11.glRotatef(flyTick * 24 + fracTick, 0, 1, 0);
                if (renderRiptide) {
                    Minecraft.getMinecraft().getTextureManager().bindTexture(RenderRiptide.SWIRL_TEX);
                    RenderRiptide.whoosh.render(null, 0, 0, 0, fracTick, 0, 0.0625F * SCALE);
                }
            }
        }
    }
}
