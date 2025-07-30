package invalid.myask.undertow.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IVesselOfMob<T extends Entity> {
    T containedEntity(ItemStack stack, World w);
    default T containedEntity(ItemStack stack, World w, double x, double y, double z) {
        T djinni = containedEntity(stack, w);
        djinni.setPosition(x, y, z);
        return djinni;
    }

    default void releaseEntity(EntityPlayer user, ItemStack stack, World w, double x, double y, double z) {
        Entity e = containedEntity(stack, w, x, y, z);
        w.spawnEntityInWorld(e);
    }
}
