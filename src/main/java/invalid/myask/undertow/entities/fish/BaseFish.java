package invalid.myask.undertow.entities.fish;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;

import invalid.myask.undertow.Config;

public class BaseFish extends EntityWaterMob {
    public ItemStack corpse, cookedCorpse; //TODO: these should be static maybe?
    public BaseFish(World myWorld) {
        this(myWorld, new ItemStack(Items.fish, 1, 0), new ItemStack(Items.cooked_fished, 1, 0));
    }
    public BaseFish(World myWorld, ItemStack deadFish, ItemStack cookedFish) {
        super(myWorld);
        corpse = deadFish;
        cookedCorpse = cookedFish;
    }

    public void setCorpses(ItemStack raw, ItemStack done) {
        corpse = raw;
        cookedCorpse = done;
    }

    //Mixin of super handles Aquatic getCreatureAttribute()
    //HearTheOcean#despawnAFish handles despawnMob()

    public boolean wasBucketed() {
        return this.getEntityData().getByte("FromBucket") == 1;
    }

    public boolean validSpawnBiome() {
        return BiomeDictionary.isBiomeOfType(worldObj.getBiomeGenForCoords((int) posX, (int) posZ), BiomeDictionary.Type.OCEAN);
    }

    public boolean checkSpawnWater(int x, int y, int z) {
        Block block = worldObj.getBlock(x, y, z);
        if (Config.fish_spawn_weirdwater) {
            return block.getMaterial() == Material.water;
        }
        return block == Blocks.water || block == Blocks.flowing_water;
    }

    @Override
    public boolean getCanSpawnHere() {
        if (super.getCanSpawnHere()) {
            if (posY >= Config.fish_spawn_y_min && posY <= Config.fish_spawn_y_max &&
                validSpawnBiome() &&
                worldObj.getBlock((int)posX, (int)posY, (int)posZ).getMaterial() == Material.water &&
                worldObj.getBlock((int)posX, (int)posY - 1, (int)posZ).getMaterial() == Material.water &&
                checkSpawnWater((int)posX, (int)posY - 1, (int)posZ)) {
                EntityPlayer fg = this.worldObj.getClosestPlayerToEntity(this, Config.fish_spawn_dist_max);
                return fg != null && this.getDistanceToEntity(fg) >= Config.fish_spawn_dist_min;
            }
        }
        return false;
    }

    @Override
    public boolean allowLeashing() {
        return false;
    }

    @Override
    protected void dropFewItems(boolean playerResponsible, int looting) {
        if (Config.fishbone_drop_bedrock)
            if (this.rand.nextInt(100) < 25 + looting)
                dropItem(Items.bone, 1);
        else if (this.rand.nextInt(20) == 0)
                entityDropItem(new ItemStack(Items.dye, 1, 15), 0);

        if (this.isBurning())
            entityDropItem(cookedCorpse.copy(), 0);
        else
            entityDropItem(corpse.copy(), 0);
    }
}
