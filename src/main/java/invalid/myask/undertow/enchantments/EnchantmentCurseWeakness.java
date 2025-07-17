package invalid.myask.undertow.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.item.ItemStack;

public class EnchantmentCurseWeakness extends EnchantmentExtendedDamage {
    protected static final String[] boon_names ={"all","undead","arthropods","aquatic"};

    public EnchantmentCurseWeakness(int id, int weight, int targetedKind) {
        super(id, weight, targetedKind);
    }

    /**
     * Damage modifier function.
     * @param enchantment_amplitude I, II, III...how enchanted are we?
     * @param targetKind The kind of creature this helps.
     * @return Damage bonus(/penalty) to what's hit.
     */
    @Override
    public float func_152376_a(int enchantment_amplitude, EnumCreatureAttribute targetKind) {
        return -super.func_152376_a(enchantment_amplitude, targetKind);
    }

    @Override
    public float damageBonusTo(int enchantment_amplitude, EntityLivingBase stricken) {
        return -super.damageBonusTo(enchantment_amplitude, stricken);
    }

    @Override
    public int getMinEnchantability(int level) {
        return 13 + 8 * level;
    }

    @Override
    public int getMaxEnchantability(int level) {
        return this.getMinEnchantability(level) + 20;
    }

    @Override
    public String getName() {
        return "enchantment.curse.damage." + boon_names[damageType];
    }

    @Override
    public void func_151368_a(EntityLivingBase aggressor, Entity stricken, int baneKind) {
        //do nothing so Arthropods don't get slowed by Bugsboon.
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean canApplyTogether(Enchantment theOther) {
        return !(theOther instanceof EnchantmentDamage endam && endam.damageType == damageType);
    }
}
