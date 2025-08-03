package invalid.myask.undertow.entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.world.World;

public class EntityBottledLightning extends EntityCalledLightning {
    public EntityBottledLightning(World w, double x, double y, double z, EntityLivingBase callerParam) {
        super(w, x, y, z, callerParam);
    }
    public EntityBottledLightning(World w, double x, double y, double z) {
        this(w, x, y, z, null);
    }
}
