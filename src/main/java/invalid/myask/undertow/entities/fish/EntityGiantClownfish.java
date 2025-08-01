package invalid.myask.undertow.entities.fish;

import net.minecraft.init.Items;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityGiantClownfish extends BaseFish {
    static ItemStack concrete_shoes = new ItemStack(Items.fish, 1, ItemFishFood.FishType.CLOWNFISH.func_150976_a());
    public EntityGiantClownfish(World myWorld) {
        super(myWorld, concrete_shoes.copy(), concrete_shoes.copy());
    }
}
