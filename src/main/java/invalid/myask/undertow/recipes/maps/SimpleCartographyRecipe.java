package invalid.myask.undertow.recipes.maps;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class SimpleCartographyRecipe implements IRecipe {
    ItemStack output;
    Item first;
    Item second;
    public SimpleCartographyRecipe(ItemStack out, Item first, Item second) {
        output = out; this.first = first; this.second = second;
    }

    @Override
    public boolean matches(@Nonnull InventoryCrafting inventory, World world) {
        return inventory.getSizeInventory() == 2
            && inventory.getStackInSlot(0).getItem() == first
            && inventory.getStackInSlot(1).getItem() == second;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventory) {
        ItemStack result = output.copy();
        return result;
    }

    @Override
    public int getRecipeSize() {
        return 2;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return output;
    }
}
