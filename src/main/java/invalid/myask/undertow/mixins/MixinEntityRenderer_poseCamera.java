package invalid.myask.undertow.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.EntityLivingBase;

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer_poseCamera {
/*    @Inject(method = "orientCamera(F)V", at = @At("INVOKE",
        target = "Lorg/lwjgl/opengl/GL11;glRotatef(FFFF)V"))  // double camY, @Local(ordinal = 0)  */
    @Shadow
    private Minecraft mc;
    @ModifyVariable(method = "orientCamera(F)V", at = @At("STORE"), ordinal = 1, name = "f1")
    private float poseCamera(float yDiff) {
        return -mc.thePlayer.ySize; //KnownGood: 0
        //(float) (yDiff + ((IUndertowPosableEntity)this.mc.renderViewEntity).undertow$getCurrentYDifference());
    }
 /*   @Inject(method = "orientCamera(F)V", at = @At("INVOKE",
    target = "Lorg/lwjgl/opengl/GL11;glTranslatef(FFF)V", ordinal = 4))
    private*/
}
