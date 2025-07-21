package invalid.myask.undertow.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockPlain extends Block {
    public BlockPlain(Material materialIn) { //because Block(Material) is protected.
        super(materialIn);
    }
}
