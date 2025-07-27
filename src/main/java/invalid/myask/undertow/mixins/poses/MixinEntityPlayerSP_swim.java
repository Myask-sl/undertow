package invalid.myask.undertow.mixins.poses;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.world.World;

import invalid.myask.undertow.Config;

@Mixin(EntityPlayerSP.class)
public abstract class MixinEntityPlayerSP_swim extends AbstractClientPlayer {
    public MixinEntityPlayerSP_swim(World world, GameProfile profile) {
        super(world, profile);
    }

    @ModifyExpressionValue(method = "onLivingUpdate()V",
        at = @At(value = "FIELD", target = "Lnet/minecraft/client/entity/EntityPlayerSP;isCollidedHorizontally:Z", opcode = Opcodes.GETFIELD))
    private boolean undertow$allowSwimAgainstWall(boolean collidedHorizontally) {
        return collidedHorizontally && Config.enable_swim && this.isInWater();
    }
}
