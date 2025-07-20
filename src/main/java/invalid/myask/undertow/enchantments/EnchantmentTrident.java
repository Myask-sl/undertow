package invalid.myask.undertow.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;

import invalid.myask.undertow.item.ItemSpear;
import invalid.myask.undertow.item.ItemTrident;

public abstract class EnchantmentTrident extends Enchantment {
    protected EnchantmentTrident(int id, int weight, EnumEnchantmentType enchiladaType) {
        super(id, weight, enchiladaType);
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return stack.getItem() instanceof ItemTrident
            && !(stack.getItem() instanceof ItemSpear);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}
