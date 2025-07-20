package invalid.myask.undertow.mixins;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

import invalid.myask.undertow.ducks.ILootTable;

@Mixin(value = ChestGenHooks.class, remap = false)
public abstract class MixinChestGenHooks implements ILootTable {
    @Shadow
    private ArrayList<WeightedRandomChestContent> contents;

    @Shadow
    public abstract int getCount(Random rand);
    @Override
    public List<ItemStack> getOutput(Random rng) { //TODO: shouldn't this be in InCommand?
        return getOutputCGH(rng);
    }

    public List<ItemStack> getOutputCGH(Random rng) {
        ArrayList<ItemStack> output = new ArrayList<>();
        int rounds = getCount(rng); //ugh, vanilla has bug--can't gen max.
        for (int i = 0; i < rounds; i++) {
            WeightedRandomChestContent wrcc = (WeightedRandomChestContent) WeightedRandom.getRandomItem(rng, contents);
            ItemStack[] summation = ChestGenHooks.generateStacks(rng, wrcc.theItemId, wrcc.theMinimumChanceToGenerateItem, wrcc.theMaximumChanceToGenerateItem);
            if (summation.length > 1 && wrcc.theItemId.getMaxStackSize() > 1) {
                int total = 0;
                for (ItemStack stack : summation) {
                    total += stack.stackSize;
                }
                while (total > wrcc.theItemId.getMaxStackSize()) { //collapse them back into max stacks
                    total -= wrcc.theItemId.getMaxStackSize();
                    ItemStack newstack = wrcc.theItemId.copy();
                    newstack.stackSize = newstack.getMaxStackSize();
                    output.add(newstack);
                }
                if (total > 0) {
                    ItemStack newstack = wrcc.theItemId.copy();
                    newstack.stackSize = total;
                    output.add(newstack);
                }
            } else output.addAll(Arrays.asList(summation));
        }
        return output;
    }
}
