package invalid.myask.undertow.recipes.maps;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class MapRecipeHelper {
    public static ItemStack addCompass(ItemStack inMap) {
        ItemStack outMap = new ItemStack(Items.filled_map);
        copyMapStuff(inMap, outMap, false);
        return outMap;
    }

    public static ItemStack cloneMap(ItemStack inMap, int totalCopies) {
        ItemStack outMaps = new ItemStack(inMap.getItem(), totalCopies);
        copyMapStuff(inMap, outMaps, false);
        return outMaps;
    }

    public static ItemStack zoomMap(ItemStack inMap) {
        ItemStack outMap = new ItemStack(inMap.getItem());
        copyMapStuff(inMap, outMap, true);
        return outMap;
    }

    public static void copyMapStuff(ItemStack inMap, ItemStack outMap, boolean zoomOut) {
        outMap.setItemDamage(inMap.getItemDamage());
        NBTTagCompound nbt = new NBTTagCompound();
        if (inMap.getTagCompound() != null && inMap.getTagCompound().hasKey("decorations"))
            nbt.setTag("decorations", inMap.getTagCompound().getTag("decorations"));
        if (zoomOut)
            nbt.setBoolean("map_is_scaling", true);
        outMap.setTagCompound(nbt);
        if (inMap.hasDisplayName())
        {
            outMap.setStackDisplayName(inMap.getDisplayName());
        }
    }
}
