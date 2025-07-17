package invalid.myask.undertow.loottables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

import invalid.myask.undertow.ducks.ILootTable;
import invalid.myask.undertow.mixins.ChestGenHooksGetter;

public class MultiPoolLootTable extends ChestGenHooks {
    protected List<ChestGenHooks> otherPools = new ArrayList<>(1);
    public MultiPoolLootTable(String category) {
        super(category);
    }

    public MultiPoolLootTable(String category, WeightedRandomChestContent[] items, int min, int max) {
        super(category, items, min, max);
    }
    public MultiPoolLootTable fromIterable(String category, Iterable<WeightedRandomChestContent> items, int min, int max) {
        List<WeightedRandomChestContent> newItems = new ArrayList<>();
        for (WeightedRandomChestContent item : items) {
            newItems.add(item);
        }
        return new MultiPoolLootTable(category, newItems.toArray(new WeightedRandomChestContent[0]), min, max);
    }

    public void addItem(WeightedRandomChestContent item, int poolSlot) {
        if (poolSlot == 0)
            super.addItem(item);
        else {
            if (poolSlot - 1  >= otherPools.size())
                otherPools.add(new ChestGenHooks(nameIndex(otherPools.size())));
            ChestGenHooks pool = otherPools.get(poolSlot - 1);
            if (pool == null) {
                pool = new ChestGenHooks(nameIndex(poolSlot));
            }
            pool.addItem(item);
        }
    }

    public void addAllItems(Iterable<WeightedRandomChestContent> items, int poolSlot) {
        for (WeightedRandomChestContent item : items) {
            addItem(item, poolSlot);
        }
    }

    public void addAllItemsToNewPool(Iterable<WeightedRandomChestContent> items) {
        addAllItems(items, otherPools.size() + 1);
    }

    public void addAllItemsToNewPool(WeightedRandomChestContent... items) {
    //    List<WeightedRandomChestContent> newItems = new ArrayList<>(items.length);
     //   newItems.addAll();
        addAllItemsToNewPool(Arrays.asList(items));
    }

    public void removeItem(ItemStack item, int poolSlot) {
        if (item == null) return;
        if (poolSlot == 0)
            super.removeItem(item);
        else {
            ChestGenHooks pool = otherPools.get(poolSlot - 1);
            if (pool == null) {
                pool = new ChestGenHooks(nameIndex(poolSlot));
            } else {
                pool.removeItem(item);
            }
        }
    }
    public void setPoolMinDraws(int poolSlot, int min) {
        if (poolSlot == 0)
            setMin(min);
        else  if (poolSlot - 1 < otherPools.size()){
            otherPools.get(poolSlot - 1).setMin(min);
        }
    }
    public void setPoolMaxDraws(int poolSlot, int max) {
        if (poolSlot == 0)
            setMax(max);
        else if (poolSlot - 1 < otherPools.size())
            otherPools.get(poolSlot - 1).setMax(max);
    }
    public void setPoolDrawRange(int poolSlot, int min, int max) {
        setPoolMinDraws(poolSlot, min);
        setPoolMaxDraws(poolSlot, max);
    }
    public void setLastPoolDrawRange(int min, int max) {
        setPoolDrawRange(otherPools.size(), min, max);
    }

    private String nameIndex(int poolSlot) {
        return ((ChestGenHooksGetter)this).getCategory() + poolSlot;
    }

    public void fillInventory (IInventory inventory, Random rng) {
        if (inventory != null) {
            int slot, initSlot;
            out:
            for (int i = 0; i <= otherPools.size(); i++) {
                ChestGenHooks thisPool = (i == 0) ? this : otherPools.get(i - 1);
                int maxCount = thisPool.getCount(rng);
                for (int round = 0; round < maxCount; round++) {
                    slot = rng.nextInt(inventory.getSizeInventory());
                    initSlot = slot;
                    while (inventory.getStackInSlot(slot) != null) {
                        slot = (slot + 1) % inventory.getSizeInventory();
                        if (slot == initSlot) break out; //out of room, did full cycle
                    }
                    inventory.setInventorySlotContents(slot, thisPool.getOneItem(rng));
                }
            }
        }
    }

    public List<ItemStack> getOutput(Random rng) {
        ArrayList<ItemStack> output = new ArrayList<>();
        for (int i = 0; i <= otherPools.size(); i++) {
            if (i == 0) output.addAll(((ILootTable)this).getOutputCGH(rng));
            else output.addAll(((ILootTable)otherPools.get(i - 1)).getOutput(rng));
           /* ChestGenHooks thisPool = (i == 0) ? this : otherPools.get(i - 1);
            int maxCount = thisPool.getCount(rng);
            for (int round = 0; round < maxCount; round++) {
                output.add(thisPool.getOneItem(rng));
            }
            */
        }
        return output;
    }
}
