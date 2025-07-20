package invalid.myask.undertow.entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.world.World;

public class EntityCalledLightning extends EntityLightningBolt {
    public EntityLivingBase caller = null;
    public EntityCalledLightning(World w, double x, double y, double z, EntityLivingBase callerParam) {
        super(w, x, y, z);
        this.caller = callerParam;
    }
    public EntityCalledLightning(World w, double x, double y, double z) {
        this(w, x, y, z, null);
    }
}
