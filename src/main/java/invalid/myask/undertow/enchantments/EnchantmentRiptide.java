package invalid.myask.undertow.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

import invalid.myask.undertow.Config;
import invalid.myask.undertow.UndertowEnchantments;
import invalid.myask.undertow.util.UndertowUtils;

public class EnchantmentRiptide extends EnchantmentTrident {
    private static int ONE_BLOCK_FLIGHT = 1;
    private static double[] THRUST =  {4.1D, 6D, 7.9D};

    public EnchantmentRiptide(int id, int weight) {
        super(id, weight, EnumEnchantmentType.weapon);
    }

    @Override
    public String getName() {
        return "enchantment.trident.aquafly";
    }

    public static boolean canFly(int level, EntityLivingBase elb) {
        if (level > 3) return true; //SuperFly!
        boolean flag = false;
        if (elb.worldObj != null) {
            flag = UndertowUtils.rainCheck(elb);
        }
        return flag || elb.isInWater();
    }

    public static boolean canFly(ItemStack trident, EntityLivingBase elb) {
        return canFly (EnchantmentHelper.getEnchantmentLevel(UndertowEnchantments.RIPTIDE.effectId, trident), elb);
    }

    public int getMaxFlightTime(int level) {
        if (Config.riptide_impulse_instant)
            return 30;
        else return (3 + 6 * level) * ONE_BLOCK_FLIGHT;
    }

    public double getThrust(int level) {
        if (Config.riptide_impulse_instant)
            return THRUST[MathHelper.clamp_int(level, 1, 3) - 1];
        else return 1;
    }

    @Override
    public int getMaxLevel() {
        return Config.riptide_super_enabled ? 4 : 3;
    }

    @Override
    public boolean canApplyTogether(Enchantment theOther) {
        return theOther != this && theOther != UndertowEnchantments.CHANNELING && theOther != UndertowEnchantments.LOYALTY;
    }
}
