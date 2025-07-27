package invalid.myask.undertow.mixins.poses;

import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import invalid.myask.undertow.ducks.IUndertowPosableEntity;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;

@Mixin(RenderPlayer.class)
public class MixinRenderPlayer_flagHandRender {
    @Inject(method = "renderFirstPersonArm(Lnet/minecraft/entity/player/EntityPlayer;)V",
        at = @At(value = "INVOKE",
        target = "Lnet/minecraft/client/model/ModelBiped;setRotationAngles(FFFFFFLnet/minecraft/entity/Entity;)V"))
    private void flagAHand(CallbackInfo nameandnumber, @Local(argsOnly = true) EntityPlayer player) {
        ((IUndertowPosableEntity)player).undertow$setHandRendering();
    }
}
