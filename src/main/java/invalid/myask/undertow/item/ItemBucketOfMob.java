package invalid.myask.undertow.item;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemBucketOfMob extends ItemBucket implements IVesselOfMob<EntityLiving> {
    public final String kindOfMob;
    protected final Block fluidBlock;
    public ItemBucketOfMob(Block whichFluid, Class<? extends EntityLiving> kindOfMob) {
        super(whichFluid);
        fluidBlock = whichFluid;
        this.kindOfMob = EntityList.classToStringMapping.get(kindOfMob);
    }

    @Override
    public EntityLiving containedEntity(ItemStack stack, World w) {
        NBTTagCompound nbt = stack.getTagCompound();
        EntityLiving result;
        if (nbt.hasKey("mobDetails")) {
            result = (EntityLiving) EntityList.createEntityFromNBT(nbt.getCompoundTag("mobDetails"), w);
        } else result = (EntityLiving) EntityList.createEntityByName(kindOfMob, w);
        return result;
    }

    @Override
    public int getRenderPasses(int metadata) {
        return 2;
    }

    @Override
    public IIcon getIconFromDamageForRenderPass(int damage, int pass) {
        if (pass == 0) {
            if (fluidBlock == Blocks.flowing_water) return Items.water_bucket.getIconFromDamage(damage);
            else if (fluidBlock == Blocks.flowing_lava) return Items.lava_bucket.getIconFromDamage(damage);
            else return Items.bucket.getIconFromDamage(damage);
        } //else if (pass == 1) {
        return itemIcon;
    }

    //All the details are handled in FillBucketEvent!
}
