package invalid.myask.undertow;

import cpw.mods.fml.common.registry.GameRegistry;
import invalid.myask.undertow.blocks.BlockCartographyTable;
import invalid.myask.undertow.blocks.BlockConduit;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class UndertowBlocks {
    public static final Block CARTOGRAPHY_TABLE = new BlockCartographyTable(Material.wood).setBlockName("cartography_table").setBlockTextureName("maptable");
    public static final Block CONDUIT = new BlockConduit();
    public static void register() {
        GameRegistry.registerBlock(CARTOGRAPHY_TABLE, "cartography_table");
    }
}
