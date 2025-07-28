package invalid.myask.undertow.entities.fish;

import net.minecraft.init.Items;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class AbstractTropicalFish extends BaseFish {
    public AbstractTropicalFish(World myWorld, ItemStack deadFish, ItemStack cookedFish) {
        super(myWorld, new ItemStack(Items.fish, 1, ItemFishFood.FishType.CLOWNFISH.func_150976_a()),
            new ItemStack(Items.fish, 1, ItemFishFood.FishType.CLOWNFISH.func_150976_a()));
    }
}
