package invalid.myask.undertow.blocks;

import invalid.myask.undertow.Undertow;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockCartographyTable extends BlockContainer {
    private IIcon sideIcon;
    public BlockCartographyTable(Material mat) {
        super(mat);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return null;
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return side == 1 ? blockIcon : sideIcon;
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        this.blockIcon = reg.registerIcon(Undertow.MODID + ":" + this.textureName);
        this.sideIcon = reg.registerIcon(Undertow.MODID + ":" + this.textureName + "side");
    }
}
