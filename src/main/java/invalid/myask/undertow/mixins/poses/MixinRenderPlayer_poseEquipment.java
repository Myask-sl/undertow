package invalid.myask.undertow.mixins.poses;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(RenderPlayer.class)
public abstract class MixinRenderPlayer_poseEquipment extends RendererLivingEntity {//TODO: delete
    public MixinRenderPlayer_poseEquipment(ModelBase mb, float f) {
        super(mb, f);
    }

    //TODO: pose for hand.
/*
    @Inject(method="renderEquippedItems(Lnet/minecraft/client/entity/AbstractClientPlayer;F)V",
        slice = @Slice(
            from = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderPlayer;bindTexture(Lnet/minecraft/util/ResourceLocation;)V",
                ordinal = 1)),
        at = @At(value = "INVOKE", target = "glTranslatef(FFF)V", ordinal = 0, remap = false))
            //"Lnet/minecraft/client/model/ModelBiped;renderCloak(F)V"))
    private void fixCloak(AbstractClientPlayer player, float fracTick, CallbackInfo ci) {
        //TODO: put it in sensible spot. Think I did?
        IUndertowPosableEntity iUPO = ((IUndertowPosableEntity) player);
        int tiltick = iUPO.undertow$getTilt(), flyTick = iUPO.undertow$getFlyingTicks();
        float adj_angle = iUPO.undertow$isCrawling() ? 0 : ((ModelBiped)mainModel).bipedHead.rotateAngleX * 180 / (float) Math.PI;
        adj_angle += 90;
        adj_angle *= (float) tiltick / FULL_TILT;

        if (iUPO.undertow$smallPosed() > 0) GL11.glTranslated(0, -iUPO.undertow$getCurrentYDifference(), 0);

        GL11.glRotatef(adj_angle, 1, 0, 0); //this.bipedBody.rotateAngleX += adj_angle;
        GL11.glRotatef(iUPO.undertow$rippingTide() ? flyTick * 24 + fracTick : 0, 0, 1, 0);
    } */
}
