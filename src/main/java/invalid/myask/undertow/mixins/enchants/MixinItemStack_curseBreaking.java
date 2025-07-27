package invalid.myask.undertow.mixins.enchants;

import java.util.Random;

import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

import invalid.myask.undertow.Config;

@Mixin(ItemStack.class)
public class MixinItemStack_curseBreaking {
    @ModifyVariable(
        method = "attemptDamageItem",
        at = @At(value = "HEAD"),
        argsOnly = true)
    private int breakMore(int original, @Local(argsOnly = true) Random rand) {
        if (Config.enable_curse_breaking) {
            int breaking = EnchantmentHelper.getEnchantmentLevel(Config.enchant_curse_breaking_id, (ItemStack) (Object) this);
            for (int i = 0; i < breaking; ++i) {
                if (rand.nextInt(5) < 3) {
                    ++original;
                }
            }
        }
        return original;
    }
}
