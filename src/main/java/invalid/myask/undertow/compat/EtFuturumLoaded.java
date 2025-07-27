package invalid.myask.undertow.compat;

import net.minecraft.entity.player.EntityPlayer;

import ganymedes01.etfuturum.api.elytra.IElytraPlayer;

public class EtFuturumLoaded extends EtFuturum {
    @Override
    public boolean isPlayerFlying(EntityPlayer steve) {
        return ((IElytraPlayer)steve).etfu$isElytraFlying();
    }
}
