package invalid.myask.undertow.item;

import invalid.myask.undertow.UndertowItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemMapBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;

public class ItemEmptyDumbMap extends ItemMapBase implements IBackportedMap {

    public ItemEmptyDumbMap()
    {
        this.setCreativeTab(CreativeTabs.tabMisc);
        this.setUnlocalizedName("dumb_map");
        this.setTextureName("map_empty");
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer player)
    {
        ItemStack writtenMap = new ItemStack(UndertowItems.FILLED_DUMB_MAP, 1, worldIn.getUniqueDataId("map"));
        String mapID = "map_" + writtenMap.getItemDamage();
        MapData mapdata = new MapData(mapID);
        worldIn.setItemData(mapID, mapdata);
        mapdata.scale = 0;
        int i = 128 * (1 << mapdata.scale);
        mapdata.xCenter = (int)(Math.round(player.posX / (double)i) * (long)i);
        mapdata.zCenter = (int)(Math.round(player.posZ / (double)i) * (long)i);
        mapdata.dimension = worldIn.provider.dimensionId;
        mapdata.markDirty();
        --itemStackIn.stackSize;

        if (itemStackIn.stackSize <= 0)
        {
            return writtenMap;
        }
        else
        {
            if (!player.inventory.addItemStackToInventory(writtenMap.copy()))
            {
                player.dropPlayerItemWithRandomChoice(writtenMap, false);
            }

            return itemStackIn;
        }
    }
}
