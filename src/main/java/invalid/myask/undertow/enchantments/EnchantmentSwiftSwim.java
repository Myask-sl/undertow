package invalid.myask.undertow.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.item.ItemStack;

import invalid.myask.undertow.Config;
import invalid.myask.undertow.ducks.IDLC;

public class EnchantmentSwiftSwim extends Enchantment {
    public EnchantmentSwiftSwim(int id, int weight, EnumEnchantmentType type) {
        super(id, weight, type);
        setName("swift_swim");
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public int getMinEnchantability(int level) {
        return -5 + 10 * level;
    }

    @Override
    public int getMaxEnchantability(int level) {
        return getMinEnchantability(level) + 10;
    }

    @Override
    public boolean canApplyTogether(Enchantment theOtherOne) {
        return !theOtherOne.getName().equals("enchantment.swift_sneak");
    }

    public static int getSwiftSwimLevel (EntityLivingBase elb, boolean forLava) {
        int swimLevel = 0;
        if (Config.enable_swift_swim) {
            ItemStack swimEquipment = elb.getEquipmentInSlot(1);
            int id = forLava ? -1 : Config.enchant_swiftswim_id;
            if (Config.swiftswim_horses && elb instanceof EntityHorse horse && horse.func_110241_cb() > 0)
                swimEquipment = ((IDLC) horse).getArmor();
            swimLevel = EnchantmentHelper.getEnchantmentLevel(id, swimEquipment);

            if (Config.swiftswim_nonpants) {
                int otherLevel;
                for (int i = 2; i < 5; i++) {
                    otherLevel = EnchantmentHelper.getEnchantmentLevel(id, elb.getEquipmentInSlot(i));
                    if (otherLevel > swimLevel) swimLevel = otherLevel;
                }
            }
        }
        return swimLevel;
    }

    public static float getSwiftSwimMultiplier(EntityLivingBase elb, boolean b) {
        return 1 + Config.swift_swim_multiplier * getSwiftSwimLevel(elb, b);
    }
}
