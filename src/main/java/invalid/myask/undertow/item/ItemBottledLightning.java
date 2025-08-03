package invalid.myask.undertow.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import invalid.myask.undertow.entities.ProjectileLightningBottle;

public class ItemBottledLightning extends Item {
    public ItemBottledLightning() {
        this.maxStackSize = 16;
    }

    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer player)
    {
        if (!player.capabilities.isCreativeMode)
        {
            --itemStackIn.stackSize;
        }

        worldIn.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!worldIn.isRemote)
        {
            worldIn.spawnEntityInWorld(new ProjectileLightningBottle(worldIn, player));
        }

        return itemStackIn;
    }
}
