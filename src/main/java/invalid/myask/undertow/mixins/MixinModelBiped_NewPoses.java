package invalid.myask.undertow.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import invalid.myask.undertow.client.PoseHelper;
import invalid.myask.undertow.ducks.IUndertowPosableEntity;
import invalid.myask.undertow.item.ItemShield;
import invalid.myask.undertow.item.ItemTrident;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelBiped.class)
public class MixinModelBiped_NewPoses extends ModelBase {
/*
 * @Inject(method = "doRender(Lnet/minecraft/entity/EntityLiving;DDDFF)V",
 *       at = @At("TAIL"))
 *   private void resetLeftHand ()
 *
 */
    @Shadow
    public int heldItemRight;
    @Shadow
    public ModelRenderer bipedRightArm;
    @Shadow
    public int heldItemLeft;
    @Shadow
    public ModelRenderer bipedLeftArm;
    @Shadow
    public ModelRenderer bipedBody;
    @Shadow
    public ModelRenderer bipedHead;
    @Shadow
    public ModelRenderer bipedHeadwear;
    @Shadow
    public ModelRenderer bipedLeftLeg;
    @Shadow
    public ModelRenderer bipedRightLeg;
    @Shadow
    public boolean isSneak;


    private static final float SWIM_SPEED_CONST = 0.1F;
    private static final float TWIST_WHEN = 0.9F;

    @Inject(method = "setRotationAngles(FFFFFFLnet/minecraft/entity/Entity;)V", at = @At("HEAD"))
    private void crawlingIsNotSneaking(CallbackInfo ci, @Local(argsOnly = true) Entity entity) {
        if (entity instanceof EntityLivingBase elbe) {
            IUndertowPosableEntity iUPO = (IUndertowPosableEntity) elbe;
            if (iUPO.undertow$smallPosed() == 2) isSneak = false;
        }
//        if (((IUndertowPosableEntity)entity).undertow$smallPosed() == 2)
 //           onGround = 0;
    }

 /*   @ModifyConstant(method = "setRotationAngles(FFFFFFLnet/minecraft/entity/Entity;)V",
    constant = @Constant(floatValue = -9990.0F))
    private float crawlNoBackturn(float in, @Local(argsOnly = true) Entity entity) {
        return ((IUndertowPosableEntity)entity).undertow$smallPosed() == 2 ? 9990 : in;
    }*/

