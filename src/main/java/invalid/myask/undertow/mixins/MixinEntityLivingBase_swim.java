package invalid.myask.undertow.mixins;

import invalid.myask.undertow.Config;
import invalid.myask.undertow.ducks.IUndertowPosableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase_swim extends Entity {
    public MixinEntityLivingBase_swim(World worldIn) {
        super(worldIn);
    }

    @ModifyArg(method = "moveEntityWithHeading", //"Lnet/minecraft/entity/EntityLivingBase;moveEntityWithHeading(FF)V",
        slice = @Slice(to = @At(value = "INVOKE",
            target = "Lnet/minecraft/entity/EntityLivingBase;moveEntity(DDD)V", ordinal = 0) ),
        at = @At(value = "INVOKE",
            target = "Lnet/minecraft/entity/EntityLivingBase;moveFlying(FFF)V"), index = 1)
    private float undertow$pitchSwimForward(float originalForward) { //TODO: global swim enable?
        if (!((IUndertowPosableEntity) this).undertow$isSwimming()) return originalForward;
        this.motionY = (Config.silly_swim ? this.motionY : 0)
            + originalForward * MathHelper.sin(-this.rotationPitch * (float)Math.PI / 180.0F)
            * Config.swim_y_multiplier;
        return originalForward * MathHelper.cos(-this.rotationPitch * (float)Math.PI / 180.0F);
    }

    @Inject(method = "moveEntityWithHeading", //"Lnet/minecraft/entity/EntityLivingBase;moveEntityWithHeading(FF)V",
        at = @At(value = "INVOKE",
            target = "Lnet/minecraft/entity/EntityLivingBase;moveEntity(DDD)V", shift = At.Shift.AFTER, ordinal = 0))
    private void undertow$undoGravity(CallbackInfo cbA) {
        if (((IUndertowPosableEntity)(Entity) this).undertow$isSwimming())
            this.motionY += 0.02D;
    }
}
