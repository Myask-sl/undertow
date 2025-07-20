package invalid.myask.undertow.mixins;

import net.minecraft.inventory.ContainerRepair;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ContainerRepair.class)
public interface IAnvilSlotGetter {
    @Accessor
    IInventory getOutputSlot();
    @Accessor
    IInventory getInputSlots();
    @Accessor
    World getTheWorld();
}
