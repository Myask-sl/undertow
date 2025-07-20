package invalid.myask.undertow.enchantments;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;

import invalid.myask.undertow.item.ItemTrident;

public class EnchantmentLoyalty extends EnchantmentTrident {
    public EnchantmentLoyalty(int id, int weight) {
        super(id, weight, EnumEnchantmentType.weapon);
    }

    @Override
    public int getMinEnchantability(int level) {
        return 5 + 7 * level;
    }

    @Override
    public int getMaxEnchantability(int level) {
        return 50;
    }

    @Override
    public String getName() {
        return "enchantment.trident.lanyard";
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return stack.getItem() instanceof ItemTrident; //allow spears to get loyalty
    }
}
