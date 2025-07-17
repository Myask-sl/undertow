package invalid.myask.undertow.mixins;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(NetHandlerPlayServer.class)
public class MixinNetHandlerPlayServer { //TODO: delete...or add speedcap override for riptide.
    @Shadow
    public EntityPlayerMP playerEntity;

    @ModifyConstant(method = "setPlayerLocation(DDDFF)V", constant = @Constant(doubleValue = 1.6200000047683716D))
    private double useYOffset(double oldValue) {
        return oldValue; // + ((IUndertowPosableEntity)playerEntity).undertow$getCurrentYDifference();
    }
}
