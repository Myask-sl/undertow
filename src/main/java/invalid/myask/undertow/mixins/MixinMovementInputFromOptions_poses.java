package invalid.myask.undertow.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import invalid.myask.undertow.ducks.IUndertowPosableEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MovementInputFromOptions;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MovementInputFromOptions.class)
public class MixinMovementInputFromOptions_poses {
    @ModifyExpressionValue(method = "updatePlayerMoveState()V",
        at = @At(value = "FIELD", target = "Lnet/minecraft/util/MovementInputFromOptions;sneak:Z", opcode = Opcodes.GETFIELD))
    private boolean crawlingSlowsLikeSneaking(boolean original) {
        return original || ((IUndertowPosableEntity)Minecraft.getMinecraft().thePlayer).undertow$isCrawling();
    }
}
