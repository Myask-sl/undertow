package invalid.myask.undertow.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.storage.MapData;

import invalid.myask.undertow.Config;
import invalid.myask.undertow.UndertowItems;
import invalid.myask.undertow.ducks.IMapGenStructure_Accessor;
import invalid.myask.undertow.util.UndertowUtils;

public class ItemExplorerMap extends ItemDecoMap {
    public ItemExplorerMap() {
        super();
        this.setUnlocalizedName("explorer_map");
        this.setTextureName("map_filled");
    }

    public static ItemStack mapTo(MapGenStructure target, String targetname, World w, int x, int z) {
        ItemStack s = new ItemStack(UndertowItems.EXPLORER_MAP);
        s.setItemDamage(w.getUniqueDataId("map"));
        MapData mapData = Items.filled_map.getMapData(s, w);
        NBTTagCompound nbt = s.getTagCompound();
        if (nbt == null) nbt = new NBTTagCompound();
        ((IMapGenStructure_Accessor)target).undertow$assureWorld(w);
        ChunkCoordIntPair theSpot = UndertowUtils.locateStructure(target, w, x, z, Config.explorer_map_search_radius, !Config.explorer_map_always_unfound, true,
            (target instanceof MapGenScatteredFeature) ? UndertowUtils.getTempleBiomeByName(targetname) : null);
        mapData.xCenter = theSpot.chunkXPos * 16;
        mapData.zCenter = theSpot.chunkZPos * 16;
        mapData.scale = Config.explorer_map_zoom;

        nbt.setString("target", targetname);
        nbt.setInteger("targetX", mapData.xCenter);
        nbt.setInteger("targetZ", mapData.zCenter);
        s.setTagCompound(nbt);

        int scaleFactor = 128 * (1 << mapData.scale);
        mapData.xCenter = (int)(Math.round(mapData.xCenter / (double)scaleFactor) * (long)scaleFactor);
        mapData.zCenter = (int)(Math.round(mapData.zCenter / (double)scaleFactor) * (long)scaleFactor);
        mapData.dimension = w.provider.dimensionId;

        mapData.markDirty();
        return s;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        NBTTagCompound nbt = stack.getTagCompound();
        String resultName = "item.explorer_map";
        if (nbt != null) {
            String targName = nbt.getString("target");
            if (targName != null) resultName += "." + targName;
        }
        return resultName;
    }

    @Override
    public void addInformation(ItemStack theMap, EntityPlayer player, List<String> tooltipLines, boolean showAdvancedTooltips) {
        super.addInformation(theMap, player, tooltipLines, showAdvancedTooltips);
        if (showAdvancedTooltips)
            if (theMap.getTagCompound() != null && theMap.getTagCompound().getString("targetname") != null)
                tooltipLines.add("Target: " + theMap.getTagCompound().getString("targetname"));
    }
}
