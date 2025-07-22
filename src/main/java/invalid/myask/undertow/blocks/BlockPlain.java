package invalid.myask.undertow.blocks;

import cpw.mods.fml.common.Loader;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockPlain extends Block {
    public BlockPlain(Material materialIn) { //because Block(Material) is protected.
        super(materialIn);
    }

    public Block setModdedTextureName(String textureName) {
        return setBlockTextureName(Loader.instance().activeModContainer().getModId() + ":" + textureName);
    }
}
