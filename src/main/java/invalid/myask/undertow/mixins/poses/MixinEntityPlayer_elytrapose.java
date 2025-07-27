package invalid.myask.undertow.mixins.poses;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import invalid.myask.undertow.compat.EtFuturum;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer_elytrapose extends MixinEntityLivingBase_Poses {
    public MixinEntityPlayer_elytrapose(World worldIn) {
        super(worldIn);
    }

    @Override
    public int undertow$smallPosed() {
        return EtFuturum.isElytraFlying(this) ? 2 : super.undertow$smallPosed();
    }
}
