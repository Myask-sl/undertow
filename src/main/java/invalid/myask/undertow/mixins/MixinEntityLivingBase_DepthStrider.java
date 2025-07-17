package invalid.myask.undertow.mixins;

import invalid.myask.undertow.enchantments.EnchantmentDepthStrider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase_DepthStrider extends Entity {

    public MixinEntityLivingBase_DepthStrider(World worldIn) {
        super(worldIn);
    }

    @ModifyArg(method = "moveEntityWithHeading", //"Lnet/minecraft/entity/EntityLivingBase;moveEntityWithHeading(FF)V",
        slice = @Slice(to = @At(value = "INVOKE",
            target = "Lnet/minecraft/entity/EntityLivingBase;moveEntity(DDD)V", ordinal = 0) ),
        at = @At(value = "INVOKE",
            target = "Lnet/minecraft/entity/EntityLivingBase;moveFlying(FFF)V"), index = 2)
    private float undertow$newFriction(float originalFriction) {
        return (float) EnchantmentDepthStrider.getAdjustedFluidFriction((EntityLivingBase) (Object) this, originalFriction, false);
    }

    @Inject(method = "moveEntityWithHeading", //"Lnet/minecraft/entity/EntityLivingBase;moveEntityWithHeading(FF)V",
      at = @At(value = "INVOKE",
          target = "Lnet/minecraft/entity/EntityLivingBase;moveEntity(DDD)V", shift = At.Shift.AFTER, ordinal = 0))
    private void undertow$undoFriction(CallbackInfo cbA) {
        EnchantmentDepthStrider.counterFluidFriction((EntityLivingBase) (Object) this, false);
    }

    @ModifyArg(method = "moveEntityWithHeading", //"Lnet/minecraft/entity/EntityLivingBase;moveEntityWithHeading(FF)V",
        slice = @Slice(from = @At(value = "INVOKE",
            target = "Lnet/minecraft/entity/EntityLivingBase;moveEntity(DDD)V", ordinal = 0),
            to = @At(value = "INVOKE",
            target = "Lnet/minecraft/entity/EntityLivingBase;moveEntity(DDD)V", ordinal = 1) ),
        at = @At(value = "INVOKE",
            target = "Lnet/minecraft/entity/EntityLivingBase;moveFlying(FFF)V"), index = 2)
    private float undertow$newLavaFriction(float x) {
        return (float) EnchantmentDepthStrider.getAdjustedFluidFriction((EntityLivingBase) (Object) this, x, true);
    }

    @Inject(method = "moveEntityWithHeading", //"Lnet/minecraft/entity/EntityLivingBase;moveEntityWithHeading(FF)V",
        at = @At(value = "INVOKE",
            target = "Lnet/minecraft/entity/EntityLivingBase;moveEntity(DDD)V", shift = At.Shift.AFTER, ordinal = 1))
    private void undertow$undoLavaFriction(CallbackInfo cbA) {
        EnchantmentDepthStrider.counterFluidFriction((EntityLivingBase) (Object) this, true);
    }
}
