package invalid.myask.undertow.mixins.enchants;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentArrowInfinite;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;

import invalid.myask.undertow.Config;
import invalid.myask.undertow.item.ItemTrident;

@Mixin(EnchantmentArrowInfinite.class)
public class MixinEnchantmentArrowInfinite_allowTrident extends Enchantment {
    protected MixinEnchantmentArrowInfinite_allowTrident(int id, int weight, EnumEnchantmentType type) {
        super(id, weight, type);
    }

    //@ModifyReturnValue(method="canApply", at = @At("RETURN"))
    //private boolean applyToTrident(boolean original, @Local(argsOnly = true) ItemStack stack) {
    @Override
    public boolean canApply(ItemStack stack) {
        return super.canApply(stack) || (Config.infinity_applicable_trident && stack.getItem() instanceof ItemTrident);
    }
}
