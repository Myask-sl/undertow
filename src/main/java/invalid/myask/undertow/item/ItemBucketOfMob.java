package invalid.myask.undertow.item;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemBucketOfMob extends ItemBucket implements IVesselOfMob<EntityLiving> {
    public final String kindOfMob;
    public ItemBucketOfMob(Block whichFluid, Class<? extends EntityLiving> kindOfMob) {
        super(whichFluid);
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

    //All the details are[TODO: will be] handled in FillBucketEvent!
}
