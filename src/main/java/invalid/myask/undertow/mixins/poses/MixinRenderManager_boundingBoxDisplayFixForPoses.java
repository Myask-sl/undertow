package invalid.myask.undertow.mixins.poses;

import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;

@Mixin(RenderManager.class)
public class MixinRenderManager_boundingBoxDisplayFixForPoses {
    @ModifyVariable(method = "renderDebugBoundingBox",
        at = @At("STORE"), ordinal = 0)
    private AxisAlignedBB undertow$fixposeHeights(AxisAlignedBB original, @Local(argsOnly = true) Entity entity) {
//        if (entity instanceof EntityLivingBase) {
            double correctionAmt = -entity.yOffset; //1.62D -((IUndertowPosableEntity)entity).undertow$getCurrentYDifference();
            original.minY += correctionAmt; original.maxY += correctionAmt;
//        }
        return original;
    }
}
