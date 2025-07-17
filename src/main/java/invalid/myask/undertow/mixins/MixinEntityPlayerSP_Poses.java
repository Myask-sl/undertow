package invalid.myask.undertow.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.authlib.GameProfile;
import invalid.myask.undertow.Undertow;
import invalid.myask.undertow.ducks.IUndertowPosableEntity;
import invalid.myask.undertow.network.PoseChangeMessage;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityPlayerSP.class)
public abstract class MixinEntityPlayerSP_Poses extends AbstractClientPlayer {
    @Unique
    private boolean undertow$wasRippingTide = false, undertow$wasCrawling = false, undertow$wasSwimming = false;

    public MixinEntityPlayerSP_Poses(World world, GameProfile poses) {
        super(world, poses);
    }
    //sneak is automatically taken care of..in theory.

    //@Inject(method = "onUpdate()V", at = @At("HEAD"))
    //private void undertow$fireAtWill (CallbackInfo starsixnine) {
    //oh, there's none present at this level, so it's just a straight Override.

    @Override
    public void onUpdate() {
        super.onUpdate();

        IUndertowPosableEntity mcClane = (IUndertowPosableEntity) this;
        if (mcClane.undertow$rippingTide() != undertow$wasRippingTide ||
            mcClane.undertow$isCrawling() != undertow$wasCrawling ||
            mcClane.undertow$isSwimming() != undertow$wasSwimming) {
            undertow$wasRippingTide = mcClane.undertow$rippingTide();
            undertow$wasCrawling = mcClane.undertow$isCrawling();
            undertow$wasSwimming = mcClane.undertow$isSwimming();
            Undertow.networkWrapper.sendToServer(new PoseChangeMessage(undertow$wasCrawling, undertow$wasSwimming, undertow$wasRippingTide,
                mcClane.undertow$getFlyingTicks(), mcClane.undertow$getMaxFlyTicks()));
        }
    }

    //EntityPlayerSP doesn't super.isSneaking() so gotta re-mixin
    @ModifyReturnValue(method = "isSneaking()Z", at = @At ("RETURN"))
    private boolean undertow$forceSneakEnact(boolean wasReturn) {
        if (((IUndertowPosableEntity)this).undertow$forcingSneak() && !isPlayerSleeping()) return true;
        return wasReturn;
    }
    /*@ModifyExpressionValue(method = "onLivingUpdate()V",
        at = @At(value = "FIELD", target = "Lnet/minecraft/util/MovementInputFromOptions;sneak:Z", opcode = Opcodes.GETFIELD))
    private boolean stopMessingMyRender(boolean original) {
        return false;
    }*/
}
