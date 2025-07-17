package invalid.myask.undertow.enchantments;

import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

public class EnchantmentCleaving extends EnchantmentDamage {
    public EnchantmentCleaving(int iD, int weight) {
        super(iD, weight, 0);

        setName("cleaving");
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return stack != null && stack.getItem() instanceof ItemAxe;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinEnchantability(int level) {
        return 3 + 5 * level;
    } //do as Smite, etc. for now

    @Override
    public int getMaxEnchantability(int level) {
        return getMinEnchantability(level) + 20;
    }

    /**
     * Damage modifier function.
     * @param enchantment_amplitude I, II, III...how enchanted are we?
     * @param targetKind The kind of creature this hit.
     * @return damage bonus to what's hit.
     */
    public float func_152376_a(int enchantment_amplitude, EnumCreatureAttribute targetKind) {
        return 1 + enchantment_amplitude;
    }
    /**
     * Applies effects to the stricken if applicable.
     * @param aggressor The attacker.
     * @param stricken The defender.
     * @param baneKind The kind of creature this is good against.
     */
    public void func_151368_a(EntityLivingBase aggressor, Entity stricken, int baneKind) {}
}
