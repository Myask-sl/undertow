package invalid.myask.undertow.world;


import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.*;

import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.BlockFluidBase;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import invalid.myask.undertow.Config;
import invalid.myask.undertow.UndertowItems;
import invalid.myask.undertow.loottables.MultiPoolLootTable;
import invalid.myask.undertow.util.UndertowUtils;

public class BuriedTreasureGen extends MapGenStructure {
    public static BuriedTreasureGen instance = new BuriedTreasureGen();
    public static final Set<Integer> dimensionsGenerating = new HashSet<>();
    public static MultiPoolLootTable lootTable = new MultiPoolLootTable("undertow:buried_treasure");

    static {
        if (Config.generate_buried_treasure) {
            dimensionsGenerating.add(0);//default dimension
            lootTable.addItem(new WeightedRandomChestContent(UndertowItems.SEAHEART, 0, 1, 1, 1));
            lootTable.setPoolDrawRange(0, 1, 1);
            if (Config.buried_treasure_loot_bedrock) {
                lootTable.addAllItemsToNewPool(
                    new WeightedRandomChestContent(Items.iron_ingot, 0, 3, 5, 20),
                    new WeightedRandomChestContent(Items.chainmail_helmet, 0, 1, 1, 20),
                    new WeightedRandomChestContent(Items.chainmail_chestplate, 0, 1, 1, 20),
                    new WeightedRandomChestContent(Items.chainmail_leggings, 0, 1, 1, 20),
                    new WeightedRandomChestContent(Items.chainmail_boots, 0, 1, 1, 20),
                    new WeightedRandomChestContent(Items.potionitem, 8237, 1, 1, 15),
                    new WeightedRandomChestContent(Items.diamond, 0, 1, 1, 15),
                    new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 5, 10),
                    new WeightedRandomChestContent(Items.lead, 0, 1, 3, 10),
                    new WeightedRandomChestContent(new ItemStack(Blocks.tnt), 1, 2, 10),
                    new WeightedRandomChestContent(Items.name_tag, 0, 1, 1, 10),
                    new WeightedRandomChestContent(Items.potionitem, Potion.regeneration.getId(), 1, 1, 10),
                    new WeightedRandomChestContent(Items.writable_book, 0, 1, 2, 5),
                    new WeightedRandomChestContent(Items.record_mellohi, 0, 1, 1, 5),
                    new WeightedRandomChestContent(Items.record_wait, 0, 1, 1, 5),
                    new WeightedRandomChestContent(Items.experience_bottle, 0, 1, 1, 3),
                    new WeightedRandomChestContent(Items.cake, 0, 1, 1, 1));
                lootTable.setPoolDrawRange(1, 5, 12);
                if (OreDictionary.doesOreNameExist("dustPrismarine")) {
                    lootTable.addItem(new WeightedRandomChestContent(OreDictionary.getOres("dustPrismarine").get(0), 1, 5, 5), 1);
                }
            } else { //Java edition tables
                lootTable.addAllItemsToNewPool(
                    new WeightedRandomChestContent(new ItemStack(Blocks.tnt), 1, 2, 5),
                    new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 4, 20),
                    new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 2, 10)
                );
                lootTable.setPoolDrawRange(1, 5, 8);

                lootTable.addAllItemsToNewPool(
                    new WeightedRandomChestContent(Items.diamond, 0, 1, 2, 5),
                    new WeightedRandomChestContent(Items.emerald, 0, 4, 8, 5)
                );
                if (OreDictionary.doesOreNameExist("dustPrismarine")) {
                    lootTable.addItem(new WeightedRandomChestContent(OreDictionary.getOres("dustPrismarine").get(0), 1, 5, 5), 2);
                }
                lootTable.setPoolDrawRange(2, 1, 4);

                lootTable.addAllItemsToNewPool(
                    new WeightedRandomChestContent(Items.leather_chestplate, 0, 1, 1, 1),
                    new WeightedRandomChestContent(Items.iron_sword, 0, 1, 1, 1)
                    );
                lootTable.setPoolDrawRange(3, 0, 1);

                lootTable.addAllItemsToNewPool(
                    new WeightedRandomChestContent(Items.cooked_fished, 0, 2, 4, 1),
                    new WeightedRandomChestContent(Items.cooked_fished, 1, 2, 4, 1) //salmon
                );
                lootTable.setPoolDrawRange(4, 2, 2);

