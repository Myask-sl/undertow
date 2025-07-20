package invalid.myask.undertow.ducks;

import java.util.List;
import java.util.Random;

import net.minecraft.item.ItemStack;

public interface ILootTable {
    List<ItemStack> getOutput(Random rng);

    List<ItemStack> getOutputCGH(Random rng);
}
