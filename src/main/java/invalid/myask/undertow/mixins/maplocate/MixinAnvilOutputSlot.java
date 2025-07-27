package invalid.myask.undertow.mixins.maplocate;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.inventory.IInventory;

import invalid.myask.undertow.Config;
import invalid.myask.undertow.recipes.CartographyRegistry;

@Mixin(targets = {"net.minecraft.inventory.ContainerRepair$2"})
public abstract class MixinAnvilOutputSlot extends net.minecraft.inventory.Slot {
    @Shadow(remap = false) @Final
    ContainerRepair this$0;

    public MixinAnvilOutputSlot(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
        super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
    }
    //Lnet/minecraft/inventory/ContainerRepair; Lnet/minecraft/inventory/ContainerRepair$2; "a(Lyz;)Z"
        @ModifyReturnValue(method = "canTakeStack(Lnet/minecraft/entity/player/EntityPlayer;)Z",
            at = @At("RETURN"))
        private boolean canTakeStack(boolean originalReturn, @Local(argsOnly = true) EntityPlayer player) {
            if (this$0.maximumCost == 0) { //If this is the case, then there is no need to check player exp level
                if (Config.allow_free_anvil_recipes_general ||
                    (Config.map_anvil_recipe_levelcost == 0 && CartographyRegistry.anvilMatch(
                        ((IAnvilSlotGetter)this$0).getInputSlots().getStackInSlot(0),
                        ((IAnvilSlotGetter)this$0).getInputSlots().getStackInSlot(1))))
                    return getHasStack();
            }
            return originalReturn;
        }
/*    @Redirect(method = "canTakeStack(Lnet/minecraft/entity/player/EntityPlayer;)Z",
    at = @At(value = "FIELD", target = "Lnet/minecraft/inventory/ContainerRepair;maximumCost:I", opcode = Opcodes.GETFIELD, ordinal = 1))
    private int fakeLevel(int oldLevel) {
        if (oldLevel == 0) {
            if (Config.allow_free_anvil_recipes_general) return 1;
            else if (Config.map_anvil_recipe_levelcost == 0)
                if (UndertowRecipes.anvilMatch(
                     ((IAnvilSlotGetter)this$0).getInputSlots().getStackInSlot(0),
                     ((IAnvilSlotGetter)this$0).getInputSlots().getStackInSlot(1)))
                    return 1;
        }
        return oldLevel;
    } */
/*    @Overwrite //works now. Failed to find it when mixin was inner class of mixin..
    public boolean canTakeStack(EntityPlayer player) {
        return (player.capabilities.isCreativeMode || player.experienceLevel >= this$0.maximumCost)
            && (this$0.maximumCost > 0 ||
            (Config.map_anvil_recipe_levelcost == 0
                && ((IAnvilSlotGetter)this$0).getOutputSlot()
                .getStackInSlot(0).getItem() instanceof ItemMapBase)
            || Config.allow_free_anvil_recipes_general)
            && this.getHasStack();
    }
*/
}
