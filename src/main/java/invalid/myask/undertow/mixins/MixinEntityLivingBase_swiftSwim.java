package invalid.myask.undertow.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import invalid.myask.undertow.enchantments.EnchantmentSwiftSwim;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase_swiftSwim extends Entity {
    public MixinEntityLivingBase_swiftSwim(World worldIn) {
        super(worldIn);
    }

    @WrapOperation(method = "moveEntityWithHeading",
        at = @At(value = "INVOKE",
            target = "Lnet/minecraft/entity/EntityLivingBase;moveEntity(DDD)V", ordinal = 0))
    private void multiplyMotion(EntityLivingBase instance, double vX, double vY, double vZ, Operation<Void> original) {
        float swim_multiplier = EnchantmentSwiftSwim.getSwiftSwimMultiplier((EntityLivingBase) (Entity) this, false);
        original.call(instance, vX * swim_multiplier, vY * swim_multiplier, vZ * swim_multiplier);
    }
}
