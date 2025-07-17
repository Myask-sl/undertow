package invalid.myask.undertow.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDurability;
import net.minecraft.item.ItemStack;

public class EnchantmentCurseBreaking extends EnchantmentDurability {
    public EnchantmentCurseBreaking(int id, int weight) {
        super(id, weight);
        setName("curse.durability");
    }

    @Override
    public int getMinEnchantability(int level) {
        return 25;
    }

    @Override
    public int getMaxEnchantability(int level) {
        return 50;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public String getName() {
        return "enchantment.curse.durability";
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean canApplyTogether(Enchantment theOther) {
        return theOther != Enchantment.unbreaking;
    }
}
