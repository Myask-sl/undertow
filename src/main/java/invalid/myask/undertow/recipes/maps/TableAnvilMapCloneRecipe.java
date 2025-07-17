package invalid.myask.undertow.recipes.maps;

import invalid.myask.undertow.UndertowItems;
import invalid.myask.undertow.recipes.anvil.IAnvilCosts;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class TableAnvilMapCloneRecipe extends RecipesMoreMapsCloning implements IAnvilCosts {
    public static TableAnvilMapCloneRecipe instance = new TableAnvilMapCloneRecipe();

    @Override
    public boolean matches(InventoryCrafting tableContents, World zaWarudo) {
        if (tableContents.getSizeInventory() != 2) return false;
        boolean isMap, andAnEmptyMap;
        Item slot0 = tableContents.getStackInSlot(0).getItem();
        Item slot1 = tableContents.getStackInSlot(1).getItem();
        isMap = slot0 == Items.filled_map || slot0 == UndertowItems.DECO_MAP || slot0 == UndertowItems.FILLED_DUMB_MAP;
        andAnEmptyMap = slot1 == Items.map || slot1 == UndertowItems.EMPTY_DUMB_MAP;
        return isMap && andAnEmptyMap;
    }

    @Override
    public int getRecipeSize() {
        return 2;
    }
}