                lootTable.addAllItemsToNewPool(
                    new WeightedRandomChestContent(Items.potionitem, Potion.waterBreathing.getId(), 1, 1, 1)
                );
                lootTable.setPoolDrawRange(5, 0, 2);
            }
        }
    }
    BuriedTreasureGen() {
        range = 1;
    }
    @Override
    public String func_143025_a() {
        return "buried_treasure";
    }

    @Override
    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
        if (!dimensionsGenerating.contains(worldObj.provider.dimensionId)) return false;
        this.rand.setSeed(worldObj.getSeed());
        long xStructFactor = rand.nextLong(),
            zStructFactor = rand.nextLong();
        this.rand.setSeed(worldObj.getWorldInfo().getSeed() ^ (chunkX * xStructFactor) ^ (chunkZ * zStructFactor) ^
            0x72b846b287523aafL);
        if (this.rand.nextInt(Config.buried_treasure_chance_denominator) >= Config.buried_treasure_chance_numerator)
            return false;
        BiomeGenBase biomeGenForCoords = worldObj.getBiomeGenForCoords((chunkX << 4) + Config.buried_treasure_x, (chunkZ << 4) + Config.buried_treasure_z);
        if (!BiomeDictionary.isBiomeOfType(biomeGenForCoords, BiomeDictionary.Type.BEACH)
            && !BiomeDictionary.isBiomeOfType(biomeGenForCoords, BiomeDictionary.Type.OCEAN)) {
            return false;
        }

        return true;
    }

    @Override
    protected StructureStart getStructureStart(int chunkX, int chunkZ) {
        StructureStart theStart = new Start(worldObj, rand, chunkX, chunkZ);
        return theStart;
    }

    public class Start extends StructureStart {
        protected BuriedTreasureComponent theChest;
        World world;
        Random rand;
        int y;
        Start() {}
        Start(World w, Random rng, int chunkX, int chunkZ) {
            super(chunkX, chunkZ);
            world = w;
            rand = rng;
            this.rand.setSeed(world.getSeed());
            long xStructFactor = rand.nextLong(),
                zStructFactor = rand.nextLong();
            this.rand.setSeed(world.getSeed() ^ (chunkX * xStructFactor) ^ (chunkZ * zStructFactor) ^
                0x72b846b287523aafL);
            this.rand.nextInt(Config.buried_treasure_chance_denominator);

            int x = chunkX * 16 + Config.buried_treasure_x,
                z = chunkZ * 16 + Config.buried_treasure_z,
                i, meta = 0, topY = UndertowUtils.topSolidBlock(worldObj, x, z),
                y = 64;
            Block block = Blocks.sand;
            for (i = 0; i < 10; i++) {
                y = topY - rand.nextInt(Config.buried_treasure_y_depth_max);
                block = world.getBlock(x, y, z);
                meta = world.getBlockMetadata(x, y, z);
                Material m = block.getMaterial();
                if (m != Material.air && m != Material.lava && m != Material.water
                    && !(block instanceof BlockFluidBase)) {
                    break;
                }
            }
            theChest = new BuriedTreasureComponent(x, y, z, block, meta);
            components.add(theChest);
            theChest.buildComponent(theChest, components, rand);
            boundingBox = new StructureBoundingBox(x, y, z, x, y, z);
        }

        @Override
        public void func_143017_b(NBTTagCompound tagCompound) {
            super.func_143017_b(tagCompound);
            y = boundingBox.maxY;
        }

        @Override
        public void func_143020_a(World worldIn, NBTTagCompound tagCompound) {
            super.func_143020_a(worldIn, tagCompound);
            world = worldIn;
            rand = worldIn.rand;
        }
    }
    public static void registerPiece(){
        MapGenStructureIO.func_143031_a(BuriedTreasureComponent.class, "treasurechestcomp");
    }

    public class BuriedTreasureComponent extends StructureComponent {
        protected Block replacing;
        protected int x,y,z, meta;
        protected boolean genned = false;
        public BuriedTreasureComponent(int x, int y, int z, Block block, int meta) {
            boundingBox = new StructureBoundingBox(x,y,z,x,y,z);
            replacing = block;
            this.x = x;
            this.y = y;
            this.z = z;
            this.meta = meta;
        }

        /**
         * This is a WriteToNBT, for specific things. It's called from one that writes
         * bounding box, ID, coordBaseMode, componentType.
         * @param nbtToWrite
         */
        @Override
        protected void func_143012_a(NBTTagCompound nbtToWrite) {
            nbtToWrite.setInteger("Width", 1);
            nbtToWrite.setInteger("Height", 1);
            nbtToWrite.setInteger("Depth", 1);
            nbtToWrite.setBoolean("genned", genned);
//            nbtToWrite.setInteger("HPos", 0);
        }

        /**
         * This is a ReadFromNBT. As above, called from one that handles those.
         * @param nbtToRead
         */
        @Override
        protected void func_143011_b(NBTTagCompound nbtToRead) {
            if (boundingBox != null ) {
                x = boundingBox.minX;
                y = boundingBox.minY;
                z = boundingBox.minZ;
            } else {
                x = Config.buried_treasure_x; y = 64; z = Config.buried_treasure_z;
                boundingBox = new StructureBoundingBox(x,y,z,x,y,z);
            }
            if (y < Config.world_height_min || y > Config.world_height_max) y = 64;
            genned = nbtToRead.getBoolean("genned");
        }

        @Override
        public boolean addComponentParts(World worldIn, Random rng, StructureBoundingBox placeIn) {
            return false;
        }

        @Override
        public void buildComponent(StructureComponent it, List<StructureComponent> allegedlyComponents, Random rng) {
            if (genned) return;
            if (BuriedTreasureGen.this.worldObj.getBlock(x, y, z) instanceof BlockChest) {
                genned = true;
                return;
            }
            BuriedTreasureGen.this.worldObj.setBlock(x, y, z, Blocks.chest, 2 + rng.nextInt(4), 2);
            for (ForgeDirection fDir : ForgeDirection.VALID_DIRECTIONS) {
                if (fDir == ForgeDirection.DOWN) continue;
                BuriedTreasureGen.this.worldObj.setBlock(x + fDir.offsetX, y + fDir.offsetY, z + fDir.offsetZ, replacing, meta, 2);
            }
            TileEntityChest theChest = (TileEntityChest)BuriedTreasureGen.this.worldObj.getTileEntity(x,y,z);
            if (theChest != null)
                fillChest(theChest, BuriedTreasureGen.lootTable, rng);
            genned = true;
        }
    }

    private void fillChest(IInventory theChest, MultiPoolLootTable lootTable, Random rng) {
        lootTable.fillInventory(theChest, rng);
    }
}
