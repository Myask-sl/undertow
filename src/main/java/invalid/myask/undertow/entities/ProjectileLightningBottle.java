package invalid.myask.undertow.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import invalid.myask.undertow.Config;

public class ProjectileLightningBottle extends EntityThrowable {
    @SuppressWarnings("unused")
    public ProjectileLightningBottle(World worldIn) {
        super(worldIn);
    }

    public ProjectileLightningBottle(World worldIn, EntityPlayer player) {
        super(worldIn, player);
    }

    public ProjectileLightningBottle(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    @Override
    protected void onImpact(MovingObjectPosition hit) {
        Entity bolt = new EntityBottledLightning(worldObj, hit.blockX + 0.5, hit.blockY, hit.blockZ, getThrower());
        worldObj.spawnEntityInWorld(bolt);
        if (!Config.silly_lightning_bottles) setDead();
    }
}
