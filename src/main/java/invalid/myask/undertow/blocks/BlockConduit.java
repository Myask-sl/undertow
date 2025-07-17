package invalid.myask.undertow.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockConduit extends Block implements ITileEntityProvider {
    protected BlockConduit(Material materialIn) {
        super(materialIn);
    }

    public BlockConduit() {this(Material.coral);}

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return null;
    }
}
