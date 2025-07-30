package invalid.myask.undertow.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import invalid.myask.undertow.entities.ProjectileLightningBottle;
import invalid.myask.undertow.entities.EntityCalledLightning;

public class ItemBottledLightning extends Item implements IVesselOfMob<EntityCalledLightning> {
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

    @Override
    public EntityCalledLightning containedEntity(ItemStack stack, World w) {
        return null; //can't set position late
    }

    @Override
    public EntityCalledLightning containedEntity(ItemStack stack, World w, double x, double y, double z) {
        return new EntityCalledLightning(w, x, y, z);
    }

    @Override
    public void releaseEntity(EntityPlayer user, ItemStack stack, World w, double x, double y, double z) {
        Entity e = new EntityCalledLightning(w, x, y, z, user);
        w.spawnEntityInWorld(e);
    }

    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        return pass > 0 ? Items.glass_bottle.getIcon(stack, 0) : itemIcon;
    }
}
