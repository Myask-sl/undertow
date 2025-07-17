package invalid.myask.undertow.recipes.maps;

import invalid.myask.undertow.UndertowItems;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;

public class RecipesDumbMapsExtending extends ShapedRecipes
{

    public RecipesDumbMapsExtending()
    {
        super(3, 3, new ItemStack[]
            {new ItemStack(Items.paper), new ItemStack(Items.paper), new ItemStack(Items.paper),
             new ItemStack(Items.paper), new ItemStack(UndertowItems.FILLED_DUMB_MAP, 0, 32767), new ItemStack(Items.paper),
                new ItemStack(Items.paper), new ItemStack(Items.paper), new ItemStack(Items.paper)},
            new ItemStack(UndertowItems.FILLED_DUMB_MAP, 0, 0));
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(InventoryCrafting tableContents, World zaWarudo)
    {
        if (!super.matches(tableContents, zaWarudo))
        {
            return false;
        }
        else
        {
            ItemStack itemstack = null;

            for (int i = 0; i < tableContents.getSizeInventory() && itemstack == null; ++i)
            {
                ItemStack itemstack1 = tableContents.getStackInSlot(i);

                if (itemstack1 != null && itemstack1.getItem() == UndertowItems.FILLED_DUMB_MAP)
                {
                    itemstack = itemstack1;
                }
            }

            if (itemstack == null)
            {
                return false;
            }
            else
            {
                MapData mapdata = UndertowItems.FILLED_DUMB_MAP.getMapData(itemstack, zaWarudo);
                return mapdata == null ? false : mapdata.scale < 4;
            }
        }
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack getCraftingResult(InventoryCrafting tableContents)
    {
        ItemStack originalMap = null;

        for (int i = 0; i < tableContents.getSizeInventory() && originalMap == null; ++i)
        {
            ItemStack thisSlot = tableContents.getStackInSlot(i);

            if (thisSlot != null && (thisSlot.getItem() == Items.filled_map || thisSlot.getItem() == UndertowItems.FILLED_DUMB_MAP))
            {
                originalMap = thisSlot;
            }
        }

        originalMap = originalMap.copy();
        originalMap.stackSize = 1;

        if (originalMap.getTagCompound() == null)
        {
            originalMap.setTagCompound(new NBTTagCompound());
        }

        originalMap.getTagCompound().setBoolean("map_is_scaling", true);
        return originalMap;
    }
}
