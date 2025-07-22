package invalid.myask.undertow;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.oredict.OreDictionary;

import invalid.myask.undertow.blocks.BlockCartographyTable;
import invalid.myask.undertow.blocks.BlockConduit;
import invalid.myask.undertow.blocks.BlockPlain;

public class UndertowBlocks {
    public static final Block CARTOGRAPHY_TABLE = new BlockCartographyTable(Material.wood).setBlockName("cartography_table").setBlockTextureName("maptable");
    public static final Block CONDUIT = new BlockConduit();
    public static final Block RAW_PRISMARINE = new BlockPlain(Material.rock).setModdedTextureName("prismarine_raw").setBlockName("prismarine_raw").setHardness(3.0F);
    public static void register() {
        GameRegistry.registerBlock(CARTOGRAPHY_TABLE, "cartography_table");
        GameRegistry.registerBlock(RAW_PRISMARINE, "prismarine_raw");
        OreDictionary.registerOre("prismarineAll", RAW_PRISMARINE);
        OreDictionary.registerOre("prismarineRaw", RAW_PRISMARINE);
    }
}
