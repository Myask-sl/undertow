package invalid.myask.undertow.recipes.maps;

import invalid.myask.undertow.UndertowItems;
import invalid.myask.undertow.recipes.anvil.IAnvilCosts;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class TableAnvilMapCompassAddRecipe implements IRecipe, IAnvilCosts {
    public static TableAnvilMapCompassAddRecipe instance = new TableAnvilMapCompassAddRecipe();
    ItemStack recOut = new ItemStack(Items.filled_map);

    @Override
    public boolean matches(InventoryCrafting table, World zaWarudo) {
        return table.getSizeInventory() == 2
            && table.getStackInSlot(0).getItem() == UndertowItems.FILLED_DUMB_MAP
            && table.getStackInSlot(1).getItem() == Items.compass;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting table) {
        return MapRecipeHelper.addCompass(table.getStackInSlot(0));
    }

    @Override
    public int getRecipeSize() {
        return 2;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return recOut;
    }
}
