package invalid.myask.undertow.recipes.anvil;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class SimpleAnvilRecipe implements IRecipe, IAnvilCosts {
    public final ItemStack left, right, result;
    public final int materialCost, levelCost;

    public SimpleAnvilRecipe(ItemStack left, ItemStack right, ItemStack result, int materialCost, int levelCost) {
        this.left = left;
        this.right = right;
        this.result = result;
        this.materialCost = materialCost;
        this.levelCost = levelCost;
    }

    @Override
    public boolean matches(InventoryCrafting anvilContents, World w) {
        ItemStack leftIn = anvilContents.getStackInSlot(0), rightIn = anvilContents.getStackInSlot(1);
        return matches (leftIn, rightIn);
    }

    public boolean matches(ItemStack leftIn, ItemStack rightIn) {
        boolean match = true;
        if (left != null && leftIn != null) {
            match = leftIn.getItem() == left.getItem();
            match = match && leftIn.getItemDamage() == left.getItemDamage();
        } else if (left == null ^ leftIn == null)
            return false;
        if (right != null && rightIn != null) {
            match = match && rightIn.getItem() == right.getItem();
            match = match && rightIn.getItemDamage() == right.getItemDamage();
        } else if (right == null ^ rightIn == null)
            return false;
        return match;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting anvilContents) {
        return result.copy();
    }

    @Override
    public int getRecipeSize() {
        return ((left == null) ? 0 : 1) + ((right == null) ? 0 : 1);
    }

    @Override
    public ItemStack getRecipeOutput() {
        return result;
    }

    @Override
    public int getLevelCost() {
        return levelCost;
    }

    @Override
    public int getMaterialCost() {
        return materialCost;
    }
}
