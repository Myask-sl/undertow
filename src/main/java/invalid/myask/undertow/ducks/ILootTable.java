package invalid.myask.undertow.ducks;

import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Random;

public interface ILootTable {
    List<ItemStack> getOutput(Random rng);

    List<ItemStack> getOutputCGH(Random rng);
}
