package invalid.myask.undertow.mixins;

import invalid.myask.undertow.ducks.IAnvilSlotGetter;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ContainerRepair.class)
public abstract class MixinContainerRepair_anvilZeroLevelRecipes extends Container implements IAnvilSlotGetter {
    @Shadow
    public int maximumCost;
    @Shadow
    private IInventory outputSlot;
    @Shadow
    private IInventory inputSlots;
    @Shadow
    private World theWorld;

    @Override
    public IInventory getOutputSlot() {return outputSlot;}

    @Override
    public IInventory getInputSlots() {return inputSlots;}

    @Override
    public World getWorld() { return theWorld; } //no point because the ContainerRepair isn't passed anyway, so...

/*    @Inject(method = "updateRepairOutput",
        at = @At(value = "INVOKE", target =
            "Lnet/minecraftforge/common/ForgeHooks;onAnvilChange(Lnet/minecraft/inventory/ContainerRepair;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;Lnet/minecraft/inventory/IInventory;Ljava/lang/String;I)Z"))
    private void grabWorldForEvent() {
        HearTheAnvil.recipeWorld.put(inputSlots.getStackInSlot(0), theWorld);
        //TODO: bimap stack, player UUID. No, better: write zoom to tags.
    }*/
}
