package invalid.myask.undertow.ducks;

import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;

public interface IAnvilSlotGetter {
    IInventory getOutputSlot();
    IInventory getInputSlots();
    World getWorld();
}
