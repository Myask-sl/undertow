package invalid.myask.undertow.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import invalid.myask.undertow.enchantments.EnchantmentDepthStrider;
import invalid.myask.undertow.util.UndertowUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(World.class)
public class MixinWorld_DepthstriderAntiCurrent {
    @Redirect(method = "handleMaterialAcceleration", //"Lnet/minecraft/world/World;handleMaterialAcceleration(Lnet/minecraft/util/AxisAlignedBB;Lnet/minecraft/block/material/Material;Lnet/minecraft/entity/Entity;)Z",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Vec3;normalize()Lnet/minecraft/util/Vec3;"))
    private Vec3 undertow$vDivider(Vec3 instance, @Local Entity entity) {
        double mult = entity instanceof EntitySquid ? 1 : EnchantmentDepthStrider.getFlowMult(entity, false);
        Vec3 oldVec = instance.normalize();
        instance = UndertowUtils.scalarMult(instance, mult);
        return instance;
    }
}
