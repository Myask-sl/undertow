package invalid.myask.undertow.mixins.poses;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.client.entity.EntityClientPlayerMP;

import invalid.myask.undertow.ducks.IUndertowPosableEntity;

@Mixin(EntityClientPlayerMP.class)
public class MixinEntityClientPlayerMP_forcemovepacket { //TODO: delete
    @ModifyVariable(method = "sendMotionUpdates()V", at = @At("STORE"), ordinal = 2)
    private boolean undertow$forceMovePacket(boolean normove) {
        return normove || ((IUndertowPosableEntity)this).undertow$forcingMovePacket();
    }
}
