package invalid.myask.undertow.recipes.anvil;

import invalid.myask.undertow.recipes.maps.TableMapZoomRecipe;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.world.World;

public class AnvilMapZoomRecipe extends TableMapZoomRecipe implements IAnvilCosts {
    public static AnvilMapZoomRecipe instance = new AnvilMapZoomRecipe();
    @Override
    public boolean matches(InventoryCrafting table, World world) {
        return super.matches(table, world) && table.getStackInSlot(1).stackSize >= 8;
    }

    @Override
    public int getMaterialCost() {
        return 8;
    }
}