    @Inject(method = "setRotationAngles(FFFFFFLnet/minecraft/entity/Entity;)V",
        at = @At("TAIL"))
    private void undertow_rotateForNewPoses(CallbackInfo ci, @Local(argsOnly = true) Entity entity,
                   @Local(ordinal = 0, argsOnly = true) float t, @Local(ordinal = 3, argsOnly = true) float fracTick) {
        this.bipedRightArm.rotationPointX = -5;
        this.bipedLeftArm.rotationPointX = 5;
        if (entity instanceof EntityPlayer zeppo) {
            IUndertowPosableEntity iUPO = ((IUndertowPosableEntity) zeppo);
            if (iUPO.undertow$clearHandRendering()) return; //rendering a hand!
//            iUPO.undertow$setCrawling(zeppo.isSneaking());
            if (zeppo.getItemInUse() != null) { //fn is fine, this is clientside
                if (iUPO.undertow$isSpearPosing()) {
                    if (!Backhand.passthrough.isUsingOffhand(zeppo)) //right/main hand
                        bipedRightArm.rotateAngleX += (float) Math.PI;
                    else //left/offhand
                        bipedLeftArm.rotateAngleX += (float) Math.PI;
                }
                else if (zeppo.getItemInUse().getItem() instanceof ItemShield) {
                    if (zeppo.getItemInUse() == zeppo.getCurrentEquippedItem())
                        bipedRightArm.rotateAngleZ -= .5F;
                    else bipedLeftArm.rotateAngleZ += .5F;
                }
            }
            else if (!zeppo.isUsingItem() && !iUPO.undertow$rippingTide()
                && (iUPO.undertow$isCrawling() || iUPO.undertow$isSwimming()) ) {
                float cosine = MathHelper.cos(t * SWIM_SPEED_CONST), sine = MathHelper.sin(t * SWIM_SPEED_CONST);
                if (sine < 0) {
                    cosine *= -1;
                    sine *= -1;
                }
                this.bipedRightArm.rotateAngleX = this.bipedLeftArm.rotateAngleX = cosine > 0 ? -sine * (float) Math.PI : 0;
                this.bipedRightArm.rotateAngleY = cosine > 0F && sine > TWIST_WHEN ?
                    (float) ((sine - TWIST_WHEN) * (1 / (1 - TWIST_WHEN)) * Math.PI) : 0;
                this.bipedLeftArm.rotateAngleY = -this.bipedRightArm.rotateAngleY;

                this.bipedRightArm.rotateAngleZ = cosine > 0 ? 0 : sine * (float) Math.PI;
                this.bipedLeftArm.rotateAngleZ = -this.bipedRightArm.rotateAngleZ;
                float out_arms = 5;
                if (sine > TWIST_WHEN || cosine < 0) {
                    if (cosine > 0) out_arms += (sine - TWIST_WHEN) * 2 * (1 / (1 - TWIST_WHEN));
                    else out_arms += 2 * (1 + cosine);
                }
                this.bipedRightArm.rotationPointX = -out_arms;
                this.bipedLeftArm.rotationPointX = out_arms;
            }
            if (iUPO.undertow$rippingTide()) {
                if (zeppo.getCurrentEquippedItem() != null && zeppo.getCurrentEquippedItem().getItem() instanceof ItemTrident)
                    bipedRightArm.rotateAngleX = (float) Math.PI;
                else bipedLeftArm.rotateAngleX = (float) Math.PI;
            }

            PoseHelper.poseRotate((EntityLivingBase) entity, false);
/*
//old ver
            int tiltick = iUPO.undertow$getTilt(), flyTick = iUPO.undertow$getFlyingTicks();
            if (iUPO.undertow$smallPosed() == 1) {
                GL11.glTranslated(0, -iUPO.undertow$getCurrentYDifference(), 0);
            }
            float theRatio = Float.min(tiltick + fracTick, FULL_TILT) / FULL_TILT;

            if (iUPO.undertow$isCrawling() || iUPO.undertow$isSwimming() || iUPO.undertow$rippingTide()
                || iUPO.undertow$getTilt() > 0) {
                float adj_angle = iUPO.undertow$isCrawling() ? 0 : this.bipedHead.rotateAngleX;//unlike model elements needs no  * 180 / (float) Math.PI;
                adj_angle += 90;
                adj_angle *= theRatio;
                GL11.glTranslated(0, -iUPO.undertow$getCurrentYDifference() * theRatio, 0);

                GL11.glRotatef(adj_angle,1,0,0); //this.bipedBody.rotateAngleX += adj_angle;
                if (iUPO.undertow$rippingTide() ) {
                    GL11.glRotatef(flyTick * 24 + fracTick, 0, 1, 0);
                }
            }*/


/*            if (iUPO.undertow$smallPosed() > 0) GL11.glTranslated(0, -iUPO.undertow$getCurrentYDifference(), 0);
//old ver
            if (iUPO.undertow$isCrawling() || iUPO.undertow$isSwimming() || iUPO.undertow$rippingTide()
                || iUPO.undertow$getTilt() > 0) {//zeppo.crawlPose() || zeppo.flyPose() || tilt > 0) {
                int tiltick = iUPO.undertow$getTilt(), flyTick = iUPO.undertow$getFlyingTicks();
                float adj_angle = iUPO.undertow$isCrawling() ? 0 : this.bipedHead.rotateAngleX * 180 / (float) Math.PI;
                adj_angle += 90;
                adj_angle *= (float) tiltick / FULL_TILT;

                this.bipedHead.rotateAngleX -= adj_angle * (float) Math.PI / 360;
                this.bipedHeadwear.rotateAngleX = this.bipedHead.rotateAngleX;
                this.bipedLeftLeg.rotateAngleX *= 1 - (float) (tiltick * 5) / (FULL_TILT * 6);
                this.bipedRightLeg.rotateAngleX *= 1 - (float) (tiltick * 5) / (FULL_TILT * 6);
                GL11.glRotatef(adj_angle, 1, 0, 0); //this.bipedBody.rotateAngleX += adj_angle;
                GL11.glRotatef(iUPO.undertow$rippingTide() ? flyTick * 24 + fracTick : 0, 0, 1, 0);//this.bipedBody.rotateAngleY += ;
            }
*/

        }
    }

    @Inject(method = "render(Lnet/minecraft/entity/Entity;FFFFFF)V", at = @At("HEAD"))
    private void statepush(CallbackInfo ci) {
        GL11.glPushMatrix();
    }
    @Inject(method = "render(Lnet/minecraft/entity/Entity;FFFFFF)V", at = @At("TAIL"))
    private void statepop(CallbackInfo ci) {
        GL11.glPopMatrix();
    }


}
