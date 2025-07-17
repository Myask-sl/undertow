package invalid.myask.undertow.enchantments;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class EnchantmentUndertow extends EnchantmentTrident {
    public EnchantmentUndertow(int id, int weight, EnumEnchantmentType enchiladaType) {
        super(id, weight, enchiladaType);
        setName("undertow");
    }

    @Override
    public void func_151368_a(EntityLivingBase aggressor, Entity stricken, int level) {
        super.func_151368_a(aggressor, stricken, level);
        if (stricken.isInWater())
            stricken.motionY -= 0.8F;
    }
}
