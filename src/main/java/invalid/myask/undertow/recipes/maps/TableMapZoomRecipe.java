package invalid.myask.undertow.recipes.maps;

import invalid.myask.undertow.UndertowItems;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;

public class TableMapZoomRecipe extends RecipesDumbMapsExtending {
    @Override
    public boolean matches(InventoryCrafting table, World world) {
        if (table.getSizeInventory() == 2
            && table.getStackInSlot(1).getItem() == Items.paper) {
            MapData mapdata = null;
            ItemStack mapIn = table.getStackInSlot(0);
            if (mapIn.getItem() == UndertowItems.FILLED_DUMB_MAP)
                mapdata = UndertowItems.FILLED_DUMB_MAP.getMapData(mapIn, world);
            else if (mapIn.getItem() == Items.filled_map)
                mapdata = Items.filled_map.getMapData(mapIn, world);
            return mapdata != null && mapdata.scale < 4;
        }
        return false;
    }

    @Override
    public int getRecipeSize() {
        return 2;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }
}
