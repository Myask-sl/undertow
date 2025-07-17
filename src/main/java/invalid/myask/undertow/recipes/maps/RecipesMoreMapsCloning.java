package invalid.myask.undertow.recipes.maps;

import invalid.myask.undertow.UndertowItems;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipesMapCloning;
import net.minecraft.world.World;

public class RecipesMoreMapsCloning extends RecipesMapCloning
{
    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(InventoryCrafting tableContents, World zaWarudo)
    {
        int howMany = 0;
        ItemStack theMap = null;

        for (int j = 0; j < tableContents.getSizeInventory(); ++j)
        {
            ItemStack thisSlot = tableContents.getStackInSlot(j);

            if (thisSlot != null)
            {
                if (thisSlot.getItem() == Items.filled_map
                    || thisSlot.getItem() == UndertowItems.FILLED_DUMB_MAP
                    || thisSlot.getItem() == UndertowItems.DECO_MAP)
                {
                    if (theMap != null)
                    {
                        return false;
                    }

                    theMap = thisSlot;
                }
                else
                {
                    if (thisSlot.getItem() != Items.map && thisSlot.getItem() != UndertowItems.EMPTY_DUMB_MAP)
                    {
                        return false;
                    }

                    howMany++;
                }
            }
        }

        return theMap != null && howMany > 0;
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack getCraftingResult(InventoryCrafting tableContents)
    {
        int howMany = 0;
        ItemStack theMap = null;

        for (int j = 0; j < tableContents.getSizeInventory(); j++)
        {
            ItemStack thisSlot = tableContents.getStackInSlot(j);

            if (thisSlot != null)
            {
                if (thisSlot.getItem() == Items.filled_map || thisSlot.getItem() == UndertowItems.FILLED_DUMB_MAP || thisSlot.getItem() == UndertowItems.DECO_MAP)
                {
                    if (theMap != null)
                    {
                        return null;
                    }

                    theMap = thisSlot;
                }
                else
                {
                    if (thisSlot.getItem() != Items.map && thisSlot.getItem() != UndertowItems.EMPTY_DUMB_MAP)
                    {
                        return null;
                    }

                    ++howMany;
                }
            }
        }

        if (theMap != null && howMany >= 1)
        {
            ItemStack resultMaps = MapRecipeHelper.cloneMap(theMap, howMany + 1);

            return resultMaps;
        }
        else
        {
            return null;
        }
    }
}
