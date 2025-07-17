package invalid.myask.undertow.enchantments;

import net.minecraft.enchantment.EnumEnchantmentType;

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
}
