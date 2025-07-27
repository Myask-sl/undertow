package invalid.myask.undertow.compat;

import cpw.mods.fml.common.Loader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class EtFuturum {
    public static EtFuturum passthrough = new EtFuturum();

    public static boolean isElytraFlying(Entity herobrine) {
        if (herobrine instanceof EntityPlayer steve)
            return passthrough.isPlayerFlying(steve);
        return false;
    }

    public boolean isPlayerFlying(EntityPlayer herobrine) {
        if (Loader.isModLoaded("etfuturum"))
            passthrough = new EtFuturumLoaded();
        else passthrough = new EtFuturum.Unloaded();
        return passthrough.isPlayerFlying(herobrine);
    }

    private static class Unloaded extends EtFuturum {
        @Override
        public boolean isPlayerFlying(EntityPlayer roadhog) {
            return false;
        }
    }
}
